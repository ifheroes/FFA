package de.IDev.ifh.customs;

import java.util.ArrayList;
import java.util.List;

public enum Attributes {

	DAMAGE, ATTACK_SPEED, CRIT_CHANCE, CRIT_DAMAGE, SLOW, SPEED, PROTECTION;

	public static List<Attributes> getWeaponAttributes() {
		List<Attributes> a = new ArrayList<>();
		a.add(DAMAGE);
		a.add(ATTACK_SPEED);
		a.add(CRIT_CHANCE);
		a.add(SLOW);
		a.add(CRIT_DAMAGE);
		return a;
	}

	public static List<Attributes> getArmorAttributes() {
		List<Attributes> a = new ArrayList<>();
		a.add(PROTECTION);
		a.add(SPEED);
		return a;
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
		case SLOW:
			return 1;
		case SPEED:
			return 1;
		case PROTECTION:
			return 2.5;
		case CRIT_DAMAGE:
			return 5.25;
		default:
			return 0;
		}
	}
}
