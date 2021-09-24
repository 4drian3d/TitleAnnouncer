package net.dreamerzero.titleannouncer.utils;

public class GeneralUtils {
    public static String getCommandString(String[] args){
        // Concatenate the arguments provided by the command sent.
        var titleandsubtitle = new StringBuilder();
        for (String argument : args) {
            titleandsubtitle = titleandsubtitle.append(" ");
            titleandsubtitle = titleandsubtitle.append(argument);
        }

        return titleandsubtitle.toString();
    }

    public static String getCommandString(String[] args, int since){
        // Concatenate the arguments provided by the command sent.
        var titleandsubtitle = new StringBuilder();
        for (int i = since; i < args.length; i++) {
            titleandsubtitle = titleandsubtitle.append(" ");
            titleandsubtitle = titleandsubtitle.append(args[i]);
        }

        return titleandsubtitle.toString();
    }
}
