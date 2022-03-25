package me.dreamerzero.titleannouncer.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;

@Plugin(id = "titleannouncer")
public final class VelocityPlugin {
    private final ProxyServer proxy;

    @Inject
    public VelocityPlugin(ProxyServer proxy){
        this.proxy = proxy;
    }
}
