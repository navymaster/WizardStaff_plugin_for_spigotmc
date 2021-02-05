import org.bukkit.*;
import org.bukkit.block.Skull;
import org.bukkit.entity.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;
public class MagicExecutor {
    public static HashMap<String,MagicExecutor> MagicList=new HashMap<>();
    public static void register_magic(String name,MagicExecutor m){
        MagicList.put(name,m);
    }
    public static void register_default(){
        //火球
        MagicExecutor FIRE_BALL=new MagicExecutor(){
            @Override
            public boolean runMagic(LivingEntity Caster){
                World w=Caster.getWorld();
                Location loc=Caster.getLocation();
                loc.add(0,1,0);
                Fireball fb=(Fireball)w.spawnEntity(loc, EntityType.FIREBALL);
                fb.setIsIncendiary(true);
                fb.setShooter(Caster);
                return true;
            }
        };
        register_magic("FIRE_BALL",FIRE_BALL);
        //火焰箭
        MagicExecutor FLAME_ARROW=new MagicExecutor(){
            @Override
            public boolean runMagic(LivingEntity Caster){
                World w = Caster.getWorld();
                Location loc = Caster.getLocation();
                loc.add(0, 2, 0);
                Arrow ar = w.spawnArrow(loc, loc.getDirection(), (float) 0.6, 60);
                ar.setFireTicks(1200);
                ar.setShooter(Caster);
                return true;
            }
        };
        register_magic("FLAME_ARROW",FLAME_ARROW);
        //雷鸣波
        MagicExecutor THUNDER_WAVE=new MagicExecutor(){
            @Override
            public boolean runMagic(LivingEntity Caster){
                Location loc=Caster.getLocation();
                List<Entity> al=Caster.getNearbyEntities(9,9,9);
                Vector ln;
                for(Entity en:al)
                {

                    ln=en.getLocation().toVector().subtract(loc.toVector());
                    en.getVelocity().add(ln.multiply(6.0/en.getLocation().distance(loc)));
                    if(LivingEntity.class.isAssignableFrom(en.getClass())){
                        ((LivingEntity)en).damage(5,Caster);
                    }
                }
                Caster.getWorld().playSound(Caster.getLocation(), Sound.ENTITY_GENERIC_EXPLODE,2F,0F);
                return true;
            }
        };
        register_magic("THUNDER_WAVE",THUNDER_WAVE);
        //粉碎音波
        MagicExecutor SHATTER=new MagicExecutor(){
            @Override
            public boolean runMagic(LivingEntity Caster){
                World w=Caster.getWorld();
                try{
                    Location loc = Caster.rayTraceBlocks(36, FluidCollisionMode.NEVER).getHitBlock().getLocation();
                    Collection<Entity> al = w.getNearbyEntities(loc,6, 6, 6);
                    Vector ln;
                    for (Entity en : al) {
                        ln = en.getLocation().toVector().subtract(loc.toVector());
                        en.getVelocity().add(ln.multiply(6.0 / en.getLocation().distance(loc)));
                        if (LivingEntity.class.isAssignableFrom(en.getClass())) {
                            ((LivingEntity) en).damage(7, Caster);
                        }
                    }
                    Caster.getWorld().playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 2F, 0F);
                }catch (NullPointerException n){
                    return false;
                }
                return true;
            }
        };
        register_magic("SHATTER",SHATTER);
        //魔法飞弹
        MagicExecutor MAGIC_MISSILE=new MagicExecutor(){
            @Override
            public boolean runMagic(LivingEntity Caster){
                World w = Caster.getWorld();
                Location loc = Caster.getLocation();
                try{
                    Entity tar= Objects.requireNonNull(w.rayTraceEntities(loc, loc.getDirection(), 72)).getHitEntity();
                    if(tar!=Caster&&LivingEntity.class.isAssignableFrom(tar.getClass())) {
                        loc.add(1, 2, 0);
                        FollowTask FT;
                        Snowball a;
                        Vector ln;
                        a = (Snowball) w.spawnEntity(loc, EntityType.SNOWBALL);
                        ln = (tar.getLocation().clone().add(0, 1, 0)).toVector().subtract(a.getLocation().toVector());
                        a.setVelocity(ln.multiply(1.0 / a.getLocation().distance(tar.getLocation())));//
                        a.setCustomName("魔法飞弹");
                        a.setTicksLived(300);
                        a.setGravity(false);
                        a.setShooter(Caster);
                        FT = new FollowTask((LivingEntity)tar,a);
                        FT.runTaskTimer(WizardStaffMain.only, 1, 1);
                        loc.add(-2, 0, 1);
                        a = (Snowball) w.spawnEntity(loc, EntityType.SNOWBALL);
                        ln = (tar.getLocation().clone().add(0, 1, 0)).toVector().subtract(a.getLocation().toVector());
                        a.setVelocity(ln.multiply(1.0 / a.getLocation().distance(tar.getLocation())));//
                        a.setCustomName("魔法飞弹");
                        a.setTicksLived(300);
                        a.setGravity(false);
                        a.setShooter(Caster);
                        FT = new FollowTask((LivingEntity)tar,a);
                        FT.runTaskTimer(WizardStaffMain.only, 1, 1);
                        loc.add(0, 0, -2);
                        a = (Snowball) w.spawnEntity(loc, EntityType.SNOWBALL);
                        ln = (tar.getLocation().clone().add(0, 1, 0)).toVector().subtract(a.getLocation().toVector());
                        a.setVelocity(ln.multiply(1.0 / a.getLocation().distance(tar.getLocation())));//
                        a.setCustomName("魔法飞弹");
                        a.setTicksLived(300);
                        a.setGravity(false);
                        a.setShooter(Caster);
                        FT = new FollowTask((LivingEntity)tar,a);
                        FT.runTaskTimer(WizardStaffMain.only, 1, 1);
                    }
                }catch (NullPointerException n){
                    return false;
                }
                return true;
            }
        };
        register_magic("MAGIC_MISSILE",MAGIC_MISSILE);
        //冰铠
        MagicExecutor FROST_ARMOR=new MagicExecutor(){
            @Override
            public boolean runMagic(LivingEntity Caster){
                PlayerDamageListener.watchlist.add(Caster);
                Caster.setAbsorptionAmount(Caster.getAbsorptionAmount()+4);
                FrostArmorTask FAT=new FrostArmorTask(Caster);
                FAT.runTaskTimer(WizardStaffMain.only,1,1);
                return true;
            }
        };
        register_magic("FROST_ARMOR",FROST_ARMOR);
        //召雷术
        MagicExecutor THUNDER_CALLING=new MagicExecutor(){
            @Override
            public boolean runMagic(LivingEntity Caster){
                try {
                    Location loc = Caster.rayTraceBlocks(36, FluidCollisionMode.NEVER).getHitBlock().getLocation();
                    Caster.getWorld().strikeLightning(loc);
                }catch (NullPointerException n){
                    return false;
                }
                return true;
            }
        };
        register_magic("THUNDER_CALLING",THUNDER_CALLING);
        //怪物定身术
        MagicExecutor HOLD_MONSTER=new MagicExecutor(){
            @Override
            public boolean runMagic(LivingEntity Caster){
                World w = Caster.getWorld();
                Location loc = Caster.getLocation();
                try{
                    Entity tar= Objects.requireNonNull(w.rayTraceEntities(loc, loc.getDirection(), 72)).getHitEntity();
                    if(tar!=Caster&&LivingEntity.class.isAssignableFrom(tar.getClass())) {
                        ((LivingEntity)tar).setAI(false);
                        new BukkitRunnable(){
                            int count=60;
                            @Override
                            public void run(){
                                if(count>0)
                                {
                                    count--;
                                }
                                else{
                                    ((LivingEntity) tar).setAI(true);
                                    this.cancel();
                                }
                            }
                        }.runTaskTimer(WizardStaffMain.only,1,1);
                    }
                }catch (NullPointerException n){
                    return false;
                }
                return true;
            }
        };
        register_magic("HOLD_MONSTER",HOLD_MONSTER);
        //传送术
        MagicExecutor TELEPORT=new MagicExecutor(){
            @Override
            public boolean runMagic(LivingEntity Caster){
                if(Player.class.isAssignableFrom(Caster.getClass())) {
                    Inventory inven=Bukkit.createInventory((Player)Caster,9*6,"teleport_target");
                    List<Entity> l=Caster.getWorld().getEntities();
                    List<LivingEntity> choice=new ArrayList<>();
                    for(Entity e:l){
                        if(LivingEntity.class.isAssignableFrom(e.getClass())){
                            choice.add((LivingEntity) e);
                        }
                    }
                    int i=0;
                    ItemStack is;
                    SkullMeta im;
                    for(LivingEntity e:choice){
                        if(i>=45)break;
                        im=(SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_BANNER_PATTERN);
                        im.setOwningPlayer((OfflinePlayer)e);
                        is=new ItemStack(Material.SKULL_BANNER_PATTERN);
                        is.setItemMeta(im);
                        inven.setItem(i++,is);
                    }
                    return true;
                }else return false;
            }
        };
        register_magic("TELEPORT",TELEPORT);
    }
    public boolean runMagic(LivingEntity Caster){return true;}
    public void run(LivingEntity Caster,int cold_time){
        if(Player.class.isAssignableFrom(Caster.getClass())) {
            PlayerMagicList PML = WizardStaffMain.player_magics.get((Player) Caster);
            if (PML.cooldown == 0) {
                boolean suc = runMagic(Caster);
                if (suc) {
                    PML.cooldown = cold_time;
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (PML.cooldown > 0) {
                                PML.cooldown--;
                            } else {
                                this.cancel();
                            }
                        }
                    }.runTaskTimer(WizardStaffMain.only, 1, 1);
                }
            }
        }else{
            Bukkit.getLogger().info("错误的run函数对象：非玩家实体调用了含有冷却时长的执行器run函数");
        }
    }
    public void run(LivingEntity Caster){
        runMagic(Caster);
    }
}