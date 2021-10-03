package net.dreamerzero.titleannouncer.paper.commands.bossbar;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dreamerzero.titleannouncer.common.utils.BossBarUtils;
import net.dreamerzero.titleannouncer.common.utils.ComponentType;
import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.common.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.common.utils.PlaceholderUtil;
import net.dreamerzero.titleannouncer.paper.Announcer;
import net.dreamerzero.titleannouncer.paper.utils.PaperBossBar;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class AnnouncerBossbarCommand implements CommandExecutor {
    private Announcer plugin;
    public AnnouncerBossbarCommand(Announcer plugin) {
        this.plugin = plugin;
    }

    // The audience that will receive the bossbar will be all the players on the server.
    Audience audience = Bukkit.getServer();

    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // The command requires arguments to work
        BossBarUtils bUtils = new BossBarUtils();
        if (!bUtils.regularBossbarArgs(args.length, sender)) {
            return false;
        }

        // Concatenate the arguments provided by the command sent.
        String bossbartext = new GeneralUtils().getCommandString(args, 3);

        float time = bUtils.validBossbarNumber(args[0], sender);
        if(time == 0.1f) return false;

        BossBar.Color color = bUtils.bossbarColor(args[1]);
        BossBar.Overlay overlay = bUtils.bossbarOverlay(args[2]);

        ConfigUtils config = new ConfigUtils();
        MiniMessageUtil mUtils = new MiniMessageUtil();
        PaperBossBar pBossBar = new PaperBossBar(plugin);

        if (color == null || overlay == null) {
            sender.sendMessage(config.getPrefix().append(Component.text("Invalid Argument", NamedTextColor.DARK_RED)));
            return false;
        }

        boolean placeholderAPISupport = PlaceholderUtil.placeholderAPIHook();

        // Send to all
        if (sender instanceof Player player) {
            pBossBar.sendBukkitBossBar(
                audience,
                time,
                mUtils.parse(mUtils.replaceLegacy(
                    placeholderAPISupport ? PlaceholderAPI.setPlaceholders(player, bossbartext) : bossbartext), 
                    PlaceholderUtil.replacePlaceholders(player)),
                color,
                overlay);
            config.sendConfirmation(ComponentType.BOSSBAR, sender);
            config.playPaperSound(ComponentType.BOSSBAR, audience);
            return true;
        } else {
            pBossBar.sendBukkitBossBar(
                audience,
                time,
                mUtils.parse(
                    mUtils.replaceLegacy(
                        placeholderAPISupport ? PlaceholderAPI.setPlaceholders(null, bossbartext) : bossbartext),
                        PlaceholderUtil.replacePlaceholders()),
                color,
                overlay);
            config.sendConfirmation(ComponentType.BOSSBAR, sender);
            config.playPaperSound(ComponentType.BOSSBAR, audience);
            return true;
        }
    }
}
