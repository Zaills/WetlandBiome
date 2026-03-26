package net.zaills.wetland.worldgen.surfacebuilders;

import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Blocks;
import net.zaills.wetland.Wetland;
import net.zaills.wetland.worldgen.biome.WetlandBiome;

import java.util.HashMap;

public class ModSurfaceBuilders {
	private static final HashMap<Identifier, WetlandSurfaceBuilder> builder = new HashMap<>(1);

	public static void init() {
		builder.put(Identifier.fromNamespaceAndPath(Wetland.MOD_ID, "surfece/wetland"),
				(WetlandSurfaceBuilder) new WetlandSurfaceBuilder(
						Blocks.WATER.defaultBlockState(),
						Blocks.DIRT.defaultBlockState(),
						Blocks.GRASS_BLOCK.defaultBlockState(),
						Blocks.AIR.defaultBlockState(),
						Blocks.MOSS_BLOCK.defaultBlockState()
				).setBiomeKey(WetlandBiome.WETLAND));
	}

	public static HashMap<Identifier, WetlandSurfaceBuilder> getBuilder() {
		return builder;
	}
}