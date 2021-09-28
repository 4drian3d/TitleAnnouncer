package net.dreamerzero.titleannouncer.velocity.commands.actionbar;

import java.util.List;
import java.util.Optional;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;

import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.common.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.common.utils.PlaceholderUtil;
import net.dreamerzero.titleannouncer.common.utils.SoundUtil;
import net.dreamerzero.titleannouncer.velocity.Announcer;

public class ServerActionbarCommand implements SimpleCommand {
    private ProxyServer server;
    public ServerActionbarCommand(ProxyServer server) {
        this.server = server;
    }
    @Override
    public void execute(Invocation invocation) {
        CommandSource sender = invocation.source();
        String[] args = invocation.arguments();

        if(args.length == 0) {
            ConfigUtils.noActionbarArgumentProvided(sender);
            return;
        }else if (args.length < 2) {
            ConfigUtils.noServerArgumentProvided(sender);
            return;
        }

        Optional<RegisteredServer> optionalServerObjetive = server.getServer(args[0]);
        if(!optionalServerObjetive.isPresent()) {
            ConfigUtils.noServerFound(sender);
            return;
        }
        RegisteredServer serverObjetive = optionalServerObjetive.get();

        // Concatenate the arguments provided by the command sent.
        String actionbartext = GeneralUtils.getCommandString(args, 1);

        serverObjetive.sendActionBar(
            MiniMessageUtil.parse(
                MiniMessageUtil.replaceLegacy(
                    actionbartext), 
                    PlaceholderUtil.replaceProxyPlaceholders()));
        SoundUtil.playToServerProxyActionbarSound(serverObjetive);
        ConfigUtils.sendActionbarConfirmation(sender);
    }

    @Override
    public List<String> suggest(final Invocation invocation) {
        if(invocation.arguments().length <= 1){
            List<String> servers = List.of("");
            Announcer.getProxyServer().getAllServers().forEach(server ->
                servers.add(server.getServerInfo().getName()));
            return servers;
        }
        return List.of("[message]");
    }

    @Override
    public boolean hasPermission(final Invocation invocation) {
        return invocation.source().hasPermission("titleannouncer.actionbar.server");
    }
}
