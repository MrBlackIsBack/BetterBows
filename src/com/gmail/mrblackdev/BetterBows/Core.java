package com.gmail.mrblackdev.BetterBows;

import com.gmail.mrblackdev.BetterBows.bows.ExplosiveBow;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

/**
 * Copyright @ Mr_Black_Is_Back (C)
 * All rights reserved.
 * --------------------------------
 * By all means - no one has permission
 * to take or copy any part of this
 * project. If you would like to use
 * some of this code you can contact
 * me via email: jamesbehan198@gmail.com
 * --------------------------------
 * Time / Date: 16:09 / 19/02/2015
 */
public class Core extends JavaPlugin {

    public ArrayList<String> expBow;
    public ArrayList<String> lightningBow;

    /*
    TODO:
     */

    @Override
    public void onEnable() {
        // Register Bows
        expBow = new ArrayList<String>();
        lightningBow = new ArrayList<String>();

        // Register Commands & Events
        registerCommands();
        registerEvents();


        // Save the default config.
        saveDefaultConfig();

        // Send enable message to console with color.
        Bukkit.getConsoleSender().sendMessage("§2Better bows has been Enabled.");
    }

    @Override
    public void onDisable() {

        // Save the default config
        saveDefaultConfig();

        // Send disable message to console with color.
        Bukkit.getConsoleSender().sendMessage("§4Better bows has been Disabled!");
    }


    // Register Commands method
    public void registerCommands() {
        // Explosive bow.
        getCommand("expbow").setExecutor(new ExplosiveBow(this));
    }

    // Register Events method
    public void registerEvents() {
        PluginManager pm = Bukkit.getServer().getPluginManager();
        // Explosive bow
        pm.registerEvents(new ExplosiveBow(this), this);
    }


}
