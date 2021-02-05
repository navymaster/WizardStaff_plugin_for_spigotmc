import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashSet;
import java.util.Set;

public class PlayerDamageListener implements Listener {
    public static Set<LivingEntity> watchlist=new HashSet<>();
    @EventHandler
    public void handle(EntityDamageByEntityEvent e){
        if (LivingEntity.class.isAssignableFrom(e.getEntity().getClass())) {
            LivingEntity entity = (LivingEntity) e.getEntity();
            if (watchlist.contains(entity)) {
                if (LivingEntity.class.isAssignableFrom(e.getDamager().getClass())) {
                    ((LivingEntity) e.getDamager()).damage(4, e.getEntity());
                }
            }
        }
    }
}
