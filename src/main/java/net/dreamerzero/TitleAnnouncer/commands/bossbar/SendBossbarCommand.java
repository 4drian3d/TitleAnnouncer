package net.dreamerzero.titleannouncer.commands.bossbar;

import static net.dreamerzero.titleannouncer.utils.PlaceholderUtil.replacePlaceholders;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dreamerzero.titleannouncer.Announcer;
import net.dreamerzero.titleannouncer.utils.BossBarUtils;
import net.dreamerzero.titleannouncer.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.utils.PlaceholderUtil;
import net.dreamerzero.titleannouncer.utils.SoundUtil;
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
        if (!sender.hasPermission("announcer.bossbar.send")) {
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.bossbar.no-permission",
                        "<red>You do not have permission to execute this command</red>"))));
            return true;
        }

        // The command requires arguments to work
        // It is not necessary to include this in BossbarUtils as it will only be used once.
        switch (args.length) {
            case 0 -> {
                sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.bossbar.without-argument",
                        "<red>You need to enter the time, color and message arguments.</red>"))));
                return false;
            }
            case 1 -> {
                sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.bossbar.only-player",
                        "<gray>You must enter the message to be sent after the player's name.</gray>"))));
                return false;
            }
            case 2 -> {
                sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.bossbar.only-time",
                        "<gray>You must enter the color, overlay and the message arguments.</gray>"))));
                return false;
            }
            case 3 -> {
                sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.bossbar.overlay-missing",
                        "<gray>You must enter the overlay and the message arguments.</gray>"))));
            return false;
            }
            case 4 -> {
                sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.bossbar.without-message",
                        "<gray>You need to enter the message to announce.</gray>"))));
                return false;
            }
        }

        Player playerObjetive = Bukkit.getPlayer(args[0]);

        //Collection of all players in the server
        final var serverplayers = Bukkit.getOnlinePlayers();

        if (!serverplayers.contains(playerObjetive)) {
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
        String bossbarToParse = bossbartext.toString();
        int time;
        try {
            time = Integer.parseInt(args[1]);
        } catch (Exception e){
            sender.sendMessage(prefix.append(Component.text("This is not a valid number", NamedTextColor.DARK_RED)));
            return false;
        }

        BossBar.Color color;
        BossBar.Overlay overlay;

        color = BossBarUtils.bossbarColor(args[2]);
        overlay = BossBarUtils.bossbarOverlay(args[3]);

        if (color == null || overlay == null) {
            sender.sendMessage(prefix.append(Component.text("Invalid Argument", NamedTextColor.DARK_RED)));
            return false;
        }
        String announceToSend;

        if(PlaceholderUtil.placeholderAPIHook()){
            if (sender instanceof Player player) {
                announceToSend = MiniMessageUtil.replaceLegacy(PlaceholderAPI.setPlaceholders(player, bossbarToParse));
                BossBarUtils.sendBukkitBossBar(
                    playerObjetive,
                    time,
                    MiniMessageUtil.parse(announceToSend, replacePlaceholders(player, playerObjetive)),
                    color,
                    overlay);
            } else {
                announceToSend = MiniMessageUtil.replaceLegacy(PlaceholderAPI.setPlaceholders(null, bossbarToParse));
                BossBarUtils.sendBukkitBossBar(
                    playerObjetive,
                    time,
                    MiniMessageUtil.parse(announceToSend, replacePlaceholders(playerObjetive)),
                    color,
                    overlay);
            }
        } else {
            if (sender instanceof Player player) {
                BossBarUtils.sendBukkitBossBar(
                    playerObjetive,
                    time,
                    MiniMessageUtil.parse(bossbarToParse, replacePlaceholders(player, playerObjetive)),
                    color,
                    overlay);
            } else {
                BossBarUtils.sendBukkitBossBar(
                    playerObjetive,
                    time,
                    MiniMessageUtil.parse(bossbarToParse, replacePlaceholders(playerObjetive)),
                    color,
                    overlay);
            }
        }

        sender.sendMessage(
            prefix.append(MiniMessageUtil.parse(
                plugin.getConfig().getString(
                    "messages.bossbar.successfully",
                    "<green>Bossbar succesfully sended</green>"))));

        String soundToPlay = plugin.getConfig().getString(
            "sounds.bossbar.sound-id",
            "entity.experience_orb.pickup");
        boolean soundEnabled = plugin.getConfig().getBoolean("sounds.bossbar.enabled", true);
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