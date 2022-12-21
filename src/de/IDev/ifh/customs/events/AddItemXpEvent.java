package de.IDev.ifh.customs.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import de.IDev.ifh.utils.CustomItem;

public class AddItemXpEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	private CustomItem item;
	private Player player;
	private int xp;
	private int newXp;

	public AddItemXpEvent(ItemStack originalItem, Player p, int xp) {
		this.item = new CustomItem(originalItem);
		this.player = p;
		this.xp = xp;
		this.newXp = item.getXp()+xp;
		if (item.getXp() + xp < item.getMaxXp()) {
			item.setXp(newXp);
			originalItem.setItemMeta(item.getItem().getItemMeta());
		} else {
			item.setXp(item.getMaxXp() - (item.getXp()+xp));
			originalItem.setItemMeta(item.getItem().getItemMeta());
			Bukkit.getPluginManager().callEvent(new UpgradeItemEvent(originalItem, player, item.getLevel(), item.getLevel()+1));
		}
		
		ItemStack cursor = p.getOpenInventory().getCursor();
		if(cursor.getAmount() < 1) {
			cursor.setType(Material.AIR);
		} else {
			cursor.setAmount(cursor.getAmount()-1);
		}
	}

	public CustomItem getItem() {
		return this.item;
	}

	public Player getPlayer() {
		return this.player;
	}

	public int getXp() {
		return this.xp;
	}

	public int getNewXp() {
		return this.newXp;
	}
}
