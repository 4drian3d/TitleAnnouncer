package net.dreamerzero.titleannouncer.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.event.Subscribe;
import org.slf4j.Logger;

import net.dreamerzero.titleannouncer.common.utils.ConfigManager;
import net.dreamerzero.titleannouncer.velocity.commands.actionbar.SelfActionbarCommand;
import net.dreamerzero.titleannouncer.velocity.commands.AnnouncerCommand;
import net.dreamerzero.titleannouncer.velocity.commands.actionbar.AnnouncerActionbarCommand;
import net.dreamerzero.titleannouncer.velocity.commands.actionbar.SendActionbarCommand;
import net.dreamerzero.titleannouncer.velocity.commands.bossbar.AnnouncerBossbarCommand;
import net.dreamerzero.titleannouncer.velocity.commands.bossbar.SelfBossbarCommand;
import net.dreamerzero.titleannouncer.velocity.commands.bossbar.SendBossbarCommand;
import net.dreamerzero.titleannouncer.velocity.commands.bossbar.ServerBossbarCommand;
import net.dreamerzero.titleannouncer.velocity.commands.title.AnnouncerTitleCommand;
import net.dreamerzero.titleannouncer.velocity.commands.title.SelfTitleCommand;
import net.dreamerzero.titleannouncer.velocity.commands.title.SendTitleCommand;
import net.dreamerzero.titleannouncer.velocity.commands.title.ServerTitleCommand;

public class Announcer {
    private final ProxyServer server;
    private final Logger logger;
    private static ProxyServer proxy;
    private static Announcer instance;

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
        server.getCommandManager().register("vselftitle", new SelfTitleCommand());
        server.getCommandManager().register("vsendtitle", new SendTitleCommand(server));
        server.getCommandManager().register("vservertitle", new ServerTitleCommand(server));

        server.getCommandManager().register("vannouncebossbar", new AnnouncerBossbarCommand(server));
        server.getCommandManager().register("vselfbossbar", new SelfBossbarCommand());
        server.getCommandManager().register("vsendbossbar", new SendBossbarCommand(server));
        server.getCommandManager().register("vserverbossbar", new ServerBossbarCommand(server));

        server.getCommandManager().register("vannounceactionbar", new AnnouncerActionbarCommand(server));
        server.getCommandManager().register("vselfactionbar", new SelfActionbarCommand());
        server.getCommandManager().register("vsendactionbar", new SendActionbarCommand(server));
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
