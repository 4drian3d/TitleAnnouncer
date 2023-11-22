package io.github._4drian3d.titleannouncer.common;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;
import io.github._4drian3d.titleannouncer.common.annotations.DataFolder;
import io.github._4drian3d.titleannouncer.common.commands.AnnouncerCommand;
import io.github._4drian3d.titleannouncer.common.commands.TitleCommand;
import io.github._4drian3d.titleannouncer.common.configuration.Configuration;
import io.github._4drian3d.titleannouncer.common.configuration.ConfigurationContainer;
import io.github._4drian3d.titleannouncer.common.configuration.Messages;
import io.github._4drian3d.titleannouncer.common.format.Formatter;
import io.github._4drian3d.titleannouncer.common.format.MiniPlaceholdersFormatter;
import org.slf4j.Logger;

import java.nio.file.Path;

public final class TitleAnnouncerMainModule extends AbstractModule {
    @Singleton
    @Provides
    public ConfigurationContainer<Configuration> configurationContainer(
            final Logger logger,
            final @DataFolder Path path
    ) {
        return ConfigurationContainer.load(logger, path, Configuration.class, "config");
    }

    @Singleton
    @Provides
    public ConfigurationContainer<Messages> messagesContainer(
            final Logger logger,
            final @DataFolder Path path
    ) {
        return ConfigurationContainer.load(logger, path, Messages.class, "config");
    }

    @Singleton
    @Provides
    public Formatter formatter() {
        try {
            Class.forName("io.github.miniplaceholders.api.MiniPlaceholders");
            return new MiniPlaceholdersFormatter();
        } catch (Throwable t) {
            return new Formatter();
        }
    }

    @Override
    protected void configure() {
        final Multibinder<AnnouncerCommand> commandBinder = Multibinder.newSetBinder(binder(), AnnouncerCommand.class);
        commandBinder.addBinding().to(TitleCommand.class).in(Scopes.SINGLETON);
    }
}
