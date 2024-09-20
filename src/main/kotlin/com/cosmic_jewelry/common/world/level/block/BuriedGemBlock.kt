package com.cosmic_jewelry.common.world.level.block

import com.cosmic_jewelry.common.registry.BlockEntityTypeRegistry.buriedGemBlockEntityType
import com.cosmic_jewelry.common.world.level.block.entity.BuriedGemBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.Containers
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.PushReaction

class BuriedGemBlock(properties: Properties) : BaseEntityBlock(properties.noOcclusion().noTerrainParticles()) {

    override fun newBlockEntity(pPos: BlockPos, pState: BlockState) = BuriedGemBlockEntity(pPos, pState)

    override fun getPistonPushReaction(state: BlockState) = PushReaction.DESTROY

    override fun codec() = TODO("Not yet implemented")

    override fun onRemove(        pState : BlockState,
                                  pLevel : Level,
                                    pPos : BlockPos,
                               pNewState : BlockState,
                          pMovedByPiston : Boolean     )
    {
        val be = pLevel.getBlockEntity(pPos, buriedGemBlockEntityType).get()

        Containers.dropItemStack(pLevel, pPos.x.toDouble(), pPos.y.toDouble(), pPos.z.toDouble(), be.gem)

        if (pNewState.isAir) pLevel.setBlock(pPos, be.block, 2)
        else if (pLevel is ServerLevel) Containers.dropContents(pLevel, pPos, be.blockDrops)

        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston)
    }
}