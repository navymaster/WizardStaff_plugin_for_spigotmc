import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.*;
/**
 * 玩家法术参数类<br>
 * 目前仅保存冷却时间，其他等待后续开发
 * @author navy_master
 * @version 1.0.0
 */
public class PlayerMagic //implements ConfigurationSerializable
{
    public int getCool_time() {
        return cool_time;
    }

    public void setCool_time(int cool_time) {
        this.cool_time = cool_time;
    }

    private int cool_time =0;
    private String player_name;
    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    /*@Override
    public Map<String, Object>  serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("name",player_name);
        map.put("cooltime", cool_time);
        return map;
    }

    public static PlayerMagic deserialize(Map<String, Object> map) {
        PlayerMagic PML=new PlayerMagic();
        PML.player_name=(String)map.get("name");
        PML.cool_time =(int)map.get("cooltime");
        return PML;
    }*/
}