package somdudewillson.ncradmetabolosis.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import org.apache.logging.log4j.Logger;

import somdudewillson.ncradmetabolosis.RadMetabolosis;
import somdudewillson.ncradmetabolosis.config.NCRMConfig;
import somdudewillson.ncradmetabolosis.radiation.RadiationMetabolosisHandler;

public class CommonProxy {
	@SuppressWarnings("unused")
	private Logger log = null;
	
	public void postInit(FMLPostInitializationEvent postEvent) {
		//Make sure config data is up-to-date
		NCRMConfig.updateCalculatedValues();
		ConfigManager.sync(RadMetabolosis.MODID, Config.Type.INSTANCE);
		
		//Register handler
		MinecraftForge.EVENT_BUS.register(new RadiationMetabolosisHandler());
	}

	public void setLogger(Logger logger) {
		this.log = logger;
	}

}