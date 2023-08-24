package de.IDev.ifh.utils;

import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import de.IDev.ifh.FFA;

public class File {

	private java.io.File f;
	private YamlConfiguration yml;
	private String name;
	private String path;

	public File(String name, String path) {
		this.name = name;
		this.path = path;

		if (path == null) {
			this.path = FFA.getPlugin(FFA.class).getDataFolder().toString();
		}

		f = new java.io.File(this.path, name);

		if (!f.exists()) {
            f.getParentFile().mkdirs();
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        yml = YamlConfiguration.loadConfiguration(f);
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
        try {
            yml.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
