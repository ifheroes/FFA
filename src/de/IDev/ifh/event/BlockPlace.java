package de.IDev.ifh.event;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace implements Listener {

	@EventHandler
	public void a(BlockPlaceEvent e) {
		if(e.getPlayer().getGameMode() != GameMode.SURVIVAL) return;
		e.setCancelled(true);
	}
	
}
