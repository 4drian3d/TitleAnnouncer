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
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;

public record WorldBossbarCommand(Announcer plugin, MiniMessage mm) implements CommandExecutor {

    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // It will send an actionbar to the world in which the command is executed, 
        // it makes no sense for the console to execute it.
        if (!(sender instanceof Player player)) {
            ConfigUtils.onlyPlayerExecute(sender);
            return false;
        }

        BossBarUtils bUtils = new BossBarUtils(mm);

        // The command requires arguments to work
        if (!bUtils.regularBossbarArgs(args.length, sender)) {
            return false;
        }

        // Concatenate the arguments provided by the command sent.
        String bossbartext = GeneralUtils.getCommandString(args, 3);

        float time = bUtils.validBossbarNumber(args[0], sender);
        if(time == 0.1f) return false;

        BossBar.Color color = bUtils.bossbarColor(args[1]);
        BossBar.Overlay overlay = bUtils.bossbarOverlay(args[2]);

        if (color == null || overlay == null) {
            sender.sendMessage(ConfigUtils.getPrefix().append(Component.text("Invalid Argument", NamedTextColor.DARK_RED)));
            return false;
        }

        // The audience that will receive the bossbar will be all the players on the server.
        Audience audience = player.getWorld();

        new PaperBossBar(plugin).sendBukkitBossBar(
            audience,
            time,
            mm.parse(
                MiniMessageUtil.replaceLegacy(
                    Announcer.placeholderAPIHook() ? PlaceholderAPI.setPlaceholders(player, bossbartext) : bossbartext), 
                    PPlaceholders.replacePlaceholders()),
            color,
            overlay);
        ConfigUtils.sendConfirmation(ComponentType.BOSSBAR, sender);
        ConfigUtils.playPaperSound(ComponentType.BOSSBAR, audience);
        return true;
    }
}
