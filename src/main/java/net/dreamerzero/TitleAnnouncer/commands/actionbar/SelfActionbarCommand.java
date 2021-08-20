package net.dreamerzero.TitleAnnouncer.commands.actionbar;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dreamerzero.TitleAnnouncer.Announcer;
import net.dreamerzero.TitleAnnouncer.utils.MiniMessageUtil;
import net.dreamerzero.TitleAnnouncer.utils.SoundUtil;

public class SelfActionbarCommand implements CommandExecutor {
    private Announcer plugin;
	public SelfActionbarCommand(Announcer plugin) {
		this.plugin = plugin;
	}

    //Configuration Paths
    //String configSound = plugin.getConfig().getString("sounds.actionbar.id");
    //Boolean soundEnabled = plugin.getConfig().getBoolean("sounds.actionbar.enabled");

    //Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // It will send an actionbar to the one who executes the command, 
        // it makes no sense for the console to execute it.
        if (!(sender instanceof Player)) {
            plugin.getLogger().info("The console cannot execute this command.");
            return false;
        }

        // Permission Check
        if (!(sender.hasPermission("announcer.actionbar.test"))){
            sender.sendMessage(
                MiniMessageUtil.parse(
                    plugin.getConfig().getString("messages.title.no-permission")));
            return true;
        }
        
        // Concatenate the arguments provided by the command sent.
        StringBuilder actionbartext = new StringBuilder();
        for (byte i = 0; i < args.length; i++) {
            actionbartext = actionbartext.append(" ");
            actionbartext = actionbartext.append(args[i]); 
        }
        
        // Convert StringBuilder to String, Component is not compatible :nimodo:
        String actionbartoparse = actionbartext.toString();
        
        // Send to sender
        sender.sendActionBar(
            MiniMessageUtil.parse(actionbartoparse));
        sender.sendMessage(
            MiniMessageUtil.parse(
                plugin.getConfig().getString("messages.actionbar.successfully")));

        // Play the sound
        SoundUtil.playSound(
            "entity.experience_orb.pickup", 
            sender, 
            10f, 
            2f);

        return true;
    }
}