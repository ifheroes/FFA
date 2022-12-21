package de.IDev.ifh.utils;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.IDev.ifh.enchantments.CustomEnchant;

public class CustomItem {

	private static String custom = "custom";
	
	private ItemStack item;
	private ItemMeta meta;
	private int level;
	
	public CustomItem(Material m) {
		this.item = new ItemStack(m);
		this.meta = item.getItemMeta();	
		meta.setLocalizedName(custom);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.setUnbreakable(true);
		
		this.level = 0;
	}
	
	/*
	 * Import existing ItemStack
	 */
	public CustomItem(ItemStack item) {
		this.item = item;
		this.meta = item.getItemMeta();
		
		if(meta.hasLocalizedName() && meta.getLocalizedName().equalsIgnoreCase(custom)) {
			//TODO: Initalize all variables
			
			return;
		}
		meta.setLocalizedName("custom");
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
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

	public void addCustomEnchants(CustomEnchant ench, int level) {
		
	}

	public int getLevel() {
		return this.level;
	}	
	
	public ItemStack getItem() {
		this.item.setItemMeta(this.meta);
		return this.item;
	}
	
	public boolean isCustom(ItemStack itemStack) {
		if(!itemStack.hasItemMeta()) return false;
		if(!meta.hasLocalizedName()) return false;
		if(!meta.getLocalizedName().equals(custom)) return false;
		return true;
	}
}
