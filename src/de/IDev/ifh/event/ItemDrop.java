package de.IDev.ifh.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;

public class ItemDrop implements Listener {

	@EventHandler
	public void a(BlockDropItemEvent e) {
		e.setCancelled(true);
	}
}
