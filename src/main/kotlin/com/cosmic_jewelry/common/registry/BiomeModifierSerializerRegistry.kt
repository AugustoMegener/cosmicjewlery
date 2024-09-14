package com.cosmic_jewelry.common.registry

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.world.TagBiomeModifier
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey
import net.minecraft.world.level.levelgen.GenerationStep.Decoration
import net.minecraft.world.level.levelgen.placement.PlacedFeature
import net.neoforged.neoforge.common.world.BiomeModifier
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.NeoForgeRegistries.BIOME_MODIFIER_SERIALIZERS
import thedarkcolour.kotlinforforge.neoforge.forge.getValue

object BiomeModifierSerializerRegistry {
    val biomeModifierSerializers: DeferredRegister<MapCodec<out BiomeModifier>> =
        DeferredRegister.create(BIOME_MODIFIER_SERIALIZERS, ID)

    val tagBiomeModifierSerializer: MapCodec<TagBiomeModifier> by
        biomeModifierSerializers.register("tag_biome_modifier") { ->
            RecordCodecBuilder.mapCodec {
                it.group(
                    TagKey.codec(Registries.BIOME).fieldOf("biomes")  .forGetter(TagBiomeModifier::tag),
                    PlacedFeature.LIST_CODEC      .fieldOf("features").forGetter(TagBiomeModifier::features),
                    Decoration.CODEC              .fieldOf("step")    .forGetter(TagBiomeModifier::step)
                ).apply(it) { t, f, s-> TagBiomeModifier(t, f, s) }
            }
        }
}