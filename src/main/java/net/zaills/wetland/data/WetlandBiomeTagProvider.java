package net.zaills.wetland.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.biome.Biome;
import net.zaills.wetland.worldgen.biome.WetlandBiome;

import java.util.concurrent.CompletableFuture;

public class WetlandBiomeTagProvider extends FabricTagProvider<Biome> {
	public WetlandBiomeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, RegistryKeys.BIOME, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
		builder(BiomeTags.ALLOWS_SURFACE_SLIME_SPAWNS)
				.addOptional(WetlandBiome.WETLAND);
	}

}
