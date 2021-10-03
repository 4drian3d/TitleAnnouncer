package net.dreamerzero.titleannouncer.common.utils;

import de.leonhard.storage.Yaml;
import de.leonhard.storage.internal.settings.ReloadSettings;

public class ConfigManager {
    private Yaml config;

    public ConfigManager(){
        config = new Yaml("config", "plugins/TitleAnnouncer");
    }

    public void defaultConfig(){
        config.setDefault("messages.prefix.enabled", true);
        config.setDefault(
            "messages.title.error",
            "<dark_red>An error occurred while sending the title. Be sure to use the ';' to separate the title and the subtitle.</dark_red>");
        config.setDefault(
            "messages.title.successfully",
            "<green>Title succesfully sended</green>");
        config.setDefault(
            "messages.title.without-argument",
            "<red>You need to enter the title and subtitle arguments.</red>");
        config.setDefault(
            "messages.title.only-player",
            "<gray>You must enter the title and subtitle after the player's name to send the message correctly.</gray>");
        config.setDefault("sounds.title.sound-id", "entity.experience_orb.pickup");
        config.setDefault("sounds.title.enabled", true);
        config.setDefault("sounds.title.volume", 10);
        config.setDefault("sounds.title.pitch", 2);
        config.setDefault(
            "messages.actionbar.successfully",
            "<green>Actionbar succesfully sended</green>");
        config.setDefault(
            "messages.actionbar.without-argument",
            "<red>You need to enter the message to announce.</red>");
        config.setDefault(
            "messages.actionbar.only-player",
            "<gray>You must enter the message to be sent after the player's name.</gray>");
        config.setDefault("sounds.actionbar.enabled", true);
        config.setDefault("sounds.actionbar.sound-id", "entity.experience_orb.pickup");
        config.setDefault("sounds.actionbar.volume", 10);
        config.setDefault("sounds.actionbar.pitch", 2);
        config.setDefault(
            "messages.bossbar.successfully",
            "<green>Bossbar succesfully sended</green>");
        config.setDefault(
            "messages.bossbar.without-argument",
            "<red>You need to enter the time, color and message arguments.</red>");
        config.setDefault(
            "messages.bossbar.only-time",
            "<gray>You must enter the color and the message arguments.</gray>");
        config.setDefault(
            "messages.bossbar.overlay-missing",
            "<gray>You must enter the overlay and the message arguments.</gray>");
        config.setDefault(
            "messages.bossbar.without-message",
            "<gray>You need to enter the message to announce.</gray>");
        config.setDefault(
            "messages.bossbar.only-player",
            "<gray>You must enter the message to be sent after the player's name.</gray>");
        config.setDefault("sounds.bossbar.enabled", true);
        config.setDefault("sounds.bossbar.sound-id", "entity.experience_orb.pickup");
        config.setDefault("sounds.bossbar.volume", 10);
        config.setDefault("sounds.bossbar.pitch", 2);

        config.setDefault(
            "messages.general.no-permission",
            "<red>You do not have permission to execute this command</red>");
        config.setDefault("messages.general.player-not-found", "<red>Player not found</red>");
        config.setDefault("messages.general.help-message", "<white>Available Commands:</white>");
        config.setDefault("messages.general.invalid-command", "<red>Unknown Command</red>");
        config.setDefault("messages.general.reload-config", "<green>Config Reloaded</green>");
        config.setDefault("messages.general.no-console", "<red>The console cannot execute this command</red>");
        config.setDefault("messages.prefix.line", "<gray>[</gray><gradient:yellow:blue>TitleAnnouncer</gradient><gray>]</gray> ");
        config.setHeader("""
        TitleAnnouncer | by 4drian3d

        # To modify the plugin messages and to use the plugin in general,
        # I recommend that you have a basic knowledge of MiniMessage.
        # Guide: https://docs.adventure.kyori.net/minimessage.html#format
        # Spanish Guide: https://gist.github.com/4drian3d/9ccce0ca1774285e38becb09b73728f3

        """);
        config.setReloadSettings(ReloadSettings.MANUALLY);
    }
    public void defaultProxyConfig(){
        config.setDefault(
            "messages.general.server-not-found",
            "<red>Server not found</red>");
        config.setDefault(
            "messages.general.no-server-provided",
            "<red>No server provided to send the message</red>");
    }

    public Yaml getConfig(){
        return config;
    }
}
