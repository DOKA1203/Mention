package org.doka.plugin.mention;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemTool {
    public static Inventory InvCreator(String Name , int Line , boolean IsBorder)
    {

        Inventory inventory = Bukkit.createInventory(null, Line*9, Main.autoColor(Name));

        if(!IsBorder){
            return inventory;
        }

        ItemStack Item = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta itemMeta = Item.getItemMeta();
        itemMeta.setDisplayName(" ");
        Item.setItemMeta(itemMeta);
        Item.setDurability((short) 0);

        if(Line == 3){
            inventory.setItem(0,Item);
            inventory.setItem(1,Item);
            inventory.setItem(2,Item);
            inventory.setItem(3,Item);
            inventory.setItem(4,Item);
            inventory.setItem(5,Item);
            inventory.setItem(6,Item);
            inventory.setItem(7,Item);
            inventory.setItem(8,Item);
            inventory.setItem(9,Item);
            inventory.setItem(17,Item);
            inventory.setItem(18,Item);
            inventory.setItem(19,Item);
            inventory.setItem(20,Item);
            inventory.setItem(21,Item);
            inventory.setItem(22,Item);
            inventory.setItem(23,Item);
            inventory.setItem(24,Item);
            inventory.setItem(25,Item);
            inventory.setItem(26,Item);
        }
        if(Line == 4){
            inventory.setItem(0,Item);
            inventory.setItem(1,Item);
            inventory.setItem(2,Item);
            inventory.setItem(3,Item);
            inventory.setItem(4,Item);
            inventory.setItem(5,Item);
            inventory.setItem(6,Item);
            inventory.setItem(7,Item);
            inventory.setItem(8,Item);
            inventory.setItem(9,Item);
            inventory.setItem(17,Item);
            inventory.setItem(18,Item);
            inventory.setItem(26,Item);
            inventory.setItem(27,Item);
            inventory.setItem(28,Item);
            inventory.setItem(29,Item);
            inventory.setItem(30,Item);
            inventory.setItem(31,Item);
            inventory.setItem(32,Item);
            inventory.setItem(33,Item);
            inventory.setItem(34,Item);
            inventory.setItem(35,Item);

        }
        if(Line == 5){
            inventory.setItem(0,Item);
            inventory.setItem(1,Item);
            inventory.setItem(2,Item);
            inventory.setItem(3,Item);
            inventory.setItem(4,Item);
            inventory.setItem(5,Item);
            inventory.setItem(6,Item);
            inventory.setItem(7,Item);
            inventory.setItem(8,Item);
            inventory.setItem(9,Item);
            inventory.setItem(17,Item);
            inventory.setItem(18,Item);
            inventory.setItem(26,Item);
            inventory.setItem(27,Item);
            inventory.setItem(35,Item);
            inventory.setItem(36,Item);
            inventory.setItem(37,Item);
            inventory.setItem(38,Item);
            inventory.setItem(39,Item);
            inventory.setItem(40,Item);
            inventory.setItem(41,Item);
            inventory.setItem(42,Item);
            inventory.setItem(43,Item);
            inventory.setItem(44,Item);

        }
        if(Line == 6){
            inventory.setItem(0,Item);
            inventory.setItem(1,Item);
            inventory.setItem(2,Item);
            inventory.setItem(3,Item);
            inventory.setItem(4,Item);
            inventory.setItem(5,Item);
            inventory.setItem(6,Item);
            inventory.setItem(7,Item);
            inventory.setItem(8,Item);
            inventory.setItem(9,Item);
            inventory.setItem(17,Item);
            inventory.setItem(18,Item);
            inventory.setItem(26,Item);
            inventory.setItem(27,Item);
            inventory.setItem(35,Item);
            inventory.setItem(36,Item);
            inventory.setItem(44,Item);
            inventory.setItem(45,Item);
            inventory.setItem(46,Item);
            inventory.setItem(47,Item);
            inventory.setItem(48,Item);
            inventory.setItem(49,Item);
            inventory.setItem(50,Item);
            inventory.setItem(51,Item);
            inventory.setItem(52,Item);
            inventory.setItem(53,Item);
        }
        return inventory;
    }
}
