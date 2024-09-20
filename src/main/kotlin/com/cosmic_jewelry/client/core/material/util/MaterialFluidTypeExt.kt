package com.cosmic_jewelry.client.core.material.util

import com.cosmic_jewelry.common.core.material.Material
import net.minecraft.resources.ResourceLocation
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions
import net.minecraft.resources.ResourceLocation.parse as loc

class MaterialFluidTypeExt<M: Material<M>>(material       : Material<M>,
                                           textureName    : String,
                                           materialSymbol : String = "#") : IClientFluidTypeExtensions
{
    private val basePath: ResourceLocation =
        loc("${material.owner}:${textureName.replace(materialSymbol, material.name)}").withPrefix("block/")

    private val  sourcePath: ResourceLocation = basePath.withSuffix("_source")
    private val flowingPath: ResourceLocation = basePath.withPrefix("flowing_")

    override fun   getStillTexture() = sourcePath
    override fun getFlowingTexture() = flowingPath
}