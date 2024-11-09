package net.mc3699.arcc.block.special;

import net.mc3699.arcc.block.entity.ARTerminalBlockEntity;
import net.mc3699.arcc.network.ModNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.Nullable;

public class ARTerminalBlock extends Block implements EntityBlock {

    private final ModNetworking networking;

    public ARTerminalBlock(Properties pProperties, ModNetworking networking) {
        super(pProperties);
        this.networking = networking;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ARTerminalBlockEntity(blockPos,blockState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
    }
}
