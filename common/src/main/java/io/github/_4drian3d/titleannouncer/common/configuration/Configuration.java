package io.github._4drian3d.titleannouncer.common.configuration;

import net.kyori.adventure.bossbar.BossBar.Color;
import net.kyori.adventure.bossbar.BossBar.Overlay;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@ConfigSerializable
public class Configuration implements Section {
    private BossBar bossbar = new BossBar();
    private Title title = new Title();

    public BossBar bossbar() {
        return bossbar;
    }

    public Title title() {
        return title;
    }

    @ConfigSerializable
    public static class BossBar {
        private Color defaultColor = Color.PURPLE;
        private Overlay defaultOverlay = Overlay.PROGRESS;

        public Color defaultColor() {
            return defaultColor;
        }

        public Overlay defaultOverlay() {
            return defaultOverlay;
        }
    }

    @ConfigSerializable
    public static class Title {
        private Duration defaultFadeIn = Duration.of(1, ChronoUnit.SECONDS);
        private Duration defaultStay = Duration.of(2, ChronoUnit.SECONDS);
        private Duration defaultFadeOut = Duration.of(1, ChronoUnit.SECONDS);

        public Duration defaultFadeIn() {
            return defaultFadeIn;
        }

        public Duration defaultStay() {
            return defaultStay;
        }

        public Duration defaultFadeOut() {
            return defaultFadeOut;
        }
    }
}
