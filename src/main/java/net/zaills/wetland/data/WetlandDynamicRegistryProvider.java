package net.zaills.wetland.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.zaills.wetland.Wetland;
import net.zaills.wetland.worldgen.biome.WetlandBiome;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class WetlandDynamicRegistryProvider extends FabricDynamicRegistryProvider {
	public WetlandDynamicRegistryProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	public static void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.BIOME, WetlandBiome::bootstrap);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
		addAll(entries, registries.getOrThrow(RegistryKeys.BIOME), Wetland.MOD_ID);
	}

	@Override
	public String getName() {
		return "Wetland Dynamic Registries";
	}

	@SuppressWarnings("UnusedReturnValue")
	public <T> List<RegistryEntry<T>> addAll(Entries entries, RegistryWrapper.Impl<T> registry, String modId) {
		return registry.streamKeys()
				.filter(tRegistryKey -> tRegistryKey.getValue().getNamespace().equals(modId))
				.map(tRegistryKey -> entries.add(registry, tRegistryKey))
				.toList();
	}
}
