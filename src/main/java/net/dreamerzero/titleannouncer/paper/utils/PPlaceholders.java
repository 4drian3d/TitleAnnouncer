package net.dreamerzero.titleannouncer.paper.utils;

import org.bukkit.Bukkit;

import static net.kyori.adventure.text.Component.text;

import net.kyori.adventure.text.minimessage.Template;
import net.kyori.adventure.text.minimessage.template.TemplateResolver;

public class PPlaceholders {
    /**
     * Replace Placeholders in Title/ActionBar
     * for the context of Console
     * @return Placeholders for console
     */
    public static TemplateResolver replacePlaceholders() {
        return TemplateResolver.templates(
            Template.template("online", text(Bukkit.getServer().getOnlinePlayers().size())),
            Template.template("mspt", text(String.valueOf(Bukkit.getAverageTickTime()/20).substring(0, 3))),
            Template.template("tps", text(String.valueOf(Bukkit.getTPS()[0]).substring(0, 4))));
    }
    /**
     * Replace Placeholders in Title/ActionBar
     * for player sender
     * @param player
     * @return Placeholders for sender player
     */
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
}
