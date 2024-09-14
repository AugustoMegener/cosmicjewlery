package com.cosmic_jewelry.common.world.level.block.entity

import com.cosmic_jewelry.common.registry.BlockEntityTypeRegistry.buriedGemBlockEntityType
import com.cosmic_jewelry.common.registry.ItemRegistry.cutGemTag
import com.cosmic_jewelry.common.util.NeoForgeUtil.get
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.HolderLookup
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Blocks.STONE
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.storage.loot.LootParams
import net.neoforged.neoforge.items.ItemStackHandler
import net.minecraft.core.Direction.CODEC as DirCodec
import net.minecraft.nbt.NbtOps.INSTANCE as NbtOps
import net.minecraft.world.level.block.state.BlockState.CODEC as BlockStateCodec

class BuriedGemBlockEntity(pPos: BlockPos, pBlockState: BlockState)
    : BlockEntity(buriedGemBlockEntityType, pPos, pBlockState)
{
    var block: BlockState = STONE.defaultBlockState()
    var gem get() = inventory[0]; set(value) { inventory.setStackInSlot(0, value) }
    var gemFacing = Direction.NORTH

    private val inventory: ItemStackHandler =
    object : ItemStackHandler(1)
        { override fun getSlotLimit(slot: Int) = 1
          override fun onContentsChanged(slot: Int) { setChanged() }
          override fun isItemValid(slot: Int, stack: ItemStack) = stack.`is`(cutGemTag) }

    val blockDrops: NonNullList<ItemStack> =
        if (level is ServerLevel)
            NonNullList.copyOf(block.getDrops(LootParams.Builder(level as ServerLevel)))
        else
            NonNullList.create()


    override fun saveAdditional(pTag: CompoundTag, pRegistries: HolderLookup.Provider)
    { pTag.put("block",     BlockStateCodec.encodeStart(NbtOps, block).orThrow)
      pTag.put("inventory", inventory.serializeNBT(pRegistries))
      pTag.put("facing",    DirCodec.encodeStart(NbtOps, gemFacing).orThrow)

      super.saveAdditional(pTag, pRegistries) }

    override fun loadAdditional(pTag: CompoundTag, pRegistries: HolderLookup.Provider)
    { block = BlockStateCodec.parse(NbtOps, pTag.getCompound("block")).orThrow
      inventory.deserializeNBT(pRegistries, pTag.getCompound("inventory"))
      gemFacing = DirCodec.parse(NbtOps, pTag.getCompound("facing")).orThrow

      super.loadAdditional(pTag, pRegistries) }
}