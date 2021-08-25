package net.dreamerzero.TitleAnnouncer.commands.title;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.dreamerzero.TitleAnnouncer.Announcer;
import net.dreamerzero.TitleAnnouncer.utils.MiniMessageUtil;
import net.dreamerzero.TitleAnnouncer.utils.SoundUtil;
import net.dreamerzero.TitleAnnouncer.utils.TitleUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

public class AnnouncerTitleCommand implements CommandExecutor {
    private final Announcer plugin;
	public AnnouncerTitleCommand(Announcer plugin) {
		this.plugin = plugin;
	}

    //The audience that will receive the title will be all the players on the server.
    private Audience audience = Bukkit.getServer();

    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        var enabledPrefix = plugin.getConfig().getBoolean("messages.prefix.enabled", true);
        Component prefix = Component.text("");

        if (enabledPrefix) {
            prefix = MiniMessageUtil.parse(plugin.getConfig().getString(
                "messages.prefix.line", 
                "<gray>[</gray><gradient:yellow:blue>TitleAnnouncer</gradient><gray>]</gray>"));
        }
        
        // Permission Check
        if (!(sender.hasPermission("announcer.title.global"))) {
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
        // The command requires title and subtitle arguments to work properly.
        } else if (args.length == 1) {
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.title.single-argument", 
                        "<gray>You need to enter the title, the subtitle and the separator ';' in orden to send the title.</gray>"))));
            return true;
        }

        // Concatenate the arguments provided by the command sent.
        var titleandsubtitle = new StringBuilder();
        for (byte i = 0; i < args.length; i++) {
            titleandsubtitle = titleandsubtitle.append(" ");
            titleandsubtitle = titleandsubtitle.append(args[i]); 
        }
        
        // Get sound from config
        var soundToPlay = plugin.getConfig().getString(
            "sounds.title.sound-id", 
            "entity.experience_orb.pickup");
        var soundEnabled = plugin.getConfig().getBoolean("sounds.title.enabled", true);
        float volume = plugin.getConfig().getInt("sounds.title.volume", 10);
        float pitch = plugin.getConfig().getInt("sounds.title.pitch", 2);

        try {
            // Convert StringBuilder to String, Component is not compatible :nimodo:
            String titleandsubtitlefinal[] = titleandsubtitle.toString().split(";");
            
            // Send the title
            TitleUtil.sendTitle(
                MiniMessageUtil.parse(titleandsubtitlefinal[0]), 
                MiniMessageUtil.parse(titleandsubtitlefinal[1]),
                audience, 
                1000, 
                3000, 
                1000);
            
            // Send message to the sender
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString("messages.title.successfully"))));
            
            if (soundEnabled) {
                //Play the sound
                SoundUtil.playSound(
                    soundToPlay, 
                    audience, 
                    volume, 
                    pitch);
            }
            
            return true;
        // In case the command does not contain a separator ";", 
        // it will catch the error in the console and send an error message to the sender.
        } catch (Exception e) {
            // Send an error message to the sender using the command
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString("messages.title.error"))));
            return false;
        }
    }
}
