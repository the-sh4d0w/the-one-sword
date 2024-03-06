package myth_and_magic.datagen;

import myth_and_magic.ExcaliburClaimedCriterion;
import myth_and_magic.MythAndMagic;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.RecipeCraftedCriterion;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class MythAndMagicAdvancementsProvider extends FabricAdvancementProvider {
    public MythAndMagicAdvancementsProvider(FabricDataOutput dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> consumer) {
        // TODO: add advancements
        // - root; figure out what
        //  -> create magic table
        //   -> create magic iron
        //    -> craft excalibur
        //     -> claim excalibur
        //   -> create magic gold
        // TODO: add translations
        Advancement rootAdvancement = Advancement.Builder.create()
                .display(
                        MythAndMagic.EXCALIBUR,
                        Text.literal("Worthy?"),
                        Text.literal("Craft excalibur."),
                        new Identifier("textures/gui/advancements/backgrounds/adventure.png"),
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                ).criterion("got_excalibur", RecipeCraftedCriterion.Conditions.create(
                        new Identifier(MythAndMagic.MOD_ID, "excalibur"))
                ).build(consumer, MythAndMagic.MOD_ID + "/got_excalibur");
        Advancement.Builder.create().parent(rootAdvancement)
                .display(
                        MythAndMagic.EXCALIBUR,
                        Text.literal("You are worthy!"),
                        Text.literal("Claim excalibur."),
                        null,
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        false
                ).criterion("claimed_excalibur", ExcaliburClaimedCriterion.Conditions.create()
                ).build(consumer, MythAndMagic.MOD_ID + "/claimed_excalibur");
    }
}