package io.github._4drian3d.titleannouncer.common.configuration;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@SuppressWarnings("FieldMayBeFinal")
@ConfigSerializable
public class Configuration implements Section {
  @Comment("Title announcements default values configuration")
  private Title title = new Title();

  public Title title() {
    return title;
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
}
