package de.IDev.ifh.customs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
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
	private HashMap<Attributes, Double> attributes = new HashMap<>();

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
	}

	/*
	 * Import existing ItemStacks
	 */
	public CustomItem(ItemStack item) {
		this.item = item;
		this.meta = item.getItemMeta();
		if (isCustom(item)) {
			ArrayList<String> lore = (ArrayList<String>) meta.getLore();
			this.level = Integer.parseInt(lore.get(0).split(" ")[1]);
			this.maxXp = Integer.parseInt(lore.get(1).split("/")[1].split(" ")[0]);
			this.xp = Integer.parseInt(lore.get(1).split("/")[0].replaceAll("§3", ""));
			
			if(lore.size() < 3) return;
			for(int i = 3; i <= lore.size()-1; i++) {
				String string = lore.get(i);
				string = string.replaceAll("§c", "").replaceAll("§7", "").replaceAll("\\+", "").replaceAll("%", "");
				String[] data = string.split(":");
				Attributes att = Attributes.valueOf(data[0].replaceAll(" ", "_").toUpperCase());
				double value = Double.parseDouble(data[1]); 
				attributes.put(att, value);
			}
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

	public HashMap<Attributes, Double> getAttributes(){
		return this.attributes;
	}
	
	public Double getAttributeValue(Attributes attribute) {
		if(!this.attributes.containsKey(attribute)) return 0.0;
		return this.attributes.get(attribute);
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
	
	public void setAttributes(HashMap<Attributes, Double> attributes) {
		this.attributes = attributes;
	}
	
	public void setAttribute(Attributes attribute, double value) {
		this.attributes.put(attribute, value);
	}
	
	public void addXp(int xp) {
		this.xp = this.xp + xp;
	}
	
	public ItemStack getItem() {
		List<String> lore = new ArrayList<>();
		lore.add("§3Level: " + level);
		lore.add("§3" + xp + "/" + maxXp + " XP");
		if(!attributes.isEmpty()) {
			lore.add(" ");
			for(Map.Entry<Attributes, Double> set : attributes.entrySet()) {
				lore.add("§7"+Attributes.getName(set.getKey())+ ":§c +" + set.getValue()+ "%");
			}
		}
		meta.setLore(lore);
		this.item.setItemMeta(this.meta);
		return this.item;
	}

	public static boolean isCustom(ItemStack itemStack) {
		if(itemStack == null) return false;
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
