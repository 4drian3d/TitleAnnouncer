package net.dreamerzero.titleannouncer.velocity.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.permission.Tristate;

import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.MiniMessageUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;

import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.Component.*;

public class AnnouncerCommand implements SimpleCommand {
	public AnnouncerCommand() {}

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

    @Override
    public void execute(final Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();

        switch (source.getPermissionValue("announcer.command.show")){
            case FALSE -> {
                source.sendMessage(
                    MiniMessageUtil.parse(
                    "<gradient:yellow:blue>TitleAnnouncer</gradient> <gray>by</gray> <gradient:green:yellow>4drian3d</gradient>"));
                return;
            }
            case UNDEFINED -> {
                ConfigUtils.sendNoMainPermission(source);
                return;
            }
            default -> {}
        }

        if (args.length == 0) {
            source.sendMessage(
                MiniMessageUtil.parse(
                "<gradient:yellow:blue>TitleAnnouncer</gradient> <gray>by</gray> <gradient:green:yellow>4drian3d</gradient>"));
            ConfigUtils.helpPrefix(source);
            source.sendMessage(titleHelpMessage);
            source.sendMessage(actionbarHelpMessage);
            source.sendMessage(bossbarHelpMessage);
            source.sendMessage(fullwikilink);
            return;
        }

        switch (args[0].toLowerCase()) {
            case "reload" -> {
                //plugin.reloadConfig();
                ConfigUtils.reloadMessage(source);
                return;
            }
            case "help" -> {
                source.sendMessage(
                    MiniMessageUtil.parse(
                    "<gradient:yellow:blue>TitleAnnouncer</gradient> <gray>by</gray> <gradient:green:yellow>4drian3d</gradient>"));
                ConfigUtils.helpPrefix(source);
                if(args.length == 2){
                    switch (args[1].toLowerCase()) {
                        case "title" -> {
                            source.sendMessage(titleHelpMessage);
                            source.sendMessage(titlewikilink);
                            return;
                        }
                        case "actionbar" -> {
                            source.sendMessage(actionbarHelpMessage);
                            source.sendMessage(actionbarwikilink);
                            return;
                        }
                        case "bossbar" -> {
                            source.sendMessage(bossbarHelpMessage);
                            source.sendMessage(bossbarwikilink);
                            return;
                        }
                        default -> {
                            source.sendMessage(titleHelpMessage);
                            source.sendMessage(actionbarHelpMessage);
                            source.sendMessage(bossbarHelpMessage);
                            source.sendMessage(fullwikilink);
                            return;
                        }
                    }
                } else {
                    source.sendMessage(titleHelpMessage);
                    source.sendMessage(actionbarHelpMessage);
                    source.sendMessage(bossbarHelpMessage);
                    source.sendMessage(fullwikilink);
                    return;
                }
            }
            default -> {
                ConfigUtils.invalidCommand(source);
                return;
            }
        }

    }

    @Override
    public boolean hasPermission(final Invocation invocation) {
        return invocation.source().getPermissionValue("titleannouncer.command") != Tristate.TRUE;
    }
}
