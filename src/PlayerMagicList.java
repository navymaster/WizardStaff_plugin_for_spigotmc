import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.*;

public class PlayerMagicList implements ConfigurationSerializable
{
    int cool_time =0;
    private String player_name;
    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    @Override
    public Map<String, Object>  serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("name",player_name);
        map.put("cooltime", cool_time);
        return map;
    }

    public static PlayerMagicList deserialize(Map<String, Object> map) {
        PlayerMagicList PML=new PlayerMagicList();
        PML.player_name=(String)map.get("name");
        PML.cool_time =(int)map.get("cooltime");
        return PML;
    }
}