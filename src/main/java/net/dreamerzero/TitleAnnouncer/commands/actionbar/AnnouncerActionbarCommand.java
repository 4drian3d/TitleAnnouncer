package net.dreamerzero.titleannouncer.commands.actionbar;

import static net.dreamerzero.titleannouncer.utils.PlaceholderUtil.replacePlaceholders;

import org.bukkit.Bukkit;
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

public class AnnouncerActionbarCommand implements CommandExecutor {
    private final Announcer plugin;
    public AnnouncerActionbarCommand(Announcer plugin) {
        this.plugin = plugin;
    }

    // The audience that will receive the actionbar will be all the players on the server.
    Audience audience = Bukkit.getServer();

    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        var enabledPrefix = plugin.getConfig().getBoolean("messages.prefix.enabled", true);
        Component prefix = Component.text("");

        if (enabledPrefix) {
            prefix = MiniMessageUtil.parse(plugin.getConfig().getString(
                "messages.prefix.line",
                "<gray>[</gray><gradient:yellow:blue>TitleAnnouncer</gradient><gray>]</gray> "));
        }
        // Permission Check
        if (!sender.hasPermission("announcer.actionbar.global")) {
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.actionbar.no-permission",
                        "<red>You do not have permission to execute this command</red>"))));
            return true;
        }

        // Concatenate the arguments provided by the command sent.
        StringBuilder actionbartext = new StringBuilder();
        for (String argument : args) {
            actionbartext = actionbartext.append(" ");
            actionbartext = actionbartext.append(argument);
        }

        // Convert StringBuilder to String, Component is not compatible :nimodo:
        String actionbarToParse = actionbartext.toString();

        String announceToSend;

        // Send to all
        if(PlaceholderUtil.placeholderAPIHook()){
            if (sender instanceof Player player) {
                announceToSend = MiniMessageUtil.replaceLegacy(PlaceholderAPI.setPlaceholders(player, actionbarToParse));
                audience.sendActionBar(
                    MiniMessageUtil.parse(announceToSend, replacePlaceholders(player)));
            } else {
                announceToSend = MiniMessageUtil.replaceLegacy(PlaceholderAPI.setPlaceholders(null, actionbarToParse));
                audience.sendActionBar(
                    MiniMessageUtil.parse(announceToSend, replacePlaceholders()));
            }
        } else {
            if (sender instanceof Player player) {
                audience.sendActionBar(
                    MiniMessageUtil.parse(actionbarToParse, replacePlaceholders(player)));
            } else {
                audience.sendActionBar(
                    MiniMessageUtil.parse(actionbarToParse, replacePlaceholders()));
            }
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
