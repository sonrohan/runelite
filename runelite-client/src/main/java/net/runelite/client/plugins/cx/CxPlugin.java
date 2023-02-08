package net.runelite.client.plugins.cx;

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
        name = "ROHAN DEBUG - CX",
        description = "CX Testing",
        tags = {"testing123", "overlay"}
)
@Slf4j
public class CxPlugin extends Plugin
{
    @Inject
    private OverlayManager overlayManager;

    @Inject
    private CxOverlay overlay;

    @Getter(AccessLevel.PACKAGE)
    @Nullable
    private CxAttack attack;

    private NPC jad;

    @Override
    protected void startUp() throws Exception
    {
        log.error("ROHAN DEBUG PLUGIN STARTED!");
        overlayManager.add(overlay);
    }

    @Override
    protected void shutDown() throws Exception
    {
        overlayManager.remove(overlay);
        jad = null;
        attack = null;
    }

    @Subscribe
    public void onNpcSpawned(final NpcSpawned event)
    {
        final int id = event.getNpc().getId();

        if (id == NpcID.TZTOKJAD || id == NpcID.TZTOKJAD_6506)
        {
            jad = event.getNpc();
        }
    }

    @Subscribe
    public void onNpcDespawned(final NpcDespawned event)
    {
        if (jad == event.getNpc())
        {
            jad = null;
            attack = null;
        }
    }

    @Subscribe
    public void onAnimationChanged(final AnimationChanged event)
    {
        if (event.getActor() != jad)
        {
            return;
        }

        log.error("Jad detected!");

        if (jad.getAnimation() == CxAttack.MAGIC.getAnimation())
        {
            attack = CxAttack.MAGIC;
        }
        else if (jad.getAnimation() == CxAttack.RANGE.getAnimation())
        {
            attack = CxAttack.RANGE;
        }
    }
}
