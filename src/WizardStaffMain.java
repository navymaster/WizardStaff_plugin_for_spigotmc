import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Objects;


public class WizardStaffMain extends JavaPlugin {
    public static WizardStaffMain only;
    public static HashMap<Player,PlayerMagicList> player_magics =new HashMap<>();
    public static FileConfiguration FC;
    public static boolean debug_mode;
    @Override
    public void onEnable() {
        only=this;
        MagicExecutor.register_default();
        ConfigurationSerialization.registerClass(PlayerMagicList.class);
        MagicLearnCommand MLC=new MagicLearnCommand();
        Objects.requireNonNull(Bukkit.getPluginCommand("learnmagic")).setExecutor(MLC);
        Objects.requireNonNull(Bukkit.getPluginCommand("playerhandbook")).setExecutor(new PlayerHandBook());
        Objects.requireNonNull(Bukkit.getPluginCommand("setplayerhandbook")).setExecutor(new PHBSetter());
        Objects.requireNonNull(Bukkit.getPluginCommand("learnmagic")).setTabCompleter(MLC);
        Bukkit.getPluginManager().registerEvents(new PlayerEnterListener(),this);
        Bukkit.getPluginManager().registerEvents(new ShowMagic(),this);
        Bukkit.getPluginManager().registerEvents(new StorageMagicListener(),this);
        Bukkit.getPluginManager().registerEvents(new RightClickListener(),this);
        Bukkit.getPluginManager().registerEvents(new HitListener(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerDamageListener(),this);
        Bukkit.getPluginManager().registerEvents(new EnhanceHandler(),this);
        saveDefaultConfig();
        FC=getConfig();
        try{
        debug_mode=(boolean)FC.get("Debug_Mode");
        }
        catch (NullPointerException e){
            debug_mode=false;
        }
        PlayerHandBook.setPhb((ItemStack) FC.get("phbdata"));
    }
    @Override
    public void onDisable() {
        saveConfig();
        Bukkit.getScheduler().cancelTasks(this);
    }
}
