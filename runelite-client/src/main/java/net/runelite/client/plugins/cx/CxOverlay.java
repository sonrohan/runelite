package net.runelite.client.plugins.cx;


import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.SpriteID;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.ComponentConstants;
import net.runelite.client.ui.overlay.components.ImageComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;

import javax.inject.Inject;
import java.awt.*;
import java.awt.image.BufferedImage;

@Slf4j
public class CxOverlay extends Overlay
{
    private static final Color NOT_ACTIVATED_BACKGROUND_COLOR = new Color(150, 0, 0, 150);

    private final Client client;
    private final CxPlugin plugin;
    private final SpriteManager spriteManager;
    private final PanelComponent imagePanelComponent = new PanelComponent();

    @Inject
    private CxOverlay(Client client, CxPlugin plugin, SpriteManager spriteManager)
    {
        setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
        setPriority(OverlayPriority.HIGH);
        this.client = client;
        this.plugin = plugin;
        this.spriteManager = spriteManager;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        final CxAttack attack = plugin.getAttack();

        if (attack == null)
        {
            return null;
        }

        final BufferedImage prayerImage = getPrayerImage(attack);

        imagePanelComponent.getChildren().clear();
        imagePanelComponent.getChildren().add(new ImageComponent(prayerImage));
        imagePanelComponent.setBackgroundColor(client.isPrayerActive(attack.getPrayer())
                ? ComponentConstants.STANDARD_BACKGROUND_COLOR
                : NOT_ACTIVATED_BACKGROUND_COLOR);

        return imagePanelComponent.render(graphics);
    }

    private BufferedImage getPrayerImage(CxAttack attack)
    {
        final int prayerSpriteID = attack == CxAttack.MAGIC ? SpriteID.PRAYER_PROTECT_FROM_MAGIC : SpriteID.PRAYER_PROTECT_FROM_MISSILES;

        if (prayerSpriteID == SpriteID.PRAYER_PROTECT_FROM_MAGIC) {
            log.error("MAGIC");
        } else {
            log.error("RANGE");
        }
        return spriteManager.getSprite(prayerSpriteID, 0);
    }
}