import com.sun.org.apache.xml.internal.security.keys.content.MgmtData;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.image.LookupOp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static java.lang.Math.abs;

public class EnhanceHandler implements Listener {
    Random r=new Random();
    @EventHandler
    public void handle_enhance(EnchantItemEvent e) {
        int a = r.nextInt(100);
        if(a<25||WizardStaffMain.debug_mode) {
            ItemStack is = e.getItem();
            List<String> Lore;
            try{
                Lore= is.getItemMeta().getLore();
                if(Objects.isNull(Lore)){
                    Lore= new ArrayList<>();
                }
            }catch (NullPointerException ex){
                Lore= new ArrayList<>();
            }
            ItemMeta im = is.getItemMeta();
            a=r.nextInt(MagicExecutor.enhance_registered);
            int i=0;
            if(WizardStaffMain.debug_mode)
                Bukkit.getLogger().info(a+" "+MagicExecutor.enhance_registered);
            for(String s:MagicExecutor.MagicList.keySet()){
                if(MagicExecutor.MagicList.get(s).isEnhanceable())
                if(a==i++){
                    Bukkit.getLogger().info(s);
                    Lore.add(s);
                    break;
                }
            }
            im.setLore(Lore);
            is.setItemMeta(im);
        }
    }
}
