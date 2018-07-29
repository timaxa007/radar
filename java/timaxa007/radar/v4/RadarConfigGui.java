package timaxa007.radar.v4;

import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

public class RadarConfigGui extends GuiConfig {

	public RadarConfigGui(GuiScreen parent) {
		super(parent, new ConfigElement(Config.config.getCategory(Config.categoryGui)).getChildElements(),
				RadarMod.MODID, false, false, GuiConfig.getAbridgedConfigPath(Config.config.toString()));
	}

}
