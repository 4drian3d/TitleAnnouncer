package io.github._4drian3d.titleannouncer.common.commands.suggestions;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public final class SoundSuggestions<C> implements SuggestionProvider<C> {
  @Override
  public CompletableFuture<Suggestions> getSuggestions(final CommandContext<C> context, final SuggestionsBuilder builder) {
    final String remaining = builder.getRemaining();
    final int indexOfSeparator = remaining.indexOf(':');
    final boolean namespaced = indexOfSeparator != -1;
    if (remaining.isBlank() || !namespaced) {
      if (!namespaced && isNotLowercased(remaining)) {
        builder.suggest(remaining.toLowerCase(Locale.ROOT));
      }
      return builder.suggest("\"minecraft:").buildFuture();
    }
    final int remainingLength = remaining.length();
    String remainingBeforeSeparator;
    // If the argument has characters after the :
    if (remainingLength > 2 && !(remainingBeforeSeparator = remaining.substring(1)).isBlank()) {
      if (isNotLowercased(remainingBeforeSeparator)) {
        final String remainingLowerCased = remainingBeforeSeparator.toLowerCase(Locale.ROOT);
        return builder.suggest("\"" + remainingLowerCased).buildFuture();
      }
      if (remainingLength - 1 > indexOfSeparator) {
        final String remainingAfterSeparator = remaining.substring(indexOfSeparator + 1);
        final SuggestionsBuilder offsetBuilder = builder.createOffset(builder.getStart() + indexOfSeparator + 1);
        if (remaining.charAt(remainingLength - 1) != '"') {
          offsetBuilder.suggest(remainingAfterSeparator.toLowerCase(Locale.ROOT) + "\"");
        }
        return offsetBuilder.buildFuture();
      }
    }
    return builder.buildFuture();

  }

  private boolean isNotLowercased(String string) {
    final String lowerCased = string.toLowerCase(Locale.ROOT);
    return !Objects.equals(string, lowerCased);
  }
}
