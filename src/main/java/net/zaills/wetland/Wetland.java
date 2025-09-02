package net.zaills.wetland;

import com.terraformersmc.biolith.api.biome.BiomePlacement;
import com.terraformersmc.biolith.api.surface.SurfaceGeneration;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.biome.BiomeKeys;
import net.zaills.wetland.worldgen.biome.WetlandBiome;
import net.zaills.wetland.worldgen.surfacebuilders.ModSurfaceBuilders;

public class Wetland implements ModInitializer {
	public static final String MOD_ID = "wetland";

	@Override
	public void onInitialize() {
		ModSurfaceBuilders.init();
		ModSurfaceBuilders.getBuilder().forEach(SurfaceGeneration::addSurfaceBuilder);

		BiomePlacement.replaceOverworld(BiomeKeys.SWAMP, WetlandBiome.WETLAND, 0.5d);
	}
}
