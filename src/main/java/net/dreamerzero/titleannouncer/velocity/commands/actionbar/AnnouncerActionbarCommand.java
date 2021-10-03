package net.dreamerzero.titleannouncer.velocity.commands.actionbar;

import java.util.List;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;

import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.common.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.common.utils.ComponentType;
import net.dreamerzero.titleannouncer.velocity.utils.SoundUtils;
import net.dreamerzero.titleannouncer.velocity.utils.VPlaceholders;

public class AnnouncerActionbarCommand implements SimpleCommand {
    private ProxyServer server;
    public AnnouncerActionbarCommand(ProxyServer server) {
        this.server = server;
    }
    @Override
    public void execute(Invocation invocation) {
        CommandSource sender = invocation.source();
        String[] args = invocation.arguments();
        // Concatenate the arguments provided by the command sent.
        String actionbartext = new GeneralUtils().getCommandString(args);
        ConfigUtils config = new ConfigUtils();
        MiniMessageUtil mUtils = new MiniMessageUtil();
        VPlaceholders vPlaceholders = new VPlaceholders(server);

        if(args.length == 0) {
            config.noActionbarArgumentProvided(sender);
            return;
        }

        // Send to all
        server.sendActionBar(mUtils.parse(
            mUtils.replaceLegacy(
                actionbartext),
                sender instanceof Player player ?
                    vPlaceholders.replaceProxyPlaceholders(player) :
                    vPlaceholders.replaceProxyPlaceholders()));
        new SoundUtils().playProxySound(ComponentType.ACTIONBAR);
        config.sendConfirmation(ComponentType.ACTIONBAR, sender);
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
