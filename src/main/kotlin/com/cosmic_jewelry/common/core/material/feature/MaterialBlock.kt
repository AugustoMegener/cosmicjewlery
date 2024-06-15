package com.cosmic_jewelry.common.core.material.feature

import com.cosmic_jewelry.common.core.material.Material
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.BlockTags
import net.minecraft.tags.TagKey
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.neoforged.neoforge.client.model.generators.BlockStateProvider

abstract class MaterialBlock<T : Material<T>> private constructor(builder: MaterialBlockBuilder<T>) :
    RegistryMaterialFeature<T, Block>(builder.location, builder.materialSymbol),
    DataGenFeature<BlockStateProvider, T, Block>,
    TagableFeature<T, Block>
{
    constructor(location: ResourceLocation,
                materialSymbol: String,
                builder: MaterialBlockBuilder<T>.() -> MaterialBlockBuilder<T>) :
            this(builder(MaterialBlockBuilder(
                location,
                materialSymbol,
                { Block(BlockBehaviour.Properties.of()) },
                { _, b -> simpleBlockWithItem(b, cubeAll(b)) })
            ))

    constructor(location: ResourceLocation, builder: MaterialBlockBuilder<T>.() -> MaterialBlockBuilder<T>) :
            this(location, "#", builder)


    open class MaterialBlockBuilder<T : Material<T>>
    internal constructor(        val location:       ResourceLocation,
                                 val materialSymbol: String,
                         private val defaultBlock:   (T) -> Block,
                         private val defaultModel:   BlockStateProvider.(T, Block) -> Unit)
    {
        private var blockValue: ((T) -> Block)? = null
        internal val block: (T) -> Block get() = blockValue ?: defaultBlock

        val tags = arrayListOf<TagKey<Block>>()
        protected var tools: Array<out HarvestTool> = arrayOf(); private set
        protected var level: HarvestLevel? = null; private set

        private var modelValue: (BlockStateProvider.(T, Block) -> Unit)? = null
        internal val model get() = modelValue ?: defaultModel


        fun harvestTools(vararg values: HarvestTool) { tags += values.toList().map { it.tag } }
        fun harvestLevel(value: HarvestLevel) { tags += value.tag }
        fun tags(vararg values: TagKey<Block>) { tags += values.toList() }

        fun model(value: BlockStateProvider.(T, Block) -> Unit) { modelValue = value }
        fun build(value: (T) -> Block) = this.also { blockValue = value }
    }

    override val featureRegistry = BuiltInRegistries.BLOCK.key()

    override val tags = builder.tags.toTypedArray()

    abstract val item: MaterialItem<T>

    override val dataGen = builder.model

    val blockBuilder = builder.block

    override fun getFeatureBuilder(material: T) = blockBuilder(material)

    override fun getFeature(material: T) = get(material)!!

    companion object {
        data class HarvestLevel(val tag: TagKey<Block>) {
            companion object {
                val stoneLevel   = HarvestLevel(BlockTags.NEEDS_STONE_TOOL)
                val ironLevel    = HarvestLevel(BlockTags.NEEDS_IRON_TOOL)
                val diamondLevel = HarvestLevel(BlockTags.NEEDS_DIAMOND_TOOL)
            }
        }

        data class HarvestTool(val tag: TagKey<Block>) {
            companion object {
                val pickaxeTool = HarvestTool(BlockTags.MINEABLE_WITH_PICKAXE)
                val axeTool     = HarvestTool(BlockTags.MINEABLE_WITH_AXE)
                val shovelTool  = HarvestTool(BlockTags.MINEABLE_WITH_SHOVEL)
                val hoeTool     = HarvestTool(BlockTags.MINEABLE_WITH_HOE)
                val swordTool     = HarvestTool(BlockTags.SWORD_EFFICIENT)
            }
        }
    }


}