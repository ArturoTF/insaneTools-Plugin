package com.insaneItems.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.ChatColor;
import java.util.ArrayList;
import java.util.List;

public class ExplosiveTools {

    public static ItemStack createExplosivePickaxe() {
        return createExplosiveTool(Material.DIAMOND_PICKAXE, "Pico Explosivo");
    }

    public static ItemStack createExplosiveAxe() {
        return createExplosiveTool(Material.DIAMOND_AXE, "Hacha Explosiva");
    }

    public static ItemStack createExplosiveShovel() {
        return createExplosiveTool(Material.DIAMOND_SPADE, "Pala Explosiva");
    }

    private static ItemStack createExplosiveTool(Material material, String toolName) {
        ItemStack tool = new ItemStack(material);
        ItemMeta meta = tool.getItemMeta();

        if (meta != null) {
            String name = ChatColor.GOLD + "" + ChatColor.BOLD + ChatColor.MAGIC + "III"
                + ChatColor.DARK_RED + "" + ChatColor.BOLD + toolName
                + ChatColor.GOLD + "" + ChatColor.BOLD + ChatColor.MAGIC + "III";
            meta.setDisplayName(name);
            meta.addEnchant(Enchantment.DIG_SPEED, 6, true);
            meta.addEnchant(Enchantment.DURABILITY, 7, true);
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.DARK_RED + "Explosivo I");
            meta.setLore(lore);

            tool.setItemMeta(meta);
        }

        return tool;
    }
}

