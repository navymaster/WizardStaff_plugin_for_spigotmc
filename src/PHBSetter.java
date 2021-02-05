import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PHBSetter implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender.isOp()){
            ItemStack newphb=((Player)commandSender).getInventory().getItem(EquipmentSlot.HAND).clone();
            PlayerHandBook.setPhb(newphb);
            WizardStaffMain.FC.set("phbdata",newphb);
            return true;
        }
        return false;
    }
}
