import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


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
        ((Player)commandSender).getInventory().addItem(phb.clone());
        return true;
    }
}
