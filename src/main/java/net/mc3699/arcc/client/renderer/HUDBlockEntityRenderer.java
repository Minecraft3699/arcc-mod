package net.mc3699.arcc.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.mc3699.arcc.block.entity.HUDBlockEntity;
import net.mc3699.arcc.block.special.HUDBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class HUDBlockEntityRenderer implements BlockEntityRenderer<HUDBlockEntity> {

    private final BlockEntityRendererProvider.Context context;
    private List<HUDTextElement> elementList = new ArrayList<>();

    public HUDBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {this.context = ctx;}

    private void drawText(String text, float x, float y, float rotation, int color, PoseStack pPoseStack, int pPackedLight, MultiBufferSource pBuffer) {
        Font font = Minecraft.getInstance().font;
        font.drawInBatch(
                text,
                x,
                y,
                color,
                false,
                pPoseStack.last().pose(),
                pBuffer,
                Font.DisplayMode.NORMAL,
                0x00AA00,
                0xF000F0
        );
    }

    @Override
    public void render(HUDBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {

        pPoseStack.pushPose();
        pPoseStack.translate(0.5, 0.65, 0.5);
        Direction dir = pBlockEntity.getBlockState().getValue(HUDBlock.FACING);
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(-180));

        switch (dir)
        {
            case NORTH -> {
                pPoseStack.mulPose(Axis.YP.rotationDegrees(180));
            }

            case EAST -> {
                pPoseStack.mulPose(Axis.YP.rotationDegrees(270));
            }

            case SOUTH -> {
                pPoseStack.mulPose(Axis.YP.rotationDegrees(0));
            }

            case WEST -> {
                pPoseStack.mulPose(Axis.YP.rotationDegrees(90));
            }
        }


        float scale = 0.006f;
        pPoseStack.scale(scale, scale, scale);

        elementList = pBlockEntity.getElementList();

        if(elementList != null && !elementList.isEmpty())
        {
            for(HUDTextElement element: elementList)
            {
                drawText(element.getText(), element.getXPos(), element.getYPos(), 0, element.getColor(), pPoseStack, pPackedLight, pBuffer);
            }
        }

        pPoseStack.popPose();
    }
}
