package com.cosmic_jewelry

import com.cosmic_jewelry.common.core.feature.essence.EssenceRecipe.Companion.fillEssenceContainersRecipe
import com.cosmic_jewelry.common.core.feature.gem.GemRecipe.Companion.cutGemBlockRecipe
import com.cosmic_jewelry.common.core.feature.gem.GemRecipe.Companion.gemLappingRecipe
import com.cosmic_jewelry.common.core.feature.gem.GemRecipe.Companion.gemPillarBlockRecipe
import com.cosmic_jewelry.common.core.feature.gem.GemRecipe.Companion.gemTilesBlockRecipe
import com.cosmic_jewelry.common.core.material.essence.Essence.Companion.blueEssence
import com.cosmic_jewelry.common.core.material.essence.Essence.Companion.pinkEssence
import com.cosmic_jewelry.common.core.material.essence.Essence.Companion.whiteEssence
import com.cosmic_jewelry.common.core.material.essence.Essence.Companion.yellowEssence
import com.cosmic_jewelry.common.core.material.gem.GemType.Companion.amethystGem
import com.cosmic_jewelry.common.core.material.gem.GemType.Companion.blueQuartzGem
import com.cosmic_jewelry.common.core.material.gem.GemType.Companion.carnelianGem
import com.cosmic_jewelry.common.core.material.gem.GemType.Companion.lapisLazuliGem
import com.cosmic_jewelry.common.core.material.gem.GemType.Companion.orangeJasperGem
import com.cosmic_jewelry.common.core.material.gem.GemType.Companion.peridotGem
import com.cosmic_jewelry.common.core.material.gem.GemType.Companion.roseQuartzGem
import com.cosmic_jewelry.common.core.material.gem.GemType.Companion.rubyGem
import com.cosmic_jewelry.common.core.material.gem.GemType.Companion.sapphireGem
import com.cosmic_jewelry.common.registry.BiomeModifierSerializerRegistry.biomeModifierSerializers
import com.cosmic_jewelry.common.registry.BlockEntityTypeRegistry.blockEntityTypes
import com.cosmic_jewelry.common.registry.BlockRegistry.blocks
import com.cosmic_jewelry.common.registry.BlockRegistry.cutGemBlock
import com.cosmic_jewelry.common.registry.BlockRegistry.deepslateGemOreBlock
import com.cosmic_jewelry.common.registry.BlockRegistry.gemOreBlock
import com.cosmic_jewelry.common.registry.BlockRegistry.pillarBlock
import com.cosmic_jewelry.common.registry.BlockRegistry.tilesBlock
import com.cosmic_jewelry.common.registry.DataComponentTypeRegistry.dataComponentTypes
import com.cosmic_jewelry.common.registry.FluidRegistry.fluids
import com.cosmic_jewelry.common.registry.FluidTypeRegistry.essenceFluidType
import com.cosmic_jewelry.common.registry.FluidTypeRegistry.fluidTypes
import com.cosmic_jewelry.common.registry.ItemRegistry.cutGemItem
import com.cosmic_jewelry.common.registry.ItemRegistry.essenceJarItem
import com.cosmic_jewelry.common.registry.ItemRegistry.items
import com.cosmic_jewelry.common.registry.ItemRegistry.rawGemItem
import com.cosmic_jewelry.common.registry.MaterialRegister
import com.cosmic_jewelry.common.registry.MaterialRegister.MaterialBuilder.Companion.blockFeature
import com.cosmic_jewelry.common.registry.MaterialRegister.MaterialBuilder.Companion.fluidTypeFeature
import com.cosmic_jewelry.common.registry.MenuTypeRegistry.menuTypes
import com.cosmic_jewelry.common.registry.RecipeRegistry.recipeSerializers
import com.cosmic_jewelry.common.registry.RecipeRegistry.recipeTypes
import com.cosmic_jewelry.common.registry.TabRegistry.tabs
import net.neoforged.fml.common.Mod
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

@Mod(CosmicJewelry.ID)
object CosmicJewelry {
    const val ID = "cosmic_jewelry"

    val logger: Logger = LogManager.getLogger(ID)

    init {
        registerMaterial()
        listOf(blocks, items, tabs, blockEntityTypes, menuTypes, recipeTypes, recipeSerializers, dataComponentTypes,
               biomeModifierSerializers, fluids, fluidTypes)
            .forEach { it.register(MOD_BUS) }
    }

    private fun registerMaterial() {
        MaterialRegister(ID) {
            addMaterial(      "peridot" to peridotGem,
                             "amethyst" to amethystGem,
                          "blue_quartz" to blueQuartzGem,
                            "carnelian" to carnelianGem,
                         "lapis_lazuli" to lapisLazuliGem,
                        "orange_jasper" to orangeJasperGem,
                          "rose_quartz" to roseQuartzGem,
                                 "ruby" to rubyGem,
                             "sapphire" to sapphireGem     )
            {
                blockFeature(blocks, items,
                    cutGemBlock, tilesBlock, pillarBlock)

                feature(items, cutGemItem)
                feature(cutGemBlockRecipe, gemPillarBlockRecipe, gemTilesBlockRecipe)
            }

            addFeatures(peridotGem) {
                blockFeature(blocks, items, deepslateGemOreBlock)
            }

            addFeatures(rubyGem, sapphireGem) {
                blockFeature(blocks, items, gemOreBlock)
            }

            addFeatures(rubyGem, sapphireGem, peridotGem) {
                feature(items, rawGemItem)
                feature(gemLappingRecipe)
            }

            addMaterial( "white_essence" to  whiteEssence,
                        "yellow_essence" to yellowEssence,
                          "blue_essence" to   blueEssence,
                          "pink_essence" to   pinkEssence   )
            {
                fluidTypeFeature(fluidTypes, fluids, blocks, items,
                    essenceFluidType)
                feature(items, essenceJarItem)
                feature(fillEssenceContainersRecipe)
            }
        }
    }
}