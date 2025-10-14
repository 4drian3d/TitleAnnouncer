package io.github._4drian3d.titleannouncer.velocity.adapter;

import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import io.github._4drian3d.titleannouncer.common.commands.suggestions.TargetSuggestionType;

import java.util.concurrent.CompletableFuture;

public record ServerSuggestionType(ProxyServer proxyServer) implements TargetSuggestionType {
  @Override
  public String targetPrefix() {
    return "server:";
  }

  @Override
  public CompletableFuture<Suggestions> provideSuggestions(String argument, SuggestionsBuilder builder) {
    builder = builder.createOffset(argument.indexOf(':') + builder.getStart() + 1);
    for (final RegisteredServer allServer : this.proxyServer.getAllServers()) {
      builder.suggest(allServer.getServerInfo().getName());
    }
    return builder.buildFuture();
  }
}
