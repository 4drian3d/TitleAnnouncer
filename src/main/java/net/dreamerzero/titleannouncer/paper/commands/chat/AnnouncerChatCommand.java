package net.dreamerzero.titleannouncer.paper.commands.chat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import net.dreamerzero.titleannouncer.common.utils.ComponentType;
import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.paper.utils.PaperPlaceholders;
import net.kyori.adventure.audience.Audience;

public class AnnouncerChatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {

            if(args.length == 0) {
                ConfigUtils.noChatArgumentProvided(sender);
                return false;
            }

            String chattext = GeneralUtils.getCommandString(args);

            Audience audience = Bukkit.getServer();

            var placeholders = new PaperPlaceholders();

            if (sender instanceof Player player) {
                audience.sendMessage(placeholders.applyPlaceholders(chattext, player));
                ConfigUtils.playPaperSound(ComponentType.CHAT, audience);
                ConfigUtils.sendConfirmation(ComponentType.CHAT, sender);
            } else {
                audience.sendMessage(placeholders.applyPlaceholders(chattext));
                ConfigUtils.playPaperSound(ComponentType.CHAT, audience);
                ConfigUtils.sendConfirmation(ComponentType.CHAT, sender);
            }
            return true;
    }
}
