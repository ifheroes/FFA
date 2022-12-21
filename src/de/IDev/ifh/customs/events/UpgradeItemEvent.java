package de.IDev.ifh.customs.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import de.IDev.ifh.utils.CustomItem;

public class UpgradeItemEvent extends Event{

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
    	
    	item.setLevel(item.getLevel()+1);
    	item.setMaxXp((int) ((Math.pow(item.getLevel()*0.15, 2)+5)*10));
    	item.setDamage(item.getDamage()+1.5);
    	
    	originalItem.setItemMeta(item.getItem().getItemMeta());
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
}
