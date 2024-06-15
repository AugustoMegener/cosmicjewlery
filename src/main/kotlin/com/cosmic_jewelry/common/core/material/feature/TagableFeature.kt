package com.cosmic_jewelry.common.core.material.feature

import com.cosmic_jewelry.common.core.material.Material
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import org.jetbrains.annotations.ApiStatus.NonExtendable


interface TagableFeature<M: Material<M>, F> {
    val featureRegistry: ResourceKey<out Registry<F>>

    val tags: Array<TagKey<F>>

    fun newTag(location: ResourceLocation) = TagKey.create(featureRegistry, location)

    @Suppress("UNCHECKED_CAST")
    fun getFeatureTag(feature: RegistryMaterialFeature<M, F>, material: M) =
        featureTags.computeIfAbsent(feature.createTypeLocation(material)) { newTag(it) } as TagKey<F>

    @NonExtendable
    fun getTagsFor(feature: RegistryMaterialFeature<M, F>, material: M) = tags + getFeatureTag(feature, material)

    companion object {
        val featureTags = HashMap<ResourceLocation, TagKey<*>>()
    }
}