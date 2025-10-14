package io.github._4drian3d.titleannouncer.common.commands.suggestions;

import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.github._4drian3d.titleannouncer.common.adapter.PlatformAdapter;

import java.util.concurrent.CompletableFuture;

public record PlayerSuggestionType(PlatformAdapter<?, ?> platformAdapter) implements TargetSuggestionType {
  @Override
  public String targetPrefix() {
    return "player:";
  }

  @Override
  public CompletableFuture<Suggestions> provideSuggestions(final String argument, SuggestionsBuilder builder) {
    builder = builder.createOffset(argument.indexOf(':') + builder.getStart() + 1);
    this.platformAdapter.playerSuggestions().forEach(builder::suggest);
    return builder.buildFuture();
  }
}
