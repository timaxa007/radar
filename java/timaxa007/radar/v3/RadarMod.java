package timaxa007.radar.v3;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = RadarMod.MODID, name = RadarMod.NAME, version = RadarMod.VERSION)
public class RadarMod {

	public static final String
	MODID = "radar",
	NAME = "Radar Mod v3",
	VERSION = "3";

	@Mod.Instance(MODID)
	public static RadarMod instance;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		//Our event class - Наш класс для эвентов
		Events e = new Events();
		//Config
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		e.direction = config.get("gui", "direction", e.direction, "0 - left-top, 1 - center-top, 2 - right-top, \n"
				+ "3 - left-center, 4 - center-center, 5 - right-center, \n"
				+ "6 - left-botton, 7 - center-botton, 8 - right-botton.").getInt();
		e.scaleAuto = config.get("gui", "scaleAuto", e.scaleAuto).getBoolean();
		e.scale = (float)config.get("gui", "scale", 0).getDouble();
		e.showDrop = config.get("gui", "showDrop", e.showDrop).getBoolean();
		e.showOnlyPlayer = config.get("gui", "showOnlyPlayer", e.showOnlyPlayer).getBoolean();
		config.save();
		//buses - шины
		MinecraftForge.EVENT_BUS.register(e);
		FMLCommonHandler.instance().bus().register(e);
	}

}
