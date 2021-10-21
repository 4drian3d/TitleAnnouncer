package net.dreamerzero.titleannouncer.velocity.commands.actionbar;

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
import net.kyori.adventure.text.minimessage.MiniMessage;

public class SelfActionbarCommand implements SimpleCommand{
    private final MiniMessage mm;
    private SoundUtils sUtils;
    private VPlaceholders vPlaceholders;
    public SelfActionbarCommand(ProxyServer server, MiniMessage mm){
        this.mm = mm;
        this.sUtils = new SoundUtils(server);
        this.vPlaceholders = new VPlaceholders(server);
    }
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

        sender.sendActionBar(mm.deserialize(
            MiniMessageUtil.replaceLegacy(actionbartext),
            vPlaceholders.replaceProxyPlaceholders(player)));
        sUtils.playProxySound(player, ComponentType.ACTIONBAR);
        ConfigUtils.sendConfirmation(ComponentType.ACTIONBAR, sender);
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
