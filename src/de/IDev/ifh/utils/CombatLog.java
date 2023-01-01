package de.IDev.ifh.utils;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.IDev.ifh.FFA;

public class CombatLog {

	private static HashMap<Player, Double> timer = new HashMap<>();
	public CombatLog() {
		System.out.println("Initializing CombatLog");
		Bukkit.getScheduler().scheduleSyncRepeatingTask(FFA.getPlugin(FFA.class), new Runnable() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				for(Entry<Player, Double> e : ((HashMap<Player, Double>) timer.clone()).entrySet()) {
					double ticksleft = e.getValue() - 1;
					if(ticksleft <= 0) {
						timer.remove(e.getKey());
						continue;
					} else {
						timer.put(e.getKey(), ticksleft);
					}
				}
			}
		}, 1L, 1L);
	}
	
	public static void addPlayer(Player p, double ticks) {
		timer.remove(p);
		timer.put(p, ticks);
	}
	
	public static void removePlayer(Player p) {
		timer.remove(p);
	}
	
	public static boolean hasPlayer(Player p) {
		return timer.containsKey(p);
	}
	
	public static double getTicksOfPlayer(Player p) {
		return timer.containsKey(p) ? timer.get(p) : 0;
	}
}
