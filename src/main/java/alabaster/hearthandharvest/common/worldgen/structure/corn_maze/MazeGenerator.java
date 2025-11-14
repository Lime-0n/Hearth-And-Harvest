package alabaster.hearthandharvest.common.worldgen.structure.corn_maze;

import net.minecraft.util.RandomSource;

public class MazeGenerator {

    /**
     * Generate a solvable maze.
     * true = wall, false = path
     */
    public static boolean[][] generate(int w, int h, RandomSource rand) {
        boolean[][] maze = new boolean[w][h];

        // Fill all with walls
        for (int x = 0; x < w; x++)
            for (int z = 0; z < h; z++)
                maze[x][z] = true;

        // Carve maze starting at (1,1)
        carve(1, 1, maze, rand);

        // Ensure entrance and exit are paths
        maze[0][h / 2] = false;         // entrance on west edge
        maze[w - 1][h / 2] = false;     // exit on east edge

        return maze;
    }

    /**
     * Recursive DFS maze carving
     */
    private static void carve(int x, int z, boolean[][] maze, RandomSource rand) {
        int w = maze.length;
        int h = maze[0].length;

        int[] dirs = {0, 1, 2, 3}; // 0=E,1=W,2=S,3=N
        shuffle(dirs, rand);

        for (int dir : dirs) {
            int dx = 0, dz = 0;
            switch (dir) {
                case 0 -> dx = 2;
                case 1 -> dx = -2;
                case 2 -> dz = 2;
                case 3 -> dz = -2;
            }

            int nx = x + dx;
            int nz = z + dz;

            if (nx > 0 && nz > 0 && nx < w - 1 && nz < h - 1 && maze[nx][nz]) {
                maze[nx][nz] = false;                   // carve new cell
                maze[x + dx / 2][z + dz / 2] = false;   // carve wall between
                carve(nx, nz, maze, rand);
            }
        }
    }

    private static void shuffle(int[] arr, RandomSource rand) {
        for (int i = arr.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }
}
