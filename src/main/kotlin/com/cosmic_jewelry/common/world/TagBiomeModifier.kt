package com.cosmic_jewelry.common.world

import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.Holder
import net.minecraft.core.HolderSet
import net.minecraft.core.registries.Registries
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
        if (phase == BiomeModifier.Phase.ADD && tag in biome.tags().toList()) return

        features.forEach { builder.generationSettings.addFeature(step, it) }
    }

    override fun codec() = codec

    companion object {

        val codec: MapCodec<TagBiomeModifier> = RecordCodecBuilder.mapCodec {
            it.group(TagKey.codec(Registries.BIOME).fieldOf("biomes")   .forGetter(TagBiomeModifier::tag),
                     PlacedFeature.LIST_CODEC      .fieldOf("features") .forGetter(TagBiomeModifier::features),
                     Decoration.CODEC               .fieldOf("step")    .forGetter(TagBiomeModifier::step)
            ).apply(it) { t, f, s-> TagBiomeModifier(t, f, s) } }
    }
}