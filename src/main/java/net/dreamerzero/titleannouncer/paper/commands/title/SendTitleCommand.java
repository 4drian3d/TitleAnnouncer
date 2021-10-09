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
import net.dreamerzero.titleannouncer.common.utils.TitleUtil;
import net.dreamerzero.titleannouncer.paper.Announcer;
import net.dreamerzero.titleannouncer.paper.utils.PPlaceholders;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class SendTitleCommand implements CommandExecutor {
    private MiniMessage mm;
    public SendTitleCommand(MiniMessage mm) {
        this.mm = mm;
    }

    //Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ConfigUtils config = new ConfigUtils();
        // The command requires arguments to work
        switch (args.length) {
            case 0 -> {
                config.sendNoArgumentMessage(sender);
                return true;
            }
            case 1 -> {
                config.noTitlePlayerArgumentProvided(sender);
                return true;
            }
        }

        boolean placeholderAPISupport = Announcer.placeholderAPIHook();

        // Concatenate the arguments provided by the command sent.
        String titleandsubtitle = new GeneralUtils().getCommandString(args, 1);

        var serverplayers = Bukkit.getOnlinePlayers();
        Player playerObjetive = Bukkit.getPlayer(args[0]);

        if (!serverplayers.contains(playerObjetive)) {
            // Send an error message to the sender using the command.
            config.playerNotFoundMessage(sender);
            return false;
        }

        TitleUtil tUtil = new TitleUtil();

        if(!titleandsubtitle.contains(";")){
            tUtil.sendOnlySubtitle(
                mm.parse(MiniMessageUtil.replaceLegacy(
                    placeholderAPISupport ? PlaceholderAPI.setPlaceholders(playerObjetive, titleandsubtitle) : titleandsubtitle), 
                    PPlaceholders.replacePlaceholders(playerObjetive)),
                    playerObjetive, 1000, 3000, 1000);
            config.sendConfirmation(ComponentType.TITLE, sender);
            config.playPaperSound(ComponentType.TITLE, playerObjetive);
            return true;
        }

        String titleandsubtitlefinal[] = tUtil.getTitleAndSubtitle(titleandsubtitle, sender);

        if(titleandsubtitlefinal == null) return false;

        // Send the title
        tUtil.sendTitle(
            mm.parse(placeholderAPISupport ? MiniMessageUtil.replaceLegacy(
                PlaceholderAPI.setPlaceholders(playerObjetive, titleandsubtitlefinal[0])) : titleandsubtitlefinal[0], 
                PPlaceholders.replacePlaceholders(playerObjetive)),
            mm.parse(placeholderAPISupport ? MiniMessageUtil.replaceLegacy(
                PlaceholderAPI.setPlaceholders(playerObjetive, titleandsubtitlefinal[1])) : titleandsubtitlefinal[1], 
                PPlaceholders.replacePlaceholders(playerObjetive)),
            playerObjetive, 1000, 3000, 1000);
        config.playPaperSound(ComponentType.TITLE, playerObjetive);
        config.sendConfirmation(ComponentType.TITLE, sender);
        return true;
    }
}
