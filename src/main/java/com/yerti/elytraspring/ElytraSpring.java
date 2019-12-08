package com.yerti.elytraspring;

import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.io.File;


public class ElytraSpring extends JavaPlugin implements Listener {

    private int velocityMultiplier;

    @Override
    public void onEnable() {

        File config = new File(getDataFolder() + "/ElytraSpring/config.yml");
        if (!config.getParentFile().exists()) config.getParentFile().mkdir();

        if (!config.exists()) {
            saveDefaultConfig();
        }

        velocityMultiplier = getConfig().getInt("velocity-multiplier");

        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!event.getPlayer().isOnGround()) return;
        if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR) return;
        if (event.getAction() != Action.RIGHT_CLICK_AIR) return;

        ItemStack main = event.getPlayer().getInventory().getItemInMainHand();
        ItemStack off = event.getPlayer().getInventory().getItemInOffHand();
        if (main.getType() == Material.FIREWORK_ROCKET) {
            if (event.getPlayer().getInventory().getChestplate() != null && event.getPlayer().getInventory().getChestplate().getType() != Material.AIR) {
                if (event.getPlayer().getInventory().getChestplate().getType() == Material.ELYTRA) {
                    main.setAmount(main.getAmount() - 1);
                    Location location = event.getPlayer().getLocation();
                    event.getPlayer().setVelocity(event.getPlayer().getVelocity().add(event.getPlayer().getLocation().getDirection().multiply(velocityMultiplier)));
                    Firework f = location.getWorld().spawn(location, Firework.class);
                }
            }



        } else if (off.getType() == Material.FIREWORK_ROCKET) {
            off.setAmount(off.getAmount() - 1);
        }
    }


}
