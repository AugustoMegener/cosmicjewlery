package com.cosmic_jewelry.common.world.level.block.entity

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.registry.BlockEntityTypeRegistry.lappingTableBlockEntityType
import com.cosmic_jewelry.common.registry.RecipeRegistry.lappingRecipeType
import com.cosmic_jewelry.common.world.inventory.menu.LappingTableMenu
import com.cosmic_jewelry.common.world.item.crafting.LappingRecipeInput
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.items.ItemStackHandler
import kotlin.jvm.optionals.getOrNull

class LappingTableBlockEntity(pPos: BlockPos, pBlockState: BlockState)
: BlockEntity(lappingTableBlockEntityType, pPos, pBlockState), MenuProvider {

    private val blockLevel by lazy { level!! }
    private val recipeManager by lazy { blockLevel.recipeManager }

    val actualRecipe get() = recipeManager.getRecipeFor(lappingRecipeType,
                                                        LappingRecipeInput(inventory),
                                                        blockLevel).getOrNull()

    val inventory: ItemStackHandler = object : ItemStackHandler(4) {

        override fun getSlotLimit(slot: Int) = if (slot == 0 || slot == 2) 1 else super.getSlotLimit(slot)

        override fun onContentsChanged(slot: Int) {
            this@LappingTableBlockEntity.setChanged()

            if (slot == 2 || slot == 3) return

            val inv = this // BRUH...

            actualRecipe?.run {
                if (getStackInSlot(3).takeIf { !it.isEmpty }?.let { !it.`is`(value.result.item) } == true ||
                    extractItem(0, 1, true).takeIf { it != ItemStack.EMPTY }
                        ?.run { insertItem(2, this, true) == ItemStack.EMPTY } == false) return@run

                insertItem(3, value.assemble(LappingRecipeInput(inv), blockLevel.registryAccess()), false)
                insertItem(2, extractItem(0, 1, false), false)
            }
        }
    }

    override fun saveAdditional(pTag: CompoundTag, pRegistries: HolderLookup.Provider) {
        pTag.put("inventory", inventory.serializeNBT(pRegistries))
        super.saveAdditional(pTag, pRegistries)
    }

    override fun loadAdditional(pTag: CompoundTag, pRegistries: HolderLookup.Provider) {
        inventory.deserializeNBT(pRegistries, pTag.getCompound("inventory"))
        super.loadAdditional(pTag, pRegistries)
    }


    override fun createMenu(pContainerId: Int, pPlayerInventory: Inventory, pPlayer: Player) =
        LappingTableMenu(pContainerId, pPlayerInventory, this)

    override fun getDisplayName(): Component = Component.translatable("container.$ID.lapping_table")
}