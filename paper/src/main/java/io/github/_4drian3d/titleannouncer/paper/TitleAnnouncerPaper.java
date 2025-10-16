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

import java.util.List;

public final class TitleAnnouncerPaper extends JavaPlugin {
  @Override
  public void onEnable() {
    final Injector injector = Guice.createInjector(new TitleAnnouncerPaperModule(this.getServer(), this.getComponentLogger(), this.getDataPath()));

    final LiteralCommandNode<CommandSourceStack> announcerNode = injector
        .getInstance(Key.get(new TypeLiteral<AnnouncerCommand<Player, CommandSourceStack>>() {
        }))
        .buildCommand("", new WorldSuggestionType(this.getServer()));
    final LiteralCommandNode<CommandSourceStack> mainCommandNode = injector
        .getInstance(Key.get(new TypeLiteral<TitleAnnouncerCommand<Player, CommandSourceStack>>() {
        }))
        .buildCommand("");

    this.getLifecycleManager()
        .registerEventHandler(LifecycleEvents.COMMANDS, event -> {
          event.registrar().register(this.getPluginMeta(), announcerNode, "Announce commands", List.of());
          event.registrar().register(this.getPluginMeta(), mainCommandNode, "TitleAnnouncer main command", List.of());
        });
  }
}
