package net.zaills.wetland.worldgen.surfacebuilders;

import com.terraformersmc.biolith.api.surface.BiolithSurfaceBuilder;
import com.terraformersmc.biolith.impl.noise.OpenSimplexNoise2;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.chunk.BlockColumn;

public class WetlandSurfaceBuilder extends BiolithSurfaceBuilder {

	private static final OpenSimplexNoise2 NOISE = new OpenSimplexNoise2(346987);

	private final BlockState waterMaterial;
	private final BlockState landMaterial;
	private final BlockState topMaterial;
	private final BlockState airMaterial;

	public WetlandSurfaceBuilder(BlockState waterMaterial, BlockState landMaterial, BlockState topMaterial, BlockState airMaterial) {
		this.waterMaterial = waterMaterial;
		this.landMaterial = landMaterial;
		this.topMaterial = topMaterial;
		this.airMaterial = airMaterial;
	}
	@Override
	public void generate(BiomeAccess biomeAccess, BlockColumn column, Random rand, Chunk chunk, Biome biome, int x, int z, int vHeight, int seaLevel) {
		vHeight = chunk.sampleHeightmap(Heightmap.Type.OCEAN_FLOOR_WG, x & 0xf, z & 0xf);
		int y = vHeight;

		double waterHeight = NOISE.sample(x * 0.025, z * 0.05) * 3;
		double landHeight = -waterHeight;

		for (int h = 0; h < waterHeight; h++) {
			column.setState(y, y < seaLevel ? waterMaterial : airMaterial);
			--y;
		}

		BlockState landBlocks = landMaterial;
		BlockState topBlocks = topMaterial;
		if (y > seaLevel){
			landBlocks = airMaterial;
			topBlocks = airMaterial;
		}
		for (int h = 0; h < landHeight; h++) {
			column.setState(y, landBlocks);
			y++;
		}
		if (y >= seaLevel) {
			while (column.getState(y).isAir()) y--;
			column.setState(y, topBlocks);
		}
	}

}

