package com.cosmic_jewelry.common.core.material.feature

import com.cosmic_jewelry.common.core.material.Material
import com.cosmic_jewelry.common.util.ClassRegister
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.tags.BlockTags
import net.minecraft.tags.TagKey
import net.minecraft.util.valueproviders.ConstantInt
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.DropExperienceBlock
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
import net.minecraft.world.level.levelgen.placement.PlacedFeature
import net.neoforged.neoforge.client.model.generators.BlockStateProvider
import net.neoforged.neoforge.registries.DeferredRegister

abstract class MaterialOre<M: Material<M>>(name: String, gemSymbol: String = "#")
    : MaterialBlock<M>(name, gemSymbol), DataGenFeature<BlockStateProvider, M, Block>
{
    abstract val miningTime: (Float) -> Float
    abstract val dropItem: MaterialItem<M>

    private val configuredFeatureMap = HashMap<() -> Block, ResourceKey<ConfiguredFeature<*, *>>>()
    val configuredFeatures by lazy { configuredFeatureMap.mapKeys { it.key() } }

    private val placementMap = HashMap<() -> Block, ResourceKey<PlacedFeature>>()
    val placements by lazy { placementMap.mapKeys { it.key() } }

    val materialConfiguredFeature     by lazy { users.map { it.value to configuredFeatures[it.key]!! } }
    val placementsToConfiguredFeature by
        lazy { placements.map { users[it.key]!! to (it.value to configuredFeatures[it.key]!!) } .toMap() }

    init { all += this }

    override fun registerPost(material: M, context: DeferredRegister<Block>, feature: () -> Block) {
        super.registerPost(material, context, feature)

        placementMap[feature] = ResourceKey.create(Registries.PLACED_FEATURE, createPath(material))
        configuredFeatureMap[feature] = ResourceKey.create(Registries.CONFIGURED_FEATURE, createPath(material))
    }

    @Suppress("UNCHECKED_CAST")
    fun getDropItemUnsafe(material: Any) = dropItem[material as M]

    companion object : ClassRegister<MaterialOre<*>>() {

        @JvmInline value class MiningLevel(val tag: TagKey<Block>) {
            companion object {
                val stoneLevel = MiningLevel(BlockTags.NEEDS_STONE_TOOL)
                val ironLevel = MiningLevel(BlockTags.NEEDS_IRON_TOOL)
                val diamondLevel = MiningLevel(BlockTags.NEEDS_DIAMOND_TOOL)
            }
        }
    }

    override val featureBuilder: (M) -> Block =
        { DropExperienceBlock(ConstantInt.of((0)), BlockBehaviour.Properties.of().strength(miningTime(it.mohs), (3f))
            .requiresCorrectToolForDrops()) }
}