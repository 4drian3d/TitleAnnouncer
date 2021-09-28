package net.dreamerzero.titleannouncer.velocity.commands.title;

import java.util.Optional;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;

import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.common.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.common.utils.PlaceholderUtil;
import net.dreamerzero.titleannouncer.common.utils.SoundUtil;
import net.dreamerzero.titleannouncer.common.utils.TitleUtil;

public class SendTitleCommand implements SimpleCommand {
    private ProxyServer server;
    public SendTitleCommand(ProxyServer server) {
        this.server = server;
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
                ConfigUtils.noTitlePlayerArgumentProvided(sender);
                return;
            }
        }

        Optional<Player> optionalPlayerObjetive = server.getPlayer(args[0]);
        if(!optionalPlayerObjetive.isPresent()) {
            ConfigUtils.playerNotFoundMessage(sender);
            return;
        }
        Player playerObjetive = optionalPlayerObjetive.get();

        // Concatenate the arguments provided by the command sent.
        String titleandsubtitle = GeneralUtils.getCommandString(args, 1);

        if(!titleandsubtitle.contains(";")){
            TitleUtil.sendOnlyTitle(
                MiniMessageUtil.parse(
                MiniMessageUtil.replaceLegacy(
                    titleandsubtitle),
                    PlaceholderUtil.replaceProxyPlaceholders(playerObjetive)),
                    playerObjetive, 1000, 3000, 1000);
            ConfigUtils.sendTitleConfirmation(sender);
            SoundUtil.playProxyTitleSound(playerObjetive);
            return;
        }

        String titleandsubtitlefinal[];

        if(TitleUtil.getTitleAndSubtitle(titleandsubtitle, sender) == null) {
            return;
        } else {
            titleandsubtitlefinal = TitleUtil.getTitleAndSubtitle(titleandsubtitle, sender);
        }

        // Send the title
        TitleUtil.sendTitle(
            MiniMessageUtil.parse(
                MiniMessageUtil.replaceLegacy(
                    titleandsubtitlefinal[0]),
                PlaceholderUtil.replaceProxyPlaceholders(playerObjetive)),
            MiniMessageUtil.parse(
                MiniMessageUtil.replaceLegacy(
                    titleandsubtitlefinal[1]),
                PlaceholderUtil.replaceProxyPlaceholders(playerObjetive)),
            playerObjetive,
            1000,
            3000,
            1000);
        SoundUtil.playProxyTitleSound(playerObjetive);
        ConfigUtils.sendTitleConfirmation(sender);
    }

    //TODO: Add Title Tab Complete
}
