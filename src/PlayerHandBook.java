import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

/**
 * phb指令执行器<br>
 * setphb设置phb数据<br>
 * phb指令获取玩家手册
 * @author navy_master
 * @version 1.0.0
 * @see org.bukkit.command.CommandExecutor
 */
public class PlayerHandBook implements CommandExecutor {
    public static ItemStack getPhb() {
        return phb;
    }

    public static void setPhb(ItemStack phb) {
        PlayerHandBook.phb = phb;
    }

    private static ItemStack phb;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command.getName().equals("playerhandbook")){
            ((Player)commandSender).getInventory().addItem(phb.clone());
            return true;
        }
        if(command.getName().equals("setplayerhandbook")) {
            if (commandSender.isOp()) {
                ItemStack newphb = ((Player) commandSender).getInventory().getItem(EquipmentSlot.HAND).clone();
                setPhb(newphb);
                WizardStaffMain.FC.set("phbdata", newphb);
                return true;
            }
            return false;
        }
        return false;
    }
}
