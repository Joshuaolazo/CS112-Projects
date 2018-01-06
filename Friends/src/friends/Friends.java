package friends;

import structures.Queue;
import structures.Stack;


import java.util.*;

public class Friends {

	/**
	 * Finds the shortest chain of people from p1 to p2.
	 * Chain is returned as a sequence of names starting with p1,
	 * and ending with p2. Each pair (n1,n2) of consecutive names in
	 * the returned chain is an edge in the graph.
	 * 
	 * @param g Graph for which shortest chain is to be found.
	 * @param p1 Person with whom the chain originates
	 * @param p2 Person at whom the chain terminates
	 * @return The shortest chain from p1 to p2. Null if there is no
	 *         path from p1 to p2
	 */
	public static ArrayList<String> shortestChain(Graph g, String p1, String p2) {
		// Use best freind serach
		/** COMPLETE THIS METHOD **/
		
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY
		// CHANGE AS REQUIRED FOR YOUR IMPLEMENTATION
		
		Queue<Person> queue = new Queue<Person>();
		Boolean[] visited = new Boolean[g.members.length];
		for (int i=0;i<visited.length;i++) {
			visited[i]=false;
		}
		int[] prevs = new int[g.members.length];
		for (int i=0;i<prevs.length;i++) {
			prevs[i]=-1;
		}
		if(g.map.get(p1)== null ||g.map.get(p2)== null ) {
			//System.out.println("Person not found");
		}
	
		Person start = g.members[g.map.get(p1)];
		breadfirstSearch(g,start ,visited, prevs, queue, p2);
		ArrayList<String> reversed = pathfinder(prevs,g,p1, p2 );
		if (reversed == null)
			return null;
		Collections.reverse(reversed);
		return reversed;
	}
	private static ArrayList<String> pathfinder(int[]prevs,Graph g,String p1,String p2 ){
		ArrayList<String> ans = new ArrayList<String>();
		int end = g.map.get(p1);
		int desired = g.map.get(p2);
		int lookat = desired;
		while( lookat!= end) {
			if(prevs[lookat]==-1) {
				//System.out.println("no path"+" "+"deez nuts");
				return null;
			}
			ans.add(g.members[lookat].name);
			lookat= prevs[lookat];			
		}
		ans.add(g.members[lookat].name);
		return ans;
	}
	

	private static void breadfirstSearch(Graph g, Person start , Boolean[] visited, int[] prevs, Queue<Person> queue,String p2) {
		int startno = g.map.get(start.name);
		visited[startno]=true;
		//System.out.println("Visiting" +" " + start.name);
		queue.enqueue(start);
		prevs[startno]= start.first.fnum;
		while(!queue.isEmpty() ) {
			Person v = queue.dequeue();
			for(Friend seide = v.first; seide!=null;seide = seide.next ) {
				int vperson = seide.fnum;
				if(g.members[vperson].name== p2)
					break;
				if(!visited[vperson]) {
				
					//System.out.println("Visiting" + " "+g.members[vperson].name );
					int prevno = g.map.get(v.name);
					prevs[vperson] = prevno;
					visited[vperson]=true;
					queue.enqueue(g.members[vperson]);
				}
			}
		}		
	}
	
	
	/**
	 * Finds all cliques of students in a given school.
	 * 
	 * Returns an array list of array lists - each constituent array list contains
	 * the names of all students in a clique.
	 * 
	 * @param g Graph for which cliques are to be found.
	 * @param school Name of school
	 * @return Array list of clique array lists. Null if there is no student in the
	 *         given school
	 */
	public static ArrayList<ArrayList<String>> cliques(Graph g, String school) {
		// use dfs or bfs
		/** COMPLETE THIS METHOD **/
		
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY
		// CHANGE AS REQUIRED FOR YOUR IMPLEMENTATION
		ArrayList<ArrayList<String>> ans = new ArrayList<ArrayList<String>>();
		Queue<Person> queue = new Queue<Person>();
		Boolean[] visited = new Boolean[g.members.length];
		for (int i=0;i<visited.length;i++) {
			visited[i]=false;
		}
		int[] prevs = new int[g.members.length];
		for (int i=0;i<prevs.length;i++) {
			prevs[i]=-1;
		}
		for(int v = 0; v<visited.length;v++) {
			if(!visited[v]) {
				Person start = g.members[v];
				skoolSearch(g,start ,visited, prevs, queue, school);
				ArrayList<String> indivs = new ArrayList<String>();
			
				indivs = pathfindertoo(prevs,g,v , indivs,school);
				if(indivs.isEmpty()==false) {
					ans.add(indivs);
				}
			}
		}	
		if(ans.isEmpty())
			return null;
		return ans;
			
	}
	//Need to find a way to find connections, maybe use recursive
	private static ArrayList<String> pathfindertoo(int[]prevs,Graph g, int start, ArrayList<String> indivs, String school){
		if( g.members[start].school== null) {
			return indivs;
		}
		if(g.members[start].school.equals(school)) {
			indivs.add(g.members[start].name);
		}
		for(int i=0; i<prevs.length;i++) {
			if( prevs[i]== start ) {
				pathfindertoo(prevs,g,i, indivs,school);
			}				
		}
		return indivs;
	}
	
	
	private static void skoolSearch(Graph g, Person start , Boolean[] visited, int[] prevs, Queue<Person> queue,String School) {
		int startno = g.map.get(start.name);
		visited[startno]=true;
		//System.out.println("Visiting" +" " + start.name);
		queue.enqueue(start);
		while(!queue.isEmpty() ) {
			Person v = queue.dequeue();
			for(Friend seide = v.first; seide!=null;seide = seide.next ) {
				int vperson = seide.fnum;
				String vschool = g.members[vperson].school;
				Boolean sameschool = false;
				if( vschool == null) {
					sameschool = false;
				}else {
					sameschool= vschool.equals(School);
				}				
				if(!visited[vperson] && sameschool) {			
					//System.out.println("Visiting" + " "+g.members[vperson].name );
					int prevno = g.map.get(v.name);
					prevs[vperson] = prevno;
					visited[vperson]=true;
					queue.enqueue(g.members[vperson]);
				}
			}
		}		
	}
	
