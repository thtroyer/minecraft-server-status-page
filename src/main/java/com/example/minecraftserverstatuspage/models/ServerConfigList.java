package com.example.minecraftserverstatuspage.models;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.util.List;

@ConfigurationProperties(prefix = "servers")
@ConfigurationPropertiesScan
public record ServerConfigList (List<ServerConfig> serverList){
}