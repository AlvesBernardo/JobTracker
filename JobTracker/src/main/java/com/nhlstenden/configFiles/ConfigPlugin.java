package com.nhlstenden.configFiles;

import io.javalin.config.JavalinConfig;
import io.javalin.plugin.bundled.CorsPluginConfig;

import java.util.Properties;

public class ConfigPlugin
{
    private static final Properties properties = new Properties();
    private static final ClassLoader loader = Thread.currentThread().getContextClassLoader();


    public static void configure(JavalinConfig config)
    {
        config.bundledPlugins.enableCors(cors ->
        {
            String allowedOrigins = properties.getProperty("cors.allowedOrigins", "*");
            cors.addRule(CorsPluginConfig.CorsRule::anyHost);
        });
    }
}
