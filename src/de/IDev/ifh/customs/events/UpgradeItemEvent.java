package de.IDev.ifh.customs.events;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.CreativeCategory;
import org.bukkit.inventory.ItemStack;

import de.IDev.ifh.FFA;
import de.IDev.ifh.customs.Attributes;
import de.IDev.ifh.customs.CustomItem;

public class UpgradeItemEvent extends Event {

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
	private int level;
	private int newLevel;

	public UpgradeItemEvent(ItemStack originalItem, Player p, int level, int newLevel) {
		this.item = new CustomItem(originalItem);
		this.player = p;
		this.level = level;
		this.newLevel = newLevel;

		item.setLevel(item.getLevel() + 1);
		item.setMaxXp(FFA.getMaxXPForLevel(item.getLevel()));
		if (isWeapon(originalItem.getType())) {
			List<Attributes> weaponatt = Attributes.getWeaponAttributes();
			Attributes att = weaponatt.get(new Random().nextInt(weaponatt.size()));
			double scaling = Attributes.getScaling(att);
			item.setAttribute(att, item.getAttributeValue(att) + scaling);
		}
		originalItem.setItemMeta(item.getItem().getItemMeta());
		if(item.getXp() >= item.getMaxXp()) {
			item.setXp(item.getXp()-item.getMaxXp());
			Bukkit.getPluginManager().callEvent(new UpgradeItemEvent(originalItem, p, level, newLevel));
		}
		p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
	}

	public CustomItem getItem() {
		return this.item;
	}

	public Player getPlayer() {
		return this.player;
	}

	public int getLevel() {
		return this.level;
	}

	public int getNewLevel() {
		return this.newLevel;
	}
	
	private boolean isWeapon(Material m) {
		String mat = m.toString();
		if (m.getCreativeCategory() != CreativeCategory.COMBAT)
			return false;
		if (mat.contains("BOW"))
			return true;
		if (mat.contains("SWORD"))
			return true;
		if (mat.contains("TRIDENT"))
			return true;
		if (mat.contains("CROSSBOW"))
			return true;
		return false;
	}
}
