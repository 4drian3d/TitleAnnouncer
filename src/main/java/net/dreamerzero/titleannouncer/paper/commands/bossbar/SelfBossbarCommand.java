package net.dreamerzero.titleannouncer.paper.commands.bossbar;

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
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public record SelfBossbarCommand(Announcer plugin) implements CommandExecutor {

    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ConfigUtils config = new ConfigUtils();
        // It will send an actionbar to the one who executes the command,
        // it makes no sense for the console to execute it.
        if (!(sender instanceof Player player)) {
            config.onlyPlayerExecute(sender);
            return false;
        }

        BossBarUtils bUtils = new BossBarUtils();
        MiniMessageUtil mUtils = new MiniMessageUtil();
        PaperBossBar pBossBar = new PaperBossBar(plugin);

        // The command requires arguments to work
        if (!bUtils.regularBossbarArgs(args.length, sender)) {
            return false;
        }

        float time = bUtils.validBossbarNumber(args[0], sender);
        if(time == 0.1f) return false;

        BossBar.Color color = bUtils.bossbarColor(args[1]);
        BossBar.Overlay overlay = bUtils.bossbarOverlay(args[2]);

        if (color == null || overlay == null) {
            sender.sendMessage(config.getPrefix().append(Component.text("Invalid Argument", NamedTextColor.DARK_RED)));
            return false;
        }

        // Concatenate the arguments provided by the command sent.
        String bossbartext = new GeneralUtils().getCommandString(args, 4);

        pBossBar.sendBukkitBossBar(
            player,
            time,
            mUtils.parse(
                mUtils.replaceLegacy(
                    Announcer.placeholderAPIHook() ? PlaceholderAPI.setPlaceholders(player, bossbartext) : bossbartext), 
                    new PPlaceholders().replacePlaceholders(player)),
            color,
            overlay);
        config.playPaperSound(ComponentType.BOSSBAR, sender);
        config.sendConfirmation(ComponentType.BOSSBAR, sender);
        return true;
    }
}
