package de.IDev.ifh.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.IDev.ifh.FFA;
import de.IDev.ifh.utils.CombatLog;

public class Spawn implements CommandExecutor {

	public static List<Player> spawnDelay = new ArrayList<>();
	
	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] args) {
		if(!(s instanceof Player)) return true;
		if(getSpawnLoc() == null) {
			s.sendMessage("§eDer Spawn wurde noch nicht gesetzt");
			return true;
		}
		Player p = (Player) s;
		
		if(spawnDelay.contains(p)) {
			
		}
		if(args.length == 0 && p.getGameMode() == GameMode.CREATIVE) {
			p.teleport(getSpawnLoc());
			return true;	
		}
		if(args.length == 1 && p.hasPermission("ffa.forcespawn")) {
			Player player = Bukkit.getPlayer(args[0]);
			if(player == null) {
				s.sendMessage("§cDer Spieler " + args[1] + " wurde nicht gefunden");
				return true;
			}
			player.teleport(getSpawnLoc());
			return true;
		}
		if(CombatLog.hasPlayer(p)) {
			p.sendMessage("§cDu kannst momentan nicht teleportiert werden");
			return true;
		}
		
		p.sendMessage("§6Du wirst in §e3 Sekunden §6teleportiert");
		spawnDelay.add(p);
		Bukkit.getScheduler().runTaskLater(FFA.getPlugin(FFA.class), new Runnable() {
			
			@Override
			public void run() {
				if(CombatLog.hasPlayer(p)) return;
				if(!spawnDelay.contains(p)) return;
				p.teleport(getSpawnLoc());
				spawnDelay.remove(p);
			}
		}, 20*3L);
		
		
		return true;
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
