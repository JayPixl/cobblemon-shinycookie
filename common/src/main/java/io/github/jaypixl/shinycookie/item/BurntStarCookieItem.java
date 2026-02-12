package io.github.jaypixl.shinycookie.item;

import com.cobblemon.mod.common.CobblemonSounds;
import com.cobblemon.mod.common.api.item.PokemonSelectingItem;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.item.battle.BagItem;
import com.cobblemon.mod.common.pokemon.Pokemon;
import io.github.jaypixl.shinycookie.ShinyCookieMod;
import io.github.jaypixl.shinycookie.config.ShinyCookieConfigLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class BurntStarCookieItem extends Item implements PokemonSelectingItem {

    public BurntStarCookieItem(@NotNull Item.Properties settings) { super(settings); }

    @Override
    public boolean canUseOnBattlePokemon(@NotNull ItemStack stack, @NotNull BattlePokemon battlePokemon) { return false; }

    @Override
    public @Nullable BagItem getBagItem() { return null; }

    @Override
    public @Nullable InteractionResultHolder<ItemStack> applyToPokemon(@NotNull ServerPlayer serverPlayer, @NotNull ItemStack itemStack, @NotNull Pokemon pokemon) {
        if (!pokemon.isFull() && !pokemon.isFainted()) {
            pokemon.decrementFriendship(25, true);
            //ShinyCookieMod.LOGGER.info(String.valueOf(ShinyCookieConfigLoader.CONFIG.rewardStardustOnBurntCookie));
            if (ShinyCookieConfigLoader.CONFIG.rewardStardustOnBurntCookie && pokemon.getShiny()) {
                int min = ShinyCookieConfigLoader.CONFIG.burntCookieStardustMin;
                int max = ShinyCookieConfigLoader.CONFIG.burntCookieStardustMax;
                //ShinyCookieMod.LOGGER.info(String.valueOf(min));
                //ShinyCookieMod.LOGGER.info(String.valueOf(max));

                RandomSource random = serverPlayer.getRandom();
                int amount = 0;

                if (min == max) {
                    amount = min;
                } else {
                    amount = random.nextInt(min, max);
                }

                ItemStack stardust = new ItemStack(
                        BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("shinycookie", "stardust")),
                        amount
                );

                if (!serverPlayer.getInventory().add(stardust)) {
                    serverPlayer.drop(stardust, false);
                }
            }
            pokemon.setShiny(false);

            if (!serverPlayer.isCreative()) { itemStack.shrink(1); }

            Objects.requireNonNull(pokemon.getEntity()).playSound(CobblemonSounds.MOCHI_USE);

            return InteractionResultHolder.success(itemStack);
        }
        return InteractionResultHolder.pass(itemStack);
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
}
