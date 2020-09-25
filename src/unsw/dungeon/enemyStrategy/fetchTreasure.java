package unsw.dungeon.enemyStrategy;

import java.util.LinkedList;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.Opponents;

public class fetchTreasure implements enemyStrategy{
    private Dungeon dungeon;

    public void setDungeon(Dungeon dungeon){
        this.dungeon = dungeon;
    }
    
    @Override
    public void moveStrategy(Opponents enemy, Entity treasure){
        int t_x = treasure.getY();
        int t_y = treasure.getX();

        int e_x = enemy.getY();
        int e_y = enemy.getX();

        int[][] matrix = dungeon.getMatrix();
        getShortestPath(matrix, t_x, t_y, e_x, e_y, enemy, dungeon);
    }
    /**
     * Obtain the shortest path from the location of the gnome to the treasure
     * The path is calculated with BFS
     * @param matrix
     * @param t_x
     * @param t_y
     * @param e_x
     * @param e_y
     * @param enemy
     * @param dungeon
     */
    public static void getShortestPath(int[][] matrix, int t_x, int t_y, int e_x, int e_y, Opponents enemy, Dungeon dungeon){
        if(matrix[t_x][t_y] == 0 || matrix[e_x][e_y] == 0){
            return;
        }
        // initialize the each cell with integer max value and set previous cell as null
        Grid[][] grids = new Grid[dungeon.getHeight()][dungeon.getWidth()];
        for(int x = 0; x < dungeon.getHeight(); x++){
            for(int y = 0; y < dungeon.getWidth(); y++){
                if(matrix[x][y] != 0){
                    grids[x][y] = new Grid(x, y, Integer.MAX_VALUE, null);
                }
            }
        }
        // Search around to find the shortest path
        LinkedList<Grid> queue = new LinkedList<>();
        Grid src = grids[e_x][e_y];
        src.dist = 0;
        queue.add(src);
        Grid dest = null;
        Grid grid;
        while((grid = queue.poll()) != null){
            if(grid.x == t_x && grid.y == t_y){
                dest = grid;
                break;
            }
            visit(grids, queue, grid.x + 1, grid.y, grid);
            visit(grids, queue, grid.x - 1, grid.y, grid);
            visit(grids, queue, grid.x, grid.y + 1, grid);
            visit(grids, queue, grid.x, grid.y - 1, grid);
        }
        // If destination is reachable, gnome will move along the shortest path
        if(dest == null){
            return;
        }else{
            LinkedList<Grid> path = new LinkedList<>();
            grid = dest;
            do{
                path.addFirst(grid);
            }while((grid = grid.prev) != null);
            Grid nextMove = path.poll();
            if((nextMove = path.poll()) != null){
                enemy.setX(nextMove.y);
                enemy.setY(nextMove.x);
            }
        }
    }

    public static void visit(Grid[][] grids, LinkedList<Grid> queue, int x, int y, Grid parent){
        // Check if the cell is valid
        if(x < 0 || x >= grids.length || y < 0 || y >= grids[0].length || grids[x][y] == null){
            return;
        }
        // Input valid grids in the queue and also record the distance and prev
        int dist = parent.dist + 1;
        Grid grid = grids[x][y];
        if(dist < grid.dist){
            grid.dist = dist;
            grid.prev = parent;
            queue.add(grid);
        }
    }
    
    private static class Grid {
        int x;
        int y;
        int dist;
        Grid prev;

        Grid(int x, int y, int dist, Grid prev) {
            this.x = x;
            this.y = y;
            this.dist = dist;
            this.prev = prev;
        }
    }
}