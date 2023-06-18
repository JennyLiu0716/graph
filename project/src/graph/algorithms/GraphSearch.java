package graph.algorithms;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import graph.Graph;
import graph.Node;
import utils.DList;

/**
 * 
 * @author Liu Chenxi, Yixin Cao (February, 2023)
 *
 *         common graph algorithms.
 * 
 *         todo: expand the comment.
 * 
 */
public class GraphSearch {
    // a vertex in the last level with the minimum degree is an end vertex
    // O(n) ok
    // reference:
    // https://www.geeksforgeeks.org/level-node-tree-source-node-using-bfs/

    /*
     * meaning of x?
     */
	
    /**
     * A linear-time algorithm to find the end vertex using BFS.
     * By lemma 3.7, a vertex in the last level with the minimum degree is an end vertex
     * 
     * Algorithm: 
     * Create the tree, a queue to store the nodes and insert the root or starting node in the queue. Create an extra array level of size v (number of vertices) and create a visited array.
     * Run a loop while size of queue is greater than 0.
     * Mark the current node as visited.
     * Pop one node from the queue and insert its childrens (if present) and update the size of the inserted node as level[child] = level[node] + 1.
     * Record the largest level
     * Choose the vertex in last level with minimum degree as end vertex
     * @param g graph
     * @return endVertex
     */
    public static int bfsEndVertex(Graph g, int source) {
        int[][] adj = g.adjacentGraph;
        int endVertex = 0;
        // array to store level of each node
        int vertexNum = adj.length;
        int lastLevel = 0;
        int level[] = new int[vertexNum];
        boolean marked[] = new boolean[vertexNum];

        // create a queue
        Queue<Integer> que = new LinkedList<Integer>();

        int x=source; // x = 0 -> choose the first element as the root. 
        // enqueue element x
        que.add(x);

        // initialize level of source node to 0
        level[x] = 0;

        // marked it as visited
        marked[x] = true;

        // do until queue is empty
        while (que.size() > 0) {

            // get the first element of queue
            x = que.peek();

            // dequeue element
            que.remove();

            // traverse neighbors of node x
            for (int i = 0; i < adj[x].length; i++) {
                // b is neighbor of node x
                int b = adj[x][i];
                // System.out.println(x+" "+i);
                // if b is not marked already
                if (!marked[b]) {

                    // enqueue b in queue
                    que.add(b);

                    // level of b is level of x + 1
                    level[b] = level[x] + 1;
                    if (level[b] > lastLevel)
                        lastLevel = level[b];

                    // mark b
                    marked[b] = true;
                }
            }
        }
        
        // endvertex -> minimum degree in largest level
        int minDegree = vertexNum;
        for (int i = 0; i < level.length; i++) {
            if (level[i] == lastLevel) {
                // graph[i].length = degree
                if (adj[i].length < minDegree) {
                    minDegree = adj[i].length;
                    endVertex = i;
                }
            }
        }
        return endVertex;
    }

    // ok O(m+n)
    // LBFS & LBFS+
    
    /**
     * The connector between the caller and LBFS algorithm.
     * Preprocessing for normal LBFS and LBFS plus.
     *
     * For normal LBFS, as the order to select vertex is any vertex with largest lexicographical label. The adjacent lists for each vertex do not need to be reordered.
     * For LBFS plus, for each selection, the vertex to be taken out is the last vertex in lbfsOrdering with largest lexicographical label. So, the adjacent lists for each vertex need to 
     * be sorted by the lbfsOrdering.
     * 
     * @param g -> graph
     * @param lbfsOrdering -> an LBFS ordering
     * @param num -> an identifier for normal LBFS and LBFS plus (1: normal; 2: plus)
     * @return
     */
    public static int[] lbfs(Graph g, int[] lbfsOrdering, int num) {
        int[][] adjgraph = g.adjacentGraph;
        if (num == 1) {
        	// normal LBFS -> any vertex with lexicographically largest label
        	// do not need to reorder the adj order
            return lbfsCore(adjgraph, lbfsOrdering, 0);
        } else if (num == 2) {
        	// LBFS+ -> from the vertex with lexicographically largest label, select the last order in the lbfsOrdering list
            // reorder the adj list according to lbfsOrdering
            int[][] adj = sortAdjacencyLists(adjgraph, lbfsOrdering);
            return lbfsCore(adj, lbfsOrdering, lbfsOrdering[lbfsOrdering.length - 1]);
        }
        return null;
    }

    
    // LBFS DELTA -> vertex with minimum degree and largest lexicographical label
    /**
     * a connector between caller and core LBFS algorithm for LBFSdelta
     * For each selection, the vertex to be taken out is the vertex with minimum degree in largest lexicographical label
     * So, we first sort the vertex by their degree. Then use the degree order to sort the adjacent lists for each vertex
     * 
     * @param g 
     * @param endVertex 
     * @return
     */
    public static int[] lbfsDelta(Graph g, int endVertex) {
        
    	int[][] adjgraph = g.adjacentGraph;
        
    	// the permutation is the vertices order of degree from max to min
        int[] degree = getDegreeOrder(g);
        
        // sort adj list by the degree
        int[][] adj = sortAdjacencyLists(adjgraph, degree);

        return lbfsCore(adj, degree, endVertex);
    }
    
