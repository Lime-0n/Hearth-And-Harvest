package alabaster.hearthandharvest.data;

import alabaster.hearthandharvest.HearthAndHarvest;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.utility.TextUtils;

import java.util.function.Supplier;

public class SoundDefinitions extends SoundDefinitionsProvider
{
    protected SoundDefinitions(PackOutput output, ExistingFileHelper helper) {
        super(output, HearthAndHarvest.MODID, helper);
    }

    @Override
    public void registerSounds() {
    }

    public void generateNewSoundWithSubtitle(Supplier<SoundEvent> event, String baseSoundDirectory, int numberOfSounds) {
        generateNewSound(event, baseSoundDirectory, numberOfSounds, true);
    }

    public void generateNewSound(Supplier<SoundEvent> event, String baseSoundDirectory, int numberOfSounds, boolean subtitle) {
        String formattedSub = subtitle ? TextUtils.subtitleKey(event.get().getLocation().getPath()) : null;
        this.generateNewSoundCustomSubtitle(event, baseSoundDirectory, numberOfSounds, formattedSub);
    }

    public void generateNewSoundCustomSubtitle(Supplier<SoundEvent> event, String baseSoundDirectory, int numberOfSounds, @Nullable String subtitle) {
        SoundDefinition definition = SoundDefinition.definition();
        if (subtitle != null) {
            definition.subtitle(subtitle);
        }
        for (int i = 1; i <= numberOfSounds; i++) {
            definition.with(SoundDefinition.Sound.sound(ResourceLocation.fromNamespaceAndPath(FarmersDelight.MODID, baseSoundDirectory + (numberOfSounds > 1 ? i : "")), SoundDefinition.SoundType.SOUND));
        }
        this.add(event, definition);
    }

    public void generateExistingSoundWithSubtitle(Supplier<SoundEvent> event, SoundEvent referencedSound) {
        this.generateExistingSound(event, referencedSound, true);
    }

    public void generateExistingSound(Supplier<SoundEvent> event, SoundEvent referencedSound, boolean subtitle) {
        SoundDefinition definition = SoundDefinition.definition();
        if (subtitle) {
            definition.subtitle(TextUtils.subtitleKey(event.get().getLocation().getPath()));
        }
        this.add(event, definition
                .with(SoundDefinition.Sound.sound(referencedSound.getLocation(), SoundDefinition.SoundType.EVENT)));
    }
}