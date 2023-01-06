package de.IDev.ifh.boss;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import de.IDev.ifh.FFA;
import de.IDev.ifh.boss.bosses.ZombieBoss;

public class BossHandler {

	private static ZombieBoss boss;
	
	public BossHandler() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(FFA.getPlugin(FFA.class), new Runnable() {
			
			@Override
			public void run() {
				if(boss != null) return;
				for(World w : Bukkit.getWorlds()) {
					for(Entity e : w.getEntities()) {
						if(!e.getScoreboardTags().contains(IBoss.BOSS)) continue;
						boss = new ZombieBoss(BossType.ZOMBIE);
						System.out.println("yes");
					}
				}
			}
		}, 20L, 20*30L);
	}
	
	public static ZombieBoss getBoss() {
		return boss;
	}

	public static void createBoss(Location loc) {
		ZombieBoss boss = new ZombieBoss(BossType.ZOMBIE);
		boss.spawnBoss(loc);
	}
}
