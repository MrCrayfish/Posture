package com.mrcrayfish.posture.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.posture.client.RenderingPlayerInfo;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LivingEntityRenderer.class)
public class OptifineLivingRendererMixin<T extends LivingEntity, M extends EntityModel<T>>
{
    @Shadow
    protected M model;

    @Inject(method = "render*", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/EntityModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;IIFFFF)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void capture(T entity, float entityYaw, float deltaTicks, PoseStack poseStack, MultiBufferSource source, int light, CallbackInfo ci, float f, float f1, float headYaw, float headPitch, float ageInTicks, float limbSwingAmount, float limbSwing)
    {
        if(entity.getType() == EntityType.PLAYER && this.model instanceof PlayerModel<?>)
        {
            RenderingPlayerInfo.get().updateInfo((Player) entity, (PlayerModel<?>) this.model, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch);
        }
        else
        {
            RenderingPlayerInfo.get().resetInfo();
        }
    }
}
