package engineer.carrot.warren.thump.api

import net.minecraftforge.common.config.Configuration

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ThumpServicePlugin

interface IThumpServicePlugin {

    val id: String

    val commandHandler: ICommandHandler

    fun configure(context: ThumpPluginContext)
    fun start()
    fun stop()
    fun status(): List<String>

    fun onMinecraftMessage(message: String)
    fun anyConnectionsMatch(name: String): Boolean

}

data class ThumpPluginContext(val configuration: Configuration, val minecraftSink: IThumpMinecraftSink, val serviceSink: IThumpServiceSink)

interface IThumpServicePluginConfig {

    fun load()
    fun save()

}

interface IThumpMinecraftSink {

    fun sendToAllPlayers(source: String, message: String)
    fun sendToAllPlayersWithoutCheckingSource(message: String)

}

interface IThumpServiceSink {

    fun sendToAllServices(message: String)

}