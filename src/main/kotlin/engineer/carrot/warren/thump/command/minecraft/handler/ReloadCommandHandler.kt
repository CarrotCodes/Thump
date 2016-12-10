package engineer.carrot.warren.thump.command.minecraft.handler

import com.google.common.collect.Lists
import engineer.carrot.warren.thump.Thump
import engineer.carrot.warren.thump.api.ICommandHandler
import engineer.carrot.warren.thump.helper.LogHelper
import engineer.carrot.warren.thump.plugin.IThumpServicePlugins
import net.minecraft.command.ICommandSender
import net.minecraft.util.text.TextComponentString

class ReloadCommandHandler : ICommandHandler {

    override val command: String
        get() = COMMAND_NAME

    override fun processParameters(sender: ICommandSender, parameters: Array<String>) {
        LogHelper.info("Player '{}' triggered a reload (stopping and starting services - the server might lag for a few seconds)...", sender.name)
        sender.sendMessage(TextComponentString("Reloading Thump (stopping and starting services - the server might lag for a few seconds)..."))

        Thump.reloadConfiguration()

        sender.sendMessage(TextComponentString("Thump reloaded! Check services with /thump status"))
    }

    override val usage: String
        get() = COMMAND_NAME + " " + COMMAND_USAGE

    override fun addTabCompletionOptions(sender: ICommandSender, parameters: Array<String>): List<String> {
        return Lists.newArrayList()
    }

    companion object {

        private val COMMAND_NAME = "reload"
        private val COMMAND_USAGE = ""
    }
}
