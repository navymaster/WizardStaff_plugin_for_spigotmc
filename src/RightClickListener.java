import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class RightClickListener implements Listener {
    @EventHandler
    public void handle_right_click(PlayerInteractEvent e){
        if(e.hasItem()) {
            try {
                List<String> Lore = e.getItem().getItemMeta().getLore();
                for(String s: MagicExecutor.MagicList.keySet()){
                    if (Lore.contains(s)) {
                        MagicExecutor.MagicList.get(s).run(e.getPlayer(), 0);
                    }
                }
            }catch(NullPointerException n){

            }
        }
    }
}
