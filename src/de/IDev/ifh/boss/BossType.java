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
	
	public static BossType getBossType(EntityType type) {
		switch (type) {
		case ZOMBIE:
			return ZOMBIE;
		case PIGLIN_BRUTE:
			return PIGLIN;
		case BLAZE:
			return BLAZE;
		case IRON_GOLEM:
			return GOLEM;
		case WOLF:
			return WOLF;
		default:
			System.out.println("[Error] Illegal EntityType has been initalized: default = Zombie");
			return ZOMBIE;
		}
	}
	
	
}
