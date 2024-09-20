package com.cosmic_jewelry.client.renderer.blockentity

import com.cosmic_jewelry.common.world.level.block.entity.BuriedGemBlockEntity
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.block.BlockRenderDispatcher
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.entity.ItemRenderer
import net.minecraft.core.Direction
import net.minecraft.core.Direction.*
import net.minecraft.world.item.ItemDisplayContext
import org.joml.Vector3f


object BuriedGemRenderer : BlockEntityRenderer<BuriedGemBlockEntity> {

    private val translates = mapOf(SOUTH to Vector3f(0.5f,  0.5f,  1.01f ),
                                   NORTH to Vector3f(0.5f,   0.5f, -0.01f),
                                   WEST  to Vector3f(-0.01f, 0.5f, 0.5f  ),
                                   EAST  to Vector3f(1.01f,  0.5f, 0.5f  ))

    private val mulPoses = mapOf(NORTH to Axis.YP.rotationDegrees(180f),
                                 WEST  to Axis.YP.rotationDegrees(90f),
                                 EAST  to Axis.YP.rotationDegrees(-90f))

    private val blockRenderer: BlockRenderDispatcher by lazy { Minecraft.getInstance().blockRenderer }
    private val itemRenderer :          ItemRenderer by lazy { Minecraft.getInstance().itemRenderer  }


    override fun render(  pBlockEntity : BuriedGemBlockEntity,
                          pPartialTick : Float,
                            pPoseStack : PoseStack,
                               pBuffer : MultiBufferSource,
                          pPackedLight : Int,
                        pPackedOverlay : Int
    ) {
        pPoseStack.pushPose()
        blockRenderer.renderSingleBlock(pBlockEntity.block, pPoseStack, pBuffer, pPackedLight, pPackedOverlay)
        pPoseStack.popPose()

        pPoseStack.pushPose()
        adjustItemRender(pPoseStack, pBlockEntity.gemFacing)
        itemRenderer.renderStatic(pBlockEntity.gem, ItemDisplayContext.GUI, pPackedLight, pPackedOverlay, pPoseStack, pBuffer, pBlockEntity.level, 0)
        pPoseStack.popPose()
    }

    private fun adjustItemRender(poseStack: PoseStack, direction: Direction) {

        val rotation = direction.rotation

        translates[direction]?.run { poseStack.translate(x, y, z) }
        mulPoses[direction]?.also { poseStack.mulPose(it) }
    }
}