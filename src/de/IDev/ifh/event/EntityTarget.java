package de.IDev.ifh.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import de.IDev.ifh.boss.Boss;
import de.IDev.ifh.boss.BossHandler;
import de.IDev.ifh.boss.BossType;

public class EntityTarget implements Listener{

	 @EventHandler
	 public void a(EntityTargetLivingEntityEvent e) {
		 if(!Boss.isBoss(e.getEntity())) return;
		 if(BossType.getBossType(e.getEntityType()) != BossType.ZOMBIE) return;
		 
		Boss boss = BossHandler.getBoss();
		
		System.out.println(e.getTarget().getName());
		
//		boss.moveTo(e.getTarget().getLocation(), 5);
	 }
	
}
