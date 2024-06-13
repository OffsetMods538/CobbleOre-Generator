# Description
[![discord-singular](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/social/discord-singular_vector.svg)](https://discord.offsetmonkey538.top/)
[![modrinth](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/modrinth_vector.svg)](https://modrinth.com/mod/cog)  
[![requires-monkeylib538](https://raw.githubusercontent.com/OffsetMods538/MonkeyLib538/master/images/requires_monkeylib538.png)](https://modrinth.com/mod/monkeylib538)
[![Requires Fabric API](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/requires/fabric-api_vector.svg)](https://modrinth.com/mod/fabric-api)

## What it does
It makes cobblestone generators generate **any** ores. *Even modded*

There's a 25% chance for it to generate an ore which it chooses randomly from the `c:ores` block tag. This means that if you have some mod that adds a really overpowered ore, this ore would have the same chance of spawning as coal ore.

## Config
Don't like how op it is? Yeah, that makes sense.  
Well thankfully there's a config, which allows you to change how ores are distributed!  
The config is located at `.minecraft/config/cog.json`.  
I'm too dumb to explain how it works so I'll just give you an example with cobblestone having a 30% chance, diamond block with a 1% chance, and cake with a 69% chance of spawning:
```json
{
	"generatableBlocks": {
		"minecraft:cobblestone": 30,
		"minecraft:diamond_block": 1,
		"minecraft:cake": 69
	}
}
```


## Can I use it in a modpack?
Of course! It's not needed, but including a link to the modrinth page somewhere would be nice :D