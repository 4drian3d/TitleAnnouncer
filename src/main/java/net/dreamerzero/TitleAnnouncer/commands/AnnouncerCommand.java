package net.dreamerzero.titleannouncer.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.dreamerzero.titleannouncer.Announcer;
import net.dreamerzero.titleannouncer.utils.MiniMessageUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.newline;
import static net.kyori.adventure.text.Component.space;
import static net.kyori.adventure.text.format.NamedTextColor.AQUA;
import static net.kyori.adventure.text.format.NamedTextColor.YELLOW;
import static net.kyori.adventure.text.format.NamedTextColor.GOLD;
import org.jetbrains.annotations.NotNull;

public class AnnouncerCommand implements CommandExecutor {
    private final Announcer plugin;

    public AnnouncerCommand(Announcer plugin) {
        this.plugin = plugin;
    }

    final static Component titleArguments = text("[Title]; [SubTitle]", AQUA);
    final static Component actionbarArguments = text("[ActionBar]", AQUA);
    final static Component bossbarArguments = text("[Time] [Color] [Style] [BossBar]", AQUA);

    final static Component titleHelpMessage = text()
        .append(text()
            .append(text("Title", YELLOW))
        )
        .append(newline())
        .append(text()
            .append(text("/announcetitle", GOLD))
        )
        .append(space())
        .append(text()
            .append(titleArguments)
        )
        .append(newline())
        .append(text()
            .append(text("/selftitle", GOLD))
        )
        .append(space())
        .append(text()
            .append(titleArguments)
        )
        .append(newline())
        .append(text()
            .append(text("/worldtitle", GOLD))
        )
        .append(space())
        .append(text()
            .append(titleArguments)
        )
        .append(newline())
        .append(text()
            .append(text("/sendtitle", GOLD))
        )
        .append(space())
        .append(text()
            .append(text("[Player]", AQUA))
        )
        .append(space())
        .append(text()
            .append(titleArguments)
        )
        .build();

    final static Component actionbarHelpMessage = text()
        .color(YELLOW)
        .append(text()
            .append(text("ActionBar"))
        )
        .append(newline())
        .append(text()
            .append(text("/announceactionbar", GOLD))
        )
        .append(space())
        .append(text()
            .append(actionbarArguments)
        )
        .append(newline())
        .append(text()
            .append(text("/selfactionbar", GOLD))
        )
        .append(space())
        .append(text()
            .append(actionbarArguments)
        )
        .append(newline())
        .append(text()
            .append(text("/worldactionbar", GOLD))
        )
        .append(space())
        .append(text()
            .append(actionbarArguments)
        )
        .append(newline())
        .append(text()
            .append(text("/sendactionbar", GOLD))
        )
        .append(space())
        .append(text()
            .append(text("[Player]", AQUA))
        )
        .append(space())
        .append(text()
            .append(actionbarArguments)
        )
        .build();

        final static Component bossbarHelpMessage = text()
        .color(YELLOW)
        .append(text()
            .append(text("BossBar"))
        )
        .append(newline())
        .append(text()
            .append(text("/announcebossbar", GOLD))
        )
        .append(space())
        .append(text()
            .append(bossbarArguments)
        )
        .append(newline())
        .append(text()
            .append(text("/selfbossbar", GOLD))
        )
        .append(space())
        .append(text()
            .append(bossbarArguments)
        )
        .append(newline())
        .append(text()
            .append(text("/worldbossbar", GOLD))
        )
        .append(space())
        .append(text()
            .append(bossbarArguments)
        )
        .append(newline())
        .append(text()
            .append(text("/sendbossbar", GOLD))
        )
        .append(space())
        .append(text()
            .append(text("[Player]", AQUA))

        )
        .append(space())
        .append(text()
            .append(bossbarArguments)
        )
        .build();

