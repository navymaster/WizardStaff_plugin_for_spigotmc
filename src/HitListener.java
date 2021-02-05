import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.Objects;

public class HitListener implements Listener {
    @EventHandler
    public void handle_magic_missile(ProjectileHitEvent e)
    {
        if(Objects.equals(e.getEntity().getCustomName(), "魔法飞弹")){
            if(LivingEntity.class.isAssignableFrom(Objects.requireNonNull(e.getHitEntity()).getClass())) {
                ((LivingEntity) e.getHitEntity()).setNoDamageTicks(0);
                ((LivingEntity) e.getHitEntity()).damage(2, (Entity) e.getEntity().getShooter());
            }
        }
    }
}
