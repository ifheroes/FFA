package de.IDev.ifh.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

import de.IDev.ifh.FFA;
import de.IDev.ifh.commands.Spawn;
import de.IDev.ifh.customs.Attributes;
import de.IDev.ifh.customs.CustomItem;

public class Damage implements Listener{

	@EventHandler
	public void a(EntityDamageByEntityEvent e) {
		if(!(e.getDamager() instanceof Player)) return;
		Player one = (Player) e.getDamager();
		Entity two = e.getEntity();
		Spawn.combatLog.add(two.getName());
		Bukkit.getScheduler().runTaskLater(FFA.getPlugin(FFA.class), new Runnable() {
			
			@Override
			public void run() {
				Spawn.combatLog.remove(two.getName());
			}
		}, 20*3L);
		
		ItemStack i = one.getInventory().getItemInMainHand();
		CustomItem weapon = new CustomItem(i);
		/*
		 * Damage
		 */
		double damage = weapon.getAttributeValue(Attributes.DAMAGE);
		System.out.println(damage);
		System.out.println(1+damage/100);
		e.setDamage(e.getDamage()*(1+(damage/100)));
		System.out.println(e.getDamage());
		/*
		 * AttackSpeed
		 */
		
	}
	
	@EventHandler
	public void a(EntityDamageEvent e) {
		if(e.getCause() == DamageCause.FALL) {
			e.setCancelled(true);
		}
	}
}