        final static Component fullwikilink = text()
            .append(text()
                .append(Component.text("Visit full guide on")))
            .append(space())
            .append(text()
                .append(text("WIKI"))
                .clickEvent(ClickEvent.openUrl("https://github.com/4drian3d/TitleAnnouncer/wiki"))
                .hoverEvent(HoverEvent.showText(
                    MiniMessageUtil.parse("<gradient:red:blue>Click Here</gradient>"))))
            .build();
        final static Component titlewikilink = text()
            .append(text()
                .append(Component.text("Visit full guide on")))
            .append(space())
            .append(text()
                .append(text("WIKI"))
                .clickEvent(ClickEvent.openUrl("https://github.com/4drian3d/TitleAnnouncer/wiki/Title-Commands"))
                .hoverEvent(HoverEvent.showText(
                    MiniMessageUtil.parse("<gradient:red:blue>Click Here</gradient>"))))
                .build();
        final static Component actionbarwikilink = text()
            .append(text()
                .append(Component.text("Visit full guide on")))
            .append(space())
            .append(text()
                .append(text("WIKI"))
                .clickEvent(ClickEvent.openUrl("https://github.com/4drian3d/TitleAnnouncer/wiki/ActionBar-Commands"))
                .hoverEvent(HoverEvent.showText(
                    MiniMessageUtil.parse("<gradient:red:blue>Click Here</gradient>"))))
                .build();

        final static Component bossbarwikilink = text()
            .append(text()
                .append(Component.text("Visit full guide on")))
            .append(space())
            .append(text()
                .append(text("WIKI"))
                .clickEvent(ClickEvent.openUrl("https://github.com/4drian3d/TitleAnnouncer/wiki/Bossbar-Commands"))
                .hoverEvent(HoverEvent.showText(
                    MiniMessageUtil.parse("<gradient:red:blue>Click Here</gradient>"))))
                .build();

    // Main Command
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        var enabledPrefix = plugin.getConfig().getBoolean("messages.prefix.enabled", true);

        Component prefix = text("");
        final Component announce = MiniMessageUtil.parse(
                "<gradient:yellow:blue>TitleAnnouncer</gradient> <gray>by</gray> <gradient:green:yellow>4drian3d</gradient>"
        );

        if (enabledPrefix) {
            prefix = MiniMessageUtil.parse(plugin.getConfig().getString(
                "messages.prefix.line", 
                "<gray>[</gray><gradient:yellow:blue>TitleAnnouncer</gradient><gray>]</gray> "));
        }

        if (!sender.hasPermission("announcer.command.show")) {
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.general.no-permission", 
                        "<red>You do not have permission to execute this command</red>"))));
            return false;
        }
        if (!sender.hasPermission("announcer.command.admin")) {
            sender.sendMessage(announce);
            return false;
        }

        if (args.length == 0) {
            sender.sendMessage(announce);
            sender.sendMessage(
                MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.general.help-message", 
                        "<white>Available Commands:</white>")));
            sender.sendMessage(titleHelpMessage);
            sender.sendMessage(actionbarHelpMessage);
            sender.sendMessage(bossbarHelpMessage);
            sender.sendMessage(fullwikilink);
            return true;
        } else if(args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();

            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.general.reload-config", 
                        "<green>Config Reloaded</green>"))));
            return true;
        } else if (args[0].equalsIgnoreCase("help")) {
            sender.sendMessage(announce);
            sender.sendMessage(
                MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.general.help-message", 
                        "<white>Available Commands:</white>")));
            if(args.length == 2){    
                if (args[1].equalsIgnoreCase("title")){
                    sender.sendMessage(titleHelpMessage);
                    sender.sendMessage(titlewikilink);
                    return true;
                } else if(args[1].equalsIgnoreCase("actionbar")){
                    sender.sendMessage(actionbarHelpMessage);
                    sender.sendMessage(actionbarwikilink);
                    return true;
                } else if (args[1].equalsIgnoreCase("bossbar")){
                    sender.sendMessage(bossbarHelpMessage);
                    sender.sendMessage(bossbarwikilink);
                    return true;
                } else {
                    sender.sendMessage(titleHelpMessage);
                    sender.sendMessage(actionbarHelpMessage);
                    sender.sendMessage(bossbarHelpMessage);
                    sender.sendMessage(fullwikilink);
                }
            } else {
                sender.sendMessage(titleHelpMessage);
                sender.sendMessage(actionbarHelpMessage);
                sender.sendMessage(bossbarHelpMessage);
                sender.sendMessage(fullwikilink);
                return true;
            }
        } else {
            sender.sendMessage(
                prefix.append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.general.invalid-command", 
                        "<red>Unknown Command</red>"))));
        }
        return true;
    }

}
