import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

public class FrostArmorTask extends BukkitRunnable {
    public FrostArmorTask(LivingEntity e){
        watch=e;
    }
    LivingEntity watch;
    @Override
    public void run() {
        if(watch.getAbsorptionAmount()==0){
            PlayerDamageListener.watchlist.remove(watch);
            this.cancel();
        }else{
            watch.getWorld().spawnParticle(Particle.SNOWBALL,watch.getLocation(),30,2,3,2);
        }
    }
}
