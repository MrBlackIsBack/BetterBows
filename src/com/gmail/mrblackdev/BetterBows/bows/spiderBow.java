package com.gmail.mrblackdev.BetterBows.bows;

import com.gmail.mrblackdev.BetterBows.Core;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Spider;
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
 * Time / Date: 16:15 / 20/02/2015
 */
public class spiderBow implements CommandExecutor, Listener {

    public Core plugin;

    public spiderBow(Core instance) {
        plugin = instance;
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        Projectile proj = e.getEntity();
        if (proj instanceof Arrow) {
            Arrow arrow = (Arrow) proj;
            if (arrow.getShooter() instanceof Player) {
                Player p = (Player) arrow.getShooter();
                if (plugin.spiderBow.contains(p.getName())) {
                    arrow.getLocation().getWorld().spawn(arrow.getLocation(), Spider.class);
                }

            }

        }

    }




    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "§4Only players can use this!");
            return true;
        }

        if (sender.hasPermission("betterbows.spider")) {
            if (cmd.getName().equalsIgnoreCase("spiderbow")) {
                Player p = (Player) sender;
                if (plugin.expBow.contains(p.getName())) {
                    p.sendMessage("§4You can not activate the explosive bow while you have another bow activated!");
                    return true;
                }
                if (plugin.spiderBow.contains(p.getName())) {
                    sender.sendMessage("§4Spider bow disabled!");
                    plugin.spiderBow.remove(p.getName());
                    return true;
                }

                ItemStack spider = new ItemStack(Material.BOW, 1);
                ItemMeta spiderMeta = spider.getItemMeta();

                spiderMeta.setDisplayName("§a§nSpider Bow");
                spiderMeta.setLore(Arrays.asList("§c§oWho doesn't fear them!"));

                spider.setItemMeta(spiderMeta);

                if (p.getInventory().contains(spider)) {
                    return true;
                } else {

                    p.sendMessage("§2Spider Bow activated!");
                    p.getInventory().addItem(new ItemStack(spider));
                    p.getInventory().addItem(new ItemStack(Material.ARROW, 64));
                    plugin.spiderBow.add(p.getName());
                }

            }
        }

        return true;
    }
}
