package com.example.minecraftserverstatuspage.models.config;

public record ServerConfig(
        String name,
        String address,
        String dynmapAddress,
        int order) {
}
