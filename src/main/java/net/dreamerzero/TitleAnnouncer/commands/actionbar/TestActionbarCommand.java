package net.dreamerzero.TitleAnnouncer.commands.actionbar;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dreamerzero.TitleAnnouncer.Announcer;
import net.dreamerzero.TitleAnnouncer.utils.MiniMessageUtil;
import net.dreamerzero.TitleAnnouncer.utils.SoundUtil;

public class TestActionbarCommand implements CommandExecutor {
    private Announcer plugin;
	public TestActionbarCommand(Announcer plugin) {
		this.plugin = plugin;
	}

    //Configuration Paths
    //String configSound = plugin.getConfig().getString("sounds.actionbar.id");
    //Boolean soundEnabled = plugin.getConfig().getBoolean("sounds.actionbar.enabled");

    //Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            plugin.getLogger().info("The console cannot execute this command.");
            return false;
        }
        
        //Concatenate the arguments provided by the command sent.
        StringBuilder actionbartext = new StringBuilder();
        for (byte i = 0; i < args.length; i++) {
            actionbartext = actionbartext.append(" ");
            actionbartext = actionbartext.append(args[i]); 
        }
        
        //Convert StringBuilder to String, Component is not compatible :nimodo:
        String actionbartoparse = actionbartext.toString();
        
        //Send to sender
        sender.sendActionBar(
            MiniMessageUtil.miniMessageParse(actionbartoparse));
        sender.sendMessage(
            MiniMessageUtil.miniMessageParse(
                plugin.getConfig().getString("messages.actionbar.successfully")));

        SoundUtil.playSound(
            "entity.experience_orb.pickup", 
            sender, 
            10f, 
            2f);

        return true;
    }
}
