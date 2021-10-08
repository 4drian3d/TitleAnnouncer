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
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public record SendBossbarCommand(Announcer plugin) implements CommandExecutor{

    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        BossBarUtils bUtils = new BossBarUtils();

        // The command requires arguments to work
        if (!bUtils.sendBossbarArgs(args.length, sender)) {
            return false;
        }

        Player playerObjetive = Bukkit.getPlayer(args[0]);

        //Collection of all players in the server
        var serverplayers = Bukkit.getOnlinePlayers();

        ConfigUtils config = new ConfigUtils();
        MiniMessageUtil mUtils = new MiniMessageUtil();

        if (!serverplayers.contains(playerObjetive)) {
            // Send an error message to the sender using the command.
            config.playerNotFoundMessage(sender);
            return false;
        }

        float time = bUtils.validBossbarNumber(args[1], sender);
        if(time == 0.1f) return false;

        BossBar.Color color = bUtils.bossbarColor(args[2]);
        BossBar.Overlay overlay = bUtils.bossbarOverlay(args[3]);

        if (color == null || overlay == null) {
            sender.sendMessage(config.getPrefix().append(Component.text("Invalid Argument", NamedTextColor.DARK_RED)));
            return false;
        }

        // Concatenate the arguments provided by the command sent.
        String bossbartext = new GeneralUtils().getCommandString(args, 5);

        new PaperBossBar(plugin).sendBukkitBossBar(
            playerObjetive,
            time,
            mUtils.parse(mUtils.replaceLegacy(
                Announcer.placeholderAPIHook() ? PlaceholderAPI.setPlaceholders(playerObjetive, bossbartext) : bossbartext),
                new PPlaceholders().replacePlaceholders(playerObjetive)),
            color,
            overlay);
        config.sendConfirmation(ComponentType.BOSSBAR, sender);
        config.playPaperSound(ComponentType.BOSSBAR, playerObjetive);
        return true;
    }
}
