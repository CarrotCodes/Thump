package engineer.carrot.warren.thump.command.minecraft.handler

import com.google.common.base.Joiner
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import engineer.carrot.warren.thump.Thump
import engineer.carrot.warren.thump.config.ServerConfiguration
import engineer.carrot.warren.thump.connection.ConnectionManager
import engineer.carrot.warren.thump.connection.ConnectionState
import net.minecraft.command.ICommandSender
import net.minecraft.util.ChatComponentText
import net.minecraft.util.EnumChatFormatting

class StatusCommandHandler(private val manager: ConnectionManager) : ICommandHandler {

    override val command: String
        get() = COMMAND_NAME

    override fun processParameters(sender: ICommandSender, parameters: Array<String>) {
        val connections = this.manager.allConnections
        if (connections.isEmpty()) {
            sender.addChatMessage(ChatComponentText("Thump is not configured to connect to any servers."))
            return
        }

        sender.addChatMessage(ChatComponentText("Thump connection statuses:"))

        if (this.manager.allConnections.isEmpty()) {
            sender.addChatMessage(ChatComponentText(" There are no IRC connections available."))

            return
        }

        for (id in this.manager.allConnections) {
            val state = this.manager.getConnectionState(id)

            val statusMessage = ChatComponentText(" " + id + ": " + state.toString())

            if (state == ConnectionState.CONNECTED) {
                val channelsToJoin: Set<String> = Thump.configuration.servers.servers[id]?.channels?.keys ?: Sets.newHashSet()

                val joinedChannels = this.manager.getAllJoinedChannelsForConnection(id)
                val joinedChannelsMessage: ChatComponentText = if (channelsToJoin.isEmpty()) {
                    ChatComponentText(", no channels configured")
                } else {
                    val text = ChatComponentText(", channels: ")

                    val channelsOutput: MutableList<String> = Lists.newArrayList()

                    for (channel in channelsToJoin) {
                        if (joinedChannels.contains(channel)) {
                            channelsOutput.add(EnumChatFormatting.GREEN.toString() + channel + EnumChatFormatting.RESET.toString())
                        } else {
                            channelsOutput.add(EnumChatFormatting.RED.toString() + channel + EnumChatFormatting.RESET.toString())
                        }
                    }

                    text.appendText(Joiner.on(", ").join(channelsOutput))

                    text
                }

                statusMessage.appendSibling(joinedChannelsMessage)
            }

            sender.addChatMessage(statusMessage)
        }
    }

    override val usage: String
        get() = COMMAND_NAME + " " + COMMAND_USAGE

    override fun addTabCompletionOptions(sender: ICommandSender, parameters: Array<String>): List<String> {
        return Lists.newArrayList()
    }

    companion object {

        private val COMMAND_NAME = "status"
        private val COMMAND_USAGE = ""
    }
}