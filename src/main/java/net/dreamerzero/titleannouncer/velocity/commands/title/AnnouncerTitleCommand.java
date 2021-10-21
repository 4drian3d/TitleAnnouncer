package net.dreamerzero.titleannouncer.velocity.commands.title;

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
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.dreamerzero.titleannouncer.common.utils.TitleUtil;

public class AnnouncerTitleCommand implements SimpleCommand {
    private final MiniMessage mm;
    private final ProxyServer server;
    private SoundUtils sUtils;
    private VPlaceholders vPlaceholders;
    public AnnouncerTitleCommand(ProxyServer server, MiniMessage mm){
        this.server = server;
        this.mm = mm;
        this.sUtils = new SoundUtils(server);
        this.vPlaceholders = new VPlaceholders(server);
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource sender = invocation.source();
        String[] args = invocation.arguments();

        if(args.length == 0) {
            ConfigUtils.sendNoArgumentMessage(sender);
            return;
        }

        // Concatenate the arguments provided by the command sent.
        String titleandsubtitle = GeneralUtils.getCommandString(args);

        if(!titleandsubtitle.contains(";")){
            TitleUtil.sendOnlySubtitle(
                mm.deserialize(
                    MiniMessageUtil.replaceLegacy(titleandsubtitle),
                    sender instanceof Player player ? vPlaceholders.replaceProxyPlaceholders(player) : vPlaceholders.replaceProxyPlaceholders()),
                server, 1000, 3000, 1000);
            ConfigUtils.sendConfirmation(ComponentType.TITLE, sender);
            sUtils.playProxySound(ComponentType.TITLE);
            return;
        }

        String titleandsubtitlefinal[] = TitleUtil.getTitleAndSubtitle(titleandsubtitle, sender);

        if(titleandsubtitlefinal == null) return;

        if (sender instanceof Player player) {
            // Send the title
            // Possible bug fix? -> VTask.run(()-> {task});
            // Bug in Adventure, not mine
                TitleUtil.sendTitle(
                mm.deserialize(
                    MiniMessageUtil.replaceLegacy(titleandsubtitlefinal[0]),
                    vPlaceholders.replaceProxyPlaceholders(player)),
                mm.deserialize(
                    MiniMessageUtil.replaceLegacy(titleandsubtitlefinal[1]),
                    vPlaceholders.replaceProxyPlaceholders(player)),
                server,
                1000,
                3000,
                1000);
            sUtils.playProxySound(ComponentType.TITLE);
            ConfigUtils.sendConfirmation(ComponentType.TITLE, sender);
        } else {
            // Send the title
            TitleUtil.sendTitle(
                mm.deserialize(
                    MiniMessageUtil.replaceLegacy(titleandsubtitlefinal[0]),
                    vPlaceholders.replaceProxyPlaceholders()),
                mm.deserialize(
                    MiniMessageUtil.replaceLegacy(titleandsubtitlefinal[1]),
                    vPlaceholders.replaceProxyPlaceholders()),
                server,
                1000,
                3000,
                1000);
            sUtils.playProxySound(ComponentType.TITLE);
            ConfigUtils.sendConfirmation(ComponentType.TITLE, sender);
        }
    }

    @Override
    public List<String> suggest(final Invocation invocation) {
        if (!TitleUtil.containsComma(invocation.arguments())){
            return List.of("[Title]");
        } else {
            return List.of("[SubTitle]");
        }
    }

    @Override
    public boolean hasPermission(final Invocation invocation) {
        return invocation.source().hasPermission("titleannouncer.title.global");
    }
}
