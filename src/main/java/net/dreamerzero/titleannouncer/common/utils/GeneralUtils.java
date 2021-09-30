package net.dreamerzero.titleannouncer.common.utils;

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
}
