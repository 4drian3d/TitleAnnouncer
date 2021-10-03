package net.dreamerzero.titleannouncer.velocity.commands.title;

import java.util.List;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;

import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.common.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.common.utils.PlaceholderUtil;
import net.dreamerzero.titleannouncer.common.utils.ComponentType;
import net.dreamerzero.titleannouncer.velocity.utils.SoundUtils;
import net.dreamerzero.titleannouncer.common.utils.TitleUtil;

public class SelfTitleCommand implements SimpleCommand {
    public SelfTitleCommand() {}
    @Override
    public void execute(Invocation invocation) {
        CommandSource sender = invocation.source();
        String[] args = invocation.arguments();
        ConfigUtils config = new ConfigUtils();
        MiniMessageUtil mUtils = new MiniMessageUtil();

        if (!(sender instanceof Player player)) {
            config.onlyPlayerExecute(sender);
            return;
        }

        if(args.length == 0) {
            config.sendNoArgumentMessage(sender);
            return;
        }

        // Concatenate the arguments provided by the command sent.
        String titleandsubtitle = new GeneralUtils().getCommandString(args);

        SoundUtils sUtils = new SoundUtils();
        TitleUtil tUtil = new TitleUtil();

        if(!titleandsubtitle.contains(";")){
            tUtil.sendOnlySubtitle(
                mUtils.parse(mUtils.replaceLegacy(titleandsubtitle),
                    PlaceholderUtil.replaceProxyPlaceholders(player)),
                sender, 1000, 3000, 1000);
            config.sendConfirmation(ComponentType.TITLE, sender);
            sUtils.playProxySound(player, ComponentType.TITLE);
            return;
        }

        String titleandsubtitlefinal[] = tUtil.getTitleAndSubtitle(titleandsubtitle, sender);

        if(titleandsubtitlefinal == null) return;

        // Send the title
        tUtil.sendTitle(
            mUtils.parse(
                mUtils.replaceLegacy(titleandsubtitlefinal[0]),
                PlaceholderUtil.replaceProxyPlaceholders(player)),
            mUtils.parse(
                mUtils.replaceLegacy(titleandsubtitlefinal[1]),
                PlaceholderUtil.replaceProxyPlaceholders(player)),
            sender,
            1000,
            3000,
            1000);
        sUtils.playProxySound(player, ComponentType.TITLE);
        config.sendConfirmation(ComponentType.TITLE, sender);
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
        return invocation.source().hasPermission("titleannouncer.title.self");
    }
}
