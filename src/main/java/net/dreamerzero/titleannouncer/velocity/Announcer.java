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
        server.getConsoleCommandSource().sendMessage(
            MiniMessageUtil.parse("<aqua>Enabling</aqua> <gradient:yellow:blue>TitleAnnouncer</gradient>"));
        ConfigManager.createConfig();
		ConfigManager.defaultConfig();
        ConfigManager.defaultProxyConfig();
        RegisterCommands.registerProxyMainCommand(server);
        RegisterCommands.registerProxyBossbar(server);
        RegisterCommands.registerProxyTitle(server);
        RegisterCommands.registerProxyActionbar(server);
        SoundUtils.setActionBarSound();
        SoundUtils.setBossBarSound();
        SoundUtils.setTitleSound();
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
