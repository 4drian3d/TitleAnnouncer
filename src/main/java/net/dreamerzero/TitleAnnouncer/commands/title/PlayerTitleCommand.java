package net.dreamerzero.TitleAnnouncer.commands.title;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dreamerzero.TitleAnnouncer.Announcer;
import net.dreamerzero.TitleAnnouncer.utils.MiniMessageUtil;
import net.dreamerzero.TitleAnnouncer.utils.SoundUtil;
import net.dreamerzero.TitleAnnouncer.utils.TitleUtil;

public class PlayerTitleCommand implements CommandExecutor {
    private Announcer plugin;
	public PlayerTitleCommand(Announcer plugin) {
		this.plugin = plugin;
	}

    //Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // It will send an title to the one who executes the command, 
        // it makes no sense for the console to execute it.
        if (!(sender instanceof Player)) {
            plugin.getLogger().info("The console cannot execute this command.");
            return false;
        }

        // Permission Check
        if (!(sender.hasPermission("announcer.title.send"))){
            sender.sendMessage(plugin.getConfig().getString("messages.title.no-permission"));
            return true;
        }
        
        // The command requires arguments to work
        if (args.length == 0) {
            sender.sendMessage(
                MiniMessageUtil.parse(
                    plugin.getConfig().getString("messages.title.without-argument")));
            return true;
        // The command requires player argument to work.
        } else if (args.length == 1) {
            sender.sendMessage(
                MiniMessageUtil.parse(
                    plugin.getConfig().getString("messages.title.only-player")));
            return true;
        // The command requires title and subtitle arguments to work properly.
        } else if (args.length == 2) {
            sender.sendMessage(
                MiniMessageUtil.parse(
                    plugin.getConfig().getString("messages.title.single-argument")));
            return true;
        }

        // Concatenate the arguments provided by the command sent.
        StringBuilder titleandsubtitle = new StringBuilder();
        for (byte i = 1; i < args.length; i++) {
            titleandsubtitle = titleandsubtitle.append(" ");
            titleandsubtitle = titleandsubtitle.append(args[i]); 
        }
        
        try {
            // Convert StringBuilder to String, Component is not compatible :nimodo:
            String titleandsubtitlefinal[] = titleandsubtitle.toString().split(";");
			Player playerObjetive = Bukkit.getPlayer(args[0]);

            try {
                var serverplayers = Bukkit.getOnlinePlayers();

                if (!(serverplayers.contains(playerObjetive))){
                    // Send an error message to the sender using the command.
                    sender.sendMessage(
                        MiniMessageUtil.parse(
                            plugin.getConfig().getString("messages.title.player-not-found")));
                    return false;
                }
				
                // Send the title
                TitleUtil.sendTitle(
                    MiniMessageUtil.parse(titleandsubtitlefinal[0]), 
                    MiniMessageUtil.parse(titleandsubtitlefinal[1]),
                    playerObjetive, 
                    1000, 
                    3000, 
                    1000);

                //Play the sound
                SoundUtil.playSound(
                    "entity.experience_orb.pickup", 
                    playerObjetive, 
                    10f, 
                    2f);
                // Send message to the sender
                sender.sendMessage(
                    MiniMessageUtil.parse(
                        plugin.getConfig().getString("messages.title.successfully")));
                
                return true;
            } catch (Exception e) {
                // Send an error message to the sender using the command
                sender.sendMessage(
                    MiniMessageUtil.parse(
                        plugin.getConfig().getString("messages.title.error")));
                return false;
            }
        // In case the command does not contain a separator ";", 
        // it will catch the error in the console and send an error message to the sender.
        } catch (Exception e) {
            // Send an error message to the sender using the command
            sender.sendMessage(
                MiniMessageUtil.parse(
                    plugin.getConfig().getString("messages.title.error")));
            return false;
        }
    }
}