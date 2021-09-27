package net.dreamerzero.titleannouncer.velocity.commands.actionbar;

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
            sender.sendMessage(MiniMessageUtil.parse("The console cannot execute this command."));
            return;
        }
        // Concatenate the arguments provided by the command sent.
        String actionbartext = GeneralUtils.getCommandString(args);

        sender.sendActionBar(MiniMessageUtil.parse(
            MiniMessageUtil.replaceLegacy(actionbartext),
            PlaceholderUtil.replaceProxyPlaceholders(player)));
        SoundUtil.playProxyActionbarSound(player);
        ConfigUtils.sendActionbarConfirmation(sender);
        return;
    }
}
