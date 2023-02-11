package de.IDev.ifh.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeath implements Listener{

	@EventHandler
	public void a(EntityDeathEvent e) {
		e.getDrops().clear();
		e.setDroppedExp(0);
	}
}
