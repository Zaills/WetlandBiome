package net.zaills.wetland.client;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.zaills.wetland.data.WetlandBiomeTagProvider;
import net.zaills.wetland.data.WetlandDynamicRegistryProvider;

public class WetlandDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(WetlandDynamicRegistryProvider::new);
		pack.addProvider(WetlandBiomeTagProvider::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		WetlandDynamicRegistryProvider.buildRegistry(registryBuilder);
	}
}
