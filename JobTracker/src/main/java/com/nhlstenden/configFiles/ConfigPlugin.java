package com.nhlstenden.configFiles;

import io.javalin.config.JavalinConfig;
import io.javalin.plugin.bundled.CorsPluginConfig;

import java.util.Properties;

public class ConfigPlugin
{
    private static final Properties properties = new Properties();
    private static final ClassLoader loader = Thread.currentThread().getContextClassLoader();

    static {
        try {
            properties.load(loader.getResourceAsStream("config.properties"));
        } catch (Exception e) {
            System.err.println("Could not load properties file: " + e.getMessage());
        }
    }

    public static void configure(JavalinConfig config)
    {
        config.bundledPlugins.enableCors(cors ->
        {
            String allowedOrigins = properties.getProperty("cors.allowedOrigins", "*");
            cors.addRule(CorsPluginConfig.CorsRule::anyHost);
        });
    }
}
