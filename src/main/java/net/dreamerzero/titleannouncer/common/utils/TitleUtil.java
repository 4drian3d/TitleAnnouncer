package net.dreamerzero.titleannouncer.common.utils;

import java.time.Duration;
import java.util.regex.PatternSyntaxException;

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

        // Send the title to the specified audience.
        audience.showTitle(Title.title(
            title,
            subtitle,
            Title.Times.of(
                Duration.ofMillis(fadein),
                Duration.ofMillis(stay),
                Duration.ofMillis(fadeout))));
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

        audience.sendTitlePart(TitlePart.TITLE, title);
        audience.sendTitlePart(TitlePart.TIMES, Title.Times.of(
            Duration.ofMillis(fadein),
            Duration.ofMillis(stay),
            Duration.ofMillis(fadeout)));
    }

    /**
     * Display a single subtitle to the desired audience.
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

        audience.sendTitlePart(TitlePart.TITLE, subtitle);
        audience.sendTitlePart(TitlePart.TIMES, Title.Times.of(
            Duration.ofMillis(fadein),
            Duration.ofMillis(stay),
            Duration.ofMillis(fadeout)));
    }

    public static String[] getTitleAndSubtitle(String string, Audience sender) {
        try {
            String[] newString = string.split(";");
            if(newString.length > 2) {
                String[] fixedString = {newString[0], newString[1]};
                newString = fixedString;
            }
            return newString;
        // In case the command does not contain a separator ";",
        // it will catch the error in the console and send an error message to the sender.
        } catch (PatternSyntaxException e) {
            // Send an error message to the sender using the command
            ConfigUtils.sendTitleError(sender);
            return new String[0];
        }
    }

    public static boolean containsComma(String[] args){
        for(String argument : args){
            if(argument.contains(";")) return true;
        }
        return false;
    }

    public static boolean containsComma(String[] args, int since){
        for(int i = since; i < args.length; i++){
            if(args[i].contains(";")) return true;
        }
        return false;
    }

    private TitleUtil() throws IllegalAccessException{
        throw new IllegalAccessException("Utility Class");
    }
}
