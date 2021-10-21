package net.dreamerzero.titleannouncer.velocity.commands.bossbar;

import java.util.List;
import java.util.Optional;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;

import net.dreamerzero.titleannouncer.common.utils.BossBarUtils;
import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.common.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.common.utils.ComponentType;
import net.dreamerzero.titleannouncer.velocity.Announcer;
import net.dreamerzero.titleannouncer.velocity.utils.SoundUtils;
import net.dreamerzero.titleannouncer.velocity.utils.VPlaceholders;
import net.dreamerzero.titleannouncer.velocity.utils.VelocityBossbar;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class ServerBossbarCommand implements SimpleCommand {
    private final MiniMessage mm;
    private final ProxyServer server;
    private final Announcer plugin;
    private SoundUtils sUtils;
    private VPlaceholders vPlaceholders;
    public ServerBossbarCommand(ProxyServer server, Announcer plugin, MiniMessage mm){
        this.server = server;
        this.mm = mm;
        this.plugin = plugin;
        this.sUtils = new SoundUtils(server);
        this.vPlaceholders = new VPlaceholders(server);
    }

    @Override
    public void execute(Invocation invocation){
        CommandSource sender = invocation.source();
        String[] args = invocation.arguments();

        // The command requires arguments to work
        if (!BossBarUtils.proxyBossbarArgs(args.length, sender)) {
            return;
        }

        Optional<RegisteredServer> optionalServerObjetive = server.getServer(args[0]);
        if(!optionalServerObjetive.isPresent()) {
            ConfigUtils.noServerFound(sender);
            return;
        }
        RegisteredServer serverObjetive = optionalServerObjetive.get();

        float time = BossBarUtils.validBossbarNumber(args[1], sender);
        if(time == 0.1f) return;

        BossBar.Color color = BossBarUtils.bossbarColor(args[2]);
        BossBar.Overlay overlay = BossBarUtils.bossbarOverlay(args[3]);

        if (color == null || overlay == null) {
            sender.sendMessage(ConfigUtils.getPrefix().append(mm.deserialize("<dark_red>Invalid Argument")));
            return;
        }

        // Concatenate the arguments provided by the command sent.
        String bossbartext = GeneralUtils.getCommandString(args, 5);

        new VelocityBossbar(plugin, server).sendVelocityBossbar(
            serverObjetive,
            time,
            mm.deserialize(
                MiniMessageUtil.replaceLegacy(
                    bossbartext),
                    vPlaceholders.replaceProxyPlaceholders()),
            color,
            overlay);
        ConfigUtils.sendConfirmation(ComponentType.BOSSBAR, sender);
        sUtils.playProxySound(serverObjetive, ComponentType.BOSSBAR);
    }

    @Override
    public List<String> suggest(final Invocation invocation) {
        return switch (invocation.arguments().length) {
            case 0, 1 -> server.getAllServers().stream()
                .map(server -> server.getServerInfo().getName()).toList();
            case 2 -> List.of("[Time]");
            case 3 -> List.of("[Color]");
            case 4 -> List.of("[Type]");
            default -> List.of("[message]");
        };
    }

    @Override
    public boolean hasPermission(final Invocation invocation) {
        return invocation.source().hasPermission("titleannouncer.bossbar.server");
    }
}
