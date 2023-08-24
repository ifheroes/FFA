package de.IDev.ifh.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.IDev.ifh.customs.CustomItem;

public class ResetPlayer implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender s, Command c, String arg2, String[] args) {
		if (!s.hasPermission("FFA.resetplayer")) {
			s.sendMessage("§cDafür hast du keine Berechtigung");
			return true;
		}
		
		if(args.length == 0) {
			s.sendMessage("§eNutzung: /resetplayer [name]");
			return true;
		}
		
		Player p = Bukkit.getPlayer(args[0]);
		if(!p.isOnline()) {
			s.sendMessage("§cDieser Spieler ist nicht online");
			return true;
		}
		
		p.getInventory().clear();
		
		CustomItem sword = new CustomItem(Material.STONE_SWORD, true, 25);
		sword.setName("§6Schwert");
		CustomItem helmet = new CustomItem(Material.IRON_HELMET, true, 25);
		helmet.setName("§6Helm");
		CustomItem chestplate = new CustomItem(Material.IRON_CHESTPLATE, true, 25);
		chestplate.setName("§6Brustpanzer");
		CustomItem leggings = new CustomItem(Material.IRON_LEGGINGS, true, 25);
		leggings.setName("§6Hose");
		CustomItem boots = new CustomItem(Material.IRON_BOOTS, true, 25);
		boots.setName("§6Schuhe");
		// Upgradle later
		CustomItem pickaxe = new CustomItem(Material.STONE_PICKAXE, false, 0);
		pickaxe.setName("§6Spitzhacke");
		CustomItem apple = new CustomItem(Material.GOLDEN_APPLE, false, 0);
		apple.setName("§6Heilung");
		
		p.getInventory().addItem(sword.getItem());
		p.getInventory().addItem(pickaxe.getItem());
		p.getInventory().addItem(apple.getItem());
		p.getInventory().setArmorContents(
				new ItemStack[] { boots.getItem(), leggings.getItem(), chestplate.getItem(), helmet.getItem() });
		
		
		s.sendMessage("§aDer Spieler "+p.getName()+ " wurde zurückgesetzt");
		
		return true;
	}

}
