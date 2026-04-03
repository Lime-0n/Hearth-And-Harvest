package alabaster.hearthandharvest.data;

import alabaster.hearthandharvest.HearthAndHarvest;
import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Generates two JSON model files per jar type:
 *
 *   item/2d_{name}.json
 *     A plain item/generated sprite — used in GUI, on the ground, and in item frames.
 *
 *   item/{name}.json
 *     A neoforge:separate_transforms model — shows the existing 3D block model
 *     in all other contexts (held, on entities, etc.) and falls back to the
 *     2D sprite for gui, ground, and fixed.
 *
 * Register alongside ItemModels in your GatherDataEvent handler:
 *   event.addProvider(true, new JarItemModelProvider(event.getPackOutput()));
 *
 * Adding a new jar type: add its name to JAR_NAMES below.
 * The name must match both the block model path (block/{name})
 * and the item sprite path (item/{name}).
 */
public class JarItemModelProvider implements DataProvider {

    /**
     * Registry of all jar content names.
     * Each entry must have:
     *   - an existing block model at  assets/hearthandharvest/models/block/{name}.json
     *   - an existing item sprite at  assets/hearthandharvest/textures/item/{name}.png
     */
    private static final List<String> JAR_NAMES = List.of(
            "jar",
            "apple_jam",
            "blueberry_jam",
            "cherry_jam",
            "grape_jam",
            "raspberry_jam",
            "sweet_berry_jam",
            "glow_berry_jam",
            "melon_jam",
            "peanut_butter",
            "pickled_beetroots",
            "pickled_cabbage",
            "pickled_carrots",
            "pickled_onions",
            "pickled_potatoes"
    );

    private final PackOutput.PathProvider itemModelPath;

    public JarItemModelProvider(PackOutput output) {
        this.itemModelPath = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models/item");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        for (String name : JAR_NAMES) {
            // ── item/2d_{name}.json ──────────────────────────────────────────────
            // Plain generated sprite, used by the perspectives block below.
            JsonObject twoD = new JsonObject();
            twoD.addProperty("parent", "minecraft:item/generated");
            JsonObject twoDTextures = new JsonObject();
            twoDTextures.addProperty("layer0", HearthAndHarvest.MODID + ":item/" + name);
            twoD.add("textures", twoDTextures);

            futures.add(DataProvider.saveStable(cache, twoD,
                    itemModelPath.json(ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "2d_" + name))));

            // ── item/{name}.json ─────────────────────────────────────────────────
            // separate_transforms root:
            //   base        → the existing 3D block model (held, on entities, etc.)
            //   perspectives → 2D sprite for gui / ground / fixed
            JsonObject root = new JsonObject();
            root.addProperty("loader", "neoforge:separate_transforms");

            // particle texture drives breaking/eating particles
            JsonObject rootTextures = new JsonObject();
            rootTextures.addProperty("particle", HearthAndHarvest.MODID + ":item/" + name);
            root.add("textures", rootTextures);

            // base: the existing block model, referenced directly — no wrapper file needed
            JsonObject base = new JsonObject();
            base.addProperty("parent", HearthAndHarvest.MODID + ":block/" + name);
            root.add("base", base);

            // perspectives: override the three flat-display contexts with the 2D sprite
            JsonObject perspectives = new JsonObject();
            for (String ctx : List.of("gui", "ground", "fixed")) {
                JsonObject p = new JsonObject();
                p.addProperty("parent", HearthAndHarvest.MODID + ":item/2d_" + name);
                perspectives.add(ctx, p);
            }
            root.add("perspectives", perspectives);

            futures.add(DataProvider.saveStable(cache, root,
                    itemModelPath.json(ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, name))));
        }

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Jar Item Models";
    }
}