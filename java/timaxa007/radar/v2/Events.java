package timaxa007.radar.v2;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

@SideOnly(Side.CLIENT)
public class Events {

	private static final Minecraft mc = Minecraft.getMinecraft();
	private static final ResourceLocation texture = new ResourceLocation(RadarMod.MODID, "textures/gui/gui.png");
	private final AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(0, 0, 0, 0, 0, 0);
	private final float radius = 32;

	@SubscribeEvent
	public void drawText(RenderGameOverlayEvent.Post event) {
		switch(event.type) {
		case ALL:
			if (aabb == null) return;
			mc.getTextureManager().bindTexture(texture);
			float offsetX, offsetY;

			GL11.glPushMatrix();
			GL11.glTranslatef((event.resolution.getScaledWidth() / 2), radius, 0);
			GL11.glRotatef(-mc.thePlayer.rotationYaw + 180F, 0, 0, 1);

			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glColor3f(0.5F, 0.5F, 0.5F);
			mc.ingameGUI.drawTexturedModalRect(-(int)radius, -(int)radius, 0, 0, (int)radius * 2, (int)radius * 2);
			GL11.glColor3f(1.0F, 1.0F, 1.0F);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glPopMatrix();

			for (Object o : mc.theWorld.getEntitiesWithinAABB(EntityLivingBase.class, aabb)) {
				EntityLivingBase entity = (EntityLivingBase)o;
				offsetX = (float)(entity.posX - mc.thePlayer.posX);
				offsetY = (float)(entity.posZ - mc.thePlayer.posZ);
				float dis = offsetX + offsetY;
				dis = Math.abs(dis);
				if (dis > radius) continue;
				GL11.glPushMatrix();
				GL11.glTranslatef(offsetX, offsetY, 0);
				//GL11.glScalef(0.5F, 0.5F, 0.5F);
				mc.ingameGUI.drawTexturedModalRect(-2, -2, 0, 0, 4, 4);
				GL11.glPopMatrix();
			}
			GL11.glPopMatrix();

			break;
		default:return;
		}
	}

	@SubscribeEvent
	public void onClientTickEvent(TickEvent.ClientTickEvent event) {
		if (mc.thePlayer == null) return;
		switch(event.phase) {
		case END:
			aabb.minX = mc.thePlayer.posX - radius;
			aabb.maxX = mc.thePlayer.posX + radius;
			aabb.minY = mc.thePlayer.posY - radius;
			aabb.maxY = mc.thePlayer.posY + radius;
			aabb.minZ = mc.thePlayer.posZ - radius;
			aabb.maxZ = mc.thePlayer.posZ + radius;
			break;
		default:break;
		}
	}

}
