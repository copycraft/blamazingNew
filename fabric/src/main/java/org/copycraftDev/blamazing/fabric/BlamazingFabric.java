package org.copycraftDev.blamazing.fabric;

import net.fabricmc.api.ModInitializer;
import org.copycraftDev.blamazing.BlamazingCommon;

public class BlamazingFabric implements ModInitializer {
	@Override
	public void onInitialize() {
		BlamazingCommon.init();
	}
}
