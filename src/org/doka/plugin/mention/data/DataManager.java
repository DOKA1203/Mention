package org.doka.plugin.mention.data;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.doka.plugin.mention.Main;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class DataManager {
    private final Main plugin;
    private FileConfiguration dataConfig = null;
    private File configFile = null;

    public DataManager(Main plugin){
        this.plugin = plugin;
        saveDefaultConfig();

    }

    public void reloadConfig(){
        if(this.configFile == null){
            this.configFile = new File(this.plugin.getDataFolder(), "data.yml");
        }
        this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);
        InputStream defaultStream = this.plugin.getResource("data.yml");
        if(defaultStream != null){
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.dataConfig.setDefaults(defaultConfig);
        }
    }
    public FileConfiguration getConfig(){
        if(this.dataConfig == null){
            reloadConfig();
        }
        return this.dataConfig;
    }
    public void saveConfig(){
        if(this.dataConfig == null || this.configFile == null){
            return;
        }
        try {
            this.getConfig().save(this.configFile);
        }catch (IOException e){
            plugin.getLogger().log(Level.SEVERE,"Could not save to config");
        }

    }
    public void saveDefaultConfig() {
        if(this.configFile == null){
            this.configFile = new File(this.plugin.getDataFolder(), "data.yml");
        }
        if(!this.configFile.exists()){
            this.plugin.saveResource("data.yml",false);
        }
    }

    public List<String> getStringList() {
        List<String> list = new ArrayList<>();

        for (String value : this.dataConfig.getStringList("busstop.")) {
            list.add(value);
        }
        if(list.isEmpty()){
            plugin.getLogger().log(Level.SEVERE,"fucker");
        }
        return list;
    }
}
