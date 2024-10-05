package com.cosmic_jewelry.common.event

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.core.feature.MaterialItem
import com.cosmic_jewelry.common.core.feature.gem.GemItem
import com.cosmic_jewelry.common.registry.BlockEntityTypeRegistry.buriedGemBlockEntityType
import com.cosmic_jewelry.common.registry.BlockRegistry.buriedGemBlock
import com.cosmic_jewelry.common.registry.BlockRegistry.buryableBlockTag
import com.cosmic_jewelry.common.registry.DataComponentTypeRegistry.fluidData
import com.cosmic_jewelry.common.registry.ItemRegistry.cutGemTag
import com.cosmic_jewelry.common.world.item.FluidContainerItem
import com.mojang.datafixers.util.Either
import net.minecraft.network.chat.Component
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.capabilities.Capabilities.FluidHandler
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent
import net.neoforged.neoforge.client.event.RenderTooltipEvent
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack

@EventBusSubscriber(modid = ID, bus = EventBusSubscriber.Bus.GAME)
object NeoForgeCommonEvent {

    @SubscribeEvent
    fun onRenderTooltip(event: RenderTooltipEvent.GatherComponents) {
        GemItem.cuttersMohsMap[event.itemStack.item]?.also {
            event.tooltipElements += Either.left(Component.literal(("Mohs: ")).withColor(0xffbb00).append("$it"))
        }
    }

    @SubscribeEvent
    fun PlayerInteractEvent.RightClickBlock.onRightClickBlock() {
        if (!entity.isShiftKeyDown) return

        val state = level.getBlockState(pos)
        if (!state.`is`(buryableBlockTag)) return

        val item = entity.getItemInHand(hand)
        if (!item.`is`(cutGemTag)) return

        level.setBlock(pos, buriedGemBlock.defaultBlockState(), 2)

        level.getBlockEntity(pos, buriedGemBlockEntityType).get().apply {     block = state
                                                                                gem = item.split(1)
                                                                          gemFacing = entity.direction.opposite }
    }
}