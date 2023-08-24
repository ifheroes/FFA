package de.IDev.ifh.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Gamemode implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] args) {
		if (!(s instanceof Player))
			return true;
		if (!s.hasPermission("ffa.gamemode")) {
			s.sendMessage("§cDafür hast du keine Berechtigung");
			return true;
		}

		if (args.length == 0) {
			s.sendMessage("§eNutzung: /gamemode [1,2,...]");
			return true;
		}
		if (args.length == 1) {
			Player p = (Player) s;

			switch (args[0]) {
			case "0":
				p.setGameMode(GameMode.SURVIVAL);
				s.sendMessage("§eGamemode zu " + p.getGameMode().toString() + " geändert");
				return true;
			case "1":
				p.setGameMode(GameMode.CREATIVE);
				s.sendMessage("§eGamemode zu " + p.getGameMode().toString() + " geändert");
				return true;
			case "2":
				p.setGameMode(GameMode.ADVENTURE);
				s.sendMessage("§eGamemode zu " + p.getGameMode().toString() + " geändert");
				return true;
			case "3":
				p.setGameMode(GameMode.SPECTATOR);
				s.sendMessage("§eGamemode zu " + p.getGameMode().toString() + " geändert");
				return true;
			default:
				s.sendMessage("§eEtwas ist schief gelaufen");
			}
		}
		if (args.length == 2) {
			Player p = Bukkit.getPlayer(args[1]);
			if (p == null) {
				s.sendMessage("§cDer Spieler " + args[1] + " wurde nicht gefunden");
				return true;
			}

			switch (args[0]) {
			case "0":
				p.setGameMode(GameMode.SURVIVAL);
				s.sendMessage("§eGamemode von " + p.getName() + " zu " + p.getGameMode().toString() + " geändert");
				p.sendMessage("§eDein Gamemode wurde von "+ ((Player) s).getName() + " geändert");
				return true;
			case "1":
				p.setGameMode(GameMode.CREATIVE);
				s.sendMessage("§eGamemode von " + p.getName() + " zu " + p.getGameMode().toString() + " geändert");
				p.sendMessage("§eDein Gamemode wurde von "+ ((Player) s).getName() + " geändert");
				return true;
			case "2":
				p.setGameMode(GameMode.ADVENTURE);
				s.sendMessage("§eGamemode von " + p.getName() + " zu " + p.getGameMode().toString() + " geändert");
				p.sendMessage("§eDein Gamemode wurde von "+ ((Player) s).getName() + " geändert");
				return true;
			case "3":
				p.setGameMode(GameMode.SPECTATOR);
				s.sendMessage("§eGamemode von " + p.getName() + " zu " + p.getGameMode().toString() + " geändert");
				p.sendMessage("§eDein Gamemode wurde von "+ ((Player) s).getName() + " geändert");
				return true;
			default:
				s.sendMessage("§eEtwas ist schief gelaufen");
			}
		}
		return true;
	}
}
