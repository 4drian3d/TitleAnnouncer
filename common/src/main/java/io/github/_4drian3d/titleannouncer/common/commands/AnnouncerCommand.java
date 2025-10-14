package io.github._4drian3d.titleannouncer.common.commands;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.github._4drian3d.titleannouncer.common.adapter.PlatformAdapter;
import io.github._4drian3d.titleannouncer.common.commands.format.TitleAnnounceNode;
import io.github._4drian3d.titleannouncer.common.commands.suggestions.PlayerSuggestionType;
import io.github._4drian3d.titleannouncer.common.commands.suggestions.TargetSuggestionType;
import io.github._4drian3d.titleannouncer.common.commands.suggestions.TargetSuggestions;
import io.github._4drian3d.titleannouncer.common.configuration.Configuration;
import io.github._4drian3d.titleannouncer.common.configuration.ConfigurationContainer;
import io.github._4drian3d.titleannouncer.common.format.Formatter;
import net.kyori.adventure.audience.Audience;

public final class AnnouncerCommand<P extends Audience, C> {
  @Inject
  private Formatter formatter;
  @Inject
  private ConfigurationContainer<Configuration> configurationContainer;
  @Inject
  private PlatformAdapter<P, C> platformAdapter;
  @Inject
  private Injector injector;

  public LiteralCommandNode<C> buildCommand(final String prefix, TargetSuggestionType nativeTargetSuggestions) {
    final LiteralArgumentBuilder<C> announceBuilder = LiteralArgumentBuilder.literal(prefix + "announce");
    final PlayerSuggestionType playerSuggestionType = new PlayerSuggestionType(platformAdapter);
    final TargetSuggestions<C> targetSuggestions = new TargetSuggestions<>(playerSuggestionType, nativeTargetSuggestions);

    announceBuilder.then(new TitleAnnounceNode<>(formatter, platformAdapter).provideNode(targetSuggestions));

    return announceBuilder.build();
  }
}
