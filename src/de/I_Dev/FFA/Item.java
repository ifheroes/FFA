package de.I_Dev.FFA;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

public class Item {

	private ItemStack i = new ItemStack(Material.AIR);

	public Item(Material material, int ammount, String name, ArrayList<String> lore, int subid, String owner) {
		ItemStack im = new ItemStack(material, ammount, (byte) subid);
		ItemMeta m = im.getItemMeta();
		m.setDisplayName(name);
		m.setLore(lore);
		im.setItemMeta(m);
		if(im.getType() == Material.SKULL_ITEM) {
			SkullMeta sm = (SkullMeta) im.getItemMeta();
			sm.setOwner(owner);
			im.setItemMeta(sm);
		}
		i = im;
	}

	public Item(Material material, int ammount, String name, ArrayList<String> lore, int subid) {
		ItemStack im = new ItemStack(material, ammount, (byte) subid);
		ItemMeta m = im.getItemMeta();
		m.setDisplayName(name);
		m.setLore(lore);
		im.setItemMeta(m);
		i = im;
	}

	public Item(Material material, int ammount, String name, ArrayList<String> lore) {
		ItemStack im = new ItemStack(material, ammount);
		ItemMeta m = im.getItemMeta();
		m.setDisplayName(name);
		m.setLore(lore);
		im.setItemMeta(m);
		i = im;
	}
	
	public Item(Material material, int ammount, String name, int subid) {
		ItemStack im = new ItemStack(material, ammount, (byte) subid);
		ItemMeta m = im.getItemMeta();
		m.setDisplayName(name);
		im.setItemMeta(m);
		i = im;
	}

	public Item(Material material, int ammount, String name) {
		ItemStack im = new ItemStack(material, ammount);
		ItemMeta m = im.getItemMeta();
		m.setDisplayName(name);
		im.setItemMeta(m);
		i = im;
	}

	public Item(Material material, int ammount) {
		ItemStack im = new ItemStack(material, ammount);
		i = im;
	}

	public Item(Material material, String name) {
		ItemStack im = new ItemStack(material);
		ItemMeta m = im.getItemMeta();
		m.setDisplayName(name);
		im.setItemMeta(m);
		i = im;
	}

	public Item(Material material) {
		ItemStack im = new ItemStack(material);
		i = im;
	}

	// 				Set's
	public Item setItem(ItemStack itemstack) {
		i = itemstack;
		return this;
	}

	public Item setMaterial(Material material) {
		i.setData(new MaterialData(material));
		return this;
	}

	public Item setAmount(int ammount) {
		i.setAmount(ammount);
		return this;
	}

	public Item setName(String name) {
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(name);
		i.setItemMeta(m);
		return this;
	}

	public Item setLore(ArrayList<String> lore) {
		ItemMeta m = i.getItemMeta();
		m.setLore(lore);
		i.setItemMeta(m);
		return this;
	}

	public Item setUnbreakable(boolean wert) {
		ItemMeta m = i.getItemMeta();
		m.spigot().setUnbreakable(wert);
		i.setItemMeta(m);
		return this;
	}
	
	public Item addEnchantment(Enchantment enchantment, int level) {
		i.addEnchantment(enchantment, level);
		return this;
	}

	@SuppressWarnings("deprecation")
	public Item setData(int subid) {
		ItemStack it = new ItemStack(i.getType(), i.getAmount(), i.getData().getData());
		i = it;
		return this;
	}

	public Item setSkullOwner(String owner) {
		SkullMeta m = (SkullMeta) i.getItemMeta();
		m.setOwner(owner);
		return this;
	}

	//			Get's
	public ItemStack toItem() {
		return i;
	}
	
	public Material getMaterial() {
		return i.getType();
	}
	
	public int getAmmount() {
		return i.getAmount();
	}
	
	public String getName() {
		return i.getItemMeta().getDisplayName();
	}
	
	public ArrayList<String> getLore(){
		return (ArrayList<String>) i.getItemMeta().getLore();
	}
	
	public HashMap<Enchantment, Integer> getEnchantments(){
		return (HashMap<Enchantment, Integer>) i.getEnchantments();
	}
	
	@SuppressWarnings("deprecation")
	public Integer getData() {
		return (int) i.getData().getData();
	}
	
	public String getOwner() {
		return ((SkullMeta) i.getItemMeta()).getOwner();
	}
}
