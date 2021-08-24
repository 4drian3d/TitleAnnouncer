package net.dreamerzero.TitleAnnouncer.commands.actionbar;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.dreamerzero.TitleAnnouncer.Announcer;
import net.dreamerzero.TitleAnnouncer.utils.MiniMessageUtil;
import net.dreamerzero.TitleAnnouncer.utils.SoundUtil;
import net.kyori.adventure.text.Component;

public class SelfActionbarCommand implements CommandExecutor {
    private final Announcer plugin;
    private final FileConfiguration config;
	public SelfActionbarCommand(Announcer plugin) {
		this.plugin = plugin;
        this.config = plugin.getConfig();
	}

    //Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // It will send an actionbar to the one who executes the command, 
        // it makes no sense for the console to execute it.
        if (!(sender instanceof Player)) {
            plugin.getLogger().info("The console cannot execute this command.");
            return false;
        }

        Boolean enabledPrefix = config.getBoolean("messages.prefix.enabled", true);
        Component prefix = Component.text("");

        if (enabledPrefix){
            prefix = MiniMessageUtil.parse(config.getString(
                "messages.prefix.line", 
                "<gray>[</gray><gradient:yellow:blue>TitleAnnouncer</gradient><gray>]</gray>"));
        }

        // Permission Check
        if (!(sender.hasPermission("announcer.actionbar.test"))){
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    config.getString(
                        "messages.actionbar.no-permission", 
                        "<red>You do not have permission to execute this command</red>"))));
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
        
        // Send to sender
        sender.sendActionBar(
            MiniMessageUtil.parse(actionbarToParse));
        sender.sendMessage(
            prefix.append(MiniMessageUtil.parse(
                config.getString("messages.actionbar.successfully"))));

        String soundToPlay = config.getString(
            "sounds.actionbar.sound-id", 
            "entity.experience_orb.pickup");
        boolean soundEnabled = config.getBoolean("sounds.actionbar.enabled", true);
        float volume = config.getInt("sounds.actionbar.volume", 10);
        float pitch = config.getInt("sounds.actionbar.pitch", 2);

        if (soundEnabled) {
            // Play the sound
            SoundUtil.playSound(
                soundToPlay, 
                sender, 
                volume, 
                pitch
            );
        }
        return true;
    }
}
