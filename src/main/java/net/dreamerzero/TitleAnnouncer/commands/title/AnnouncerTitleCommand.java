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

public class AnnouncerTitleCommand implements CommandExecutor {
    private Announcer plugin;
	public AnnouncerTitleCommand(Announcer plugin) {
		this.plugin = plugin;
	}

    //The audience that will receive the title will be all the players on the server.
    public Audience audience = Bukkit.getServer();

    //Configuration paths
    //String soundtitle = plugin.getConfig().getString("sounds.title.id");
    //Boolean soundEnabled = plugin.getConfig().getBoolean("sounds.title.enabled");

    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // The command requires arguments to work
        if (args.length == 0) {
            sender.sendMessage(
                MiniMessageUtil.miniMessageParse(
                    plugin.getConfig().getString("messages.title.without-argument")));
            return true;
        // The command requires title and subtitle arguments to work properly.
        } else if (args.length == 1) {
            sender.sendMessage(
                MiniMessageUtil.miniMessageParse(
                    plugin.getConfig().getString("messages.title.single-argument")));
            return true;
        }

        // Concatenate the arguments provided by the command sent.
        StringBuilder titleandsubtitle = new StringBuilder();
        for (byte i = 0; i < args.length; i++) {
            titleandsubtitle = titleandsubtitle.append(" ");
            titleandsubtitle = titleandsubtitle.append(args[i]); 
        }
        
        try {
            // Convert StringBuilder to String, Component is not compatible :nimodo:
            String titleandsubtitlefinal[] = titleandsubtitle.toString().split(";");
            
            // Send the title
            TitleUtil.sendTitle(
                MiniMessageUtil.miniMessageParse(titleandsubtitlefinal[0]), 
                MiniMessageUtil.miniMessageParse(titleandsubtitlefinal[1]),
                audience, 
                1000, 
                3000, 
                1000);
            
            // Send message to the sender
            sender.sendMessage(
                MiniMessageUtil.miniMessageParse(
                    plugin.getConfig().getString("messages.title.successfully")));
            
            //Play the sound
            SoundUtil.playSound(
                "entity.experience_orb.pickup", 
                audience, 
                10f, 
                2f);
            
            return true;
        // In case the command does not contain a separator ";", 
        // it will catch the error in the console and send an error message to the sender.
        } catch (Exception e) {
            // Send an error message to the sender using the command
            sender.sendMessage(
                MiniMessageUtil.miniMessageParse(
                    plugin.getConfig().getString("messages.title.error")));
            return false;
        }
    }
}
