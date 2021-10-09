package net.dreamerzero.titleannouncer.velocity.commands.title;

import java.util.ArrayList;
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

public record ServerTitleCommand(ProxyServer server, MiniMessage mm) implements SimpleCommand {

    @Override
    public void execute(Invocation invocation) {
        CommandSource sender = invocation.source();
        String[] args = invocation.arguments();
        ConfigUtils config = new ConfigUtils();
        VPlaceholders vPlaceholders = new VPlaceholders(server);

        switch (args.length) {
            case 0 -> {
                config.sendNoArgumentMessage(sender);
                return;
            }
            case 1 -> {
                config.noServerArgumentProvided(sender);
                return;
            }
        }

        Optional<RegisteredServer> optionalServerObjetive = server.getServer(args[0]);
        if(!optionalServerObjetive.isPresent()) {
            config.noServerFound(sender);
            return;
        }
        RegisteredServer serverObjetive = optionalServerObjetive.get();

        // Concatenate the arguments provided by the command sent.
        String titleandsubtitle = new GeneralUtils().getCommandString(args, 1);

        SoundUtils sUtils = new SoundUtils(server);
        TitleUtil tUtil = new TitleUtil();

        if(!tUtil.containsComma(args, 1)){
            tUtil.sendOnlySubtitle(
                mm.parse(MiniMessageUtil.replaceLegacy(titleandsubtitle),
                    vPlaceholders.replaceProxyPlaceholders()),
                serverObjetive, 1000, 3000, 1000);
            config.sendConfirmation(ComponentType.TITLE, sender);
            sUtils.playProxySound(serverObjetive, ComponentType.TITLE);
            return;
        }

        String titleandsubtitlefinal[] = tUtil.getTitleAndSubtitle(titleandsubtitle, sender);

        if(titleandsubtitlefinal == null) return;

        // Send the title
        tUtil.sendTitle(
            mm.parse(
                MiniMessageUtil.replaceLegacy(titleandsubtitlefinal[0]),
                vPlaceholders.replaceProxyPlaceholders()),
            mm.parse(
                MiniMessageUtil.replaceLegacy(titleandsubtitlefinal[1]),
                vPlaceholders.replaceProxyPlaceholders()),
            serverObjetive,
            1000,
            3000,
            1000);
        sUtils.playProxySound(serverObjetive, ComponentType.TITLE);
        config.sendConfirmation(ComponentType.TITLE, sender);
    }

    @Override
    public List<String> suggest(final Invocation invocation) {
        if (invocation.arguments().length <= 1){
            ArrayList<String> servers = new ArrayList<>();
            server.getAllServers().forEach(sv -> servers.add(sv.getServerInfo().getName()));
            return servers;
        } else if (!new TitleUtil().containsComma(invocation.arguments())){
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
