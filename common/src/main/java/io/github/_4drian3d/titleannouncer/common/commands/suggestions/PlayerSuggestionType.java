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
  public boolean canSuggest(String lowerCasedArgument) {
    return lowerCasedArgument.startsWith("\"" + targetPrefix());
  }

  @Override
  public CompletableFuture<Suggestions> provideSuggestions(final String remaining, SuggestionsBuilder builder) {
    return this.provideListSuggestions(remaining, builder.restart(), platformAdapter::playerSuggestions);
  }
}
