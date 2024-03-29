package com.kwpugh.greater_eye.items;

import com.kwpugh.greater_eye.config.GeneralModConfig;
import com.kwpugh.greater_eye.init.TagInit;
import com.kwpugh.greater_eye.util.LocateUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ItemGreaterEyeNether extends Item
{
	boolean enableNetherBuildings = GeneralModConfig.ENABLE_BUILDINGS_NETHER.get();

	String structureChoice = "Fortresses";
    static TagKey<Structure> netherType = TagInit.FORTRESSES;

	public ItemGreaterEyeNether(Properties properties)
	{
		super(properties);
	}

	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn)
	{
		ItemStack itemstack = playerIn.getItemInHand(handIn);

		playerIn.startUsingItem(handIn);

		if((worldIn instanceof ServerLevel) && (playerIn.isShiftKeyDown()))   //shift right-click changes structure type to locate
		{
			switch(structureChoice)
			{
				case "Fortresses" -> {
					structureChoice = "Outposts";
					netherType = TagInit.OUTPOSTS;
				}
				case "Outposts" -> {
					structureChoice = "Temples";
					netherType = TagInit.TEMPLES;
				}
				case "Temples" -> {
					structureChoice = "Buried Treasures";
					netherType = TagInit.BURIED_TREASURES;
				}
				case "Buried Treasures" -> {
					structureChoice = "Pyramids";
					netherType = TagInit.PYRAMIDS;
				}
				case "Pyramids" -> {
					structureChoice = "Fossils";
					netherType = TagInit.FOSSILS;
				}
				case "Fossils" -> {
					if(enableNetherBuildings)  // if buildings enabled in config, use it here
					{
						structureChoice = "Nether Buildings";
						netherType = TagInit.BUILDINGS_NETHER;
					}
					else // otherwise move on to next
					{
						structureChoice = "Bastions";
						netherType = TagInit.BASTIONS;
					}
				}
				case "Nether Buildings" -> {
					structureChoice = "Bastions";
					netherType = TagInit.BASTIONS;
				}
				case "Bastions" -> {
					structureChoice = "Fortresses";
					netherType = TagInit.FORTRESSES;
				}
			}

			playerIn.displayClientMessage((Component.translatable("item.greater_eye.greater_eye.message1", structureChoice).withStyle(ChatFormatting.BOLD)), true);

			return InteractionResultHolder.success(itemstack);
		}

		if(!playerIn.isShiftKeyDown())   //simple right-click executes
		{
			if((worldIn instanceof ServerLevel))
			{
				LocateUtil.findStructureAndShoot(worldIn, playerIn, itemstack, structureChoice, netherType);

				return InteractionResultHolder.success(itemstack);
			}
		}

        return InteractionResultHolder.success(itemstack);
	}

	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
	{
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add((Component.translatable("item.greater_eye.greater_eye.line1").withStyle(ChatFormatting.GREEN)));
		tooltip.add((Component.translatable("item.greater_eye.greater_eye.line2").withStyle(ChatFormatting.YELLOW)));
		tooltip.add((Component.translatable("item.greater_eye.greater_eye.message2", structureChoice).withStyle(ChatFormatting.LIGHT_PURPLE)));
	}
}
