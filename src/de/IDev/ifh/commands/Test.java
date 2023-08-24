package de.IDev.ifh.commands;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import de.IDev.ifh.FFA;
import de.IDev.ifh.boss.BossHandler;
import de.IDev.ifh.boss.BossType;
import de.IDev.ifh.boss.bosses.ZombieBoss;
import de.IDev.ifh.customs.CustomItem;

public class Test implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] args) {
		if (!s.hasPermission("ffa.test")) {
			s.sendMessage("§cDafür hast du keine Berechtigung");
			return true;
		}

		String key = "expobow";
		if(args.length != 0) {
			key = args[0];
		}
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
		case "pickaxe":
			CustomItem item = new CustomItem(Material.IRON_PICKAXE, false, 0);
			item.setName("§6XXX");
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
		case "armor":
			CustomItem helm = new CustomItem(Material.NETHERITE_HELMET, true, 25);
			helm.setName("Strong Helmet");
			helm.addEnchants(Enchantment.PROTECTION_ENVIRONMENTAL, 10);
			
			CustomItem chest = new CustomItem(Material.NETHERITE_CHESTPLATE, true, 25);
			chest.setName("Strong Chestplate");
			chest.addEnchants(Enchantment.PROTECTION_ENVIRONMENTAL, 10);
			
			CustomItem legs = new CustomItem(Material.NETHERITE_LEGGINGS, true, 25);
			legs.setName("Strong Leggings");
			legs.addEnchants(Enchantment.PROTECTION_ENVIRONMENTAL, 10);
			
			CustomItem boots = new CustomItem(Material.NETHERITE_BOOTS, true, 25);
			boots.setName("Strong Boots");
			boots.addEnchants(Enchantment.PROTECTION_ENVIRONMENTAL, 10);
			
			p.getInventory().addItem(helm.getItem());
			p.getInventory().addItem(chest.getItem());
			p.getInventory().addItem(legs.getItem());
			p.getInventory().addItem(boots.getItem());
			break;
		case "ultrabow":
			CustomItem b = new CustomItem(Material.BOW, false, 25);
			b.setName("§bUltra Bow");
			p.getInventory().addItem(b.getItem());
			break;
		case "expobow":
			CustomItem b1 = new CustomItem(Material.BOW, false, 25);
			b1.setName("§cExplosive Bow");
			p.getInventory().addItem(b1.getItem());
			break;
		case "armorstand":
			ArmorStand armorstand = (ArmorStand) p.getWorld().spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);
			armorstand.setBasePlate(false);
			armorstand.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
			armorstand.setSmall(true);
			armorstand.setVisible(false);
			break;
		case "sword":
			CustomItem customItem = new CustomItem(Material.WOODEN_SWORD, true, 25);
			customItem.setName("§6Schwert");
			p.getInventory().addItem(customItem.getItem());
			break;
		case "dsword":
			CustomItem dItem = new CustomItem(Material.NETHERITE_SWORD, true, 25);
			dItem.setName("§4Excalibur");
			p.getInventory().addItem(dItem.getItem());
			break;
		case "changeitem":
			changeItemMeta(p.getInventory().getItemInMainHand());
			break;
		case "food":
			p.setFoodLevel(1);
			break;
		case "boss":
			BossHandler.createBoss(p.getLocation(), BossType.ZOMBIE);
			break;
		case "attack":
			BossHandler.getBoss().attack(p, 5);
			break;
		case "move":
			BossHandler.getBoss().moveTo(p.getLocation(), 2);
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

	public void changeItemMeta(ItemStack stack) {
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("Test"+new Random().nextInt(20));
		stack.setItemMeta(meta);
	}
	
}
