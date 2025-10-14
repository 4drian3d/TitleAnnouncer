package io.github._4drian3d.titleannouncer.common.commands.format;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github._4drian3d.titleannouncer.common.commands.suggestions.TargetSuggestions;

public interface AnnounceNode<C> {
  LiteralArgumentBuilder<C> provideNode(TargetSuggestions<C> targetSuggestions);
}
