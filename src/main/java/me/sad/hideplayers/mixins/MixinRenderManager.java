package me.sad.hideplayers.mixins;

import me.sad.hideplayers.HidePlayers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RenderManager.class)
public abstract class MixinRenderManager {
    @Shadow public abstract <T extends Entity> Render<T> getEntityRenderObject(Entity entityIn);
    @Overwrite
    public boolean shouldRender(Entity entityIn, ICamera camera, double camX, double camY, double camZ) {
        if (entityIn instanceof EntityOtherPlayerMP) {
            if (!HidePlayers.players.contains(entityIn.getName().toLowerCase()) && !HidePlayers.toggled)
                return false;
        }
        Render<Entity> render = this.<Entity>getEntityRenderObject(entityIn);
        return render != null && render.shouldRender(entityIn, camera, camX, camY, camZ);
    }
}
