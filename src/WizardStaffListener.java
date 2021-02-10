import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class WizardStaffListener implements Listener {
    @EventHandler
    public void handle_right_click(PlayerInteractEvent e){
        if(e.hasItem()) {
            try {
                List<String> Lore = e.getItem().getItemMeta().getLore();
                for(String s: MagicExecutor.MagicList.keySet()){
                    if (Lore.contains(s)) {
                        MagicExecutor.MagicList.get(s).run(e.getPlayer());
                    }
                }
            }catch(NullPointerException n){

            }
        }
    }
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
    @EventHandler
    public void handle_leave(PlayerQuitEvent e){
        WizardStaffMain.player_magics.get(e.getPlayer()).cool_time=0;
    }
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
    public static List<LivingEntity> tp_choice;
    @EventHandler
    public void handle_inventory(InventoryClickEvent e){
        if(e.getWhoClicked().getOpenInventory().getTitle().equals("teleport_target")){
            int slot=e.getRawSlot();
            int page=e.getInventory().getItem(49).getAmount();
            int start_at;
            if(slot<45&&!Objects.isNull(e.getInventory().getItem(slot))){
                start_at=45*(page-1);
                e.getWhoClicked().teleport(tp_choice.get(start_at+slot));
            }else if(slot==45&&!Objects.isNull(e.getInventory().getItem(45))){
                page--;
                start_at=45*(page-1);
                SkullMeta im;
                ItemStack is;
                int i;
                for(i=start_at;i<start_at+45&&i<tp_choice.size();i++)
                {
                    im=(SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.PLAYER_HEAD);
                    if(Player.class.isAssignableFrom(e.getClass()))
                        im.setOwningPlayer((Player)tp_choice.get(i));
                    else
                        im.setOwningPlayer(org.bukkit.Bukkit.getOfflinePlayer("MHF_"+tp_choice.get(i).getName()));
                    im.setDisplayName(tp_choice.get(i).getName());
                    is=new ItemStack(Material.PLAYER_HEAD);
                    is.setItemMeta(im);
                    e.getInventory().setItem(i-start_at,is);
                }
                if(i<start_at+45){
                    for(;i<start_at+45;i++)
                    {
                        e.getInventory().setItem(i-start_at,null);
                    }
                }
                if(start_at+45<tp_choice.size()){
                    is=new ItemStack(Material.GRASS_BLOCK);
                    ItemMeta imx=is.getItemMeta();
                    imx.setDisplayName("下一页");
                    is.setItemMeta(imx);
                    e.getInventory().setItem(53,is);
                }else{
                    e.getInventory().setItem(53,null);
                }
                if(page!=1){
                    is=new ItemStack(Material.GRASS_BLOCK);
                    ItemMeta imx=is.getItemMeta();
                    imx.setDisplayName("上一页");
                    is.setItemMeta(imx);
                    e.getInventory().setItem(45,is);
                }else{
                    e.getInventory().setItem(45,null);
                }
                e.getInventory().getItem(49).setAmount(page);
            }else if(slot==53&&!Objects.isNull(e.getInventory().getItem(53))){
                page++;
                start_at=45*(page-1);
                SkullMeta im;
                ItemStack is;
                int i;
                for(i=start_at;i<start_at+45&&i<tp_choice.size();i++)
                {
                    im=(SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.PLAYER_HEAD);
                    if(Player.class.isAssignableFrom(e.getClass()))
                        im.setOwningPlayer((Player)tp_choice.get(i));
                    else
                        im.setOwningPlayer(org.bukkit.Bukkit.getOfflinePlayer("MHF_"+tp_choice.get(i).getName()));
                    im.setDisplayName(tp_choice.get(i).getName());
                    is=new ItemStack(Material.PLAYER_HEAD);
                    is.setItemMeta(im);
                    e.getInventory().setItem(i-start_at,is);
                }
                if(i<start_at+45){
                    for(;i<start_at+45;i++)
                    {
                        e.getInventory().setItem(i-start_at,null);
                    }
                }
                if(start_at+45<tp_choice.size()){
                    is=new ItemStack(Material.GRASS_BLOCK);
                    ItemMeta imx=is.getItemMeta();
                    imx.setDisplayName("下一页");
                    is.setItemMeta(imx);
                    e.getInventory().setItem(53,is);
                }else{
                    e.getInventory().setItem(53,null);
                }
                if(page!=1){
                    is=new ItemStack(Material.GRASS_BLOCK);
                    ItemMeta imx=is.getItemMeta();
                    imx.setDisplayName("上一页");
                    is.setItemMeta(imx);
                    e.getInventory().setItem(45,is);
                }else{
                    e.getInventory().setItem(45,null);
                }
                e.getInventory().getItem(49).setAmount(page);
            }
            e.setCancelled(true);
        }
    }
}
