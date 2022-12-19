package de.IDev.ifh.Utils;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomItem {

	private ItemStack item;
	private ItemMeta meta;
	
	public CustomItem(Material m) {
		this.item = new ItemStack(m);
		this.meta = item.getItemMeta();	
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.setUnbreakable(true);
	}
	
	public void setName(String name) {
		meta.setDisplayName(name);
	}
	
	public void setLore(String... lore) {
		meta.setLore(Arrays.asList(lore));
	}
	
	public void addEnchants(Enchantment ench, int level) {
		meta.addEnchant(ench, level, true);
	}
	
	public ItemStack getItem() {
		this.item.setItemMeta(this.meta);
		return this.item;
	}
}
