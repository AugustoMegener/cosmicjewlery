package com.cosmic_jewelry.common.core.material.feature

import com.cosmic_jewelry.common.core.material.Material
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.neoforged.neoforge.client.model.generators.ItemModelProvider

abstract class MaterialItem<T : Material<T>>(location: ResourceLocation, featureSymbol: String) :
    RegistryMaterialFeature<T, Item>(location, featureSymbol),
    DataGenFeature<ItemModelProvider, T, Item>
{
    override fun getFeature(material: T) = get(material)!!
}