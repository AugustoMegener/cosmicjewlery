package com.cosmic_jewelry.common.event

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.core.material.feature.gem.GemItem
import com.mojang.datafixers.util.Either
import net.minecraft.network.chat.Component
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.client.event.RenderTooltipEvent

@EventBusSubscriber(modid = ID, bus = EventBusSubscriber.Bus.GAME)
object NeoForgeCommonEvent {

    @SubscribeEvent
    fun onRenderTooltip(event: RenderTooltipEvent.GatherComponents) {
        GemItem.cuttersMohsMap[event.itemStack.item]?.also {
            event.tooltipElements += Either.left(Component.literal(("Mohs: ")).withColor(0xffbb00).append("$it"))
        }
    }
}