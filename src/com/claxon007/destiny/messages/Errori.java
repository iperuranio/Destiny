package com.claxon007.destiny.messages;

import org.bukkit.Location;

import net.md_5.bungee.api.ChatColor;

public class Errori {
	public static String getBlockTypeError(Location l) {
		return ChatColor.DARK_RED + "[" + ChatColor.RED + "ERROR AT" + ChatColor.DARK_RED + "]" + ChatColor.GRAY
				+ " [X]" + l.getBlockX() + " [Y]" + l.getBlockY() + " [Z]" + l.getBlockZ() + ": " + ChatColor.RED
				+ "that block is not a LuckyBlock!";
	}

	public static String getBlockError(Location l) {
		return ChatColor.DARK_RED + "[" + ChatColor.RED + "ERROR AT" + ChatColor.DARK_RED + "]" + ChatColor.GRAY
				+ " [X]" + l.getBlockX() + " [Y]" + l.getBlockY() + " [Z]" + l.getBlockZ() + ": " + ChatColor.RED
				+ "that block has a metadata!";
	}

	public static String getParseWrongType(Location l) {
		return ChatColor.DARK_RED + "[" + ChatColor.RED + "ERROR AT" + ChatColor.DARK_RED + "]" + ChatColor.GRAY
				+ " [X]" + l.getBlockX() + " [Y]" + l.getBlockY() + " [Z]" + l.getBlockZ() + ": " + ChatColor.RED
				+ "there is a wrong value in the config that is selected for that block!";
	}
}
