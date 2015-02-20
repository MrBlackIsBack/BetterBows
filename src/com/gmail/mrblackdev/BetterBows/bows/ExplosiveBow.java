package com.gmail.mrblackdev.BetterBows.bows;

import com.gmail.mrblackdev.BetterBows.Core;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

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
 * Time / Date: 16:13 / 19/02/2015
 */
public class ExplosiveBow implements CommandExecutor, Listener {

    public Core plugin;

    public ExplosiveBow(Core instance) {
        plugin = instance;
    }


    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        Projectile proj = e.getEntity();
        if (proj instanceof Arrow) {
            Arrow arrow = (Arrow) proj;
            if (arrow.getShooter() instanceof Player) {
                Player p = (Player) arrow.getShooter();
                if (plugin.expBow.contains(p.getName())) {
                    arrow.getWorld().createExplosion(arrow.getLocation(),
                            plugin.getConfig().getInt("betterbows.explode.explosianSize"));
                }

            }

        }

    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§4Only players can use the Explosive bow!");
            return true;
        }

        if (sender.hasPermission("betterbows.explode")) {
            if (cmd.getName().equalsIgnoreCase("expbow")) {
                if (args.length == 0) {
                    Player p = (Player) sender;
                    if (plugin.lightningBow.contains(p.getName())) {
                        p.sendMessage("§4You can not activate the explosive bow while you have another bow activated!");
                        return true;
                    }
                    if (plugin.expBow.contains(p.getName())) {
                        p.sendMessage("§4Explosive Bow disabled!");
                        plugin.expBow.remove(p.getName());
                        return true;
                    }

                    ItemStack exp = new ItemStack(Material.BOW, 1);
                    ItemMeta expMeta = exp.getItemMeta();

                    expMeta.setDisplayName("§a§nExplosive Bow");
                    expMeta.setLore(Arrays.asList("§c§oBoom!"));

                    exp.setItemMeta(expMeta);

                    if (p.getInventory().contains(exp)) {
                        return true;
                    }

                    p.sendMessage("§2Explosive Bow activated!");
                    p.getInventory().addItem(new ItemStack(exp));
                    p.getInventory().addItem(new ItemStack(Material.ARROW, 64));
                    plugin.expBow.add(p.getName());
                } else if (args[0].equalsIgnoreCase("changepower")) {
                    if (sender.hasPermission("betterbows.explode.change")) {
                        float value = Integer.parseInt(args[1]);
                        float max = plugin.getConfig().getInt("betterbows.explode.maxExplosianSize");
                        if (value > max) {
                            sender.sendMessage("§4Value is to big!");
                            return true;
                        }

                        sender.sendMessage("§2The explosian size has been changed to " + "§a" + value);

                        plugin.getConfig().set("betterbows.explode.explosianSize", value);
                        plugin.saveConfig();

                    }
                }

            }
        }

        return true;
    }


}
