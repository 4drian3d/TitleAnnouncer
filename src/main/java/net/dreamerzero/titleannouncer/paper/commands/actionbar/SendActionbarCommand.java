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
import net.dreamerzero.titleannouncer.common.utils.PlaceholderUtil;

public class SendActionbarCommand implements CommandExecutor {
    public SendActionbarCommand() {}

    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ConfigUtils config = new ConfigUtils();
        if(args.length == 0) {
            config.noActionbarArgumentProvided(sender);
            return false;
        } else if (args.length < 2) {
            config.noActionbarPlayerArgumentProvided(sender);
            return false;
        }

        // Get the player
        Player playerObjetive = Bukkit.getPlayer(args[0]);

        //Collection of all players in the server
        var serverplayers = Bukkit.getOnlinePlayers();

        if (!serverplayers.contains(playerObjetive)) {
            // Send an error message to the sender using the command.
            config.playerNotFoundMessage(sender);
            return false;
        }

        // Concatenate the arguments provided by the command sent.
        String actionbartext = new GeneralUtils().getCommandString(args, 1);
        MiniMessageUtil mUtils = new MiniMessageUtil();

        playerObjetive.sendActionBar(
            mUtils.parse(
                mUtils.replaceLegacy(
                    PlaceholderUtil.placeholderAPIHook() ? PlaceholderAPI.setPlaceholders(playerObjetive, actionbartext) : actionbartext), 
                    PlaceholderUtil.replacePlaceholders(playerObjetive)));
        config.playPaperSound(ComponentType.ACTIONBAR, playerObjetive);
        config.sendConfirmation(ComponentType.ACTIONBAR, sender);
        return true;
    }
}
