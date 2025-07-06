package org.copycraftDev.blamazing.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BlamaceItem extends SwordItem {
    private static final float BASE_DAMAGE = 50.0f;
    private static final double VELOCITY_SCALE = 3.0;
    private static final double MIN_FALL_DISTANCE = 5.0;
    private static final float KNOCKBACK_STRENGTH = 1000f;

    public BlamaceItem(ToolMaterial material, int extraDamage, float attackSpeed, Item.Settings settings) {
        super(material, extraDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!(attacker instanceof PlayerEntity player)) {
            return super.postHit(stack, target, attacker);
        }

        if (player.fallDistance > MIN_FALL_DISTANCE) {
            World world = player.getWorld();
            Vec3d velocity = player.getVelocity();
            float bonus = (float) (VELOCITY_SCALE * velocity.length());
            float totalDamage = BASE_DAMAGE + bonus;

            // Deal damage
            DamageSource src = new DamageSource();
            target.damage(src, totalDamage);

            // Spawn particles on server
            if (!world.isClient && world instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(
                        ParticleTypes.FLAME,
                        target.getX(), target.getY() + target.getHeight() * 0.5,
                        target.getZ(),
                        20, 0.3, 0.3, 0.3, 0.1
                );
            }

            // Play sound
            world.playSound(
                    null,
                    target.getX(), target.getY(), target.getZ(),
                    SoundEvents.ENTITY_PLAYER_ATTACK_STRONG,
                    SoundCategory.PLAYERS,
                    1.0f, 1.0f
            );

            // Reset player velocity and apply knockback
            player.setVelocity(Vec3d.ZERO);
            Vec3d dir = player.getPos().subtract(target.getPos()).normalize();
            player.takeKnockback(KNOCKBACK_STRENGTH, dir.x, dir.z);
        }

        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return super.use(world, user, hand);
    }
}