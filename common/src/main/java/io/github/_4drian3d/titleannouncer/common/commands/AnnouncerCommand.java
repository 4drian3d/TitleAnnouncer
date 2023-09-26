package io.github._4drian3d.titleannouncer.common.commands;

import cloud.commandframework.CommandManager;
import io.github._4drian3d.titleannouncer.common.adapter.Commander;

public interface AnnouncerCommand {
    void register(CommandManager<Commander> commandManager);
}
