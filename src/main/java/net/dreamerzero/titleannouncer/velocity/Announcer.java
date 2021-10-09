package net.dreamerzero.titleannouncer.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.event.Subscribe;

import net.dreamerzero.titleannouncer.common.utils.ConfigManager;
import net.dreamerzero.titleannouncer.velocity.utils.RegisterCommands;
import net.dreamerzero.titleannouncer.velocity.utils.SoundUtils;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class Announcer {
    private final ProxyServer server;
    private final MiniMessage mm;

    @Inject
    public Announcer(final ProxyServer server) {
        this.server = server;
        this.mm = MiniMessage.miniMessage();
    }

    @Subscribe
    public void onProxyInitialization(final ProxyInitializeEvent event) {
        server.getConsoleCommandSource().sendMessage(
            mm.parse("<aqua>Enabling</aqua> <gradient:yellow:blue>TitleAnnouncer</gradient>"));
        ConfigManager cManager = new ConfigManager();
		cManager.defaultConfig();
        cManager.defaultProxyConfig();
        RegisterCommands rCommands = new RegisterCommands(this, server, mm);
        rCommands.registerProxyMainCommand();
        rCommands.registerProxyBossbar();
        rCommands.registerProxyTitle();
        rCommands.registerProxyActionbar();
        if(server.getPluginManager().isLoaded("protocolize")){
            SoundUtils sUtils = new SoundUtils(server);
            sUtils.setActionBarSound();
            sUtils.setBossBarSound();
            sUtils.setTitleSound();
        }
    }
}
