package net.dreamerzero.TitleAnnouncer.commands.actionbar;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.dreamerzero.TitleAnnouncer.Announcer;
import net.dreamerzero.TitleAnnouncer.utils.MiniMessageUtil;
import net.dreamerzero.TitleAnnouncer.utils.SoundUtil;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

public class AnnouncerActionbarCommand implements CommandExecutor {
    private final Announcer plugin;
	public AnnouncerActionbarCommand(Announcer plugin) {
		this.plugin = plugin;
	}

    // The audience that will receive the actionbar will be all the players on the server.
    private Audience audience = Bukkit.getServer();

    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        var enabledPrefix = plugin.getConfig().getBoolean("messages.prefix.enabled", true);
        Component prefix = Component.text("");

        if (enabledPrefix) {
            prefix = MiniMessageUtil.parse(plugin.getConfig().getString(
                "messages.prefix.line", 
                "<gray>[</gray><gradient:yellow:blue>TitleAnnouncer</gradient><gray>]</gray>"));
        }
        // Permission Check
        if (!(sender.hasPermission("announcer.actionbar.global"))) {
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
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
        var actionbarToParse = actionbartext.toString();
        
        // Send to all
        audience.sendActionBar(
            MiniMessageUtil.parse(actionbarToParse));
        sender.sendMessage(
            prefix.append(MiniMessageUtil.parse(
                plugin.getConfig().getString(
                    "messages.actionbar.successfully",  
                    "<green>Actionbar succesfully sended</green>"))));

        var soundToPlay = plugin.getConfig().getString(
            "sounds.actionbar.sound-id", 
            "entity.experience_orb.pickup");
        var soundEnabled = plugin.getConfig().getBoolean("sounds.actionbar.enabled", true);
        float volume = plugin.getConfig().getInt("sounds.actionbar.volume", 10);
        float pitch = plugin.getConfig().getInt("sounds.actionbar.pitch", 2);

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
