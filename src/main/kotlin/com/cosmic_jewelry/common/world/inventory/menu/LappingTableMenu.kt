package com.cosmic_jewelry.common.world.inventory.menu

import com.cosmic_jewelry.common.core.gem.feature.RegistryGemItem.Companion.cutterMohs
import com.cosmic_jewelry.common.core.gem.feature.RegistryGemItem.Companion.isCutter
import com.cosmic_jewelry.common.registry.BlockRegistry.lappingTableBlock
import com.cosmic_jewelry.common.registry.MenuTypeRegistry.lappingTableMenu
import com.cosmic_jewelry.common.world.level.block.entity.LappingTableBlockEntity
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.neoforged.neoforge.items.SlotItemHandler

class LappingTableMenu(containerId: Int, playerInventory: Inventory, val blockEntity: LappingTableBlockEntity)
: AbstractContainerMenu(lappingTableMenu, containerId)
{
    val level: Level = playerInventory.player.level()
    val levelAccess: ContainerLevelAccess = ContainerLevelAccess.create(level, blockEntity.blockPos)
    val inventory = blockEntity.inventory

    private val recipes = level.recipeManager


    val cutterInputSlot: SlotItemHandler =
    object : SlotItemHandler(inventory, 0,  38,  35) {
        override fun mayPlace(stack: ItemStack) =
            super.mayPlace(stack) && stack.isCutter && !cutterOutputSlot.hasItem() && gemInputSlot.hasItem() &&
                                     stack.cutterMohs!! >= gemInputSlot.item.cutterMohs!!

        override fun isHighlightable() = !cutterOutputSlot.hasItem() && gemInputSlot.hasItem()
        override fun getMaxStackSize() = 1
    }

    val gemInputSlot: SlotItemHandler =
    object : SlotItemHandler(inventory, 1,  56,  53) {
        override fun mayPlace(stack: ItemStack) = stack.isCutter
    }

    val cutterOutputSlot: SlotItemHandler =
    object : SlotItemHandler(inventory, 2, 38, 53) {
        override fun mayPlace(pStack: ItemStack) = false
        override fun isHighlightable() = !cutterInputSlot.hasItem() && hasItem()
        override fun getMaxStackSize() = 1
    }

    val gemOutputSlot: SlotItemHandler =
    object : SlotItemHandler(inventory, 3, 116, 35) {
        override fun mayPlace(pStack: ItemStack) = false
    }



    constructor(containerId: Int, inv: Inventory, buf: RegistryFriendlyByteBuf)
            : this(containerId, inv, inv.player.level().getBlockEntity(buf.readBlockPos()) as LappingTableBlockEntity)

    init {
        repeat(3) { y ->
        repeat(9) { x -> addSlot(Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18)) } }

        repeat(9) { addSlot(Slot(playerInventory, it, 8 + it * 18, 142)) }

        arrayOf(cutterInputSlot, gemInputSlot, cutterOutputSlot, gemOutputSlot).forEach(::addSlot)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        val sourceSlot = slots[index]
        val sourceStack = sourceSlot.item.takeIf { !it.isEmpty } ?: return ItemStack.EMPTY
        val copyOfSourceStack = sourceStack.copy()

        val (start, end) = when (index) {
            in 0..1 -> 2 to 4
            in 2..3 -> 0 to 2
            else -> 0 to 4
        }

        if (!moveItemStackTo(sourceStack, start, end, false)) return ItemStack.EMPTY

        if (sourceStack.isEmpty) sourceSlot.set(ItemStack.EMPTY)
        else sourceSlot.setChanged()

        return if (sourceStack.count == copyOfSourceStack.count)  ItemStack.EMPTY
               else copyOfSourceStack.also { sourceSlot.onTake(player, sourceStack) }
    }


    override fun stillValid(pPlayer: Player) = stillValid(levelAccess, pPlayer, lappingTableBlock)
}