package io.github._4drian3d.titleannouncer.common.commands;

import com.google.inject.Inject;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.github._4drian3d.titleannouncer.common.adapter.PlatformAdapter;
import io.github._4drian3d.titleannouncer.common.commands.format.*;
import io.github._4drian3d.titleannouncer.common.commands.suggestions.PlayerSuggestionType;
import io.github._4drian3d.titleannouncer.common.commands.suggestions.TargetSuggestionType;
import io.github._4drian3d.titleannouncer.common.commands.suggestions.TargetSuggestions;
import io.github._4drian3d.titleannouncer.common.configuration.Configuration;
import io.github._4drian3d.titleannouncer.common.configuration.ConfigurationContainer;
import io.github._4drian3d.titleannouncer.common.configuration.Messages;
import io.github._4drian3d.titleannouncer.common.format.Formatter;
import io.github._4drian3d.titleannouncer.common.manager.BossBarManager;
import net.kyori.adventure.audience.Audience;

import java.util.List;

public final class AnnouncerCommand<P extends Audience, C> {
  @Inject
  private Formatter formatter;
  @Inject
  private ConfigurationContainer<Configuration> configurationContainer;
  @Inject
  private ConfigurationContainer<Messages> messagesContainer;
  @Inject
  private PlatformAdapter<P, C> platformAdapter;
  @Inject
  private BossBarManager bossBarManager;

  public LiteralCommandNode<C> buildCommand(final String prefix, TargetSuggestionType nativeTargetSuggestions) {
    final LiteralArgumentBuilder<C> announceBuilder = LiteralArgumentBuilder.literal(prefix + "announce");
    final PlayerSuggestionType playerSuggestionType = new PlayerSuggestionType(platformAdapter);
    final TargetSuggestions<C> targetSuggestions = new TargetSuggestions<>(playerSuggestionType, nativeTargetSuggestions);

    final List<AnnounceNode<C>> nodes = List.of(
        new TitleAnnounceNode<>(formatter, platformAdapter, configurationContainer, messagesContainer),
        new ActionbarAnnounceNode<>(formatter, platformAdapter, configurationContainer, messagesContainer),
        new ChatAnnounceNode<>(formatter, platformAdapter, configurationContainer, messagesContainer),
        new BossbarAnnounceNode<>(formatter, platformAdapter, bossBarManager, configurationContainer, messagesContainer)
    );
    for (final AnnounceNode<C> node : nodes) {
      announceBuilder.then(node.provideNode(targetSuggestions));
    }
    return announceBuilder.build();
  }
}
