package net.dreamerzero.titleannouncer.paper.commands.title;

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
import net.dreamerzero.titleannouncer.common.utils.TitleUtil;

public class SendTitleCommand implements CommandExecutor {
    public SendTitleCommand() {}

    //Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // The command requires arguments to work
        switch (args.length) {
            case 0 -> {
                ConfigUtils.sendNoArgumentMessage(sender);
                return true;
            }
            case 1 -> {
                ConfigUtils.noTitlePlayerArgumentProvided(sender);
                return true;
            }
        }

        boolean placeholderAPISupport = PlaceholderUtil.placeholderAPIHook();

        // Concatenate the arguments provided by the command sent.
        String titleandsubtitle = GeneralUtils.getCommandString(args, 1);

        var serverplayers = Bukkit.getOnlinePlayers();
        Player playerObjetive = Bukkit.getPlayer(args[0]);

        if(!titleandsubtitle.contains(";")){
            TitleUtil.sendOnlySubtitle(
                MiniMessageUtil.parse(
                MiniMessageUtil.replaceLegacy(
                    placeholderAPISupport ? PlaceholderAPI.setPlaceholders(playerObjetive, titleandsubtitle) : titleandsubtitle), 
                    PlaceholderUtil.replacePlaceholders(playerObjetive)),
                    playerObjetive, 1000, 3000, 1000);
            ConfigUtils.sendConfirmation(ComponentType.TITLE, sender);
            ConfigUtils.playPaperSound(ComponentType.TITLE, playerObjetive);
            return true;
        }

        String titleandsubtitlefinal[] = TitleUtil.getTitleAndSubtitle(titleandsubtitle, sender);

        if(titleandsubtitlefinal == null) return false;

        if (!serverplayers.contains(playerObjetive)) {
            // Send an error message to the sender using the command.
            ConfigUtils.playerNotFoundMessage(sender);
            return false;
        }

        // Send the title
        TitleUtil.sendTitle(
            MiniMessageUtil.parse(placeholderAPISupport ? MiniMessageUtil.replaceLegacy(
                PlaceholderAPI.setPlaceholders(playerObjetive, titleandsubtitlefinal[0])) : titleandsubtitlefinal[0], 
                PlaceholderUtil.replacePlaceholders(playerObjetive)),
            MiniMessageUtil.parse(placeholderAPISupport ? MiniMessageUtil.replaceLegacy(
                PlaceholderAPI.setPlaceholders(playerObjetive, titleandsubtitlefinal[1])) : titleandsubtitlefinal[1], 
                PlaceholderUtil.replacePlaceholders(playerObjetive)),
            playerObjetive, 1000, 3000, 1000);
        ConfigUtils.playPaperSound(ComponentType.TITLE, playerObjetive);
        ConfigUtils.sendConfirmation(ComponentType.TITLE, sender);
        return true;
    }
}
