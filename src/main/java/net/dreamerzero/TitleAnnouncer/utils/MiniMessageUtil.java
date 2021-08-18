package net.dreamerzero.TitleAnnouncer.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class MiniMessageUtil {
    //Component that parses the title with the MiniMessage format.
    public static Component miniMessageParse(final String message) {
        return MiniMessage.get().parse(message);
    }
}
