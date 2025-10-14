package io.github._4drian3d.titleannouncer.paper;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import io.github._4drian3d.titleannouncer.common.TitleAnnouncerMainModule;
import io.github._4drian3d.titleannouncer.common.adapter.PlatformAdapter;
import io.github._4drian3d.titleannouncer.common.annotations.DataFolder;
import io.github._4drian3d.titleannouncer.paper.adapter.TitleAnnouncerPaperAdapter;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.slf4j.Logger;

import java.nio.file.Path;

public final class TitleAnnouncerPaperModule extends AbstractModule {
  private final Server server;
  private final ComponentLogger logger;
  private final Path pluginFolder;

  public TitleAnnouncerPaperModule(
      final Server server,
      final ComponentLogger logger,
      final Path pluginFolder
  ) {
    this.server = server;
    this.logger = logger;
    this.pluginFolder = pluginFolder;
  }

  @Override
  protected void configure() {
    this.install(new TitleAnnouncerMainModule());

    this.bind(Path.class).annotatedWith(DataFolder.class).toInstance(pluginFolder);
    this.bind(Key.get(new TypeLiteral<PlatformAdapter<Player, CommandSourceStack>>() {
    })).to(TitleAnnouncerPaperAdapter.class);
    this.bind(Server.class).toInstance(server);
    this.bind(Logger.class).toInstance(logger);
  }
}
