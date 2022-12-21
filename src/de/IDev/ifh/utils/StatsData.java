package de.IDev.ifh.utils;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class StatsData {

	public static HashMap<Player, StatsData> playerStats = new HashMap<>();
	
	private int deaths;
	private int kills;
	
	public StatsData(Player p, int deaths, int kills) {
		this.deaths = deaths;
		this.kills = kills;
		playerStats.put(p, this);
	}
	
	public void setKills(int kills) {
		this.kills = kills;
	}
	
	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}
	
	public int getKills() {
		return this.kills;
	}
	
	public int getDeaths() {
		return this.deaths;
	}
	
	public double getKd() {
		return Math.round (((double) this.kills/(double) (this.deaths == 0 ? 1 : deaths)) * 100.0) / 100.0;
	}
}
