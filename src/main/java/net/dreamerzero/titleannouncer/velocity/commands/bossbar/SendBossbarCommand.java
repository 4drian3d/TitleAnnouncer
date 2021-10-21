package net.dreamerzero.titleannouncer.velocity.commands.bossbar;

import java.util.List;
import java.util.Optional;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;

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

public class SendBossbarCommand implements SimpleCommand{
    private final MiniMessage mm;
    private final ProxyServer server;
    private final Announcer plugin;
    private VPlaceholders vpapi;
    private SoundUtils sUtils;
    public SendBossbarCommand(ProxyServer server, Announcer plugin, MiniMessage mm){
        this.mm = mm;
        this.server = server;
        this.plugin = plugin;
        this.vpapi = new VPlaceholders(server);
    }

    @Override
    public void execute(Invocation invocation){
        CommandSource sender = invocation.source();
        String[] args = invocation.arguments();
        VelocityBossbar vBossbar = new VelocityBossbar(plugin, server);

        // The command requires arguments to work
        if (!BossBarUtils.sendBossbarArgs(args.length, sender)) {
            return;
        }

        Optional<Player> optionalPlayerObjetive = server.getPlayer(args[0]);
        if(!optionalPlayerObjetive.isPresent()) {
            ConfigUtils.playerNotFoundMessage(sender);
            return;
        }
        Player playerObjetive = optionalPlayerObjetive.get();

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

        vBossbar.sendVelocityBossbar(
            playerObjetive,
            time,
            mm.deserialize(
                MiniMessageUtil.replaceLegacy(
                    bossbartext),
                    vpapi.replaceProxyPlaceholders(playerObjetive)),
            color,
            overlay);
        ConfigUtils.sendConfirmation(ComponentType.BOSSBAR, sender);
        sUtils.playProxySound(playerObjetive, ComponentType.BOSSBAR);
    }

    @Override
    public List<String> suggest(final Invocation invocation) {
        return switch (invocation.arguments().length) {
            case 1 -> server.getAllPlayers().stream()
                .map(Player::getUsername).toList();
            case 2 -> List.of("[Time]");
            case 3 -> List.of("[Color]");
            case 4 -> List.of("[Type]");
            default -> List.of("[message]");
        };
    }

    @Override
    public boolean hasPermission(final Invocation invocation) {
        return invocation.source().hasPermission("titleannouncer.bossbar.send");
    }
}
