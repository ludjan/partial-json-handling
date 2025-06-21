import java.time.LocalDateTime

class StaticLogger {

    companion object {

        const val DEBUG: Boolean = false

        fun log(msg: String) {
            println(msg)
        }

        fun debugLog(msg: String) {
            if (DEBUG) { log("[Debug ${LocalDateTime.now()}]: $msg") }
        }
    }
}