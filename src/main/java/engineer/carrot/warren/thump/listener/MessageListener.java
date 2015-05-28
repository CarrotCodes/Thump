package engineer.carrot.warren.thump.listener;

import com.google.common.eventbus.Subscribe;
import engineer.carrot.warren.thump.connection.ConnectionManager;
import engineer.carrot.warren.thump.util.helper.LogHelper;
import engineer.carrot.warren.thump.util.helper.PlayerHelper;
import engineer.carrot.warren.warren.event.ChannelMessageEvent;
import engineer.carrot.warren.warren.event.PrivateMessageEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

import java.util.List;

public class MessageListener {
    private ConnectionManager manager;

    public MessageListener(ConnectionManager manager) {
        this.manager = manager;
    }

    @Subscribe
    public void handleChannelMessage(ChannelMessageEvent event) {
        String user = event.fromUser.getNameWithoutAccess();

        if (manager.usernameMatchesAnyConnection(user)) {
            return;
        }

        String output = event.channel.toString() + ": <" + user + "> " + event.contents;
        LogHelper.info(output);

        PlayerHelper.sendMessageToAllPlayers(output);
    }

    @Subscribe
    public void handlePrivateMessage(PrivateMessageEvent event) {
        LogHelper.info("PM from {}: {}", event.fromUser, event.contents);
    }
}
