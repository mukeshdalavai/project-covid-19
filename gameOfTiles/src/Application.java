import javax.xml.bind.SchemaOutputResolver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Application {
    public Map<Integer, Integer> tileScoreMap = new HashMap<>();
    public int solve(int length, List<ArrayList<Integer>> tiles){ return score(0,tiles); }

    public int score(int i,List<ArrayList<Integer>> tiles){
        if (i >= tiles.size()){ return 0; }
        else if (tileScoreMap.containsKey(i)){ return tileScoreMap.get(i); }
        else {
            int maxScore = Math.max(tiles.get(i).get(0) + score(i+1,tiles),Math.max(tiles.get(i).get(1) + score(i+2,tiles), tiles.get(i).get(2 + score(i+3,tiles))));
            tileScoreMap.put(i,maxScore);
            return maxScore;
        }
    }

    public static void main(String[] args){
        int length = 3;
        List<ArrayList<Integer>> tiles = new ArrayList<>();
        ArrayList<Integer> tileA = new ArrayList<>();
        tileA.add(44);
        tileA.add(58);
        tileA.add(69);
        ArrayList<Integer> tileB = new ArrayList<>();
        tileB.add(34);
        tileB.add(70);
        tileB.add(-96);
        ArrayList<Integer> tileC = new ArrayList<>();
        tileC.add(18);
        tileC.add(26);
        tileC.add(32);
        tiles.add(tileA);
        tiles.add(tileB);
        tiles.add(tileC);
        Application app = new Application();
        System.out.println("Input : ");
        for (ArrayList<Integer> tile: tiles) {
            System.out.print("\t");
            for (Integer num: tile) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
        System.out.println("\nOutput : " + app.solve(length,tiles));
        System.out.println("\nTileScoreMap : ");
        for (Map.Entry<Integer, Integer> val : app.tileScoreMap.entrySet()){
            System.out.println("Tile : " + val.getKey() + " ||||  Score : " + val.getValue());
        }
    }
}
