package io.github._4drian3d.titleannouncer.common.commands.suggestions;

import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public interface TargetSuggestionType {
  String targetPrefix();

  default boolean canSuggest(String lowerCasedArgument) {
    return lowerCasedArgument.startsWith(targetPrefix());
  }

  CompletableFuture<Suggestions> provideSuggestions(final String remaining, final SuggestionsBuilder builder);

  default CompletableFuture<Suggestions> provideListSuggestions(
      final String remaining,
      SuggestionsBuilder builder,
      final Supplier<Collection<String>> argumentsSupplier
  ) {
    final int remainingLength = remaining.length();
    final int indexOfQuote = remaining.indexOf(':');
    final SuggestionsBuilder offsetBuilder = builder.createOffset(indexOfQuote + builder.getStart() + 1);

    if (remaining.charAt(remainingLength - 1) != '"') {
      argumentsSupplier.get().forEach(offsetBuilder::suggest);
      if (remainingLength > 9) {
        offsetBuilder.suggest(remaining.substring(8) + "\"");
      }
    }

    return offsetBuilder.buildFuture();
  }

  default void suggestSelf(SuggestionsBuilder builder) {
    builder.suggest("\"" + targetPrefix());
  }
}
