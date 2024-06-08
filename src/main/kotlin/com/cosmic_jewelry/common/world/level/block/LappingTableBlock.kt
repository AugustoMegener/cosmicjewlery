package com.cosmic_jewelry.common.world.level.block

import com.cosmic_jewelry.common.world.level.block.entity.LappingTableBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult


class LappingTableBlock(properties: Properties) : Block(properties), EntityBlock {
    override fun newBlockEntity(pPos: BlockPos, pState: BlockState) = LappingTableBlockEntity(pPos, pState)

    override fun getMenuProvider(pState: BlockState, pLevel: Level, pPos: BlockPos) =
        (pLevel.getBlockEntity(pPos) ?: newBlockEntity(pPos, pState)) as LappingTableBlockEntity

    override fun useWithoutItem(pState: BlockState, pLevel: Level, pPos: BlockPos, pPlayer: Player, r: BlockHitResult)
    : InteractionResult
    {

        if (!pLevel.isClientSide && pPlayer is ServerPlayer) {
            val a = getMenuProvider(pState, pLevel, pPos)
            a
            (pPlayer as ServerPlayer).openMenu(a)
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide)
    }
}