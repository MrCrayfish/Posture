package test.renderplayer;

import com.mrcrayfish.posture.api.event.PlayerModelEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Author: MrCrayfish
 */
@Mod("render_player_test")
@Mod.EventBusSubscriber(modid = "render_player_test", value = Dist.CLIENT)
public class RenderPlayerTest
{
    @SubscribeEvent
    public static void onPlayerPose(PlayerModelEvent.Render.Pre event)
    {
        event.getPoseStack().translate(0, -0.5, 0);
    }
}
