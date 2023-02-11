package de.IDev.ifh.boss;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import de.IDev.ifh.FFA;
import de.IDev.ifh.boss.bosses.ZombieBoss;

public class BossHandler {

	private static Boss boss;
	
	public BossHandler() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(FFA.getPlugin(FFA.class), new Runnable() {
			
			@Override
			public void run() {
				if(boss != null && boss.isAlive()) return;
				for(World w : Bukkit.getWorlds()) {
					for(Entity e : w.getEntities()) {
						if(!e.getScoreboardTags().contains(IBoss.BOSS)) continue;
						boss = new Boss(e);
					}
				}
			}
		}, 20L, 20*10L);
	}
	
	public static Boss getBoss() {
		return boss;
	}

	public static void createBoss(Location loc, BossType type) {
		ZombieBoss zboss = new ZombieBoss(type);
		zboss.spawnBoss(loc);
		boss = zboss;
	}
}
