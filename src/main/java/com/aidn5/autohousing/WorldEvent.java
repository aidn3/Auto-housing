package com.aidn5.housing;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IWorldAccess;

public class WorldEvent implements IWorldAccess {

	@Override
	public void markBlockForUpdate(BlockPos pos) {
		// TODO Auto-generated method stub
		pos.getX();

	}

	@Override
	public void notifyLightSet(BlockPos pos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void playSound(String soundName, double x, double y, double z, float volume, float pitch) {
		// TODO Auto-generated method stub

	}

	@Override
	public void playSoundToNearExcept(EntityPlayer except, String soundName, double x, double y, double z, float volume,
			float pitch) {
		// TODO Auto-generated method stub

	}

	@Override
	public void spawnParticle(int particleID, boolean ignoreRange, double xCoord, double yCoord, double zCoord,
			double xOffset, double yOffset, double zOffset, int... p_180442_15_) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEntityAdded(Entity entityIn) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEntityRemoved(Entity entityIn) {
		// TODO Auto-generated method stub

	}

	@Override
	public void playRecord(String recordName, BlockPos blockPosIn) {
		// TODO Auto-generated method stub

	}

	@Override
	public void broadcastSound(int p_180440_1_, BlockPos p_180440_2_, int p_180440_3_) {
		// TODO Auto-generated method stub

	}

	@Override
	public void playAuxSFX(EntityPlayer player, int sfxType, BlockPos blockPosIn, int p_180439_4_) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress) {
		// TODO Auto-generated method stub

	}

}
