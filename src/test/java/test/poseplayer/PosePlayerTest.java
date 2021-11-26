package test.poseplayer;

import com.mrcrayfish.posture.api.event.PlayerModelEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Author: MrCrayfish
 */
@Mod("pose_player_test")
@Mod.EventBusSubscriber(modid = "pose_player_test", value = Dist.CLIENT)
public class PosePlayerTest
{
    @SubscribeEvent
    public static void onPlayerPose(PlayerModelEvent.Pose.Post event)
    {
        event.getPlayerModel().rightArm.zRot = (float) Math.toRadians(140F);
        event.getPlayerModel().leftArm.zRot = (float) Math.toRadians(-140F);
    }
}
