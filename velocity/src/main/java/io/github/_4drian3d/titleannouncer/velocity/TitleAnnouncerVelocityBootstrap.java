package io.github._4drian3d.titleannouncer.velocity;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.annotation.DataDirectory;

import java.nio.file.Path;

public final class TitleAnnouncerVelocityBootstrap {
    @Inject
    private Injector injector;
    @Inject
    @DataDirectory
    private Path path;

    private TitleAnnouncerVelocity plugin;

    @Subscribe
    public void onStartup(final ProxyInitializeEvent event) {
        this.injector = injector.createChildInjector(
                new TitleAnnouncerVelocityModule(path)
        );
        this.plugin = injector.getInstance(TitleAnnouncerVelocity.class);
        this.plugin.init();
    }

    @Subscribe
    public void onShutdown(final ProxyShutdownEvent event) {
        if (this.plugin != null) {
            this.plugin.stop();
        }
    }
}
