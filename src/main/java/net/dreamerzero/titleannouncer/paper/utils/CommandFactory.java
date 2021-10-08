package net.dreamerzero.titleannouncer.paper.utils;

import org.bukkit.command.CommandExecutor;

/**
 * Factory command register
 * @author Jonakls
 */
public record CommandFactory(String command, CommandExecutor executor) {}
