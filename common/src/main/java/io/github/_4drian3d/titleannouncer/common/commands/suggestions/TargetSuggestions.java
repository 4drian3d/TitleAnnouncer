package io.github._4drian3d.titleannouncer.common.commands.suggestions;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public record TargetSuggestions<C>(TargetSuggestionType... targetSuggestionTypes) implements SuggestionProvider<C> {
  @Override
  public CompletableFuture<Suggestions> getSuggestions(final CommandContext<C> context, final SuggestionsBuilder builder) {
    final String originalInput = builder.getRemaining();
    final String inputLowercased = originalInput.toLowerCase(Locale.ROOT);
    for (final TargetSuggestionType targetSuggestionType : this.targetSuggestionTypes) {
      if (targetSuggestionType.canSuggest(inputLowercased)) {
        return targetSuggestionType.provideSuggestions(originalInput, builder);
      }
    }
    for (final TargetSuggestionType targetSuggestionType : this.targetSuggestionTypes) {
      targetSuggestionType.suggestSelf(builder);
    }
    return builder.suggest("self").suggest("all").buildFuture();
  }
}
