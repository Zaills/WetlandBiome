package net.zaills.wetland.worldgen.biome;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.attribute.BackgroundMusic;
import net.minecraft.world.attribute.EnvironmentAttributeMap;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.zaills.wetland.Wetland;

public class WetlandBiome {
	public static final ResourceKey<Biome> WETLAND = ResourceKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(Wetland.MOD_ID, "wetland"));

	public static void bootstrap(BootstrapContext<Biome> registerable) {
		registerable.register(WETLAND, create(registerable));
	}


	public static Biome create(BootstrapContext<Biome> registerable){
		return new Biome.BiomeBuilder()
				.generationSettings(createGenerationSettings(registerable))
				.mobSpawnSettings(createSpawnSettings())
				.hasPrecipitation(true)
				.temperature(0.2F)
				.downfall(0.7F)
				.specialEffects(createBiomeEffects())
				.putAttributes(createEnvironmentAttributes())
				.build();
	}

	private static BiomeGenerationSettings createGenerationSettings(BootstrapContext<Biome> registerable) {
		HolderGetter<ConfiguredWorldCarver<?>> configuredCarvers = registerable.lookup(Registries.CONFIGURED_CARVER);
		HolderGetter<PlacedFeature> placedFeatures = registerable.lookup(Registries.PLACED_FEATURE);

		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, configuredCarvers);
		addFeatures(builder);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addDefaultFlowers(builder);
		BiomeDefaultFeatures.addDefaultGrass(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder, true);
		BiomeDefaultFeatures.addSwampVegetation(builder);
		BiomeDefaultFeatures.addSwampExtraVegetation(builder);
		return builder.build();
	}

	private static void addFeatures(BiomeGenerationSettings.Builder generationSettings) {
		//DefaultBiomeFeatures.addLandCarvers(generationSettings);
		// disable LAKE_LAVA
		generationSettings.addCarver(Carvers.CAVE);
		generationSettings.addCarver(Carvers.CAVE_EXTRA_UNDERGROUND);
		generationSettings.addCarver(Carvers.CANYON);
		generationSettings.addFeature(GenerationStep.Decoration.LAKES, MiscOverworldPlacements.LAKE_LAVA_UNDERGROUND);

		BiomeDefaultFeatures.addDefaultCrystalFormations(generationSettings);
		BiomeDefaultFeatures.addDefaultMonsterRoom(generationSettings);
		BiomeDefaultFeatures.addDefaultUndergroundVariety(generationSettings);
		BiomeDefaultFeatures.addDefaultSprings(generationSettings);
		BiomeDefaultFeatures.addSurfaceFreezing(generationSettings);
	}


	private static MobSpawnSettings createSpawnSettings() {
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		builder.addSpawn(MobCategory.AMBIENT, 10, new MobSpawnSettings.SpawnerData(EntityType.BAT, 8, 8));

		builder.addSpawn(MobCategory.CREATURE, 12, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 4, 4));
		builder.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(EntityType.PIG, 4, 4));
		builder.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 4, 4));
		builder.addSpawn(MobCategory.CREATURE,  8, new MobSpawnSettings.SpawnerData(EntityType.COW, 4, 4));
		builder.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(EntityType.FROG, 2, 5));

		builder.addSpawn(MobCategory.MONSTER, 100, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 4, 4));
		builder.addSpawn(MobCategory.MONSTER, 95, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 4, 4));
		builder.addSpawn(MobCategory.MONSTER, 5, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE_VILLAGER, 4, 4));
		builder.addSpawn(MobCategory.MONSTER, 70, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 4, 4));
		builder.addSpawn(MobCategory.MONSTER, 100, new MobSpawnSettings.SpawnerData(EntityType.CREEPER, 4, 4));
		builder.addSpawn(MobCategory.MONSTER, 10, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 1, 4));
		builder.addSpawn(MobCategory.MONSTER, 1, new MobSpawnSettings.SpawnerData(EntityType.SLIME, 1, 1));
		builder.addSpawn(MobCategory.MONSTER, 30, new MobSpawnSettings.SpawnerData(EntityType.BOGGED, 4, 4));

		builder.addSpawn(MobCategory.UNDERGROUND_WATER_CREATURE, 10, new MobSpawnSettings.SpawnerData(EntityType.GLOW_SQUID, 4, 6));
		return builder.build();
	}

	private static BiomeSpecialEffects createBiomeEffects() {
		return new BiomeSpecialEffects.Builder()
				.waterColor(0x438a6f)
				.grassColorOverride(0x365334)
				.foliageColorOverride(0x577539)
				.grassColorModifier(BiomeSpecialEffects.GrassColorModifier.SWAMP)
				.build();
	}

	private static EnvironmentAttributeMap createEnvironmentAttributes() {
		return EnvironmentAttributeMap.builder()
				.set(EnvironmentAttributes.SKY_COLOR, 0x78A7FF)
				.set(EnvironmentAttributes.FOG_COLOR, 0xC0D8FF)
				.set(EnvironmentAttributes.WATER_FOG_COLOR, 0x316451)
				.set(EnvironmentAttributes.BACKGROUND_MUSIC, new BackgroundMusic(SoundEvents.MUSIC_BIOME_SWAMP))
				.set(EnvironmentAttributes.WATER_FOG_END_DISTANCE, 0.85f)
				.build();
	}
}
