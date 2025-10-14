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

  /**
   * Generates suggestions to complete an argument based on two sub-arguments.
   * <br>
   * This allows valid suggestions to be generated for use in conjunction with {@link com.mojang.brigadier.arguments.StringArgumentType#string()}
   * and a suggestion format: {@code “argument:suggestion” } where the argument is defined in {@link #targetPrefix()}
   *
   * @param remaining the actual argument provided by the player
   * @param builder the suggestion builder
   * @param argumentsSupplier the suggestions provider
   * @return the suggestions to provide to the user
   */
  default CompletableFuture<Suggestions> provideListSuggestions(
      final String remaining,
      SuggestionsBuilder builder,
      final Supplier<Collection<String>> argumentsSupplier
  ) {
    final int remainingLength = remaining.length();
    final int indexOfQuote = remaining.indexOf(':');
    final SuggestionsBuilder offsetBuilder = builder.createOffset(indexOfQuote + builder.getStart() + 1);

    if (remaining.charAt(remainingLength - 1) != '"') {
      if (remainingLength > targetPrefix().length() + 2) {
        return offsetBuilder.createOffset(builder.getStart() + remainingLength)
            .suggest("\"")
            .buildFuture();
      }
      argumentsSupplier.get().forEach(offsetBuilder::suggest);
    }

    return offsetBuilder.buildFuture();
  }

  default void suggestSelf(SuggestionsBuilder builder) {
    builder.suggest("\"" + targetPrefix());
  }
}
