package io.github._4drian3d.titleannouncer.paper.adapter;

import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.github._4drian3d.titleannouncer.common.commands.suggestions.TargetSuggestionType;
import org.bukkit.Server;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public record WorldSuggestionType(Server server) implements TargetSuggestionType {
  @Override
  public String targetPrefix() {
    return "world:";
  }

  @Override
  public boolean canSuggest(String lowerCasedArgument) {
    return lowerCasedArgument.indexOf('\"') == 0
        ? lowerCasedArgument.startsWith(targetPrefix(), 1)
        : lowerCasedArgument.startsWith(targetPrefix());
  }

  @Override
  public void suggestSelf(SuggestionsBuilder builder) {
    builder.suggest("world").suggest("\"world:");
  }

  @Override
  public CompletableFuture<Suggestions> provideSuggestions(String remaining, SuggestionsBuilder builder) {
    if (remaining.isBlank() || remaining.indexOf(':') == -1) {
      return builder.restart().suggest("\"world:").suggest("world").buildFuture();
    }
    return this.provideListSuggestions(remaining, builder, this::worldNames);
  }

  private List<String> worldNames() {
    final List<String> worldNames = new ArrayList<>();
    for (final World world : server.getWorlds()) {
      worldNames.add(world.getName());
    }
    return worldNames;
  }
}
