package de.IDev.ifh.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class ItemPickUp implements Listener {

	@EventHandler
	public void a(EntityPickupItemEvent e) {
		if(!(e.getEntity() instanceof Player)) return;
			
		//TODO: Animation
	}
	
}
