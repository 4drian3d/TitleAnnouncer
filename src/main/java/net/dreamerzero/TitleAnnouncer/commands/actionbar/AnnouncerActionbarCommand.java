package net.dreamerzero.TitleAnnouncer.commands.actionbar;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.dreamerzero.TitleAnnouncer.Announcer;
import net.dreamerzero.TitleAnnouncer.utils.MiniMessageUtil;
import net.dreamerzero.TitleAnnouncer.utils.SoundUtil;

import net.kyori.adventure.audience.Audience;

public class AnnouncerActionbarCommand implements CommandExecutor {
    private Announcer plugin;
	public AnnouncerActionbarCommand(Announcer plugin) {
		this.plugin = plugin;
	}

    // The audience that will receive the actionbar will be all the players on the server.
    public Audience audience = Bukkit.getServer();

    // Default Sound
    String soundToPlay = "entity.experience_orb.pickup";
    // Is Enabled?
    Boolean soundEnabled = true;
    // Volume
    float volume = 10f;
    // Pitch
    float pitch = 2f;

    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Permission Check
        if (!(sender.hasPermission("announcer.actionbar.global"))){
            sender.sendMessage(
                MiniMessageUtil.parse(
                    plugin.getConfig().getString("messages.actionbar.no-permission")));
            return true;
        }

        // Concatenate the arguments provided by the command sent.
        StringBuilder actionbartext = new StringBuilder();
        for (byte i = 0; i < args.length; i++) {
            actionbartext = actionbartext.append(" ");
            actionbartext = actionbartext.append(args[i]); 
        }

        // Convert StringBuilder to String, Component is not compatible :nimodo:
        String actionbarToParse = actionbartext.toString();
        
        // Send to all
        audience.sendActionBar(
            MiniMessageUtil.parse(actionbarToParse));
        sender.sendMessage(
            MiniMessageUtil.parse(
                plugin.getConfig().getString("messages.actionbar.successfully")));

        soundToPlay = plugin.getConfig().getString("sounds.actionbar.sound-id");
        soundEnabled = plugin.getConfig().getBoolean("sounds.actionbar.enabled");
        volume = plugin.getConfig().getInt("sounds.actionbar.volume");
        pitch = plugin.getConfig().getInt("sounds.actionbar.pitch");

        if (soundEnabled) {
            // Play the sound
            SoundUtil.playSound(
                soundToPlay, 
                audience, 
                volume, 
                pitch
            );
        }
        return true;
    }
}
