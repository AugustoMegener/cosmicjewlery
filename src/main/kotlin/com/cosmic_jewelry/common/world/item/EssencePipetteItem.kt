package com.cosmic_jewelry.common.world.item

import com.cosmic_jewelry.common.util.fluid
import com.cosmic_jewelry.common.util.opposite
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.InteractionResultHolder.pass
import net.minecraft.world.InteractionResultHolder.success
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.neoforged.neoforge.fluids.FluidUtil.tryFluidTransfer

class EssencePipetteItem(properties: Properties, capacity: Int) : FluidContainerItem(properties, capacity, 0x808080) {

    override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
        val item = player.getItemInHand(usedHand)
        val pickItemHand = if (player.isShiftKeyDown) usedHand.opposite else usedHand

        tryFluidTransfer(player.getItemInHand(pickItemHand)         .fluid ?: return pass(item),
                         player.getItemInHand(pickItemHand.opposite).fluid ?: return pass(item), 10, true)
            .also { return if (it.isEmpty) pass(item) else success(item) }
    }
}