import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnhanceHandler implements Listener {
    Random r=new Random();
    @EventHandler
    public void handle_enhance(EnchantItemEvent e) {
        int a = r.nextInt() %100;
        if(a<10||WizardStaffMain.debug_mode) {
            ItemStack is = e.getItem();
            List<String> Lore;
            try{
                Lore= is.getItemMeta().getLore();
            }catch (NullPointerException ex){
                Lore= new ArrayList<>();
            }
            ItemMeta im = is.getItemMeta();
            a=r.nextInt()%8;
            switch (a) {
                case 0:
                    Lore.add("FROST_ARMOR");
                    break;
                case 1:
                    Lore.add("FIRE_BALL");
                    break;
                case 2:
                    Lore.add("FLAME_ARROW");
                    break;
                case 3:
                    Lore.add("THUNDER_WAVE");
                    break;
                case 4:
                    Lore.add("SHATTER");
                    break;
                case 5:
                    Lore.add("MAGIC_MISSILE");
                    break;
                case 6:
                    Lore.add("THUNDER_CALLING");
                    break;
                default:
                    Lore.add("HOLD_MONSTER");
                    break;
            }
            im.setLore(Lore);
            is.setItemMeta(im);
        }
    }
}
