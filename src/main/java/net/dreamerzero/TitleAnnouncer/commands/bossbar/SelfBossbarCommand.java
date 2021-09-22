package net.dreamerzero.titleannouncer.commands.bossbar;

import static net.dreamerzero.titleannouncer.utils.PlaceholderUtil.replacePlaceholders;

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

public class SelfBossbarCommand implements CommandExecutor {
    private final Announcer plugin;
    public SelfBossbarCommand(Announcer plugin) {
        this.plugin = plugin;
    }

    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // It will send an actionbar to the one who executes the command, 
        // it makes no sense for the console to execute it.
        if (!(sender instanceof Player player)) {
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
        if (!sender.hasPermission("announcer.bossbar.self")) {
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.bossbar.no-permission",
                        "<red>You do not have permission to execute this command</red>"))));
            return true;
        }

        // The command requires arguments to work
        if (!BossBarUtils.regularBossbarArgs(args.length, sender, prefix)) {
            return false;
        }

        // Concatenate the arguments provided by the command sent.
        StringBuilder bossbartext = new StringBuilder();
        for (byte i = 4; i < args.length; i++) {
            bossbartext = bossbartext.append(" ");
            bossbartext = bossbartext.append(args[i]);
        }

        // Convert StringBuilder to String, Component is not compatible :nimodo:
        var bossbarToParse = bossbartext.toString();

        int time;
        try {
            time = Integer.parseInt(args[0]);
        } catch (Exception e){
            sender.sendMessage(prefix.append(Component.text("This is not a valid number", NamedTextColor.DARK_RED)));
            return false;
        }

        BossBar.Color color;
        BossBar.Overlay overlay;

        color = BossBarUtils.bossbarColor(args[1]);
        overlay = BossBarUtils.bossbarOverlay(args[2]);

        if (color == null || overlay == null) {
            sender.sendMessage(prefix.append(Component.text("Invalid Argument", NamedTextColor.DARK_RED)));
            return false;
        }

        if(PlaceholderUtil.placeholderAPIHook()){
            String announceToSend = MiniMessageUtil.replaceLegacy(PlaceholderAPI.setPlaceholders(player, bossbarToParse));
            BossBarUtils.sendBukkitBossBar(
                player,
                time,
                MiniMessageUtil.parse(announceToSend, replacePlaceholders(player)),
                color,
                overlay);
        } else {
            BossBarUtils.sendBukkitBossBar(
                player,
                time,
                MiniMessageUtil.parse(bossbarToParse, replacePlaceholders(player)),
                color,
                overlay);
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
                sender,
                volume,
                pitch
            );
        }
        return true;
    }
}
