package com.cosmic_jewelry.common.core.material.feature

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.core.material.Material
import com.cosmic_jewelry.common.util.ClassRegister
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.client.model.generators.BlockStateProvider
import net.minecraft.resources.ResourceLocation.parse as loc

abstract class MaterialBlock<M: Material<M>>(name: String, tags: List<TagKey<Block>> = listOf(), materialSymbol: String = "#")
    : RegistrableMaterialFeature<M, Block>(BuiltInRegistries.BLOCK, name, tags, materialSymbol),
      DataGenFeature<BlockStateProvider, M, Block>
{
    abstract val item: MaterialItem<M>

    override val featureGeneralTag = TagKey.create(BuiltInRegistries.BLOCK.key(), loc("$ID:block"))

    init { all += this }

    override fun getFeature(material: M) = get(material)!!

    companion object: ClassRegister<MaterialBlock<*>>();

    open fun <T : M> getDropItem(material: T): Item? = null

    @Suppress("UNCHECKED_CAST")
    fun getDropItem(material: Any) = getDropItem(material as M)
}