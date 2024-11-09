package com.cosmic_jewelry.common.core.feature.gem

import com.cosmic_jewelry.common.core.feature.DataGenFeature
import com.cosmic_jewelry.common.core.feature.MaterialOre
import com.cosmic_jewelry.common.core.material.gem.GemType
import com.cosmic_jewelry.common.core.util.UniversalTag
import com.cosmic_jewelry.common.registry.ItemRegistry.rawGemItem
import com.cosmic_jewelry.common.world.TagBiomeModifier
import net.minecraft.core.HolderSet
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.levelgen.GenerationStep.Decoration.UNDERGROUND_ORES
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration
import net.neoforged.neoforge.client.model.generators.BlockStateProvider
import net.neoforged.neoforge.common.Tags.Biomes.IS_OVERWORLD
import net.neoforged.neoforge.common.world.BiomeModifier


class GemOre(name          : String,
             override val miningTime    : (Float) -> Float = { it },
             override val dataGen       : BlockStateProvider.(GemType, Block) -> Unit =
                              { _, b -> simpleBlockWithItem(b, cubeAll(b)) },
             val biomeMod      : GemOre.() -> BiomeModifier =
                              { TagBiomeModifier(IS_OVERWORLD, HolderSet.direct(*placedFeatures.values.toTypedArray()), UNDERGROUND_ORES)  },

             tags          : List<UniversalTag> = listOf(),
             gemSymbol     : String = "#")
    : MaterialOre<GemType>(name, tags, gemSymbol), DataGenFeature<BlockStateProvider, GemType, Block>
{
    override val dropItem = rawGemItem

    override val biomeModifier get() = biomeMod(this)

    override fun <T : GemType> getPlacements(material: T) = material.family.orePlacements

    override val item = GemItem(name, { BlockItem(this[it]!!, GemItem.defaultProperty(it)) }, { _, _ -> })

    override fun <T : GemType> getConfig(material: T): OreConfiguration = material.family.oreConfiguration(material)
}