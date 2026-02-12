package io.github.jaypixl.shinycookie.item;

import net.minecraft.world.item.Item;

public final class ModItems {
    public static final Item STARDUST = new Item(new Item.Properties());
    public static final StarCookieItem STAR_COOKIE = new StarCookieItem(new Item.Properties().stacksTo(16));
    public static final BurntStarCookieItem BURNT_STAR_COOKIE = new BurntStarCookieItem(new Item.Properties().stacksTo(16));
}
