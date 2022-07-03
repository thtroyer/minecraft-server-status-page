package com.example.minecraftserverstatuspage.services;

import com.example.minecraftserverstatuspage.models.ServerConfig;
import com.example.minecraftserverstatuspage.models.ServerConfigStatus;
import com.example.minecraftserverstatuspage.models.ServerStatus;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;


@Service
@CacheConfig(cacheNames = {"serverstatus"})
public class MinecraftQueryService {
    private Map<ServerConfig, ServerConfigStatus> cache;

    public ServerConfigStatus getServerStatus(ServerConfig serverConfig) {
        System.out.println("not cached");
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
     * Clear cache every 180,000 ms (3 minutes)
     */
    @Scheduled(fixedDelay = 180_000)
    public void clearCache() {
        cache = new HashMap<>();
    }

}
