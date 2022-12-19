package de.IDev.ifh.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Phsic implements Listener{

	@EventHandler
	public void a(PlayerInteractEvent e) {
		if(e.getAction() == Action.PHYSICAL) {
			e.setCancelled(true);
		}
	}
}
