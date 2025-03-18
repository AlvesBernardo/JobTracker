package com.nhlstenden.configFiles;

import io.javalin.config.JavalinConfig;

import java.util.Properties;

public class ConfigPlugin
{
    private static final Properties properties = new Properties();
    private static final ClassLoader loader = Thread.currentThread().getContextClassLoader();


//	InputStream input = loader.getResourceAsStream("/config.properites")
//	{
//		try (input == null)
//		{
//			if (input == null)
//			{
//				throw new RuntimeException("Configuration file not found in resources!");
//			}
//			properties.load(input);
//		} catch (IOException e)
//		{
//			throw new RuntimeException("Failed to load configuration", e);
//		}
//	}


    public static void configure(JavalinConfig config)
    {
        config.bundledPlugins.enableCors(cors ->
        {
            String allowedOrigins = properties.getProperty("cors.allowedOrigins", "*");
            cors.addRule(it -> it.anyHost());
        });
    }
}
