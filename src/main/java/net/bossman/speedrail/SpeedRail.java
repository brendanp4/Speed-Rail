package net.bossman.speedrail;

import com.mojang.datafixers.types.templates.Tag;
import net.bossman.speedrail.block.SpeedRailBlock;
import net.bossman.speedrail.item.ModItems;
import net.fabricmc.api.ModInitializer;

import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpeedRail implements ModInitializer {
	public static final String MOD_ID = "speed_rail";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Block SPEED_RAIL_BLOCK = new SpeedRailBlock(AbstractBlock.Settings.create().noCollision().strength(0.7F).sounds(BlockSoundGroup.METAL));


	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
		//ModItems.RegisterModItems();
		//SpeedRailBlock.registerBlocks();
		Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "speed_rail"), SPEED_RAIL_BLOCK);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "speed_rail"), new BlockItem(SPEED_RAIL_BLOCK, new Item.Settings()));



	}
}