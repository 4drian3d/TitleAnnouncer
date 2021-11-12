package net.dreamerzero.titleannouncer.paper.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dreamerzero.titleannouncer.common.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.common.utils.PlaceholderProvider;
import net.dreamerzero.titleannouncer.paper.Announcer;

import static net.kyori.adventure.text.Component.text;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.Template;
import net.kyori.adventure.text.minimessage.template.TemplateResolver;

public class PaperPlaceholders implements PlaceholderProvider {
    /**
     * Replace Placeholders in Title/ActionBar
     * for the context of Console
     * @return Placeholders for console
     * @deprecated replaced
     */
    @Deprecated(forRemoval = true)
    @Override
    public TemplateResolver replacePlaceholders() {
        return TemplateResolver.templates(
            Template.template("online", text(Bukkit.getServer().getOnlinePlayers().size())),
            Template.template("mspt", text(String.valueOf(Bukkit.getAverageTickTime()/20).substring(0, 3))),
            Template.template("tps", text(Bukkit.getTPS()[0]))
        );
    }
    /**
     * Replace Placeholders in Title/ActionBar
     * for player sender
     * @param player
     * @return Placeholders for sender player
     * @deprecated replaced
     */
    @Deprecated(forRemoval = true)
    public static TemplateResolver replacePlaceholders(org.bukkit.entity.Player player) {
        return TemplateResolver.templates(
            Template.template("name", text(player.getName())),
            Template.template("ping", text(String.valueOf(player.getPing()))),
            Template.template("online", text(Bukkit.getServer().getOnlinePlayers().size())),
            Template.template("world", text(player.getWorld().getName())),
            Template.template("player", text(player.getName())),
            Template.template("mspt", text(String.valueOf(Bukkit.getAverageTickTime()/20).substring(0, 3))),
            Template.template("tps", text(String.valueOf(Bukkit.getTPS()[0]).substring(0, 4))));
    }

    @Override
    public Component applyPlaceholders(@NotNull String string) {
        return minimessage().deserialize(
            MiniMessageUtil.replaceLegacy(Announcer.placeholderAPIHook()
                ? PlaceholderAPI.setPlaceholders(null, string)
                : string)
            , TemplateResolver.templates(
                Template.template("online", text(Bukkit.getServer().getOnlinePlayers().size())),
                Template.template("mspt", text(String.valueOf(Bukkit.getAverageTickTime()/20).substring(0, 3))),
                Template.template("tps", text(Bukkit.getTPS()[0])))
        );
    }

    public Component applyPlaceholders(@NotNull String string, @NotNull Player player) {
        return minimessage().deserialize(
            MiniMessageUtil.replaceLegacy(Announcer.placeholderAPIHook()
                ? PlaceholderAPI.setPlaceholders(player, string)
                : string)
            , TemplateResolver.templates(
                Template.template("name", text(player.getName())),
                Template.template("ping", text(String.valueOf(player.getPing()))),
                Template.template("online", text(Bukkit.getServer().getOnlinePlayers().size())),
                Template.template("world", text(player.getWorld().getName())),
                Template.template("player", text(player.getName())),
                Template.template("mspt", text(String.valueOf(Bukkit.getAverageTickTime()/20).substring(0, 3))),
                Template.template("tps", text(String.valueOf(Bukkit.getTPS()[0]).substring(0, 4))))
        );
    }
}
