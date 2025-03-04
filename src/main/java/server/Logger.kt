package server

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch

object LogManager {
    data class LogEntry(val prefix: String, val data: String)

    private lateinit var logChannel: SendChannel<LogEntry>
    private var isEnabled = false
    fun enable() {
        fun CoroutineScope.loggerActor() = actor<LogEntry> {
            for (entry in channel) {
                println("[${entry.prefix}]\t${entry.data}")
            }
        }
        logChannel = GlobalScope.loggerActor()
        isEnabled = true
    }

    suspend fun send(prefix: String, data: String) {
        if (isEnabled) {
            logChannel.send(LogEntry(prefix, data))
        }
    }
}

class Logger(private val prefix: String) {
    fun log(data: String) {
        GlobalScope.launch(Dispatchers.Default) {
            LogManager.send(prefix, data)
        }
    }
}