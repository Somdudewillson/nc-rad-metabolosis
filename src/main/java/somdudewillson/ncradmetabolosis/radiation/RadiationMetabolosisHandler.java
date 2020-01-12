package somdudewillson.ncradmetabolosis.radiation;

import static nc.config.NCConfig.radiation_player_tick_rate;
import nc.capability.radiation.entity.IEntityRads;
import nc.config.NCConfig;
import nc.network.PacketHandler;
import nc.network.radiation.PlayerRadsUpdatePacket;
import nc.radiation.RadiationHelper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.FoodStats;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import somdudewillson.ncradmetabolosis.config.NCRMConfig;

public class RadiationMetabolosisHandler {
	
	@SubscribeEvent(priority=EventPriority.LOW)
	public void applyEnvironmentalRadiation(TickEvent.PlayerTickEvent event) {
		
		if (!NCConfig.radiation_enabled_public) { return; }
		
		if (event.phase != TickEvent.Phase.START ||
				((event.player.world.getTotalWorldTime() + event.player.getUniqueID().hashCode()) % radiation_player_tick_rate) != 0) { return; }
		
		if (event.side == Side.SERVER && event.player instanceof EntityPlayerMP) {			
			EntityPlayerMP player = (EntityPlayerMP)event.player;
			FoodStats food = player.getFoodStats();
			
			//General hunger level check
			if (food.getFoodLevel() >= NCRMConfig.min_food) {
				IEntityRads playerRads = RadiationHelper.getEntityRadiation(player);
				//General rad level check
				if (!playerRads.isTotalRadsNegligible()) {
					
					//-----Saturation dissipation
					if (food.getSaturationLevel() > 0.0f &&
							NCRMConfig.saturation_dissipation &&
							NCRMConfig.saturation_max_dissipation > 0.0) {
						double maxUnitsForFood = food.getSaturationLevel();
						double maxUnitsForTicks = (NCRMConfig.saturation_max_dissipation*radiation_player_tick_rate)/
								NCRMConfig.rads_per_saturation;
						double maxUnitsForRads = (playerRads.getTotalRads()/NCRMConfig.rads_per_saturation);
						int appliedUnits = (int) Math.floor(Math.min(Math.min(maxUnitsForFood,
								maxUnitsForTicks), maxUnitsForRads));
						
						food.setFoodSaturationLevel(food.getSaturationLevel()-appliedUnits);
						
						double dissipatedRads = -(appliedUnits*NCRMConfig.rads_per_saturation);
						playerRads.setTotalRads(Math.max(playerRads.getTotalRads()-dissipatedRads, 0.0), false);
						playerRads.setRadiationLevel(playerRads.getRadiationLevel()-dissipatedRads);
					} else
						//-----Hunger dissipation
						if (NCRMConfig.passive_dissipation_amount > 0) {
							double maxUnitsForFood = (food.getFoodLevel()-NCRMConfig.min_food)/(NCRMConfig.exhaustion_cost/4.0)+1;
						double maxUnitsForTicks = radiation_player_tick_rate;
						double maxUnitsForRads = (playerRads.getTotalRads()/NCRMConfig.passive_dissipation_amount);
						int appliedUnits = (int) Math.floor(Math.min(Math.min(maxUnitsForFood,
								maxUnitsForTicks), maxUnitsForRads));
						
						food.addExhaustion((float) (appliedUnits*NCRMConfig.exhaustion_cost));
						
						double dissipatedRads = (appliedUnits*NCRMConfig.passive_dissipation_amount);
						playerRads.setTotalRads(Math.max(playerRads.getTotalRads()-dissipatedRads, 0.0), false);
						playerRads.setRadiationLevel(playerRads.getRadiationLevel()-dissipatedRads);
					}
					//-----
					
					PacketHandler.instance.sendTo(new PlayerRadsUpdatePacket(playerRads), player);
				}
			}
			
		}
	}
}
