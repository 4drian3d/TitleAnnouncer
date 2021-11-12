package net.dreamerzero.titleannouncer.velocity.commands.actionbar;

import java.util.List;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;

import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.common.utils.ComponentType;
import net.dreamerzero.titleannouncer.velocity.utils.SoundUtils;
import net.dreamerzero.titleannouncer.velocity.utils.VelocityPlaceholders;

public class AnnouncerActionbarCommand implements SimpleCommand {
    private final ProxyServer server;
    private SoundUtils sUtils;
    private VelocityPlaceholders vPlaceholders;
    public AnnouncerActionbarCommand(ProxyServer server){
        this.server = server;
        this.sUtils = new SoundUtils(server);
        this.vPlaceholders = new VelocityPlaceholders(server);
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource sender = invocation.source();
        String[] args = invocation.arguments();

        if(args.length == 0) {
            ConfigUtils.noActionbarArgumentProvided(sender);
            return;
        }

        String actionbartext = GeneralUtils.getCommandString(args);

        // Send to all
        server.sendActionBar(
                sender instanceof Player player ?
                    vPlaceholders.applyPlaceholders(actionbartext, player) :
                    vPlaceholders.applyPlaceholders(actionbartext));
        sUtils.playProxySound(ComponentType.ACTIONBAR);
        ConfigUtils.sendConfirmation(ComponentType.ACTIONBAR, sender);
    }

    @Override
    public List<String> suggest(final Invocation invocation) {
        return List.of("[message]");
    }

    @Override
    public boolean hasPermission(final Invocation invocation) {
        return invocation.source().hasPermission("titleannouncer.actionbar.global");
    }
}
