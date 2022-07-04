package com.example.minecraftserverstatuspage.models;

import java.util.*;

public record ServerStatus(
        String isOnline,
        String ip,
        int port,
        Map<String, String> debug,
//        Map<String, Map<String, String[]>> motd,
        Map<String, Object> motd,
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

    public String status() {
        if (players == null) {
            return "Offline";
        }

        List<?> playerList = (List<?>) players.get("list");

        return (playerList != null && playerList.size() > 0) ?
                "Online" :
                "Offline";
    }

    public boolean active() {
        return status().equals("Online");
    }

    public int playerCount() {
        if (players == null) {
            return 0;
        }

        try {
            return (Integer.parseInt(String.valueOf(players.get("online"))));
        } catch (Exception ignored) {
            return 0;
        }
    }

    public Collection<String> playerList() {
        if (players == null) {
            return new ArrayList<>();
        }

        Map<String, String> playerList = (Map<String, String>) players.get("uuid");
        if (playerList == null) {
            return new ArrayList<>();
        }

        return playerList.keySet();
    }
}
