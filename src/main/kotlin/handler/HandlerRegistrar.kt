package handler

import StaticLogger.Companion.debugLog

/**
 * A HandlerRegistrar, which helps register handlers for a JsonResponseStreamHandlers
 */
class HandlerRegistrar(val handler: JsonResponseStreamHandler) {

    /**
     * Register a path and a block of code to be used in the JsonResponseStreamHandler
     */
    inline fun <reified T> on(path: String, noinline block: (T) -> Unit) {
        debugLog("About to register a handler for $path")
        handler.registerHandler(path, block)
        debugLog("Registered a handler for $path")
    }
}