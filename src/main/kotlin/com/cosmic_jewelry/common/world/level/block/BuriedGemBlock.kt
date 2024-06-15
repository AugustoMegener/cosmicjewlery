package com.cosmic_jewelry.common.world.level.block

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class BuriedGemBlock(properties: Properties) : Block(properties), EntityBlock {
    override fun newBlockEntity(pPos: BlockPos, pState: BlockState): BlockEntity? {
        TODO("Not yet implemented")
    }
}