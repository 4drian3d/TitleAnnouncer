package net.dreamerzero.titleannouncer.commands.actionbar;

import static net.dreamerzero.titleannouncer.utils.PlaceholderUtil.replacePlaceholders;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dreamerzero.titleannouncer.Announcer;
import net.dreamerzero.titleannouncer.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.utils.PlaceholderUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.util.TriState;

public class WorldActionbarCommand implements CommandExecutor {
    private final Announcer plugin;
    public WorldActionbarCommand(Announcer plugin) {
        this.plugin = plugin;
    }

    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // It will send an actionbar to the world in which the command is executed,
        // it makes no sense for the console to execute it.
        if (!(sender instanceof Player player)) {
            plugin.getLogger().info("The console cannot execute this command.");
            return false;
        }

        // Permission Check
        if (player.permissionValue("announcer.actionbar.world") != TriState.TRUE) {
            ConfigUtils.sendNoActionbarPermission(sender);
            return true;
        }

        // Get the world in which the player is located
        Audience audience = player.getWorld();

        // Concatenate the arguments provided by the command sent.
        var actionbartext = GeneralUtils.getCommandString(args);

        if(PlaceholderUtil.placeholderAPIHook()){
            // Send to all
            audience.sendActionBar(
                MiniMessageUtil.parse(
                    MiniMessageUtil.replaceLegacy(
                        PlaceholderAPI.setPlaceholders(player, actionbartext)), replacePlaceholders(player)));
            ConfigUtils.sendActionbarConfirmation(sender);
            ConfigUtils.playActionbarSound(audience);
            return true;
        } else {
            // Send to all
            audience.sendActionBar(
                MiniMessageUtil.parse(actionbartext, replacePlaceholders(player)));
            ConfigUtils.sendActionbarConfirmation(sender);
            ConfigUtils.playActionbarSound(audience);
            return true;
        }
    }
}
