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
//		BiomePlacement.addOverworld(WetlandBiome.WETLAND,
//				new MultiNoiseUtil.NoiseHypercube(
//					MultiNoiseUtil.ParameterRange.of(-0.11f,1f),
//					MultiNoiseUtil.ParameterRange.of(1.0f),
//					MultiNoiseUtil.ParameterRange.of(0.55f, 1f),
//					MultiNoiseUtil.ParameterRange.of(-1f, 1f),
//					MultiNoiseUtil.ParameterRange.of(0.0f),
//					MultiNoiseUtil.ParameterRange.of(-0.45f, 0.2f),
//					0L));

		//BiomePlacement.addSubOverworld(BiomeKeys.SWAMP, WetlandBiome.WETLAND, CriterionBuilder.NEAR_INTERIOR);
		ModSurfaceBuilders.init();
		ModSurfaceBuilders.getBuilder().forEach(SurfaceGeneration::addSurfaceBuilder);

		BiomePlacement.replaceOverworld(BiomeKeys.SWAMP, WetlandBiome.WETLAND, 0.5d);
	}
}
