package io.github._4drian3d.titleannouncer.paper;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.github._4drian3d.titleannouncer.common.commands.AnnouncerCommand;
import io.github._4drian3d.titleannouncer.common.commands.TitleAnnouncerCommand;
import io.github._4drian3d.titleannouncer.paper.adapter.WorldSuggestionType;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NullUnmarked;

import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
@NullUnmarked
public final class TitleAnnouncerPaper extends JavaPlugin {
  private Injector injector;

  @Override
  public void onEnable() {
    this.injector = Guice.createInjector(new TitleAnnouncerPaperModule(this.getServer(), this.getComponentLogger(), this.getDataPath()));

    final LiteralCommandNode<CommandSourceStack> announcerNode = this.injector
        .getInstance(Key.get(new TypeLiteral<AnnouncerCommand<Player, CommandSourceStack>>() {
        }))
        .buildCommand("", new WorldSuggestionType(this.getServer()));
    final LiteralCommandNode<CommandSourceStack> mainCommandNode = this.injector
        .getInstance(Key.get(new TypeLiteral<TitleAnnouncerCommand<Player, CommandSourceStack>>() {
        }))
        .buildCommand("");

    this.getLifecycleManager()
        .registerEventHandler(LifecycleEvents.COMMANDS, event -> {
          event.registrar().register(this.getPluginMeta(), announcerNode, "Announce commands", List.of());
          event.registrar().register(this.getPluginMeta(), mainCommandNode, "TitleAnnouncer main command", List.of());
        });
  }

  @Override
  public void onDisable() {
    super.onDisable();
  }
}
