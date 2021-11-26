package com.mrcrayfish.posture.client;

import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.world.entity.player.Player;

/**
 * Author: MrCrayfish
 */
public final class RenderingPlayerInfo
{
    private static RenderingPlayerInfo instance;

    public static RenderingPlayerInfo get()
    {
        if(instance == null)
        {
            instance = new RenderingPlayerInfo();
        }
        return instance;
    }

    private Player player;
    private PlayerModel<?> model;
    private float limbSwing;
    private float limbSwingAmount;
    private float ageInTicks;
    private float headYaw;
    private float headPitch;

    private RenderingPlayerInfo() {}

    public void updateInfo(Player player, PlayerModel<?> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        this.player = player;
        this.model = model;
        this.limbSwing = limbSwing;
        this.limbSwingAmount = limbSwingAmount;
        this.ageInTicks = ageInTicks;
        this.headYaw = netHeadYaw;
        this.headPitch = headPitch;
    }

    public void resetInfo()
    {
        this.player = null;
        this.model = null;
    }

    public boolean isRendering(AgeableListModel<?> model)
    {
        return this.player != null && this.model == model;
    }

    public Player getPlayer()
    {
        return this.player;
    }

    public PlayerModel<?> getModel()
    {
        return this.model;
    }

    public float getLimbSwing()
    {
        return this.limbSwing;
    }

    public float getLimbSwingAmount()
    {
        return this.limbSwingAmount;
    }

    public float getAgeInTicks()
    {
        return this.ageInTicks;
    }

    public float getHeadYaw()
    {
        return this.headYaw;
    }

    public float getHeadPitch()
    {
        return this.headPitch;
    }
}
