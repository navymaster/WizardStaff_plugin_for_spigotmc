import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Objects;

/**
 *插件主类
 * @author navy_master
 * @version 1.0.0
 * @since spigot1.16.5
 * @see org.bukkit.plugin.java.JavaPlugin
 * */
public class WizardStaffMain extends JavaPlugin {
    public static WizardStaffMain only;
    public static HashMap<Player, PlayerMagic> player_magics =new HashMap<>();
    public static FileConfiguration FC;
    public static boolean debug_mode;
    /**
     * 插件启动函数<br>
     * 这会初始化孤例only，建立一个由玩家到其法术冷却时间的映射player_magics<br>
     * 并将插件的配置置为static以便调用，同时也会从中读出是否为debug_mode
     */
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

    /**
     * 插件关闭函数<br>
     * 保存配置文件中有保存需求的项目，然后终止所有插件进程
     */
    @Override
    public void onDisable() {
        saveConfig();
        Bukkit.getScheduler().cancelTasks(this);
    }
}
