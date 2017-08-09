import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import java.util.Arrays;
import java.util.HashSet;

public class BaseballElimination
{
    private int[] win, lose, left;
    private double[][] game;
    private String[] teams;
    private int teamNum;
    private HashSet<String> result;
    private final double INFINITY = Double.POSITIVE_INFINITY;
    
    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename)
    {
        In input = new In(filename);
        teamNum = Integer.valueOf(input.readLine());
        
        teams = new String[teamNum];
        win = new int[teamNum];
        lose = new int[teamNum];
        left = new int[teamNum];
        game = new double[teamNum][teamNum];
        
        int team = 0;
        while (input.hasNextLine())
        {
            String[] str = ("x " + input.readLine()).split(" +");
            teams[team] = str[1];
            win[team] = Integer.valueOf(str[2]);
            lose[team] = Integer.valueOf(str[3]);
            left[team] = Integer.valueOf(str[4]);
            
            for (int i = 0; i < game[team].length; i++)
                game[team][i] = Integer.valueOf(str[5 + i]);
            team++;
        }
    }
    
    // number of teams
    public int numberOfTeams() 
    {return teamNum;}
    
    // all teams
    public Iterable<String> teams() 
    {return Arrays.asList(teams);}
    
    // number of wins for given team
    public int wins(String team) 
    {return win[Arrays.asList(teams).indexOf(team)];}
    
    // number of losses for given team
    public int losses(String team)
    {return lose[Arrays.asList(teams).indexOf(team)];}
    
    // number of remaining games for given team
    public int remaining(String team) 
    {return left[Arrays.asList(teams).indexOf(team)];}
    
    // number of remaining games between team1 and team2
    public int against(String team1, String team2)
    {
        int idx1 = Arrays.asList(teams).indexOf(team1);
        int idx2 = Arrays.asList(teams).indexOf(team2);
        return (int)game[idx1][idx2];
    }
    
   // is given team eliminated?
    public boolean isEliminated(String team)
    {
        certificateOfElimination(team);
        return result.size() != 0;  
    }
    
    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team)
    {
        result = new HashSet<>();
        
        // Trivial elimination
        int idx = Arrays.asList(teams).indexOf(team);
        for (int i = 0; i < teamNum; i++)
            if (win[idx] + left[idx] < win[i]) 
            {
                result.add(teams[i]);
                return result;
            }
        
        int vNum = teamNum * teamNum + teamNum + 2;
        int s = 0, temp = teamNum * teamNum - 1, t = teamNum * (teamNum + 1);
        
        FlowNetwork network = new FlowNetwork(vNum);
        for (int i = 0; i < teamNum; i++)
                for (int j = i + 1; j < teamNum; j++){
                    if (i == idx || j == idx || game[i][j] == 0) continue;
                    int id = i * teamNum + j;
                    network.addEdge(new FlowEdge(s, id, game[i][j]));
                    network.addEdge(new FlowEdge(id, temp + i, INFINITY));
                    network.addEdge(new FlowEdge(id, temp + j, INFINITY));
                }    
        
        for (int i = 0; i < teamNum; i++)
        {
            if (i != idx) network.addEdge(new FlowEdge(temp + i, t, win[idx] + left[idx] - win[i]));
        }
        
        FordFulkerson ff = new FordFulkerson(network, s, t);
        for (int i = 0; i < teamNum; i++)
        {
            if (i != idx && ff.inCut(temp + i)) result.add(teams[i]);
        }
        return result;
    }
   
   public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}