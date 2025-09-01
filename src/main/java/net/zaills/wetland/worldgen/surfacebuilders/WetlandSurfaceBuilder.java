package net.zaills.wetland.worldgen.surfacebuilders;

import com.terraformersmc.biolith.api.biomeperimeters.BiomePerimeters;
import com.terraformersmc.biolith.api.surface.BiolithSurfaceBuilder;
import com.terraformersmc.biolith.impl.noise.OpenSimplexNoise2;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
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

	public WetlandSurfaceBuilder(BlockState waterMaterial, BlockState landMaterial, BlockState topMaterial) {
		this.waterMaterial = waterMaterial;
		this.landMaterial = landMaterial;
		this.topMaterial = topMaterial;
	}
	@Override
	public void generate(BiomeAccess biomeAccess, BlockColumn column, Random rand, Chunk chunk, Biome biome, int x, int z, int vHeight, int seaLevel) {
		vHeight = chunk.sampleHeightmap(Heightmap.Type.OCEAN_FLOOR_WG, x & 0xf, z & 0xf);

		int y = vHeight;

		int top = seaLevel - 1;
		BlockState blocks = Blocks.AIR.getDefaultState();

		double waterHeight = NOISE.sample(x * 0.025, z * 0.05) * 3;
		double landHeight = -waterHeight;

		while (y > top) {
			column.setState(y, Blocks.AIR.getDefaultState());
			--y;
		}

//		Pair<Integer, Integer> neighbor = sampleNeighborBiome(biomeAccess, chunk, x, z, biome);
//		if (neighbor != null && neighbor.getLeft() != vHeight) {
//			int distance = neighbor.getRight();
//			int biomeTop =  neighbor.getLeft() - seaLevel;
//			int rampTop = (int) Math.sqrt(distance * distance + biomeTop * biomeTop);
//
//			top = (int) Math.max(Math.max(landHeight, seaLevel), biomeTop - distance);
//
//			while (y < rampTop) {
//				column.setState(y, Blocks.RED_STAINED_GLASS.getDefaultState());
//				y++;
//			}
//			return;
//		}

		for (int h = 0; h < waterHeight; h++) {
			column.setState(y, waterMaterial);
			--y;
		}

		for (int h = 0; h < landHeight; h++) {
			column.setState(y, landMaterial);
			++y;
		}
		if (y >= seaLevel)
			column.setState(y - 1, topMaterial);
	}
//
//	private Pair<Integer, Integer> sampleNeighborBiome(BiomeAccess biomeAccess, Chunk chunk, int x, int y, Biome currentBiome) {
//		int radius = 12;
//		for (int dx = -radius; dx <= radius; dx++) {
//			int neighborX = x + dx;
//			for (int dz = -radius; dz <= radius; dz++) {
//				int neighborZ = y + dz;
//				Biome neighborBiome = biomeAccess.getBiome(new BlockPos( neighborX, 64, neighborZ)).value();
//				if (!neighborBiome.equals(currentBiome)) {
//					int distance = (int) Math.sqrt(dx * dx + dz * dz);
//					return new Pair<>(chunk.sampleHeightmap(Heightmap.Type.OCEAN_FLOOR_WG, neighborX & 0xf, neighborZ & 0xf), distance);
//				}
//			}
//		}
//		return null;
//	}
}
