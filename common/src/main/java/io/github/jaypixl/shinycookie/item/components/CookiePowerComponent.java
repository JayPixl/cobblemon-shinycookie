package io.github.jaypixl.shinycookie.item.components;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public final class CookiePowerComponent {

    private final double shinyBoost;

    public CookiePowerComponent(double shinyMultiplier) {
        this.shinyBoost = shinyMultiplier;
    }

    public double getShinyBoost() {
        return shinyBoost;
    }

    public static final Codec<CookiePowerComponent> CODEC =
            Codec.DOUBLE.xmap(
                    CookiePowerComponent::new,
                    CookiePowerComponent::getShinyBoost
            );

    public static final StreamCodec<ByteBuf, CookiePowerComponent> PACKET_CODEC = ByteBufCodecs.fromCodec(CODEC);

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CookiePowerComponent other)) return false;
        return Double.compare(this.shinyBoost, other.shinyBoost) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(shinyBoost);
    }
}
