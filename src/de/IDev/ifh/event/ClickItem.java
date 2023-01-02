package de.IDev.ifh.event;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import de.IDev.ifh.customs.CustomItem;
import de.IDev.ifh.customs.events.AddItemXpEvent;

public class ClickItem implements Listener {

	@EventHandler
	public void a(InventoryClickEvent e) {
		int xp = 10;

		if (e.getCursor() == null)
			return;
		if (e.getCurrentItem() == null)
			return;
		if (!CustomItem.isCustom(e.getCurrentItem()))
			return;
		if (!CustomItem.isUpgradable(e.getCurrentItem()))
			return;
		ItemStack cursor = e.getCursor();
		ItemStack slot = e.getCurrentItem();
		if (new CustomItem(slot).isMaxLevel())
			return;

		if (!e.getCursor().hasItemMeta())
			return;
		if (e.getCursor().getType() != Material.IRON_INGOT)
			return;

		if (e.getClick() == ClickType.LEFT) {
			xp = xp * cursor.getAmount();
			cursor.setAmount(0);
		}
		if (cursor.getAmount() < 1) {
			cursor.setType(Material.AIR);
		} else {
			cursor.setAmount(cursor.getAmount() - 1);
		}
		Bukkit.getPluginManager().callEvent(new AddItemXpEvent(slot, (Player) e.getWhoClicked(), xp));
		e.setCancelled(true);
	}
}
