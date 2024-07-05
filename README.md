# Biome-Modifier-for-Fabric

![Banner](assets/banner.png)

## Abstract

This is a library mod that allows users utilize datapacks to modify biomes easily and compatibly.

## Usage for Developers

We all know that for Forge modpacks, developers can utilize the following [example](https://github.com/Viola-Siemens/EmeraldCraftMod/blob/Forge-1.20.1_9.1.X/src/main/resources/data/emeraldcraft/forge/biome_modifier/ec_wild_crops.json) to modify biomes:

```json
{
  "type": "forge:add_features",
  "biomes": "#forge:is_plains",
  "features": [
    "emeraldcraft:wild_cabbage",
    "emeraldcraft:wild_chili"
  ],
  "step": "vegetal_decoration"
}
```

Let's make this work for Fabric!

Datapack developers should put all biome modifiers (with format `*.json`) on `data/<modid>/biome_modifiers/` directory. With this mod, all biome modifiers will be applied on each biome.

All biome modifiers should follow this format:

```json
{
  "type": "<modifier type>",
  "biomes": "<registry name of a single biome/list of registry names of biomes/a tag of biomes>",
  "priority": priority,
  "<other keys...>": "<other values...>"
}
```

Where `type` stands for the type of biome modifiers, and `biome` stands for all biomes to be modified.

Notice that `priority` field is optional. The smaller it is, the earlier it will be applied to the biomes (for example, modifiers with priority 99 will be applied earlier than modifiers with priority 100). The default value is 1000.

### Different Types of Biome Modifiers

We provide 7 different types of biome modifiers:

#### biome_modifier:none

This type allows developers to replace other biome modifiers in other datapacks.

```json
{
  "type": "biome_modifier:none"
}
```

#### biome_modifier:add_features

This biome modifier adds all placed features mentioned in `features` field to target biomes.

If any placed feature is missing from the registry, an error will be logged (won't crash the game with safe mode).

```json
{
  "type": "biome_modifier:add_features",
  "biomes": "<registry name(s) of biomes>",
  "features": "<registry name of a single feature/list of registry names of features>",
  "step": "<step of world generation>"
}
```

#### biome_modifier:remove_features

This biome modifier removes all placed features mentioned in `features` field from target biomes.

If any placed feature is missing from the target biomes, a warning will be logged and won't crash the game.

If any placed feature is missing from the registry, an error will be logged (won't crash the game with safe mode).

```json
{
  "type": "biome_modifier:remove_features",
  "biomes": "<registry name(s) of biomes>",
  "features": "<registry name of a single feature/list of registry names of features>",
  "step": "<step of world generation>"
}
```

#### biome_modifier:add_spawns

This biome modifier adds all entity spawns mentioned in `spawns` field to target biomes.

If any entity type is missing from the registry, an error will be logged (won't crash the game with safe mode).

```json
{
  "type": "biome_modifier:add_spawns",
  "biomes": "<registry name(s) of biomes>",
  "spawners": [
    {
      "type": "<registry name of a single entity type>",
      "weight": weight,
      "minCount": minCount,
      "maxCount": maxCount
    }
    //If you only add one spawner, you can also replace the list with the only object.
  ]
}
```

#### biome_modifier:remove_spawns

This biome modifier removes all entity spawns mentioned in `entity_types` field from target biomes.

If any entity type is missing from the target biomes, a warning will be logged and won't crash the game.

If any entity type is missing from the registry, an error will be logged (won't crash the game with safe mode).

```json
{
  "type": "biome_modifier:remove_spawns",
  "biomes": "<registry name(s) of biomes>",
  "entity_types": [
    "<registry name of a single entity type>"
    //If you only remove one spawner, you can also replace the list with the only string.
  ]
}
```

#### biome_modifier:add_spawn_costs

This biome modifier adds all entity spawns mentioned in `spawn_costs` field to target biomes.

Unlikely to `biome_modifier:add_spawns`, this modifier adds spawns to spawn costs (aka. spawn potential, spawn density).

If any entity type is missing from the registry, an error will be logged (won't crash the game with safe mode).

```json
{
  "type": "biome_modifier:add_spawn_costs",
  "biomes": "<registry name(s) of biomes>",
  "spawn_costs": {
    "<registry name of a single entity type>": {
      "charge": charge,
      "energy_budget": energy_budget
    }
  }
}
```

#### biome_modifier:remove_spawn_costs

This biome modifier removes all entity spawns mentioned in `entity_types` field from target biomes.

Unlikely to `biome_modifier:remove_spawns`, this modifier removes spawns to spawn costs (aka. spawn potential, spawn density).

If any entity type is missing from the target biomes, a warning will be logged and won't crash the game.

If any entity type is missing from the registry, an error will be logged (won't crash the game with safe mode).

```json
{
  "type": "biome_modifier:remove_spawn_costs",
  "biomes": "<registry name(s) of biomes>",
  "entity_types": [
    "<registry name of a single entity type>"
    //If you only remove one spawner, you can also replace the list with the only string.
  ]
}
```

## Examples

