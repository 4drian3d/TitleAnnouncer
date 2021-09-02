package net.dreamerzero.TitleAnnouncer.commands.title;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dreamerzero.TitleAnnouncer.Announcer;
import net.dreamerzero.TitleAnnouncer.utils.MiniMessageUtil;
import net.dreamerzero.TitleAnnouncer.utils.SoundUtil;
import net.dreamerzero.TitleAnnouncer.utils.TitleUtil;
import static net.dreamerzero.TitleAnnouncer.utils.PlaceholderUtil.replacePlaceholders;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

public class WorldTitleCommand implements CommandExecutor {
    private final Announcer plugin;
	public WorldTitleCommand(Announcer plugin) {
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

        // Player
        final var player = (Player) sender;

        var enabledPrefix = plugin.getConfig().getBoolean("messages.prefix.enabled", true);
        Component prefix = Component.text("");

        if (enabledPrefix) {
            prefix = MiniMessageUtil.parse(plugin.getConfig().getString(
                "messages.prefix.line", 
                "<gray>[</gray><gradient:yellow:blue>TitleAnnouncer</gradient><gray>]</gray> "));
        }
        
        // Permission Check
        if (!player.hasPermission("announcer.title.world")) {
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.title.no-permission", 
                        "<red>You do not have permission to execute this command</red>"))));
            return true;
        }

        // Get the world in which the player is located.
        final Audience audience = player.getWorld();
        
        // The command requires arguments to work
        if (args.length == 0) {
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.title.without-argument", 
                        "<red>You need to enter the title and subtitle arguments.</red>"))));
            return true;
        // The command requires title and subtitle arguments to work properly.
        } else if (args.length == 1) {
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.title.single-argument", 
                        "<gray>You need to enter the title, the subtitle and the separator ';' in orden to send the title.</gray>"))));
            return true;
        }

        // Concatenate the arguments provided by the command sent.
        var titleandsubtitle = new StringBuilder();
        for (byte i = 0; i < args.length; i++) {
            titleandsubtitle = titleandsubtitle.append(" ");
            titleandsubtitle = titleandsubtitle.append(args[i]); 
        }
        
        var soundToPlay = plugin.getConfig().getString(
            "sounds.title.sound-id", 
            "entity.experience_orb.pickup");
        var soundEnabled = plugin.getConfig().getBoolean("sounds.title.enabled", true);
        float volume = plugin.getConfig().getInt("sounds.title.volume", 10);
        float pitch = plugin.getConfig().getInt("sounds.title.pitch", 10);

        try {
            // Convert StringBuilder to String, Component is not compatible :nimodo:
            final String titleandsubtitlefinal[] = titleandsubtitle.toString().split(";");
            
            TitleUtil.sendTitle(
                MiniMessageUtil.parse(titleandsubtitlefinal[0], replacePlaceholders(player)), 
                MiniMessageUtil.parse(titleandsubtitlefinal[1], replacePlaceholders(player)),
                audience, 
                1000, 
                3000, 
                1000);
            
            // Send message to the sender
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.title.successfully",
                        "<green>Title succesfully sended</green>"))));
            
            if (soundEnabled) {
                //Play the sound
                SoundUtil.playSound(
                    soundToPlay, 
                    audience, 
                    volume, 
                    pitch);
            }

            return true;
        // In case the command does not contain a separator ";", 
        // it will catch the error in the console and send an error message to the sender.
        } catch (Exception e) {
            // Send an error message to the sender using the command
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.title.error",
                        "<dark_red>An error occurred while sending the title. Be sure to use the ';' to separate the title and the subtitle.</dark_red>"))));
            return false;
        }
    }
}
