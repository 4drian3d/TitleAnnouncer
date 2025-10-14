package io.github._4drian3d.titleannouncer.common.commands.suggestions;

import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import java.util.concurrent.CompletableFuture;

public interface TargetSuggestionType {
  String targetPrefix();

  default boolean canSuggest(String lowerCasedArgument) {
    return lowerCasedArgument.startsWith(targetPrefix()) && lowerCasedArgument.charAt(lowerCasedArgument.length() - 1) == '"';
  }

  CompletableFuture<Suggestions> provideSuggestions(String argument, final SuggestionsBuilder builder);
}
