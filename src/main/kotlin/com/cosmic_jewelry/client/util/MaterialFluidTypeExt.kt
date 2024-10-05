package com.cosmic_jewelry.client.util

import com.cosmic_jewelry.common.core.material.Material
import net.minecraft.resources.ResourceLocation
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions
import net.minecraft.resources.ResourceLocation.parse as loc

class MaterialFluidTypeExt<M: Material<M>>(material       : Material<M>,
                                           textureName    : String,
                                           materialSymbol : String = "#") : IClientFluidTypeExtensions
{

    private val basePath = loc("${material.owner}:${textureName.replace(materialSymbol, material.name)}")

    private val  sourcePath : ResourceLocation = basePath.withPrefix("block/")
    private val flowingPath : ResourceLocation = basePath.withPrefix("block/flowing_")

    override fun   getStillTexture() = sourcePath
    override fun getFlowingTexture() = flowingPath
}