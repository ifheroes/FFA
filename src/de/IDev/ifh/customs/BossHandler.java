package de.IDev.ifh.customs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import de.IDev.ifh.FFA;

public class BossHandler {

	private static Boss boss;
	
	public BossHandler() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(FFA.getPlugin(FFA.class), new Runnable() {
			
			@Override
			public void run() {
				System.out.println("Check");
				for(World w : Bukkit.getWorlds()) {
					for(Entity e : w.getEntities()) {
						if(e.getScoreboardTags().contains(Boss.BOSS)) System.out.println("nice");
					}
				}
			}
		}, 20L, 20*30L);
	}
	
	public Boss getBoss() {
		return this.boss;
	}

	public void createBoss(Location loc) {
		
	}
	
}
