package net.telepathicgrunt.bumblezone.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.world.World;
import net.telepathicgrunt.bumblezone.Bumblezone;

import java.util.List;


public class WrathOfTheHiveEffect extends StatusEffect
{
	public WrathOfTheHiveEffect(StatusEffectType type, int potionColor)
	{
		super(type, potionColor);
	}


	/**
	 * Returns true if the potion has an instant effect instead of a continuous one (eg Harming)
	 */
	public boolean isInstant()
	{
		return true;
	}


	/**
	 * checks if Potion effect is ready to be applied this tick.
	 */
	public boolean canApplyUpdateEffect(int duration, int amplifier)
	{
		return duration >= 1;
	}


	/**
	 * Makes the bees swarm at the entity
	 */
	public void applyUpdateEffect(LivingEntity entity, int amplifier)
	{
		//Maximum aggression
		if(amplifier >= 2) 
		{
			unBEElievablyHighAggression(entity.world, entity);
		}
		//Anything lower than 2 is medium aggression
		else 
		{
			mediumAggression(entity.world, entity);
		}
	}
	
	
	/**
	 * Bees are angry but not crazy angry
	 */
	public static void mediumAggression(World world, LivingEntity livingEntity) 
	{
		TargetPredicate line_of_sight = (new TargetPredicate()).setBaseMaxDistance(Bumblezone.BZ_CONFIG.aggressionTriggerRadius).includeHidden();
		List<BeeEntity> beeList = world.getTargets(BeeEntity.class, line_of_sight, livingEntity, livingEntity.getBoundingBox().expand(Bumblezone.BZ_CONFIG.aggressionTriggerRadius));

		for(BeeEntity bee : beeList)
		{
			bee.setBeeAttacker(livingEntity);

			// weaker potion effects for when attacking bears
			bee.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 20, Math.max(Bumblezone.BZ_CONFIG.speedBoostLevel, 1), false, false));
			bee.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 20, Math.max(Bumblezone.BZ_CONFIG.absorptionBoostLevel/2, 1), false, false));
			bee.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 20, Math.max(Bumblezone.BZ_CONFIG.strengthBoostLevel/3, 1), false, true));
		}
	}
	
	
	/**
	 * Bees are REALLY angry!!! HIGH TAIL IT OUTTA THERE BRUH!!!
	 */
	public static void unBEElievablyHighAggression(World world, LivingEntity livingEntity) 
	{
		TargetPredicate see_through_walls = (new TargetPredicate()).setBaseMaxDistance(Bumblezone.BZ_CONFIG.aggressionTriggerRadius);
		List<BeeEntity> beeList = world.getTargets(BeeEntity.class, see_through_walls, livingEntity, livingEntity.getBoundingBox().expand(Bumblezone.BZ_CONFIG.aggressionTriggerRadius));
		for(BeeEntity bee : beeList)
		{
			bee.setBeeAttacker(livingEntity);
			bee.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 20, Bumblezone.BZ_CONFIG.speedBoostLevel, false, false));
			bee.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 20, Bumblezone.BZ_CONFIG.absorptionBoostLevel, false, false));
			bee.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 20, Bumblezone.BZ_CONFIG.strengthBoostLevel, false, true));

		}
	}
}
