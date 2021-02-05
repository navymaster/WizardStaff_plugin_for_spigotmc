import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PlayerEnterListener implements Listener {
    @EventHandler
    public void handle_enter(PlayerJoinEvent e){
        if(!WizardStaffMain.player_magics.containsKey(e.getPlayer())) {
            if(WizardStaffMain.debug_mode){
                for(String s: MagicExecutor.MagicList.keySet()){
                    ItemStack is=new ItemStack(Material.STICK);
                    ItemMeta im=is.getItemMeta();
                    List<String> l=new ArrayList<>();
                    l.add(s);
                    im.setLore(l);
                    is.setItemMeta(im);
                    e.getPlayer().getInventory().addItem(is);
                }
            }
            if(WizardStaffMain.FC.get(e.getPlayer().getDisplayName())!=null){
                WizardStaffMain.player_magics.put(e.getPlayer(),(PlayerMagicList) WizardStaffMain.FC.get(e.getPlayer().getDisplayName()));
            }else {
                PlayerMagicList PML=new PlayerMagicList();
                PML.setPlayer_name(e.getPlayer().getDisplayName());
                WizardStaffMain.player_magics.put(e.getPlayer(),PML);
                WizardStaffMain.FC.set(e.getPlayer().getDisplayName(),PML);
            }
        }
    }
}
