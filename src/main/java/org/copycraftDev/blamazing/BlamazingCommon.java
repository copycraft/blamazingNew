package org.copycraftDev.blamazing;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.copycraftDev.blamazing.item.BlamaceItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlamazingCommon {
	public static final Logger LOGGER = LoggerFactory.getLogger("blamazing");
	public static final Item BLAMACE = new BlamaceItem(             // Tier
			3,                                         // Extra attack damage
			-2.4f                                   // Attack speed modifier
	);
	public static void init() {
		LOGGER.info("BLAMAZING");
		Registry.register(
				Registries.ITEM,
				Identifier.of("blamazing", "blamace"),
				BLAMACE
		);
	}
}
