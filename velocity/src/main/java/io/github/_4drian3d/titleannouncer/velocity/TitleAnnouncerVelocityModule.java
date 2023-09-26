package io.github._4drian3d.titleannouncer.velocity;

import cloud.commandframework.CommandManager;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.velocity.VelocityCommandManager;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.proxy.ProxyServer;
import io.github._4drian3d.titleannouncer.common.TitleAnnouncerMainModule;
import io.github._4drian3d.titleannouncer.common.adapter.CommandAdapter;
import io.github._4drian3d.titleannouncer.common.adapter.Commander;

public final class TitleAnnouncerVelocityModule extends AbstractModule {
    @Provides
    @Singleton
    public CommandManager<Commander> commandmanager(
            final ProxyServer proxyServer,
            final PluginContainer pluginContainer
    ) {
        return new VelocityCommandManager<>(
                pluginContainer,
                proxyServer,
                CommandExecutionCoordinator.simpleCoordinator(),
                VelocityCommander::new,
                commander -> ((VelocityCommander) commander).audience()
        );
    }
    @Override
    protected void configure() {
        this.install(new TitleAnnouncerMainModule());

        this.bind(CommandAdapter.class).to(VelocityAdapter.class);
    }

    private record VelocityCommander(CommandSource audience) implements Commander {
    }
}
