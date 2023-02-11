package de.IDev.ifh.boss.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;

import de.IDev.ifh.boss.Boss;

public class EntityCompust implements Listener{

	@EventHandler
	public void a(EntityCombustEvent e) {
		if(Boss.isBoss(e.getEntity())) {
			e.setCancelled(true);
		}
	}
}
