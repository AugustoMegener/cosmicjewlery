package com.cosmic_jewelry.common.core.feature

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.core.material.Material
import com.cosmic_jewelry.common.core.util.ClassRegister
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation.parse
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.neoforged.neoforge.client.model.generators.ItemModelProvider

abstract class MaterialItem<M: Material<M>>(name: String, tags: List<TagKey<Item>> = listOf(), materialSymbol: String = "#") :
    RegistrableMaterialFeature<M, Item>(BuiltInRegistries.ITEM, name, tags, materialSymbol),
    DataGenFeature<ItemModelProvider, M, Item>
{
    init { all += this }

    override val dataGen: ItemModelProvider.(M, Item) -> Unit = { _, i -> basicItem(i) }

    override val featureGeneralTag : TagKey<Item> =
        TagKey.create(BuiltInRegistries.ITEM.key(), parse("$ID:item"))

    override fun getFeature(material: M) = get(material)!!

    companion object : ClassRegister<MaterialItem<*>>()
}