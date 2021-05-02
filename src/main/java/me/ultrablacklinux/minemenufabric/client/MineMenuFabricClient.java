package me.ultrablacklinux.minemenufabric.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import me.ultrablacklinux.minemenufabric.client.screen.MineMenuSelectScreen;
import me.ultrablacklinux.minemenufabric.client.screen.TypeCycle;
import me.ultrablacklinux.minemenufabric.client.util.GsonUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.TranslatableText;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class MineMenuFabricClient implements ClientModInitializer {
    MineMenuSelectScreen mineMenuSelectScreen;
    public static KeyBinding keyBinding;
    public static JsonObject minemenuData;
    public static ArrayList<String> datapath;
    TypeCycle typeCycle;
    @Override
    public void onInitializeClient() {
        //testData();
        minemenuData = GsonUtil.fixEntryAmount(new JsonObject());
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.examplemod.spook", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R,
                "category.examplemod.test"));

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (mineMenuSelectScreen != null) this.mineMenuSelectScreen.tick();
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (keyBinding.wasPressed()) {
                if (!(client.currentScreen instanceof MineMenuSelectScreen)) {
                    System.out.println(minemenuData);
                    client.openScreen(new MineMenuSelectScreen(minemenuData,
                            new TranslatableText("minemenu.default.title").getString(), null));
                }
            }

        });
    }

    private void testData() {
        JsonObject j = new JsonObject();

        JsonObject print1 = new JsonObject();
        print1.add("message", new JsonPrimitive("test123"));
        j.add(String.valueOf(j.size()), GsonUtil.template(
                "print1",
                "minecraft:red_bed",
                "print",
                print1
        ));

        JsonObject print2 = new JsonObject();
        print2.add("message", new JsonPrimitive("321tset"));
        j.add(String.valueOf(j.size()), GsonUtil.template(
                "print2",
                "minecraft:red_wool",
                "print",
                print2));


        JsonObject category = new JsonObject();
        category.add(String.valueOf(category.size()), GsonUtil.template(
                "printincategory",
                "minecraft:chest",
                "print",
                print2));

        GsonUtil.fixEntryAmount(category);


        j.add(String.valueOf(j.size()), GsonUtil.template(
                "category1",
                "minecraft:chest",
                "category",
                category));

        GsonUtil.fixEntryAmount(j);
        minemenuData = j;
    }
}
