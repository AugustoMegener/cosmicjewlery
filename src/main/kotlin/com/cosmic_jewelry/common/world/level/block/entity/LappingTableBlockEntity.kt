package com.cosmic_jewelry.common.world.level.block.entity

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.registry.BlockEntityTypeRegistry.lappingTableBlockEntityType
import com.cosmic_jewelry.common.world.inventory.menu.LappingTableMenu
import net.minecraft.core.BlockPos
import net.minecraft.core.NonNullList
import net.minecraft.network.chat.Component
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity
import net.minecraft.world.level.block.state.BlockState

class LappingTableBlockEntity(pPos: BlockPos, pBlockState: BlockState)
: BaseContainerBlockEntity(lappingTableBlockEntityType, pPos, pBlockState) {

    val container = SimpleContainer(containerSize)

    override fun getContainerSize() = 3

    override fun createMenu(pContainerId: Int, pInventory: Inventory) =
        LappingTableMenu(pContainerId, pInventory, this)

    override fun getDefaultName(): Component = Component.translatable("container.$ID.lapping_table")

    override fun getItems(): NonNullList<ItemStack> = container.items

    override fun setItems(pItems: NonNullList<ItemStack>) {
        pItems.withIndex().forEach { container.setItem(it.index, it.value) }
    }
}