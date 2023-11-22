package io.github._4drian3d.titleannouncer.common;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import io.github._4drian3d.titleannouncer.common.commands.AnnouncerCommand;

import java.util.Set;

public abstract class TitleAnnouncer {
    private final Injector injector;

    protected TitleAnnouncer(
            Injector injector
    ) {
        this.injector = injector;
    }

    public void init() {
        var commands = injector.getInstance(Key.get(new TypeLiteral<Set<AnnouncerCommand>>() {}));
        for (final AnnouncerCommand command : commands) {
            command.register();
        }
    }

    public abstract void stop();
}
