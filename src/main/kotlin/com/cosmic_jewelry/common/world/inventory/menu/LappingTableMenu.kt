package com.cosmic_jewelry.common.world.inventory.menu

import com.cosmic_jewelry.common.core.gem.feature.GemItem.Companion.cutterMohs
import com.cosmic_jewelry.common.core.gem.feature.GemItem.Companion.isCutter
import com.cosmic_jewelry.common.registry.BlockRegistry.lappingTableBlock
import com.cosmic_jewelry.common.registry.MenuTypeRegistry.lappingTableMenu
import com.cosmic_jewelry.common.registry.RecipeRegistry
import com.cosmic_jewelry.common.registry.RecipeRegistry.lappingRecipeType
import com.cosmic_jewelry.common.world.level.block.entity.LappingTableBlockEntity
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.inventory.CraftingMenu
import net.minecraft.world.inventory.FurnaceMenu
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.items.IItemHandler
import net.neoforged.neoforge.items.SlotItemHandler

class LappingTableMenu(containerId: Int, playerInventory: Inventory, val blockEntity: LappingTableBlockEntity)
: AbstractContainerMenu(lappingTableMenu, containerId)
{
    val level     = playerInventory.player.level()
    val inventory = blockEntity.inventory

    val cutterSlot =
    object : SlotItemHandler(inventory, 0,  37,  34) {
        override fun mayPlace(stack: ItemStack) = super.mayPlace(stack) && stack.isCutter
    }

    val inputSlot =
    object : SlotItemHandler(inventory, 1,  55,  52) {}

    val outputSlot =
    object : SlotItemHandler(inventory, 2, 116, 35) {
        override fun mayPickup(playerIn: Player) = false

        override fun setChanged() { outputSlotChanged()
                                    super.setChanged()    }
    }


    constructor(containerId: Int, inv: Inventory, buf: RegistryFriendlyByteBuf)
            : this(containerId, inv, inv.player.level().getBlockEntity(buf.readBlockPos()) as LappingTableBlockEntity)

    init {
        repeat(3) { y ->
        repeat(9) { x -> addSlot(Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18)) } }

        repeat(9) { addSlot(Slot(playerInventory, it, 8 + it * 18, 142)) }

        arrayOf(cutterSlot, inputSlot, outputSlot).forEach(::addSlot)
    }

    override fun quickMoveStack(pPlayer: Player, pIndex: Int): ItemStack { TODO() }

    override fun stillValid(pPlayer: Player) = stillValid(level as ContainerLevelAccess, pPlayer, lappingTableBlock)

    private fun outputSlotChanged() {
        TODO("Not yet implemented")
    }

    companion object {
        val slotCords = listOf(37  to 34 /* Cutter */,
                               55  to 52 /* Input  */,
                               116 to 35 /* Output */).withIndex()
    }
}