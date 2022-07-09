package thtroyer.minecraftserverstatuspage.models.config;

public record ServerConfig(
        String name,
        String address,
        String dynmapAddress,
        int order,
        boolean alwaysShowDynmap) {
}
