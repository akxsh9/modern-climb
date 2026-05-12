package com.akxsh.modernclimb;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod(
        modid = "modernclimb",
        name = "Modern Climb",
        version = "1.0"
)
public class ModernClimb {

    private final Minecraft mc = Minecraft.getMinecraft();

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {

        if (mc.thePlayer == null)
            return;

        EntityPlayerSP player = mc.thePlayer;

        if (player.isOnLadder()) {

            // Keep vanilla horizontal ladder behavior
            player.isCollidedHorizontally = true;

            // Hold SPACE -> climb automatically
            if (mc.gameSettings.keyBindJump.isKeyDown()) {

                player.motionY = 0.2D;
            }

            // Hold SHIFT -> stay in place
            else if (mc.gameSettings.keyBindSneak.isKeyDown()) {

                player.motionY = 0.0D;
            }

            // No input -> vanilla-like slide down
            else {

                // Vanilla-ish ladder descent speed
                if (player.motionY < -0.15D) {
                    player.motionY = -0.15D;
                }
            }

            // Prevent fall damage
            player.fallDistance = 0;
        }
    }
}