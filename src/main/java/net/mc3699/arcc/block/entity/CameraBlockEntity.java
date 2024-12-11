package net.mc3699.arcc.block.entity;

import net.mc3699.arcc.block.ModBlockEntities;
import net.mc3699.arcc.block.special.CameraBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class CameraBlockEntity extends BlockEntity {

    private static final int GRID_SIZE = 16; // Resolution of the "camera view"
    private final int[][] pixelGrid = new int[GRID_SIZE][GRID_SIZE]; // Stores pixel colors

    public CameraBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CAMERA_BLOCK_ENTITY.get(), pos, state);
    }

    /**
     * Updates the pixel grid by performing raycasts and sampling block textures.
     */
    public void updateView() {
        if (level == null) return;

        Vec3 cameraPos = Vec3.atCenterOf(worldPosition); // Position of the camera
        Direction direction = getBlockState().getValue(CameraBlock.FACING);

        Vec3 forward = Vec3.atLowerCornerOf(direction.getNormal());

        // Define the field of view (FOV) and grid size
        float fov = 60f; // Degrees
        float aspectRatio = 1f; // Square grid
        float halfFovRad = (float) Math.toRadians(fov / 2);
        float pixelStep = (float) Math.tan(halfFovRad) * 2 / GRID_SIZE;

        // Loop through the grid to perform raycasts
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                float offsetX = (x - GRID_SIZE / 2f) * pixelStep;
                float offsetY = (y - GRID_SIZE / 2f) * pixelStep;

                // Calculate the ray direction for this pixel
                Vec3 rayDirection = forward.add(offsetX, -offsetY, 0).normalize();

                // Perform the raycast
                HitResult hitResult = level.clip(new ClipContext(
                        cameraPos,
                        cameraPos.add(rayDirection.scale(50)), // Max ray distance
                        ClipContext.Block.OUTLINE,
                        ClipContext.Fluid.NONE,
                        null
                ));

                if (hitResult != null && hitResult.getType() == HitResult.Type.BLOCK) {
                    BlockHitResult blockHitResult = (BlockHitResult) hitResult;
                    BlockPos hitBlockPos = blockHitResult.getBlockPos();
                    Direction hitFace = blockHitResult.getDirection();
                    Vec3 hitLocation = blockHitResult.getLocation();

                    // Get the block state at the hit position
                    BlockState blockState = level.getBlockState(hitBlockPos);

                    if (blockState.getBlock() != Blocks.AIR) {
                        // Get the relative hit position on the block face
                        Vec3 relativeHit = hitLocation.subtract(Vec3.atLowerCornerOf(hitBlockPos));

                        // Calculate UV coordinates
                        float u = 0, v = 0;
                        switch (hitFace) {
                            case NORTH, SOUTH -> {
                                u = (float) relativeHit.x;
                                v = (float) relativeHit.y;
                            }
                            case EAST, WEST -> {
                                u = (float) relativeHit.z;
                                v = (float) relativeHit.y;
                            }
                            case UP, DOWN -> {
                                u = (float) relativeHit.x;
                                v = (float) relativeHit.z;
                            }
                        }

                        // Get the pixel color at the UV position
                        int color = getColorFromTexture(blockState, hitFace, u, v);
                        pixelGrid[y][x] = color;
                    } else {
                        pixelGrid[y][x] = 0x000000; // Default black for air
                    }
                } else {
                    pixelGrid[y][x] = 0x000000; // Default black for no hit
                }
            }
        }
    }

    /**
     * Gets the color of the texture at the given UV coordinates.
     *
     * @param blockState The block's state.
     * @param face       The face of the block hit.
     * @param u          The U-coordinate (0-1) on the texture.
     * @param v          The V-coordinate (0-1) on the texture.
     * @return The color as an ARGB integer.
     */
    private int getColorFromTexture(BlockState blockState, Direction face, float u, float v) {
        var blockAtlas = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS);
        var sprite = Minecraft.getInstance().getBlockRenderer().getBlockModelShaper()
                .getTexture(blockState, level, worldPosition);

        if (sprite == null) {
            return 0x000000; // Default black for missing texture
        }

        // Convert UV to pixel coordinates
        int textureWidth = sprite.getX();
        int textureHeight = sprite.getY();
        int pixelX = Math.min((int) (u * textureWidth), textureWidth - 1);
        int pixelY = Math.min((int) (v * textureHeight), textureHeight - 1);

        // Get pixel color from the texture
        return sprite.getPixelRGBA(0, pixelX, pixelY);
    }

    public int[][] getPixelGrid() {
        return pixelGrid;
    }
}
