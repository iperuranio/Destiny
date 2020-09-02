package com.claxon007.destiny.extra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.claxon007.destiny.Destiny;

public class LoadingFunctions {
	@SuppressWarnings("deprecation")
	public static void loadItems() {
		Objects.ITEMS.clear();

		for (String i : Destiny.getDestiny().getConfig().getStringList("list_items")) {
			HashMap<Integer, Integer> enchants = new HashMap<>();
			List<String> lore = new ArrayList<String>();
			ConfigurationSection cf = Destiny.getDestiny().getConfig().getConfigurationSection("items." + i);

			ItemStack is = new ItemStack(Material.valueOf(cf.getString("item").toUpperCase()), cf.getInt("quantity"));
			ItemMeta im = is.getItemMeta();

			im.setDisplayName("§r" + cf.getString("name").replace('&', '§'));

			for (String l : cf.getStringList("lore")) {
				lore.add(l.replace('&', '§'));
			}
			im.setLore(lore);

			for (String string : cf.getStringList("enchants")) {
				String[] parti = string.split(";");
				enchants.put(Integer.valueOf(parti[0]), Integer.valueOf(parti[1]));
			}

			for (Map.Entry<Integer, Integer> map : enchants.entrySet()) {
				im.addEnchant(Enchantment.getById(map.getKey()), map.getValue(), true);
			}

			is.setItemMeta(im);

			Objects.ITEMS.put(i.toLowerCase(), is);
		}
	}

	public static void loadLuckyBlocks() {
		Objects.PROBABILITY.clear();
		for (int i = 1; i < Integer.MAX_VALUE; i++) {
			if (Destiny.getDestiny().getConfig().getConfigurationSection("lucky." + i) != null) {
				ConfigurationSection lucky = Destiny.getDestiny().getConfig().getConfigurationSection("lucky." + i);
				double l = lucky.getDouble("probability");
				Objects.PROBABILITY.add(l);
			} else {
				break;
			}
		}
	}

	public static void loadWetLuckyBlocks() {
		Objects.WET_PROBABILITY.clear();
		for (int i = 1; i < Integer.MAX_VALUE; i++) {
			if (Destiny.getDestiny().getConfig().getConfigurationSection("wet_lucky." + i) != null) {
				ConfigurationSection lucky = Destiny.getDestiny().getConfig().getConfigurationSection("wet_lucky." + i);
				double l = lucky.getDouble("probability");
				Objects.WET_PROBABILITY.add(l);
			} else {
				break;
			}
		}
	}
}
