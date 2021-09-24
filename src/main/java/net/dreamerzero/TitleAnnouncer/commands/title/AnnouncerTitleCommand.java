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
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.util.TriState;

public class AnnouncerTitleCommand implements CommandExecutor {
    public AnnouncerTitleCommand() {}

    //The audience that will receive the title will be all the players on the server.
    Audience audience = Bukkit.getServer();

    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Permission Check
        if (sender.permissionValue("announcer.title.global") != TriState.TRUE) {
            ConfigUtils.sendNoTitlePermission(sender);
            return true;
        }

        if(args.length == 0) {
            ConfigUtils.sendNoArgumentMessage(sender);
            return true;
        }

        // Concatenate the arguments provided by the command sent.
        String titleandsubtitle = GeneralUtils.getCommandString(args);

        if(!titleandsubtitle.contains(";")){
            if(PlaceholderUtil.placeholderAPIHook()){
                if(sender instanceof Player player){
                    TitleUtil.sendOnlyTitle(
                        MiniMessageUtil.parse(
                        MiniMessageUtil.replaceLegacy(
                            PlaceholderAPI.setPlaceholders(player, titleandsubtitle)), replacePlaceholders(player)),
                            audience, 1000, 3000, 1000);
                    ConfigUtils.sendTitleConfirmation(sender);
                    ConfigUtils.playTitleSound(audience);
                    return true;
                } else {
                    TitleUtil.sendOnlyTitle(
                        MiniMessageUtil.parse(
                        MiniMessageUtil.replaceLegacy(
                            PlaceholderAPI.setPlaceholders(null, titleandsubtitle)), replacePlaceholders()),
                            audience, 1000, 3000, 1000);
                    ConfigUtils.sendTitleConfirmation(sender);
                    ConfigUtils.playTitleSound(audience);
                    return true;
                }
            } else {
                if(sender instanceof Player player){
                    TitleUtil.sendOnlyTitle(
                        MiniMessageUtil.parse(
                        MiniMessageUtil.replaceLegacy(
                            titleandsubtitle), replacePlaceholders(player)),
                            audience, 1000, 3000, 1000);
                    ConfigUtils.sendTitleConfirmation(sender);
                    ConfigUtils.playTitleSound(audience);
                    return true;
                } else {
                    TitleUtil.sendOnlyTitle(
                        MiniMessageUtil.parse(
                        MiniMessageUtil.replaceLegacy(
                            titleandsubtitle), replacePlaceholders()),
                            audience, 1000, 3000, 1000);
                    ConfigUtils.sendTitleConfirmation(sender);
                    ConfigUtils.playTitleSound(audience);
                    return true;
                }
            }
        }

        String titleandsubtitlefinal[];

        if(TitleUtil.getTitleAndSubtitle(titleandsubtitle, sender) == null) {
            return false;
        } else {
            titleandsubtitlefinal = TitleUtil.getTitleAndSubtitle(titleandsubtitle, sender);
        }

        String title;
        String subtitle;

        if(PlaceholderUtil.placeholderAPIHook()){
            if (sender instanceof Player player) {
                title = MiniMessageUtil.replaceLegacy(PlaceholderAPI.setPlaceholders(player, titleandsubtitlefinal[0]));
                subtitle = MiniMessageUtil.replaceLegacy(PlaceholderAPI.setPlaceholders(player, titleandsubtitlefinal[1]));
                // Send the title
                TitleUtil.sendTitle(
                    MiniMessageUtil.parse(title, replacePlaceholders(player)), 
                    MiniMessageUtil.parse(subtitle, replacePlaceholders(player)),
                    audience,
                    1000,
                    3000,
                    1000);
                ConfigUtils.playTitleSound(audience);
                ConfigUtils.sendTitleConfirmation(sender);
                return true;
            } else {
                title = MiniMessageUtil.replaceLegacy(PlaceholderAPI.setPlaceholders(null, titleandsubtitlefinal[0]));
                subtitle = MiniMessageUtil.replaceLegacy(PlaceholderAPI.setPlaceholders(null, titleandsubtitlefinal[1]));
                // Send the title
                TitleUtil.sendTitle(
                    MiniMessageUtil.parse(title, replacePlaceholders()), 
                    MiniMessageUtil.parse(subtitle, replacePlaceholders()),
                    audience,
                    1000,
                    3000,
                    1000);
                ConfigUtils.playTitleSound(audience);
                ConfigUtils.sendTitleConfirmation(sender);
                return true;
            }
        } else {
            if (sender instanceof Player player) {
                // Send the title
                TitleUtil.sendTitle(
                    MiniMessageUtil.parse(titleandsubtitlefinal[0], replacePlaceholders(player)), 
                    MiniMessageUtil.parse(titleandsubtitlefinal[1], replacePlaceholders(player)),
                    audience,
                    1000,
                    3000,
                    1000);
                ConfigUtils.playTitleSound(audience);
                ConfigUtils.sendTitleConfirmation(sender);
                return true;
            } else {
                // Send the title
                TitleUtil.sendTitle(
                    MiniMessageUtil.parse(titleandsubtitlefinal[0], replacePlaceholders()), 
                    MiniMessageUtil.parse(titleandsubtitlefinal[1], replacePlaceholders()),
                    audience,
                    1000,
                    3000,
                    1000);
                ConfigUtils.playTitleSound(audience);
                ConfigUtils.sendTitleConfirmation(sender);
                return true;
            }
        }
    }
}
