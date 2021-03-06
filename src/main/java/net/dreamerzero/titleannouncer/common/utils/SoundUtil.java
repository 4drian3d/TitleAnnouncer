package net.dreamerzero.titleannouncer.common.utils;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;

public class SoundUtil {
    /**
     * Will play the corresponding sound at the
     * specified volume and pitch to the specified audience.
     * @param sound
     * @param audience
     * @param volume
     * @param pitch
     */
    public static void playSound(
        final String sound,
        final Audience audience,
        final float volume,
        final float pitch) {

        audience.playSound(
            Sound.sound(
                Key.key(sound),
                Sound.Source.MUSIC,
                volume,
                pitch
            )
        );
    }
}
