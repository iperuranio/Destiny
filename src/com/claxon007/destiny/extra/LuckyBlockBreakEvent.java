package com.claxon007.destiny.extra;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.claxon007.destiny.enums.Enums.LuckyBlockType;

public class LuckyBlockBreakEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	Location l;
	LuckyBlockType b;
	Player p;

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public LuckyBlockBreakEvent(Location l, LuckyBlockType b, Player p) {
		this.l = l;
		this.b = b;
		this.p = p;
	}

	public Location getLocation() {
		return this.l;
	}

	public LuckyBlockType getType() {
		return this.b;
	}

	public Player getPlayer() {
		return this.p;
	}
}
