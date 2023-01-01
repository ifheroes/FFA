package de.IDev.ifh.event;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.IDev.ifh.FFA;
import de.IDev.ifh.utils.StatsData;

public class Join implements Listener {

	@EventHandler
	public void a(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		p.teleport(getSpawnLoc() != null ? getSpawnLoc() : p.getLocation());
		p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(100);
		e.setJoinMessage("§7› "+p.getName()+" hat das Spiel betreten");
		
		
		p.setMaximumNoDamageTicks(0);
		
		Object kills = FFA.playerData.getobject(p.getUniqueId() + ".kills");
		Object deaths = FFA.playerData.getobject(p.getUniqueId() + ".deaths");
		new StatsData(p, (int) (deaths != null ? deaths : 0), (int) (kills != null ? kills : 0));
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
