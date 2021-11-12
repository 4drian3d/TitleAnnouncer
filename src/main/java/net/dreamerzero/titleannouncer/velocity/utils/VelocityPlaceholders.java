package net.dreamerzero.titleannouncer.velocity.utils;

import java.util.Arrays;
import java.util.List;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;

import org.jetbrains.annotations.NotNull;

import net.dreamerzero.titleannouncer.common.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.common.utils.PlaceholderProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.Template;
import net.kyori.adventure.text.minimessage.template.TemplateResolver;

public class VelocityPlaceholders implements PlaceholderProvider {
    private final ProxyServer proxy;
    public VelocityPlaceholders(ProxyServer proxy){
        this.proxy = proxy;
    }

    @Deprecated(forRemoval = true)
    public TemplateResolver replaceProxyPlaceholders(){
        return TemplateResolver.templates(
            Template.template("online", String.valueOf(proxy.getPlayerCount())),
            Template.template("servers", String.valueOf(proxy.getAllServers().size()))
        );
    }

    public TemplateResolver replacePlaceholders(Player player){
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
    
    @Deprecated(forRemoval = true)
    public TemplateResolver replaceProxyPlaceholders(Player player){
        return replacePlaceholders(player);
    }

    @Override
    public TemplateResolver replacePlaceholders() {
        return TemplateResolver.templates(
            Template.template("online", String.valueOf(proxy.getPlayerCount())),
            Template.template("servers", String.valueOf(proxy.getAllServers().size()))
        );
    }

    @Override
    public Component applyPlaceholders(@NotNull String string) {
        return minimessage().deserialize(
            MiniMessageUtil.replaceLegacy(string),
            replacePlaceholders()
        );
    }

    public Component applyPlaceholders(@NotNull String string, @NotNull Player player) {
        return minimessage().deserialize(
            MiniMessageUtil.replaceLegacy(string),
            replacePlaceholders(player)
        );
    }
}
