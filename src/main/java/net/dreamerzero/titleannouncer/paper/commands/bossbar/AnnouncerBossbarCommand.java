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
import net.dreamerzero.titleannouncer.paper.Announcer;
import net.dreamerzero.titleannouncer.paper.utils.PaperBossBar;
import net.dreamerzero.titleannouncer.paper.utils.PPlaceholders;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class AnnouncerBossbarCommand implements CommandExecutor {
    private Announcer plugin;
    private MiniMessage mm;
    private Audience audience;
    public AnnouncerBossbarCommand(Announcer plugin, MiniMessage mm){
        this.plugin = plugin;
        this.mm = mm;
        audience = Bukkit.getServer();
    }

    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // The command requires arguments to work
        BossBarUtils bUtils = new BossBarUtils(mm);
        if (!bUtils.regularBossbarArgs(args.length, sender)) {
            return false;
        }

        // Concatenate the arguments provided by the command sent.
        String bossbartext = GeneralUtils.getCommandString(args, 3);

        float time = bUtils.validBossbarNumber(args[0], sender);
        if(time == 0.1f) return false;

        BossBar.Color color = bUtils.bossbarColor(args[1]);
        BossBar.Overlay overlay = bUtils.bossbarOverlay(args[2]);

        PaperBossBar pBossBar = new PaperBossBar(plugin);

        if (color == null || overlay == null) {
            sender.sendMessage(ConfigUtils.getPrefix().append(Component.text("Invalid Argument", NamedTextColor.DARK_RED)));
            return false;
        }

        boolean placeholderAPISupport = Announcer.placeholderAPIHook();

        // Send to all
        if (sender instanceof Player player) {
            pBossBar.sendBukkitBossBar(
                audience,
                time,
                mm.deserialize(MiniMessageUtil.replaceLegacy(
                    placeholderAPISupport ? PlaceholderAPI.setPlaceholders(player, bossbartext) : bossbartext), 
                    PPlaceholders.replacePlaceholders(player)),
                color,
                overlay);
            ConfigUtils.sendConfirmation(ComponentType.BOSSBAR, sender);
            ConfigUtils.playPaperSound(ComponentType.BOSSBAR, audience);
            return true;
        } else {
            pBossBar.sendBukkitBossBar(
                audience,
                time,
                mm.deserialize(
                    MiniMessageUtil.replaceLegacy(
                        placeholderAPISupport ? PlaceholderAPI.setPlaceholders(null, bossbartext) : bossbartext),
                        PPlaceholders.replacePlaceholders()),
                color,
                overlay);
            ConfigUtils.sendConfirmation(ComponentType.BOSSBAR, sender);
            ConfigUtils.playPaperSound(ComponentType.BOSSBAR, audience);
            return true;
        }
    }
}
