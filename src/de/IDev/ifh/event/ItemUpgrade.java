package de.IDev.ifh.event;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import de.IDev.ifh.customs.events.AddItemXpEvent;
import de.IDev.ifh.utils.CustomItem;

public class ItemUpgrade implements Listener{

	@EventHandler
	public void a(InventoryClickEvent e) {
		if(e.getCursor() == null) return;
		if(e.getCurrentItem() == null) return;
		if(!CustomItem.isCustom(e.getCurrentItem())) return;
		ItemStack cursor = e.getCursor();
		ItemStack slot = e.getCurrentItem();
		
		if(!e.getCursor().hasItemMeta()) return;
		if(!e.getCursor().getType().toString().contains("IRON")) return;
		
		Bukkit.getPluginManager().callEvent(new AddItemXpEvent(slot, (Player) e.getWhoClicked(), 10));

		e.setCancelled(true);
	}
}
