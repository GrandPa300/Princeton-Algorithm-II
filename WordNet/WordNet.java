import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.StdOut;
import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;

public class WordNet 
{
   private HashSet<String> nouns;
   private ArrayList<String> idSynsetList;
   private HashMap<String, ArrayList<Integer>> nounIdMap; 
   private SAP sap;
      
   // constructor takes the name of the two input files
   public WordNet(String synsets, String hypernyms)
   {
       In s = new In(synsets);
       In h = new In(hypernyms);
       
       nouns = new HashSet();
       idSynsetList = new ArrayList<>();
       nounIdMap = new HashMap<>();
       
       while (s.hasNextLine())
       {
           String[] str = s.readLine().split(",");
           Integer id = Integer.valueOf(str[0]);
           idSynsetList.add(str[1]);
           
           String[] ns = str[1].split(" ");
           for (String n : ns)
           {
               if (!nounIdMap.containsKey(n))
                   nounIdMap.put(n, new ArrayList<Integer>());
               nounIdMap.get(n).add(id);
            }
        }
        nouns = new HashSet<String>(nounIdMap.keySet());
        
        int vertexNum = idSynsetList.size();
        Digraph digraph = new Digraph(vertexNum);
        while (h.hasNextLine())
        {
            String[] str = h.readLine().split(",");
            int  hypo = Integer.valueOf(str[0]);
            for (int i = 1; i < str.length; i++){
                int hype = Integer.valueOf(str[i]);
                digraph.addEdge(hypo, hype);
            } 
        }
        
        DirectedCycle dcgCheck = new DirectedCycle(digraph);
        int dagCheck = 0;
        for (int i = 0; i < vertexNum; i++)
            if (!digraph.adj(i).iterator().hasNext()) dagCheck++;
        
        if (dcgCheck.hasCycle() || dagCheck != 1)
            throw new IllegalArgumentException(); 
            
        sap = new SAP(digraph);
   }
   
   // returns all WordNet nouns
   public Iterable<String> nouns()
   {return nouns;}

   // is the word a WordNet noun?
   public boolean isNoun(String word)
   {return nouns.contains(word);}
   
   // distance between nounA and nounB (defined below)
   public int distance(String nounA, String nounB)
   {
       ArrayList<Integer> a = nounIdMap.get(nounA), b = nounIdMap.get(nounB);
       return sap.length(a, b);
    }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB)
   {
       ArrayList<Integer> a = nounIdMap.get(nounA), b = nounIdMap.get(nounB);
       int ancestor = sap.length(a, b);
       return idSynsetList.get(ancestor);
    }

   // do unit testing of this class
   /*public static void main(String[] args) 
   {
       WordNet wordnet = new WordNet("wordnet_test/synsets.txt", "wordnet_test/hypernyms.txt");
       for (String s : wordnet.nouns())
       {
           StdOut.println(s);
        }
       StdOut.println(wordnet.isNoun("wordnet"));
       StdOut.println(wordnet.distance("good", "bad"));
       StdOut.println(wordnet.sap("good", "bad"));
    }*/
}