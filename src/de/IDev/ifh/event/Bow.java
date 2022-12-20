package de.IDev.ifh.event;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

import de.IDev.ifh.FFA;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Bow implements Listener {

	@EventHandler
	public void a(EntityShootBowEvent e) {
		if(!(e.getEntity() instanceof Player)) return;
		Player p = (Player) e.getEntity();
		e.getProjectile().setCustomName("UltraBowShot");
		
		int range = 10;
		for(Entity en : e.getEntity().getNearbyEntities(range, range, range)) {
			if(en instanceof Player) {
			Location t = e.getEntity().getNearbyEntities(10, 10, 10).get(0).getLocation();
			e.getProjectile().setVelocity(t.subtract(e.getEntity().getLocation()).toVector().multiply(e.getForce()));
			break;
			}
		}

		TextComponent force = new TextComponent("§eStrengh: "+e.getForce());
		p.spigot().sendMessage(ChatMessageType.ACTION_BAR, force);
	}

	@EventHandler
	public void a(ProjectileHitEvent e) {
		if (e.getEntity().getCustomName() == null)
			return;
		if (!e.getEntity().getCustomName().equalsIgnoreCase("UltraBowShot"))
			return;

		/*
		 * To get a accurate Location of the arrow
		 * Else The HitEvent will get a estimated location
		 */
		Bukkit.getScheduler().runTaskLater(FFA.getPlugin(FFA.class), new Runnable() {

			@Override
			public void run() {
				Location loc = e.getEntity().getLocation();
				Arrow arrow = (Arrow) loc.getWorld().spawnEntity(loc.clone().add(0, 5, 0), EntityType.ARROW);
				arrow.setVelocity(new Vector(0, -1, 0).normalize().multiply(0.2));
			}
		}, 1L);
	}
}
