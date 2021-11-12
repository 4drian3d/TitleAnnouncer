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
import net.kyori.adventure.audience.Audience;

public class AnnouncerTitleCommand implements CommandExecutor {

    //The audience that will receive the title will be all the players on the server.
    Audience audience = Bukkit.getServer();

    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 0) {
            ConfigUtils.sendNoArgumentMessage(sender);
            return true;
        }

        // Concatenate the arguments provided by the command sent.
        String titleandsubtitle = GeneralUtils.getCommandString(args);

        var placeholders = new PaperPlaceholders();

        if(!TitleUtil.containsComma(args)){
            if(sender instanceof Player player){
                TitleUtil.sendOnlySubtitle(
                    placeholders.applyPlaceholders(titleandsubtitle, player),
                    audience, 1000, 3000, 1000);
                ConfigUtils.sendConfirmation(ComponentType.TITLE, sender);
                ConfigUtils.playPaperSound(ComponentType.TITLE, audience);
                return true;
            } else {
                TitleUtil.sendOnlySubtitle(
                    placeholders.applyPlaceholders(titleandsubtitle),
                    audience, 1000, 3000, 1000);
                ConfigUtils.sendConfirmation(ComponentType.TITLE, sender);
                ConfigUtils.playPaperSound(ComponentType.TITLE, audience);
                return true;
            }
        }

        String[] titleandsubtitlefinal = TitleUtil.getTitleAndSubtitle(titleandsubtitle, sender);

        if(titleandsubtitlefinal == null) return false;

        if (sender instanceof Player player) {
            // Send the title
            TitleUtil.sendTitle(
                placeholders.applyPlaceholders(titleandsubtitlefinal[0], player),
                placeholders.applyPlaceholders(titleandsubtitlefinal[1], player),
                audience,
                1000,
                3000,
                1000);
            ConfigUtils.playPaperSound(ComponentType.TITLE, audience);
            ConfigUtils.sendConfirmation(ComponentType.TITLE, sender);
            return true;
        } else {
            // Send the title
            TitleUtil.sendTitle(
                placeholders.applyPlaceholders(titleandsubtitlefinal[0]),
                placeholders.applyPlaceholders(titleandsubtitlefinal[1]),
                audience,
                1000,
                3000,
                1000);
            ConfigUtils.playPaperSound(ComponentType.TITLE, audience);
            ConfigUtils.sendConfirmation(ComponentType.TITLE, sender);
            return true;
        }
    }
}
