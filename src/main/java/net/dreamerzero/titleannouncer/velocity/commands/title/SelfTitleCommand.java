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

public class SelfTitleCommand implements SimpleCommand {
    private SoundUtils sUtils;
    private VelocityPlaceholders vPlaceholders;
    public SelfTitleCommand(ProxyServer server){
        this.sUtils = new SoundUtils(server);
        this.vPlaceholders = new VelocityPlaceholders(server);
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource sender = invocation.source();
        String[] args = invocation.arguments();

        if (!(sender instanceof Player player)) {
            ConfigUtils.onlyPlayerExecute(sender);
            return;
        }

        if(args.length == 0) {
            ConfigUtils.sendNoArgumentMessage(sender);
            return;
        }

        // Concatenate the arguments provided by the command sent.
        String titleandsubtitle = GeneralUtils.getCommandString(args);

        if(!titleandsubtitle.contains(";")){
            TitleUtil.sendOnlySubtitle(
                vPlaceholders.applyPlaceholders(titleandsubtitle, player),
                sender, 1000, 3000, 1000);
            ConfigUtils.sendConfirmation(ComponentType.TITLE, sender);
            sUtils.playProxySound(player, ComponentType.TITLE);
            return;
        }

        String[] titleandsubtitlefinal = TitleUtil.getTitleAndSubtitle(titleandsubtitle, sender);

        if(titleandsubtitlefinal == null) return;

        // Send the title
        sender.showTitle(Title.title(
            vPlaceholders.applyPlaceholders(titleandsubtitlefinal[0], player),
            vPlaceholders.applyPlaceholders(titleandsubtitlefinal[1], player)
        ));
        sUtils.playProxySound(player, ComponentType.TITLE);
        ConfigUtils.sendConfirmation(ComponentType.TITLE, sender);
    }

    @Override
    public CompletableFuture<List<String>> suggestAsync(final Invocation invocation) {
        return CompletableFuture.supplyAsync(()-> {
            if (!TitleUtil.containsComma(invocation.arguments())){
                return List.of("[Title]");
            } else {
                return List.of("[SubTitle]");
            }
        });
    }

    @Override
    public boolean hasPermission(final Invocation invocation) {
        return invocation.source().hasPermission("titleannouncer.title.self");
    }
}
