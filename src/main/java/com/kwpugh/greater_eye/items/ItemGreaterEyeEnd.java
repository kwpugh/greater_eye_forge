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

public class ItemGreaterEyeEnd extends Item
{
	boolean enableDungeons = GeneralModConfig.ENABLE_DUNGEONS.get();
	boolean enableEndBuildings = GeneralModConfig.ENABLE_BUILDINGS_NETHER.get();

	static TagKey<Structure> endType = TagInit.CITIES;

	String structureChoice = "Cities";

	public ItemGreaterEyeEnd(Properties properties)
	{
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn)
	{
		ItemStack itemstack = playerIn.getItemInHand(handIn);

		playerIn.startUsingItem(handIn);

		if((worldIn instanceof ServerLevel) && (playerIn.isShiftKeyDown()))   //shift right-click changes structure type to locate
		{
			switch(structureChoice)
			{
				case "Monuments" -> {
					structureChoice = "Mansions";
					endType = TagInit.MANSIONS;
				}
				case "Mansions" -> {
					if(enableDungeons)  // if buildings enabled in config, use it here
					{
						structureChoice = "Dungeons";
						endType = TagInit.DUNGEONS;
					}
					else // otherwise move on to next
					{
						structureChoice = "Cities";
						endType = TagInit.CITIES;
					}
				}
				case "Dungeons" -> {
					structureChoice = "Cities";
					endType = TagInit.CITIES;
				}
				case "Cities" -> {
					if(enableEndBuildings)  // if buildings enabled in config, use it here
					{
						structureChoice = "End Buildings";
						endType = TagInit.BUILDINGS_END;
					}
					else // otherwise move on to next
					{
						structureChoice = "Monuments";
						endType = TagInit.MONUMENTS;
					}
				}
				case "End Buildings" -> {
					structureChoice = "Monuments";
					endType = TagInit.MONUMENTS;
				}
			}

			playerIn.displayClientMessage((Component.translatable("item.greater_eye.greater_eye.message1", structureChoice).withStyle(ChatFormatting.BOLD)), true);

			return InteractionResultHolder.success(itemstack);
		}

		if(!playerIn.isShiftKeyDown())   //simple right-click executes
		{
			if((worldIn instanceof ServerLevel))
			{
				LocateUtil.findStructureAndShoot(worldIn, playerIn, itemstack, structureChoice, endType);

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
