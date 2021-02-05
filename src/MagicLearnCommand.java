import org.bukkit.ChatColor;
import org.bukkit.command.*;

import java.util.List;
import java.util.stream.Collectors;

public class MagicLearnCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        /*List<Entity> tar=Bukkit.getServer().selectEntities(commandSender,strings[0]);
        {
            if (commandSender.isOp()) {
                if (!MagicExecutor.MagicList.containsKey(strings[1])) {
                    Bukkit.getLogger().info("期望得到<magicname>，但得到了" + strings[1]);
                    return false;
                }
                for(Entity target:tar) {
                    if(WizardStaffMain.player_magics.containsKey(target)) {
                        WizardStaffMain.player_magics.get(target).learn(strings[1]);
                       // Bukkit.getMessenger().("玩家" + target.getName() + "学会了法术" + strings[1]);
                        WizardStaffMain.FC.set(target.getName(), WizardStaffMain.player_magics.get(target));
                        commandSender.sendMessage(ChatColor.GRAY+"[将法术加入到了目标玩家的法术列表]");
                    }
                }
                return true;
            }
        }*/
        commandSender.sendMessage(ChatColor.GRAY+"[命令已过时]");
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String alis, String[] args) {
        if(args.length==1)return null;
        //筛选所有可能的补全列表，并返回
        if(args.length==2) return MagicExecutor.MagicList.keySet().stream().filter(s -> s.startsWith(args[1])).collect(Collectors.toList());
        return null;
    }
}
