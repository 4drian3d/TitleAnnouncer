package net.dreamerzero.titleannouncer.velocity.commands.bossbar;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;

import net.dreamerzero.titleannouncer.velocity.utils.VelocityBossbar;
import net.dreamerzero.titleannouncer.common.utils.BossBarUtils;
import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.common.utils.ComponentType;
import net.dreamerzero.titleannouncer.velocity.Announcer;
import net.dreamerzero.titleannouncer.velocity.utils.SoundUtils;
import net.dreamerzero.titleannouncer.velocity.utils.VelocityPlaceholders;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class AnnouncerBossbarCommand implements SimpleCommand {
    private final ProxyServer server;
    private final MiniMessage mm;
    private final Announcer plugin;
    private SoundUtils sUtils;
    private VelocityPlaceholders vPlaceholders;
    public AnnouncerBossbarCommand(ProxyServer server, Announcer plugin, MiniMessage mm){
        this.server = server;
        this.plugin = plugin;
        this.mm = mm;
        this.sUtils = new SoundUtils(server);
        this.vPlaceholders = new VelocityPlaceholders(server);
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource sender = invocation.source();
        String[] args = invocation.arguments();

        // The command requires arguments to work
        if (!BossBarUtils.regularBossbarArgs(args.length, sender)) {
            return;
        }

        // Concatenate the arguments provided by the command sent.
        String bossbartext = GeneralUtils.getCommandString(args, 3);

        float time = BossBarUtils.validBossbarNumber(args[0], sender);
        if(time == 0.1f) return;

        BossBar.Color color = BossBarUtils.bossbarColor(args[1]);
        BossBar.Overlay overlay = BossBarUtils.bossbarOverlay(args[2]);

        if (color == null || overlay == null) {
            sender.sendMessage(
                ConfigUtils.getPrefix().append(
                    mm.deserialize("<dark_red>Invalid Argument")));
            return;
        }

        VelocityBossbar vBossbar = new VelocityBossbar(plugin, server);

        // Send to all
        if (sender instanceof Player player) {
            vBossbar.sendBossbar(
                server,
                time,
                vPlaceholders.applyPlaceholders(bossbartext, player),
                color,
                overlay);
            ConfigUtils.sendConfirmation(ComponentType.BOSSBAR, sender);
            sUtils.playProxySound(ComponentType.BOSSBAR);
        } else {
            vBossbar.sendBossbar(
                server,
                time,
                vPlaceholders.applyPlaceholders(bossbartext),
                color,
                overlay);
            ConfigUtils.sendConfirmation(ComponentType.BOSSBAR, sender);
            sUtils.playProxySound(ComponentType.BOSSBAR);
        }
    }

    @Override
    public CompletableFuture<List<String>> suggestAsync(final Invocation invocation) {
        return CompletableFuture.supplyAsync(()-> switch (invocation.arguments().length) {
            case 1 -> List.of("[Time]");
            case 2 -> List.of("[Color]");
            case 3 -> List.of("[Type]");
            default -> List.of("[message]");
        });
    }

    @Override
    public boolean hasPermission(final Invocation invocation) {
        return invocation.source().hasPermission("titleannouncer.bossbar.global");
    }
}
