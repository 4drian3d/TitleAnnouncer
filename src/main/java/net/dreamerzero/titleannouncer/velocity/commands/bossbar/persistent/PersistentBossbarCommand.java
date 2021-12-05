package net.dreamerzero.titleannouncer.velocity.commands.bossbar.persistent;

import java.util.Optional;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;

import net.dreamerzero.titleannouncer.common.utils.BossBarUtils;
import net.dreamerzero.titleannouncer.common.utils.ComponentType;
import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.velocity.Announcer;
import net.dreamerzero.titleannouncer.velocity.utils.SoundUtils;
import net.dreamerzero.titleannouncer.velocity.utils.VelocityBossbar;
import net.dreamerzero.titleannouncer.velocity.utils.VelocityPlaceholders;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class PersistentBossbarCommand implements SimpleCommand {
    private ProxyServer proxy;
    private Announcer plugin;
    private VelocityPlaceholders placeholders;
    private SoundUtils sUtils;
    MiniMessage mm;
    public PersistentBossbarCommand(ProxyServer proxy, Announcer plugin){
        this.plugin = plugin;
        this.proxy = proxy;
        this.placeholders = new VelocityPlaceholders(proxy);
        this.sUtils = new SoundUtils(proxy);
        this.mm = MiniMessage.miniMessage();
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource sender = invocation.source();
        String[] args = invocation.arguments();
        VelocityBossbar vBossbar = new VelocityBossbar(plugin, proxy);

        // The command requires arguments to work
        if (!BossBarUtils.sendBossbarArgs(args.length, sender)) {
            return;
        }

        Optional<Player> optionalPlayerObjetive = proxy.getPlayer(args[0]);
        if(!optionalPlayerObjetive.isPresent()) {
            ConfigUtils.playerNotFoundMessage(sender);
            return;
        }
        Player playerObjetive = optionalPlayerObjetive.get();

        float time = BossBarUtils.validBossbarNumber(args[1], sender);
        if(time == 0.1f) return;

        BossBar.Color color = BossBarUtils.bossbarColor(args[2]);
        BossBar.Overlay overlay = BossBarUtils.bossbarOverlay(args[3]);

        if (color == null || overlay == null) {
            sender.sendMessage(ConfigUtils.getPrefix().append(mm.deserialize("<dark_red>Invalid Argument")));
            return;
        }

        // Concatenate the arguments provided by the command sent.
        String bossbartext = GeneralUtils.getCommandString(args, 5);

        vBossbar.sendPersistentBossbar(
            playerObjetive,
            100,
            placeholders.applyPlaceholders(bossbartext, playerObjetive),
            color,
            overlay);
        ConfigUtils.sendConfirmation(ComponentType.BOSSBAR, sender);
        sUtils.playProxySound(playerObjetive, ComponentType.BOSSBAR);
        
    }
    
}
