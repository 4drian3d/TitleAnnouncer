package io.github._4drian3d.titleannouncer.velocity.adapter;

import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import io.github._4drian3d.titleannouncer.common.commands.suggestions.TargetSuggestionType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public record ServerSuggestionType(ProxyServer proxyServer) implements TargetSuggestionType {
  @Override
  public String targetPrefix() {
    return "server";
  }

  @Override
  public boolean canSuggest(final String lowerCasedArgument) {
    return lowerCasedArgument.indexOf('\"') == 0
        ? lowerCasedArgument.startsWith(targetPrefix(), 1)
        : lowerCasedArgument.startsWith(targetPrefix());
  }

  @Override
  public CompletableFuture<Suggestions> provideSuggestions(final String remaining, final SuggestionsBuilder builder) {
    if (remaining.isBlank() || remaining.indexOf(':') == -1) {
      return builder.suggest("\"server:").suggest("server").buildFuture();
    }
    return this.provideListSuggestions(remaining, builder, this::serverNames);
  }

  @Override
  public void suggestSelf(SuggestionsBuilder builder) {
    builder.suggest("server").suggest("\"server:");
  }

  private List<String> serverNames() {
    final List<String> serverNames = new ArrayList<>();
    for (RegisteredServer server : this.proxyServer.getAllServers()) {
      serverNames.add(server.getServerInfo().getName());
    }
    return serverNames;
  }
}
