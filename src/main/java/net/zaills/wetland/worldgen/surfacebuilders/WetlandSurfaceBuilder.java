package net.zaills.wetland.worldgen.surfacebuilders;

import com.terraformersmc.biolith.api.surface.BiolithSurfaceBuilder;
import com.terraformersmc.biolith.impl.noise.OpenSimplexNoise2;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.BlockColumn;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;

public class WetlandSurfaceBuilder extends BiolithSurfaceBuilder {

	private static final OpenSimplexNoise2 NOISE = new OpenSimplexNoise2(346987);

	private final BlockState waterMaterial;
	private final BlockState landMaterial;
	private final BlockState topMaterial;
	private final BlockState airMaterial;
	private final BlockState mossMaterial;

	public WetlandSurfaceBuilder(BlockState waterMaterial, BlockState landMaterial, BlockState topMaterial, BlockState airMaterial, BlockState mossMaterial) {
		this.waterMaterial = waterMaterial;
		this.landMaterial = landMaterial;
		this.topMaterial = topMaterial;
		this.airMaterial = airMaterial;
		this.mossMaterial = mossMaterial;
	}
	@Override
	public void generate(BiomeManager biomeAccess, BlockColumn column, RandomSource rand, ChunkAccess chunk, Biome biome, int x, int z, int vHeight, int seaLevel) {
		vHeight = chunk.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, x & 0xf, z & 0xf);
		int y = vHeight;

		double waterHeight = NOISE.sample(x * 0.025, z * 0.05) * 3;
		double landHeight = -waterHeight;

		double mossNoise = NOISE.sample(x * 0.05, z * 0.05) * 10;

		for (int h = 0; h < waterHeight; h++) {
			column.setBlock(y, y < seaLevel ? waterMaterial : airMaterial);
			--y;
		}

		BlockState landBlocks = landMaterial;
		BlockState topBlocks = topMaterial;
		if (y > seaLevel){
			landBlocks = airMaterial;
			topBlocks = airMaterial;
		}
		for (int h = 0; h < landHeight; h++) {
			column.setBlock(y, landBlocks);
			y++;
		}
		if (y >= seaLevel) {
			while (column.getBlock(y).isAir()) y--;
			column.setBlock(y, topBlocks);
		}

		if (y < seaLevel + 3 && mossNoise > 4) {
			while (column.getBlock(y).isAir() && column.getBlock(y).getBlock().equals(waterMaterial.getBlock())) y--;
			column.setBlock(y, mossMaterial);
		}
	}

}

