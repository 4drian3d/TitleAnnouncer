package net.dreamerzero.titleannouncer.paper.commands.title;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dreamerzero.titleannouncer.common.utils.ComponentType;
import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.common.utils.TitleUtil;
import net.dreamerzero.titleannouncer.paper.utils.PaperPlaceholders;

public class SendTitleCommand implements CommandExecutor {
    private PaperPlaceholders placeholders;
    public SendTitleCommand() {
        this.placeholders = new PaperPlaceholders();
    }

    //Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // The command requires arguments to work
        if(args.length == 0){
            ConfigUtils.sendNoArgumentMessage(sender);
            return true;
        } else if(args.length == 1){
            ConfigUtils.noTitlePlayerArgumentProvided(sender);
            return true;
        }

        // Concatenate the arguments provided by the command sent.
        String titleandsubtitle = GeneralUtils.getCommandString(args, 1);

        var serverplayers = Bukkit.getOnlinePlayers();
        Player playerObjetive = Bukkit.getPlayer(args[0]);

        if (!serverplayers.contains(playerObjetive)) {
            // Send an error message to the sender using the command.
            ConfigUtils.playerNotFoundMessage(sender);
            return false;
        }

        if(!titleandsubtitle.contains(";")){
            TitleUtil.sendOnlySubtitle(
                placeholders.applyPlaceholders(titleandsubtitle, playerObjetive),
                playerObjetive, 1000, 3000, 1000);
            ConfigUtils.sendConfirmation(ComponentType.TITLE, sender);
            ConfigUtils.playPaperSound(ComponentType.TITLE, playerObjetive);
            return true;
        }

        String[] titleandsubtitlefinal = TitleUtil.getTitleAndSubtitle(titleandsubtitle, sender);

        if(titleandsubtitlefinal == null) return false;

        // Send the title
        TitleUtil.sendTitle(
            placeholders.applyPlaceholders(titleandsubtitlefinal[0], playerObjetive),
            placeholders.applyPlaceholders(titleandsubtitlefinal[1], playerObjetive),
            playerObjetive, 1000, 3000, 1000);
        ConfigUtils.playPaperSound(ComponentType.TITLE, playerObjetive);
        ConfigUtils.sendConfirmation(ComponentType.TITLE, sender);
        return true;
    }
}
