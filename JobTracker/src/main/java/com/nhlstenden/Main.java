package com.nhlstenden;

import com.nhlstenden.configFiles.ConfigPlugin;
import com.nhlstenden.route.MainRoute;
import io.javalin.Javalin;


public class Main
{
    public static void main(String[] args)
    {
        Javalin app = Javalin.create(ConfigPlugin::configure);
        app.start(8080);
        new MainRoute().configureRoutes(app);
    }

}
