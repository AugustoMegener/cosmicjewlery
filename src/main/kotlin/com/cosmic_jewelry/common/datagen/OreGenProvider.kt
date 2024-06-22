package com.cosmic_jewelry.common.datagen

import com.cosmic_jewelry.common.core.material.feature.gem.GemOre
import net.minecraft.core.registries.Registries
import net.minecraft.core.registries.Registries.PLACED_FEATURE
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.data.worldgen.placement.PlacementUtils
import net.minecraft.world.level.levelgen.placement.*

object OreGenProvider {

    val registry = PLACED_FEATURE

    fun bootstrap(ctx: BootstrapContext<PlacedFeature>) {
        GemOre.register.flatMap { it.placementsToConfiguredFeature.entries } .forEach { (g, v) ->
            PlacementUtils.register(
                ctx, v.first, ctx.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(v.second), g.family.orePlacements)
        }
    }


    fun orePlacement(pCountPlacement: PlacementModifier, pHeightRange: PlacementModifier) =
        listOf(pCountPlacement, InSquarePlacement.spread(), pHeightRange, BiomeFilter.biome())

    fun commonOrePlacement(pCount: Int, pHeightRange: PlacementModifier) =
        orePlacement(CountPlacement.of(pCount), pHeightRange)

    fun rareOrePlacement(pChance: Int, pHeightRange: PlacementModifier) =
        orePlacement(RarityFilter.onAverageOnceEvery(pChance), pHeightRange)
}