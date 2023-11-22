package io.github._4drian3d.titleannouncer.velocity;

import cloud.commandframework.CommandManager;
import cloud.commandframework.execution.AsynchronousCommandExecutionCoordinator;
import cloud.commandframework.velocity.VelocityCommandManager;
import com.google.inject.*;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.proxy.ProxyServer;
import io.github._4drian3d.titleannouncer.common.TitleAnnouncerMainModule;
import io.github._4drian3d.titleannouncer.common.adapter.PlatformAdapter;
import io.github._4drian3d.titleannouncer.common.adapter.Commander;
import io.github._4drian3d.titleannouncer.common.annotations.DataFolder;

import java.nio.file.Path;

public final class TitleAnnouncerVelocityModule extends AbstractModule {
    private final Path path;

    TitleAnnouncerVelocityModule(Path path) {
        this.path = path;
    }

    @Provides
    @Singleton
    public CommandManager<Commander> commandmanager(
            final ProxyServer proxyServer,
            final PluginContainer pluginContainer
    ) {
        return new VelocityCommandManager<>(
                pluginContainer,
                proxyServer,
                AsynchronousCommandExecutionCoordinator.simpleCoordinator(),
                VelocityCommander::new,
                commander -> ((VelocityCommander) commander).audience()
        );
    }
    @Override
    protected void configure() {
        this.bind(Path.class).annotatedWith(DataFolder.class).toInstance(path);
        this.install(new TitleAnnouncerMainModule());
        this.bind(PlatformAdapter.class).to(VelocityAdapter.class);
        this.bind(Key.get(new TypeLiteral<PlatformAdapter<?>>(){})).to(VelocityAdapter.class);
    }

    private record VelocityCommander(CommandSource audience) implements Commander {
    }
}
