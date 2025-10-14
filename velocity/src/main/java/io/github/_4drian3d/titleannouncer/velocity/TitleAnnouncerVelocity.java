package io.github._4drian3d.titleannouncer.velocity;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import io.github._4drian3d.titleannouncer.common.Constants;
import io.github._4drian3d.titleannouncer.common.commands.AnnouncerCommand;
import io.github._4drian3d.titleannouncer.common.commands.TitleAnnouncerCommand;
import io.github._4drian3d.titleannouncer.velocity.adapter.ServerSuggestionType;

import java.nio.file.Path;

@Plugin(
    id = "titleannouncer",
    name = "TitleAnnouncer",
    version = Constants.VERSION,
    authors = {"4drian3d"},
    url = "https://modrinth.com/plugin/titleannouncer",
    description = "A lightweight Paper and Velocity plugin to send Titles, Actionbars, Bossbars and Chat announces",
    dependencies = {
        @Dependency(id = "miniplaceholders", optional = true)
    }
)
public final class TitleAnnouncerVelocity {
    @Inject
    private Injector injector;
    @Inject
    @DataDirectory
    private Path path;
    @Inject
    private ProxyServer proxyServer;
    @Inject
    private CommandManager commandManager;

    @Subscribe
    public void onStartup(final ProxyInitializeEvent event) {
        this.injector = injector.createChildInjector(new TitleAnnouncerVelocityModule(path));

        final LiteralCommandNode<CommandSource> announceNode = this.injector
            .getInstance(Key.get(new TypeLiteral<AnnouncerCommand<Player, CommandSource>>() {}))
            .buildCommand("v", new ServerSuggestionType(proxyServer));
      final LiteralCommandNode<CommandSource> mainCommandNode = this.injector
          .getInstance(Key.get(new TypeLiteral<TitleAnnouncerCommand<Player, CommandSource>>() {}))
          .buildCommand("v");

        final BrigadierCommand announceCommand = new BrigadierCommand(announceNode);
        final CommandMeta announceCommandMeta = commandManager.metaBuilder(announceCommand)
            .plugin(this)
            .build();
        commandManager.register(announceCommandMeta, announceCommand);

      final BrigadierCommand mainCommand = new BrigadierCommand(mainCommandNode);
      final CommandMeta mainCommandMeta = commandManager.metaBuilder(mainCommand)
          .plugin(this)
          .build();
      commandManager.register(mainCommandMeta, mainCommand);
    }

    @Subscribe
    public void onShutdown(final ProxyShutdownEvent event) {

    }
}
