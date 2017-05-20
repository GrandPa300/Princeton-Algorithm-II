import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class Outcast 
{
   private WordNet wordnet;
    
   // constructor takes a WordNet object
   public Outcast(WordNet wordnet)
   {this.wordnet = wordnet;}
   
   // given an array of WordNet nouns, return an outcast
   public String outcast(String[] nouns) 
   {
       int[] distance = new int[nouns.length];
       for (int i = 0; i < nouns.length; i++)
           for (int j = i + 1; j < nouns.length; j++)
           {
               int d = wordnet.distance(nouns[i], nouns[j]);
               distance[i] += d;
               distance[j] += d;
            }
       int maxDist = 0, maxIdx = -1;     
       for (int i = 0; i < distance.length; i++)
       {
           if (distance[i] > maxDist)
           {
               maxDist = distance[i];
               maxIdx = i;
            }
        }
        return nouns[maxIdx];
    }
   
   // Outcast 100 test. 
   public static void main(String[] args) 
   {
        Stopwatch timer = new Stopwatch(); 
        WordNet wordnet = new WordNet("wordnet_test/synsets.txt", "wordnet_test/hypernyms.txt");
        Outcast outcast = new Outcast(wordnet);
        
        int count = 0;
        while (count++ < 100){
            In input = new In("wordnet_test/outlist.txt");
            while (input.hasNextLine())
            {
                String filename = input.readLine(); 
                In in = new In(filename);
                String[] nouns = in.readAllStrings();
                outcast.outcast(nouns);
            }
        }
        StdOut.println("Total Running Time:" + timer.elapsedTime());    
    }
    
    // Standard Outcast Test
    /*
    public static void main(String[] args) 
    {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) 
        {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
    */
}
