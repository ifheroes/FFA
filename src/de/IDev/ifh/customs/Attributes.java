package de.IDev.ifh.customs;

import java.util.Arrays;
import java.util.List;

public enum Attributes {

	DAMAGE, ATTACK_SPEED, CRIT_CHANCE, CRIT_DAMAGE, PROTECTION;

	public static List<Attributes> getWeaponAttributes() {
		return Arrays.asList(DAMAGE, ATTACK_SPEED, CRIT_CHANCE, CRIT_DAMAGE);
	}

	public static List<Attributes> getArmorAttributes() {
		return Arrays.asList(PROTECTION);
	}

	public static String getName(Attributes attribute) {
		String[] s = attribute.toString().toLowerCase().split("_");
		StringBuilder builder = new StringBuilder();
		for (String editable : s) {

			String part = editable;
			String firstLetter = part.substring(0, 1);
			String remainingLetters = part.substring(1, part.length());
			firstLetter = firstLetter.toUpperCase();
			part = firstLetter + remainingLetters;
			if (!s[s.length - 1].equalsIgnoreCase(editable)) {
				builder.append(part + " ");
			} else {
				builder.append(part);
			}
		}
		return builder.toString();
	}
	
	public static double getScaling(Attributes att) {
		switch (att) {
		case DAMAGE:
			return 4.5;
		case ATTACK_SPEED:
			return 4.75;
		case CRIT_CHANCE:
			return 3.25;
		case PROTECTION:
			return 2.5;
		case CRIT_DAMAGE:
			return 5.25;
		default:
			return 0;
		}
	}
}
