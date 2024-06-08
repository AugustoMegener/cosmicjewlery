package com.cosmic_jewelry.common.world.inventory.menu

import com.cosmic_jewelry.common.registry.MenuTypeRegistry.lappingTableMenu
import com.cosmic_jewelry.common.world.level.block.entity.LappingTableBlockEntity
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack

class LappingTableMenu(containerId: Int, playerInventory: Inventory, val blockEntity: LappingTableBlockEntity)
: AbstractContainerMenu(lappingTableMenu, containerId)
{
    val level = playerInventory.player.level()
    val container = blockEntity.container

    constructor(containerId: Int, inv: Inventory, buf: RegistryFriendlyByteBuf)
            : this(containerId, inv, inv.player.level().getBlockEntity(buf.readBlockPos()) as LappingTableBlockEntity)

    init {
        for (i in 0..2) { for (j in 0..8) {
            addSlot(Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18)) } }

        for (k in 0..8) { addSlot(Slot(playerInventory, k, 8 + k * 18, 142)) }

        listOf(Slot(container, 0, 37,  34),
               Slot(container, 1, 55,  52),
               Slot(container, 2, 116, 35)).forEach(::addSlot)
    }

    override fun quickMoveStack(pPlayer: Player, pIndex: Int): ItemStack {
        TODO("Not yet implemented")
    }

    override fun stillValid(pPlayer: Player) = container.stillValid(pPlayer)
}