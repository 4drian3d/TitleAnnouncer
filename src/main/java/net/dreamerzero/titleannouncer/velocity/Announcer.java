package net.dreamerzero.titleannouncer.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.event.Subscribe;
import org.slf4j.Logger;

import net.dreamerzero.titleannouncer.common.utils.ConfigManager;
import net.dreamerzero.titleannouncer.velocity.commands.AnnouncerCommand;
import net.dreamerzero.titleannouncer.velocity.commands.title.AnnouncerTitleCommand;

public class Announcer {
    private final ProxyServer server;
    private final Logger logger;
    private static ProxyServer proxy;

    @Inject
    public Announcer(final ProxyServer server, final Logger logger) {
        this.server = server;
        this.logger = logger;
    }

    @Subscribe
    public void onProxyInitialization(final ProxyInitializeEvent event) {
        // :)
        logger.info("TitleAnnouncer has started, have a very nice day.");
        ConfigManager.createConfig();
		ConfigManager.defaultConfig();
        server.getCommandManager().register("vannouncer", new AnnouncerCommand());
        server.getCommandManager().register("vannouncetitle", new AnnouncerTitleCommand(server));
        proxy = server;
    }

    public static ProxyServer getProxyServer(){
        return proxy;
    }
}
