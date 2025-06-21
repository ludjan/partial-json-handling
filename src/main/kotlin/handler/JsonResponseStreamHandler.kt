package handler

import StaticLogger.Companion.debugLog
import kotlinx.serialization.*
import kotlinx.serialization.json.*

/**
 * A JsonResponseStreamHandler for keeping track of <jsonPath, block>,
 * and running block when the jsonPath is received
 */
class JsonResponseStreamHandler(val json: Json = Json { ignoreUnknownKeys = true }) {

    // maps of jsonPaths and functions to run
    val typedHandlers = mutableMapOf<String, HandlerEntry<*>>()
    val buffers = mutableMapOf<String, StringBuilder>()

    // for registering a path and a handler function which takes a generic parameter
    inline fun <reified T> registerHandler(path: String, noinline handler: (T) -> Unit) {
        debugLog("About to register handler for path: $path and type: ${T::class}")
        val serializer = json.serializersModule.serializer<T>()
        typedHandlers[path] = HandlerEntry(serializer, handler)
        buffers[path] = StringBuilder()
        debugLog("Registered handler for path: $path and type: ${T::class}")
    }

    /**
     * Receive chunk and add it to the buffer for the field
     * Then try to parse it into any of the available @serializable data classes
     * On success, remove that path from the buffer, and invoke the handler with the parsed element
     * On failure, just debug print it
     */
    fun receiveChunk(path: String, chunk: String) {
        // create a buffer if we don't have one already
        val buffer = buffers.getOrPut(path) { StringBuilder() }
        // append the chunk to the buffer
        buffer.append(chunk)

        // get all the json we have currently from the buffer
        val candidateJson = buffer.toString()

        try {
            // parse it to a kotlinx JsonElement
            val element = json.parseToJsonElement(candidateJson)

            // see if we registered a handler for that path
            val entry = typedHandlers[path]
            if (entry != null) {
                // if we did, assume it has the format of the data class it was said to serialize to
                @Suppress("UNCHECKED_CAST")
                val castedEntry = entry as HandlerEntry<Any>
                val parsed = json.decodeFromJsonElement(castedEntry.serializer, element)

                // call the handler with the parsed element (Profile, Location, List<Posts> etc.)
                castedEntry.handler(parsed)
                // empty the buffer for this path
                buffers.remove(path)
            } else {
                debugLog("No handler registered for path $path")
            }
        } catch (e: SerializationException) {
            debugLog("Exception thrown: $e")
        }
    }

    /**
     * Allow users to register a block of handlers
     */
    fun registerHandlers(block: HandlerRegistrar.() -> Unit) {
        val registrar = HandlerRegistrar(this)
        registrar.block()
    }
}

