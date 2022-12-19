package de.IDev.ifh.event;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import de.IDev.ifh.FFA;

public class Shovel implements Listener {

	static List<Player> delay = new ArrayList<>();
	static List<Player> knocked = new ArrayList<>();

	@EventHandler
	public void a(PlayerInteractEvent e) {
		if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() == Action.RIGHT_CLICK_BLOCK)
			return;
		Player p = e.getPlayer();
		delay.add(p);

		ItemStack hand = p.getInventory().getItemInMainHand();
		if (hand == null)
			return;
		if (hand.getType() != Material.STONE_SHOVEL)
			return;

		int range = 10;
		List<Entity> entitys = p.getNearbyEntities(range, range / 2, range);
		for (Entity en : entitys) {
			if (!(en instanceof Player))
				continue;
			Player ent = (Player) en;

			Location loc = p.getLocation().clone();
			loc.add(ent.getLocation().subtract(loc).multiply(0.1));
			double steps = 10;

			new BukkitRunnable() {
				int step = 1;

				@Override
				public void run() {
					Location point = loc.clone().add(ent.getLocation().subtract(loc).multiply(step / steps));
					step++;
					BlockData blockdata = point.clone().add(0, -1, 0).getBlock().getBlockData();
					spawnFallingBlock(point.clone().add(0, -1, 0), blockdata);
					
					if (step >= steps) {
						addDamage(ent, p);
						this.cancel();
					}

				}
			}.runTaskTimer(JavaPlugin.getPlugin(FFA.class), 0L, 2L);
		}
	}

	public void spawnFallingBlock(Location loc, BlockData m) {
		FallingBlock b = loc.getWorld().spawnFallingBlock(loc, m);
		b.setVelocity(new Vector(0, 0.2, 0));
		loc.getWorld().spawnParticle(Particle.CRIT, loc.clone().add(0, 0.1, 0), 10);
		Bukkit.getScheduler().runTaskLater(FFA.getPlugin(FFA.class), new Runnable() {

			@Override
			public void run() {
				b.remove();
			}
		}, 20L);
	}

	public void addDamage(Player ent, Player damager) {
		if (knocked.contains(ent))
			return;
		ent.damage(10, damager);
		ent.setVelocity(new Vector(0, 1, 0).normalize().multiply(0.6));
		knocked.add(ent);
		Bukkit.getScheduler().runTaskLater(FFA.getPlugin(FFA.class), new Runnable() {

			@Override
			public void run() {
				knocked.remove(ent);
			}
		}, 20L);
	}

}
