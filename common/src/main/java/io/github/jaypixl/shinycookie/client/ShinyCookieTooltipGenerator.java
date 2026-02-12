package io.github.jaypixl.shinycookie.client;

import com.cobblemon.mod.common.client.tooltips.TooltipGenerator;
import io.github.jaypixl.shinycookie.ShinyCookieMod;
import io.github.jaypixl.shinycookie.config.ShinyCookieConfigLoader;
import io.github.jaypixl.shinycookie.item.components.ModComponents;
import io.github.jaypixl.shinycookie.item.crafting.CookieSeasoningEntry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ShinyCookieTooltipGenerator extends TooltipGenerator {
    private ShinyCookieTooltipGenerator() {}

    public static final ShinyCookieTooltipGenerator INSTANCE = new ShinyCookieTooltipGenerator();

    @Override
    public @Nullable List<Component> generateTooltip(@NotNull ItemStack stack, @NotNull List<Component> lines) {
        if (
                !BuiltInRegistries.ITEM.getKey(stack.getItem()).equals(ResourceLocation.fromNamespaceAndPath(ShinyCookieMod.MOD_ID, "star_cookie")) ||
                        stack.get(ModComponents.COOKIE_POWER) == null
        ) {
            return null;
        }
        ArrayList<Component> returnList = new ArrayList<>();
        double shinyChance = Objects.requireNonNull(stack.get(ModComponents.COOKIE_POWER)).getShinyBoost() * 100;

        returnList.add(Component.literal("Restores " + new DecimalFormat("0.#").format(ShinyCookieConfigLoader.CONFIG.starCookieHealAmount) + " HP to a Pokemon and may turn it shiny!").withStyle(ChatFormatting.GRAY));

        returnList.add(Component.literal("Shiny Chance: " + new DecimalFormat("0.#").format(shinyChance) + "%").withStyle(ChatFormatting.AQUA));

        return List.copyOf(returnList);
    }

}
