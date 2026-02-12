package io.github.jaypixl.shinycookie.item;

import com.cobblemon.mod.common.CobblemonSounds;
import com.cobblemon.mod.common.api.item.PokemonSelectingItem;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.item.battle.BagItem;
import com.cobblemon.mod.common.pokemon.Pokemon;
import io.github.jaypixl.shinycookie.config.ShinyCookieConfigLoader;
import io.github.jaypixl.shinycookie.item.components.CookiePowerComponent;
import io.github.jaypixl.shinycookie.item.components.ModComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class StarCookieItem extends Item implements PokemonSelectingItem {

    public StarCookieItem(@NotNull Item.Properties settings) { super(settings); }

    @Override
    public @Nullable BagItem getBagItem() { return null; }

    @Override
    public boolean canUseOnBattlePokemon(@NotNull ItemStack stack, @NotNull BattlePokemon battlePokemon) {
        return false;
    }

    @Override
    public @Nullable InteractionResultHolder<ItemStack> applyToPokemon(@NotNull ServerPlayer serverPlayer, @NotNull ItemStack itemStack, @NotNull Pokemon pokemon) {
        pokemon.setCurrentHealth(pokemon.getCurrentHealth() + ShinyCookieConfigLoader.CONFIG.starCookieHealAmount);

        CookiePowerComponent comp = itemStack.get(ModComponents.COOKIE_POWER);
        //ShinyCookieMod.LOGGER.info(String.valueOf(comp.getShinyBoost()));
        double chance = ShinyCookieConfigLoader.CONFIG.defaultCookieShinyChance;

        if (comp != null) {
            chance = comp.getShinyBoost();
        }

        boolean success = Math.random() < chance;

        if (success) {
            pokemon.setShiny(true);
        }

        if (!serverPlayer.isCreative()) { itemStack.shrink(1); }

        Objects.requireNonNull(pokemon.getEntity()).playSound(CobblemonSounds.MOCHI_USE);

        return InteractionResultHolder.success(itemStack);
    }

    @Override
    public boolean canUseOnPokemon(@NotNull ItemStack stack, @NotNull Pokemon pokemon) {
        return (!pokemon.isFull() && !pokemon.isFainted());
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (player instanceof ServerPlayer && level instanceof ServerLevel) {
            return use((ServerPlayer) player, player.getItemInHand(hand));
        }
        return super.use(level, player, hand);
    }

    @Override
    public boolean isFoil(ItemStack itemStack) {
        if (itemStack.get(ModComponents.COOKIE_POWER) == null) { return super.isFoil(itemStack); }
        return itemStack.get(ModComponents.COOKIE_POWER).getShinyBoost() > 0;
    }
}
