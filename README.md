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
