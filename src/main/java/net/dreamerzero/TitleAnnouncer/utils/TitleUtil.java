package net.dreamerzero.TitleAnnouncer.utils;

import java.time.Duration;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;

public class TitleUtil {
    // This will send a titled message 
    // with the specified title and 
    // subtitle components and with the 
    // specified duration for the required audience.
    public static void sendTitle(
        final Component anuntitle, 
        final Component anunsubtitle, 
        final Audience audience, 
        final int fadein, 
        final int stay, 
        final int fadeout) {

        //Title Duration
        final Title.Times times = Title.Times.of(
            Duration.ofMillis(fadein), 
            Duration.ofMillis(stay), 
            Duration.ofMillis(fadeout));
        //Title Format
        final Title title = Title.title(
            anuntitle, 
            anunsubtitle, 
            times);
        //Send the title to the specified audience.
        audience.showTitle(title);
    }
}
