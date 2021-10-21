package net.dreamerzero.titleannouncer.paper.commands.chat;

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
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class AnnouncerChatCommand implements CommandExecutor {
    private MiniMessage mm;
    public AnnouncerChatCommand(MiniMessage mm){
        this.mm = mm;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {

            if(args.length == 0) {
                ConfigUtils.noChatArgumentProvided(sender);
                return false;
            }

            boolean placeholderAPISupport = Announcer.placeholderAPIHook();

            String chattext = GeneralUtils.getCommandString(args);

            Audience audience = Bukkit.getServer();

            if (sender instanceof Player player) {
                audience.sendMessage(mm.deserialize(
                    MiniMessageUtil.replaceLegacy(
                        placeholderAPISupport ? PlaceholderAPI.setPlaceholders(player, chattext) : chattext),
                        PPlaceholders.replacePlaceholders(player)));
                ConfigUtils.playPaperSound(ComponentType.CHAT, audience);
                ConfigUtils.sendConfirmation(ComponentType.CHAT, sender);
            } else {
                audience.sendMessage(mm.deserialize(
                    MiniMessageUtil.replaceLegacy(
                        placeholderAPISupport ? PlaceholderAPI.setPlaceholders(null, chattext) : chattext),
                        PPlaceholders.replacePlaceholders()));
                ConfigUtils.playPaperSound(ComponentType.CHAT, audience);
                ConfigUtils.sendConfirmation(ComponentType.CHAT, sender);
            }
            return true;
    }
}
