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
import net.kyori.adventure.audience.Audience;

public class WorldActionbarCommand implements CommandExecutor {
    public WorldActionbarCommand() {}

    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ConfigUtils config = new ConfigUtils(); 
        // It will send an actionbar to the world in which the command is executed,
        // it makes no sense for the console to execute it.
        if (!(sender instanceof Player player)) {
            config.onlyPlayerExecute(sender);
            return false;
        }

        if(args.length == 0) {
            config.noActionbarArgumentProvided(sender);
            return false;
        }

        // Get the world in which the player is located
        Audience audience = player.getWorld();

        // Concatenate the arguments provided by the command sent.
        String actionbartext = new GeneralUtils().getCommandString(args);
        MiniMessageUtil mUtils = new MiniMessageUtil();

        audience.sendActionBar(
            mUtils.parse(mUtils.replaceLegacy(
                Announcer.placeholderAPIHook() ? PlaceholderAPI.setPlaceholders(player, actionbartext) : actionbartext),
                new PPlaceholders().replacePlaceholders(player)));
        config.sendConfirmation(ComponentType.ACTIONBAR, sender);
        config.playPaperSound(ComponentType.ACTIONBAR, audience);
        return true;
    }
}
