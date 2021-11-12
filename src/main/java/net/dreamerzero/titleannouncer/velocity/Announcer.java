package net.dreamerzero.titleannouncer.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;

import de.leonhard.storage.Yaml;

import com.velocitypowered.api.event.Subscribe;

import net.dreamerzero.titleannouncer.common.utils.Constants;
import net.dreamerzero.titleannouncer.common.utils.ConfigManager;
import net.dreamerzero.titleannouncer.velocity.utils.RegisterCommands;
import net.dreamerzero.titleannouncer.velocity.utils.SoundUtils;
import net.kyori.adventure.text.minimessage.MiniMessage;

@Plugin(
    id = "titleannouncer",
    name = Constants.NAME,
    version = Constants.VERSION,
    description = Constants.DESCRIPTION,
    authors = {"4drian3d"},
    url = Constants.URL,
    dependencies = {
        @Dependency(id = "protocolize", optional = true),
        @Dependency(id = "protocolizelegacysupport", optional = true)
    })
public class Announcer {
    private final ProxyServer server;
    private final MiniMessage mm;
    private static Yaml config = new Yaml("config", "plugins/TitleAnnouncer");

    @Inject
    public Announcer(final ProxyServer server) {
        this.server = server;
        this.mm = MiniMessage.miniMessage();
    }

    @Subscribe
    public void onProxyInitialization(final ProxyInitializeEvent event) {
        server.getConsoleCommandSource().sendMessage(
            mm.deserialize("<aqua>Enabling</aqua> <gradient:yellow:blue>TitleAnnouncer</gradient>"));
        new ConfigManager(config);
		ConfigManager.defaultConfig();
        ConfigManager.defaultProxyConfig();
        RegisterCommands rCommands = new RegisterCommands(this, server, mm);
        rCommands.registerProxyMainCommand();
        rCommands.registerProxyBossbar();
        rCommands.registerProxyTitle();
        rCommands.registerProxyActionbar();
        if(server.getPluginManager().isLoaded("protocolize")){
            SoundUtils.setActionBarSound();
            SoundUtils.setBossBarSound();
            SoundUtils.setTitleSound();
        }
    }
}
