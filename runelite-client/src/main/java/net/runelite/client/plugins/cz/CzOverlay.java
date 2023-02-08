package net.runelite.client.plugins.cz;


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
public class CzOverlay extends Overlay
{
    private static final Color NOT_ACTIVATED_BACKGROUND_COLOR = new Color(150, 0, 0, 150);

    private final Client client;
    private final CzPlugin plugin;
    private final SpriteManager spriteManager;
    private final PanelComponent imagePanelComponent = new PanelComponent();

    @Inject
    private CzOverlay(Client client, CzPlugin plugin, SpriteManager spriteManager)
    {
        setPosition(OverlayPosition.BOTTOM_RIGHT);
        setPriority(OverlayPriority.HIGH);
        this.client = client;
        this.plugin = plugin;
        this.spriteManager = spriteManager;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        final CzAttack attack = plugin.getAttack();

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

    private BufferedImage getPrayerImage(CzAttack attack)
    {
        final int prayerSpriteID = SpriteID.PRAYER_PROTECT_FROM_MAGIC;

        log.error("PRAY=" + SpriteID.PRAYER_PROTECT_FROM_MAGIC);
        return spriteManager.getSprite(prayerSpriteID, 0);
    }
}