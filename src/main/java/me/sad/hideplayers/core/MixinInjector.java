package me.sad.hideplayers.core;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import java.util.Map;

public class MixinInjector implements IFMLLoadingPlugin {

    public MixinInjector() {
        System.out.println("[HidePlayers] Injecting Mixin...");
        MixinBootstrap.init();
        Mixins.addConfiguration("hideplayers.mixins.json");
        System.out.println("[HidePlayers] Mixin Injected!");
    }

    @Override public String[] getASMTransformerClass() {
        return new String[0];
    }
    @Override public String getModContainerClass() {
        return null;
    }
    @Override public String getSetupClass() {
        return null;
    }
    @Override public void injectData(Map<String, Object> data) { }
    @Override public String getAccessTransformerClass() {
        return null;
    }
}
