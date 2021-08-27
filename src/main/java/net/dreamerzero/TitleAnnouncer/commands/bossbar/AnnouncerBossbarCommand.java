package net.dreamerzero.TitleAnnouncer.commands.bossbar;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dreamerzero.TitleAnnouncer.Announcer;
import net.dreamerzero.TitleAnnouncer.utils.SoundUtil;
import net.dreamerzero.TitleAnnouncer.utils.BossBarUtils;
import net.dreamerzero.TitleAnnouncer.utils.MiniMessageUtil;
import static net.dreamerzero.TitleAnnouncer.utils.PlaceholderUtil.replacePlaceholders;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class AnnouncerBossbarCommand implements CommandExecutor {
    private final Announcer plugin;
	public AnnouncerBossbarCommand(Announcer plugin) {
		this.plugin = plugin;
	}

    // The audience that will receive the bossbar will be all the players on the server.
    private Audience audience = Bukkit.getServer();

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
        if (!(sender.hasPermission("announcer.bossbar.global"))) {
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
        } else if (args.length == 1) {
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.bossbar.only-time",
                        "<gray>You must enter the color and the message arguments.</gray>"))));
            return false;
        // The command requires message arguments to work properly.
        } else if (args.length == 2) {
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.bossbar.single-argument",
                        "<gray>You need to enter the message to announce.</gray>"))));
            return false;
        }

        // Concatenate the arguments provided by the command sent.
        StringBuilder bossbartext = new StringBuilder();
        for (byte i = 3; i < args.length; i++) {
            bossbartext = bossbartext.append(" ");
            bossbartext = bossbartext.append(args[i]); 
        }

        // Convert StringBuilder to String, Component is not compatible :nimodo:
        final var bossbarToParse = bossbartext.toString();
        int time;
        try {
            time = Integer.parseInt(args[0]);
        } catch (Exception e){
            sender.sendMessage(Component.text("This is not a number", NamedTextColor.DARK_RED));
            return false;
        }

        BossBar.Color color;
        BossBar.Overlay overlay;

        switch (args[1]){
            case "RED": color = BossBar.Color.RED; break;
            case "BLUE": color = BossBar.Color.BLUE; break;
            case "GREEN": color = BossBar.Color.GREEN; break;
            case "PINK": color = BossBar.Color.PINK; break;
            case "PURPLE": color = BossBar.Color.PURPLE; break;
            case "WHITE": color = BossBar.Color.WHITE; break;
            case "YELLOW": color = BossBar.Color.YELLOW; break;
            default: sender.sendMessage(Component.text("Invalid Color Argument", NamedTextColor.DARK_RED)); return false;
        }
        switch (args[2]){
            case "6": overlay = BossBar.Overlay.NOTCHED_6; break;
            case "10": overlay = BossBar.Overlay.NOTCHED_10; break;
            case "12": overlay = BossBar.Overlay.NOTCHED_12; break;
            case "20": overlay = BossBar.Overlay.NOTCHED_20; break;
            case "full": case "progress": overlay = BossBar.Overlay.PROGRESS; break;
            default: sender.sendMessage(Component.text("Invalid Argument", NamedTextColor.DARK_RED)); return false;
        }
          
        // Send to all
        if (sender instanceof Player) {
            Player player = (Player) sender;
            BossBarUtils.sendBossBar(
                audience,
                time,
                MiniMessageUtil.parse(bossbarToParse, replacePlaceholders(player)),
                color,
                overlay);
        } else {
            BossBarUtils.sendBossBar(
                audience,
                time,
                MiniMessageUtil.parse(bossbarToParse, replacePlaceholders()),
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
                audience, 
                volume, 
                pitch
            );
        }
        return true;
    }
}
