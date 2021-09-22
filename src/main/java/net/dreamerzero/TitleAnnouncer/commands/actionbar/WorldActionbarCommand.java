package net.dreamerzero.titleannouncer.commands.actionbar;

import static net.dreamerzero.titleannouncer.utils.PlaceholderUtil.replacePlaceholders;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dreamerzero.titleannouncer.Announcer;
import net.dreamerzero.titleannouncer.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.utils.PlaceholderUtil;
import net.dreamerzero.titleannouncer.utils.SoundUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

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

        var enabledPrefix = plugin.getConfig().getBoolean("messages.prefix.enabled", true);
        Component prefix = Component.text("");

        if (enabledPrefix) {
            prefix = MiniMessageUtil.parse(plugin.getConfig().getString(
                "messages.prefix.line",
                "<gray>[</gray><gradient:yellow:blue>TitleAnnouncer</gradient><gray>]</gray> "));
        }

        // Permission Check
        if (!player.hasPermission("announcer.actionbar.world")) {
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.actionbar.no-permission",
                        "<red>You do not have permission to execute this command</red>"))));
            return true;
        }

        // Get the world in which the player is located
        Audience audience = player.getWorld();

        // Concatenate the arguments provided by the command sent.
        var actionbartext = new StringBuilder();
        for (String argument : args) {
            actionbartext = actionbartext.append(" ");
            actionbartext = actionbartext.append(argument);
        }

        // Convert StringBuilder to String, Component is not compatible :nimodo:
        String actionbarToParse = actionbartext.toString();

        if(PlaceholderUtil.placeholderAPIHook()){
            String announceToSend = MiniMessageUtil.replaceLegacy(PlaceholderAPI.setPlaceholders(player, actionbarToParse));
            // Send to all
            audience.sendActionBar(
                MiniMessageUtil.parse(announceToSend, replacePlaceholders(player)));
        } else {
            // Send to all
            audience.sendActionBar(
                MiniMessageUtil.parse(actionbarToParse, replacePlaceholders(player)));
        }

        
        sender.sendMessage(
            prefix.append(MiniMessageUtil.parse(
                plugin.getConfig().getString(
                    "messages.actionbar.successfully",
                    "<green>Actionbar succesfully sended</green>"))));

        String soundToPlay = plugin.getConfig().getString(
            "sounds.actionbar.sound-id",
            "entity.experience_orb.pickup");
        boolean soundEnabled = plugin.getConfig().getBoolean("sounds.actionbar.enabled", true);
        float volume = plugin.getConfig().getInt("sounds.actionbar.volume", 10);
        float pitch = plugin.getConfig().getInt("sounds.actionbar.pitch", 2);

        if (soundEnabled) {
            // Play the sound
            SoundUtil.playSound(
                soundToPlay,
                audience,
                volume,
                pitch
            );
        }
        return true;
    }
}
