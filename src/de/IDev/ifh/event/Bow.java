package de.IDev.ifh.event;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import de.IDev.ifh.FFA;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Bow implements Listener {

	@EventHandler
	public void a(EntityShootBowEvent e) {
		if (!(e.getEntity() instanceof Player))
			return;
		if (!e.getBow().hasItemMeta() || !e.getBow().getItemMeta().hasDisplayName())
			return;
		Player p = (Player) e.getEntity();
		String name = e.getBow().getItemMeta().getDisplayName();
		if (name.equalsIgnoreCase("§bUltra Bow")) {
			e.getProjectile().setCustomName("UltraBowShot");
		}
		if(name.equalsIgnoreCase("§cExplosive Bow")) {
			e.getProjectile().setCustomName("ExplosiveShot");
		}

		int range = 10;
		for (Entity en : e.getEntity().getNearbyEntities(range, range, range)) {
			if (en instanceof Player) {
				Location t = en.getLocation();
				e.getProjectile()
						.setVelocity(t.subtract(e.getEntity().getLocation()).toVector().multiply(e.getForce()));
				break;
			}
		}

		TextComponent force = new TextComponent("§eStrengh: " + e.getForce());
		p.spigot().sendMessage(ChatMessageType.ACTION_BAR, force);
	}

	/*
	 * UltraBowShot
	 */
	@EventHandler
	public void a(ProjectileHitEvent e) {
		if (e.getEntity().getCustomName() == null)
			return;
		if (!e.getEntity().getCustomName().equalsIgnoreCase("UltraBowShot"))
			return;
//		if(!(e.getHitEntity() instanceof Player))
//			return;

		/*
		 * To get a accurate Location of the arrow Else The HitEvent will get a
		 * estimated location
		 */
		Bukkit.getScheduler().runTaskLater(FFA.getPlugin(FFA.class), new Runnable() {

			@Override
			public void run() {
				Location loc = e.getEntity().getLocation();
				List<Arrow> arrows = new ArrayList<>();

				double radius = 5;
				double accuracy = 10;
				for (double i = 0; i <= 360; i = i + accuracy) {
					double x = (Math.sin(Math.toRadians(i)) * radius);
					double z = (Math.cos(Math.toRadians(i)) * radius);
					Arrow arrow = (Arrow) loc.getWorld().spawnEntity(loc.clone().add(x, 5, z), EntityType.ARROW);
					arrow.setGravity(false);
					arrows.add(arrow);
				}
				new BukkitRunnable() {
					@Override
					public void run() {
						Arrow a = arrows.get(0);
						a.setGravity(true);
						Location target = e.getEntity().getLocation();
						if (e.getHitEntity() instanceof Player) {
							target = e.getHitEntity().getLocation();
						}
						a.setVelocity(target.clone().subtract(a.getLocation()).toVector().normalize().multiply(2));
						arrows.remove(0);
						if (arrows.isEmpty())
							this.cancel();
					}
				}.runTaskTimer(JavaPlugin.getPlugin(FFA.class), 0L, 2L);

			}
		}, 1L);
	}
	
	@EventHandler
	public void b(ProjectileHitEvent e) {
		if (e.getEntity().getCustomName() == null)
			return;
		if (!e.getEntity().getCustomName().equalsIgnoreCase("ExplosiveShot"))
			return;
		Bukkit.getScheduler().runTaskLater(JavaPlugin.getPlugin(FFA.class), new Runnable() {

			@Override
			public void run() {
				World w = e.getEntity().getLocation().getWorld();
				w.createExplosion(e.getEntity().getLocation(), 5, false, true, (Entity) e.getEntity().getShooter());
			}
		}, 1L);
		e.getEntity().remove();
	}
}
