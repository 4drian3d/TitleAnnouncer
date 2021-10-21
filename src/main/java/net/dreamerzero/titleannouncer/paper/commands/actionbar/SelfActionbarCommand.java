package net.dreamerzero.titleannouncer.paper.commands.actionbar;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dreamerzero.titleannouncer.common.utils.ComponentType;
import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.common.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.paper.Announcer;
import net.dreamerzero.titleannouncer.paper.utils.PPlaceholders;
import net.kyori.adventure.text.minimessage.MiniMessage;

public record SelfActionbarCommand(MiniMessage mm) implements CommandExecutor {

    //Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // It will send an actionbar to the one who executes the command,
        // it makes no sense for the console to execute it.
        if (!(sender instanceof Player player)) {
            ConfigUtils.onlyPlayerExecute(sender);
            return false;
        }

        if(args.length == 0) {
            ConfigUtils.noActionbarArgumentProvided(sender);
            return false;
        }

        // Concatenate the arguments provided by the command sent.
        String actionbartext = GeneralUtils.getCommandString(args);

        // Send to sender
        sender.sendActionBar(mm.deserialize(
            MiniMessageUtil.replaceLegacy(
                Announcer.placeholderAPIHook() ? PlaceholderAPI.setPlaceholders(player, actionbartext) : actionbartext), 
            PPlaceholders.replacePlaceholders(player)));
        ConfigUtils.sendConfirmation(ComponentType.ACTIONBAR, sender);
        ConfigUtils.playPaperSound(ComponentType.ACTIONBAR, sender);
        return true;
    }
}
