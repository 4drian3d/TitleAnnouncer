package net.dreamerzero.titleannouncer.velocity.commands.bossbar.persistent;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.ProxyServer;

import net.dreamerzero.titleannouncer.common.utils.BossBarUtils;
import net.dreamerzero.titleannouncer.velocity.Announcer;
import net.dreamerzero.titleannouncer.velocity.utils.VelocityBossbar;

public class RemovePersistentBossbarCommand implements SimpleCommand {
    private VelocityBossbar vbossbar;
    public RemovePersistentBossbarCommand(Announcer plugin, ProxyServer proxy){
        this.vbossbar = new VelocityBossbar(plugin, proxy);
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource sender = invocation.source();
        String[] args = invocation.arguments();
        String firstargument = args[0];

        if(args.length < 1 || !BossBarUtils.isValidArgument(firstargument)){
            return;
        }

        
        vbossbar.removePlayerBossbars(vbossbar.getObjetives(firstargument, sender));
        
        
    }
    
}
