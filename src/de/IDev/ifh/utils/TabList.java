package de.IDev.ifh.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import de.IDev.ifh.FFA;
import net.luckperms.api.model.user.User;

public class TabList {

	public static void initTabList() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(FFA.getPlugin(FFA.class), new Runnable() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				Scoreboard sm = Bukkit.getScoreboardManager().getMainScoreboard();
				for(Player p : Bukkit.getOnlinePlayers()) {
					
					User user = FFA.luckPermsApi.getUserManager().getUser(p.getUniqueId());
					String prefix = user.getCachedData().getMetaData().getPrefix();
					prefix = prefix != null ? prefix : "";
					prefix = prefix.replaceAll("&", "�");
					
					p.setPlayerListName(prefix + p.getDisplayName());
					p.setPlayerListHeader("\n�b�lInfinityHeroes.de\n");
					p.setPlayerListFooter("\n�7  Wir sind auch auf Discord  \nViel Spa� beim Spielen \n");
					
					int weight = user.getCachedData().getMetaData().getWeight();
					
					if(sm.getTeam(weight+"") == null) {
						sm.registerNewTeam(weight+"");
					}
					sm.getTeam(weight+"").addPlayer(p);
					
				}
				
				for(Team t : sm.getTeams()) {
					if(t.getPlayers().size() == 0) t.unregister();
				}
			}
		}, 40, 40);
	}
}
