package io.github._4drian3d.titleannouncer.common.configuration;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal"})
@ConfigSerializable
public class Messages implements Section {
  private String invalidTarget = "<red>You have provided an invalid target";

  public String invalidTarget() {
    return invalidTarget;
  }
}
