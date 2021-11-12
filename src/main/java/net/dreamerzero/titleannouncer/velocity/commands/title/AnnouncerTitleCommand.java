package net.dreamerzero.titleannouncer.velocity.commands.title;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;

import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.common.utils.ComponentType;
import net.dreamerzero.titleannouncer.velocity.utils.SoundUtils;
import net.dreamerzero.titleannouncer.velocity.utils.VelocityPlaceholders;
import net.kyori.adventure.title.Title;
import net.dreamerzero.titleannouncer.common.utils.TitleUtil;

public class AnnouncerTitleCommand implements SimpleCommand {
    private final ProxyServer server;
    private SoundUtils sUtils;
    private VelocityPlaceholders vPlaceholders;
    public AnnouncerTitleCommand(ProxyServer server){
        this.server = server;
        this.sUtils = new SoundUtils(server);
        this.vPlaceholders = new VelocityPlaceholders(server);
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
                sender instanceof Player player
                ? vPlaceholders.applyPlaceholders(titleandsubtitle, player)
                : vPlaceholders.applyPlaceholders(titleandsubtitle),
                server, 1000, 3000, 1000);
            ConfigUtils.sendConfirmation(ComponentType.TITLE, sender);
            sUtils.playProxySound(ComponentType.TITLE);
            return;
        }

        String[] titleandsubtitlefinal = TitleUtil.getTitleAndSubtitle(titleandsubtitle, sender);

        if(titleandsubtitlefinal == null) return;

        if (sender instanceof Player player) {
            server.showTitle(Title.title(
                vPlaceholders.applyPlaceholders(titleandsubtitlefinal[0], player),
                vPlaceholders.applyPlaceholders(titleandsubtitlefinal[1], player))
            );
            sUtils.playProxySound(ComponentType.TITLE);
            ConfigUtils.sendConfirmation(ComponentType.TITLE, sender);
        } else {
            // Send the title
            server.showTitle(Title.title(
                vPlaceholders.applyPlaceholders(titleandsubtitlefinal[0]),
                vPlaceholders.applyPlaceholders(titleandsubtitlefinal[1]))
            );
            sUtils.playProxySound(ComponentType.TITLE);
            ConfigUtils.sendConfirmation(ComponentType.TITLE, sender);
        }
    }

    @Override
    public CompletableFuture<List<String>> suggestAsync(final Invocation invocation) {
        return CompletableFuture.supplyAsync(()->{
            if (!TitleUtil.containsComma(invocation.arguments())){
                return List.of("[Title]");
            } else {
                return List.of("[SubTitle]");
            }
        });
    }

    @Override
    public boolean hasPermission(final Invocation invocation) {
        return invocation.source().hasPermission("titleannouncer.title.global");
    }
}
