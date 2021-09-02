package net.dreamerzero.titleannouncer.commands.title;

import static net.dreamerzero.titleannouncer.utils.PlaceholderUtil.replacePlaceholders;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dreamerzero.titleannouncer.Announcer;
import net.dreamerzero.titleannouncer.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.utils.SoundUtil;
import net.dreamerzero.titleannouncer.utils.TitleUtil;
import net.kyori.adventure.text.Component;

public class SendTitleCommand implements CommandExecutor {
    private final Announcer plugin;
    
	public SendTitleCommand(Announcer plugin) {
		this.plugin = plugin;
	}

    //Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        var enabledPrefix = plugin.getConfig().getBoolean("messages.prefix.enabled", true);
        Component prefix = Component.text("");

        if (enabledPrefix) {
            prefix = MiniMessageUtil.parse(plugin.getConfig().getString(
                "messages.prefix.line", 
                "<gray>[</gray><gradient:yellow:blue>TitleAnnouncer</gradient><gray>]</gray> "));
        }

        // Permission Check
        if (!sender.hasPermission("announcer.title.send")) {
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.title.no-permission",
                        "<red>You do not have permission to execute this command</red>"))));
            return true;
        }
        
        // The command requires arguments to work
        if (args.length == 0) {
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.title.without-argument",
                        "<red>You need to enter the title and subtitle arguments.</red>"))));
            return true;
        // The command requires player argument to work.
        } else if (args.length == 1) {
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.title.only-player",
                        "<gray>You must enter the title and subtitle after the player's name to send the message correctly.</gray>"))));
            return true;
        // The command requires title and subtitle arguments to work properly.
        } else if (args.length == 2) {
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.title.single-argument",
                        "<gray>You need to enter the title, the subtitle and the separator ';' in orden to send the title.</gray>"))));
            return true;
        }

        // Concatenate the arguments provided by the command sent.
        var titleandsubtitle = new StringBuilder();
        for (byte i = 1; i < args.length; i++) {
            titleandsubtitle = titleandsubtitle.append(" ");
            titleandsubtitle = titleandsubtitle.append(args[i]); 
        }

        var soundToPlay = plugin.getConfig().getString(
            "sounds.title.sound-id", 
            "entity.experience_orb.pickup");
        var soundEnabled = plugin.getConfig().getBoolean("sounds.title.enabled", true);
        int volume = plugin.getConfig().getInt("sounds.title.volume", 10);
        int pitch = plugin.getConfig().getInt("sounds.title.pitch", 2);
        
        try {
            // Convert StringBuilder to String, Component is not compatible :nimodo:
            final String titleandsubtitlefinal[] = titleandsubtitle.toString().split(";");
            final var playerObjetive = Bukkit.getPlayer(args[0]);

            try {
                final var serverplayers = Bukkit.getOnlinePlayers();

                if (!serverplayers.contains(playerObjetive)) {
                    // Send an error message to the sender using the command.
                    sender.sendMessage(
                        prefix.append(MiniMessageUtil.parse(
                            plugin.getConfig().getString(
                                "messages.title.player-not-found",
                                "<red>Player not found</red>"))));
                    return false;
                }

				if (sender instanceof Player player) {
                    // Send the title
                    TitleUtil.sendTitle(
                        MiniMessageUtil.parse(titleandsubtitlefinal[0], replacePlaceholders(player, playerObjetive)), 
                        MiniMessageUtil.parse(titleandsubtitlefinal[1], replacePlaceholders(player, playerObjetive)),
                        playerObjetive, 
                        1000, 
                        3000, 
                        1000);
                } else {
                    TitleUtil.sendTitle(
                        MiniMessageUtil.parse(titleandsubtitlefinal[0], replacePlaceholders(playerObjetive)), 
                        MiniMessageUtil.parse(titleandsubtitlefinal[1], replacePlaceholders(playerObjetive)),
                        playerObjetive, 
                        1000, 
                        3000, 
                        1000); 
                }
                

                if (soundEnabled) {
                    //Play the sound
                    SoundUtil.playSound(
                    soundToPlay, 
                    playerObjetive, 
                    volume, 
                    pitch);
                }

                // Send message to the sender
                sender.sendMessage(
                    prefix.append(MiniMessageUtil.parse(
                        plugin.getConfig().getString(
                            "messages.title.successfully",
                            "<green>Title succesfully sended</green>"))));
                
                return true;
            } catch (Exception e) {
                // Send an error message to the sender using the command
                sender.sendMessage(
                    prefix.append(MiniMessageUtil.parse(
                        plugin.getConfig().getString(
                            "messages.title.error", 
                            "<dark_red>An error occurred while sending the title. Be sure to use the ';' to separate the title and the subtitle.</dark_red>"))));
                return false;
            }
        // In case the command does not contain a separator ";", 
        // it will catch the error in the console and send an error message to the sender.
        } catch (Exception e) {
            // Send an error message to the sender using the command
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.title.error",
                        "<dark_red>An error occurred while sending the title. Be sure to use the ';' to separate the title and the subtitle.</dark_red>"))));
            return false;
        }
    }
}