package net.dreamerzero.titleannouncer.velocity.utils;

import java.util.Arrays;
import java.util.List;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;

import net.kyori.adventure.text.minimessage.Template;

public class VPlaceholders {
    private ProxyServer proxy;
    public VPlaceholders(ProxyServer proxy){
        this.proxy = proxy;
    }
    public List<Template> replaceProxyPlaceholders(){
        return List.of(
            Template.of("online", String.valueOf(proxy.getPlayerCount())),
            Template.of("servers", String.valueOf(proxy.getAllServers().size())));
    }

    public List<Template> replaceProxyPlaceholders(Player player){
        List<Template> templates = Arrays.asList(
            Template.of("online", String.valueOf(proxy.getPlayerCount())),
            Template.of("servers", String.valueOf(proxy.getAllServers().size())),
            Template.of("name", player.getUsername()),
            Template.of("ping", String.valueOf(player.getPing())),
            Template.of("client", player.getClientBrand()),
            Template.of("locale", player.getEffectiveLocale().getDisplayLanguage()),
            Template.of("server", player.getCurrentServer().get().getServerInfo().getName()),
            Template.of("version", player.getProtocolVersion().getMostRecentSupportedVersion()));
        if(player.getModInfo().isPresent()) {
            templates.add(Template.of("mods", player.getModInfo().get().getMods().toString()));
        }
        return templates;
    }
}
