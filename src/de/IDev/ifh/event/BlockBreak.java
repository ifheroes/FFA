package de.IDev.ifh.event;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import de.IDev.ifh.customs.CustomItem;

public class BlockBreak implements Listener{

	@EventHandler
	public void a(BlockBreakEvent e) {
		e.setDropItems(false);
		if(e.getPlayer().getGameMode() != GameMode.SURVIVAL) return;
		e.setCancelled(true);
		if(e.getBlock().getType() == Material.IRON_ORE) {
			CustomItem ingot = new CustomItem(Material.IRON_INGOT);
			ingot.setName("§bEisen");
			e.getPlayer().getInventory().addItem(ingot.getItem());
		}
	}
}