    /**
     * through this function, we get the vertices order for degree from max to min
     * @param g - graph
     * @return vertex order
     */
    public static int[] getDegreeOrder(Graph g) {
    	
    	int[][] adjgraph = g.adjacentGraph;
    	int vertexNum = adjgraph.length;
    	int[] degree = new int[vertexNum]; // store degree of node(index)
        // sort vertex by degree 
        for (int i = 0; i < vertexNum; i++) {
            degree[i] = adjgraph[i].length;
        }
        degree = countSort(degree); // max-min degree sort
        // for(int i=0;i<degree.length;i++) {
        // System.out.print(degree[i]);
        // }
        return degree;
    	
    }

     /**
     * A linear-time algorithm to sort the adjacency lists such that vertices in each list follow the same order in {@code permutation}.
     *
     * For each vertex, create a new adjacency list by copying its neighbors in the order of their appearances in {@code permutation}.
     *
     * @param  originalLists
     *         The given adjacency lists of the graph.
     * @param  permutation
     *         A permutation of the vertices [0..n-1] -> [0..n-1].
     **/
    public static int[][] sortAdjacencyLists(int[][] originalLists, int[] permutation) {
        // todo: check adjgraph.length = permutation.lenght.

        int vertexNum = originalLists.length;
        // count[] record the current position that can be used in the adj list for each vertex
        int pos[] = new int[vertexNum];
        // Arrays.fill(count, 0);
        int[][] newLists = new int[originalLists.length][];
        for (int i = 0; i < vertexNum; i++)
            newLists[i] = new int[originalLists[i].length]; // adj[i] = originalLists[i].clone();
        
        // choose the vertex A according to the permutation
        for (int i = 0; i < vertexNum; i++) {
        	// get the adjacent vertex B of this vertex A
            for (int j = 0; j < originalLists[permutation[i]].length; j++) {
                // System.out.println(adjgraph[degree[i]][j]);
                int v = originalLists[permutation[i]][j];
                // put vertex A to the adjacency list of vertex B
                // update the position that can be used in the adjacency list of vertex B
                newLists[v][pos[v]++] = permutation[i];
            }
        }
        // finally  we can get the new adjacent list with the permutation order for each row
        return newLists;
    }

    
    /**
     * a connector to the core
     * @param g -> graph
     * @param permutation -> A permutation of the vertices 
     * @param endVertex -> the first vertex to be taken out
     * @return
     */
    public static int[] lbfsCore(Graph g, int[] permutation,  int firstoutVertex) {
        return lbfsCore(g.adjacentGraph, permutation, firstoutVertex);
    }

