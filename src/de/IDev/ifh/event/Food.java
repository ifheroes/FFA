package de.IDev.ifh.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class Food implements Listener{

	@EventHandler
	public void a(FoodLevelChangeEvent e) {
		e.getEntity().setFoodLevel(20);
		e.setCancelled(true);
	}
	
}
