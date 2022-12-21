package de.IDev.ifh.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Team;

import de.IDev.ifh.FFA;
import de.IDev.ifh.utils.StatsData;
import net.luckperms.api.model.user.User;

public class Leave implements Listener{

	@SuppressWarnings("deprecation")
	@EventHandler
	public void a(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		e.setQuitMessage("§7› "+p.getName()+" hat das Spiel verlassen");
		
		StatsData d = StatsData.playerStats.get(p);
		FFA.playerData.set(p.getUniqueId() + ".kills", d.getKills());
		FFA.playerData.set(p.getUniqueId() + ".deaths", d.getDeaths());
		StatsData.playerStats.remove(p);
		
		User user = FFA.luckPermsApi.getUserManager().getUser(p.getUniqueId());
		int weight = user.getCachedData().getMetaData().getWeight();

		Team t = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(weight+"");
		t.removePlayer(p);
	}
	
}
