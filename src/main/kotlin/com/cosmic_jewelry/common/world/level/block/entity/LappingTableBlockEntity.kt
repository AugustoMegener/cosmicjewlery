package com.cosmic_jewelry.common.world.level.block.entity

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.core.gem.feature.GemItem.Companion.cutterMohs
import com.cosmic_jewelry.common.registry.BlockEntityTypeRegistry.lappingTableBlockEntityType
import com.cosmic_jewelry.common.registry.RecipeRegistry.lappingRecipeType
import com.cosmic_jewelry.common.world.inventory.menu.LappingTableMenu
import net.minecraft.core.BlockPos
import net.minecraft.core.NonNullList
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.items.ItemStackHandler
import kotlin.jvm.optionals.getOrNull

class LappingTableBlockEntity(pPos: BlockPos, pBlockState: BlockState)
: BaseContainerBlockEntity(lappingTableBlockEntityType, pPos, pBlockState) {

    private val recipeManager by lazy { level!!.recipeManager }

    val inventory = object : ItemStackHandler(3) {

        val container get() = object : SimpleContainer(*items.toTypedArray()) {
            val recipe = recipeManager.getRecipeFor(lappingRecipeType, this, level!!).getOrNull()
        }

        override fun onContentsChanged(slot: Int) { when (slot) { 0, 1 -> recipeSlotChanged()
                                                                     2 -> outputSlotChanged() } }

        private fun outputSlotChanged() {
            with(container) {
                if (recipe != null && getStackInSlot(2).isEmpty) {
                    removeItem(1, 1)
                }
            }
        }

        private fun recipeSlotChanged() {
            with(container) {
                setStackInSlot(2, recipe?.value?.result ?: ItemStack.EMPTY)
            }
        }
    }

    override fun getContainerSize() = 3

    override fun createMenu(pContainerId: Int, pInventory: Inventory) =
        LappingTableMenu(pContainerId, pInventory, this)

    override fun getDefaultName(): Component = Component.translatable("container.$ID.lapping_table")

    override fun getItems(): NonNullList<ItemStack> =
        NonNullList.copyOf((0..inventory.slots).map { inventory.getStackInSlot(it) } )

    override fun setItems(pItems: NonNullList<ItemStack>) {
        pItems.withIndex().forEach { inventory.setStackInSlot(it.index, it.value) }
    }
}