    /**
     * A linear-time algorithm to implement LBFS core algorithm
     * For each loop, select the first vertices with the lexicographically largest label.
     * Then, for each unvisited neighbor of the vertex, make the neighbor's lexicographical label larger.
     * 
     * implementation:
     * A linked linked list{@code lexicographical_linkedlist} for the precedence of the lexicographical label
     * For each element in {@code lexicographical_linkedlist}, it is a linked list of nodes at certain lexicographical label.
     * In linked linked list {@code lexicographical_linkedlist}, from beginning to the end, the lexicographical label of linked list is decrease.
     * 
     * @param adj
     * @param permutation
     * @param firstoutVertex -> the first vertex that should be taken out
     * @return
     */
    public static int[] lbfsCore(int[][] adj, int[] permutation,int firstoutVertex) {
        int vertexNum = adj.length;

        // LBFS -> to be returned.
        // the LBFS list : index -> vertex number; element -> the order of the vertex to be taken out (from 0)
        int[] LBFS = new int[vertexNum];
        
        // initialization: all nodes haven't been taken out of the linked list.
        Arrays.fill(LBFS, -1);
        
        // the initial linked list in the linked linked list
        DList<Node> initial_linkedlist = new DList<Node>();
        
        // nodelist -> a list to store the node by their original index
        // find a node in linked list in O(1)
        Node[] nodelist = new Node[vertexNum];

        // a linked list (represents lexicographically label) with element of linked list (vertexes in each label)
        // through the implementation, the linked list in the lexicographical_linked list will be placed in the order of the lexicographical label
        // from head to tail, the lexicographical label will decrease
        // so the first element (linked list) in lexicographical_ linkedlist always has the largest label
        DList<DList<Node>> lexicographical_linkedlist = new DList<DList<Node>>();
        
        // initialization: in the linked linked list, there is only one element (except head & tail)
        // including all the vertex
        // insertFirst return the node of the linked linked list corresponding to the inserted lined list
        Node initial_node = lexicographical_linkedlist.insertFirst(initial_linkedlist);
        
        // degree of the head of the element (linked list) in linked linked list represents when this linked list is created.
        // initialization -> -1; 
        initial_node.degree = -1;
        
//        System.out.println("permutation"+Arrays.toString(permutation));
        
        // initialization: insert each node to the initial_lined list
        for (int i = 0; i < vertexNum; i++) {
        	// insertion by the permutation
            Node node = new Node(permutation[i]);
            node.degree = adj[permutation[i]].length;
            
            // curlistNode is the current linked list that the node belongs to
            // to implement O(1) when insertion new linked list with larger lexicographical label
            node.curlistNode = initial_node;
            initial_linkedlist.insertFirstNode(node);
            
            // store the node according to the vertex number in nodelist
            nodelist[permutation[i]] = node;
        }
        
//        System.out.println(firstoutVertex);
//        initial_linkedlist.printToString();

        // select the vertex to be taken out 
        // and insert new linked list with larger lexicographic label
        for (int i = 0; i < vertexNum; i++) {
            // unvisited vertices with the lexicographically largest label
            Node<DList<Node>> superNode = lexicographical_linkedlist.head.next;
            Node outNode;// node gonna be taken out
            // As we have sort the adjlist by permutation, 
            // the first element in the lexicographical label is the out node
            if (i == 0) {
                outNode = nodelist[firstoutVertex];
//                System.out.println((int)outNode.element);
            } else {
            	while(superNode.element.isEmpty()) {
            		superNode = superNode.next;
            	}
//            	superNode.element.printToString();
                outNode = superNode.element.head.next;
            }
            // System.out.println(outNode.element);
            // System.out.println(i);
            removeNode(outNode);

            // curlistNode refer to the node (in linked linked list) of the this lexicographcial label
            // if the linked list is empty
            if (((DList<Node>) outNode.curlistNode.element).isEmpty()) {
                removeNode(outNode.curlistNode);
                // System.out.println('a');
            }

            // set the output list
            LBFS[(int) outNode.element] = i;

            // take the out node's adjacent nodes and assign them with larger lexicographical label
            // implementation: remove the nodes from each current linked list, 
            // create a new linked list and insert just before their current linked list, so the new linked list has a larger lexicographical label
            // insert the nodes into the new linked list
            for (int j = 0; j < adj[(int) outNode.element].length; j++) {
            	
            	// if the adjacent nodes has already been taken out, go on
                if (LBFS[adj[(int) outNode.element][j]] != -1) {
                    continue;
                }

                // remove the node from current list
                Node temp = removeNode(nodelist[adj[(int) outNode.element][j]]);
//                System.out.println("superlist"+temp.element);
                // if the new linked list for a larger lexicographical label hasn't been created
                // that is the previous linked list is not created in this selection(i)
                if (temp.curlistNode.previous.degree != i) {
                    DList<Node> newlist = new DList<Node>();
                    // create a new linked list, and insert before the current one
                    Node newlistNode = lexicographical_linkedlist.insertBefore(temp.curlistNode, newlist);
                    newlistNode.degree = i;	// mark the new linked list is created in the selection (i)
//                    newlistNode.predegree = temp.curlistNode.degree;
                    newlist.insertFirstNode(temp); // insert the node into new list
                    
                    // check whether need to remove the old linked list (empty)
//                    if (((DList<Node>) temp.curlistNode.element).isEmpty()) {
//                        removeNode(temp.curlistNode);
//                    	temp.curlistNode.degree = -2;
//                    }
                    
                    // change the node's curlistNode to the new linked list (node element in the large linked linked list)
                    temp.curlistNode = newlistNode;
                    // System.out.println(temp.curlistNode);
                    // System.out.println('1');
                    
                 // if the new larger lexicographical list for this selection has already been created
                } else {
                    // System.out.println(temp.curlistNode.previous);
                	// simply add the node into the previous linked list (larger lex)
                    ((DList<Node>) temp.curlistNode.previous.element).insertFirstNode(temp);
                    // change the curlistNode 
                    temp.curlistNode = temp.curlistNode.previous;
                    // check whether need to delete the odd linked list (if empty)
//                    if (((DList<Node>) temp.curlistNode.next.element).isEmpty()) {
//                        removeNode(temp.curlistNode.next);
//                    }
                    // System.out.println(temp.curlistNode.previous);

                    // System.out.println(temp.curlistNode);
                    // System.out.println('2');
                }
                // System.out.println(temp.element);

            }
       
        }

        return LBFS;
    }

