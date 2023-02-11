package de.IDev.ifh.event;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import de.IDev.ifh.FFA;
import de.IDev.ifh.customs.events.AddItemXpEvent;
import de.IDev.ifh.utils.StatsData;

public class Death implements Listener {

	private int xpForKill = 5;
	
	@EventHandler
	public void a(PlayerDeathEvent e) {
		e.setKeepInventory(true);
		
		Player p = e.getEntity();
		Player killer = p.getKiller();
		
		p.getWorld().playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, (float) 0.5);
		
		if (killer == null) {
			e.setDeathMessage("§7› " + p.getName() + " ist gestroben");
			StatsData.playerStats.get(p).setDeaths(StatsData.playerStats.get(p).getDeaths()+1);
			return;
		}
		e.setDeathMessage("§7› " + killer.getName() + " hat " + p.getName() + " getötet");
		StatsData.playerStats.get(killer).setKills(StatsData.playerStats.get(killer).getKills()+1);
		StatsData.playerStats.get(p).setDeaths(StatsData.playerStats.get(p).getDeaths()+1);
		
		Location loc = p.getLocation();
		p.getWorld().spawnParticle(Particle.BUBBLE_POP, loc.add(0, 1, 0), 100);
		killer.setHealth(killer.getHealth()+10);
		
		Bukkit.getPluginManager().callEvent(new AddItemXpEvent(p.getInventory().getItemInMainHand(), p, xpForKill));
	}
	
	@EventHandler
	public void a(PlayerRespawnEvent e) {
		e.setRespawnLocation(getSpawnLoc());
	}
	
	public Location getSpawnLoc() {
		String world = (String) FFA.worldData.getobject("spawn.world");
		double x = (double) FFA.worldData.getobject("spawn.x");
		double y = (double) FFA.worldData.getobject("spawn.y");
		double z = (double) FFA.worldData.getobject("spawn.z");
		double yaw = Double.parseDouble(FFA.worldData.getobject("spawn.yaw")+"");
		int pitch = (int) FFA.worldData.getobject("spawn.pitch");
		return new Location(Bukkit.getWorld(world), x, y, z, (float) yaw, pitch);
	}
}
