package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import unsw.dungeon.Goals.CompositeGoal;
import unsw.dungeon.Goals.EnemyGoal;
import unsw.dungeon.Goals.ExitGoal;
import unsw.dungeon.Goals.Goal;
import unsw.dungeon.Goals.SwitchGoal;
import unsw.dungeon.Goals.TreasureGoal;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        Dungeon dungeon = new Dungeon(width, height);

        JSONArray jsonEntities = json.getJSONArray("entities");
        
        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }
        loadHealth(dungeon);

        JSONObject jsonGoals = json.getJSONObject("goal-condition");

		Goal goals = loadGoal(jsonGoals, dungeon);
		dungeon.setGoal(goals);
        return dungeon;
    }
    /**
     * Load player health bar 
     * @param dungeon
     */
    private void loadHealth(Dungeon dungeon){
        Entity entity = null;
        Heart heart1 = new Heart(0, 0);
        Heart heart2 = new Heart(1, 0);
        Heart heart3 = new Heart(2, 0);
        onLoad(heart1);
        onLoad(heart2);
        onLoad(heart3);
        entity = heart1;
        dungeon.addEntity(entity);
        entity = heart2;
        dungeon.addEntity(entity);
        entity = heart3;
        dungeon.addEntity(entity);
    }

    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");
        int id;
        Entity entity = null;

        switch (type) {
        case "player":
            Player player = new Player(dungeon, x, y);
            dungeon.setPlayer(player);
            onLoad(player);
            entity = player;
            break;
        case "wall":
            Wall wall = new Wall(x, y);
            onLoad(wall);
            entity = wall;
            break;
        case "boulder":
            Boulder boulder = new Boulder(dungeon,x,y);
            onLoad(boulder);
            entity = boulder;
            break;
        case "switch":
            Switch dungeonSwitch = new Switch(x, y);
            onLoad(dungeonSwitch);
            entity = dungeonSwitch;
            break;
        case "treasure":
            Treasure treasure = new Treasure(x, y);
            onLoad(treasure);
            entity = treasure;
            break;
        case "sword":
            Sword sword = new Sword(x, y);
            onLoad(sword);
            entity = sword;
            break;
        case "portal":
            id = json.getInt("id");
            Portal portal = new Portal(x, y, id);
            onLoad(portal);
            entity = portal;
            break;
        case "exit":
            Exit exit = new Exit(x, y);
            onLoad(exit);
            entity = exit;
            break;
        case "key":
            id = json.getInt("id");
            Key key = new Key(x, y, id);
            onLoad(key);
            entity = key;
            break;
        case "enemy":
            Enemy enemy = new Enemy(dungeon, x, y);
            onLoad(enemy);
            entity = enemy;
            break;
        case "invincibility":
            InvincibilityPotion potion = new InvincibilityPotion(x, y);
            onLoad(potion);
            entity = potion;
            break;
        case "door":
            id = json.getInt("id");
            Door door = new Door(x,y,id);
            onLoad(door);
            entity = door;
            break;
        case "apple":
            Apple apple = new Apple(x, y);
            onLoad(apple);
            entity = apple;
            break;
        case "gnome":
            Gnome gnome = new Gnome(dungeon, x, y);
            onLoad(gnome);
            entity = gnome;
            break;

        }
        dungeon.addEntity(entity);
    }
    /**
     * Set the goals according to the json file
     * @param json
     * @param dungeon
     * @return
     */
    private Goal loadGoal(JSONObject json, Dungeon dungeon) {
        Goal goals = null;
        CompositeGoal compositeGoal = null;
		if (json.getString("goal").equals("AND")) {
            compositeGoal = new CompositeGoal("AND");
			JSONArray subgoals = json.getJSONArray("subgoals");
			for (int i = 0; i < subgoals.length(); i++) {
				JSONObject subgoal = subgoals.getJSONObject(i);
				compositeGoal.addGoal(loadGoal(subgoal, dungeon));
			}
		} else if (json.getString("goal").equals("OR")) {
            compositeGoal = new CompositeGoal("OR");
			JSONArray subgoals = json.getJSONArray("subgoals");
			for (int i = 0; i < subgoals.length(); i++) {
				JSONObject subgoal = subgoals.getJSONObject(i);
				compositeGoal.addGoal(loadGoal(subgoal, dungeon));
			}
		} else {
            String goalName = json.getString("goal");
            if(goalName.equals("enemies")){
                goals = new EnemyGoal(dungeon);
            }else if(goalName.equals("boulders")){
                goals = new SwitchGoal(dungeon);
            }else if(goalName.equals("treasure")){
                goals = new TreasureGoal(dungeon);
            }else if(goalName.equals("exit")){
                goals = new ExitGoal(dungeon);
            }
        }
        
        if(compositeGoal != null){
            return compositeGoal;
        }
		return goals;
    }

    public abstract void onLoad(Entity player);

    public abstract void onLoad(Wall wall);

    public abstract void onLoad(Boulder boulder);

    public abstract void onLoad(Switch dungeSwitch);

    public abstract void onLoad(Treasure treasure);

    public abstract void onLoad(Sword sword);

    public abstract void onLoad(Portal portal);

    public abstract void onLoad(Exit exit);

    public abstract void onLoad(Key key);

    public abstract void onLoad(Enemy enemy);

    public abstract void onLoad(Door door);

    public abstract void onLoad(InvincibilityPotion potion);

    public abstract void onLoad(Heart heart);

    public abstract void onLoad(Apple apple);

    public abstract void onLoad(Gnome gnome);
    // TODO Create additional abstract methods for the other entities

}
