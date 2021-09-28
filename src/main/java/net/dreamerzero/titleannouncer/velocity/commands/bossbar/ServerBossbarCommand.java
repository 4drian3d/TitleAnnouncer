package net.dreamerzero.titleannouncer.velocity.commands.bossbar;

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

        float time;
        if(BossBarUtils.validBossbarNumber(args[1], sender) == 0.1f){
            return;
        } else {
            time = BossBarUtils.validBossbarNumber(args[1], sender);
        }

        BossBar.Color color = BossBarUtils.bossbarColor(args[2]);
        BossBar.Overlay overlay = BossBarUtils.bossbarOverlay(args[3]);

        if (color == null || overlay == null) {
            sender.sendMessage(ConfigUtils.getPrefix().append(MiniMessageUtil.parse("<dark_red>Invalid Argument")));
            return;
        }

        // Concatenate the arguments provided by the command sent.
        String bossbartext = GeneralUtils.getCommandString(args, 5);

        BossBarUtils.sendVelocityBossbar(
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
        return;
    }
}