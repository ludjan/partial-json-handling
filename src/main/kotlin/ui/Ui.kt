package ui

import json.Location
import json.Post
import json.Profile
import json.SocialLink

class Ui {

    fun renderWelcome(username: String) {
        println("Welcome $username!")
    }

    fun renderProfileInformation(profile: Profile) {
        println(
            StringBuilder()
                .appendLine()
                .appendLine("Information:")
                .appendLine("- Name: ${profile.fullName}")
                .appendLine("- Bio: ${profile.bio}")
                .appendLine("- Picture: ${profile.picture}")
        )
    }

    fun renderLocationInformation(location: Location) {
        println(
            StringBuilder()
                .appendLine()
                .appendLine("Location: ${location.city}, ${location.country}")
        )
    }

    fun renderPosts(posts: List<Post>) {
        println("\nPosts:")
        posts.forEach { post ->
            println(
                StringBuilder()
                    .appendLine("${post.title.uppercase()} (${post.likes} ^)")
                    .appendLine(post.content)
                    .appendLine(post.tags)
            )
        }
    }

    fun renderSocialLinks(socialLinks: List<SocialLink>) {
        println("\nLinks:")
        socialLinks.forEach { link ->
            println("${link.platform} [${link.url}]")
        }
    }
}