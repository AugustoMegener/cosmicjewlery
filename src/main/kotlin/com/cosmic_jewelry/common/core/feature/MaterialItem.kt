package com.cosmic_jewelry.common.core.feature

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.core.material.Material
import com.cosmic_jewelry.common.core.util.ClassRegister
import com.cosmic_jewelry.common.core.util.UniversalTag
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation.parse
import net.minecraft.world.item.Item
import net.neoforged.neoforge.client.model.generators.ItemModelProvider

abstract class MaterialItem<M: Material<M>>(name: String, tags: List<UniversalTag> = listOf(), materialSymbol: String = "#") :
    RegistrableMaterialFeature<M, Item>(BuiltInRegistries.ITEM, name, tags, materialSymbol),
    DataGenFeature<ItemModelProvider, M, Item>
{
    init { all += this }

    override val dataGen: ItemModelProvider.(M, Item) -> Unit = { _, i -> basicItem(i) }

    override fun <T : M> getMaterialTags(material: T) = listOf(UniversalTag(parse("$ID:${material.id.path}_item")))

    override fun getFeature(material: M) = get(material)!!

    companion object : ClassRegister<MaterialItem<*>>()
}