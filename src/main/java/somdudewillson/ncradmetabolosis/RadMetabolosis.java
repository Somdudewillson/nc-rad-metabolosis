package somdudewillson.ncradmetabolosis;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.Logger;

import somdudewillson.ncradmetabolosis.proxy.CommonProxy;

@Mod(modid = RadMetabolosis.MODID, name = RadMetabolosis.NAME, version = RadMetabolosis.VERSION)
public class RadMetabolosis
{
    public static final String MODID = "ncradmetabolosis";
    public static final String NAME = "Rad Metabolosis : NuclearCraft Addon";
    public static final String VERSION = "1.12.2-1.0.0.0";
    /* MCVERSION-MAJORMOD.MAJORAPI.MINOR.PATCH
     * 		MCVERSION
	 * Always matches the Minecraft version the mod is for.
	 * 		MAJORMOD
	 * Removing items, blocks, tile entities, etc.
	 * Changing or removing previously existing mechanics.
	 * Updating to a new Minecraft version.
	 * 		MAJORAPI
	 * Changing the order or variables of enums.
	 * Changing return types of methods.
	 * Removing public methods altogether.
	 * 		MINOR
	 * Adding items, blocks, tile entities, etc.
	 * Adding new mechanics.
	 * Deprecating public methods. (This is not a MAJORAPI increment since it doesn�t break an API.)
	 * 		PATCH
	 * Bugfixes.
     */
    
    
	public static final String CLIENT_PROXY = "main.java.somdudewillson.ncenvironmentalrads.proxy.ClientProxy";
	public static final String COMMON_PROXY = "main.java.somdudewillson.ncenvironmentalrads.proxy.CommonProxy";
    
    @Instance
    public static RadMetabolosis instance = new RadMetabolosis();
    
	@SidedProxy(clientSide = CLIENT_PROXY, serverSide = COMMON_PROXY)
	public static CommonProxy proxy;

    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
    }
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent postEvent) {
		logger.info("Postinit Started...");
		proxy.setLogger(logger);
		proxy.postInit(postEvent);
	}
}
