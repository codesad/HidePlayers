package me.sad.hideplayers.mixins;

import me.sad.hideplayers.HidePlayers;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderManager.class)
public abstract class MixinRenderManager {
    @Inject(at=@At("HEAD"), method = "shouldRender", cancellable = true)
    public void shouldRender(Entity entityIn, ICamera camera, double camX, double camY, double camZ, CallbackInfoReturnable<Boolean> cir) {
        if (entityIn instanceof EntityOtherPlayerMP) {
            if (!HidePlayers.players.contains(entityIn.getName().toLowerCase()) && !HidePlayers.toggled) {
                cir.setReturnValue(false);
            }
        }
    }
}
