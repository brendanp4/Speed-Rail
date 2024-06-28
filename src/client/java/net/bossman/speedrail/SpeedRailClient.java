package net.bossman.speedrail;

import net.bossman.speedrail.item.ModItems;
import net.fabricmc.api.ClientModInitializer;

public class SpeedRailClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModItems.RegisterModItems();
	}
}