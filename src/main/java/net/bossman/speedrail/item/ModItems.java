package net.bossman.speedrail.item;

import net.bossman.speedrail.SpeedRail;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item SRail = registerItem("srail", new Item(new Item.Settings()));

    private static void addItemsToIngredientItemsGroup(FabricItemGroupEntries entries) {
        entries.add(SRail);
    }

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(SpeedRail.MOD_ID, name), item);
    }

    public static void RegisterModItems(){
        SpeedRail.LOGGER.info("Registering Mod Items for " + SpeedRail.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemsGroup);
    }
}
