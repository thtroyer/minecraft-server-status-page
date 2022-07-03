package com.example.minecraftserverstatuspage.models;

import java.util.Map;

public record ServerStatus(
        boolean isOnline,
        String ip,
        int port,
        Map<String, String> debug,
        Map<String, Map<String, String[]>> motd,
        Map<String, Object> players,
        String version,
        int protocol,
        String hostname,
        String icon,
        String software,
        String map,
        String gamemode,
        String serverid,
        Map<String, Object> plugins,
        Map<String, Object> mods,
        Map<String, Object> info) {
}
