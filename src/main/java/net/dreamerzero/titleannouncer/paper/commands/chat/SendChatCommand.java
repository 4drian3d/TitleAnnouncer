package net.dreamerzero.titleannouncer.paper.commands.chat;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dreamerzero.titleannouncer.common.utils.ComponentType;
import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.common.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.paper.Announcer;
import net.dreamerzero.titleannouncer.paper.utils.PPlaceholders;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class SendChatCommand implements CommandExecutor {
    private final MiniMessage mm;
    public SendChatCommand(MiniMessage mm){
        this.mm = mm;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
            if(args.length == 0) {
                ConfigUtils.noChatArgumentProvided(sender);
                return false;
            } else if (args.length < 2) {
                ConfigUtils.noChatPlayerArgumentProvided(sender);
                return false;
            }

            // Get the player
            Player playerObjetive = Bukkit.getPlayer(args[0]);

            //Collection of all players in the server
            Collection<? extends Player> serverplayers = Bukkit.getOnlinePlayers();

            if (!serverplayers.contains(playerObjetive)) {
                ConfigUtils.playerNotFoundMessage(sender);
                return false;
            }

            String chattext = GeneralUtils.getCommandString(args, 1);

            playerObjetive.sendMessage(
                mm.deserialize(
                    MiniMessageUtil.replaceLegacy(
                        Announcer.placeholderAPIHook() ? PlaceholderAPI.setPlaceholders(playerObjetive, chattext) : chattext),
                        PPlaceholders.replacePlaceholders(playerObjetive)));
            ConfigUtils.playPaperSound(ComponentType.CHAT, playerObjetive);
            ConfigUtils.sendConfirmation(ComponentType.CHAT, sender);
            return true;
    }
}
