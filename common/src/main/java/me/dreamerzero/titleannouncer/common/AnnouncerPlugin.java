package me.dreamerzero.titleannouncer.common;

import me.dreamerzero.titleannouncer.common.adapter.CommandAdapter;

public interface AnnouncerPlugin<S> {
    void registerActionbar(CommandAdapter<S> adapter);

    void registerBossbar(CommandAdapter<S> adapter);

    void registerChat(CommandAdapter<S> adapter);

    void registerTitle(CommandAdapter<S> adapter);
}
