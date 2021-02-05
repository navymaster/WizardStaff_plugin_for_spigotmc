import com.sun.istack.internal.NotNull;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.*;

public class PlayerMagicList implements ConfigurationSerializable
{
    int cooldown=0;
    private String player_name;
    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    @Override
    public Map<String, Object>  serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("name",player_name);
        map.put("cooltime",cooldown);
        return map;
    }

    public static PlayerMagicList deserialize(Map<String, Object> map) {
        PlayerMagicList PML=new PlayerMagicList();
        PML.player_name=(String)map.get("name");
        PML.cooldown=(int)map.get("cooltime");
        return PML;
    }
}