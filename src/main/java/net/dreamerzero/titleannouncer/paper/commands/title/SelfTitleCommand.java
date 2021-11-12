package net.dreamerzero.titleannouncer.paper.commands.title;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dreamerzero.titleannouncer.common.utils.ComponentType;
import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.common.utils.TitleUtil;
import net.dreamerzero.titleannouncer.paper.utils.PaperPlaceholders;

public class SelfTitleCommand implements CommandExecutor {
    private PaperPlaceholders placeholders;
    public SelfTitleCommand() {
        this.placeholders = new PaperPlaceholders();
    }

    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // It will send an title to the one who executes the command,
        // it makes no sense for the console to execute it.
        if (!(sender instanceof Player player)) {
            ConfigUtils.onlyPlayerExecute(sender);
            return false;
        }

        // The command requires arguments to work
        if(args.length == 0){
            ConfigUtils.sendNoArgumentMessage(sender);
            return true;
        }

        // Concatenate the arguments provided by the command sent.
        String titleandsubtitle = GeneralUtils.getCommandString(args);

        if(!titleandsubtitle.contains(";")){
            TitleUtil.sendOnlySubtitle(
                placeholders.applyPlaceholders(titleandsubtitle, player),
                sender, 1000, 3000, 1000);
            ConfigUtils.sendConfirmation(ComponentType.TITLE, sender);
            ConfigUtils.playPaperSound(ComponentType.TITLE, sender);
            return true;
        }

        String[] titleandsubtitlefinal = TitleUtil.getTitleAndSubtitle(titleandsubtitle, sender);

        if(titleandsubtitlefinal == null) return false;

        // Send the Title
        TitleUtil.sendTitle(
            placeholders.applyPlaceholders(titleandsubtitlefinal[0], player),
            placeholders.applyPlaceholders(titleandsubtitlefinal[1], player),
            sender,
            1000,
            3000,
            1000);
        ConfigUtils.sendConfirmation(ComponentType.TITLE, sender);
        ConfigUtils.playPaperSound(ComponentType.TITLE, sender);
        return true;
    }
}
