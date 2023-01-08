package de.IDev.ifh.boss.bosses;

import org.bukkit.entity.Entity;

import de.IDev.ifh.boss.Boss;
import de.IDev.ifh.boss.BossType;

public class ZombieBoss extends Boss{

	public ZombieBoss(BossType type) {
		super(type);
	}
	
	public ZombieBoss(Entity en) {
		super(en);
	}
	
}
