package net.dreamerzero.titleannouncer.paper.commands.title;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.common.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.common.utils.PlaceholderUtil;
import net.dreamerzero.titleannouncer.common.utils.TitleUtil;
import net.kyori.adventure.audience.Audience;

public class AnnouncerTitleCommand implements CommandExecutor {
    public AnnouncerTitleCommand() {}

    //The audience that will receive the title will be all the players on the server.
    Audience audience = Bukkit.getServer();

    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 0) {
            ConfigUtils.sendNoArgumentMessage(sender);
            return true;
        }

        boolean placeholderAPISupport = PlaceholderUtil.placeholderAPIHook();

        // Concatenate the arguments provided by the command sent.
        String titleandsubtitle = GeneralUtils.getCommandString(args);

        if(!TitleUtil.containsComma(args)){
            if(sender instanceof Player player){
                TitleUtil.sendOnlySubtitle(
                    MiniMessageUtil.parse(MiniMessageUtil.replaceLegacy(
                        placeholderAPISupport ? PlaceholderAPI.setPlaceholders(player, titleandsubtitle) : titleandsubtitle), 
                        PlaceholderUtil.replacePlaceholders(player)),
                        audience, 1000, 3000, 1000);
                ConfigUtils.sendTitleConfirmation(sender);
                ConfigUtils.playTitleSound(audience);
                return true;
            } else {
                TitleUtil.sendOnlySubtitle(
                    MiniMessageUtil.parse(MiniMessageUtil.replaceLegacy(
                        placeholderAPISupport ? PlaceholderAPI.setPlaceholders(null, titleandsubtitle) : titleandsubtitle), 
                        PlaceholderUtil.replacePlaceholders()),
                        audience, 1000, 3000, 1000);
                ConfigUtils.sendTitleConfirmation(sender);
                ConfigUtils.playTitleSound(audience);
                return true;
            }
        }

        String titleandsubtitlefinal[] = TitleUtil.getTitleAndSubtitle(titleandsubtitle, sender);

        if(titleandsubtitlefinal == null) return false;

        if (sender instanceof Player player) {
            // Send the title
            TitleUtil.sendTitle(
                MiniMessageUtil.parse(MiniMessageUtil.replaceLegacy(
                    placeholderAPISupport ? PlaceholderAPI.setPlaceholders(player, titleandsubtitlefinal[0]) : titleandsubtitlefinal[0]),
                    PlaceholderUtil.replacePlaceholders(player)),
                MiniMessageUtil.parse(MiniMessageUtil.replaceLegacy(
                    placeholderAPISupport ? PlaceholderAPI.setPlaceholders(player, titleandsubtitlefinal[1]) : titleandsubtitlefinal[1]),
                    PlaceholderUtil.replacePlaceholders(player)),
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
                MiniMessageUtil.parse(MiniMessageUtil.replaceLegacy(
                    placeholderAPISupport ? PlaceholderAPI.setPlaceholders(null, titleandsubtitlefinal[0]) : titleandsubtitlefinal[0]),
                    PlaceholderUtil.replacePlaceholders()),
                MiniMessageUtil.parse(MiniMessageUtil.replaceLegacy(
                    placeholderAPISupport ? PlaceholderAPI.setPlaceholders(null, titleandsubtitlefinal[1]) : titleandsubtitlefinal[1]),
                    PlaceholderUtil.replacePlaceholders()),
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
