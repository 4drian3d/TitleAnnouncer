package net.dreamerzero.titleannouncer.commands.title;

import static net.dreamerzero.titleannouncer.utils.PlaceholderUtil.replacePlaceholders;

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

        // Concatenate the arguments provided by the command sent.
        var titleandsubtitle = GeneralUtils.getCommandString(args, 1);

        var serverplayers = Bukkit.getOnlinePlayers();
        Player playerObjetive = Bukkit.getPlayer(args[0]);

        if(!titleandsubtitle.contains(";")){
            if(PlaceholderUtil.placeholderAPIHook()){
                if(sender instanceof Player player){
                    TitleUtil.sendOnlyTitle(
                        MiniMessageUtil.parse(
                        MiniMessageUtil.replaceLegacy(
                            PlaceholderAPI.setPlaceholders(player, titleandsubtitle)), replacePlaceholders(player, playerObjetive)),
                            playerObjetive, 1000, 3000, 1000);
                    ConfigUtils.sendTitleConfirmation(sender);
                    ConfigUtils.playTitleSound(playerObjetive);
                    return true;
                } else {
                    TitleUtil.sendOnlyTitle(
                        MiniMessageUtil.parse(
                        MiniMessageUtil.replaceLegacy(
                            PlaceholderAPI.setPlaceholders(playerObjetive, titleandsubtitle)), replacePlaceholders(playerObjetive)),
                            playerObjetive, 1000, 3000, 1000);
                    ConfigUtils.sendTitleConfirmation(sender);
                    ConfigUtils.playTitleSound(playerObjetive);
                    return true;
                }
            } else {
                if(sender instanceof Player player){
                    TitleUtil.sendOnlyTitle(
                        MiniMessageUtil.parse(
                        MiniMessageUtil.replaceLegacy(
                            titleandsubtitle), replacePlaceholders(player, playerObjetive)),
                            playerObjetive, 1000, 3000, 1000);
                    ConfigUtils.sendTitleConfirmation(sender);
                    ConfigUtils.playTitleSound(playerObjetive);
                    return true;
                } else {
                    TitleUtil.sendOnlyTitle(
                        MiniMessageUtil.parse(
                        MiniMessageUtil.replaceLegacy(
                            titleandsubtitle), replacePlaceholders(playerObjetive)),
                            playerObjetive, 1000, 3000, 1000);
                    ConfigUtils.sendTitleConfirmation(sender);
                    ConfigUtils.playTitleSound(playerObjetive);
                    return true;
                }
            }
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
        String title;
        String subtitle;

        if(PlaceholderUtil.placeholderAPIHook()){
            if (sender instanceof Player player) {
                title = MiniMessageUtil.replaceLegacy(PlaceholderAPI.setPlaceholders(player, titleandsubtitlefinal[0]));
                subtitle = MiniMessageUtil.replaceLegacy(PlaceholderAPI.setPlaceholders(player, titleandsubtitlefinal[1]));
                // Send the title
                TitleUtil.sendTitle(
                    MiniMessageUtil.parse(title, replacePlaceholders(player, playerObjetive)),
                    MiniMessageUtil.parse(subtitle, replacePlaceholders(player, playerObjetive)),
                    playerObjetive,
                    1000,
                    3000,
                    1000);
                ConfigUtils.playTitleSound(playerObjetive);
                ConfigUtils.sendTitleConfirmation(sender);
                return true;
            } else {
                title = MiniMessageUtil.replaceLegacy(PlaceholderAPI.setPlaceholders(null, titleandsubtitlefinal[0]));
                subtitle = MiniMessageUtil.replaceLegacy(PlaceholderAPI.setPlaceholders(null, titleandsubtitlefinal[1]));
                TitleUtil.sendTitle(
                    MiniMessageUtil.parse(title, replacePlaceholders(playerObjetive)),
                    MiniMessageUtil.parse(subtitle, replacePlaceholders(playerObjetive)),
                    playerObjetive,
                    1000,
                    3000,
                    1000);
                ConfigUtils.playTitleSound(playerObjetive);
                ConfigUtils.sendTitleConfirmation(sender);
                return true;
            }
        } else {
            if (sender instanceof Player player) {
                // Send the title
                TitleUtil.sendTitle(
                    MiniMessageUtil.parse(titleandsubtitlefinal[0], replacePlaceholders(player, playerObjetive)),
                    MiniMessageUtil.parse(titleandsubtitlefinal[1], replacePlaceholders(player, playerObjetive)),
                    playerObjetive,
                    1000,
                    3000,
                    1000);
                ConfigUtils.playTitleSound(playerObjetive);
                ConfigUtils.sendTitleConfirmation(sender);
                return true;
            } else {
                TitleUtil.sendTitle(
                    MiniMessageUtil.parse(titleandsubtitlefinal[0], replacePlaceholders(playerObjetive)),
                    MiniMessageUtil.parse(titleandsubtitlefinal[1], replacePlaceholders(playerObjetive)),
                    playerObjetive,
                    1000,
                    3000,
                    1000);
                ConfigUtils.playTitleSound(playerObjetive);
                ConfigUtils.sendTitleConfirmation(sender);
                return true;
            }
        }
    }
}