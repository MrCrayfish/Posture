package com.mrcrayfish.posture.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mrcrayfish.posture.api.event.PlayerModelEvent;
import com.mrcrayfish.posture.client.RenderingPlayerInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.AgeableListModel;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Author: MrCrayfish
 */
@Mixin(AgeableListModel.class)
public class AgeableListModelMixin
{
    @SuppressWarnings({"ConstantConditions"})
    @Inject(method = "renderToBuffer", at = @At(value = "HEAD"), cancellable = true)
    public void headRender(PoseStack poseStack, VertexConsumer consumer, int light, int overlay, float r, float g, float b, float a, CallbackInfo ci)
    {
        if(!RenderingPlayerInfo.get().isRendering((AgeableListModel<?>) (Object) this))
            return;
        RenderingPlayerInfo info = RenderingPlayerInfo.get();
        if(MinecraftForge.EVENT_BUS.post(new PlayerModelEvent.Render.Pre(info.getPlayer(), info.getModel(), poseStack, consumer, light, overlay, info.getLimbSwing(), info.getLimbSwingAmount(), info.getAgeInTicks(), info.getHeadYaw(), info.getHeadPitch(), Minecraft.getInstance().getDeltaFrameTime())))
            ci.cancel();
    }

    @SuppressWarnings({"ConstantConditions"})
    @Inject(method = "renderToBuffer", at = @At(value = "TAIL"), cancellable = true)
    public void tailRender(PoseStack poseStack, VertexConsumer consumer, int light, int overlay, float r, float g, float b, float a, CallbackInfo ci)
    {
        if(!RenderingPlayerInfo.get().isRendering((AgeableListModel<?>) (Object) this))
            return;
        RenderingPlayerInfo info = RenderingPlayerInfo.get();
        MinecraftForge.EVENT_BUS.post(new PlayerModelEvent.Render.Post(info.getPlayer(), info.getModel(), poseStack, consumer, light, overlay, info.getLimbSwing(), info.getLimbSwingAmount(), info.getAgeInTicks(), info.getHeadYaw(), info.getHeadPitch(), Minecraft.getInstance().getDeltaFrameTime()));
    }
}
