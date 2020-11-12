package org.doka.plugin.mention;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.doka.plugin.mention.data.DataManager;

import java.util.ArrayList;
import java.util.List;

public class MentionCommand implements CommandExecutor {
    public DataManager data;
    private FileConfiguration config;


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Main.autoColor("&c&lWARNING &7:&f 콘솔에선 사용이 불가능 합니다!"));
            return false;
        }
        if(strings.length == 0)
        {
            Player player = ((Player) commandSender).getPlayer();
            Inventory inv = ItemTool.InvCreator("&f[&9&lMention&f]",3,true);

            ItemStack Item = new ItemStack(Material.STAINED_GLASS_PANE);
            ItemMeta itemMeta = Item.getItemMeta();
            itemMeta.setDisplayName(" ");
            Item.setItemMeta(itemMeta);
            Item.setDurability((short) 0);

            inv.setItem(10,Item);
            inv.setItem(11,Item);
            inv.setItem(12,Item);
            inv.setItem(14,Item);
            inv.setItem(15,Item);
            inv.setItem(16,Item);

            Item = new ItemStack(Material.WOOL);
            itemMeta = Item.getItemMeta();
            itemMeta.setDisplayName(Main.autoColor("&f[ &e&l맨션 알림 설정 &f]"));

            List<String> lore = new ArrayList<String>();

            if(Main.alarm.get(player)){
                lore.add(Main.autoColor("&f:: &7맨션 알림을 ON/OFF 합니다."));
                lore.add(" ");
                lore.add(Main.autoColor("&8현재 상태: &a&lON"));
                itemMeta.setLore(lore);
                Item.setItemMeta(itemMeta);
                Item.setDurability((short) 5);
            }
            else {
                lore.add(Main.autoColor("&f:: &7맨션 알림을 ON/OFF 합니다."));
                lore.add(" ");
                lore.add(Main.autoColor("&8현재 상태: &c&lOFF"));
                itemMeta.setLore(lore);
                Item.setItemMeta(itemMeta);
                Item.setDurability((short) 14);
            }
            inv.setItem(13,Item);
            //if(data.getConfig().set("alarm."+player.getUniqueId().toString(),false))
            
            player.openInventory(inv);
        }
        return false;
    }
}
