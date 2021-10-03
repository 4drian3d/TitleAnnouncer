package net.dreamerzero.titleannouncer.paper.commands.title;

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
import net.dreamerzero.titleannouncer.common.utils.TitleUtil;

/*
This command will be executed as a test of the "/anunciarevento" command.
It will only be sent for the same player.
*/
public class SelfTitleCommand implements CommandExecutor {
    public SelfTitleCommand() {}

    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ConfigUtils config = new ConfigUtils();
        // It will send an title to the one who executes the command,
        // it makes no sense for the console to execute it.
        if (!(sender instanceof Player player)) {
            config.onlyPlayerExecute(sender);
            return false;
        }

        // The command requires arguments to work
        if(args.length == 0){
            config.sendNoArgumentMessage(sender);
            return true;
        }

        boolean placeholderAPISupport = PlaceholderUtil.placeholderAPIHook();

        // Concatenate the arguments provided by the command sent.
        String titleandsubtitle = new GeneralUtils().getCommandString(args);

        TitleUtil tUtil = new TitleUtil();
        MiniMessageUtil mUtils = new MiniMessageUtil();

        if(!titleandsubtitle.contains(";")){
            tUtil.sendOnlySubtitle(
                mUtils.parse(mUtils.replaceLegacy(
                    placeholderAPISupport ? PlaceholderAPI.setPlaceholders(player, titleandsubtitle) : titleandsubtitle),
                    PlaceholderUtil.replacePlaceholders(player)),
                    sender, 1000, 3000, 1000);
            config.sendConfirmation(ComponentType.TITLE, sender);
            config.playPaperSound(ComponentType.TITLE, sender);
            return true;
        }

        String titleandsubtitlefinal[] = tUtil.getTitleAndSubtitle(titleandsubtitle, sender);

        if(titleandsubtitlefinal == null) return false;

        // Send the Title
        tUtil.sendTitle(
            mUtils.parse(mUtils.replaceLegacy(placeholderAPISupport ? 
                PlaceholderAPI.setPlaceholders(player, titleandsubtitlefinal[0]) : titleandsubtitlefinal[0]), 
            PlaceholderUtil.replacePlaceholders(player)),
            mUtils.parse(mUtils.replaceLegacy(placeholderAPISupport ? 
                PlaceholderAPI.setPlaceholders(player, titleandsubtitlefinal[1]) : titleandsubtitlefinal[1]), 
            PlaceholderUtil.replacePlaceholders(player)),
            sender,
            1000,
            3000,
            1000);
        config.sendConfirmation(ComponentType.TITLE, sender);
        config.playPaperSound(ComponentType.TITLE, sender);
        return true;
    }
}
