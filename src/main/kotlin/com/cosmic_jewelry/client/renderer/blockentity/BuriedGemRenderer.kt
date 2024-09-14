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
import net.minecraft.world.item.ItemDisplayContext
import org.joml.Vector3f


object BuriedGemRenderer : BlockEntityRenderer<BuriedGemBlockEntity> {

    private val blockRenderer: BlockRenderDispatcher by lazy { Minecraft.getInstance().blockRenderer }
    private val itemRenderer :          ItemRenderer by lazy { Minecraft.getInstance().itemRenderer  }

    private val itemRotations = mapOf(
        Direction.NORTH to Vector3f(0f,   0f,   0f),
        Direction.SOUTH to Vector3f(0f, 180f, 0f),
        Direction.WEST  to Vector3f(0f, 270f, 0f),
        Direction.EAST  to Vector3f(0f, 90f, 0f),
        Direction.UP    to Vector3f(90f, 0f, 0f),
        Direction.DOWN  to Vector3f(-90f, 0f,   0f))



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

        /*poseStack.translate(
            direction.stepX.toDouble() * 0.46875,
            direction.stepY.toDouble() * 0.46875,
            direction.stepZ.toDouble() * 0.46875
        )*/

        val rotation = direction.rotation

        poseStack.translate(-0.5f, -0.5f, -0.5f)
        poseStack.mulPose(Axis.XP.rotationDegrees(rotation.x))
        poseStack.mulPose(Axis.ZP.rotationDegrees(rotation.z * 360.0f / 8.0f))
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0f - rotation.y))
    }
}