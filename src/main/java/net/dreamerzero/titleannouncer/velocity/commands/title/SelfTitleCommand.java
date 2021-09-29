package net.dreamerzero.titleannouncer.velocity.commands.title;

import java.util.List;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;

import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.common.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.common.utils.PlaceholderUtil;
import net.dreamerzero.titleannouncer.velocity.utils.SoundType;
import net.dreamerzero.titleannouncer.velocity.utils.SoundUtils;
import net.dreamerzero.titleannouncer.common.utils.TitleUtil;

public class SelfTitleCommand implements SimpleCommand {
    public SelfTitleCommand() {}
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
                MiniMessageUtil.parse(
                MiniMessageUtil.replaceLegacy(
                    titleandsubtitle),
                    PlaceholderUtil.replaceProxyPlaceholders(player)),
                    player, 1000, 3000, 1000);
            ConfigUtils.sendTitleConfirmation(sender);
            SoundUtils.playProxySound(player, SoundType.TITLE);
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
                PlaceholderUtil.replaceProxyPlaceholders(player)),
            MiniMessageUtil.parse(
                MiniMessageUtil.replaceLegacy(
                    titleandsubtitlefinal[1]),
                PlaceholderUtil.replaceProxyPlaceholders(player)),
            player,
            1000,
            3000,
            1000);
        SoundUtils.playProxySound(player, SoundType.TITLE);
        ConfigUtils.sendTitleConfirmation(sender);
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
        return invocation.source().hasPermission("titleannouncer.title.self");
    }
}
