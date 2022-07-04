package com.example.minecraftserverstatuspage.services;

import com.example.minecraftserverstatuspage.models.config.ServerConfig;
import com.example.minecraftserverstatuspage.models.ServerConfigStatus;
import com.example.minecraftserverstatuspage.models.ServerStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.*;


@Service
@CacheConfig(cacheNames = {"serverstatus"})
public class MinecraftQueryService {
    Logger logger = LoggerFactory.getLogger(MinecraftQueryService.class);
    private Map<ServerConfig, ServerConfigStatus> cache;

    @Deprecated
    public ServerConfigStatus getServerStatus(ServerConfig serverConfig) {
        WebClient client = WebClient.create("https://api.mcsrvstat.us");

        Mono<ServerStatus> response = client.get()
                .uri("/2/" + serverConfig.address(), StandardCharsets.UTF_8)
                .retrieve()
                .bodyToMono(ServerStatus.class);

        return new ServerConfigStatus(serverConfig, response.block());
    }

    public List<ServerConfigStatus> getServerStatus(List<ServerConfig> serverConfigList) {
        List<ServerConfigStatus> results = new ArrayList<>();

        Map<ServerConfig, Mono<ServerStatus>> serverMap = new HashMap<>();

        for (var serverConfig : serverConfigList) {
            if (cacheContainsKey(serverConfig)) {
                results.add(getFromCache(serverConfig));
            } else {
                serverMap.put(serverConfig, getServerStatusAsync(serverConfig));
            }
        }

        serverMap.forEach((key, val) -> results.add(new ServerConfigStatus(key, val.block())));
        results.forEach(item -> cache.put(item.config(), item));

        Collections.sort(results);

        return results;
    }

    private boolean cacheContainsKey(ServerConfig serverConfig) {
        return this.cache.containsKey(serverConfig);
    }

    private ServerConfigStatus getFromCache(ServerConfig serverConfig) {
        return cache.get(serverConfig);
    }

    private Mono<ServerStatus> getServerStatusAsync(ServerConfig serverConfig) {
        WebClient client = WebClient.create("https://api.mcsrvstat.us");

        return client.get()
                .uri("/2/" + serverConfig.address(), StandardCharsets.UTF_8)
                .retrieve()
                .bodyToMono(ServerStatus.class);
    }

    /**
     * Clear cache every 119,000 ms (~<2 minutes)
     */
    @Scheduled(fixedDelay = 119_000)
    public void clearCache() {
        logger.debug("Clearing cache");
        cache = new HashMap<>();
    }

}
