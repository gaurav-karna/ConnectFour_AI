package assignment4Graph;
//import java.util.Arrays;
public class Graph {
	
	boolean[][] adjacency;
	int nbNodes;
	
	public Graph (int nb){
		this.nbNodes = nb;
		this.adjacency = new boolean [nb][nb];
		for (int i = 0; i < nb; i++){
			for (int j = 0; j < nb; j++){
				this.adjacency[i][j] = false;
			}
		}
	}
	
	public void addEdge (int i, int j){
		// ADD YOUR CODE HERE
		this.adjacency[i][j] = true;
		this.adjacency[j][i] = true;
	}
	
	public void removeEdge (int i, int j){
		// ADD YOUR CODE HERE
		this.adjacency[i][j] = false;
		this.adjacency[j][i] = false;
	}
	
	public int nbEdges(){
		// ADD YOUR CODE HERE
		// note for coder -> adjacency[0].length is used since adjacency[i] was giving ArrayIndex exception
		int cnt = 0;
		for (int i = 0; i < this.adjacency[0].length; i++) {
			for (int j = i; j < this.adjacency[0].length; j++) {
				if (this.adjacency[i][j]) {
					cnt++;
				}
			}
		}
		return cnt; // DON'T FORGET TO CHANGE THE RETURN
	}
	
	public boolean cycle(int start){
		// ADD YOUR CODE HERE
		// checking if start is within bounds of array
		if (start >= this.adjacency[0].length) {
			return false;
		}
		// checking for any connections
		int occurrence = 0;		
		for (int i = 0; i < this.adjacency[start].length; i++) {
			if (this.adjacency[start][i]) {
				occurrence++;
			}
		}
		if (occurrence < 2) {
			return false;
		}
		//System.out.println(occurrence);
		// now we know that it has at least 2 connections (enough for a cycle)
		// we now make an array of the primary connections of start
		int [] primary = new int[occurrence];
		int cnt = 0;
		for (int i = 0; i < this.adjacency[start].length; i++) {
			if (this.adjacency[start][i]) {
				primary[cnt] = i;
				cnt++;
			}
		}
		// now we use the helper method connection to see if there is a cycle evident
		// we loop through the primary values to see if there is a cycle from any of them
		boolean toReturn = false;
		for (int i = 0; i < primary.length; i++) {
			toReturn = connection(primary, start, primary[i]);
			if (toReturn) {
				break;
			}
		}
		return toReturn; // DON'T FORGET TO CHANGE THE RETURN
	}
	
	// ----------------HELPER METHOD FOR CYCLE-----------
	
	private boolean connection(int[] primary, int start, int node) {
		int occurrence = 0;
		// this is only checking how many connections the node in question has
		for (int i = 0; i < this.adjacency[0].length; i++) {
			if (this.adjacency[node][i]) {
				occurrence++;
			}
		}
		if (occurrence < 2) {
			return false;
		}
		// this for loop tests each true connection from a node to
		// see if any of those connections also connect to start, making a cycle
		boolean result = false;
		for (int i = 0; i < this.adjacency[0].length; i++) {
			if (i == start) {
				continue;
			}
			if (this.adjacency[node][i]) {
				// this loop is to see if the next node i connects with start
				for (int j = 0; j < primary.length; j++) {
					if (i == primary[j]) {
						return true;
					}
				}
				// if node i does not connect with primary, we put it
				// through the method again
				result = connection (primary, start, i);
				if (result) {
					return true;
				// if the next node returns i, then we ultimately
				// continue to the next connection of the previous node	
				} else {
					continue;
				}
			}
		}
		return result;
	}
	
	// ---------------HELPER METHOD END------------------
	
	public int shortestPath(int start, int end){
		// ADD YOUR CODE HERE
		int first = 0;
		int last = 1;
		int cnt = 0;
		int[] queue = new int [4 * this.nbNodes];
		int[] length = new int [this.nbNodes];
		for (int i = 0; i < length.length; i++) {
			length[i] = -1;
		}
		length[start]= 0;
		queue[0] = start;
		// Initialized reversal^
		while (first != last) {
			cnt = queue[first];
			first++;
			for (int i = 0; i < this.nbNodes; i++) {
				if (length[i] == -1 && this.adjacency[cnt][i]) {
					length[i] = length[cnt] + 1;
					queue[last] = i;
					last++;
				}
			}
		}
		if (length[end] != -1) {
			return length[end];
		} else {
			return (1 + this.nbNodes);
		}
		// DON'T FORGET TO CHANGE THE RETURN
	}
	
	// -------------HELPER METHOD-----------------
	
	private int result (int[] nodes, int end, int prev) {
		int occurrence = 0;
		//System.out.println(Arrays.toString(nodes));
		for (int i = 0; i < nodes.length; i++) {
			for (int j = 0; j < this.adjacency[end].length; j++) {
				if (this.adjacency[nodes[i]][j]) {
					if (!(i == prev)) {
						occurrence++;
					}
					if (j == end) {
						return 1;
					}
				}
			}
		}
		
		
		//System.out.println(occurrence);
		int [] holder = new int [occurrence];
		int holderCnt = 0;
		// has to be nodes.length since you are iterating through nodes to populate next holder
		for (int i = 0; i < nodes.length; i++) {
			for (int j = 0; j < this.adjacency[end].length; j++) {
				if (this.adjacency[nodes[i]][j]) {
					if (i != prev) {
						holder[holderCnt] = j;
						holderCnt++;
					}
				}
			}
		}
		
		return result (holder, end, nodes[0]);
	}
	// ---------------HELPER METHOD END--------------------
}
