package com.insaneItems.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.insaneItems.insaneItems.InsaneItemsMain;

public class ComandoVersion implements CommandExecutor{
private InsaneItemsMain plugin;
	
	public ComandoVersion(InsaneItemsMain plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command comando, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			//From console
			Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE+plugin.nombre+ChatColor.WHITE+"-> No puedes ejecutar comandos desde la consola");
			return false;
		}else {
			//From Player
			Player jugador = (Player) sender;
			if(args.length > 0) {
				
				if(args[0].equalsIgnoreCase("version")) {
					jugador.sendMessage(plugin.nombre+"-> "+ChatColor.WHITE+""+ChatColor.BOLD+"La version del Plugin es: "+ChatColor.GOLD+""+plugin.version+ChatColor.RESET);
					return true;
				}else {
					jugador.sendMessage(plugin.nombre+"-> "+ChatColor.DARK_RED+""+ChatColor.BOLD+"Ese comando no existe!"+ChatColor.RESET);
					return true;
				}
				
			}else {
				jugador.sendMessage(plugin.nombre+"->"+ChatColor.YELLOW+""+ChatColor.BOLD+" Usa /insaneitems version para ver la version del plugin"+ChatColor.RESET);
				return true;
			}
			
		}
		
	}
}
