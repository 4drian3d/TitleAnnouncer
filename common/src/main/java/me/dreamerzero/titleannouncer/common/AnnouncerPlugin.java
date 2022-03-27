package me.dreamerzero.titleannouncer.common;

import me.dreamerzero.titleannouncer.common.commands.CommandAdapter;

public interface AnnouncerPlugin {
    void registerActionbar(CommandAdapter adapter);

    void registerBossbar(CommandAdapter adapter);

    void registerChat(CommandAdapter adapter);

    void registerTitle(CommandAdapter adapter);
}
