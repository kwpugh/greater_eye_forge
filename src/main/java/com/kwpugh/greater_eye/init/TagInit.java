package com.kwpugh.greater_eye.init;

import com.kwpugh.greater_eye.GreaterEye;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.structure.Structure;


public class TagInit
{
    public static final TagKey<Structure> VILLAGES = TagKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(GreaterEye.modid,  "villages"));
    public static final TagKey<Structure> MINESHAFTS = TagKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(GreaterEye.modid,  "mineshafts"));
    public static final TagKey<Structure> SHIPS = TagKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(GreaterEye.modid,  "ships"));
    public static final TagKey<Structure> BURIED_TREASURES = TagKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(GreaterEye.modid,  "buried_treasures"));
    public static final TagKey<Structure> OUTPOSTS = TagKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(GreaterEye.modid,  "outposts"));
    public static final TagKey<Structure> MONUMENTS = TagKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(GreaterEye.modid,  "monuments"));
    public static final TagKey<Structure> PYRAMIDS = TagKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(GreaterEye.modid,  "pyramids"));
    public static final TagKey<Structure> MANSIONS = TagKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(GreaterEye.modid,  "mansions"));
    public static final TagKey<Structure> STRONGHOLDS = TagKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(GreaterEye.modid,  "strongholds"));
    public static final TagKey<Structure> IGLOOS = TagKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(GreaterEye.modid,  "igloos"));
    public static final TagKey<Structure> HUTS = TagKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(GreaterEye.modid,  "huts"));
    public static final TagKey<Structure> RUINS = TagKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(GreaterEye.modid,  "ruins"));
    public static final TagKey<Structure> TEMPLES = TagKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(GreaterEye.modid,  "temples"));
    public static final TagKey<Structure> FORTRESSES = TagKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(GreaterEye.modid,  "fortresses"));
    public static final TagKey<Structure> BASTIONS = TagKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(GreaterEye.modid,  "bastions"));
    public static final TagKey<Structure> FOSSILS = TagKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(GreaterEye.modid,  "fossils"));
    public static final TagKey<Structure> CITIES = TagKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(GreaterEye.modid,  "cities"));

    public static final TagKey<Structure> GRAVEYARDS = TagKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(GreaterEye.modid,  "graveyards"));
    public static final TagKey<Structure> DUNGEONS = TagKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(GreaterEye.modid,  "dungeons"));

    public static final TagKey<Structure> BUILDINGS = TagKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(GreaterEye.modid,  "buildings"));
    public static final TagKey<Structure> BUILDINGS_NETHER = TagKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(GreaterEye.modid,  "buildings_nether"));
    public static final TagKey<Structure> BUILDINGS_END = TagKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(GreaterEye.modid,  "buildings_end"));
}