import StaticLogger.Companion.debugLog
import handler.JsonResponseStreamHandler
import json.Location
import json.Post
import json.Profile
import json.SocialLink
import simulation.Simulator
import ui.Ui

fun main() {

    val ui = Ui()
    val streamHandler = JsonResponseStreamHandler()

    /**
     * Register which functions should be invoked when path successfully parses to an object
     */
    debugLog("About to register handlers")
    streamHandler.registerHandlers {
        on<String>("user.username") { ui.renderWelcome(it) }
        on<Profile>("user.profile") { ui.renderProfileInformation(it) }
        on<Location>("user.profile.location") { ui.renderLocationInformation(it) }
        on<List<Post>>("user.profile.posts") { ui.renderPosts(it) }
        on<List<SocialLink>>("user.profile.socialLinks") { ui.renderSocialLinks(it) }
    }

    val simulator = Simulator()
    simulator.simulateStreaming(streamHandler)
}