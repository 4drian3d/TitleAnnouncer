package net.dreamerzero.titleannouncer.velocity.commands.actionbar;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

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

public class SendActionbarCommand implements SimpleCommand {
    private ProxyServer server;
    public SendActionbarCommand(ProxyServer server) {
        this.server = server;
    }
    @Override
    public void execute(Invocation invocation) {
        CommandSource sender = invocation.source();
        String[] args = invocation.arguments();
        ConfigUtils config = new ConfigUtils();
        MiniMessageUtil mUtils = new MiniMessageUtil();
        VPlaceholders vPlaceholders = new VPlaceholders(server);

        if(args.length == 0) {
            config.noActionbarArgumentProvided(sender);
            return;
        }else if (args.length < 2) {
            config.noActionbarPlayerArgumentProvided(sender);
            return;
        }

        Optional<Player> optionalPlayerObjetive = server.getPlayer(args[0]);
        if(!optionalPlayerObjetive.isPresent()) {
            config.playerNotFoundMessage(sender);
            return;
        }
        Player playerObjetive = optionalPlayerObjetive.get();

        // Concatenate the arguments provided by the command sent.
        String actionbartext = new GeneralUtils().getCommandString(args, 1);

        playerObjetive.sendActionBar(
            mUtils.parse(
                mUtils.replaceLegacy(
                    actionbartext),
                    vPlaceholders.replaceProxyPlaceholders(playerObjetive)));
        new SoundUtils(server).playProxySound(playerObjetive, ComponentType.ACTIONBAR);
        config.sendConfirmation(ComponentType.ACTIONBAR, sender);
    }

    @Override
    public List<String> suggest(final Invocation invocation) {
        if(invocation.arguments().length < 1){
            ArrayList<String> players = new ArrayList<>();
            server.getAllPlayers().forEach(player ->
                players.add(player.getUsername()));
            return players;
        }
        return List.of("[message]");
    }

    @Override
    public boolean hasPermission(final Invocation invocation) {
        return invocation.source().hasPermission("titleannouncer.actionbar.send");
    }
}
