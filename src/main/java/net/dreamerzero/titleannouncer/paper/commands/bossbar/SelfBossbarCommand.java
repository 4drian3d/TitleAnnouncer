package net.dreamerzero.titleannouncer.paper.commands.bossbar;

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

public class SelfBossbarCommand implements CommandExecutor {
    public SelfBossbarCommand() {}

    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // It will send an actionbar to the one who executes the command, 
        // it makes no sense for the console to execute it.
        if (!(sender instanceof Player player)) {
            ConfigUtils.onlyPlayerExecute(sender);
            return false;
        }
        // Permission Check
        if (sender.permissionValue("titleannouncer.bossbar.self") != TriState.TRUE) {
            ConfigUtils.sendNoMainPermission(sender);
            return true;
        }

        // The command requires arguments to work
        if (!BossBarUtils.regularBossbarArgs(args.length, sender)) {
            return false;
        }

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

        // Concatenate the arguments provided by the command sent.
        String bossbartext = GeneralUtils.getCommandString(args, 4);

        PaperBossBar.sendBukkitBossBar(
            player,
            time,
            MiniMessageUtil.parse(
                MiniMessageUtil.replaceLegacy(
                    PlaceholderUtil.placeholderAPIHook() ? PlaceholderAPI.setPlaceholders(player, bossbartext) : bossbartext), 
                    PlaceholderUtil.replacePlaceholders(player)),
            color,
            overlay);
        ConfigUtils.playBossbarSound(sender);
        ConfigUtils.sendBossbarConfirmation(sender);
        return true;
    }
}
