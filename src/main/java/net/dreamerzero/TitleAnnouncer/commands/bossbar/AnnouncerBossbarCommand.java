package net.dreamerzero.titleannouncer.commands.bossbar;

import static net.dreamerzero.titleannouncer.utils.PlaceholderUtil.replacePlaceholders;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dreamerzero.titleannouncer.utils.BossBarUtils;
import net.dreamerzero.titleannouncer.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.utils.PlaceholderUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.util.TriState;

public class AnnouncerBossbarCommand implements CommandExecutor {
    public AnnouncerBossbarCommand() {}

    // The audience that will receive the bossbar will be all the players on the server.
    Audience audience = Bukkit.getServer();

    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Permission Check
        if (sender.permissionValue("announcer.bossbar.global") != TriState.TRUE) {
            ConfigUtils.sendNoBossbarPermission(sender);
            return true;
        }

        // The command requires arguments to work
        if (!BossBarUtils.regularBossbarArgs(args.length, sender)) {
            return false;
        }

        // Concatenate the arguments provided by the command sent.
        String bossbartext = GeneralUtils.getCommandString(args, 3);

        float time;
        if(BossBarUtils.validBossbarNumber(args[0], sender) == 0.1f){
            return false;
        } else {
            time = BossBarUtils.validBossbarNumber(args[0], sender);
        }

        BossBar.Color color;
        BossBar.Overlay overlay;

        color = BossBarUtils.bossbarColor(args[1]);
        overlay = BossBarUtils.bossbarOverlay(args[2]);

        if (color == null || overlay == null) {
            sender.sendMessage(ConfigUtils.getPrefix().append(Component.text("Invalid Argument", NamedTextColor.DARK_RED)));
            return false;
        }
        String announceToSend;
        if(PlaceholderUtil.placeholderAPIHook()){
            // Send to all
            if (sender instanceof Player player) {
                announceToSend = MiniMessageUtil.replaceLegacy(PlaceholderAPI.setPlaceholders(player, bossbartext));
                BossBarUtils.sendBukkitBossBar(
                audience,
                    time,
                    MiniMessageUtil.parse(announceToSend, replacePlaceholders(player)),
                    color,
                    overlay);
                ConfigUtils.sendBossbarConfirmation(sender);
                ConfigUtils.playBossbarSound(audience);
                return true;
            } else {
                announceToSend = MiniMessageUtil.replaceLegacy(PlaceholderAPI.setPlaceholders(null, bossbartext));
                BossBarUtils.sendBukkitBossBar(
                    audience,
                    time,
                    MiniMessageUtil.parse(announceToSend, replacePlaceholders()),
                    color,
                    overlay);
                ConfigUtils.sendBossbarConfirmation(sender);
                ConfigUtils.playBossbarSound(audience);
                return true;
            }
        } else {
            // Send to all
            if (sender instanceof Player player) {
                BossBarUtils.sendBukkitBossBar(
                audience,
                    time,
                    MiniMessageUtil.parse(bossbartext, replacePlaceholders(player)),
                    color,
                    overlay);
                ConfigUtils.sendBossbarConfirmation(sender);
                ConfigUtils.playBossbarSound(audience);
                return true;
            } else {
                BossBarUtils.sendBukkitBossBar(
                    audience,
                    time,
                    MiniMessageUtil.parse(bossbartext, replacePlaceholders()),
                    color,
                    overlay);
                ConfigUtils.sendBossbarConfirmation(sender);
                ConfigUtils.playBossbarSound(audience);
                return true;
            }
        }
    }
}
