package net.dreamerzero.titleannouncer.velocity.commands.title;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

public record SendTitleCommand(ProxyServer server) implements SimpleCommand {

    @Override
    public void execute(Invocation invocation) {
        CommandSource sender = invocation.source();
        String[] args = invocation.arguments();
        ConfigUtils config = new ConfigUtils();
        MiniMessageUtil mUtils = new  MiniMessageUtil();
        VPlaceholders vPlaceholders = new VPlaceholders(server);

        switch (args.length) {
            case 0 -> {
                config.sendNoArgumentMessage(sender);
                return;
            }
            case 1 -> {
                config.noTitlePlayerArgumentProvided(sender);
                return;
            }
        }

        Optional<Player> optionalPlayerObjetive = server.getPlayer(args[0]);
        if(!optionalPlayerObjetive.isPresent()) {
            config.playerNotFoundMessage(sender);
            return;
        }
        Player playerObjetive = optionalPlayerObjetive.get();

        // Concatenate the arguments provided by the command sent.
        String titleandsubtitle = new GeneralUtils().getCommandString(args, 1);

        SoundUtils sUtils = new SoundUtils(server);
        TitleUtil tUtil = new TitleUtil();

        if(!tUtil.containsComma(args, 1)){
            tUtil.sendOnlySubtitle(
                mUtils.parse(mUtils.replaceLegacy(titleandsubtitle),
                    vPlaceholders.replaceProxyPlaceholders(playerObjetive)),
                playerObjetive, 1000, 3000, 1000);
            config.sendConfirmation(ComponentType.TITLE, sender);
            sUtils.playProxySound(playerObjetive, ComponentType.TITLE);
            return;
        }

        String titleandsubtitlefinal[] = tUtil.getTitleAndSubtitle(titleandsubtitle, sender);

        if(titleandsubtitlefinal == null) return;

        // Send the title
        tUtil.sendTitle(
            mUtils.parse(
                mUtils.replaceLegacy(titleandsubtitlefinal[0]),
                vPlaceholders.replaceProxyPlaceholders(playerObjetive)),
            mUtils.parse(
                mUtils.replaceLegacy(titleandsubtitlefinal[1]),
                vPlaceholders.replaceProxyPlaceholders(playerObjetive)),
            playerObjetive,
            1000,
            3000,
            1000);
            sUtils.playProxySound(playerObjetive, ComponentType.TITLE);
        config.sendConfirmation(ComponentType.TITLE, sender);
    }

    @Override
    public List<String> suggest(final Invocation invocation) {
        if (invocation.arguments().length <= 2){
            ArrayList<String> players = new ArrayList<>();
            server.getAllPlayers().forEach(player -> players.add(player.getUsername()));
            return players;
        } else if (!new TitleUtil().containsComma(invocation.arguments())){
            return List.of("[Title]");
        } else {
            return List.of("[SubTitle]");
        }
    }

    @Override
    public boolean hasPermission(final Invocation invocation) {
        return invocation.source().hasPermission("titleannouncer.title.send");
    }
}
