package net.dreamerzero.titleannouncer.paper.commands.bossbar;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dreamerzero.titleannouncer.common.utils.BossBarUtils;
import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.common.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.common.utils.PlaceholderUtil;
import net.dreamerzero.titleannouncer.paper.utils.PaperBossBar;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class AnnouncerBossbarCommand implements CommandExecutor {
    public AnnouncerBossbarCommand() {}

    // The audience that will receive the bossbar will be all the players on the server.
    Audience audience = Bukkit.getServer();

    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
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

        BossBar.Color color = BossBarUtils.bossbarColor(args[1]);
        BossBar.Overlay overlay = BossBarUtils.bossbarOverlay(args[2]);

        if (color == null || overlay == null) {
            sender.sendMessage(ConfigUtils.getPrefix().append(Component.text("Invalid Argument", NamedTextColor.DARK_RED)));
            return false;
        }

        boolean placeholderAPISupport = PlaceholderUtil.placeholderAPIHook();

        // Send to all
        if (sender instanceof Player player) {
            PaperBossBar.sendBukkitBossBar(
                audience,
                time,
                MiniMessageUtil.parse(
                    MiniMessageUtil.replaceLegacy(
                        placeholderAPISupport ? PlaceholderAPI.setPlaceholders(player, bossbartext) : bossbartext), 
                        PlaceholderUtil.replacePlaceholders(player)),
                color,
                overlay);
            ConfigUtils.sendBossbarConfirmation(sender);
            ConfigUtils.playBossbarSound(audience);
            return true;
        } else {
            PaperBossBar.sendBukkitBossBar(
                audience,
                time,
                MiniMessageUtil.parse(
                    MiniMessageUtil.replaceLegacy(
                        placeholderAPISupport ? PlaceholderAPI.setPlaceholders(null, bossbartext) : bossbartext),
                        PlaceholderUtil.replacePlaceholders()),
                color,
                overlay);
            ConfigUtils.sendBossbarConfirmation(sender);
            ConfigUtils.playBossbarSound(audience);
            return true;
        }
    }
}
