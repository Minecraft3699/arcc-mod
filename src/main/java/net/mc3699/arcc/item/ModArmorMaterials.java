package net.mc3699.arcc.item;

import net.mc3699.arcc.arcc;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;


public enum ModArmorMaterials implements ArmorMaterial {
    AUGMENTED("augmented", 400, SoundEvents.ARMOR_EQUIP_CHAIN, 1f, 0f, () -> Ingredient.of(Items.IRON_INGOT));

    private final String name;
    private final int durrabilityMultiplier;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    private static final int[] BASE_DURABILITY = {11, 16, 15, 13};

    ModArmorMaterials(String name, int durrabilityMultiplier, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durrabilityMultiplier = durrabilityMultiplier;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return BASE_DURABILITY[0] * this.durrabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return 2;
    }

    @Override
    public int getEnchantmentValue() {
        return 0;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return arcc.MODID+":"+name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
