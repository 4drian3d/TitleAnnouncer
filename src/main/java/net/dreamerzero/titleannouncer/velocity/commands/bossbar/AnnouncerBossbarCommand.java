package net.dreamerzero.titleannouncer.velocity.commands.bossbar;

import java.util.List;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;

import net.dreamerzero.titleannouncer.velocity.utils.VelocityBossbar;
import net.dreamerzero.titleannouncer.common.utils.BossBarUtils;
import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.common.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.common.utils.ComponentType;
import net.dreamerzero.titleannouncer.velocity.Announcer;
import net.dreamerzero.titleannouncer.velocity.utils.SoundUtils;
import net.dreamerzero.titleannouncer.velocity.utils.VPlaceholders;
import net.kyori.adventure.bossbar.BossBar;

public class AnnouncerBossbarCommand implements SimpleCommand {
    private ProxyServer server;
    private Announcer plugin;
    public AnnouncerBossbarCommand(ProxyServer server, Announcer plugin) {
        this.server = server;
        this.plugin = plugin;
    }
    @Override
    public void execute(Invocation invocation) {
        CommandSource sender = invocation.source();
        String[] args = invocation.arguments();
        BossBarUtils bUtils = new BossBarUtils();
        MiniMessageUtil mUtils = new MiniMessageUtil();
        VelocityBossbar vBossbar = new VelocityBossbar(plugin, server);
        VPlaceholders vPlaceholders = new VPlaceholders(server);

        // The command requires arguments to work
        if (!bUtils.regularBossbarArgs(args.length, sender)) {
            return;
        }

        // Concatenate the arguments provided by the command sent.
        String bossbartext = new GeneralUtils().getCommandString(args, 3);

        float time = bUtils.validBossbarNumber(args[0], sender);
        if(time == 0.1f) return;

        BossBar.Color color = bUtils.bossbarColor(args[1]);
        BossBar.Overlay overlay = bUtils.bossbarOverlay(args[2]);

        ConfigUtils config = new ConfigUtils();

        if (color == null || overlay == null) {
            sender.sendMessage(
                config.getPrefix().append(
                    mUtils.parse("<dark_red>Invalid Argument")));
            return;
        }

        SoundUtils sUtils = new SoundUtils(server);

        // Send to all
        if (sender instanceof Player player) {
            vBossbar.sendVelocityBossbar(
                server,
                time,
                mUtils.parse(
                    mUtils.replaceLegacy(
                        bossbartext),
                        vPlaceholders.replaceProxyPlaceholders(player)),
                color,
                overlay);
            config.sendConfirmation(ComponentType.BOSSBAR, sender);
            sUtils.playProxySound(ComponentType.BOSSBAR);
            return;
        } else {
            vBossbar.sendVelocityBossbar(
                server,
                time,
                mUtils.parse(
                    mUtils.replaceLegacy(
                        bossbartext),
                        vPlaceholders.replaceProxyPlaceholders()),
                color,
                overlay);
            config.sendConfirmation(ComponentType.BOSSBAR, sender);
            sUtils.playProxySound(ComponentType.BOSSBAR);
        }
    }

    @Override
    public List<String> suggest(final Invocation invocation) {
        return switch (invocation.arguments().length) {
            case 1 -> List.of("[Time]");
            case 2 -> List.of("[Color]");
            case 3 -> List.of("[Type]");
            default -> List.of("[message]");
        };
    }

    @Override
    public boolean hasPermission(final Invocation invocation) {
        return invocation.source().hasPermission("titleannouncer.bossbar.global");
    }
}
