package com.insaneItems.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.List;

public class Treecapitator {
    public static ItemStack createTreecapitator() {
        ItemStack axe = new ItemStack(Material.DIAMOND_AXE);
        ItemMeta meta = axe.getItemMeta();
        
        if (meta != null) {
            meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + ChatColor.MAGIC + "III"
                    + ChatColor.DARK_RED + "" + ChatColor.BOLD + "Treecapitator"
                    + ChatColor.GOLD + "" + ChatColor.BOLD + ChatColor.MAGIC + "III");
            meta.addEnchant(Enchantment.DIG_SPEED, 7, true);
            meta.addEnchant(Enchantment.DURABILITY, 7, true);
            
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.DARK_RED + "Deforest I");
            meta.setLore(lore);
            
            axe.setItemMeta(meta);
        }
        
        return axe;
    }
}

