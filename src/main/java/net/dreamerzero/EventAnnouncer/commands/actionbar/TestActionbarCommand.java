package net.dreamerzero.EventAnnouncer.commands.actionbar;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dreamerzero.EventAnnouncer.Announcer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class TestActionbarCommand implements CommandExecutor {
    private Announcer plugin;
	public TestActionbarCommand(Announcer plugin) {
		this.plugin = plugin;
	}

    //Component that parses the title with the MiniMessage format.
    private static Component miniMessageParse(final String message) {
        return MiniMessage.get().parse(message);
    }

    //The audience that will receive the title will be all the players on the server.
    public Audience audience = Bukkit.getServer();

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
        return true;
    }
}
