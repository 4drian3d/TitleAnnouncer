package net.dreamerzero.titleannouncer.velocity.utils;

import java.util.Arrays;
import java.util.List;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;

import net.kyori.adventure.text.minimessage.Template;
import net.kyori.adventure.text.minimessage.template.TemplateResolver;

public record VPlaceholders(ProxyServer proxy) {

    public TemplateResolver replaceProxyPlaceholders(){
        return TemplateResolver.templates(
            Template.template("online", String.valueOf(proxy.getPlayerCount())),
            Template.template("servers", String.valueOf(proxy.getAllServers().size())));
    }

    public TemplateResolver replaceProxyPlaceholders(Player player){
        List<Template> templates = Arrays.asList(
            Template.template("online", String.valueOf(proxy.getPlayerCount())),
            Template.template("servers", String.valueOf(proxy.getAllServers().size())),
            Template.template("name", player.getUsername()),
            Template.template("ping", String.valueOf(player.getPing())),
            Template.template("client", player.getClientBrand()),
            Template.template("locale", player.getEffectiveLocale().getDisplayLanguage()),
            Template.template("server", player.getCurrentServer().get().getServerInfo().getName()),
            Template.template("version", player.getProtocolVersion().getMostRecentSupportedVersion()));
        if(player.getModInfo().isPresent()) {
            templates.add(Template.template("mods", player.getModInfo().get().getMods().toString()));
        }
        return TemplateResolver.templates(templates);
    }
}
