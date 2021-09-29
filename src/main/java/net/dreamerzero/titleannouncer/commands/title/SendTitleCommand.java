package net.dreamerzero.titleannouncer.commands.title;

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
import net.dreamerzero.titleannouncer.utils.TitleUtil;
import net.kyori.adventure.util.TriState;

public class SendTitleCommand implements CommandExecutor {
    public SendTitleCommand() {}

    //Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Permission Check
        if (sender.permissionValue("announcer.title.send") != TriState.TRUE) {
            ConfigUtils.sendNoTitlePermission(sender);
            return true;
        }

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
            TitleUtil.sendOnlyTitle(
                MiniMessageUtil.parse(
                MiniMessageUtil.replaceLegacy(
                    placeholderAPISupport ? PlaceholderAPI.setPlaceholders(playerObjetive, titleandsubtitle) : titleandsubtitle), 
                    PlaceholderUtil.replacePlaceholders(playerObjetive)),
                    playerObjetive, 1000, 3000, 1000);
            ConfigUtils.sendTitleConfirmation(sender);
            ConfigUtils.playTitleSound(playerObjetive);
            return true;
        }

        String titleandsubtitlefinal[];

        if(TitleUtil.getTitleAndSubtitle(titleandsubtitle, sender) == null){
            return false;
        } else {
            titleandsubtitlefinal = TitleUtil.getTitleAndSubtitle(titleandsubtitle, sender);
        }

        if (!serverplayers.contains(playerObjetive)) {
            // Send an error message to the sender using the command.
            ConfigUtils.titlePlayerNotFoundMessage(sender);
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
        ConfigUtils.playTitleSound(playerObjetive);
        ConfigUtils.sendTitleConfirmation(sender);
        return true;
    }
}
