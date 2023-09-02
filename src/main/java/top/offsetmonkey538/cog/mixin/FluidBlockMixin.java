package top.offsetmonkey538.cog.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.FluidBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import top.offsetmonkey538.cog.CobbleOreGenerator;

@Mixin(FluidBlock.class)
public abstract class FluidBlockMixin {

    @Redirect(
            method = "receiveNeighborFluids",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/block/Blocks;COBBLESTONE:Lnet/minecraft/block/Block;"
            )
    )
    private Block cog$replaceCobblestoneWithRandomBlock() {
        return CobbleOreGenerator.getRandomBlockFromConfig();
    }
}
