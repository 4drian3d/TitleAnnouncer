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
import net.dreamerzero.titleannouncer.common.utils.PlaceholderUtil;
import net.dreamerzero.titleannouncer.common.utils.SoundUtil;
import net.dreamerzero.titleannouncer.velocity.utils.VelocityBossbar;
import net.kyori.adventure.bossbar.BossBar;

public class ServerBossbarCommand implements SimpleCommand {
    private ProxyServer server;
    public ServerBossbarCommand(ProxyServer server) {
        this.server = server;
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
            sender.sendMessage(ConfigUtils.getPrefix().append(MiniMessageUtil.parse("<dark_red>Invalid Argument")));
            return;
        }

        // Concatenate the arguments provided by the command sent.
        String bossbartext = GeneralUtils.getCommandString(args, 5);

        VelocityBossbar.sendVelocityBossbar(
            serverObjetive,
            time,
            MiniMessageUtil.parse(
                MiniMessageUtil.replaceLegacy(
                    bossbartext),
                    PlaceholderUtil.replaceProxyPlaceholders()),
            color,
            overlay);
        ConfigUtils.sendBossbarConfirmation(sender);
        SoundUtil.playToServerProxyBossbarSound(serverObjetive);
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
