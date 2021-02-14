import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

/**
 * 法术执行器类
 * 用于方便地注册新的法术
 * @author navy_master
 * @version 1.0.0
 */
public class MagicExecutor {
    public static HashMap<String,MagicExecutor> MagicList=new HashMap<>();
    public static int enhance_registered=0;
    private final int cold_time;

    public boolean isEnhanceable() {
        return enhance;
    }

    private final boolean enhance;

    /**
     * 注册一个新的法术
     * @param name 法术的名称
     * @param m 法术的执行器
     */
    public static void register_magic(String name,MagicExecutor m){
        MagicList.put(name,m);
    }

    /**
     * 法术执行器的构造函数
     * @param c 冷却时间
     */
    @Deprecated
    public MagicExecutor(int c){
        cold_time=c;
        enhance=false;
    }

    /**
     * 法术执行器的构造函数
     * @param c 冷却时间
     * @param whenEnhance 是否在附魔时考虑（如果希望仅在自行设置的情况下获得法术词缀，则false）
     */
    public MagicExecutor(int c,boolean whenEnhance){
        cold_time=c;
        enhance=whenEnhance;
        if(whenEnhance)enhance_registered++;
    }

    /**
     * 法术执行函数，如果新建法术，必须重载此函数
     * @param Caster 施法者
     * @return 施法成功与否
     */
    public boolean runMagic(LivingEntity Caster){return true;}

