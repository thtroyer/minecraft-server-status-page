package com.example.minecraftserverstatuspage.models;

public record ServerConfig(
        String name,
        String address,
        String dynmapAddress,
        int order) {
}
