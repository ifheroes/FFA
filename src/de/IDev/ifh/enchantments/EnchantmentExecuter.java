package de.IDev.ifh.enchantments;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import de.IDev.ifh.utils.CustomItem;

public class EnchantmentExecuter implements Listener{

	/*
	 * At first only for Players
	 */
	
	@EventHandler
	public void a(EntityDamageByEntityEvent e) {
		if(!(e.getDamager() instanceof Player)) return;
		Player p = (Player) e.getDamager();
		CustomItem item = new CustomItem(p.getInventory().getItemInMainHand());
		
	}
}
