package net.mc3699.arcc.block.special;

import net.mc3699.arcc.block.entity.ARControllerBlockEntity;
import net.mc3699.arcc.item.ARHeadset;
import net.mc3699.arcc.network.ModNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class ARControllerBlock extends Block implements EntityBlock {

    private final ModNetworking networking;

    public ARControllerBlock(Properties pProperties, ModNetworking networking) {
        super(pProperties);
        this.networking = networking;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

        ItemStack heldItem = pPlayer.getItemInHand(pHand);

        if(!pLevel.isClientSide())
        {
            if(heldItem.getItem() instanceof ARHeadset)
            {
                ARControllerBlockEntity blockEntity = (ARControllerBlockEntity) pLevel.getBlockEntity(pPos);

                CompoundTag tag = heldItem.getOrCreateTag();
                tag.putString("controller_id", blockEntity.controllerID);
                return InteractionResult.SUCCESS;
            }
        }


        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ARControllerBlockEntity(blockPos,blockState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
    }
}