    /**
     * 包装过的施法执行函数，判断完冷却时间后，会执行runMagic函数
     * @param Caster 施法者
     */
    public void run(LivingEntity Caster){
        if(Player.class.isAssignableFrom(Caster.getClass())) {
            PlayerMagic PML = WizardStaffMain.player_magics.get((Player) Caster);
            if (PML.cool_time == 0) {
                boolean suc = runMagic(Caster);
                if (suc) {
                    PML.cool_time = cold_time;
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (PML.cool_time > 0) {
                                PML.cool_time--;
                            } else {
                                this.cancel();
                            }
                        }
                    }.runTaskTimer(WizardStaffMain.only, 1, 1);
                }
            }
        }else{
            runMagic(Caster);
        }
    }

    /**
     * 注册默认法术的函数
     */
    public static void register_default(){
        boolean install;
        Map<String,Boolean> defualt_magic;
        defualt_magic=new HashMap<>();
        if(!Objects.isNull(WizardStaffMain.FC.get("Magic"))){
            ConfigurationSection cs =WizardStaffMain.FC.getConfigurationSection("Magic");
            //defualt_magic=(Map<String,Boolean>)WizardStaffMain.FC.get("Magic");
            //defualt_magic=new HashMap<>();
            for(String key : cs.getKeys(false)) {
                defualt_magic.put(key,(Boolean) cs.get(key));
                //System.out.println(key + " = " + cs.get(key));
            }
        }

        if(defualt_magic.containsKey("FIRE_BALL")) {
            install=defualt_magic.get("FIRE_BALL");
        }else{
            install=true;
            defualt_magic.put("FIRE_BALL",true);
        }

        if(install) {
            //火球
            MagicExecutor FIRE_BALL = new MagicExecutor(20, true) {
                @Override
                public boolean runMagic(LivingEntity Caster) {
                    World w = Caster.getWorld();
                    Location loc = Caster.getLocation();
                    loc.add(0, 1, 0);
                    Fireball fb = (Fireball) w.spawnEntity(loc, EntityType.FIREBALL);
                    fb.setIsIncendiary(true);
                    fb.setShooter(Caster);
                    return true;
                }
            };
            register_magic("FIRE_BALL", FIRE_BALL);
        }

        if(defualt_magic.containsKey("FLAME_ARROW")) {
            install=defualt_magic.get("FLAME_ARROW");
        }else{
            install=true;
            defualt_magic.put("FLAME_ARROW",true);
        }
        if(install) {
            //火焰箭
            MagicExecutor FLAME_ARROW = new MagicExecutor(0, true) {
                @Override
                public boolean runMagic(LivingEntity Caster) {
                    World w = Caster.getWorld();
                    Location loc = Caster.getLocation();
                    loc.add(0, 2, 0);
                    Arrow ar = w.spawnArrow(loc, loc.getDirection(), (float) 0.6, 60);
                    ar.setFireTicks(1200);
                    ar.setShooter(Caster);
                    ar.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
                    return true;
                }
            };
            register_magic("FLAME_ARROW", FLAME_ARROW);
        }

        if(defualt_magic.containsKey("THUNDER_WAVE")) {
            install=defualt_magic.get("THUNDER_WAVE");
        }else{
            install=true;
            defualt_magic.put("THUNDER_WAVE",true);
        }
        if(install) {
            //雷鸣波
            MagicExecutor THUNDER_WAVE = new MagicExecutor(60, true) {
                @Override
                public boolean runMagic(LivingEntity Caster) {
                    Location loc = Caster.getLocation();
                    List<Entity> al = Caster.getNearbyEntities(9, 9, 9);
                    Vector ln;
                    for (Entity en : al) {
                        ln = en.getLocation().toVector().subtract(loc.toVector());
                        en.getVelocity().add(ln.multiply(6.0 / en.getLocation().distance(loc)));
                        if (LivingEntity.class.isAssignableFrom(en.getClass())) {
                            ((LivingEntity) en).damage(5, Caster);
                        }
                    }
                    Caster.getWorld().playSound(Caster.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2F, 0F);
                    return true;
                }
            };
            register_magic("THUNDER_WAVE", THUNDER_WAVE);
        }

        if(defualt_magic.containsKey("SHATTER")) {
            install=defualt_magic.get("SHATTER");
        }else{
            install=true;
            defualt_magic.put("SHATTER",true);
        }
        if(install) {
            //粉碎音波
            MagicExecutor SHATTER = new MagicExecutor(100, true) {
                @Override
                public boolean runMagic(LivingEntity Caster) {
                    World w = Caster.getWorld();
                    try {
                        Location loc = Caster.rayTraceBlocks(36, FluidCollisionMode.NEVER).getHitBlock().getLocation();
                        Collection<Entity> al = w.getNearbyEntities(loc, 6, 6, 6);
                        Vector ln;
                        for (Entity en : al) {
                            ln = en.getLocation().toVector().subtract(loc.toVector());
                            en.getVelocity().add(ln.multiply(6.0 / en.getLocation().distance(loc)));
                            if (LivingEntity.class.isAssignableFrom(en.getClass())) {
                                ((LivingEntity) en).damage(7, Caster);
                            }
                        }
                        Caster.getWorld().playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 2F, 0F);
                    } catch (NullPointerException n) {
                        return false;
                    }
                    return true;
                }
            };
            register_magic("SHATTER", SHATTER);
        }

        if(defualt_magic.containsKey("MAGIC_MISSILE")) {
            install=defualt_magic.get("MAGIC_MISSILE");
        }else{
            install=true;
            defualt_magic.put("MAGIC_MISSILE",true);
        }
        if(install) {
            //魔法飞弹
            MagicExecutor MAGIC_MISSILE = new MagicExecutor(60, true) {
                @Override
                public boolean runMagic(LivingEntity Caster) {
                    World w = Caster.getWorld();
                    Location loc = Caster.getLocation();
                    try {
                        Entity tar = Objects.requireNonNull(w.rayTraceEntities(Caster.getEyeLocation(), loc.getDirection(), 72, 0, x -> x != Caster)).getHitEntity();
                        if (WizardStaffMain.debug_mode) {
                            Bukkit.getLogger().info(loc.toString());
                            Bukkit.getLogger().info(Caster.getEyeLocation().toString());
                            Bukkit.getLogger().info(loc.clone().add(0, 1, 0).toString());
                            Bukkit.getLogger().info(Objects.requireNonNull(w.rayTraceEntities(loc, loc.getDirection(), 72)).getHitEntity().getName());
                            Bukkit.getLogger().info(Objects.requireNonNull(w.rayTraceEntities(loc.clone().add(0, 1, 0), loc.getDirection(), 72)).getHitEntity().getName());
                            Bukkit.getLogger().info(Objects.requireNonNull(w.rayTraceEntities(Caster.getEyeLocation(), loc.getDirection(), 72)).getHitEntity().getName());
                            Bukkit.getLogger().info(tar.getName());
                        }
                        if (tar != Caster && LivingEntity.class.isAssignableFrom(tar.getClass())) {
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
                            FT = new FollowTask((LivingEntity) tar, a);
                            FT.runTaskTimer(WizardStaffMain.only, 1, 1);
                            loc.add(-2, 0, 1);
                            a = (Snowball) w.spawnEntity(loc, EntityType.SNOWBALL);
                            ln = (tar.getLocation().clone().add(0, 1, 0)).toVector().subtract(a.getLocation().toVector());
                            a.setVelocity(ln.multiply(1.0 / a.getLocation().distance(tar.getLocation())));//
                            a.setCustomName("魔法飞弹");
                            a.setTicksLived(300);
                            a.setGravity(false);
                            a.setShooter(Caster);
                            FT = new FollowTask((LivingEntity) tar, a);
                            FT.runTaskTimer(WizardStaffMain.only, 1, 1);
                            loc.add(0, 0, -2);
                            a = (Snowball) w.spawnEntity(loc, EntityType.SNOWBALL);
                            ln = (tar.getLocation().clone().add(0, 1, 0)).toVector().subtract(a.getLocation().toVector());
                            a.setVelocity(ln.multiply(1.0 / a.getLocation().distance(tar.getLocation())));//
                            a.setCustomName("魔法飞弹");
                            a.setTicksLived(300);
                            a.setGravity(false);
                            a.setShooter(Caster);
                            FT = new FollowTask((LivingEntity) tar, a);
                            FT.runTaskTimer(WizardStaffMain.only, 1, 1);
                        }
                    } catch (NullPointerException n) {
                        return false;
                    }
                    return true;
                }
            };
            register_magic("MAGIC_MISSILE", MAGIC_MISSILE);
        }

        if(defualt_magic.containsKey("FROST_ARMOR")) {
            install=defualt_magic.get("FROST_ARMOR");
        }else{
            install=true;
            defualt_magic.put("FROST_ARMOR",true);
        }
        if(install) {
            //冰铠
            MagicExecutor FROST_ARMOR = new MagicExecutor(1200, true) {
                @Override
                public boolean runMagic(LivingEntity Caster) {
                    PlayerDamageListener.watchlist.add(Caster);
                    Caster.setAbsorptionAmount(Caster.getAbsorptionAmount() + 4);
                    FrostArmorTask FAT = new FrostArmorTask(Caster);
                    FAT.runTaskTimer(WizardStaffMain.only, 1, 1);
                    return true;
                }
            };
            register_magic("FROST_ARMOR", FROST_ARMOR);
        }

        if(defualt_magic.containsKey("THUNDER_CALLING")) {
            install=defualt_magic.get("THUNDER_CALLING");
        }else{
            install=true;
            defualt_magic.put("THUNDER_CALLING",true);
        }
        if(install) {
            //召雷术
            MagicExecutor THUNDER_CALLING = new MagicExecutor(100, true) {
                @Override
                public boolean runMagic(LivingEntity Caster) {
                    try {
                        Location loc = Caster.rayTraceBlocks(36, FluidCollisionMode.NEVER).getHitBlock().getLocation();
                        Caster.getWorld().strikeLightning(loc);
                    } catch (NullPointerException n) {
                        return false;
                    }
                    return true;
                }
            };
            register_magic("THUNDER_CALLING", THUNDER_CALLING);
        }

        if(defualt_magic.containsKey("HOLD_MONSTER")) {
            install=defualt_magic.get("HOLD_MONSTER");
        }else{
            install=true;
            defualt_magic.put("HOLD_MONSTER",true);
        }
        if(install) {
            //怪物定身术
            MagicExecutor HOLD_MONSTER = new MagicExecutor(120, true) {
                @Override
                public boolean runMagic(LivingEntity Caster) {
                    World w = Caster.getWorld();
                    Location loc = Caster.getLocation();
                    try {
                        Entity tar = Objects.requireNonNull(w.rayTraceEntities(Caster.getEyeLocation(), loc.getDirection(), 72, 0, x -> x != Caster)).getHitEntity();
                        if (tar != Caster && LivingEntity.class.isAssignableFrom(tar.getClass())) {
                            ((LivingEntity) tar).setAI(false);
                            new BukkitRunnable() {
                                int count = 60;

                                @Override
                                public void run() {
                                    if (count > 0) {
                                        count--;
                                    } else {
                                        ((LivingEntity) tar).setAI(true);
                                        this.cancel();
                                    }
                                }
                            }.runTaskTimer(WizardStaffMain.only, 1, 1);
                        }
                    } catch (NullPointerException n) {
                        return false;
                    }
                    return true;
                }
            };
            register_magic("HOLD_MONSTER", HOLD_MONSTER);
        }

        if(defualt_magic.containsKey("TELEPORT")) {
            install=defualt_magic.get("TELEPORT");
        }else{
            install=true;
            defualt_magic.put("TELEPORT",true);
        }
        if(install) {
            //传送术
            MagicExecutor TELEPORT = new MagicExecutor(0, true) {
                @Override
                public boolean runMagic(LivingEntity Caster) {
                    if (Player.class.isAssignableFrom(Caster.getClass())) {
                        Inventory inventory = Bukkit.createInventory((Player) Caster, 9 * 6, "teleport_target");
                        List<Entity> l = Caster.getWorld().getEntities();
                        List<LivingEntity> choice;
                        choice = new ArrayList<>();
                        for (Entity e : l) {
                            if (LivingEntity.class.isAssignableFrom(e.getClass())) {
                                choice.add((LivingEntity) e);
                            }
                        }
                        int i = 0;
                        ItemStack is;
                        SkullMeta im;
                        for (LivingEntity e : choice) {
                            if (i >= 45) break;
                            im = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.PLAYER_HEAD);
                            if (Player.class.isAssignableFrom(e.getClass()))
                                im.setOwningPlayer((Player) e);
                            else
                                im.setOwningPlayer(org.bukkit.Bukkit.getOfflinePlayer("MHF_" + e.getName()));
                            im.setDisplayName(e.getName());
                            is = new ItemStack(Material.PLAYER_HEAD);
                            is.setItemMeta(im);
                            inventory.setItem(i++, is);
                        }
                        if (i == 45) {
                            is = new ItemStack(Material.GRASS_BLOCK);
                            ItemMeta imx = is.getItemMeta();
                            imx.setDisplayName("下一页");
                            is.setItemMeta(imx);
                            inventory.setItem(53, is);
                        }
                        is = new ItemStack(Material.STONE);
                        ItemMeta imx = is.getItemMeta();
                        imx.setDisplayName("个数表示页码");
                        is.setItemMeta(imx);
                        inventory.setItem(49, is);
                        ((Player) Caster).openInventory(inventory);
                        WizardStaffListener.tp_choice = choice;
                        return true;
                    } else return false;
                }
            };
            register_magic("TELEPORT", TELEPORT);
        }
    }
}