package net.dreamerzero.titleannouncer.common.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.event.ClickEvent;

import static net.kyori.adventure.text.Component.*;
import static net.kyori.adventure.text.format.NamedTextColor.*;

public class GeneralUtils {
    /**
     * Concatenates all the arguments of a command
     * @param args Arguments of the command
     * @return The arguments of a command in string form
     */
    public static String getCommandString(String[] args){
        // Concatenate the arguments provided by the command sent.
        StringBuilder commandString = new StringBuilder();
        for (String argument : args) {
            commandString = commandString.append(" ").append(argument);
        }

        return commandString.toString();
    }

    /**
     * Concatenates the arguments of a command from a specified position
     * @param args Arguments of the command
     * @param since Specific position from which the string is to be formed
     * @return The command arguments from a specified position converted to String
     */
    public static String getCommandString(String[] args, int since){
        // Concatenate the arguments provided by the command sent.
        StringBuilder commandString = new StringBuilder();
        for (int i = since; i < args.length; i++) {
            commandString = commandString.append(" ").append(args[i]);
        }

        return commandString.toString();
    }

    public final static Component titleArguments = text("[Title]; [SubTitle]", AQUA);
    public final static Component actionbarArguments = text("[ActionBar]", AQUA);
    public final static Component bossbarArguments = text("[Time] [Color] [Style] [BossBar]", AQUA);

    public final static Component titleHelpMessage = text()
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

    public final static Component actionbarHelpMessage = text()
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

    public final static Component bossbarHelpMessage = text()
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

    public final static Component fullwikilink = text()
        .append(text()
            .append(Component.text("Visit full guide on")))
        .append(space())
        .append(text()
            .append(text("WIKI"))
            .clickEvent(ClickEvent.openUrl("https://github.com/4drian3d/TitleAnnouncer/wiki"))
            .hoverEvent(HoverEvent.showText(
                MiniMessageUtil.parse("<gradient:red:blue>Click Here</gradient>"))))
        .build();
    public final static Component titlewikilink = text()
        .append(text()
            .append(Component.text("Visit full guide on")))
        .append(space())
        .append(text()
            .append(text("WIKI"))
            .clickEvent(ClickEvent.openUrl("https://github.com/4drian3d/TitleAnnouncer/wiki/Title-Commands"))
            .hoverEvent(HoverEvent.showText(
                MiniMessageUtil.parse("<gradient:red:blue>Click Here</gradient>"))))
            .build();
    public final static Component actionbarwikilink = text()
        .append(text()
            .append(Component.text("Visit full guide on")))
        .append(space())
        .append(text()
            .append(text("WIKI"))
            .clickEvent(ClickEvent.openUrl("https://github.com/4drian3d/TitleAnnouncer/wiki/ActionBar-Commands"))
            .hoverEvent(HoverEvent.showText(
                MiniMessageUtil.parse("<gradient:red:blue>Click Here</gradient>"))))
            .build();

    public final static Component bossbarwikilink = text()
        .append(text()
            .append(Component.text("Visit full guide on")))
        .append(space())
        .append(text()
            .append(text("WIKI"))
            .clickEvent(ClickEvent.openUrl("https://github.com/4drian3d/TitleAnnouncer/wiki/Bossbar-Commands"))
            .hoverEvent(HoverEvent.showText(
                MiniMessageUtil.parse("<gradient:red:blue>Click Here</gradient>"))))
            .build();
}