    /**
     * This method does not belong here.
     */
    // 
    /**
     * A sorting algorithm in O(n) to sort the degree
     * 
     * Record the number for each degree and the start position in the sorted list
     * 
     * @param degree
     * @return
     */
    static int[] countSort(int[] degree) {

    	
        int n = degree.length;
        int[] degreeOrder = new int[n];
        int[] count = new int[n]; // degree:0 - n-1
        // for each degree, how many vertexes are there
        for (int i = 0; i < n; i++) {
            count[degree[i]]++;
        }
        // for each degree, what is the current position that can be used
        int[] cur = new int[n];
        // initialization: the begin position that can be used for each degree
        for (int i = 0; i < cur.length; i++) {
            if (i == 0)
                cur[i] = 0;
            else {
            	// beginning position for this degree is the beginning position for previous degree and the number of vertex for previous degree
                cur[i] = cur[i - 1] + count[i - 1];
            }
        }
        // put degree into the list
        int[] sortedDegree = new int[n];
        for (int i = 0; i < n; i++) {
            sortedDegree[cur[degree[i]]++] = i;
        }
        // reverse the list
        for (int i = n - 1; i >= 0; i--) {
            degreeOrder[i] = sortedDegree[n - 1 - i];
        }

        return degreeOrder;
    }

    /*
     * public-> private.
     */
    /**
     * remove a node in the doubly linked list
     * @param node
     * @return
     */
    private static Node removeNode(Node node) {
        Node temp = node.previous;
        node.previous.next = node.next;
        node.next.previous = temp;
        node.previous = null;
        node.next = null;
        // if (node.head.next.next==null) {
        // delete
        // }
        return node;
    }

    /*
     * LBFS -> sigma (this method is supposed to check whether an ordering is an umbrella ordering; using LBFS is misleading.)
     */
    /**
     * A linear-time algorithm to check whether the list {@code sigma} is an umbrella ordering
     * 
     * First, renumber the vertices an get the {@code vertexOrder}
     * Second, sort the adjacency list for each vertex in the decreasing order. 
     * Use an array of linked list for each vertex. Each linked list is the adjacency list of the vertex.
     * Traverse the vertex by {@code vertexOrder}, insert the vertex into its neighbor's adjacency list.
     * Check whether the ith linked list starts from [the first element, the second element, ... , i+1] 
     * 
     * @param g
     * @param sigma
     * @return
     */
    public static boolean intervalOrdering(Graph g, int[] vertexOrder) {
        int[][] adj = g.adjacentGraph;
        
        
//        for (int i=0;i<adj.length;i++) {
//        	System.out.print(i+":");
//        	for(int j=0;j<adj[i].length;j++) {
//        		System.out.print(adj[i][j]+",");
//        	}System.out.println();
//        }
        
        
        int[] sigma = new int[vertexOrder.length];
        for(int i=0;i<sigma.length;i++) {
        	sigma[vertexOrder[i]] = i;
        }
        
		
        DList[] lists = new DList[vertexOrder.length];
        // sort the adjacent list within each vertex in decreasing order by the vertexOrder
        // and store the adjacent list for each vertex in the lists
        // lists is a list for DList, by vertexOrder
        for(int i=0;i<vertexOrder.length;i++) {
        	int vertex = vertexOrder[i];
        	int adjlength = adj[vertex].length;
        	for(int j=0;j<adjlength;j++) {
        		if (lists[sigma[adj[vertex][j]]] == null) {
                    lists[sigma[adj[vertex][j]]] = new DList();
                }
        		Node node = new Node(i);
        		lists[sigma[adj[vertex][j]]].insertFirstNode(node);
        	}
        }
        


        // check whether the k th list starts from tthe first number to i+1. 
        // check whether the number larger than k is exactly the difference between the first element and k
        for (int k = 0; k < lists.length - 1; k++) {
            Node nodee = lists[k].head.next;
            int begin = (int) nodee.element;
            int count = 0;
//            System.out.println(k);
//            System.out.print(k+": ");
            while ((int) nodee.element > k) {
                count++;
//                System.out.print((int)nodee.element);
                nodee = nodee.next;
                
            }
//            System.out.println();
//            System.out.println(k+":");
//            System.out.println(begin-k);
//            System.out.println(count);
            if ((begin - k) != count) {
//            	System.out.println(begin-k);
//            	System.out.println(count);
//            	System.out.println(false);
            	return false;
            } 
        }
//        System.out.println(true);
        return true;
    }

    /**
     * Check whether an integer array is a correct permutation of [1..n].
     *
     * @param sigma
     *     an integer array
     * @return true if sigma is a permutation
     **/
    public static boolean isPermutation(int[] sigma) {
        return false;
    }
    
    /**
     * The algorithm maximum cardinality search.
     *
     * @param g 
     *     the input graph
     * @return an mcs ordering of g.
     **/
    public static int[] mcs(Graph g) {
        return null;
    }
}
