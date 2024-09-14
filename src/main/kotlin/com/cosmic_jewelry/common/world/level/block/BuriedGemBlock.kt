package com.cosmic_jewelry.common.world.level.block

import com.cosmic_jewelry.common.registry.BlockEntityTypeRegistry.buriedGemBlockEntityType
import com.cosmic_jewelry.common.world.level.block.entity.BuriedGemBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.Containers
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.PushReaction

class BuriedGemBlock(properties: Properties) : Block(properties.noOcclusion()), EntityBlock {
    override fun newBlockEntity(pPos: BlockPos, pState: BlockState) = BuriedGemBlockEntity(pPos, pState)
    override fun skipRendering(pState: BlockState, pAdjacentState: BlockState, pDirection: Direction) = true

    override fun getPistonPushReaction(state: BlockState) = PushReaction.DESTROY

    override fun onRemove(        pState : BlockState,
                                  pLevel : Level,
                                    pPos : BlockPos,
                               pNewState : BlockState,
                          pMovedByPiston : Boolean     )
    {
        if (pLevel is ServerLevel) {
            val be = pLevel.getBlockEntity(pPos, buriedGemBlockEntityType).get()

            Containers.dropContents(pLevel, pPos, NonNullList.of(be.gem))

            if (pState.isAir) Containers.dropContents(pLevel, pPos, be.blockDrops)
                         else pLevel.setBlock(pPos, be.block, 2)
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston)
    }

    @Deprecated("Deprecated in Java",
        ReplaceWith("RenderShape.INVISIBLE", "net.minecraft.world.level.block.RenderShape"))
    override fun getRenderShape(pState: BlockState) = RenderShape.INVISIBLE
}