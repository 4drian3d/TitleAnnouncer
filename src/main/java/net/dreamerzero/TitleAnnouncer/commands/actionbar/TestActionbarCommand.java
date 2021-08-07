package net.dreamerzero.TitleAnnouncer.commands.actionbar;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dreamerzero.TitleAnnouncer.Announcer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class TestActionbarCommand implements CommandExecutor {
    private Announcer plugin;
	public TestActionbarCommand(Announcer plugin) {
		this.plugin = plugin;
	}

    //Component that parses the actionbar with the MiniMessage format.
    private static Component miniMessageParse(final String message) {
        return MiniMessage.get().parse(message);
    }

    //The audience that will receive the actionbar will be all the players on the server.
    public Audience audience = Bukkit.getServer();

    //Configuration Paths
    String configSound = plugin.getConfig().getString("sounds.actionbar.id");
    Boolean soundEnabled = plugin.getConfig().getBoolean("sounds.actionbar.enabled");

    //Sound to play
    final Sound actionbarsound = Sound.sound(Key.key(configSound), Sound.Source.MUSIC, 10f, 2f);

    //Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)){
            plugin.getLogger().info("The console cannot execute this command.");
            return false;
        }
        //Concatenate the arguments provided by the command sent.
        StringBuilder actionbartext = new StringBuilder();
        for (byte i = 0; i < args.length; i++){
            actionbartext = actionbartext.append(" ");
            actionbartext = actionbartext.append(args[i]); 
        }
        //Convert StringBuilder to String, Component is not compatible :nimodo:
        String actionbartoparse = actionbartext.toString();
        //Send to sender
        sender.sendActionBar(miniMessageParse(actionbartoparse));
        sender.sendMessage(miniMessageParse(plugin.getConfig().getString("messages.actionbar.successfully")));
        if (soundEnabled){
            sender.playSound(actionbarsound);
        }
        return true;
    }
}
