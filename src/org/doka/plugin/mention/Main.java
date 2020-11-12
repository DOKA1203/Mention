package org.doka.plugin.mention;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.doka.plugin.mention.data.DataManager;

import java.util.*;

public class Main extends JavaPlugin implements Listener {
    public DataManager data;
    public static HashMap<Player, Boolean> alarm = new HashMap<>();
    public static String Color = "&9";
    public static String usingSound = "BLOCK_NOTE_BELL";
    public static String MentionTitle = "&f[&9&lMention&f]";
    public static String MentionSubtitle = "%player% &f님이 &6&L맨션&f.";
    @Override
    public void onEnable() {
        getLogger().info("로딩됨");
        this.data = new DataManager(this);
        FileConfiguration config = getConfig();
        config.addDefault("Color",Color);
        config.addDefault("sound",usingSound);
        config.addDefault("Title", MentionTitle);
        config.addDefault("SubTitle", MentionSubtitle);

        config.options().copyDefaults(true);
        saveConfig();

        usingSound = config.getString("sound");
        Color = config.getString("Color");
        MentionTitle = config.getString("Title");
        MentionSubtitle = config.getString("SubTitle");

        getCommand("mention").setExecutor(new MentionCommand());

        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        if (Bukkit.getOnlinePlayers().size() > 0) {
            for (Player pl : Bukkit.getOnlinePlayers())
            {
                if(this.data.getConfig().contains("alarm."+pl.getUniqueId().toString()+".ONOFF")){
                    alarm.put(pl,this.data.getConfig().getBoolean("alarm."+pl.getUniqueId().toString()+".ONOFF"));
                }
                else{
                    data.getConfig().set("alarm."+pl.getUniqueId().toString()+".ONOFF",true);
                    alarm.put(pl,true);
                    data.saveConfig();
                }
            }
        }
    }
    @Override
    public void onDisable()
    {
        for (Player pl : Bukkit.getOnlinePlayers()) {
            data.getConfig().set("alarm."+pl.getUniqueId().toString()+".ONOFF", alarm.get(pl));
        }
        data.saveConfig();
    }
    @EventHandler
    public void Join(PlayerJoinEvent evt)
    {
        if(this.data.getConfig().contains("alarm."+evt.getPlayer().getUniqueId().toString()+".ONOFF")){
            alarm.put(evt.getPlayer(),this.data.getConfig().getBoolean("alarm."+evt.getPlayer().getUniqueId().toString()+".ONOFF"));
        }
        else{
            data.getConfig().set("alarm."+evt.getPlayer().getUniqueId().toString()+".ONOFF",true);
            alarm.put(evt.getPlayer(),true);
            data.saveConfig();
        }
    }
    @EventHandler
    public void Quit(PlayerQuitEvent evt)
    {
        data.getConfig().set("alarm."+evt.getPlayer().getUniqueId().toString()+".ONOFF", alarm.get(evt.getPlayer()));
        data.saveConfig();
    }

    @EventHandler
    public void Chat(AsyncPlayerChatEvent event) {
        String Message = event.getMessage();
        String changeMessage = "";
        if (Message.contains("@")) {
            List<Player> PlayerList = new ArrayList<>(Bukkit.getOnlinePlayers());
            PlayerList.sort((o1, o2) -> Integer.compare(o2.getName().length(), o1.getName().length()));
            for (Player loopPlayer : PlayerList) {
                if(Message.contains("@"+loopPlayer.getName()))
                {
                    if(!Message.contains("&9@"+loopPlayer.getName()+"&f"))
                    {
                        changeMessage = Color + "@"+loopPlayer.getName()+"&f";
                        Message=Message.replace('@'+loopPlayer.getName(), changeMessage);
                        if(alarm.get(loopPlayer)){
                            loopPlayer.sendTitle(autoColor(MentionTitle),autoColor(MentionSubtitle.replace("%player%",event.getPlayer().getDisplayName())),10,10,1);
                            loopPlayer.playSound(loopPlayer.getLocation(), Sound.valueOf(usingSound),3,1);
                        }
                    }
                }
            }
            if (Message.contains("@everyone")) {
                if(event.getPlayer().hasPermission("mention.everyone")){
                    changeMessage = Color + "@everyone"+"&f";
                    Message=Message.replace("@everyone", changeMessage);
                    for (Player loopPlayer : PlayerList) {
                        if(alarm.get(loopPlayer)){
                            loopPlayer.sendTitle(autoColor(MentionTitle),autoColor(MentionSubtitle.replace("%player%",event.getPlayer().getDisplayName())),10,10,1);
                            loopPlayer.playSound(loopPlayer.getLocation(), Sound.valueOf(usingSound),3,1);
                        }
                    }
                }
            }
            event.setMessage(autoColor(Message));
        }
    }
    public static String autoColor(String str){ return ChatColor.translateAlternateColorCodes('&', str); }
    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent evt) {
        Inventory open = evt.getClickedInventory();
        if (open == null) {
            return;
        }
        if (evt.getView().getTitle().equals(autoColor("&f[&9&lMention&f]"))) {
            Player player = (Player) evt.getWhoClicked();
            if (evt.getSlotType() == InventoryType.SlotType.CONTAINER) {
                evt.setCancelled(true);
                //evt.getWhoClicked().sendMessage(evt.getCurrentItem().getItemMeta().getDisplayName());

                if (evt.getCurrentItem().getItemMeta().getDisplayName().equals(Main.autoColor("&f[ &e&l맨션 알림 설정 &f]"))) {
                    List<String> lore = evt.getCurrentItem().getItemMeta().getLore();
                    lore.set(2, autoColor("&8현재 상태: &a&lON"));
                    if (alarm.get(player)) {
                        lore.set(2, autoColor("&8현재 상태: &c&lOFF"));
                        ItemMeta a = evt.getCurrentItem().getItemMeta();
                        a.setLore(lore);
                        evt.getCurrentItem().setItemMeta(a);
                        evt.getCurrentItem().setDurability((short) 14);
                        alarm.put(player, false);
                    } else if (!alarm.get(player)) {
                        lore.set(2, autoColor("&8현재 상태: &a&lON"));
                        ItemMeta a = evt.getCurrentItem().getItemMeta();
                        a.setLore(lore);
                        evt.getCurrentItem().setItemMeta(a);
                        evt.getCurrentItem().setDurability((short) 5);
                        alarm.put(player, true);
                    }
                }
            }
        }
    }
}