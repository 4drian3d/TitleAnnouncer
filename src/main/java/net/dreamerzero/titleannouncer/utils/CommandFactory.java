package net.dreamerzero.titleannouncer.utils;

import org.bukkit.command.CommandExecutor;

public class CommandFactory {

    private final String command;
    private final CommandExecutor executor;

    public CommandFactory(String command, CommandExecutor executor) {
        this.command = command;
        this.executor = executor;
    }

    public String getCommand() {
        return command;
    }

    public CommandExecutor getExecutor() {
        return executor;
    }
}
