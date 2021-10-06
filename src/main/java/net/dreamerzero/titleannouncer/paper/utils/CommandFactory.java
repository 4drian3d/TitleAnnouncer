package net.dreamerzero.titleannouncer.paper.utils;

import org.bukkit.command.CommandExecutor;

/**
 * Factory command register
 * @author Jonakls
 */
public class CommandFactory {

    private final String command;
    private final CommandExecutor executor;

    public CommandFactory(String command, CommandExecutor executor) {
        this.command = command;
        this.executor = executor;
    }

    public String getCommand() {
        return this.command;
    }

    public CommandExecutor getExecutor() {
        return this.executor;
    }
}
