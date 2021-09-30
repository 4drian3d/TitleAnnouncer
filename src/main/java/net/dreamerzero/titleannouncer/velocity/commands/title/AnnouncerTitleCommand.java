package net.dreamerzero.titleannouncer.velocity.commands.title;

import java.util.List;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;

import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.common.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.common.utils.PlaceholderUtil;
import net.dreamerzero.titleannouncer.velocity.utils.SoundType;
import net.dreamerzero.titleannouncer.velocity.utils.SoundUtils;
import net.dreamerzero.titleannouncer.common.utils.TitleUtil;

public class AnnouncerTitleCommand implements SimpleCommand {
    private ProxyServer server;
    public AnnouncerTitleCommand(ProxyServer server) {
        this.server = server;
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
            if(sender instanceof Player player){
                TitleUtil.sendOnlySubtitle(
                    MiniMessageUtil.parse(
                        MiniMessageUtil.replaceLegacy(titleandsubtitle),
                        PlaceholderUtil.replaceProxyPlaceholders(player)),
                    server, 1000, 3000, 1000);
                ConfigUtils.sendTitleConfirmation(sender);
                SoundUtils.playProxySound(SoundType.TITLE);
                return;
            } else {
                TitleUtil.sendOnlySubtitle(
                    MiniMessageUtil.parse(
                        MiniMessageUtil.replaceLegacy(titleandsubtitle),
                        PlaceholderUtil.replaceProxyPlaceholders()),
                    server, 1000, 3000, 1000);
                ConfigUtils.sendTitleConfirmation(sender);
                SoundUtils.playProxySound(SoundType.TITLE);
                return;
            }
        }

        String titleandsubtitlefinal[] = TitleUtil.getTitleAndSubtitle(titleandsubtitle, sender);

        if(titleandsubtitlefinal == null) return;

        if (sender instanceof Player player) {
            // Send the title
            TitleUtil.sendTitle(
                MiniMessageUtil.parse(
                    MiniMessageUtil.replaceLegacy(titleandsubtitlefinal[0]),
                    PlaceholderUtil.replaceProxyPlaceholders(player)),
                MiniMessageUtil.parse(
                    MiniMessageUtil.replaceLegacy(titleandsubtitlefinal[1]),
                    PlaceholderUtil.replaceProxyPlaceholders(player)),
                server,
                1000,
                3000,
                1000);
            SoundUtils.playProxySound(SoundType.TITLE);
            ConfigUtils.sendTitleConfirmation(sender);
        } else {
            // Send the title
            TitleUtil.sendTitle(
                MiniMessageUtil.parse(
                    MiniMessageUtil.replaceLegacy(titleandsubtitlefinal[0]),
                    PlaceholderUtil.replaceProxyPlaceholders()),
                MiniMessageUtil.parse(
                    MiniMessageUtil.replaceLegacy(titleandsubtitlefinal[1]),
                    PlaceholderUtil.replaceProxyPlaceholders()),
                server,
                1000,
                3000,
                1000);
            SoundUtils.playProxySound(SoundType.TITLE);
            ConfigUtils.sendTitleConfirmation(sender);
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
