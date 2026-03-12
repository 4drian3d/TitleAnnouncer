package io.github._4drian3d.titleannouncer.common.configuration;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal"})
@ConfigSerializable
public class Messages implements Section {
  @Comment("Actionbar Messages")
  private Actionbar actionbar = new Actionbar();
  @Comment("Bossbar Messages")
  private Bossbar bossbar = new Bossbar();
  @Comment("Chat Messages")
  private Chat chat = new Chat();
  @Comment("Reload Messages")
  private Reload reload = new Reload();
  @Comment("Sound Messages")
  private Sound sound = new Sound();
  @Comment("Title Messages")
  private Title title = new Title();
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

  public Actionbar actionbar() {
    return actionbar;
  }

  public Bossbar bossbar() {
    return bossbar;
  }

  public Chat chat() {
    return chat;
  }

  public Title title() {
    return title;
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
  public static class Actionbar {
    private String announceSent = "<green>The actionbar announcement has been sent successfully";

    public String announceSent() {
      return announceSent;
    }
  }

  @ConfigSerializable
  public static class Bossbar {
    private String announceSent = "<green>The bossbar announcement has been sent successfully";
    private String invalidColor = "<red>Invalid color provided";
    private String invalidOverlay = "<red>Invalid overlay provided";

    public String invalidColor() {
      return invalidColor;
    }

    public String invalidOverlay() {
      return invalidOverlay;
    }

    public String announceSent() {
      return announceSent;
    }
  }

  @ConfigSerializable
  public static class Chat {
    private String announceSent = "<green>The chat announcement has been sent successfully";

    public String announceSent() {
      return announceSent;
    }
  }

  @ConfigSerializable
  public static class Title {
    private String announceSent = "<green>The title announcement has been sent successfully";

    public String announceSent() {
      return announceSent;
    }
  }

  @ConfigSerializable
  public static class Sound {
    private String invalidSoundProvided = "<dark_red><sound> <red>is not a valid sound";
    private String playingSound = "<aqua>Playing '<gold><sound>' sound to the specified target";

    public String invalidSoundProvided() {
      return invalidSoundProvided;
    }

    public String playingSound() {
      return playingSound;
    }
  }

  @ConfigSerializable
  public static class Clear {
    private String bossbarCleared = "<green>All bossbars sent to the specified target have been cleared.";
    private String titleCleared = "<green>All active titles for the specified target have been cleaned up";

    public String bossbarCleared() {
      return bossbarCleared;
    }

    public String titleCleared() {
      return titleCleared;
    }
  }
}
