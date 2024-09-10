package com.cosmic_jewelry.common.core.material.feature.gem

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.core.material.feature.DataGenFeature
import com.cosmic_jewelry.common.core.material.feature.MaterialOre
import com.cosmic_jewelry.common.core.material.gem.GemType
import com.cosmic_jewelry.common.registry.ItemRegistry.rawGemItem
import com.cosmic_jewelry.common.world.TagBiomeModifier
import net.minecraft.core.HolderSet
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.tags.TagKey
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.levelgen.GenerationStep.Decoration.UNDERGROUND_ORES
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration
import net.neoforged.neoforge.client.model.generators.BlockStateProvider
import net.neoforged.neoforge.common.Tags.Biomes.IS_OVERWORLD
import net.neoforged.neoforge.common.world.BiomeModifier
import net.minecraft.resources.ResourceLocation.parse as loc


class GemOre(             name          : String,
             override val miningTime    : (Float) -> Float = { it },
             override val dataGen       : BlockStateProvider.(GemType, Block) -> Unit = { _, _ -> },
                          biomeMod      : GemOre.() -> BiomeModifier =
                              { TagBiomeModifier(IS_OVERWORLD, HolderSet.direct(*placedFeatures.values.toTypedArray()), UNDERGROUND_ORES)  },

                          tags          : List<TagKey<Block>> = listOf(),
                          gemSymbol     : String = "#")
    : MaterialOre<GemType>(name, tags, gemSymbol), DataGenFeature<BlockStateProvider, GemType, Block>
{
    override val dropItem = rawGemItem

    override val biomeModifier = biomeMod(this)

    override fun <T : GemType> getPlacements(material: T) = material.family.orePlacements

    override val item = GemItem(name, { BlockItem(this[it]!!, GemItem.defaultProperty(it)) }, { _, _ -> })

    override fun <T : GemType> getConfig(material: T): OreConfiguration = material.family.oreConfiguration(material)

    override val featureGeneralTag = TagKey.create(BuiltInRegistries.BLOCK.key(), loc("$ID:ore"))
}