package com.insaneItems.insaneItems;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.insaneItems.commands.AutoPickupCommand;
import com.insaneItems.commands.ComandoVersion;
import com.insaneItems.commands.ExplosiveToolCommand;
import com.insaneItems.enchants.ExplosiveEnchant;


public class InsaneItemsMain extends JavaPlugin{
PluginDescriptionFile pdFile = getDescription();
	
	public String version = pdFile.getVersion();
	public String nombre = ChatColor.DARK_BLUE+""+ChatColor.BOLD+pdFile.getName()+ChatColor.RESET;
	
	public void onEnable() {
		
	    
		AutoPickupCommand autoPickupCommand = new AutoPickupCommand();
	    getCommand("autopickup").setExecutor(autoPickupCommand);
	    Bukkit.getPluginManager().registerEvents(autoPickupCommand, this);
	    Bukkit.getPluginManager().registerEvents(new ExplosiveEnchant(), this);
		Bukkit.getConsoleSender().sendMessage(
				ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Plugin " + ChatColor.RESET  + nombre + ChatColor.GREEN + " Online " + ChatColor.WHITE + "(version: " + ChatColor.GOLD + version + ChatColor.WHITE + ")"
		);
		registrarComandos();

	}
	
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(
				ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Plugin " + ChatColor.RESET  + nombre + ChatColor.DARK_RED + " Offline " + ChatColor.WHITE + "(version: " + ChatColor.GOLD + version + ChatColor.WHITE + ")"
		);
	}
	public void registrarComandos() {
		this.getCommand("insaneitems").setExecutor(new ComandoVersion(this));
		getCommand("picoexplosivo").setExecutor(new ExplosiveToolCommand("pickaxe"));
	    getCommand("hachaexplosiva").setExecutor(new ExplosiveToolCommand("axe"));
	    getCommand("palaexplosiva").setExecutor(new ExplosiveToolCommand("shovel"));
	}
	
	
}
