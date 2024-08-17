package com.cosmic_jewelry.common.core.material.feature

import com.cosmic_jewelry.common.core.material.Material
import com.cosmic_jewelry.common.util.ClassRegister
import net.minecraft.world.item.Item
import net.neoforged.neoforge.client.model.generators.ItemModelProvider

abstract class MaterialItem<M: Material<M>>(name: String, materialSymbol: String = "#") :
    RegistrableMaterialFeature<M, Item>(name, materialSymbol), DataGenFeature<ItemModelProvider, M, Item>
{
    init { all += this }

    companion object : ClassRegister<MaterialItem<*>>()
}