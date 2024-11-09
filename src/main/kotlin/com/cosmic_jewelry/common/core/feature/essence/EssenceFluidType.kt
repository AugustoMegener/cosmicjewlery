package com.cosmic_jewelry.common.core.feature.essence

import com.cosmic_jewelry.common.core.feature.MaterialFluidType
import com.cosmic_jewelry.common.core.material.essence.Essence
import com.cosmic_jewelry.common.core.util.ClassRegister
import com.cosmic_jewelry.common.core.util.UniversalTag
import net.minecraft.world.item.BucketItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items.BUCKET
import net.minecraft.world.level.block.LiquidBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraft.world.level.material.FlowingFluid
import net.minecraft.world.level.material.PushReaction
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions
import net.neoforged.neoforge.fluids.BaseFlowingFluid
import net.neoforged.neoforge.fluids.FluidType

class EssenceFluidType(name : String,
                       override val featureBuilder : (Essence) -> FluidType,
                       override val      clientExt : (Essence) -> IClientFluidTypeExtensions,
                       tags : List<UniversalTag> = listOf(),
                       essenceSymbol : String = "#",
)
    : MaterialFluidType<Essence>(name, tags, essenceSymbol)
{
    init { all += this }

    override val sourceFluid = EssenceFlowingFluid(
        "${name}_source",
        { BaseFlowingFluid.Source(getFlowingFluidProperties(it)) },
        essenceSymbol = essenceSymbol)

    override val flowingFluid = EssenceFlowingFluid(
        "flowing_$name",
        { BaseFlowingFluid.Flowing(getFlowingFluidProperties(it)) },
        essenceSymbol = essenceSymbol)

    override val bucket = EssenceItem(
        "${name}_bucket",
        { BucketItem(sourceFluid[it]!!, Item.Properties().craftRemainder(BUCKET).stacksTo(1).rarity(it.rarity)) },
        essenceSymbol = essenceSymbol)

    override val block = EssenceBlock(name, {
        LiquidBlock((sourceFluid[it] as FlowingFluid), Properties.of()
            .replaceable()
            .noCollission()
            .strength(100.0F)
            .pushReaction(PushReaction.DESTROY)
            .noLootTable()
            .liquid()
            .sound(SoundType.EMPTY))
    }, essenceSymbol = essenceSymbol)

    companion object : ClassRegister<EssenceFluidType>()
}