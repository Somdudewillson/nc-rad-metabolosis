package somdudewillson.ncradmetabolosis.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import somdudewillson.ncradmetabolosis.RadMetabolosis;

@Config(modid = RadMetabolosis.MODID, name = "NCRadMetabolosis")
public class NCRMConfig {
	
	//Global Settings
	@Config.Comment("The amount of rads which are dissipated per tick")
	public static double passive_dissipation_amount = 5/60;
	
	@Config.Comment({"Exhaustion per tick to dissipate rads.",
			"4 exhaustion ~= 1 unit of food."})
	public static double exhaustion_cost = 1;
	
	@Config.Comment("Minimum food bars required for rad dissipation.")
	public static int min_food = 1;
	
	@Config.Comment("If accelerated rad dissipation by consuming saturation is enabled.")
	public static boolean saturation_dissipation = false;
	
	@Config.Comment("The max amount of rads which are dissipated per tick with saturation.")
	public static double saturation_max_dissipation = (5/60)*6;
	
	@Config.Comment("Max saturation consumed per tick to dissipate rads.")
	public static double saturation_cost = 6;
	
	@Config.Ignore
	public static double rads_per_saturation;
	
	@Mod.EventBusSubscriber(modid = RadMetabolosis.MODID)
	private static class EventHandler {

		/**
		 * Inject the new values and save to the config file when the config has been changed from the GUI.
		 *
		 * @param event The event
		 */
		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(RadMetabolosis.MODID)) {
				updateCalculatedValues();
				ConfigManager.sync(RadMetabolosis.MODID, Config.Type.INSTANCE);
			}
		}
	}
	
	public static void updateCalculatedValues() {
		rads_per_saturation = saturation_max_dissipation/saturation_cost;
	}
}
