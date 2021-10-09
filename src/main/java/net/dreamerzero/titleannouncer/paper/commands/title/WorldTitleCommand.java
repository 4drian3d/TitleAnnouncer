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
import net.dreamerzero.titleannouncer.common.utils.TitleUtil;
import net.dreamerzero.titleannouncer.paper.Announcer;
import net.dreamerzero.titleannouncer.paper.utils.PPlaceholders;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class WorldTitleCommand implements CommandExecutor {
    private MiniMessage mm;
    public WorldTitleCommand(MiniMessage mm) {
        this.mm = mm;
    }

    //Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // It will send an title to the one who executes the command,
        // it makes no sense for the console to execute it.
        if (!(sender instanceof Player player)) {
            ConfigUtils.onlyPlayerExecute(sender);
            return false;
        }

        // Get the world in which the player is located.
        Audience audience = player.getWorld();

        // The command requires arguments to work
        if(args.length == 0){
            ConfigUtils.sendNoArgumentMessage(sender);
            return false;
        }

        boolean placeholderAPISupport = Announcer.placeholderAPIHook();

        // Concatenate the arguments provided by the command sent.
        String titleandsubtitle = GeneralUtils.getCommandString(args);

        TitleUtil tUtil = new TitleUtil();

        if(!titleandsubtitle.contains(";")){
            tUtil.sendOnlySubtitle(
                mm.parse(
                MiniMessageUtil.replaceLegacy(placeholderAPISupport ?
                    PlaceholderAPI.setPlaceholders(player, titleandsubtitle) : titleandsubtitle),
                    PPlaceholders.replacePlaceholders(player)),
                    sender, 1000, 3000, 1000);
            ConfigUtils.sendConfirmation(ComponentType.TITLE, sender);
            ConfigUtils.playPaperSound(ComponentType.TITLE, audience);
            return true;
        }

        String titleandsubtitlefinal[] = tUtil.getTitleAndSubtitle(titleandsubtitle, sender);

        if(titleandsubtitlefinal == null) return false;

        tUtil.sendTitle(
            mm.parse(MiniMessageUtil.replaceLegacy(
                placeholderAPISupport ? PlaceholderAPI.setPlaceholders(player, titleandsubtitlefinal[0]): titleandsubtitlefinal[0]), 
                PPlaceholders.replacePlaceholders(player)),
            mm.parse(MiniMessageUtil.replaceLegacy(
                placeholderAPISupport ? PlaceholderAPI.setPlaceholders(player, titleandsubtitlefinal[1]) : titleandsubtitlefinal[1]), 
                PPlaceholders.replacePlaceholders(player)),
            audience,
            1000,
            3000,
            1000);
        ConfigUtils.playPaperSound(ComponentType.TITLE, audience);
        ConfigUtils.sendConfirmation(ComponentType.TITLE, sender);
        return true;
    }
}
