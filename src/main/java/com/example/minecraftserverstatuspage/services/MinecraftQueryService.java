package com.example.minecraftserverstatuspage.services;

import com.example.minecraftserverstatuspage.models.ServerConfig;
import com.example.minecraftserverstatuspage.models.ServerConfigStatus;
import com.example.minecraftserverstatuspage.models.ServerStatus;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@CacheConfig(cacheNames = {"serverstatus"})
public class MinecraftQueryService {
    @Cacheable
    public ServerConfigStatus getServerStatus(ServerConfig serverConfig) {
        System.out.println("not cached");
        WebClient client = WebClient.create("https://api.mcsrvstat.us");

        Mono<ServerStatus> response = client.get()
//                .uri( URLEncoder.encode(serverConfig.address(), StandardCharsets.UTF_8))
                .uri("/2/" + serverConfig.address(), StandardCharsets.UTF_8)
                .retrieve()
                .bodyToMono(ServerStatus.class);

        return new ServerConfigStatus(serverConfig, response.block());
    }
}
