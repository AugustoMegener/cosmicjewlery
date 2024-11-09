package com.cosmic_jewelry.common.core.feature

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.core.material.Material
import com.cosmic_jewelry.common.core.util.ClassRegister
import com.cosmic_jewelry.common.core.util.UniversalTag
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.client.model.generators.BlockStateProvider
import net.minecraft.resources.ResourceLocation.parse as loc

abstract class MaterialBlock<M: Material<M>>(name: String, tags: List<UniversalTag> = listOf(), materialSymbol: String = "#")
    : RegistrableMaterialFeature<M, Block>(BuiltInRegistries.BLOCK, name, tags, materialSymbol),
      DataGenFeature<BlockStateProvider, M, Block>
{
    abstract val item: MaterialItem<M>

    override fun <T : M> getMaterialTags(material: T) = listOf(UniversalTag(loc("$ID:${material.id.path}_block")))

    init { all += this }

    override fun getFeature(material: M) = get(material)!!

    companion object: ClassRegister<MaterialBlock<*>>();

    open fun <T : M> getDropItem(material: T): Item? = null

    @Suppress("UNCHECKED_CAST")
    fun getDropItem(material: Any) = getDropItem(material as M)
}