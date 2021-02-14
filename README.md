# WizardStaff
一个基于spigot的minecraft服务端插件
功能是当玩家附魔时，有25%的概率该物品获得一个主动技能（法术），右键可以使用之。
每个玩家只有一个冷却时间，为0时可以使用法术

## 如果你想要使用它
你只需要下载它，然后在你的spigot服务端上，找到plugins文件夹然后丢进去即可运作
首次运行后会产生配置文件config.yml
你可以在其中进行更为细节的定制
记得你需要重启服务端以应用新的配置

## 如果你想要开发附属插件
打包为jar的部分在src中
如果你想在此基础上增加法术
这需要：
新建一个MagicExecuter(int cold_time)并重载runMagic函数（内部为执行你的法术效果的具体动作），冷却时长单位为tick(s)
然后执行register_magic(String name,MagicExecutor m)
如果你想要在其他位置执行这些法术，比如让你的怪物施法
你可以用MagicExecuter.magiclist.get(String name)来访问你的执行器，然后调用其run函数来确保冷却正常运作
