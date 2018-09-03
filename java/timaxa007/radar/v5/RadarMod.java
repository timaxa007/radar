package timaxa007.radar.v5;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = RadarMod.MODID, name = RadarMod.NAME, version = RadarMod.VERSION, guiFactory = "timaxa007.radar.v5.GuiFactory")
public class RadarMod {

	public static final String
	MODID = "radar5",
	NAME = "Radar Mod",
	VERSION = "1.5";

	@Mod.Instance(MODID)
	public static RadarMod instance;

	@SideOnly(Side.CLIENT)
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		//Config
		Config.init(event.getSuggestedConfigurationFile());
		//Our event class - Наш класс для эвентов
		final Events e = new Events();
		//buses - шины
		MinecraftForge.EVENT_BUS.register(e);
		FMLCommonHandler.instance().bus().register(e);
	}

}
