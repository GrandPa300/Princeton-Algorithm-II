## Project Assignments
### 1: WordNet
* Write `SAP` data type to represent shortest ancestral path. 
	*  In the digraph at left, SAP between 3 and 11 has length 4 (with common ancestor 1). 
	*  In the digraph at right,  SAP between 1 and 5 has length 2 (with common ancestor 0).

<div align=center>
	<img src="http://coursera.cs.princeton.edu/algs4/assignments/wordnet-sap.png" width="50%" height="50%" />
</div>

* Write `WordNet` which reads in list of synsets and hypernyms, and construct digraph (rooted DAG) to represent a [WordNet](http://wordnet.princeton.edu/).
	
<div align=center>
	<img src="http://coursera.cs.princeton.edu/algs4/assignments/wordnet-fig1.png" width="65%" height="65%" />
</div>

* Please check [instruction](http://coursera.cs.princeton.edu/algs4/assignments/wordnet.html) and [checklist](http://coursera.cs.princeton.edu/algs4/checklists/wordnet.html) for more details.  
#### Implementation and Optimization:
* __Version 1__: 
	* default `BFSDirectedPath` API was used for `SAP`. 
	* data structure for `WordNet` is as following:
		```java
		private HashSet<String> nouns; //  to quickly determine if a word exists 
		private ArrayList<String> idSynsetList; // index represets a word ID
		private HashMap<String, ArrayList<Integer>> nounIdMap; // map word ID with same meaning to a word
		private SAP sap;
	   ```
   * `DirectedCycle` object is created to ensure that digraph is single rooted and has no cycle in the graph. 
* __Version 2__: 
	* use private type `stepLockBFS` inside `SAP` class: whenever steps from a vertex is greater than best result so far, break the loop
	* Avoid re-initialize array for all vertices when number of vertices are huge. 
		1)  use ArrayList  to record index of vertex which was visited during BFS. 
		2)  Only reset those vertices' status in the array at end of each BFS search.
### 2: Seam Carving
* Check Pojrect [Seam Carving](https://github.com/GrandPa300/Seam-Carving) for more details.
### 3: Baseball Elimination
* Write `BaseballEllimination` to solve the [baseball elimination problem](https://en.wikipedia.org/wiki/Maximum_flow_problem#Baseball_elimination) by reducing it to the maxflow problem. A typical scenario from American League East Aug 30, 1996 is shown as below:

<div align=center>
	<img src="http://i.imgur.com/BcEuUWM.png" />
</div>

#### Implementation and Optimization:
* To check whether team x is eliminated, we consider two cases.
	*  __Trivial elimination__. If the maximum number of games x team can win is less than the number of wins of some other team i, then team x is trivially eliminated. 
	*  __Nontrivial elimination__. It can be solved as a maxflow problem: 
		1)  Connect an artificial source vertex _s_ to each game vertex i-j (team i v.s. j), set its capacity to g[i][j] (games left between i and j). 
		2)  Connect each game vertex i-j with the two opposing team vertices (flow goes to vertex who wins the game). no flow limitation on such edges.
		3)  Connect each team vertex to an artificial sink vertex t. To avoid elimination, team x need to win at least as many games as team i. Since team x can win at most w[x] + r[x] games, the capacity of edge from vertex i to the sink is w[x] + r[x] - w[i].
	* If all edges in the maxflow that are pointing from s are full, then this corresponds to assigning winners to all of the remaining games in such a way that no team wins more games than x. 
	* If some edges pointing from s are not full, then there is no scenario in which team x can win the division. 
	* In the flow network below Detroit is team x = 4.

<div align=center>
	<img src="http://coursera.cs.princeton.edu/algs4/assignments/baseball.png" width="50%" />
</div>

* Please check [instruction](http://coursera.cs.princeton.edu/algs4/assignments/baseball.html) and [checklist](http://coursera.cs.princeton.edu/algs4/checklists/baseball.html) for more details.  

 
 