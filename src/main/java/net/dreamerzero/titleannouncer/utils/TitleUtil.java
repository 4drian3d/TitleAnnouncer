package net.dreamerzero.titleannouncer.utils;

import java.time.Duration;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;

public class TitleUtil {
    /**
     * This will send a titled message
     * with the specified title and
     * subtitle components and with the
     * specified duration for the required audience.
     * @param title
     * @param subtitle
     * @param audience
     * @param fadein
     * @param stay
     * @param fadeout
     */
    public static void sendTitle(
        final Component title,
        final Component subtitle,
        final Audience audience,
        final int fadein,
        final int stay,
        final int fadeout) {

        // Title Duration
        final Title.Times times = Title.Times.of(
            Duration.ofMillis(fadein),
            Duration.ofMillis(stay),
            Duration.ofMillis(fadeout));
        // Title Format
        Title titleToSend = Title.title(
            title,
            subtitle,
            times);
        // Send the title to the specified audience.
        audience.showTitle(titleToSend);
    }

    /**
     * Display a single title to the desired audience.
     * @param title
     * @param audience
     * @param fadein
     * @param stay
     * @param fadeout
     */
    public static void sendOnlyTitle(final Component title,
        final Audience audience,
        final int fadein,
        final int stay,
        final int fadeout){

        Title.Times times = Title.Times.of(
            Duration.ofMillis(fadein),
            Duration.ofMillis(stay),
            Duration.ofMillis(fadeout));

        audience.sendTitlePart(TitlePart.TITLE, title);
        audience.sendTitlePart(TitlePart.TIMES, times);
    }

    /**
     * Display a single subtitle to the desired audience.
     *
     * @param subtitle
     * @param audience
     * @param fadein
     * @param stay
     * @param fadeout
     */
    public static void sendOnlySubtitle(final Component subtitle,
        final Audience audience,
        final int fadein,
        final int stay,
        final int fadeout){

        Title.Times times = Title.Times.of(
            Duration.ofMillis(fadein),
            Duration.ofMillis(stay),
            Duration.ofMillis(fadeout));

        audience.sendTitlePart(TitlePart.TITLE, subtitle);
        audience.sendTitlePart(TitlePart.TIMES, times);
    }

    public static String[] getTitleAndSubtitle(String string, Audience sender) {
        try {
            String newString[] = string.split(";");
            if(newString.length > 2) {
                String fixedString[] = {newString[0], newString[1]};
                return fixedString;
            }
            return newString;
        // In case the command does not contain a separator ";",
        // it will catch the error in the console and send an error message to the sender.
        } catch (Exception e) {
            // Send an error message to the sender using the command
            ConfigUtils.sendTitleError(sender);
            return null;
        }
    }
}
