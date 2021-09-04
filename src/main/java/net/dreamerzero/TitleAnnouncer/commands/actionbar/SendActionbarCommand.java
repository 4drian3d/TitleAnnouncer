package net.dreamerzero.titleannouncer.commands.actionbar;

import static net.dreamerzero.titleannouncer.utils.PlaceholderUtil.replacePlaceholders;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dreamerzero.titleannouncer.Announcer;
import net.dreamerzero.titleannouncer.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.utils.SoundUtil;
import net.kyori.adventure.text.Component;

public class SendActionbarCommand implements CommandExecutor {
    private final Announcer plugin;
    public SendActionbarCommand(Announcer plugin) {
        this.plugin = plugin;
    }

    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // It will send an actionbar to the world in which the command is executed,
        // it makes no sense for the console to execute it.
        if (!(sender instanceof Player)) {
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
        if (!sender.hasPermission("announcer.actionbar.send")) {
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.actionbar.no-permission",
                        "<red>You do not have permission to execute this command</red>"))));
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.actionbar.only-player",
                        "<gray>You must enter the message to be sent after the player's name.</gray>"))));
            return false;
        }

        // Get the player
        final var playerObjetive = Bukkit.getPlayer(args[0]);

        //Collection of all players in the server
        final var serverplayers = Bukkit.getOnlinePlayers();

        if (!serverplayers.contains(playerObjetive)) {
            // Send an error message to the sender using the command.
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.actionbar.player-not-found",
                        "<red>Player not found</red>"))));
                return false;
        }

        // Concatenate the arguments provided by the command sent.
        var actionbartext = new StringBuilder();
        for (byte i = 1; i < args.length; i++) {
            actionbartext = actionbartext.append(" ");
            actionbartext = actionbartext.append(args[i]);
        }

        // Convert StringBuilder to String, Component is not compatible :nimodo:
        final var actionbarToParse = actionbartext.toString();

        // Send to all
        if (sender instanceof Player player) {
            playerObjetive.sendActionBar(
                MiniMessageUtil.parse(actionbarToParse, replacePlaceholders(player, playerObjetive)));
        } else {
            playerObjetive.sendActionBar(
                MiniMessageUtil.parse(actionbarToParse, replacePlaceholders(playerObjetive)));
        }
        sender.sendMessage(
            prefix.append(MiniMessageUtil.parse(
                plugin.getConfig().getString(
                    "messages.actionbar.successfully",
                    "<green>Actionbar succesfully sended</green>"))));

        var soundToPlay = plugin.getConfig().getString("sounds.actionbar.sound-id", "entity.experience_orb.pickup");
        var soundEnabled = plugin.getConfig().getBoolean("sounds.actionbar.enabled", true);
        float volume = plugin.getConfig().getInt("sounds.actionbar.volume", 10);
        float pitch = plugin.getConfig().getInt("sounds.actionbar.pitch", 2);

        if (soundEnabled) {
            // Play the sound
            SoundUtil.playSound(
                soundToPlay,
                playerObjetive,
                volume,
                pitch
            );
        }
        return true;
    }
}
