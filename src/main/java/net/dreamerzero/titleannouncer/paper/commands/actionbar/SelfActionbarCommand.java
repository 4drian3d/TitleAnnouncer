package net.dreamerzero.titleannouncer.paper.commands.actionbar;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.common.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.common.utils.PlaceholderUtil;
import net.kyori.adventure.util.TriState;

public class SelfActionbarCommand implements CommandExecutor {
    public SelfActionbarCommand() {}

    //Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // It will send an actionbar to the one who executes the command,
        // it makes no sense for the console to execute it.
        if (!(sender instanceof Player player)) {
            ConfigUtils.onlyPlayerExecute(sender);
            return false;
        }

        // Permission Check
        if (sender.permissionValue("titleannouncer.actionbar.self") != TriState.TRUE) {
            ConfigUtils.sendNoMainPermission(sender);
            return true;
        }

        if(args.length == 0) {
            ConfigUtils.noActionbarArgumentProvided(sender);
            return false;
        }

        // Concatenate the arguments provided by the command sent.
        String actionbartext = GeneralUtils.getCommandString(args);

        // Send to sender
        sender.sendActionBar(
            MiniMessageUtil.parse(
                MiniMessageUtil.replaceLegacy(
                    PlaceholderUtil.placeholderAPIHook() ? PlaceholderAPI.setPlaceholders(player, actionbartext) : actionbartext), 
                    PlaceholderUtil.replacePlaceholders(player)));
        ConfigUtils.sendActionbarConfirmation(sender);
        ConfigUtils.playActionbarSound(sender);
    return true;
    }
}
