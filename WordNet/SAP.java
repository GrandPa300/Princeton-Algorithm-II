import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import java.util.ArrayList;
import java.util.Arrays;

public class SAP
{
    private final int vertexNum;
    private final int INFINITY = Integer.MAX_VALUE;
    private Digraph digraph;
    private boolean[] vMarked, wMarked;
    private int[] vDistTo, wDistTo;
    
    // constructor takes a digraph （not necessarily a DAG）
    public SAP(Digraph G)
    {
        if (G == null) throw new NullPointerException();
        this.digraph = G;
        this.vertexNum = G.V();
        // setup arrays for v-path
        vMarked = new boolean[vertexNum]; 
        vDistTo = new int[vertexNum];
        Arrays.fill(vDistTo, INFINITY);
        // setup arrays for w-path
        wMarked = new boolean[vertexNum];
        wDistTo = new int[vertexNum];
        Arrays.fill(wDistTo, INFINITY);
    }
    
    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w)
    {
        int[] res = stepLockBFS(v, w);
        return res[1];
    }
    
    // a common ancestor of v and w that participates in a shortest ancestral path
    // -1 if no such path
    public int ancestor(int v, int w)
    {
        int[] res = stepLockBFS(v, w);
        return res[0];
    }
    
    // length of shortest ancestral path between any vertex in v and any vertex in w;
    // -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w)
    {
        int[] res = stepLockBFS(v, w);
        return res[1];
    }
    
    // a common ancestor of v and w that participates in shortest ancestral path
    // -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
    {
        int[] res = stepLockBFS(v, w);
        return res[0];
    }

    // lock-step BFS from single sources
    private int[] stepLockBFS(int v, int w) 
    {
        Queue<Integer> vq = new Queue<Integer>(), wq = new Queue<Integer>();
        ArrayList<Integer> vIdx = new ArrayList<>(), wIdx = new ArrayList<>();
        int[] res = {INFINITY, INFINITY};
        
        // init BFS for v-path
        vMarked[v] = true;
        vDistTo[v] = 0;
        vq.enqueue(v);
        vIdx.add(v);
        
        // init BFS for w-path
        wMarked[w] = true;
        wDistTo[w] = 0;
        wq.enqueue(w);
        wIdx.add(w);
        
        // steplock BFS search on both paths
        while (!vq.isEmpty() || !wq.isEmpty())
        {
            if (!vq.isEmpty()) // bfs on v-path  
            {
                int vCur = vq.dequeue(); 
                if (vDistTo[vCur] > res[1]) break;
                for (int vAdj : digraph.adj(vCur))
                {
                    if (!vMarked[vAdj])
                    {
                        vDistTo[vAdj] = vDistTo[vCur] + 1;
                        vMarked[vAdj] = true;
                        vIdx.add(vAdj);
                        vq.enqueue(vAdj);
                    }
                    if (wMarked[vAdj])
                    {
                        int dist = vDistTo[vAdj] + wDistTo[vAdj]; 
                        if (dist < res[1])
                        {
                            res[1] = dist;
                            res[0] = vAdj;
                        }
                    }
                }
            }
            
            if (!wq.isEmpty()) // bfs on w-path  
            {
                int wCur = wq.dequeue(); 
                if (wDistTo[wCur] > res[1]) break;
                for (int wAdj : digraph.adj(wCur))
                {
                    if (!wMarked[wAdj])
                    {
                        wDistTo[wAdj] = wDistTo[wCur] + 1;
                        wMarked[wAdj] = true;
                        wIdx.add(wAdj);
                        wq.enqueue(wAdj);
                    }
                    if (vMarked[wAdj])
                    {
                        int dist = vDistTo[wAdj] + wDistTo[wAdj]; 
                        if (dist < res[1])
                        {
                            res[1] = dist;
                            res[0] = wAdj;
                        }
                    }
                }
            }
        } 
        
        // reset arrays for v-path and w-path
        for (int i = 0; i < vIdx.size(); i++)
        {
            vMarked[i] = false;
            vDistTo[i] = INFINITY;
        }
        for (int i = 0; i < wIdx.size(); i++)
        {
            wMarked[i] = false;
            wDistTo[i] = INFINITY;
        }
        
        // return ancestor and length as an array
        return res[0] == INFINITY ? new int[] {-1,-1} : res;
    }
    
    // lock-step BFS from multiple sources
    private int[] stepLockBFS(Iterable<Integer> v, Iterable<Integer> w) 
    {
        Queue<Integer> vq = new Queue<Integer>(), wq = new Queue<Integer>();
        ArrayList<Integer> vIdx = new ArrayList<>(), wIdx = new ArrayList<>();
        int[] res = {INFINITY, INFINITY};
        
        // init BFS for v-path
        for (int vCur : v)
        {
            vMarked[vCur] = true;
            vDistTo[vCur] = 0;
            vq.enqueue(vCur);
            vIdx.add(vCur);
        }
        
        // init BFS for w-path
        for (int wCur : w)
        {
            wMarked[wCur] = true;
            wDistTo[wCur] = 0;
            wq.enqueue(wCur);
            wIdx.add(wCur);
        }
        
        // steplock BFS search on both paths
        while (!vq.isEmpty() || !wq.isEmpty())
        {
            if (!vq.isEmpty()) // bfs on v-path  
            {
                int vCur = vq.dequeue(); 
                if (vDistTo[vCur] > res[1]) break;
                for (int vAdj : digraph.adj(vCur))
                {
                    if (!vMarked[vAdj])
                    {
                        vDistTo[vAdj] = vDistTo[vCur] + 1;
                        vMarked[vAdj] = true;
                        vIdx.add(vAdj);
                        vq.enqueue(vAdj);
                    }
                    if (wMarked[vAdj])
                    {
                        int dist = vDistTo[vAdj] + wDistTo[vAdj]; 
                        if (dist < res[1])
                        {
                            res[1] = dist;
                            res[0] = vAdj;
                        }
                    }
                }
            }
            
            if (!wq.isEmpty()) // bfs on w-path  
            {
                int wCur = wq.dequeue(); 
                if (wDistTo[wCur] > res[1]) break;
                for (int wAdj : digraph.adj(wCur))
                {
                    if (!wMarked[wAdj])
                    {
                        wDistTo[wAdj] = wDistTo[wCur] + 1;
                        wMarked[wAdj] = true;
                        wIdx.add(wAdj);
                        wq.enqueue(wAdj);
                    }
                    if (vMarked[wAdj])
                    {
                        int dist = vDistTo[wAdj] + wDistTo[wAdj]; 
                        if (dist < res[1])
                        {
                            res[1] = dist;
                            res[0] = wAdj;
                        }
                    }
                }
            }
        } 
        
        // reset arrays for v-path and w-path
        for (int i = 0; i < vIdx.size(); i++)
        {
            vMarked[i] = false;
            vDistTo[i] = INFINITY;
        }
        for (int i = 0; i < wIdx.size(); i++)
        {
            wMarked[i] = false;
            wDistTo[i] = INFINITY;
        }
        
        // return ancestor and length as an array
        return res[0] == INFINITY ? new int[] {-1,-1} : res;
    }
    
    /*
    // do unit testing of this class
    public static void main(String[] args)
    {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) 
        {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }*/
}
