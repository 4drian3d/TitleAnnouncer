package net.dreamerzero.TitleAnnouncer.commands.actionbar;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dreamerzero.TitleAnnouncer.Announcer;
import net.dreamerzero.TitleAnnouncer.utils.MiniMessageUtil;
import net.dreamerzero.TitleAnnouncer.utils.SoundUtil;
import net.kyori.adventure.audience.Audience;

public class WorldActionbarCommand implements CommandExecutor {
    private Announcer plugin;
	public WorldActionbarCommand(Announcer plugin) {
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

        // Player
        Player player = (Player) sender;

        // Permission Check
        if (!(player.hasPermission("announcer.actionbar.world"))){
            sender.sendMessage(
                MiniMessageUtil.parse(
                    plugin.getConfig().getString("messages.title.no-permission")));
            return true;
        }

        // Get the world in which the player is located
        Audience audience = player.getWorld();

        // Concatenate the arguments provided by the command sent.
        StringBuilder actionbartext = new StringBuilder();
        for (byte i = 0; i < args.length; i++) {
            actionbartext = actionbartext.append(" ");
            actionbartext = actionbartext.append(args[i]); 
        }
        
        // Convert StringBuilder to String, Component is not compatible :nimodo:
        String actionbartoparse = actionbartext.toString();
        
        // Send to all
        audience.sendActionBar(
            MiniMessageUtil.parse(actionbartoparse));
        sender.sendMessage(
            MiniMessageUtil.parse(
                plugin.getConfig().getString("messages.actionbar.successfully")));

        // Play the sound
        SoundUtil.playSound(
            "entity.experience_orb.pickup", 
            audience, 
            10f, 
            2f);

        return true;
    }
}
