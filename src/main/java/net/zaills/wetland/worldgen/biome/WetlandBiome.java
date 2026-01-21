package net.zaills.wetland.worldgen.biome;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.world.attribute.BackgroundMusic;
import net.minecraft.world.attribute.EnvironmentAttributeMap;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.carver.ConfiguredCarvers;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.MiscPlacedFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.zaills.wetland.Wetland;

public class WetlandBiome {
	public static final RegistryKey<Biome> WETLAND = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Wetland.MOD_ID, "wetland"));

	public static void bootstrap(Registerable<Biome> registerable) {
		registerable.register(WETLAND, create(registerable));
	}


	public static Biome create(Registerable<Biome> registerable){
		return new Biome.Builder()
				.generationSettings(createGenerationSettings(registerable))
				.spawnSettings(createSpawnSettings())
				.precipitation(true)
				.temperature(0.2F)
				.downfall(0.7F)
				.effects(createBiomeEffects())
				.addEnvironmentAttributes(createEnvironmentAttributes())
				.build();
	}

	private static GenerationSettings createGenerationSettings(Registerable<Biome> registerable) {
		RegistryEntryLookup<ConfiguredCarver<?>> configuredCarvers = registerable.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER);
		RegistryEntryLookup<PlacedFeature> placedFeatures = registerable.getRegistryLookup(RegistryKeys.PLACED_FEATURE);

		GenerationSettings.LookupBackedBuilder builder = new GenerationSettings.LookupBackedBuilder(placedFeatures, configuredCarvers);
		addFeatures(builder);
		DefaultBiomeFeatures.addDefaultOres(builder);
		DefaultBiomeFeatures.addDefaultDisks(builder);
		DefaultBiomeFeatures.addDefaultFlowers(builder);
		DefaultBiomeFeatures.addDefaultGrass(builder);
		DefaultBiomeFeatures.addDefaultMushrooms(builder);
		DefaultBiomeFeatures.addDefaultVegetation(builder, true);
		DefaultBiomeFeatures.addSwampFeatures(builder);
		DefaultBiomeFeatures.addSwampVegetation(builder);
		return builder.build();
	}

	private static void addFeatures(GenerationSettings.LookupBackedBuilder generationSettings) {
		//DefaultBiomeFeatures.addLandCarvers(generationSettings);
		// disable LAKE_LAVA
		generationSettings.carver(ConfiguredCarvers.CAVE);
		generationSettings.carver(ConfiguredCarvers.CAVE_EXTRA_UNDERGROUND);
		generationSettings.carver(ConfiguredCarvers.CANYON);
		generationSettings.feature(GenerationStep.Feature.LAKES, MiscPlacedFeatures.LAKE_LAVA_UNDERGROUND);

		DefaultBiomeFeatures.addAmethystGeodes(generationSettings);
		DefaultBiomeFeatures.addDungeons(generationSettings);
		DefaultBiomeFeatures.addMineables(generationSettings);
		DefaultBiomeFeatures.addSprings(generationSettings);
		DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);
	}


	private static SpawnSettings createSpawnSettings() {
		SpawnSettings.Builder builder = new SpawnSettings.Builder();
		builder.spawn(SpawnGroup.AMBIENT, 10, new SpawnSettings.SpawnEntry(EntityType.BAT, 8, 8));

		builder.spawn(SpawnGroup.CREATURE, 12, new SpawnSettings.SpawnEntry(EntityType.SHEEP, 4, 4));
		builder.spawn(SpawnGroup.CREATURE, 10, new SpawnSettings.SpawnEntry(EntityType.PIG, 4, 4));
		builder.spawn(SpawnGroup.CREATURE, 10, new SpawnSettings.SpawnEntry(EntityType.CHICKEN, 4, 4));
		builder.spawn(SpawnGroup.CREATURE,  8, new SpawnSettings.SpawnEntry(EntityType.COW, 4, 4));
		builder.spawn(SpawnGroup.CREATURE, 10, new SpawnSettings.SpawnEntry(EntityType.FROG, 2, 5));

		builder.spawn(SpawnGroup.MONSTER, 100, new SpawnSettings.SpawnEntry(EntityType.SPIDER, 4, 4));
		builder.spawn(SpawnGroup.MONSTER, 95, new SpawnSettings.SpawnEntry(EntityType.ZOMBIE, 4, 4));
		builder.spawn(SpawnGroup.MONSTER, 5, new SpawnSettings.SpawnEntry(EntityType.ZOMBIE_VILLAGER, 4, 4));
		builder.spawn(SpawnGroup.MONSTER, 70, new SpawnSettings.SpawnEntry(EntityType.SKELETON, 4, 4));
		builder.spawn(SpawnGroup.MONSTER, 100, new SpawnSettings.SpawnEntry(EntityType.CREEPER, 4, 4));
		builder.spawn(SpawnGroup.MONSTER, 10, new SpawnSettings.SpawnEntry(EntityType.ENDERMAN, 1, 4));
		builder.spawn(SpawnGroup.MONSTER, 1, new SpawnSettings.SpawnEntry(EntityType.SLIME, 1, 1));
		builder.spawn(SpawnGroup.MONSTER, 30, new SpawnSettings.SpawnEntry(EntityType.BOGGED, 4, 4));

		builder.spawn(SpawnGroup.UNDERGROUND_WATER_CREATURE, 10, new SpawnSettings.SpawnEntry(EntityType.GLOW_SQUID, 4, 6));
		return builder.build();
	}

	private static BiomeEffects createBiomeEffects() {
		return new BiomeEffects.Builder()
				.waterColor(0x438a6f)
				.grassColor(0x365334)
				.foliageColor(0x577539)
				.grassColorModifier(BiomeEffects.GrassColorModifier.SWAMP)
				.build();
	}

	private static EnvironmentAttributeMap createEnvironmentAttributes() {
		return EnvironmentAttributeMap.builder()
				.with(EnvironmentAttributes.SKY_COLOR_VISUAL, 0x78A7FF)
				.with(EnvironmentAttributes.FOG_COLOR_VISUAL, 0xC0D8FF)
				.with(EnvironmentAttributes.WATER_FOG_COLOR_VISUAL, 0x316451)
				.with(EnvironmentAttributes.BACKGROUND_MUSIC_AUDIO, new BackgroundMusic(SoundEvents.MUSIC_OVERWORLD_SWAMP))
				.with(EnvironmentAttributes.WATER_FOG_END_DISTANCE_VISUAL, 0.85f)
				.build();
	}
}
