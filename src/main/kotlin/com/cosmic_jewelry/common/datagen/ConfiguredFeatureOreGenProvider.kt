package com.cosmic_jewelry.common.datagen

import com.cosmic_jewelry.common.core.material.feature.gem.GemOre
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.data.worldgen.features.FeatureUtils
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration

object ConfiguredFeatureOreGenProvider {
    fun bootstrap(ctx: BootstrapContext<ConfiguredFeature<*, *>>) {
        GemOre.register.forEach { ore ->
            ore.materialConfiguredFeature.forEach { (a, b) ->
                FeatureUtils.register<OreConfiguration, Feature<OreConfiguration>>(ctx, b, Feature.ORE,
                                                                                   a.family.oreConfiguration(a))
            }
        }
    }
}