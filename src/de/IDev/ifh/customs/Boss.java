package de.IDev.ifh.customs;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;

public class Boss {

	//TODO: More Stats, Attributes, Map/List for Attacks
	
	public static String BOSS = "BOSS";
	
	private Zombie ent;
	private Location spawnloc;
	private int health;
	private int armor;
	private int damage;
	
	public Boss(Location loc) {
		this.spawnloc = loc;
	}
	
	public void spawnBoss() {
		ent = (Zombie) spawnloc.getWorld().spawnEntity(spawnloc, EntityType.ZOMBIE);
		ent.setAI(false);
		ent.addScoreboardTag(BOSS);
	}
}
