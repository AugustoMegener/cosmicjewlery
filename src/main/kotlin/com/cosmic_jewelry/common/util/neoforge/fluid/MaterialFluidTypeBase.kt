package com.cosmic_jewelry.common.util.neoforge.fluid

import com.cosmic_jewelry.client.core.material.util.MaterialFluidTypeExt
import com.cosmic_jewelry.common.core.material.Material
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions
import net.neoforged.neoforge.fluids.FluidType
import java.util.function.Consumer

class MaterialFluidTypeBase<M: Material<M>>(val material: M,
                                            private val textureName: String,
                                            val materialSymbol: String,
                                            properties: Properties) : FluidType(properties)
{
    private val clientExt = MaterialFluidTypeExt(material, textureName, materialSymbol)

    override fun initializeClient(consumer: Consumer<IClientFluidTypeExtensions>) { consumer.accept(clientExt) }


}