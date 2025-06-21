package simulation

import handler.JsonResponseStreamHandler
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject

class Simulator {

    val usernameJson = JsonPrimitive("cool_user_99")

    val locationJson = buildJsonObject {
        put("city", JsonPrimitive("Stockholm"))
        put("country", JsonPrimitive("Sweden"))
        put("coordinates", buildJsonObject {
            put("lat", JsonPrimitive(59.3293))
            put("lon", JsonPrimitive(18.0686))
        })
    }

    val socialLinksJson = buildJsonArray {
        add(buildJsonObject {
            put("platform", JsonPrimitive("twitter"))
            put("url", JsonPrimitive("https://twitter.com/cool_user_99"))
        })
        add(buildJsonObject {
            put("platform", JsonPrimitive("github"))
            put("url", JsonPrimitive("https://github.com/cool_user_99"))
        })
    }

    val profileJson = buildJsonObject {
        put("fullName", JsonPrimitive("Ludvig Example"))
        put("picture", JsonPrimitive("https://example.com/pictures/ludvig.jpg"))
        put("bio", JsonPrimitive("Just a Kotlin enthusiast building neat stuff."))
        put("location", locationJson)
        put("socialLinks", socialLinksJson)
    }

    val postsJson = buildJsonArray {
        add(buildJsonObject {
            put("id", JsonPrimitive(1))
            put("title", JsonPrimitive("Hello Kotlin!"))
            put("content", JsonPrimitive("Loving Kotlin so far. It’s concise and expressive."))
            put("tags", buildJsonArray {
                add(JsonPrimitive("kotlin"))
                add(JsonPrimitive("programming"))
                add(JsonPrimitive("fun"))
            })
            put("published", JsonPrimitive(true))
            put("likes", JsonPrimitive(150))
        })
        add(buildJsonObject {
            put("id", JsonPrimitive(2))
            put("title", JsonPrimitive("JSON streaming"))
            put("content", JsonPrimitive("Working on a library to handle JSON streams with Kotlin."))
            put("tags", buildJsonArray {
                add(JsonPrimitive("json"))
                add(JsonPrimitive("streams"))
                add(JsonPrimitive("kotlin"))
            })
            put("published", JsonPrimitive(false))
            put("likes", JsonPrimitive(45))
        })
    }

    fun simulateStreaming(streamHandler: JsonResponseStreamHandler) {

        val chunks = listOf(
            { streamHandler.receiveChunk("user.username", usernameJson.toString()) },
            { streamHandler.receiveChunk("user.profile.location", locationJson.toString()) },
            { streamHandler.receiveChunk("user.profile", profileJson.toString()) },
            { streamHandler.receiveChunk("user.profile.posts", postsJson.toString()) },
            { streamHandler.receiveChunk("user.profile.socialLinks", socialLinksJson.toString()) }
        )

        chunks.shuffled().forEach { it() }
    }
}