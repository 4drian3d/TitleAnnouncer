package io.github._4drian3d.titleannouncer.common.configuration;

import net.kyori.adventure.bossbar.BossBar.Color;
import net.kyori.adventure.bossbar.BossBar.Overlay;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@SuppressWarnings("FieldMayBeFinal")
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

    @SuppressWarnings("FieldCanBeLocal")
    @ConfigSerializable
    public static class Title {
        private int defaultFadeIn = 1;
        private int defaultStay = 2;
        private int defaultFadeOut = 1;

        public int defaultFadeIn() {
            return defaultFadeIn;
        }

        public int defaultStay() {
            return defaultStay;
        }

        public int defaultFadeOut() {
            return defaultFadeOut;
        }
    }
}
