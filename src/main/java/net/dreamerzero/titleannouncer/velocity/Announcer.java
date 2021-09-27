package net.dreamerzero.titleannouncer.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.event.Subscribe;
import org.slf4j.Logger;

import net.dreamerzero.titleannouncer.common.utils.ConfigManager;
import net.dreamerzero.titleannouncer.velocity.commands.AnnouncerCommand;

public class Announcer {
    private final ProxyServer server;
    private final Logger logger;

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
    }
}
