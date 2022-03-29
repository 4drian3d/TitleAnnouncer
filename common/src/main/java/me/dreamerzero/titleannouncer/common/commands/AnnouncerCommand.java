package me.dreamerzero.titleannouncer.common.commands;

import java.util.function.Function;

import net.kyori.adventure.audience.Audience;

public class AnnouncerCommand<A> {
    protected Function<A, Audience> fromAToAudience = a -> Audience.empty();

    protected Audience getAudience(A possibleAudience){
        if(possibleAudience instanceof Audience audience){
            return audience;
        }
        Audience audience = fromAToAudience.apply(possibleAudience);
        return audience != null ? audience : Audience.empty();
    }
}
