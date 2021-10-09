package net.dreamerzero.titleannouncer.paper.commands.actionbar;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dreamerzero.titleannouncer.common.utils.ComponentType;
import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.common.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.paper.Announcer;
import net.dreamerzero.titleannouncer.paper.utils.PPlaceholders;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.MiniMessage;

public record AnnouncerActionbarCommand(MiniMessage mm) implements CommandExecutor {

    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 0) {
            ConfigUtils.noActionbarArgumentProvided(sender);
            return false;
        }

        boolean placeholderAPISupport = Announcer.placeholderAPIHook();

        // Concatenate the arguments provided by the command sent.
        String actionbartext = GeneralUtils.getCommandString(args);
        // The audience that will receive the actionbar will be all the players on the server.
        Audience audience = Bukkit.getServer();

        // Send to all
        if (sender instanceof Player player) {
            audience.sendActionBar(mm.parse(
                MiniMessageUtil.replaceLegacy(
                    placeholderAPISupport ? PlaceholderAPI.setPlaceholders(player, actionbartext) : actionbartext),
                    PPlaceholders.replacePlaceholders(player)));
            ConfigUtils.playPaperSound(ComponentType.ACTIONBAR, audience);
            ConfigUtils.sendConfirmation(ComponentType.ACTIONBAR, sender);
            return true;
        } else {
            audience.sendActionBar(mm.parse(
                MiniMessageUtil.replaceLegacy(
                    placeholderAPISupport ? PlaceholderAPI.setPlaceholders(null, actionbartext) : actionbartext),
                    PPlaceholders.replacePlaceholders()));
            ConfigUtils.playPaperSound(ComponentType.ACTIONBAR, audience);
            ConfigUtils.sendConfirmation(ComponentType.ACTIONBAR, sender);
            return true;
        }
    }
}
