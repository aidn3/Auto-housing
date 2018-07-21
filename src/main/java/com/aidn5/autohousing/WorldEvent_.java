package com.aidn5.autohousing;

import com.aidn5.autohousing.mods.anti_griefer.Main;
import com.aidn5.autohousing.utiles.Utiles;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IWorldAccess;
import net.minecraft.world.World;

public class WorldEvent_ implements IWorldAccess {
	World world;

	public WorldEvent_(World world) {
		this.world = world;
	}

	@Override
	public void markBlockForUpdate(final BlockPos pos) {
		if (!Common.checkHousing()) return;
		String currentBlock = world.getBlockState(pos).getBlock().getRegistryName();
		try {
			if (currentBlock.equals(Block.getBlockById(0).getRegistryName())) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						Main.blockRowListener.Listener(pos.getX(), pos.getY(), pos.getZ());
					}
				}).run();
			}
		} catch (Exception e) {
			Utiles.debug(e);
		}
	}

	@Override
	public void notifyLightSet(BlockPos pos) {}

	@Override
	public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {}

	@Override
	public void playSound(String soundName, double x, double y, double z, float volume, float pitch) {}

	@Override
	public void playSoundToNearExcept(EntityPlayer except, String soundName, double x, double y, double z, float volume,
			float pitch) {}

	@Override
	public void spawnParticle(int particleID, boolean ignoreRange, double xCoord, double yCoord, double zCoord,
			double xOffset, double yOffset, double zOffset, int... p_180442_15_) {}

	@Override
	public void onEntityAdded(Entity entityIn) {}

	@Override
	public void onEntityRemoved(Entity entityIn) {}

	@Override
	public void playRecord(String recordName, BlockPos blockPosIn) {}

	@Override
	public void broadcastSound(int p_180440_1_, BlockPos p_180440_2_, int p_180440_3_) {}

	@Override
	public void playAuxSFX(EntityPlayer player, int sfxType, BlockPos blockPosIn, int p_180439_4_) {}

	@Override
	public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress) {}

}
