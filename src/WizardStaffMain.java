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
        //ConfigurationSerialization.registerClass(PlayerMagicList.class);
        PlayerHandBook phb=new PlayerHandBook();
        Objects.requireNonNull(Bukkit.getPluginCommand("playerhandbook")).setExecutor(phb);
        Objects.requireNonNull(Bukkit.getPluginCommand("setplayerhandbook")).setExecutor(phb);
        Bukkit.getPluginManager().registerEvents(new PlayerDamageListener(),this);
        Bukkit.getPluginManager().registerEvents(new WizardStaffListener(),this);
        saveDefaultConfig();
        FC=getConfig();
        if(Objects.isNull(FC.get("Debug_Mode"))){
            FC.set("Debug_Mode",false);
        }
        debug_mode=(boolean)FC.get("Debug_Mode");

        PlayerHandBook.setPhb((ItemStack) FC.get("phbdata"));

        if(Objects.isNull(FC.get("MagicALL"))){
            FC.set("MagicALL",true);
        }
        if((boolean)FC.get("MagicALL"))
        MagicExecutor.register_default();
    }
    @Override
    public void onDisable() {
        saveConfig();
        Bukkit.getScheduler().cancelTasks(this);
    }
}
