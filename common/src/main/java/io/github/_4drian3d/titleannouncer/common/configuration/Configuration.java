package io.github._4drian3d.titleannouncer.common.configuration;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@SuppressWarnings("FieldMayBeFinal")
@ConfigSerializable
public class Configuration implements Section {
    private Title title = new Title();

    public Title title() {
        return title;
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
