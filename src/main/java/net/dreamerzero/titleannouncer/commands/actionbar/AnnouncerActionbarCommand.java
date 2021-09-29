package net.dreamerzero.titleannouncer.commands.actionbar;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dreamerzero.titleannouncer.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.utils.PlaceholderUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.util.TriState;

public class AnnouncerActionbarCommand implements CommandExecutor {
    public AnnouncerActionbarCommand() {}

    // The audience that will receive the actionbar will be all the players on the server.
    Audience audience = Bukkit.getServer();

    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Permission Check
        if (sender.permissionValue("announcer.actionbar.global") != TriState.TRUE) {
            ConfigUtils.sendNoActionbarPermission(sender);
            return true;
        }

        boolean placeholderAPISupport = PlaceholderUtil.placeholderAPIHook();

        // Concatenate the arguments provided by the command sent.
        String actionbartext = GeneralUtils.getCommandString(args);

        // Send to all
        if (sender instanceof Player player) {
            audience.sendActionBar(MiniMessageUtil.parse(
                MiniMessageUtil.replaceLegacy(
                    placeholderAPISupport ? PlaceholderAPI.setPlaceholders(player, actionbartext) : actionbartext),
                    PlaceholderUtil.replacePlaceholders(player)));
            ConfigUtils.playActionbarSound(audience);
            ConfigUtils.sendActionbarConfirmation(sender);
            return true;
        } else {
            audience.sendActionBar(MiniMessageUtil.parse(
                MiniMessageUtil.replaceLegacy(
                    placeholderAPISupport ? PlaceholderAPI.setPlaceholders(null, actionbartext) : actionbartext),
                    PlaceholderUtil.replacePlaceholders()));
            ConfigUtils.playActionbarSound(audience);
            ConfigUtils.sendActionbarConfirmation(sender);
            return true;
        }
    }
}
