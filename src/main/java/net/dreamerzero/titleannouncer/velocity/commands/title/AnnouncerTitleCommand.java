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
        ConfigUtils config = new ConfigUtils();
        MiniMessageUtil mUtils = new MiniMessageUtil();
        VPlaceholders vPlaceholders = new VPlaceholders(server);

        if(args.length == 0) {
            config.sendNoArgumentMessage(sender);
            return;
        }

        // Concatenate the arguments provided by the command sent.
        String titleandsubtitle = new GeneralUtils().getCommandString(args);

        SoundUtils sUtils = new SoundUtils(server);
        TitleUtil tUtil = new TitleUtil();

        if(!titleandsubtitle.contains(";")){
            if(sender instanceof Player player){
                tUtil.sendOnlySubtitle(
                    mUtils.parse(
                        mUtils.replaceLegacy(titleandsubtitle),
                        vPlaceholders.replaceProxyPlaceholders(player)),
                    server, 1000, 3000, 1000);
                config.sendConfirmation(ComponentType.TITLE, sender);
                sUtils.playProxySound(ComponentType.TITLE);
                return;
            } else {
                tUtil.sendOnlySubtitle(
                    mUtils.parse(
                        mUtils.replaceLegacy(titleandsubtitle),
                        vPlaceholders.replaceProxyPlaceholders()),
                    server, 1000, 3000, 1000);
                config.sendConfirmation(ComponentType.TITLE, sender);
                sUtils.playProxySound(ComponentType.TITLE);
                return;
            }
        }

        String titleandsubtitlefinal[] = tUtil.getTitleAndSubtitle(titleandsubtitle, sender);

        if(titleandsubtitlefinal == null) return;

        if (sender instanceof Player player) {
            // Send the title
            // Possible bug fix? -> VTask.run(()-> {task});
                tUtil.sendTitle(
                mUtils.parse(
                    mUtils.replaceLegacy(titleandsubtitlefinal[0]),
                    vPlaceholders.replaceProxyPlaceholders(player)),
                mUtils.parse(
                    mUtils.replaceLegacy(titleandsubtitlefinal[1]),
                    vPlaceholders.replaceProxyPlaceholders(player)),
                server,
                1000,
                3000,
                1000);
            sUtils.playProxySound(ComponentType.TITLE);
            config.sendConfirmation(ComponentType.TITLE, sender);
        } else {
            // Send the title
            tUtil.sendTitle(
                mUtils.parse(
                    mUtils.replaceLegacy(titleandsubtitlefinal[0]),
                    vPlaceholders.replaceProxyPlaceholders()),
                mUtils.parse(
                    mUtils.replaceLegacy(titleandsubtitlefinal[1]),
                    vPlaceholders.replaceProxyPlaceholders()),
                server,
                1000,
                3000,
                1000);
            sUtils.playProxySound(ComponentType.TITLE);
            config.sendConfirmation(ComponentType.TITLE, sender);
        }
    }

    @Override
    public List<String> suggest(final Invocation invocation) {
        if (!new TitleUtil().containsComma(invocation.arguments())){
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
