package com.cosmic_jewelry.common.core.feature.gem

import com.cosmic_jewelry.common.core.feature.MaterialBlock
import com.cosmic_jewelry.common.core.material.gem.GemType
import com.cosmic_jewelry.common.core.util.ClassRegister
import com.cosmic_jewelry.common.core.util.UniversalTag
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.client.model.generators.BlockStateProvider

open class GemBlock(             name           : String,
                    override val featureBuilder : (GemType) -> Block,
                    override val dataGen        : BlockStateProvider.(GemType, Block) -> Unit =
                                     { _, b -> simpleBlockWithItem(b, cubeAll(b)) },
                                 tags           : List<UniversalTag> = listOf(),
                                 gemSymbol      : String = "#")
: MaterialBlock<GemType>(name, tags,  gemSymbol)
{


    override val item = GemItem(name, { BlockItem(this[it]!!, GemItem.defaultProperty(it)) }, { _, _ -> })

    override fun <T : GemType> getMaterialTags(material: T): List<UniversalTag> =
        listOf(material.family.miningLevel.tag)

    init { all += this }

    companion object : ClassRegister<GemBlock>()
}