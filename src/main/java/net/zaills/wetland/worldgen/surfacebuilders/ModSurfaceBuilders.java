package net.zaills.wetland.worldgen.surfacebuilders;

import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.zaills.wetland.Wetland;
import net.zaills.wetland.worldgen.biome.WetlandBiome;

import java.util.HashMap;

public class ModSurfaceBuilders {
	private static final HashMap<Identifier, WetlandSurfaceBuilder> builder = new HashMap<>(1);

	public static void init() {
		builder.put(Identifier.of(Wetland.MOD_ID, "surfece/wetland"),
				(WetlandSurfaceBuilder) new WetlandSurfaceBuilder(
//						Blocks.LIGHT_BLUE_STAINED_GLASS.getDefaultState(),
//						Blocks.BROWN_STAINED_GLASS.getDefaultState(),
//						Blocks.GREEN_STAINED_GLASS.getDefaultState(),
//						Blocks.AIR.getDefaultState()
						Blocks.WATER.getDefaultState(),
						Blocks.DIRT.getDefaultState(),
						Blocks.GRASS_BLOCK.getDefaultState(),
						Blocks.AIR.getDefaultState()
				).setBiomeKey(WetlandBiome.WETLAND));
	}

	public static HashMap<Identifier, WetlandSurfaceBuilder> getBuilder() {
		return builder;
	}
}