package com.kwpugh.greater_eye.util;

import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;

import java.util.Optional;
import java.util.Random;

public class LocateUtil
{
    public static void findStructureAndShoot(Level worldIn, Player playerIn, ItemStack itemstack, String structureChoice, TagKey<ConfiguredStructureFeature<?, ?>> type)
    {
        Random random = new Random();

        // A structure will always be found, no matter how far away
        BlockPos playerpos = playerIn.blockPosition();

        ServerLevel serverLevel = (ServerLevel) worldIn;
        BlockPos locpos = serverLevel.findNearestMapFeature(type, playerIn.blockPosition(), 100, false);

        if(locpos == null)
        {
            playerIn.displayClientMessage((new TranslatableComponent("Cannot be found! Structure might not exist here.").withStyle(ChatFormatting.GOLD)), true);

            return;
        }

        int structureDistance = Mth.floor(getDistance(playerpos.getX(), playerpos.getZ(), locpos.getX(), locpos.getZ()));







        // TESTING
        Optional<HolderSet.Named<ConfiguredStructureFeature<?, ?>>> optional = serverLevel.registryAccess().registryOrThrow(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY).getTag(type);
        if(optional.isPresent())
        {
            Pair<BlockPos, Holder<ConfiguredStructureFeature<?, ?>>> pair = serverLevel.getChunkSource().getGenerator().findNearestMapFeature(serverLevel, optional.get(), playerpos, 100, false);
            if(pair != null)
            {
                Holder<ConfiguredStructureFeature<?, ?>> value = pair.getSecond();
                playerIn.displayClientMessage((new TranslatableComponent("item.greater_eye.greater_eye.message3", value.unwrapKey().get().location().getPath(), structureDistance).withStyle(ChatFormatting.BOLD)), true);
            }
            else
            {
                playerIn.displayClientMessage((new TranslatableComponent("item.greater_eye.greater_eye.message3", structureChoice, structureDistance).withStyle(ChatFormatting.BOLD)), true);
            }
        }
        // TESTING






        EyeOfEnder finderentity = new EyeOfEnder(worldIn, playerIn.getX(), playerIn.getY(0.5D), playerIn.getZ());
        finderentity.setItem(itemstack);
        finderentity.signalTo(locpos);
        worldIn.addFreshEntity(finderentity);

        if (playerIn instanceof ServerPlayer)
        {
            CriteriaTriggers.USED_ENDER_EYE.trigger((ServerPlayer)playerIn, locpos);
        }

        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.NOTE_BLOCK_COW_BELL, SoundSource.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        worldIn.levelEvent(null, 1003, playerIn.blockPosition(), 0);

        if (!playerIn.getAbilities().instabuild)
        {
            itemstack.shrink(1);
        }
    }

    private static float getDistance(int x1, int z1, int x2, int z2)
    {
        int i = x2 - x1;
        int j = z2 - z1;

        return Mth.sqrt((float)(i * i + j * j));
    }
}
