package net.dreamerzero.titleannouncer.velocity.commands.actionbar;

import java.util.List;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;

import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.common.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.common.utils.PlaceholderUtil;
import net.dreamerzero.titleannouncer.common.utils.SoundUtil;

public class SelfActionbarCommand implements SimpleCommand{
    public SelfActionbarCommand() {}
    @Override
    public void execute(Invocation invocation) {
        CommandSource sender = invocation.source();
        String[] args = invocation.arguments();
        if(!(sender instanceof Player player)){
            ConfigUtils.onlyPlayerExecute(sender);
            return;
        }
        if(args.length == 0) {
            ConfigUtils.noActionbarArgumentProvided(sender);
            return;
        }
        // Concatenate the arguments provided by the command sent.
        String actionbartext = GeneralUtils.getCommandString(args);

        sender.sendActionBar(MiniMessageUtil.parse(
            MiniMessageUtil.replaceLegacy(actionbartext),
            PlaceholderUtil.replaceProxyPlaceholders(player)));
        SoundUtil.playProxyActionbarSound(player);
        ConfigUtils.sendActionbarConfirmation(sender);
    }

    @Override
    public List<String> suggest(final Invocation invocation) {
        return List.of("[message]");
    }

    @Override
    public boolean hasPermission(final Invocation invocation) {
        return invocation.source().hasPermission("titleannouncer.actionbar.self");
    }
}
