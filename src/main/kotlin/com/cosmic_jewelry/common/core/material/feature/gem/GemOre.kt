package com.cosmic_jewelry.common.core.material.feature.gem

import com.cosmic_jewelry.common.core.material.feature.DataGenFeature
import com.cosmic_jewelry.common.core.material.gem.GemType
import com.cosmic_jewelry.common.util.ClassRegister
import net.minecraft.core.registries.Registries.CONFIGURED_FEATURE
import net.minecraft.core.registries.Registries.PLACED_FEATURE
import net.minecraft.resources.ResourceKey
import net.minecraft.tags.BlockTags
import net.minecraft.tags.TagKey
import net.minecraft.util.valueproviders.ConstantInt
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.DropExperienceBlock
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
import net.minecraft.world.level.levelgen.placement.PlacedFeature
import net.neoforged.neoforge.client.model.generators.BlockStateProvider
import net.neoforged.neoforge.registries.DeferredRegister

class GemOre(name: String,
             gemSymbol: String = "#",
             miningTime: (Float) -> Float,
             override val dataGen: BlockStateProvider.(GemType, Block) -> Unit, )
: GemBlock(name, gemSymbol, { DropExperienceBlock(ConstantInt.of((0)),
                                                  Properties.of().strength(miningTime(it.mosh), (3f))
                                                                 .requiresCorrectToolForDrops()) }),
    DataGenFeature<BlockStateProvider, GemType, Block>
{
    private val configuredFeatureMap = HashMap<() -> Block, ResourceKey<ConfiguredFeature<*, *>>>()
    val configuredFeatures by lazy { configuredFeatureMap.mapKeys { it.key() } }

    private val placementMap = HashMap<() -> Block, ResourceKey<PlacedFeature>>()
    val placements by lazy { placementMap.mapKeys { it.key() } }

    val materialConfiguredFeature     by lazy { users.map { it.value to configuredFeatures[it.key]!! } }
    val placementsToConfiguredFeature by lazy {
        placements.map { users[it.key]!! to (it.value to configuredFeatures[it.key]!!) } .toMap()
    }

    constructor(name: String, miningTime: (Float) -> Float, dataGen: BlockStateProvider.(GemType, Block) -> Unit) :
            this(name, "#", miningTime, dataGen)

    constructor(name: String, miningTime: (Float) -> Float) :
            this(name, "#", miningTime, { _, b -> simpleBlockWithItem(b, cubeAll(b)) })

    constructor(name: String, dataGen: BlockStateProvider.(GemType, Block) -> Unit) :
            this(name, { (it + 3f) / 2 }, dataGen)

    constructor(name: String) :
            this(name, { _, b -> simpleBlockWithItem(b, cubeAll(b)) })


    init { all += this; Blocks.IRON_ORE }

    override fun registerPost(material: GemType, context: DeferredRegister<Block>, feature: () -> Block) {
        super.registerPost(material, context, feature)

        placementMap[feature] = ResourceKey.create(PLACED_FEATURE, createPath(material))
        configuredFeatureMap[feature] = ResourceKey.create(CONFIGURED_FEATURE, createPath(material))
    }

    companion object : ClassRegister<GemOre>() {

        @JvmInline value class MiningLevel(val tag: TagKey<Block>) {
            companion object {
                val stoneLevel = MiningLevel(BlockTags.NEEDS_STONE_TOOL)
                val ironLevel = MiningLevel(BlockTags.NEEDS_IRON_TOOL)
                val diamondLevel = MiningLevel(BlockTags.NEEDS_DIAMOND_TOOL)
            }
        }
    }
}