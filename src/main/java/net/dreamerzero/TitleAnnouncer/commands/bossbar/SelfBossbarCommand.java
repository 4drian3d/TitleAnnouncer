package net.dreamerzero.titleannouncer.commands.bossbar;

import static net.dreamerzero.titleannouncer.utils.PlaceholderUtil.replacePlaceholders;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dreamerzero.titleannouncer.Announcer;
import net.dreamerzero.titleannouncer.utils.BossBarUtils;
import net.dreamerzero.titleannouncer.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.utils.PlaceholderUtil;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.util.TriState;

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
        // Permission Check
        if (sender.permissionValue("announcer.bossbar.self") != TriState.TRUE) {
            ConfigUtils.sendNoBossbarPermission(sender);
            return true;
        }

        // The command requires arguments to work
        if (!BossBarUtils.regularBossbarArgs(args.length, sender)) {
            return false;
        }

        // Concatenate the arguments provided by the command sent.
        String bossbartext = GeneralUtils.getCommandString(args, 4);

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

        if(PlaceholderUtil.placeholderAPIHook()){
            BossBarUtils.sendBukkitBossBar(
                player,
                time,
                MiniMessageUtil.parse(
                    MiniMessageUtil.replaceLegacy(
                        PlaceholderAPI.setPlaceholders(player, bossbartext)), replacePlaceholders(player)),
                color,
                overlay);
            ConfigUtils.playBossbarSound(sender);
            ConfigUtils.sendBossbarConfirmation(sender);
            return true;
        } else {
            BossBarUtils.sendBukkitBossBar(
                player,
                time,
                MiniMessageUtil.parse(
                    MiniMessageUtil.replaceLegacy(bossbartext), replacePlaceholders(player)),
                color,
                overlay);
            ConfigUtils.playBossbarSound(sender);
            ConfigUtils.sendBossbarConfirmation(sender);
            return true;
        }
    }
}
