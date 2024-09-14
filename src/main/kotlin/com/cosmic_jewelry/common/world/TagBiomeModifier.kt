package com.cosmic_jewelry.common.world

import com.cosmic_jewelry.common.registry.BiomeModifierSerializerRegistry.tagBiomeModifierSerializer
import net.minecraft.core.Holder
import net.minecraft.core.HolderSet
import net.minecraft.tags.TagKey
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.levelgen.GenerationStep.Decoration
import net.minecraft.world.level.levelgen.placement.PlacedFeature
import net.neoforged.neoforge.common.world.BiomeModifier
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo

data class TagBiomeModifier(val tag      : TagKey<Biome>,
                            val features : HolderSet<PlacedFeature>,
                            val step     : Decoration) : BiomeModifier
{
    override fun modify(biome   : Holder<Biome>,
                        phase   : BiomeModifier.Phase,
                        builder : ModifiableBiomeInfo.BiomeInfo.Builder)
    {
        if (!(phase == BiomeModifier.Phase.ADD && tag in biome.tags().toList())) return

        features.forEach { builder.generationSettings.addFeature(step, it) }
    }

    override fun codec() = tagBiomeModifierSerializer
}