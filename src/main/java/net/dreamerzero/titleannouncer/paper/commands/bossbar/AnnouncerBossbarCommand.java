package net.dreamerzero.titleannouncer.paper.commands.bossbar;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dreamerzero.titleannouncer.common.utils.BossBarUtils;
import net.dreamerzero.titleannouncer.common.utils.ComponentType;
import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.paper.Announcer;
import net.dreamerzero.titleannouncer.paper.utils.PaperBossBar;
import net.dreamerzero.titleannouncer.paper.utils.PaperPlaceholders;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class AnnouncerBossbarCommand implements CommandExecutor {
    private Announcer plugin;
    private Audience audience;
    private PaperPlaceholders placeholders;
    public AnnouncerBossbarCommand(Announcer plugin){
        this.plugin = plugin;
        audience = Bukkit.getServer();
        this.placeholders = new PaperPlaceholders();
    }

    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // The command requires arguments to work
        if (!BossBarUtils.regularBossbarArgs(args.length, sender)) {
            return false;
        }

        // Concatenate the arguments provided by the command sent.
        String bossbartext = GeneralUtils.getCommandString(args, 3);

        float time = BossBarUtils.validBossbarNumber(args[0], sender);
        if(time == 0.1f) return false;

        BossBar.Color color = BossBarUtils.bossbarColor(args[1]);
        BossBar.Overlay overlay = BossBarUtils.bossbarOverlay(args[2]);

        PaperBossBar pBossBar = new PaperBossBar(plugin);

        if (color == null || overlay == null) {
            sender.sendMessage(ConfigUtils.getPrefix().append(Component.text("Invalid Argument", NamedTextColor.DARK_RED)));
            return false;
        }

        // Send to all
        if (sender instanceof Player player) {
            pBossBar.sendBukkitBossBar(
                audience,
                time,
                placeholders.applyPlaceholders(bossbartext, player),
                color,
                overlay);
            ConfigUtils.sendConfirmation(ComponentType.BOSSBAR, sender);
            ConfigUtils.playPaperSound(ComponentType.BOSSBAR, audience);
            return true;
        } else {
            pBossBar.sendBukkitBossBar(
                audience,
                time,
                placeholders.applyPlaceholders(bossbartext),
                color,
                overlay);
            ConfigUtils.sendConfirmation(ComponentType.BOSSBAR, sender);
            ConfigUtils.playPaperSound(ComponentType.BOSSBAR, audience);
            return true;
        }
    }
}
