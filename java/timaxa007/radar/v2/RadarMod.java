package timaxa007.radar.v2;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = RadarMod.MODID, name = RadarMod.NAME, version = RadarMod.VERSION)
public class RadarMod {

	public static final String
	MODID = "radar",
	NAME = "Radar Mod v2",
	VERSION = "2";

	@Mod.Instance(MODID)
	public static RadarMod instance;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Events e = new Events();
		MinecraftForge.EVENT_BUS.register(e);
		FMLCommonHandler.instance().bus().register(e);
	}

}
