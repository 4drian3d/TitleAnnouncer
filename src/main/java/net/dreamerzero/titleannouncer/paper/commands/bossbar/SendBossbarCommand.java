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
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class SendBossbarCommand implements CommandExecutor{
    private PaperPlaceholders placeholders;
    private Announcer plugin;
    public SendBossbarCommand(Announcer plugin){
        this.placeholders = new PaperPlaceholders();
        this.plugin = plugin;
    }
    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

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

        float time = BossBarUtils.validBossbarNumber(args[1], sender);
        if(time == 0.1f) return false;

        BossBar.Color color = BossBarUtils.bossbarColor(args[2]);
        BossBar.Overlay overlay = BossBarUtils.bossbarOverlay(args[3]);

        if (color == null || overlay == null) {
            sender.sendMessage(ConfigUtils.getPrefix().append(Component.text("Invalid Argument", NamedTextColor.DARK_RED)));
            return false;
        }

        // Concatenate the arguments provided by the command sent.
        String bossbartext = GeneralUtils.getCommandString(args, 5);

        new PaperBossBar(plugin).sendBukkitBossBar(
            playerObjetive,
            time,
            placeholders.applyPlaceholders(bossbartext, playerObjetive),
            color,
            overlay);
        ConfigUtils.sendConfirmation(ComponentType.BOSSBAR, sender);
        ConfigUtils.playPaperSound(ComponentType.BOSSBAR, playerObjetive);
        return true;
    }
}
