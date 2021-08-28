package net.dreamerzero.TitleAnnouncer.commands.bossbar;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dreamerzero.TitleAnnouncer.Announcer;
import net.dreamerzero.TitleAnnouncer.utils.BossBarUtils;
import net.dreamerzero.TitleAnnouncer.utils.MiniMessageUtil;
import net.dreamerzero.TitleAnnouncer.utils.SoundUtil;

import static net.dreamerzero.TitleAnnouncer.utils.PlaceholderUtil.replacePlaceholders;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class SendBossbarCommand implements CommandExecutor{
    private final Announcer plugin;
	public SendBossbarCommand(Announcer plugin) {
		this.plugin = plugin;
	}  
        // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        var enabledPrefix = plugin.getConfig().getBoolean("messages.prefix.enabled", true);
        Component prefix = Component.text("");

        if (enabledPrefix) {
            prefix = MiniMessageUtil.parse(plugin.getConfig().getString(
                "messages.prefix.line", 
                "<gray>[</gray><gradient:yellow:blue>TitleAnnouncer</gradient><gray>]</gray> "));
        }
        // Permission Check
        if (!(sender.hasPermission("announcer.bossbar.send"))) {
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.bossbar.no-permission", 
                        "<red>You do not have permission to execute this command</red>"))));
            return true;
        }

        // The command requires arguments to work
        if (args.length == 0) {
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.bossbar.without-argument",
                        "<red>You need to enter the time, color and message arguments.</red>"))));
            return false;
        // The command requires time argument to work.
        } else if(args.length == 1){
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.bossbar.only-player", 
                        "<gray>You must enter the message to be sent after the player's name.</gray>"))));
            return false;
        } else if (args.length == 2) {
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.bossbar.only-time",
                        "<gray>You must enter the color, overlay and the message arguments.</gray>"))));
            return false;
        // The command requires message arguments to work properly.
        } else if (args.length == 3) {
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.bossbar.overlay-missing",
                        "<gray>You must enter the overlay and the message arguments.</gray>"))));
            return false;
        } else if(args.length == 4){
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.bossbar.without-message",
                        "<gray>You need to enter the message to announce.</gray>"))));
            return false;
        }

        final Player playerObjetive = Bukkit.getPlayer(args[0]);

        //Collection of all players in the server
        final var serverplayers = Bukkit.getOnlinePlayers();

        if (!(serverplayers.contains(playerObjetive))) {
            // Send an error message to the sender using the command.
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.bossbar.player-not-found",
                        "<red>Player not found</red>"))));
                return false;
        }

        // Concatenate the arguments provided by the command sent.
        StringBuilder bossbartext = new StringBuilder();
        for (byte i = 5; i < args.length; i++) {
            bossbartext = bossbartext.append(" ");
            bossbartext = bossbartext.append(args[i]); 
        }

        // Convert StringBuilder to String, Component is not compatible :nimodo:
        final var bossbarToParse = bossbartext.toString();
        int time;
        try {
            time = Integer.parseInt(args[1]);
        } catch (Exception e){
            sender.sendMessage(prefix.append(Component.text("This is not a valid number", NamedTextColor.DARK_RED)));
            return false;
        }

        BossBar.Color color;
        BossBar.Overlay overlay;

        switch (args[2]){
            case "RED": case "red": color = BossBar.Color.RED; break;
            case "BLUE": case "blue": color = BossBar.Color.BLUE; break;
            case "GREEN": case "green": color = BossBar.Color.GREEN; break;
            case "PINK": case "pink": color = BossBar.Color.PINK; break;
            case "PURPLE": case "purple": color = BossBar.Color.PURPLE; break;
            case "WHITE": case "white": color = BossBar.Color.WHITE; break;
            case "YELLOW": case "yellow": color = BossBar.Color.YELLOW; break;
            default: sender.sendMessage(prefix.append(Component.text("Invalid Color Argument", NamedTextColor.DARK_RED))); return false;
        }
        switch (args[3]){
            case "6": overlay = BossBar.Overlay.NOTCHED_6; break;
            case "10": overlay = BossBar.Overlay.NOTCHED_10; break;
            case "12": overlay = BossBar.Overlay.NOTCHED_12; break;
            case "20": overlay = BossBar.Overlay.NOTCHED_20; break;
            case "full": case "progress": overlay = BossBar.Overlay.PROGRESS; break;
            default: sender.sendMessage(prefix.append(Component.text("Invalid Argument", NamedTextColor.DARK_RED))); return false;
        }
          
        // Send to all
        if (sender instanceof Player) {
            Player player = (Player) sender;
            BossBarUtils.sendBossBar(
                playerObjetive,
                time,
                MiniMessageUtil.parse(bossbarToParse, replacePlaceholders(player, playerObjetive)),
                color,
                overlay);
        } else {
            BossBarUtils.sendBossBar(
                playerObjetive,
                time,
                MiniMessageUtil.parse(bossbarToParse, replacePlaceholders(playerObjetive)),
                color,
                overlay);
        }

        sender.sendMessage(
            prefix.append(MiniMessageUtil.parse(
                plugin.getConfig().getString(
                    "messages.bossbar.successfully",  
                    "<green>Bossbar succesfully sended</green>"))));

        var soundToPlay = plugin.getConfig().getString(
            "sounds.bossbar.sound-id", 
            "entity.experience_orb.pickup");
        var soundEnabled = plugin.getConfig().getBoolean("sounds.bossbar.enabled", true);
        float volume = plugin.getConfig().getInt("sounds.bossbar.volume", 10);
        float pitch = plugin.getConfig().getInt("sounds.bossbar.pitch", 2);

        if (soundEnabled) {
            // Play the sound
            SoundUtil.playSound(
                soundToPlay, 
                playerObjetive, 
                volume, 
                pitch
            );
        }
        return true;
    }
}