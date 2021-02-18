import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

/**
 * 一个同步进程，作用是让一个实体跟随另一个
 * @author navy_master
 * @version 1.0.0
 * @see org.bukkit.scheduler.BukkitRunnable
 */
public class FollowTask extends BukkitRunnable {

    public FollowTask(LivingEntity tar,Projectile bullet){
        this.bullet=bullet;
        this.tar=tar;
    }
    LivingEntity tar;
    Projectile bullet;
    @Override
    public void run() {
        if((!tar.isDead())&&(!Objects.isNull(bullet))) {
            //Bukkit.getLogger().info("雪球飞行轨道校正");
            bullet.setVelocity((tar.getLocation().clone().add(0,1,0)).subtract(bullet.getLocation()).toVector().multiply(1.0 / bullet.getLocation().distance(tar.getLocation())));
        }else {
            if(!Objects.isNull(bullet)) bullet.setGravity(true);
            this.cancel();
        }
    }
}
