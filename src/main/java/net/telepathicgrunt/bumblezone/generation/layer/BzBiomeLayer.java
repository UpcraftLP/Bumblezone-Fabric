package net.telepathicgrunt.bumblezone.generation.layer;

import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.layer.type.InitLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.gen.ChunkRandom;
import net.telepathicgrunt.bumblezone.biome.BzBiomesInit;


public enum BzBiomeLayer implements InitLayer
{
	INSTANCE;

	private static final int SUGAR_WATER = Registry.BIOME.getRawId(BzBiomesInit.SUGAR_WATER);
	private static final int HIVE_WALL = Registry.BIOME.getRawId(BzBiomesInit.HIVE_WALL);
	private static final int HIVE_PILLAR = Registry.BIOME.getRawId(BzBiomesInit.HIVE_PILLAR);

	private static OctaveSimplexNoiseSampler perlinGen;
//	private double max = 0;
//	private double min = 1;
	

	public int sample(LayerRandomnessSource noise, int x, int z)
	{
		double perlinNoise = perlinGen.sample((double) x * 0.1D, (double)z * 0.00001D, false) * 0.5D + 0.5D;
		
//		max = Math.max(max, perlinNoise);
//		min = Math.min(min, perlinNoise);
//		Bumblezone.LOGGER.log(Level.DEBUG, "Max: " + max +", Min: "+min + ", perlin: "+perlinNoise);

		if(noise.nextInt(5) == 0) 
		{
			return HIVE_PILLAR;
		}
		else if(Math.abs(perlinNoise) < 0.7)
		{
			return SUGAR_WATER;
		}
		else
		{
			return HIVE_WALL;
		}
	}


	public static void setSeed(long seed)
	{
		if (perlinGen == null)
		{
			ChunkRandom sharedseedrandom = new ChunkRandom(seed);
			perlinGen = new OctaveSimplexNoiseSampler(sharedseedrandom, 0, 0);
		}
	}
}