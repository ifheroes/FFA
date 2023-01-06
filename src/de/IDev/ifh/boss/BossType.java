package de.IDev.ifh.boss;

import org.bukkit.entity.EntityType;

public enum BossType {

	ZOMBIE, PIGLIN, BLAZE, GOLEM, WOLF;
	
	public static EntityType getEntityType(BossType type) {
		switch (type) {
		case ZOMBIE:
			return EntityType.ZOMBIE;
		case PIGLIN:
			return EntityType.PIGLIN_BRUTE;
		case BLAZE:
			return EntityType.BLAZE;
		case GOLEM:
			return EntityType.IRON_GOLEM;
		case WOLF:
			return EntityType.WOLF;
		default:
			return null;
		}
	}
	
	
}
