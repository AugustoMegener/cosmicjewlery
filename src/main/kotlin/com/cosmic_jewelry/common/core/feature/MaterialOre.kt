package com.cosmic_jewelry.common.core.feature

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.core.material.Material
import com.cosmic_jewelry.common.core.util.ClassRegister
import com.cosmic_jewelry.common.core.util.UniversalTag
import net.minecraft.core.Holder
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.util.valueproviders.ConstantInt
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.DropExperienceBlock
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration
import net.minecraft.world.level.levelgen.placement.PlacedFeature
import net.minecraft.world.level.levelgen.placement.PlacementModifier
import net.neoforged.neoforge.client.model.generators.BlockStateProvider
import net.neoforged.neoforge.common.world.BiomeModifier
import net.neoforged.neoforge.registries.DeferredRegister
import net.minecraft.resources.ResourceLocation.parse as loc

abstract class MaterialOre<M: Material<M>>(name: String, tags: List<UniversalTag> = listOf(), gemSymbol: String = "#")
    : MaterialBlock<M>(name, tags, gemSymbol), DataGenFeature<BlockStateProvider, M, Block>
{
    abstract val miningTime: (Float) -> Float

    abstract val dropItem: MaterialItem<M>
    abstract val biomeModifier: BiomeModifier

    private val configuredFeatureMap = HashMap<() -> Block, ResourceKey<ConfiguredFeature<*, *>>>()
    val configuredFeatures by lazy { configuredFeatureMap.mapKeys { it.key() } }

    private val placedFeaturesKeysMap = HashMap<() -> Block, ResourceKey<PlacedFeature>>()
    val placedFeatureKeys by lazy { placedFeaturesKeysMap.mapKeys { it.key() } }

    val placedFeatures = HashMap<M, Holder<PlacedFeature>>()

    @Suppress("UNCHECKED_CAST")
    fun addPlacedFeatureUnsafe(material: Any, feature: Holder<PlacedFeature>) { placedFeatures[material as M] = feature }

    val materialConfiguredFeature     by lazy { users.map { it.value to configuredFeatures[it.key]!! } }
    val placementsToConfiguredFeature by
        lazy { placedFeatureKeys.map { users[it.key]!! to (it.value to configuredFeatures[it.key]!!) } .toMap() }

    init {
        all += this
    }

    override fun registerPost(material: M, context: DeferredRegister<Block>, feature: () -> Block) {
        super.registerPost(material, context, feature)

        placedFeaturesKeysMap[feature] = ResourceKey.create(Registries.PLACED_FEATURE, createPath(material))
        configuredFeatureMap[feature] = ResourceKey.create(Registries.CONFIGURED_FEATURE, createPath(material))
    }

    override fun <T: M> getDropItem(material: T) = dropItem[material]

    abstract fun <T: M> getConfig(material: T): OreConfiguration
    abstract fun <T: M> getPlacements(material: T): List<PlacementModifier>


    @Suppress("UNCHECKED_CAST") fun getConfigUnsafe(material: Material<*>) = getConfig(material as M)
    @Suppress("UNCHECKED_CAST") fun getPlacementsUnsafe(material: Material<*>) = getPlacements(material as M)

    override fun <T : M> getMaterialTags(material: T) = listOf(UniversalTag(loc("$ID:${material.id.path}_ore")))

    companion object : ClassRegister<MaterialOre<*>>() {

        @JvmInline
        value class MiningLevel(val tag: UniversalTag) {
            companion object {
                val stoneLevel   = MiningLevel(UniversalTag(loc("needs_stone_tool"  )))
                val ironLevel    = MiningLevel(UniversalTag(loc("needs_iron_tool"   )))
                val diamondLevel = MiningLevel(UniversalTag(loc("needs_diamond_tool")))
            }
        }
    }

    override val featureBuilder: (M) -> Block =
        { DropExperienceBlock(ConstantInt.of((0)), BlockBehaviour.Properties.of().strength(miningTime(it.mohs), (3f))
            .requiresCorrectToolForDrops()) }
}