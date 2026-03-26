package net.zaills.wetland.data;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.zaills.wetland.Wetland;
import net.zaills.wetland.worldgen.biome.WetlandBiome;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class WetlandDynamicRegistryProvider extends FabricDynamicRegistryProvider {
	public WetlandDynamicRegistryProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	public static void buildRegistry(RegistrySetBuilder registryBuilder) {
		registryBuilder.add(Registries.BIOME, WetlandBiome::bootstrap);
	}

	@Override
	protected void configure(HolderLookup.Provider registries, Entries entries) {
		addAll(entries, registries.lookupOrThrow(Registries.BIOME), Wetland.MOD_ID);
	}

	@Override
	public String getName() {
		return "Wetland Dynamic Registries";
	}

	@SuppressWarnings("UnusedReturnValue")
	public <T> List<Holder<T>> addAll(Entries entries, HolderLookup.RegistryLookup<T> registry, String modId) {
		return registry.listElementIds()
				.filter(tRegistryKey -> tRegistryKey.identifier().getNamespace().equals(modId))
				.map(tRegistryKey -> entries.add(registry, tRegistryKey))
				.toList();
	}
}
