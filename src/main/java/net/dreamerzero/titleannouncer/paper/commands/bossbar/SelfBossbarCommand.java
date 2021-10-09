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
import net.kyori.adventure.text.minimessage.MiniMessage;

public class SelfBossbarCommand implements CommandExecutor {
    private MiniMessage mm;
    private Announcer plugin;
    private BossBarUtils bUtils;
    public SelfBossbarCommand(Announcer plugin, MiniMessage mm){
        this.mm = mm;
        this.plugin = plugin;
        bUtils = new BossBarUtils(mm);
    }
    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // It will send an actionbar to the one who executes the command,
        // it makes no sense for the console to execute it.
        if (!(sender instanceof Player player)) {
            ConfigUtils.onlyPlayerExecute(sender);
            return false;
        }

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
            sender.sendMessage(ConfigUtils.getPrefix().append(Component.text("Invalid Argument", NamedTextColor.DARK_RED)));
            return false;
        }

        // Concatenate the arguments provided by the command sent.
        String bossbartext = GeneralUtils.getCommandString(args, 4);

        pBossBar.sendBukkitBossBar(
            player,
            time,
            mm.parse(MiniMessageUtil.replaceLegacy(
                Announcer.placeholderAPIHook() ? PlaceholderAPI.setPlaceholders(player, bossbartext) : bossbartext), 
                PPlaceholders.replacePlaceholders(player)),
            color,
            overlay);
        ConfigUtils.playPaperSound(ComponentType.BOSSBAR, sender);
        ConfigUtils.sendConfirmation(ComponentType.BOSSBAR, sender);
        return true;
    }
}
