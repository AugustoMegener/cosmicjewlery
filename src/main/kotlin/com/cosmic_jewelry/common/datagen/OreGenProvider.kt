package com.cosmic_jewelry.common.datagen

import com.cosmic_jewelry.common.core.feature.MaterialOre
import net.minecraft.core.Registry
import net.minecraft.core.registries.Registries.CONFIGURED_FEATURE
import net.minecraft.core.registries.Registries.PLACED_FEATURE
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.data.worldgen.placement.PlacementUtils
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.levelgen.placement.*

object OreGenProvider {

    val registry: ResourceKey<Registry<PlacedFeature>> = PLACED_FEATURE

    fun bootstrap(ctx: BootstrapContext<PlacedFeature>) {
        MaterialOre.register.forEach { o -> o.placementsToConfiguredFeature.forEach { (g, v) ->
            PlacementUtils.register(
                ctx, v.first, ctx.lookup(CONFIGURED_FEATURE).getOrThrow(v.second).delegate, o.getPlacementsUnsafe(g))
            o.addPlacedFeatureUnsafe(g, ctx.lookup(PLACED_FEATURE).getOrThrow(v.first))
        } }

    }


    fun orePlacement(pCountPlacement: PlacementModifier, pHeightRange: PlacementModifier) =
        listOf(pCountPlacement, InSquarePlacement.spread(), pHeightRange, BiomeFilter.biome())

    fun commonOrePlacement(pCount: Int, pHeightRange: PlacementModifier) =
        orePlacement(CountPlacement.of(pCount), pHeightRange)

    fun rareOrePlacement(pChance: Int, pHeightRange: PlacementModifier) =
        orePlacement(RarityFilter.onAverageOnceEvery(pChance), pHeightRange)
}