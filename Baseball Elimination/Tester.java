import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
import java.util.HashSet;

public class Tester
{
    public static void main(String[] args) 
    {
        Stopwatch timer = new Stopwatch();
        StdOut.println("=== teams4.txt ===");
        BaseballElimination division = new BaseballElimination("baseball/teams4.txt");
        for (String team : division.teams()) 
        {
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
        StdOut.println("=== teams5.txt ===");
        division = new BaseballElimination("baseball/teams5.txt");
        for (String team : division.teams()) 
        {
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
        StdOut.println("=== nontrivial result for all tests ===");
        In in = new In("list.txt");
        while (in.hasNextLine())
        {
            String filename = in.readLine();
            division = new BaseballElimination(filename);
            String res = " ";
            for (String team : division.teams()) 
            {
                int counter = 0;
                for (String t : division.certificateOfElimination(team)) counter++;
                if (counter > 1) res = res + " " + team;  
            }
            //String f = String.format("%-20s", filename.substring(9));
            StdOut.printf("%0$-20s Teams nontrivial eliminated is %s \n", filename.substring(9), res); 
        }
        StdOut.println(timer.elapsedTime());
    }
}
