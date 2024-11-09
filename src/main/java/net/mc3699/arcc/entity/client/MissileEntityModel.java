package net.mc3699.arcc.entity.client;// Made with Blockbench 4.10.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class MissileEntityModel<T extends Entity> extends EntityModel<T> {

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -53.0F, -4.0F, 8.0F, 46.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(32, 13).addBox(-3.0F, -7.0F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(50, 13).addBox(-3.0F, -4.0F, -4.0F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(50, 0).addBox(-3.0F, -4.0F, 3.0F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(32, 0).addBox(-3.0F, -60.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(32, 22).addBox(-2.0F, -63.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(10, 54).addBox(-2.0F, -8.0F, -1.0F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 54).addBox(-4.0F, 0.0F, -1.0F, 3.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(44, 39).addBox(-5.0F, 8.0F, -1.0F, 4.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -23.0F, -3.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r2 = bb_main.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(16, 54).addBox(-2.0F, -8.0F, -1.0F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(32, 29).addBox(-5.0F, 8.0F, -1.0F, 4.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(32, 49).addBox(-4.0F, 0.0F, -1.0F, 3.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -23.0F, -3.0F, 0.0F, -2.3562F, 0.0F));

		PartDefinition cube_r3 = bb_main.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(56, 27).addBox(-2.3F, -8.0F, -1.0F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(42, 49).addBox(-4.3F, 0.0F, -1.0F, 3.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(32, 39).addBox(-5.3F, 8.0F, -1.0F, 4.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -23.0F, 2.9F, 0.0F, 2.5045F, 0.0F));

		PartDefinition cube_r4 = bb_main.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(22, 54).addBox(-2.0F, -8.0F, -1.0F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(44, 29).addBox(-5.0F, 8.0F, -1.0F, 4.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(52, 49).addBox(-4.0F, 0.0F, -1.0F, 3.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -23.0F, 3.15F, 0.0F, 0.6981F, 0.0F));

		PartDefinition cube_r5 = bb_main.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(24, 0).addBox(-3.0F, -2.0F, -4.0F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, -2.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r6 = bb_main.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(48, 22).addBox(-3.0F, -2.0F, -4.0F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
	}
}