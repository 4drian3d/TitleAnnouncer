package io.github._4drian3d.titleannouncer.common.configuration;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal"})
@ConfigSerializable
public class Messages implements Section {
  @Comment("Error sending if no valid target is detected to send the announcement to")
  private String invalidTarget = "<red>You have provided an invalid target";
  private String errorWhileReloadingConfiguration = "<red>An error occurred while reloading the configuration. Check the console for more information";
  private String successfullyReloaded = "<green>Successful configuration reload";

  public String invalidTarget() {
    return invalidTarget;
  }

  public String errorWhileReloadingConfiguration() {
    return errorWhileReloadingConfiguration;
  }

  public String successfullyReloaded() {
    return successfullyReloaded;
  }
}
