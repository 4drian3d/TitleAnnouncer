package net.dreamerzero.titleannouncer.velocity.commands.chat;

import java.util.List;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;

import net.dreamerzero.titleannouncer.common.utils.ComponentType;
import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.common.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.velocity.utils.SoundUtils;
import net.dreamerzero.titleannouncer.velocity.utils.VPlaceholders;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class AnnouncerChatCommand implements SimpleCommand {
    private final ProxyServer server;
    private final MiniMessage mm;
    private SoundUtils sUtils;
    private VPlaceholders vPlaceholders;
    public AnnouncerChatCommand(ProxyServer server, MiniMessage mm){
        this.server = server;
        this.mm = mm;
        this.sUtils = new SoundUtils(server);
        this.vPlaceholders = new VPlaceholders(server);
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource sender = invocation.source();
        String[] args = invocation.arguments();

        if(args.length == 0) {
            ConfigUtils.noChatArgumentProvided(sender);
            return;
        }

        String chattext = GeneralUtils.getCommandString(args);

        // Send to all
        server.sendMessage(mm.deserialize(
            MiniMessageUtil.replaceLegacy(
                chattext),
                sender instanceof Player player ?
                    vPlaceholders.replaceProxyPlaceholders(player) :
                    vPlaceholders.replaceProxyPlaceholders()));
        sUtils.playProxySound(ComponentType.CHAT);
        ConfigUtils.sendConfirmation(ComponentType.CHAT, sender);
    }

    @Override
    public List<String> suggest(final Invocation invocation) {
        return List.of("[message]");
    }

    @Override
    public boolean hasPermission(final Invocation invocation) {
        return invocation.source().hasPermission("titleannouncer.chat.global");
    }
}
