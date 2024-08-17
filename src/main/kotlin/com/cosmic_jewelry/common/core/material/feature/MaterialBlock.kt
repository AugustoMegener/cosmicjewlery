package com.cosmic_jewelry.common.core.material.feature

import com.cosmic_jewelry.common.core.material.Material
import com.cosmic_jewelry.common.util.ClassRegister
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.client.model.generators.BlockStateProvider

abstract class MaterialBlock<M: Material<M>>(name: String, materialSymbol: String = "#")
    : RegistrableMaterialFeature<M, Block>(name, materialSymbol), DataGenFeature<BlockStateProvider, M, Block>
{
    abstract val item: MaterialItem<M>

    init { all += this }

    override fun getFeature(material: M) = get(material)!!

    companion object: ClassRegister<MaterialBlock<*>>();
}