package net.dreamerzero.titleannouncer.velocity.commands.bossbar;

import java.util.List;

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

public class SelfBossbarCommand implements SimpleCommand {
    private Announcer plugin;
    private ProxyServer server;
    public SelfBossbarCommand(Announcer plugin, ProxyServer server){
        this.plugin = plugin;
        this.server = server;
    }
    @Override
    public void execute(Invocation invocation) {
        CommandSource sender = invocation.source();
        String[] args = invocation.arguments();
        ConfigUtils config = new ConfigUtils();
        MiniMessageUtil mUtils = new MiniMessageUtil();

        if (!(sender instanceof Player player)) {
            config.onlyPlayerExecute(sender);
            return;
        }

        BossBarUtils bUtils = new BossBarUtils();

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

        if (color == null || overlay == null) {
            sender.sendMessage(
                config.getPrefix().append(
                    mUtils.parse("<dark_red>Invalid Argument")));
            return;
        }

        // Send to all
        new VelocityBossbar(plugin, server).sendVelocityBossbar(
            sender,
            time,
            mUtils.parse(mUtils.replaceLegacy(
                bossbartext),
                new VPlaceholders(server).replaceProxyPlaceholders(player)),
            color,
            overlay);
        config.sendConfirmation(ComponentType.BOSSBAR, sender);
        new SoundUtils(server).playProxySound(player, ComponentType.BOSSBAR);
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
        return invocation.source().hasPermission("titleannouncer.bossbar.self");
    }
}
