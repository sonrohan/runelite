package net.runelite.client.plugins.cz;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.NPC;
import net.runelite.api.NpcID;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.annotation.Nullable;
import javax.inject.Inject;

@PluginDescriptor(
        name = "ROHAN DEBUG - CZ",
        description = "BAT Testing",
        tags = {"testing123", "overlay"}
)
@Slf4j
public class CzPlugin extends Plugin
{
    @Inject
    private OverlayManager overlayManager;

    @Inject
    private CzOverlay overlay;

    @Getter(AccessLevel.PACKAGE)
    @Nullable
    private CzAttack attack;

    private NPC bat;

    @Override
    protected void startUp() throws Exception
    {
        log.error("ROHAN DEBUG BAT PLUGIN STARTED!");
        overlayManager.add(overlay);
    }

    @Override
    protected void shutDown() throws Exception
    {
        overlayManager.remove(overlay);
        bat = null;
        attack = null;
    }

    @Subscribe
    public void onNpcSpawned(final NpcSpawned event)
    {
        final int id = event.getNpc().getId();

        if (id == NpcID.TZKIH || id == NpcID.TZKIH_2190 || id == NpcID.TZKIH_3116 || id == NpcID.TZKIH_3117)
        {
            log.error("ROHAN DEBUG BAT DETECTED!");
            bat = event.getNpc();
        }
    }

    @Subscribe
    public void onNpcDespawned(final NpcDespawned event)
    {
        if (bat == event.getNpc())
        {
            bat = null;
            attack = null;
        }
    }

    @Subscribe
    public void onAnimationChanged(final AnimationChanged event)
    {
        if (event.getActor() != bat)
        {
            return;
        }

        log.error("ROHAN DEBUG BAT onAnimationChanged");

        attack = CzAttack.MAGIC;

//        if (bat.getAnimation() == CzAttack.MAGIC.getAnimation())
//        {
//            attack = CzAttack.MAGIC;
//        }
//        else if (bat.getAnimation() == CzAttack.RANGE.getAnimation())
//        {
//            attack = CzAttack.RANGE;
//        }
    }
}
