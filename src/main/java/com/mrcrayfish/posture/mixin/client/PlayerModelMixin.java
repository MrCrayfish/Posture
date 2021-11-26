package com.mrcrayfish.posture.mixin.client;

import com.mrcrayfish.posture.api.event.PlayerModelEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Author: MrCrayfish
 */
@Mixin(PlayerModel.class)
public class PlayerModelMixin<T extends LivingEntity> extends HumanoidModel<T>
{
    @Shadow
    @Final
    private boolean slim;

    @Shadow
    @Final
    public ModelPart leftSleeve;

    @Shadow
    @Final
    public ModelPart rightSleeve;

    @Shadow
    @Final
    public ModelPart leftPants;

    @Shadow
    @Final
    public ModelPart rightPants;

    @Shadow
    @Final
    public ModelPart jacket;

    public PlayerModelMixin(ModelPart part)
    {
        super(part);
    }

    @SuppressWarnings({"ConstantConditions"})
    @Inject(method = "setupAnim", at = @At(value = "HEAD"), cancellable = true)
    private void setRotationAnglesHead(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci)
    {
        if(entity.getType() != EntityType.PLAYER)
            return;

        PlayerModel<?> model = (PlayerModel<?>) (Object) this;
        this.restoreModelDefinitions();
        this.resetVisibilities();
        if(MinecraftForge.EVENT_BUS.post(new PlayerModelEvent.Pose.Pre((Player) entity, model, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, Minecraft.getInstance().getDeltaFrameTime())))
        {
            this.updateClothes();
            ci.cancel();
        }
    }

    @SuppressWarnings({"ConstantConditions"})
    @Inject(method = "setupAnim", at = @At(value = "TAIL"))
    private void setRotationAnglesTail(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci)
    {
        if(entity.getType() != EntityType.PLAYER)
            return;

        PlayerModel<?> model = (PlayerModel<?>) (Object) this;
        MinecraftForge.EVENT_BUS.post(new PlayerModelEvent.Pose.Post((Player) entity, model, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, Minecraft.getInstance().getDeltaFrameTime()));
        this.updateClothes();
    }

    private void updateClothes()
    {
        this.rightSleeve.copyFrom(this.rightArm);
        this.leftSleeve.copyFrom(this.leftArm);
        this.rightPants.copyFrom(this.rightLeg);
        this.leftPants.copyFrom(this.leftLeg);
        this.jacket.copyFrom(this.body);
        this.hat.copyFrom(this.head);
    }

    /**
     * Resets all the rotations and rotation points back to their initial values. This makes it
     * so ever developer doesn't have to do it themselves.
     */
    private void restoreModelDefinitions()
    {
        this.resetAll(this.head);
        this.resetAll(this.hat);
        this.resetAll(this.body);
        this.resetAll(this.jacket);

        this.resetAll(this.rightArm);
        this.rightArm.x = -5.0F;
        this.rightArm.y = this.slim ? 2.5F : 2.0F;
        this.rightArm.z = 0.0F;

        this.resetAll(this.rightSleeve);
        this.rightSleeve.x = -5.0F;
        this.rightSleeve.y = this.slim ? 2.5F : 2.0F;
        this.rightSleeve.z = 10.0F;

        this.resetAll(this.leftArm);
        this.leftArm.x = 5.0F;
        this.leftArm.y = this.slim ? 2.5F : 2.0F;
        this.leftArm.z = 0.0F;

        this.resetAll(this.leftSleeve);
        this.leftSleeve.x = 5.0F;
        this.leftSleeve.y = this.slim ? 2.5F : 2.0F;
        this.leftSleeve.z = 0.0F;

        this.resetAll(this.leftLeg);
        this.leftLeg.x = 1.9F;
        this.leftLeg.y = 12.0F;
        this.leftLeg.z = 0.0F;

        this.resetAll(this.leftPants);
        this.leftPants.copyFrom(this.leftLeg);

        this.resetAll(this.rightLeg);
        this.rightLeg.x = -1.9F;
        this.rightLeg.y = 12.0F;
        this.rightLeg.z = 0.0F;

        this.resetAll(this.rightPants);
        this.rightPants.copyFrom(this.rightLeg);
    }

    /**
     * Resets the rotation angles and points to zero for the given model renderer
     *
     * @param part the model part to reset
     */
    private void resetAll(ModelPart part)
    {
        part.xRot = 0;
        part.yRot = 0;
        part.zRot = 0;
        part.x = 0;
        part.y = 0;
        part.z = 0;
    }

    private void resetVisibilities()
    {
        this.head.visible = true;
        this.body.visible = true;
        this.rightArm.visible = true;
        this.leftArm.visible = true;
        this.rightLeg.visible = true;
        this.leftLeg.visible = true;
    }
}
