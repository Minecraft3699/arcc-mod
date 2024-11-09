package net.mc3699.arcc.block.special;

import net.mc3699.arcc.block.entity.ARControllerBlockEntity;
import net.mc3699.arcc.block.entity.CellularAPBlockEntity;
import net.mc3699.arcc.item.ARHeadset;
import net.mc3699.arcc.item.PagerItem;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class CellularAPBlock extends Block implements EntityBlock {
    public CellularAPBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CellularAPBlockEntity(pPos, pState);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {


        ItemStack heldItem = pPlayer.getItemInHand(pHand);

        if(!pLevel.isClientSide())
        {
            if(heldItem.getItem() instanceof PagerItem)
            {
                CellularAPBlockEntity blockEntity = (CellularAPBlockEntity) pLevel.getBlockEntity(pPos);

                CompoundTag tag = heldItem.getOrCreateTag();
                tag.putInt("phone_number", blockEntity.phoneNumberAssign);

                tag.putInt("cell_x", pPos.getX());
                tag.putInt("cell_y", pPos.getY());
                tag.putInt("cell_z", pPos.getZ());

                Minecraft.getInstance().player.playSound(SoundEvents.LODESTONE_COMPASS_LOCK, 1.0f, 1.0f);
                return InteractionResult.SUCCESS;
            }
        }


        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
}
