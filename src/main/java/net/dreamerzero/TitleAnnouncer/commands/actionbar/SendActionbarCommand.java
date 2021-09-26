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
import net.kyori.adventure.util.TriState;

public class SendActionbarCommand implements CommandExecutor {
    public SendActionbarCommand() {}

    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Permission Check
        if (sender.permissionValue("announcer.actionbar.send") != TriState.TRUE) {
            ConfigUtils.sendNoActionbarPermission(sender);
            return true;
        }

        if (args.length < 2) {
            ConfigUtils.noActionbarPlayerArgumentProvided(sender);
            return false;
        }

        // Get the player
        Player playerObjetive = Bukkit.getPlayer(args[0]);

        //Collection of all players in the server
        var serverplayers = Bukkit.getOnlinePlayers();

        if (!serverplayers.contains(playerObjetive)) {
            // Send an error message to the sender using the command.
            ConfigUtils.actionbarPlayerNotFoundMessage(sender);
            return false;
        }

        // Concatenate the arguments provided by the command sent.
        String actionbartext = GeneralUtils.getCommandString(args, 1);

        playerObjetive.sendActionBar(
            MiniMessageUtil.parse(
                MiniMessageUtil.replaceLegacy(
                    PlaceholderUtil.placeholderAPIHook() ? PlaceholderAPI.setPlaceholders(playerObjetive, actionbartext) : actionbartext), 
                    PlaceholderUtil.replacePlaceholders(playerObjetive)));
        ConfigUtils.playActionbarSound(playerObjetive);
        ConfigUtils.sendActionbarConfirmation(sender);
        return true;
    }
}
