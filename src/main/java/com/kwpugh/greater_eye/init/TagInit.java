package com.kwpugh.greater_eye.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;

public class TagInit
{
    public static final TagKey<ConfiguredStructureFeature<?, ?>> VILLAGES = TagKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation("greater_eye",  "villages"));
    public static final TagKey<ConfiguredStructureFeature<?, ?>> MINESHAFTS = TagKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation("greater_eye",  "mineshafts"));
    public static final TagKey<ConfiguredStructureFeature<?, ?>> SHIPS = TagKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation("greater_eye",  "ships"));
    public static final TagKey<ConfiguredStructureFeature<?, ?>> BURIED_TREASURES = TagKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation("greater_eye",  "buried_treasures"));
    public static final TagKey<ConfiguredStructureFeature<?, ?>> OUTPOSTS = TagKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation("greater_eye",  "outposts"));
    public static final TagKey<ConfiguredStructureFeature<?, ?>> MONUMENTS = TagKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation("greater_eye",  "monuments"));
    public static final TagKey<ConfiguredStructureFeature<?, ?>> PYRAMIDS = TagKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation("greater_eye",  "pyramids"));
    public static final TagKey<ConfiguredStructureFeature<?, ?>> MANSIONS = TagKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation("greater_eye",  "mansions"));
    public static final TagKey<ConfiguredStructureFeature<?, ?>> STRONGHOLDS = TagKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation("greater_eye",  "strongholds"));
    public static final TagKey<ConfiguredStructureFeature<?, ?>> IGLOOS = TagKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation("greater_eye",  "igloos"));
    public static final TagKey<ConfiguredStructureFeature<?, ?>> HUTS = TagKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation("greater_eye",  "huts"));
    public static final TagKey<ConfiguredStructureFeature<?, ?>> RUINS = TagKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation("greater_eye",  "ruins"));
    public static final TagKey<ConfiguredStructureFeature<?, ?>> TEMPLES = TagKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation("greater_eye",  "temples"));
    public static final TagKey<ConfiguredStructureFeature<?, ?>> FORTRESSES = TagKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation("greater_eye",  "fortresses"));
    public static final TagKey<ConfiguredStructureFeature<?, ?>> BASTIONS = TagKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation("greater_eye",  "bastions"));
    public static final TagKey<ConfiguredStructureFeature<?, ?>> FOSSILS = TagKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation("greater_eye",  "fossils"));
    public static final TagKey<ConfiguredStructureFeature<?, ?>> CITIES = TagKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation("greater_eye",  "cities"));

    public static final TagKey<ConfiguredStructureFeature<?, ?>> GRAVEYARDS = TagKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation("greater_eye",  "graveyards"));
    public static final TagKey<ConfiguredStructureFeature<?, ?>> DUNGEONS = TagKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation("greater_eye",  "dungeons"));

    public static final TagKey<ConfiguredStructureFeature<?, ?>> BUILDINGS = TagKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation("greater_eye",  "buildings"));
    public static final TagKey<ConfiguredStructureFeature<?, ?>> BUILDINGS_NETHER = TagKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation("greater_eye",  "buildings_nether"));
    public static final TagKey<ConfiguredStructureFeature<?, ?>> BUILDINGS_END = TagKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation("greater_eye",  "buildings_end"));
}