package com.claxon007.destiny;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.claxon007.destiny.enums.Enums.ExecutionType;
import com.claxon007.destiny.enums.Enums.LuckyBlockType;
import com.claxon007.destiny.extra.LittleHelp;
import com.claxon007.destiny.extra.LoadingFunctions;
import com.claxon007.destiny.extra.LuckyBlockBreakEvent;

public class Destiny extends JavaPlugin implements Listener {
	public static String LUCKYPREFIX = "§e§lLuckyBlock §8§l» §r";
	private static Destiny destiny = null;
	public static Plugin instance = Destiny.getInstance();

	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		getCommand("destiny").setExecutor(new com.claxon007.destiny.destiny.Destiny());
		destiny = this;

		if (getConfig().getBoolean("NON_TOCCARE") == false) {
			getConfig().options().copyDefaults(true);
			saveConfig();
			getConfig().set("NON_TOCCARE", true);
			saveConfig();
			loadConfig();
		} else {
			loadConfig();
		}
	}

	public static Destiny getInstance() {
		return destiny;
	}

	public static JavaPlugin getDestiny() {
		if (instance == null)
			instance = Destiny.getPlugin(Destiny.class);
		return (JavaPlugin) instance;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Block b = e.getBlock();
		Player p = e.getPlayer();
		if (b.getType().equals(Material.SPONGE)) {
			e.setCancelled(true);
			if (b.getData() == 0) {
				Bukkit.getPluginManager()
						.callEvent(new LuckyBlockBreakEvent(b.getLocation(), LuckyBlockType.NORMAL, p));
			} else {
				if (p.hasPermission("destiny.vip")) {
					Bukkit.getPluginManager()
							.callEvent(new LuckyBlockBreakEvent(b.getLocation(), LuckyBlockType.VIP, p));
				} else {
					p.sendMessage("§7Devi essere §a§lVIP §7per poter rompere questo blocco!");
				}
			}
		}
	}

	public static void loadConfig() {
		LoadingFunctions.loadItems();
		LoadingFunctions.loadLuckyBlocks();
		LoadingFunctions.loadWetLuckyBlocks();
	}

	@EventHandler
	public void onLuckyBreak(LuckyBlockBreakEvent e) {
		Location l = e.getLocation();
		Player p = e.getPlayer();
		new Location(l.getWorld(), l.getBlockX(), l.getBlockY(), l.getBlockZ()).getBlock().setType(Material.AIR);

		String PREFIX;
		int prob = 1;

		for (Map.Entry<Double, Double> entry : LittleHelp.calculateProbability().entrySet()) {
			prob = (int) (entry.getKey() + 1);
			break;
		}
		
		if (e.getType().equals(LuckyBlockType.NORMAL)) {
			l.getWorld().spawnParticle(Particle.LAVA, l, 10);
			p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5.0F, 1.0F);
			
			PREFIX = "lucky." + prob;
		} else {
			l.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, l, 10);
			p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5.0F, 1.0F);

			PREFIX = "wet_lucky." + prob;
		}
		
		ExecutionType et = ExecutionType.valueOf(getConfig().getString(PREFIX + ".action"));

		LittleHelp.executeCase(et, p, PREFIX, l);
	}
}
