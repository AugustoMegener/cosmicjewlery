package com.cosmic_jewelry.common.datagen

import com.cosmic_jewelry.common.core.feature.MaterialOre
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.resources.ResourceKey
import net.neoforged.neoforge.common.world.BiomeModifier
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys.BIOME_MODIFIERS

object BiomeModifierProvider {

    fun bootstrap(ctx: BootstrapContext<BiomeModifier>) {
        MaterialOre.register.forEach { o ->
            ctx.register(ResourceKey.create(BIOME_MODIFIERS, o.genericPath), o.biomeModifier)
        }
    }
}