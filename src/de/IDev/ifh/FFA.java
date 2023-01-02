package de.IDev.ifh;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import de.IDev.ifh.commands.DeleteMessage;
import de.IDev.ifh.commands.Gamemode;
import de.IDev.ifh.commands.Heal;
import de.IDev.ifh.commands.Kill;
import de.IDev.ifh.commands.SetBossSpawn;
import de.IDev.ifh.commands.SetSpawn;
import de.IDev.ifh.commands.SetWarp;
import de.IDev.ifh.commands.Spawn;
import de.IDev.ifh.commands.Stats;
import de.IDev.ifh.commands.Test;
import de.IDev.ifh.commands.Warp;
import de.IDev.ifh.customs.BossHandler;
import de.IDev.ifh.event.BlockBreak;
import de.IDev.ifh.event.BlockPlace;
import de.IDev.ifh.event.Bow;
import de.IDev.ifh.event.Chat;
import de.IDev.ifh.event.Damage;
import de.IDev.ifh.event.Death;
import de.IDev.ifh.event.Explosion;
import de.IDev.ifh.event.Food;
import de.IDev.ifh.event.ItemPickUp;
import de.IDev.ifh.event.ClickItem;
import de.IDev.ifh.event.Join;
import de.IDev.ifh.event.Leave;
import de.IDev.ifh.event.Phsic;
import de.IDev.ifh.event.Shovel;
import de.IDev.ifh.utils.CombatLog;
import de.IDev.ifh.utils.File;
import de.IDev.ifh.utils.StatsData;
import de.IDev.ifh.utils.TabList;
import net.luckperms.api.LuckPerms;

/*stats:
setspawn:
heal:
kill: */

public class FFA extends JavaPlugin{
	
	public static File playerData = null;
	public static File worldData = null;
	public static LuckPerms luckPermsApi = null;
	
	@Override
	public void onEnable() {
		System.out.println("FFA - Loading");
		playerData = new File("playerdata.yml", null);
		worldData = new File("worlddata.yml", null);
		RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
		if (provider != null) {
		    luckPermsApi = provider.getProvider();
		}
		
		new CombatLog();
		new BossHandler();
		
		//Register Commands
		getCommand("stats").setExecutor(new Stats());
		getCommand("setspawn").setExecutor(new SetSpawn());
		getCommand("heal").setExecutor(new Heal());
		getCommand("kill").setExecutor(new Kill());
		getCommand("deletemessage").setExecutor(new DeleteMessage());
		getCommand("gamemode").setExecutor(new Gamemode());
		getCommand("spawn").setExecutor(new Spawn());
		getCommand("test").setExecutor(new Test());
		getCommand("warp").setExecutor(new Warp());
		getCommand("setwarp").setExecutor(new SetWarp());
		getCommand("setbossspawn").setExecutor(new SetBossSpawn());
		
		//Register Listeners
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new Death(), this);
		pm.registerEvents(new ItemPickUp(), this);
		pm.registerEvents(new Join(), this);
		pm.registerEvents(new Leave(), this);
		pm.registerEvents(new Chat(), this);
		pm.registerEvents(new Damage(), this);
		pm.registerEvents(new Shovel(), this);
		pm.registerEvents(new Phsic(), this);
		pm.registerEvents(new BlockBreak(), this);
		pm.registerEvents(new Bow(), this);
		pm.registerEvents(new Explosion(), this);
		pm.registerEvents(new ClickItem(), this);
		pm.registerEvents(new Food(), this);
		pm.registerEvents(new BlockPlace(), this);
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			Object kills = playerData.getobject(p.getUniqueId() + ".kills");
			Object deaths = playerData.getobject(p.getUniqueId() + ".deaths");
			new StatsData(p, (int) (deaths != null ? deaths : 0), (int) (kills != null ? kills : 0));
		}
		
		TabList.initTabList();
		
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		for(Player p : Bukkit.getOnlinePlayers()) {
			StatsData d = StatsData.playerStats.get(p);
			playerData.set(p.getUniqueId() + ".kills", d.getKills());
			playerData.set(p.getUniqueId() + ".deaths", d.getDeaths());
		}
		
		super.onDisable();
	}
	
	public static int getMaxXPForLevel(int currentlevel) {
		return (int) ((Math.pow(currentlevel * 0.15, 2) + 20) * 10);
	}
}
