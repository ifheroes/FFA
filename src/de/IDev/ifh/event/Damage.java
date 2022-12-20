package de.IDev.ifh.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import de.IDev.ifh.FFA;
import de.IDev.ifh.commands.Spawn;

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
		
		
		//TODO: Integrete Stats into this
	}
}
