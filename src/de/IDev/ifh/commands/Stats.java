package de.IDev.ifh.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.IDev.ifh.utils.StatsData;

public class Stats implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] args) {
		if(!(s instanceof Player)) return true;
		StatsData d = StatsData.playerStats.get((Player) s);
		
		int kills = d.getKills();
		int deaths = d.getDeaths();
		double kd = d.getKd();
		
		s.sendMessage("§7§l¯¯¯¯¯¯¯¯ \n"
				+ "§6Kills: "+kills+ "\n"
				+ "Deaths: "+deaths+"\n"
				+ "KD: "+kd +"\n \n§7§l¯¯¯¯¯¯¯¯");
		
		return true;
	}
}
