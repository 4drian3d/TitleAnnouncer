package net.dreamerzero.TitleAnnouncer.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import net.dreamerzero.TitleAnnouncer.Announcer;
import net.dreamerzero.TitleAnnouncer.utils.MiniMessageUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AnnouncerCommand implements CommandExecutor {

    private final Announcer plugin;
    private final FileConfiguration config;
    private final List<Component> commands = new ArrayList<>();

	public AnnouncerCommand(Announcer plugin) {
		this.plugin = plugin;
        this.config = plugin.getConfig();
	}

    // Main Command
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        boolean enabledPrefix = config.getBoolean("messages.prefix.enabled", true);
        Component prefix = Component.text("");
        Component announce = MiniMessageUtil.parse(
                "<gradient:yellow:blue>TitleAnnouncer</gradient> <gray>by</gray> <gradient:green:yellow>4drian3d</gradient>"
        );

        // Help commands of plugin
        commands.add(Component.text("Title:", NamedTextColor.AQUA));
        commands.add(Component.text("/announcetitle [Title]; [Subtitle]", NamedTextColor.GOLD));
        commands.add(Component.text("/selftitle [Title]; [Subtitle]", NamedTextColor.GOLD));
        commands.add(Component.text("/worldtitle [Title]; [Subtitle]", NamedTextColor.GOLD));
        commands.add(Component.text("ActionBar: ", NamedTextColor.AQUA));
        commands.add(Component.text("/announceactionbar [Actionbar]", NamedTextColor.GOLD));
        commands.add(Component.text("/selfactionbar [Actionbar]", NamedTextColor.GOLD));
        commands.add(Component.text("/worldactionbar [Actionbar]", NamedTextColor.GOLD));
        commands.add(Component.text("/sendactionbar [Player] [Actionbar]", NamedTextColor.GOLD));


        if (enabledPrefix){
            prefix = MiniMessageUtil.parse(config.getString(
                "messages.prefix.line", 
                "<gray>[</gray><gradient:yellow:blue>TitleAnnouncer</gradient><gray>]</gray>"));
        }

        if (!(sender.hasPermission("announcer.command.show"))) {
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    config.getString(
                        "messages.general.no-permission", 
                        "<red>You do not have permission to execute this command</red>"))));
            return false;
        }
        if (!(sender.hasPermission("announcer.command.admin"))){
            sender.sendMessage(announce);
            return false;
        }

        if(args.length == 0) {
            sender.sendMessage(announce);
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    config.getString(
                        "messages.general.help-message", 
                        "<white>Available Commands:</white>"))));

            for (Component component : commands) {
                sender.sendMessage(component);
            }
            return true;
        }

        if(args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    config.getString(
                        "messages.general.reload-config", 
                        "<green>Config Reloaded</green>"))));
            return true;
        } else if (args[0].equalsIgnoreCase("help")){
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    config.getString(
                        "messages.general.help-message", 
                        "<white>Available Commands:</white>"))));
            for (Component component : commands) {
                sender.sendMessage(component);
            }
            return true;
        } else {
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    config.getString(
                        "messages.general.invalid-command", 
                        "<red>Unknown Command</red>"))));
        }
        return true;
    }
}
