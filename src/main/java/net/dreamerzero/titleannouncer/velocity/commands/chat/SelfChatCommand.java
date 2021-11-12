package net.dreamerzero.titleannouncer.velocity.commands.chat;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;

import net.dreamerzero.titleannouncer.common.utils.ComponentType;
import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.velocity.utils.SoundUtils;
import net.dreamerzero.titleannouncer.velocity.utils.VelocityPlaceholders;

public class SelfChatCommand implements SimpleCommand {
    private SoundUtils sUtils;
    private VelocityPlaceholders vPlaceholders;
    public SelfChatCommand(ProxyServer server){
        this.sUtils = new SoundUtils(server);
        this.vPlaceholders = new VelocityPlaceholders(server);
    }
    @Override
    public void execute(Invocation invocation) {
        CommandSource sender = invocation.source();
        String[] args = invocation.arguments();

        if(!(sender instanceof Player player)){
            ConfigUtils.onlyPlayerExecute(sender);
            return;
        }
        if(args.length == 0) {
            ConfigUtils.noChatArgumentProvided(sender);
            return;
        }

        String chattext = GeneralUtils.getCommandString(args);

        sender.sendMessage(vPlaceholders.applyPlaceholders(chattext, player));
        sUtils.playProxySound(player, ComponentType.CHAT);
        ConfigUtils.sendConfirmation(ComponentType.CHAT, sender);
    }

    @Override
    public CompletableFuture<List<String>> suggestAsync(final Invocation invocation) {
        return CompletableFuture.supplyAsync(()->List.of("[message]"));
    }

    @Override
    public boolean hasPermission(final Invocation invocation) {
        return invocation.source().hasPermission("titleannouncer.chat.self");
    }
}
