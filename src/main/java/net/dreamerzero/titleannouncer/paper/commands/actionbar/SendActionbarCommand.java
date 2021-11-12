package net.dreamerzero.titleannouncer.paper.commands.actionbar;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dreamerzero.titleannouncer.common.utils.ComponentType;
import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.paper.utils.PaperPlaceholders;

public class SendActionbarCommand implements CommandExecutor {
    private PaperPlaceholders placeholders;
    public SendActionbarCommand(){
        this.placeholders = new PaperPlaceholders();
    }

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

        playerObjetive.sendActionBar(placeholders.applyPlaceholders(actionbartext, playerObjetive));
        ConfigUtils.playPaperSound(ComponentType.ACTIONBAR, playerObjetive);
        ConfigUtils.sendConfirmation(ComponentType.ACTIONBAR, sender);
        return true;
    }
}
