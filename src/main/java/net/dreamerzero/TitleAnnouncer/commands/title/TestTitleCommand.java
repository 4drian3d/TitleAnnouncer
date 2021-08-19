package net.dreamerzero.TitleAnnouncer.commands.title;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dreamerzero.TitleAnnouncer.Announcer;
import net.dreamerzero.TitleAnnouncer.utils.MiniMessageUtil;
import net.dreamerzero.TitleAnnouncer.utils.SoundUtil;
import net.dreamerzero.TitleAnnouncer.utils.TitleUtil;

/*
This command will be executed as a test of the "/anunciarevento" command. 
It will only be sent for the same player.
*/
public class TestTitleCommand implements CommandExecutor {
    private Announcer plugin;
	public TestTitleCommand(Announcer plugin) {
		this.plugin = plugin;
	}

    //Configuration paths
    //String configSound = plugin.getConfig().getString("sounds.title");
    //Boolean soundEnabled = plugin.getConfig().getBoolean("sounds.title.enabled");

    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            plugin.getLogger().info("The console cannot execute this command.");
            return false;
        }
        if (args.length == 0) {
            sender.sendMessage(
                MiniMessageUtil.miniMessageParse(
                    plugin.getConfig().getString("messages.title.without-argument")));
            return true;
        } else if (args.length == 1) {
            sender.sendMessage(
                MiniMessageUtil.miniMessageParse(
                    plugin.getConfig().getString("messages.title.single-argument")));
            return true;
        }

        StringBuilder titleandsubtitle = new StringBuilder();
        for (byte i = 0; i < args.length; i++) {
            titleandsubtitle = titleandsubtitle.append(" ");
            titleandsubtitle = titleandsubtitle.append(args[i]); 
        }
        
        // Convert StringBuilder to String, Component is not compatible :nimodo:
        try {
            String titleandsubtitlefinal[] = titleandsubtitle.toString().split(";");
            
            TitleUtil.sendTitle(
                MiniMessageUtil.miniMessageParse(titleandsubtitlefinal[0]), 
                MiniMessageUtil.miniMessageParse(titleandsubtitlefinal[1]), 
                sender,
                1000,
                3000,
                1000);
            
            sender.sendMessage(
                MiniMessageUtil.miniMessageParse(
                    plugin.getConfig().getString("messages.title.successfully")));
            
            //Sound
            SoundUtil.playSound(
                "entity.experience_orb.pickup", 
                sender, 
                10f, 
                2f);
            
            return true;
        } catch (Exception e) {
            sender.sendMessage(
                MiniMessageUtil.miniMessageParse(
                    plugin.getConfig().getString("messages.title.error")));
            return false;
        }
    }
}
