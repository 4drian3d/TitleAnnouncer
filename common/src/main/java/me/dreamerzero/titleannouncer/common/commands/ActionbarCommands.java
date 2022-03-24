package me.dreamerzero.titleannouncer.common.commands;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;

import org.jetbrains.annotations.NotNull;

import net.kyori.adventure.audience.Audience;

public class ActionbarCommands<A> {
    private final @NotNull Supplier<@NotNull Collection<@NotNull String>> playersSuggestions;
    private final @NotNull Function<@NotNull String, Audience> toAudience;
    private Function<A, Audience> fromAToAudience = a -> Audience.empty();

    public ActionbarCommands(Supplier<Collection<String>> playersSuggestions, Function<@NotNull String, Audience> toAudience){
        this.playersSuggestions = playersSuggestions;
        this.toAudience = toAudience;
    }

    public ActionbarCommands(Supplier<Collection<String>> playersSuggestions, Function<@NotNull String, Audience> toAudience, Function<A, Audience> fromAToAudience){
        this(playersSuggestions, toAudience);
        this.fromAToAudience = fromAToAudience;
    }
    public LiteralArgumentBuilder<A> actionbar(LiteralArgumentBuilder<A> platformArgument){
        return LiteralArgumentBuilder.<A>literal("actionbar")
            .then(LiteralArgumentBuilder.<A>literal("global")
                
            )
            .then(platformArgument);

    }
}
