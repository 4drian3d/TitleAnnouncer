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
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.util.TriState;

public class SendBossbarCommand implements CommandExecutor{
    public SendBossbarCommand() {}

    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Permission Check
        if (sender.permissionValue("titleannouncer.bossbar.send") != TriState.TRUE) {
            ConfigUtils.sendNoMainPermission(sender);
            return true;
        }

        // The command requires arguments to work
        if (!BossBarUtils.sendBossbarArgs(args.length, sender)) {
            return false;
        }

        Player playerObjetive = Bukkit.getPlayer(args[0]);

        //Collection of all players in the server
        var serverplayers = Bukkit.getOnlinePlayers();

        if (!serverplayers.contains(playerObjetive)) {
            // Send an error message to the sender using the command.
            ConfigUtils.playerNotFoundMessage(sender);
            return false;
        }

        float time;
        if(BossBarUtils.validBossbarNumber(args[1], sender) == 0.1f){
            return false;
        } else {
            time = BossBarUtils.validBossbarNumber(args[1], sender);
        }

        BossBar.Color color = BossBarUtils.bossbarColor(args[2]);
        BossBar.Overlay overlay = BossBarUtils.bossbarOverlay(args[3]);

        if (color == null || overlay == null) {
            sender.sendMessage(ConfigUtils.getPrefix().append(Component.text("Invalid Argument", NamedTextColor.DARK_RED)));
            return false;
        }

        boolean placeholderAPISupport = PlaceholderUtil.placeholderAPIHook();

        // Concatenate the arguments provided by the command sent.
        String bossbartext = GeneralUtils.getCommandString(args, 5);

        if (sender instanceof Player player) {
            PaperBossBar.sendBukkitBossBar(
                playerObjetive,
                time,
                MiniMessageUtil.parse(
                    MiniMessageUtil.replaceLegacy(
                        placeholderAPISupport ? PlaceholderAPI.setPlaceholders(playerObjetive, bossbartext) : bossbartext),
                        PlaceholderUtil.replacePlaceholders(playerObjetive)),
                color,
                overlay);
            ConfigUtils.sendBossbarConfirmation(sender);
            ConfigUtils.playBossbarSound(playerObjetive);
            return true;
        } else {
            PaperBossBar.sendBukkitBossBar(
                playerObjetive,
                time,
                MiniMessageUtil.parse(
                    MiniMessageUtil.replaceLegacy(
                        placeholderAPISupport ? PlaceholderAPI.setPlaceholders(null, bossbartext) : bossbartext),
                        PlaceholderUtil.replacePlaceholders(playerObjetive)),
                color,
                overlay);
            ConfigUtils.sendBossbarConfirmation(sender);
            ConfigUtils.playBossbarSound(playerObjetive);
            return true;
        }
    }
}
