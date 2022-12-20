package de.IDev.ifh.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import de.IDev.ifh.FFA;
import de.IDev.ifh.Utils.CustomItem;

public class Test implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] args) {
		if (!s.hasPermission("ffa.test")) {
			s.sendMessage("§cDafür hast du keine Berechtigung");
			return true;
		}

		String key = "blaze";
		Player p = (Player) s;

		switch (key) {
		case "anvil":
			p.getWorld().playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, (float) 0.5);
			break;
		case "earthquake":
			int range = 10;
			List<Entity> entities = p.getNearbyEntities(range, range / 2, range);

			if (entities.size() > 1)
				break;
			Player ent = (Player) entities.get(0);
			Location loc = p.getLocation().clone();
			Location target = ent.getLocation();

			new BukkitRunnable() {
				@Override
				public void run() {
					loc.add(ent.getLocation().subtract(loc).multiply(0.1));

					spawnFallingBlock(loc.clone().add(0, 2, 0), loc.clone().add(0, -1, 0).getBlock().getBlockData());

					if (loc.clone().distance(target.clone()) <= 2) {
						ent.setVelocity(new Vector(0, 2, 0));
						ent.damage(10, p);
						this.cancel();
					}
				}
			}.runTaskTimer(FFA.getPlugin(FFA.class), 0L, 2L);

			break;
		case "fallingblock":
			BlockData bd = p.getLocation().clone().add(0, -1, 0).getBlock().getBlockData();
			spawnFallingBlock(p.getLocation().clone().add(0, -0.1, 0), bd);
			break;
		case "item":
			CustomItem item = new CustomItem(Material.IRON_PICKAXE);
			item.setName("§6Spitzhacke der Farmer");
			item.setLore("§bEinst eine wahre besonderheit", "§bist sie heute nur noch", "§bschrott und verdreckt");
			item.addEnchants(Enchantment.DIG_SPEED, 10);
			p.getInventory().addItem(item.getItem());
			break;
		case "blaze":
			Entity e = p.getWorld().spawnEntity(p.getLocation().clone().add(0, 3, 0), EntityType.BLAZE);
			LivingEntity blaze = (LivingEntity) e;
			blaze.setAI(false);
			blaze.setSilent(true);
			
			new BukkitRunnable() {
				@Override
				public void run() {
					if(blaze.isDead()) this.cancel();
					blaze.teleport(p.getLocation().clone().add(0, 3, 0));
				}
			}.runTaskTimer(FFA.getPlugin(FFA.class), 0L, 1L);
			
			break;
		default:
			return true;
		}
		return true;
	}

	public void spawnFallingBlock(Location loc, BlockData m) {
		FallingBlock b = loc.getWorld().spawnFallingBlock(loc, m);
		b.setVelocity(new Vector(0, 0.2, 0));
		Bukkit.getScheduler().runTaskLater(FFA.getPlugin(FFA.class), new Runnable() {

			@Override
			public void run() {
				loc.getBlock().setBlockData(m);
			}
		}, 20L);
		Bukkit.getScheduler().runTaskLater(FFA.getPlugin(FFA.class), new Runnable() {

			@Override
			public void run() {
				b.remove();
			}
		}, 20L);
	}

}
