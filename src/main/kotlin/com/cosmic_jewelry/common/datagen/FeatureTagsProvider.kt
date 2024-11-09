package com.cosmic_jewelry.common.datagen

import com.cosmic_jewelry.common.core.feature.RegistrableMaterialFeature
import com.cosmic_jewelry.common.core.util.ClassRegister
import net.minecraft.core.HolderLookup
import net.minecraft.core.Registry
import net.minecraft.data.PackOutput
import net.minecraft.data.tags.TagsProvider
import net.neoforged.neoforge.common.data.ExistingFileHelper
import java.util.concurrent.CompletableFuture

class FeatureTagsProvider<T, F: RegistrableMaterialFeature<*, T>>(id:
                                                                    String,
                                                                  packOutput:
                                                                    PackOutput,
                                                                  registries:
                                                                    CompletableFuture<HolderLookup.Provider>,
                                                                  existingFileHelper:
                                                                    ExistingFileHelper,
                                                                  val registry:
                                                                    Registry<T>,
                                                                  val featureRegistry:
                                                                  ClassRegister<F>
)
    : TagsProvider<T>(packOutput, registry.key(), registries, id, existingFileHelper)
{
    override fun addTags(pProvider: HolderLookup.Provider) {
        featureRegistry.register
            .forEach { o -> o.content.forEach { (m, b) ->
            o.getTags(m).forEach { tag(it.by(o.registry.key())).add(registry.getResourceKey(b!!).get()) } } }
    }
}