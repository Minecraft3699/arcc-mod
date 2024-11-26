package net.mc3699.arcc.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.mc3699.arcc.block.entity.HUDBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

import java.util.List;

public class HUDBlockEntityRenderer implements BlockEntityRenderer<HUDBlockEntity> {

    private final BlockEntityRendererProvider.Context context;

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
                pPackedLight
        );
    }

    @Override
    public void render(HUDBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();

        pPoseStack.translate(0.5, 0.65, 0.5);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(-90));
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(-180));

        float scale = 0.006f;
        pPoseStack.scale(scale, scale, scale);

        List<HUDTextElement> elementList = pBlockEntity.getElementList();

        if(elementList != null && !elementList.isEmpty())
        {
            for(HUDTextElement element: elementList)
            {
                drawText(element.getText(), element.getXPos(), 0, element.getYPos(), element.getColor(), pPoseStack, pPackedLight, pBuffer);
            }
        }

        pPoseStack.popPose();
    }
}
