package net.zaills.wetland.data;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.zaills.wetland.worldgen.biome.WetlandBiome;

import java.util.concurrent.CompletableFuture;

public class WetlandBiomeTagProvider extends FabricTagsProvider<Biome> {
	public WetlandBiomeTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, Registries.BIOME, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider wrapperLookup) {
		builder(BiomeTags.ALLOWS_SURFACE_SLIME_SPAWNS)
				.addOptional(WetlandBiome.WETLAND);
	}

}
