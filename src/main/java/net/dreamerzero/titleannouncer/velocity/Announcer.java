package net.dreamerzero.titleannouncer.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.event.Subscribe;

import net.dreamerzero.titleannouncer.common.utils.ConfigManager;
import net.dreamerzero.titleannouncer.common.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.velocity.utils.RegisterCommands;
import net.dreamerzero.titleannouncer.velocity.utils.SoundUtils;

public class Announcer {
    private final ProxyServer server;
    private static ProxyServer proxy;
    private static Announcer instance;

    @Inject
    public Announcer(final ProxyServer server) {
        this.server = server;
    }

    @Subscribe
    public void onProxyInitialization(final ProxyInitializeEvent event) {
        SoundUtils sUtils = new SoundUtils();
        server.getConsoleCommandSource().sendMessage(
            new MiniMessageUtil().parse("<aqua>Enabling</aqua> <gradient:yellow:blue>TitleAnnouncer</gradient>"));
        ConfigManager cManager = new ConfigManager();
		cManager.defaultConfig();
        cManager.defaultProxyConfig();
        RegisterCommands.registerProxyMainCommand(server);
        RegisterCommands.registerProxyBossbar(server);
        RegisterCommands.registerProxyTitle(server);
        RegisterCommands.registerProxyActionbar(server);
        sUtils.setActionBarSound();
        sUtils.setBossBarSound();
        sUtils.setTitleSound();
        proxy = server;
        instance = this;
    }

    public static ProxyServer getProxyServer(){
        return proxy;
    }

    public static Announcer getVInstance(){
        return instance;
    }
}
