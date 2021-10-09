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
import net.kyori.adventure.text.minimessage.MiniMessage;

public record SendActionbarCommand(MiniMessage mm) implements CommandExecutor {

    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 0) {
            ConfigUtils.noActionbarArgumentProvided(sender);
            return false;
        } else if (args.length < 2) {
            ConfigUtils.noActionbarPlayerArgumentProvided(sender);
            return false;
        }

        // Get the player
        Player playerObjetive = Bukkit.getPlayer(args[0]);

        //Collection of all players in the server
        var serverplayers = Bukkit.getOnlinePlayers();

        if (!serverplayers.contains(playerObjetive)) {
            // Send an error message to the sender using the command.
            ConfigUtils.playerNotFoundMessage(sender);
            return false;
        }

        // Concatenate the arguments provided by the command sent.
        String actionbartext = GeneralUtils.getCommandString(args, 1);

        playerObjetive.sendActionBar(
            mm.parse(
                MiniMessageUtil.replaceLegacy(
                    Announcer.placeholderAPIHook() ? PlaceholderAPI.setPlaceholders(playerObjetive, actionbartext) : actionbartext), 
                    PPlaceholders.replacePlaceholders(playerObjetive)));
        ConfigUtils.playPaperSound(ComponentType.ACTIONBAR, playerObjetive);
        ConfigUtils.sendConfirmation(ComponentType.ACTIONBAR, sender);
        return true;
    }
}
