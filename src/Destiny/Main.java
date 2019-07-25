package Destiny;

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
import Enums.Enums.ExecutionType;
import Enums.Enums.LuckyBlockType;
import Extra.LittleHelp;
import Extra.LoadingFunctions;
import Extra.LuckyBlockBreakEvent;

public class Main extends JavaPlugin implements Listener
{	
	public static String LUCKYPREFIX = "§e§lLuckyBlock §8§l» §r";
	private static Main Destiny = null;
	public static Plugin instance = Main.getInstance();
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		getCommand("destiny").setExecutor(new Destiny());
		Destiny = this;
		
		if(getConfig().getBoolean("NON_TOCCARE") == false)
		{
			getConfig().options().copyDefaults(true);
			saveConfig();
			getConfig().set("NON_TOCCARE", true);
			saveConfig();
			loadConfig();
		}
		else
		{
			loadConfig();
		}
	}
	
	public static Main getInstance()
	{
		return Destiny;
	}
	
	public static JavaPlugin getDestiny() {
	    if(instance == null) instance = Main.getPlugin(Main.class);
	    return (JavaPlugin) instance;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBreak(BlockBreakEvent e)
	{
		Block b = e.getBlock();
		Player p = e.getPlayer();
		if(b.getType().equals(Material.SPONGE))
		{
			e.setCancelled(true);
			if(b.getData() == 0)
			{
				Bukkit.getPluginManager().callEvent(new LuckyBlockBreakEvent(b.getLocation(), LuckyBlockType.NORMAL, p));
			}
			else
			{
				if(e.getPlayer().hasPermission("destiny.vip"))
				{
					Bukkit.getPluginManager().callEvent(new LuckyBlockBreakEvent(b.getLocation(), LuckyBlockType.VIP, p));
				}
				else
				{
					Player not_vip = e.getPlayer();
					not_vip.sendMessage("§7Devi essere §a§lVIP §7per poter rompere questo blocco!");
				}
			}
		}
	}
	
	static void loadConfig()
	{
		LoadingFunctions.loadItems();
		LoadingFunctions.loadLuckyBlocks();
		LoadingFunctions.loadWetLuckyBlocks();
	}
	
	@EventHandler
	public void onLuckyBreak(LuckyBlockBreakEvent e)
	{
		Location l = e.getLocation();
		Player p = e.getPlayer();
		new Location(l.getWorld(), l.getBlockX(), l.getBlockY(), l.getBlockZ()).getBlock().setType(Material.AIR);
		
		if(e.getType().equals(LuckyBlockType.NORMAL))
		{
			l.getWorld().spawnParticle(Particle.LAVA, l, 10);
			p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5.0F, 1.0F);
			
			int prob = 1;
			
			for(Map.Entry<Double, Double> entry : LittleHelp.calculateProbability().entrySet())
			{
				prob = (int) (entry.getKey() + 1);
				break;
			}
			
			String PREFIX = "lucky."+prob;
			
			ExecutionType et = ExecutionType.valueOf(getConfig().getString(PREFIX+".action"));
			
			LittleHelp.executeCase(et, p, PREFIX, l);
		}
		else
		{
			l.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, l, 10);
			p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5.0F, 1.0F);
			
			int prob = 1;
			
			for(Map.Entry<Double, Double> entry : LittleHelp.calculateProbability().entrySet())
			{
				prob = (int) (entry.getValue() + 1);
				break;
			}
			
			String PREFIX = "wet_lucky."+prob;
			
			ExecutionType et = ExecutionType.valueOf(getConfig().getString(PREFIX+".action"));
			
			LittleHelp.executeCase(et, p, PREFIX, l);
		}
	}
}
