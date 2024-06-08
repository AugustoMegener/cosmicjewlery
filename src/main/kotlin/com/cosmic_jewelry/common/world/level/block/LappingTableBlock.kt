package com.cosmic_jewelry.common.world.level.block

import com.cosmic_jewelry.common.world.level.block.entity.LappingTableBlockEntity
import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import java.util.*


class LappingTableBlock(properties: Properties) : Block(properties), EntityBlock {

    override fun newBlockEntity(pPos: BlockPos, pState: BlockState) = LappingTableBlockEntity(pPos, pState)

    override fun getMenuProvider(pState: BlockState, pLevel: Level, pPos: BlockPos) =
        getBlockEntity(pLevel, pPos, pState) as LappingTableBlockEntity

    override fun useWithoutItem(pState: BlockState, pLevel: Level, pPos: BlockPos, pPlayer: Player, r: BlockHitResult)
    : InteractionResult
    {
        if (pPlayer is ServerPlayer) pPlayer.openMenu(pState.getMenuProvider(pLevel, pPos)!!) {}

        return InteractionResult.sidedSuccess(pLevel.isClientSide)
    }

    private fun getBlockEntity(level: Level, pos: BlockPos, state: BlockState) =
        level.getBlockEntity(pos) ?: newBlockEntity(pos, state)
}