	/**
	 * Finds and returns all connectors in the graph.
	 * 
	 * @param g Graph for which connectors needs to be found.
	 * @return Names of all connectors. Null if there are no connectors.
	 */
	public static ArrayList<String> connectors(Graph g) {
		//use dfs
		/** COMPLETE THIS METHOD **/
		
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY
		// CHANGE AS REQUIRED FOR YOUR IMPLEMENTATION
		int nomem = g.members.length;
		Boolean[] visited = new Boolean[ nomem];
		Boolean[] connectors = new Boolean[ nomem];
		for (int i=0;i< nomem;i++) {
			visited[i]=false;
		}
		
		int[] dfsnum = new int[ nomem];
		int[] back = new int[ nomem];
		int dfsno=1;
		
		for(int v = 0; v<visited.length;v++) {
			if(!visited[v]) {
				connectors[v]=false;
				dafirstsearch(g,g.members[v],visited,connectors,dfsnum,dfsno,back);
			}
		}
		ArrayList<String> ans = new ArrayList<String>();
		ans = trueprint(connectors, g);
		if(ans.isEmpty()) {
			return null;
		}
		return ans;
		
	}
	private static ArrayList<String> trueprint(Boolean[] connectors, Graph g) {
		ArrayList<String> ans = new ArrayList<String>();
		for(int i=0; i<connectors.length;i++) {
			
			if(connectors[i]!=null) {
				if(connectors[i]) {
					ans.add(g.members[i].name);
				}
			}
		}
		
		
		return ans;
	}

		
		
	
	private static void dafirstsearch(Graph g,Person start, Boolean[] visited, Boolean[] connectors,int[]  dfsnum,int dfsno, int[] back) {
		int startno = g.map.get(start.name);
		visited[startno]=true;
		dfsnum[ startno]= dfsno;
		back[startno]= dfsno;
		dfsno++;

		for(Friend seide = start.first; seide!=null;seide = seide.next ) {
			if(!visited[seide.fnum]) {
				dafirstsearch(g,g.members[seide.fnum],visited,connectors,dfsnum,dfsno,back);
				if(dfsnum[ startno]> back[seide.fnum]) {
					back[ startno]= Math.min(back[startno],back[seide.fnum]);
				}else {
					if(connectors[startno]==null) {
						connectors[ startno]=true;
					}else {
						if(connectors[startno]==false) {
							connectors[startno]=null;
						}
					}
				}
			}else {
				back[ startno]= Math.min(back[startno],dfsnum[seide.fnum]);
			}
		}
	}

	
}
