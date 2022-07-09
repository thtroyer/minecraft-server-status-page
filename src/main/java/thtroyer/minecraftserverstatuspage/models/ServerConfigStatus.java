package thtroyer.minecraftserverstatuspage.models;

import thtroyer.minecraftserverstatuspage.models.config.ServerConfig;

public record ServerConfigStatus(ServerConfig config, ServerStatus status) implements Comparable<ServerConfigStatus> {
    @Override
    public int compareTo(ServerConfigStatus other) {
        return (this.config.order() > other.config.order()) ?
                1 : -1;

    }
}
