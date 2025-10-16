package io.github._4drian3d.titleannouncer.common.configuration;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal"})
@ConfigSerializable
public class Messages implements Section {
  @Comment("Reload Messages")
  private Reload reload = new Reload();
  @Comment("Sound Messages")
  private Sound sound = new Sound();
  @Comment("Error sending if no valid target is detected to send the announcement to")
  private String invalidTarget = "<red>You have provided an invalid target";

  public String invalidTarget() {
    return invalidTarget;
  }

  public Reload reload() {
    return reload;
  }

  public Sound sound() {
    return sound;
  }

  @ConfigSerializable
  public static class Reload {
    private String errorWhileReloadingConfiguration = "<red>An error occurred while reloading the configuration. Check the console for more information";
    private String successfullyReloaded = "<green>Successful configuration reload";

    public String errorWhileReloadingConfiguration() {
      return errorWhileReloadingConfiguration;
    }

    public String successfullyReloaded() {
      return successfullyReloaded;
    }
  }

  @ConfigSerializable
  public static class Sound {
    private String invalidSoundProvided = "<dark_red><sound> <red>is not a valid sound";
    private String playingSound = "<aqua>Playing '<gold><sound>' sound";

    public String invalidSoundProvided() {
      return invalidSoundProvided;
    }

    public String playingSound() {
      return playingSound;
    }
  }
}
