package net.dreamerzero.titleannouncer.paper.commands.title;

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
import net.kyori.adventure.util.TriState;

public class WorldTitleCommand implements CommandExecutor {
    public WorldTitleCommand() {}

    //Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // It will send an title to the one who executes the command,
        // it makes no sense for the console to execute it.
        if (!(sender instanceof Player player)) {
            ConfigUtils.onlyPlayerExecute(sender);
            return false;
        }

        // Permission Check
        if (player.permissionValue("titleannouncer.title.world") != TriState.TRUE) {
            ConfigUtils.sendNoMainPermission(sender);
            return true;
        }

        // Get the world in which the player is located.
        Audience audience = player.getWorld();

        // The command requires arguments to work
        if(args.length == 0){
            ConfigUtils.sendNoArgumentMessage(sender);
            return false;
        }

        boolean placeholderAPISupport = PlaceholderUtil.placeholderAPIHook();

        // Concatenate the arguments provided by the command sent.
        String titleandsubtitle = GeneralUtils.getCommandString(args);

        if(!titleandsubtitle.contains(";")){
            TitleUtil.sendOnlySubtitle(
                MiniMessageUtil.parse(
                MiniMessageUtil.replaceLegacy(placeholderAPISupport ?
                    PlaceholderAPI.setPlaceholders(player, titleandsubtitle) : titleandsubtitle),
                    PlaceholderUtil.replacePlaceholders(player)),
                    sender, 1000, 3000, 1000);
            ConfigUtils.sendTitleConfirmation(sender);
            ConfigUtils.playTitleSound(sender);
            return true;
        }

        String titleandsubtitlefinal[];

        if(TitleUtil.getTitleAndSubtitle(titleandsubtitle, sender) == null){
            return false;
        } else {
            titleandsubtitlefinal = TitleUtil.getTitleAndSubtitle(titleandsubtitle, sender);
        }

        TitleUtil.sendTitle(
            MiniMessageUtil.parse(MiniMessageUtil.replaceLegacy(
                placeholderAPISupport ? PlaceholderAPI.setPlaceholders(player, titleandsubtitlefinal[0]): titleandsubtitlefinal[0]), 
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
    }
}
