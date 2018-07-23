package timaxa007.radar.v3;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

@SideOnly(Side.CLIENT)
public class Events {

	private static final Minecraft mc = Minecraft.getMinecraft();
	private static final ResourceLocation texture = new ResourceLocation(RadarMod.MODID, "textures/gui/gui.png");
	private final AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(0, 0, 0, 0, 0, 0);
	private final float radius = 32;
	public static int direction = 0;
	public static boolean
	showDrop = false,
	showOnlyPlayer = false,
	scaleAuto = true;
	public static float scale = 2;

	@SubscribeEvent
	public void drawText(RenderGameOverlayEvent.Post event) {
		switch(event.type) {
		case ALL:
			//Binding texture -- Бинд текстуры
			mc.getTextureManager().bindTexture(texture);
			float offsetX, offsetY;
			boolean ena = false;
			GL11.glPushMatrix();
			ena = GL11.glIsEnabled(GL11.GL_LIGHTING);
			if (ena) GL11.glDisable(GL11.GL_LIGHTING);

			//Radar position -- Положение радара
			GL11.glTranslatef(
					(direction % 3 == 1 ? (event.resolution.getScaledWidth() / 2) :
						direction % 3 == 2 ? event.resolution.getScaledWidth() : 0),
					(direction / 3 == 2 ? event.resolution.getScaledHeight() :
						direction / 3 == 1 ? (event.resolution.getScaledHeight() / 2) : 0), 0);

			//Automatic resizing for radar -- Автоматическое изменение размера для радара
			if (scaleAuto) scale = (float)mc.displayHeight / 540F;
			//Changing the size of our radar -- Изменение размера нашего радара
			GL11.glScalef(scale, scale, scale);

			//Offset radar -- Смещение радара
			GL11.glTranslatef(
					(direction % 3 == 1 ? 0 :
						direction % 3 == 2 ? -36 : 36),
					(direction / 3 == 2 ? -36 : direction / 3 == 1 ? 0 : 36), 0);

			//The main texture of the radar -- Основная текстура радара
			GL11.glPushMatrix();
			//Decrease the size of the main texture -- Уменьшаем размер основной текстуры
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			GL11.glEnable(GL11.GL_BLEND);
			//Staining the main texture -- Окрашивание основной текстуры
			GL11.glColor3f(0.25F, 0.75F, 0.5F);
			mc.ingameGUI.drawTexturedModalRect(-64, -64, 0, 128, 128, 128);
			//Reset color -- Сбрасываем цвет
			GL11.glColor3f(1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();

			//Compass North-South -- Компас север-юг
			GL11.glPushMatrix();
			//Compass position -- Положение компаса
			GL11.glTranslatef(28, 28, 0);
			//Turning the compass -- Поворачивание компаса
			GL11.glRotatef(-mc.thePlayer.rotationYaw + 180F, 0, 0, 1);
			//Decrease the size of the compass -- Уменьшаем размер компаса
			GL11.glScalef(0.75F, 0.75F, 0.75F);
			mc.ingameGUI.drawTexturedModalRect(-4, -8, 0, 4, 8, 16);
			GL11.glPopMatrix();

			//Point for radar center -- Точка для обозначения центра радара
			GL11.glPushMatrix();
			//Decrease the size of the point -- Уменьшаем размер точки
			GL11.glScalef(0.25F, 0.25F, 0.25F);
			mc.ingameGUI.drawTexturedModalRect(-2, -2, 0, 0, 4, 4);
			GL11.glPopMatrix();

			//Show only players -- Показывать только игроков
			if (showOnlyPlayer)
				for (Object o : mc.theWorld.getEntitiesWithinAABB(EntityPlayer.class, aabb)) {
					EntityPlayer entity = (EntityPlayer)o;
					//Remove from search yourself -- Убрать из поиска себя
					if (entity == mc.thePlayer) continue;
					offsetX = (float)(entity.posX - mc.thePlayer.posX);
					offsetY = (float)(entity.posZ - mc.thePlayer.posZ);
					double dis = Math.sqrt(offsetX * offsetX + offsetY * offsetY);
					if (dis > radius) continue;

					double rad = Math.atan2(offsetY, offsetX);
					double deg = rad * (180 / Math.PI);
					deg -= mc.thePlayer.rotationYaw + 180F;
					rad = Math.toRadians(deg);

					GL11.glPushMatrix();
					//Point position -- Позиция точки
					GL11.glTranslated(Math.cos(rad) * (dis * (32F / radius)), Math.sin(rad) * (dis * (32F / radius)), 0);
					//Decrease the size of the point -- Уменьшаем размер точки
					GL11.glScalef(0.5F, 0.5F, 0.5F);
					GL11.glColor3f(0.75F, 0.25F, 0.25F);//Point color -- Цвет точки
					mc.ingameGUI.drawTexturedModalRect(-2, -2, 0, 0, 4, 4);
					GL11.glPopMatrix();
				}
			//Show all living beings -- Показывать всех живых существ
			else
				for (Object o : mc.theWorld.getEntitiesWithinAABB(EntityLivingBase.class, aabb)) {
					EntityLivingBase entity = (EntityLivingBase)o;
					if (entity == mc.thePlayer) continue;
					offsetX = (float)(entity.posX - mc.thePlayer.posX);
					offsetY = (float)(entity.posZ - mc.thePlayer.posZ);
					double dis = Math.sqrt(offsetX * offsetX + offsetY * offsetY);
					if (dis > radius) continue;

					double rad = Math.atan2(offsetY, offsetX);
					double deg = rad * (180 / Math.PI);
					deg -= mc.thePlayer.rotationYaw + 180F;
					rad = Math.toRadians(deg);

					GL11.glPushMatrix();
					//Point position -- Позиция точки
					GL11.glTranslated(Math.cos(rad) * (dis * (32F / radius)), Math.sin(rad) * (dis * (32F / radius)), 0);
					//Decrease the size of the point -- Уменьшаем размер точки
					GL11.glScalef(0.5F, 0.5F, 0.5F);
					GL11.glColor3f(0.75F, 0.25F, 0.25F);//Point color -- Цвет точки
					mc.ingameGUI.drawTexturedModalRect(-2, -2, 0, 0, 4, 4);
					GL11.glPopMatrix();
				}

			//Show drops -- Показывать дропы
			if (showDrop)
				for (Object o : mc.theWorld.getEntitiesWithinAABB(EntityItem.class, aabb)) {
					EntityItem entity = (EntityItem)o;
					offsetX = (float)(entity.posX - mc.thePlayer.posX);
					offsetY = (float)(entity.posZ - mc.thePlayer.posZ);
					double dis = Math.sqrt(offsetX * offsetX + offsetY * offsetY);
					if (dis > radius) continue;

					double rad = Math.atan2(offsetY, offsetX);
					double deg = rad * (180 / Math.PI);
					deg -= mc.thePlayer.rotationYaw + 180F;
					rad = Math.toRadians(deg);

					GL11.glPushMatrix();
					//Point position -- Позиция точки
					GL11.glTranslated(Math.cos(rad) * (dis * (32F / radius)), Math.sin(rad) * (dis * (32F / radius)), 0);
					//Decrease the size of the point -- Уменьшаем размер точки
					GL11.glScalef(0.5F, 0.5F, 0.5F);
					GL11.glColor3f(0.25F, 0.25F, 0.75F);//Point color -- Цвет точки
					mc.ingameGUI.drawTexturedModalRect(-2, -2, 0, 0, 4, 4);
					GL11.glPopMatrix();
				}

			if (ena) GL11.glEnable(GL11.GL_LIGHTING);
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
			//Create a square radius -- Создаём квадратный радиус
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
