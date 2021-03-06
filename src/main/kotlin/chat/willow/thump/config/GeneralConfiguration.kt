package chat.willow.thump.config

import net.minecraftforge.common.config.Configuration

class GeneralConfiguration(configuration: Configuration) {
    var obfuscateUserSourceFromMinecraft = true

    init {
        this.obfuscateUserSourceFromMinecraft = configuration.getBoolean(OBFUSCATE_USER_SOURCE_FROM_MINECRAFT, CATEGORY, this.obfuscateUserSourceFromMinecraft, OBFUSCATE_USER_SOURCE_FROM_MINECRAFT_COMMENT)
    }

    companion object {
        val CATEGORY = "general"
        private val OBFUSCATE_USER_SOURCE_FROM_MINECRAFT = "ObfuscateUserSourceFromMinecraft"
        private val OBFUSCATE_USER_SOURCE_FROM_MINECRAFT_COMMENT = "Inserts a zero-width character in to source usernames going from Minecraft to other services - commonly used to prevent unnecessary pings."
    }
}
