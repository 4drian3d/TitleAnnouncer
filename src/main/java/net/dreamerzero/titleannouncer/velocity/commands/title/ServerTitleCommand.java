package net.dreamerzero.titleannouncer.velocity.commands.title;

import java.util.List;
import java.util.Optional;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;

import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.common.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.common.utils.ComponentType;
import net.dreamerzero.titleannouncer.velocity.utils.SoundUtils;
import net.dreamerzero.titleannouncer.velocity.utils.VPlaceholders;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.dreamerzero.titleannouncer.common.utils.TitleUtil;

public class ServerTitleCommand implements SimpleCommand {
    private final MiniMessage mm;
    private final ProxyServer server;
    private SoundUtils sUtils;
    private VPlaceholders vPlaceholders;
    public ServerTitleCommand(ProxyServer server, MiniMessage mm){
        this.server = server;
        this.mm = mm;
        this.sUtils = new SoundUtils(server);
        this.vPlaceholders = new VPlaceholders(server);
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource sender = invocation.source();
        String[] args = invocation.arguments();

        switch (args.length) {
            case 0 -> {
                ConfigUtils.sendNoArgumentMessage(sender);
                return;
            }
            case 1 -> {
                ConfigUtils.noServerArgumentProvided(sender);
                return;
            }
        }

        Optional<RegisteredServer> optionalServerObjetive = server.getServer(args[0]);
        if(!optionalServerObjetive.isPresent()) {
            ConfigUtils.noServerFound(sender);
            return;
        }
        RegisteredServer serverObjetive = optionalServerObjetive.get();

        // Concatenate the arguments provided by the command sent.
        String titleandsubtitle = GeneralUtils.getCommandString(args, 1);

        if(!TitleUtil.containsComma(args, 1)){
            TitleUtil.sendOnlySubtitle(
                mm.deserialize(MiniMessageUtil.replaceLegacy(titleandsubtitle),
                    vPlaceholders.replaceProxyPlaceholders()),
                serverObjetive, 1000, 3000, 1000);
            ConfigUtils.sendConfirmation(ComponentType.TITLE, sender);
            sUtils.playProxySound(serverObjetive, ComponentType.TITLE);
            return;
        }

        String titleandsubtitlefinal[] = TitleUtil.getTitleAndSubtitle(titleandsubtitle, sender);

        if(titleandsubtitlefinal == null) return;

        // Send the title
        TitleUtil.sendTitle(
            mm.deserialize(
                MiniMessageUtil.replaceLegacy(titleandsubtitlefinal[0]),
                vPlaceholders.replaceProxyPlaceholders()),
            mm.deserialize(
                MiniMessageUtil.replaceLegacy(titleandsubtitlefinal[1]),
                vPlaceholders.replaceProxyPlaceholders()),
            serverObjetive,
            1000,
            3000,
            1000);
        sUtils.playProxySound(serverObjetive, ComponentType.TITLE);
        ConfigUtils.sendConfirmation(ComponentType.TITLE, sender);
    }

    @Override
    public List<String> suggest(final Invocation invocation) {
        if (invocation.arguments().length <= 1){
            return server.getAllServers().stream()
                .map(server -> server.getServerInfo().getName()).toList();
        } else if (!TitleUtil.containsComma(invocation.arguments())){
            return List.of("[Title]");
        } else {
            return List.of("[SubTitle]");
        }
    }

    @Override
    public boolean hasPermission(final Invocation invocation) {
        return invocation.source().hasPermission("titleannouncer.title.server");
    }
}
