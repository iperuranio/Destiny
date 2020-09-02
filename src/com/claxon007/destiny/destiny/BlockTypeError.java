package com.claxon007.destiny.destiny;

import org.bukkit.Location;

import net.md_5.bungee.api.ChatColor;

public class BlockTypeError extends Exception {
	private static final long serialVersionUID = -8088076206558472273L;

	public BlockTypeError(Location l) {
		super(ChatColor.DARK_RED + "[" + ChatColor.RED + "ERROR AT" + ChatColor.DARK_RED + "] " + ChatColor.GRAY + " "
				+ l.getBlockX() + " " + l.getBlockY() + " " + l.getBlockZ() + ":" + ChatColor.RED
				+ "that block is not a LuckyBlock!");
	}
}
