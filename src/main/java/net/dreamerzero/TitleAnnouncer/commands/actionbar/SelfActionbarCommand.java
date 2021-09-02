package net.dreamerzero.TitleAnnouncer.commands.actionbar;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dreamerzero.TitleAnnouncer.Announcer;
import net.dreamerzero.TitleAnnouncer.utils.MiniMessageUtil;
import net.dreamerzero.TitleAnnouncer.utils.SoundUtil;
import static net.dreamerzero.TitleAnnouncer.utils.PlaceholderUtil.replacePlaceholders;
import net.kyori.adventure.text.Component;

public class SelfActionbarCommand implements CommandExecutor {
    private final Announcer plugin;
	public SelfActionbarCommand(Announcer plugin) {
		this.plugin = plugin;
	}

    //Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // It will send an actionbar to the one who executes the command, 
        // it makes no sense for the console to execute it.
        if (!(sender instanceof Player)) {
            plugin.getLogger().info("The console cannot execute this command.");
            return false;
        }

        var enabledPrefix = plugin.getConfig().getBoolean("messages.prefix.enabled", true);
        Component prefix = Component.text("");

        if (enabledPrefix) {
            prefix = MiniMessageUtil.parse(plugin.getConfig().getString(
                "messages.prefix.line", 
                "<gray>[</gray><gradient:yellow:blue>TitleAnnouncer</gradient><gray>]</gray> "));
        }

        // Permission Check
        if (!sender.hasPermission("announcer.actionbar.test")) {
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.actionbar.no-permission", 
                        "<red>You do not have permission to execute this command</red>"))));
            return true;
        }

        // Concatenate the arguments provided by the command sent.
        var actionbartext = new StringBuilder();
        for (byte i = 0; i < args.length; i++) {
            actionbartext = actionbartext.append(" ");
            actionbartext = actionbartext.append(args[i]); 
        }
        
        // Convert StringBuilder to String, Component is not compatible :nimodo:
        final var actionbarToParse = actionbartext.toString();
        
        Player player = (Player)sender;
        // Send to sender
        sender.sendActionBar(
            MiniMessageUtil.parse(actionbarToParse, replacePlaceholders(player)));
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
                sender, 
                volume, 
                pitch
            );
        }
        return true;
    }
}
