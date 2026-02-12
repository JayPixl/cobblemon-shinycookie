package io.github.jaypixl.shinycookie.item.crafting;

import com.cobblemon.mod.common.item.crafting.SeasoningProcessor;
import io.github.jaypixl.shinycookie.config.ShinyCookieConfigLoader;
import io.github.jaypixl.shinycookie.item.components.CookiePowerComponent;
import io.github.jaypixl.shinycookie.item.components.ModComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class CookiePowerProcessor implements SeasoningProcessor {

    public static final CookiePowerProcessor INSTANCE = new CookiePowerProcessor();

    private CookiePowerProcessor() {}

    @Override
    public @NotNull String getType() {
        return "shiny_cookie";
    }

    @Override
    public void apply(@NotNull ItemStack result, @NotNull List<ItemStack> seasoningItems) {
        double totalPower = 0.0;

        //ShinyCookieMod.LOGGER.info(result.getItem().getName(result).getString());

        for (ItemStack stack : seasoningItems) {
            ResourceLocation id =
                    BuiltInRegistries.ITEM.getKey(stack.getItem());

            CookieSeasoningEntry entry =
                    ShinyCookieConfigLoader.CONFIG.seasonings.get(id.toString());

            if (entry != null) {
                totalPower += entry.shinyBoost;
            }
        }

        if (totalPower == 0.0) { totalPower = ShinyCookieConfigLoader.CONFIG.defaultCookieShinyChance; }

        totalPower = Math.min(100.0, Math.max(totalPower, 0.0));

        result.set(ModComponents.COOKIE_POWER, new CookiePowerComponent(totalPower));
    }


    @Override
    public boolean consumesItem(@NotNull ItemStack itemStack) {
        return ShinyCookieConfigLoader.CONFIG.seasonings.get(BuiltInRegistries.ITEM.getKey(itemStack.getItem()).toString()) != null;
    }
}
