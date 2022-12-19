package de.I_Dev.FFA;

import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public class File {

	java.io.File f;
	YamlConfiguration yml;
	String name;
	String path;
	
	public File(String name, String path) {
		this.name = name;
		this.path = path;
		
		if(path == null) {
			this.path = Main.getPlugin(Main.class).getDataFolder().toString();
		}
		
		f = new java.io.File(this.path, name);
		yml = YamlConfiguration.loadConfiguration(f);
		
		save();
	}
	
	public String getname() {
		return name;
	}
	
	public String getpath() {
		return path;
	}
	
	public YamlConfiguration getyml() {
		return yml;
	}
	
	public java.io.File getfile() {
		return f;
	}
	
	public Object getobject(String path) {
		return yml.get(path);
	}
	
	public void set(String path, Object obj) {
		yml.set(path, obj);
		save();
	}
	
	public void save() {
		if(!f.exists()) {
			try {
				f.createNewFile();
				yml.options().copyDefaults(true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			yml.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
