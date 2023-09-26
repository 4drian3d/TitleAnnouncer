package io.github._4drian3d.titleannouncer.common.configuration;

import net.kyori.adventure.bossbar.BossBar.Color;
import net.kyori.adventure.bossbar.BossBar.Overlay;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public class Configuration implements Section {
    private BossBar bossbar = new BossBar();

    @ConfigSerializable
    public static class BossBar {
        private Color defaultColor = Color.PURPLE;
        private Overlay defaultOverlay = Overlay.PROGRESS;

        public Color getDefaultColor() {
            return defaultColor;
        }

        public Overlay getDefaultOverlay() {
            return defaultOverlay;
        }
    }
}
