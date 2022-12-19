package de.IDev.ifh.event;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

public class Bow implements Listener{
	
	@EventHandler
	public void a(EntityShootBowEvent e) {
		if(!(e.getEntity() instanceof Player)) return;
		e.getProjectile().setCustomName("UltraBowShot");
		
		
		
		Location t = e.getEntity().getNearbyEntities(10, 10, 10).get(0).getLocation();
		System.out.println(e.getForce());
		e.getProjectile().setVelocity(t.subtract(e.getEntity().getLocation()).toVector().multiply(e.getForce()));
	}
	
	@EventHandler
	public void a(ProjectileHitEvent e) {
		if(e.getEntity().getCustomName() == null) return;
		if(!e.getEntity().getCustomName().equalsIgnoreCase("UltraBowShot")) return;
		Location loc = e.getEntity().getLocation();
		Arrow arrow = (Arrow) loc.getWorld().spawnEntity(loc.clone().add(0, 5, 0), EntityType.ARROW);
		arrow.setVelocity(new Vector(0, -1, 0).normalize().multiply(0.2));
	}
}
