package io.github._4drian3d.titleannouncer.common.configuration;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;

@ConfigSerializable
public class Configuration implements Section {
    private Sounds sounds = new Sounds();

    public Sounds sounds() {
        return this.sounds;
    }

    @ConfigSerializable
    public static class Sounds {
        private Title title = new Title();
        private BossBar bossbar = new BossBar();
        private ActionBar actionbar = new ActionBar();
        private Toast toast = new Toast();

        public Title title() {
            return this.title;
        }

        public BossBar bossbar() {
            return this.bossbar;
        }

        public ActionBar actionbar() {
            return this.actionbar;
        }

        public Toast toast() {
            return this.toast;
        }

        @ConfigSerializable
        public static non-sealed class Title extends SoundConfig {}

        @ConfigSerializable
        public static non-sealed class BossBar extends SoundConfig {}

        @ConfigSerializable
        public static non-sealed class ActionBar extends SoundConfig {}

        @ConfigSerializable
        public static non-sealed class Toast extends SoundConfig {}

        @ConfigSerializable
        public static sealed class SoundConfig {
            private boolean enabled = true;
            private Sound sound = Sound.sound(
                Key.key("entity.experience_orb.pickup"),
                Sound.Source.MUSIC, 5, 5
            );

            public boolean enabled() {
                return this.enabled;
            }

            public Sound sound() {
                return this.sound;
            }
        }
        
    }
}
