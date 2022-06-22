package me.dreamerzero.titleannouncer.common;

import me.dreamerzero.titleannouncer.common.adapter.CommandAdapter;
import me.dreamerzero.titleannouncer.common.configuration.Configuration;

public interface AnnouncerPlugin<S> {
    Configuration config();
    
    void registerActionbar(CommandAdapter<S> adapter);

    void registerBossbar(CommandAdapter<S> adapter);

    void registerChat(CommandAdapter<S> adapter);

    void registerTitle(CommandAdapter<S> adapter);
}
