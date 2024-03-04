package myth_and_magic.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class TeleportEvasionEnchantment extends Enchantment {
    public TeleportEvasionEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.ARMOR_CHEST, new EquipmentSlot[]{EquipmentSlot.CHEST});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinPower(int level) {
        return 1 + (level - 1) * 11;
    }

    @Override
    public int getMaxPower(int level) {
        return getMinPower(level) + 11;
    }

    @Override
    public void onUserDamaged(LivingEntity user, Entity target, int level) {
        // s̶t̶o̶l̶e̶n̶ copied from chorus fruit
        if ((user.getRandom().nextInt(level + 1) > 0)) {
            World world = user.getWorld();
            if (!world.isClient) {
                double d = user.getX();
                double e = user.getY();
                double f = user.getZ();
                for (int i = 0; i < 16; ++i) {
                    double g = user.getX() + (user.getRandom().nextDouble() - 0.5) * level * 6;
                    double h = MathHelper.clamp(user.getY() + (double) (user.getRandom().nextInt(16) - 8),
                            (double) world.getBottomY(), (double) (world.getBottomY() + ((ServerWorld) world).getLogicalHeight() - 1));
                    double j = user.getZ() + (user.getRandom().nextDouble() - 0.5) * level * 6;
                    if (user.hasVehicle()) {
                        user.stopRiding();
                    }
                    Vec3d vec3d = user.getPos();
                    if (!user.teleport(g, h, j, true)) continue;
                    world.emitGameEvent(GameEvent.TELEPORT, vec3d, GameEvent.Emitter.of(user));
                    SoundEvent soundEvent = SoundEvents.ENTITY_ENDERMAN_TELEPORT;
                    world.playSound(null, d, e, f, soundEvent, SoundCategory.PLAYERS, 1.0f, 1.0f);
                    user.playSound(soundEvent, 1.0f, 1.0f);
                    break;
                }
            }
        }
    }
}