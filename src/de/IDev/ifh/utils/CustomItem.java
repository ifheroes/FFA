package de.IDev.ifh.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.CreativeCategory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomItem {

	private static String custom = "custom";

	private ItemStack item;
	private ItemMeta meta;
	private int level;
	private int xp;
	private int maxXp;
	private double damage;

	public CustomItem(Material m) {
		this.item = new ItemStack(m);
		this.meta = item.getItemMeta();
		meta.setLocalizedName(custom);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.setUnbreakable(true);

		this.level = 0;
		this.maxXp = 50;
		this.xp = 0;
		this.damage = 0;
	}

	/*
	 * Import existing ItemStacks
	 */
	public CustomItem(ItemStack item) {
		this.item = item;
		this.meta = item.getItemMeta();
		if (meta.hasLocalizedName() && meta.getLocalizedName().equalsIgnoreCase(custom)) {
			ArrayList<String> lore = (ArrayList<String>) meta.getLore();
			this.level = Integer.parseInt(lore.get(0).split(" ")[1]);
			this.maxXp = Integer.parseInt(lore.get(1).split("/")[1].split(" ")[0]);
			this.xp = Integer.parseInt(lore.get(1).split("/")[0].replaceAll("§3", ""));
			
			if (item.getType().getCreativeCategory() == CreativeCategory.COMBAT) {
				this.damage = Double.parseDouble(lore.get(3).split(" ")[1].replaceAll("\\+", "").replaceAll("%", ""));
			}
			return;
		}
	}

	public void setName(String name) {
		meta.setDisplayName(name);
	}

	public void addEnchants(Enchantment ench, int level) {
		meta.addEnchant(ench, level, true);
	}

	public int getLevel() {
		return this.level;
	}

	public int getMaxXp() {
		return this.maxXp;
	}

	public int getXp() {
		return this.xp;
	}

	public double getDamage() {
		return this.damage;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

	public void setMaxXp(int xp) {
		this.maxXp = xp;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public void addXp(int xp) {
		this.xp = this.xp + xp;
	}

	public ItemStack getItem() {
		List<String> lore = new ArrayList<>();
		lore.add("§3Level: " + level);
		lore.add("§3" + xp + "/" + maxXp + " XP");
		if (item.getType().getCreativeCategory() == CreativeCategory.COMBAT) {
			lore.add(" ");
			lore.add("§7Damage:§c +" + damage + "%");
		}
		meta.setLore(lore);
		this.item.setItemMeta(this.meta);
		return this.item;
	}

	public static boolean isCustom(ItemStack itemStack) {
		if (!itemStack.hasItemMeta())
			return false;
		ItemMeta itemMeta = itemStack.getItemMeta();
		if (!itemMeta.hasLocalizedName())
			return false;
		if (!itemMeta.getLocalizedName().equals(custom))
			return false;
		return true;
	}
}
