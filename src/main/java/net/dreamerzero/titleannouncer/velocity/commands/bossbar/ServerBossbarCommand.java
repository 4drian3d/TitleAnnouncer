package net.dreamerzero.titleannouncer.velocity.commands.bossbar;

import java.util.ArrayList;
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

public class ServerBossbarCommand implements SimpleCommand {
    private ProxyServer server;
    private Announcer plugin;
    public ServerBossbarCommand(ProxyServer server, Announcer plugin) {
        this.server = server;
        this.plugin = plugin;
    }
    @Override
    public void execute(Invocation invocation){
        CommandSource sender = invocation.source();
        String[] args = invocation.arguments();
        BossBarUtils bUtils = new BossBarUtils();
        ConfigUtils config = new ConfigUtils();
        MiniMessageUtil mUtils = new MiniMessageUtil();

        // The command requires arguments to work
        if (!bUtils.proxyBossbarArgs(args.length, sender)) {
            return;
        }

        Optional<RegisteredServer> optionalServerObjetive = server.getServer(args[0]);
        if(!optionalServerObjetive.isPresent()) {
            config.noServerFound(sender);
            return;
        }
        RegisteredServer serverObjetive = optionalServerObjetive.get();

        float time = bUtils.validBossbarNumber(args[1], sender);
        if(time == 0.1f) return;

        BossBar.Color color = bUtils.bossbarColor(args[2]);
        BossBar.Overlay overlay = bUtils.bossbarOverlay(args[3]);

        if (color == null || overlay == null) {
            sender.sendMessage(config.getPrefix().append(mUtils.parse("<dark_red>Invalid Argument")));
            return;
        }

        // Concatenate the arguments provided by the command sent.
        String bossbartext = new GeneralUtils().getCommandString(args, 5);

        new VelocityBossbar(plugin).sendVelocityBossbar(
            serverObjetive,
            time,
            mUtils.parse(
                mUtils.replaceLegacy(
                    bossbartext),
                    new VPlaceholders(server).replaceProxyPlaceholders()),
            color,
            overlay);
        config.sendConfirmation(ComponentType.BOSSBAR, sender);
        new SoundUtils().playProxySound(serverObjetive, ComponentType.BOSSBAR);
    }

    @Override
    public List<String> suggest(final Invocation invocation) {
        return switch (invocation.arguments().length) {
            case 0, 1 -> {
                ArrayList<String> servers = new ArrayList<>();
                server.getAllServers().forEach(sv -> servers.add(sv.getServerInfo().getName()));
                yield servers;
            }
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
