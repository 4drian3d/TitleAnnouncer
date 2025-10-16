package io.github._4drian3d.titleannouncer.common.configuration;

import net.kyori.adventure.sound.Sound.Source;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@SuppressWarnings("FieldMayBeFinal")
@ConfigSerializable
public class Configuration implements Section {
  @Comment("Title announcements default values configuration")
  private Title title = new Title();
  @Comment("Sound announcements default values configuration")
  private Sound sound = new Sound();

  public Title title() {
    return title;
  }

  public Sound sound() {
    return sound;
  }

  @SuppressWarnings("FieldCanBeLocal")
  @ConfigSerializable
  public static class Title {
    @Comment("Default fade-in value, in milliseconds")
    private int defaultFadeIn = 500;
    @Comment("Default stay value, in milliseconds")
    private int defaultStay = 2000;
    @Comment("Default fade-out value, in milliseconds")
    private int defaultFadeOut = 1000;

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

  @SuppressWarnings("FieldCanBeLocal")
  @ConfigSerializable
  public static class Sound {
    @Comment("Default volume to use")
    private float defaultVolume = 1;
    @Comment("Default pitch to use")
    private float defaultPitch = 1;
    @Comment("Default Sound source to play in")
    private Source defaultSource = Source.MASTER;

    public float defaultVolume() {
      return defaultVolume;
    }

    public float defaultPitch() {
      return defaultPitch;
    }

    public Source defaultSource() {
      return defaultSource;
    }
  }
}
