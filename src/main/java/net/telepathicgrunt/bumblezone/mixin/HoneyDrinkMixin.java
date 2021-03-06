package net.telepathicgrunt.bumblezone.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoneyBottleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.telepathicgrunt.bumblezone.Bumblezone;
import net.telepathicgrunt.bumblezone.dimension.BzDimensionType;
import net.telepathicgrunt.bumblezone.effects.BzEffectsInit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(HoneyBottleItem.class)
public class HoneyDrinkMixin
{
    //bees attack player that drinks honey bottles
    @Inject(method = "finishUsing",
            at = @At("HEAD"))
    private void onHoneyDrink(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> ci) {

       // Bumblezone.LOGGER.log(Level.INFO, "just drank");
        if(user instanceof PlayerEntity){
            PlayerEntity playerEntity = (PlayerEntity)user;

            //Make sure we are on actual player's computer and not a dedicated server. Vanilla does this check too.
            //Also checks to make sure we are in dimension and that player isn't in creative or spectator
            if (!world.isClient &&
                    (playerEntity.dimension == BzDimensionType.BUMBLEZONE_TYPE || Bumblezone.BZ_CONFIG.allowWrathOfTheHiveOutsideBumblezone) &&
                    !playerEntity.isCreative() &&
                    !playerEntity.isSpectator())
            {
                //if player drinks honey, bees gets very mad...
                if(stack.getItem() == Items.HONEY_BOTTLE && Bumblezone.BZ_CONFIG.aggressiveBees){
                    playerEntity.addStatusEffect(new StatusEffectInstance(BzEffectsInit.WRATH_OF_THE_HIVE, Bumblezone.BZ_CONFIG.howLongWrathOfTheHiveLasts, 2, false, Bumblezone.BZ_CONFIG.showWrathOfTheHiveParticles, true));
                }
            }
        }
    }

}