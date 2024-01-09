package graph.algorithms;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Vector;
import java.util.stream.IntStream;

import graph.Graph;
import graph.Node;
import utils.DoublyLinkedList;
import utils.Functions;

/**
 * 
 * @author Liu Chenxi, Yixin Cao (February, 2023)
 *
 *         common graph algorithms.
 * 
 *         Todo: the GraphSearch class now can only be used for connected graph
 *         recognition
 *         Idea: Detect the connected components in the disconnected graph, for
 *         each component, run the algorithm, the graph is a UIG(IG) if and only
 *         if every connected component is a UIG(IG)
 * 
 */
public class GraphSearch {

    /**
     * UPDATED
     * To find an end vertex of unit interval graph using BFS
     * Lemma 3.7 - Let G be unit interval graph. Let T be a BFS tree of G. A vertex
     * in the last level With the minimum degree is an end vertex.
     * 
     * @param g: the input graph
     * @return an end vertex
     */
    public static int findendVertex(Graph g) {

        int n = g.vertexNum;
        Vector<Integer>[] adj = g.adjacentGraph;
        int[] level = BFS(g);
        int lastlevel = level[n];

        int mindegree = n;
        int endVertex = -1;

        for (int i = 0; i < n; i++) {
            if (level[i] == lastlevel) {
                if (adj[i].size() < mindegree) {
                    mindegree = adj[i].size();
                    endVertex = i;
                }
            }
        }
        return endVertex;
    }

    /**
     * UPDATED
     * a modified BFS, which records the level of each node in the BFS tree and the
     * largest level of the tree
     * 
     * https://www.geeksforgeeks.org/breadth-first-search-or-bfs-for-a-graph/
     *
     * @param g:      the input graph, assumed to be connected
     * @param source: the starting vertex, default to be 0
     * @return the level of each vertex
     */
    private static int[] BFS(Graph g) {
        Vector<Integer>[] adj = g.adjacentGraph;
        int V = g.vertexNum;

        // Mark all the vertices as not visited(By default set as false)
        boolean visited[] = new boolean[V];

        // Create a queue for BFS
        LinkedList<Integer> queue = new LinkedList<Integer>();

        // to find endvertex, we need to record the level of each node in the BFS tree
        // use level[V] to pass the largest level of the bfs tree
        int[] level = new int[V + 1];

        // Mark the first node as visited and enqueue it
        visited[0] = true;
        queue.add(0);
        level[0] = 0;
        int largestLevel = 0;

        while (queue.size() != 0) {

            // Dequeue a vertex from queue and print it
            int s = queue.poll();

            // Get all adjacent vertices of the dequeued vertex s
            // If an adjacent has not been visited, then mark it visited and enqueue it
            for (int j : adj[s]) {
                if (!visited[j]) {
                    visited[j] = true;
                    queue.add(j);
                    level[j] = level[s] + 1;
                    if (level[j] > largestLevel)
                        largestLevel = level[j];
                }
            }
        }
        level[V] = largestLevel;
        return level;
    }

    /**
     * UPDATED
     * Get the vertices order by degree from small to large
     * 
     * @param g - graph
     * @return vertices sorted by degree
     */
    public static int[] getDegreeOrder(Graph g) {

        Vector<Integer>[] adjgraph = g.adjacentGraph;
        int vertexNum = g.vertexNum;

        // degree for each vertex
        int[] degree = new int[vertexNum];
        for (int i = 0; i < vertexNum; i++) {
            degree[i] = adjgraph[i].size();
        }

        // sort the vertex by degree from small to large
        int[] vertexOrder = Functions.countSortIndex(degree);

        return vertexOrder;
    }

    /**
     * UPDATED
     * A linear-time algorithm to sort the adjacency lists such that vertices in
     * each list follow the same order in {@code permutation}.
     *
     * @param originalLists
     * @param permutation
     **/
    private static Vector<Integer>[] sortAdjacencyLists(Vector<Integer>[] originalLists, int[] permutation) {

        int vertexNum = originalLists.length;
        Vector<Integer>[] newlist = new Vector[vertexNum];
        for (int i = 0; i < vertexNum; i++) {
            newlist[i] = new Vector<>();
        }

        // Similar to the sort adjacent list precedure in intervalOrderingChecking, but
        // we don't need to renumbering at this time
        // Also, the checking sorts the array in the reverse order of permutation by the
        // lemma, while this one is exactly sorting by permutation
        // for the explanation and correctness, please refer to intervalOrderingChecking
        for (int i = 0; i < vertexNum; i++) {
            int vertex = permutation[i];
            for (int j : originalLists[vertex]) {
                newlist[j].add(vertex);
            }
        }
        return newlist;
    }

    /**
     * UPDATED
     * Input: A connected graph G.
     * Output: Whether G is a unit interval graph.
     * 
     * Figure 6: The three-sweep recognition algorithm for unit interval graphs
     * 
     * @param g
     * @return whether or not graph g is a UIG
     */
    public static boolean threeSweepUIG(Graph g) {
        int[] t = LBFS(g);
        int[] t_new = Functions.transferIE(t);

        // testing graph14.txt vertex order is incorrect
        // System.out.println("vertexOrder1");
        // System.out.println(Arrays.toString(t_new));

        int[] sigma = LBFSplus(g, t_new);
        int[] sigma_new = Functions.transferIE(sigma);

        // testing graph14.txt vertex order is incorrect
        // System.out.println("vertexOrder2");
        // System.out.println(Arrays.toString(sigma_new));

        int[] sigmaPLUS = LBFSplus(g, sigma_new);
        int[] sigmaPLUS_new = Functions.transferIE(sigmaPLUS);

        // testing graph14.txt vertex order is incorrect
        // System.out.println("vertexOrder1");
        // System.out.println(Arrays.toString(sigmaPLUS_new));

        return intervalOrderingChecking(g, sigmaPLUS_new);
    }

    /**
     * UPDATED
     * Input: A connected graph G.
     * Output: Whether G is a unit interval graph.
     * 
     * Figure 7: A two-sweep recognition algorithm for unit interval graphs.
     * 
     * @param g
     * @return whether or not graph g is a UIG
     */
    public static boolean twoSweepUIG(Graph g) {
        int u = findendVertex(g);
        int[] sigma = LBFSdelta(g, u);
        int[] sigma_new = Functions.transferIE(sigma);
        return intervalOrderingChecking(g, sigma_new);
    }

    private static int[] LBFS(Graph g) {
        int[] permutation = IntStream.range(0, g.vertexNum).toArray();
        return basicLBFS(g.adjacentGraph, permutation, 0, false, false);
    }

    private static int[] LBFSplus(Graph g, int[] permutation) {
        return basicLBFS(g.adjacentGraph, permutation, -1, true, false);
    }

    private static int[] LBFSdelta(Graph g, int out) {
        // this permutation is sorted vertices by degree from small to large
        int[] permutation = getDegreeOrder(g);
        return basicLBFS(g.adjacentGraph, permutation, out, false, true);
    }

    /**
     * UPDATED
     * A linear-time algorithm to implement Figure 3: the procedure LBFS
     * Idea: using doubly linked linked lists to simulate lexicographically
     * labelling
     * 
     * Data structure:
     * The list {@code lexicographical_linkedlist} is a linked list.
     * The nodes of this linked list are also linked list.
     * Each small linked list contains disjoint vertices, and each list represents a
     * unique lexicographical precedence of the vertices contained.
     * By implementation, from head to tail, the lexicographical precedence for each
     * small linked list decreases.
     * 
     * Correctness:
     * Since the goal of label editing is to find the precedence of vertices,
     * We do not need to explicitly record each label, instead the precedence
     * matters.
     * Through this data structure, we convert complicated label operation to linked
     * list deletion and insertion, which could be implemented linearly.
     * 
     * LBFS variants:
     * Since LBFS plus and LBFS sigma are similar to the basic LBFS, we add some
     * parameters to integrate them together
     * 
     * @param adj         - adjacency list for graph
     * @param permutation - [0,1,...,n] for LBFS
     * @param out         - vertex to take out at the first step, 0 for LBFS
     * @param plus        - whether this is LBFS plus algorithm
     * @return
     */
    private static int[] basicLBFS(Vector<Integer>[] adjgraph, int[] permutation, int out, boolean plus,
            boolean delta) {

        int vertexNum = permutation.length;

        // For LBFSplus and LBFSdelta, in step 2.2, when choosing the vertex to be taken
        // out, a certain condition related to the permutation should be satisfied.
        // To enable a O(1) implementation to pick the vertex, we sort the adjacency
        // list by the permutation.
        // Then, since we scan the list of neighbors from left to right in the vector,
        // then order we scan is the exactly the permutation.
        // In this case every small linked list is guaranted to be sorted by
        // permutation, we don't need extra modify in the procedure.
        Vector<Integer>[] adj = new Vector[vertexNum];

        // we also sort the adj list for normal LBFS, because we also give a permutation
        // by default from 1 to n
        adj = sortAdjacencyLists(adjgraph, permutation);

        // correct adj
        // for(int i=0;i<adj.length;i++){
        // for(int j:adj[i]){
        // System.out.print(j+",");
        // }System.out.println();
        // }

        // sigma -> LBFS ordering to be returned.
        // index -> vertex number;
        // element -> the order of the vertex to be taken out (from 0)
        int[] sigma = new int[vertexNum];

        // initialization
        Arrays.fill(sigma, -1);

        // For a linear implementation, we store the current linked list for each node
        // to implement a O(1) search for which list the node is in
        Node[] curlinkedlistnodes = new Node[vertexNum];
        // We also store each node in a list for O(1) searching
        Node[] nodes = new Node[vertexNum]; // nodelist for previous

        // the initial linked list in the linked linked list
        // it should contains all vertices since the labels for all vertices are empty
        // initially
        // time represents when the linked list is created
        // it is created before the core LBFS, so the time is set to be -1
        DoublyLinkedList initial_linkedlist = new DoublyLinkedList();
        initial_linkedlist.time = -1;

        // a big linked list of serveral small linked list
        DoublyLinkedList lexicographical_linkedlist = new DoublyLinkedList();
        lexicographical_linkedlist.time = -1;

        // initially: there is only initial_linkedlist in the big linked list
        Node initial_node = new Node<DoublyLinkedList>(initial_linkedlist);
        lexicographical_linkedlist.insertAtBeginning(initial_node);

        // insert each node to the initial_linkedlist
        // since the labels for all vertices are empty initially
        // permutation is a kind of precedence for the node,
        // used for step2.2 how to pick the vertex from set S
        for (int i = 0; i < vertexNum; i++) {
            // insertion by the permutation
            // permutation[number] = vertex
            int vertex = permutation[i];
            Node node = new Node(vertex);
            // store the current linked list for the node
            curlinkedlistnodes[vertex] = initial_node;
            // store the node to the corresponding vertex
            nodes[vertex] = node;
            // insert at end to make sure the initial linked list is sorted by permutation
            initial_linkedlist.insertAtEnd(node);
        }

        // initial_node correct
        // ((DoublyLinkedList) initial_node.element).display();

        // LBFS core:
        // Select the vertex to be taken out, move it from the linked list
        // Move all its neighbors in the lists to a higher precedence (adding to get a
        // larger lexicographic label)
        for (int i = 0; i < vertexNum; i++) {

            // lexicographical_linkedlist.display();

            // S ← unvisited vertices with the lexicographically largest label;
            DoublyLinkedList superNode = (DoublyLinkedList) lexicographical_linkedlist.head.element;

            Node outNode;// node gonna be taken out

            // 2.2. v ← the last vertex of σ|S;
            // for each small linked list, by implementation it is sorted by permutation
            if (plus == true) {
                outNode = superNode.tail;
            } else {
                // take out the node:
                // 1st time: arbitrary
                // other time: largest permutation in largest lexicographical label
                // Since when we insert the initial nodes by permutation and we sort the adj
                // list by permutation, the first element in the largest-label linked list is
                // the out node.

                // The procedures for LBFSdelta and orginal LBFS are the same in implementation.
                // The difference is that for LBFS, we define the permutation using array
                // 0...n-1 to simplify.
                // For LBFS delta, the permutation is by the degree of the vertices from small
                // to large

                if (i == 0) {
                    outNode = nodes[out];
                } else {
                    outNode = superNode.head;
                }
            }

            int outVertex = (int) outNode.element;
            // get the list where the node is in
            Node currentListNode = curlinkedlistnodes[outVertex];
            DoublyLinkedList currentList = (DoublyLinkedList) currentListNode.element;
            currentList.delete(outNode);

            // if current small list is empty, delete it from the big linked list
            if (currentList.isEmpty())
                lexicographical_linkedlist.delete(currentListNode);

            // set the output list
            sigma[outVertex] = i;

            // Idea: take the outnode's adjacent nodes and assign them with larger
            // lexicographical label
            // implementation:
            // Find the neighbors by the adj list (sorted by permutation)
            // For each neighbor
            // - Locate the current list for it, and remove the node from the list
            // - Create a new linked list and insert just before the previous list
            // containing this neighbor
            // (the new list represents a higher precedence, corresponding to a larger
            // label)
            // Insert the node into the end of the new list

            Vector<Node> emptyLists = new Vector<>();

            for (int j : adj[outVertex]) {

                // if the adjacent nodes has already been taken out, continue
                if (sigma[j] != -1)
                    continue;

                Node node = nodes[j];
                Node currentlistNode = curlinkedlistnodes[j];
                DoublyLinkedList currList = (DoublyLinkedList) currentlistNode.element;

                // remove the node from current list
                currList.delete(node);

                // if it is empty, do not delete it immediately, since we need the old list to
                // do the insertion for new list. we record it
                if (currList.isEmpty())
                    emptyLists.add(currentlistNode);

                // we need to check whether the new linked list for this current list has been
                // created by checking time field of the last linked list
                // the checking is needed because there could be two neighbors in the same
                // linked list originally
                boolean created = false;
                if (currentlistNode == lexicographical_linkedlist.head) {
                    created = false;
                } else {
                    Node previousNode = currentlistNode.previous;
                    DoublyLinkedList previousList = (DoublyLinkedList) previousNode.element;
                    int time = previousList.time;
                    if (time == i) {
                        created = true;
                    } else {
                        created = false;
                    }
                }

                Node newNode;
                DoublyLinkedList newlist;
                if (created == false) {

                    // create a new linked list
                    newlist = new DoublyLinkedList();
                    // record the time
                    newlist.time = i;
                    newNode = new Node<DoublyLinkedList>(newlist);

                    // insert just before the previous one
                    lexicographical_linkedlist.insertBefore(currentlistNode, newNode);

                    // insert the node into new list
                    newlist.insertAtEnd(node);

                    // change the node's curlistNode to the new linked list node
                    curlinkedlistnodes[j] = newNode;

                } else {

                    // the new list has already been created
                    newNode = currentlistNode.previous;
                    newlist = (DoublyLinkedList) newNode.element;

                    // insert the node into new list
                    newlist.insertAtEnd(node);

                    // change the curlistNode
                    curlinkedlistnodes[j] = newNode;

                }
                // check whether need to remove the old linked list (empty)
                // don't delete the list here, since we need the old empty list to seperate new
                // list with the same time
                // to avoid unexpected merging of new list seperated by an empty list
                // if (currList.isEmpty())
                // lexicographical_linkedlist.delete(currentlistNode);
            }
            // delete here
            for (Node node : emptyLists)
                lexicographical_linkedlist.delete(node);

        }

        return sigma;
    }

    /**
     * UPDATED
     * A linear-time algorithm to check whether the vertex order {@code vertexOrder}
     * is an
     * umbrella ordering for UIG (interval ordering for IG)
     * LINE 160: Given an ordering σ of V(G), we can verify whether σ is an interval
     * ordering in linear time.
     * 
     * Implementation:
     * Renumber the vertices such that σ(vi) = i.
     * Sort the adjacency list for each vertex in the decreasing order.
     * Check for all i = 1, . . . ,n − 1, the ith list starts from [f(i), f(i) − 1,
     * . . . ,i + 1], where f(i) is the first number in the list
     * 
     * @param g
     * @param sigma
     * @return
     */
    public static boolean intervalOrderingChecking(Graph g, int[] vertexOrder) {

        int vertexNum = vertexOrder.length;
        Vector<Integer> adj[] = g.adjacentGraph;

        // renumber vertices:
        // index of label -> orignal vertex number
        // element of label -> new vertex number
        int[] label = new int[vertexNum];
        for (int i = 0; i < vertexNum; i++) {
            label[vertexOrder[i]] = i;
        }

        // A linear implementation of renumbering and sorting at the same time:
        // Create a new empty adjacancy list
        // We scan the vertex from right to left in vertexOrder, so the label of vertex
        // is decreasing while scanning.
        // When scanning a node, we add it to adjacency list for its neighbors
        // Since Vector add() Method in Java appends the specified element to the end of
        // this vector.
        // So, after scanning all vertices and adding to the corresponding adjacency
        // list, we have already sorted the adjacency list.

        Vector<Integer>[] newadj = new Vector[vertexNum];
        for (int i = 0; i < vertexNum; i++) {
            newadj[i] = new Vector<>();
        }

        // sort the adjacent list within each vertex in decreasing order by the
        // vertexOrder
        // and store the adjacent list for each vertex in the lists
        // lists is a list for DList, by vertexOrder
        for (int i = vertexNum - 1; i >= 0; i--) {
            int vertex = vertexOrder[i];
            for (int j : adj[vertex]) {
                newadj[label[j]].add(i);
            }
        }

        // check whether the ith list starts from the first number to i+1.
        // convert the checking to whether the number at position (first - (i+1)) is
        // exactly i+1
        // since Vector stores an array of the element it store, so the
        // Vector.get(index) is implemented in O(1) by default
        for (int i = 0; i < vertexNum; i++) {
            Vector<Integer> list = newadj[i];
            if (list.isEmpty())
                continue;
            int first = list.get(0);
            if (first <= i + 1)
                continue;
            if (first - (i + 1) > newadj[i].size() - 1) {
                return false;
            }
            if (list.get(first - (i + 1)) != i + 1) {
                return false;
            }

        }
        // System.out.println(true);
        return true;
    }

    /**
     * Check whether an integer array is a correct permutation of [1..n].
     *
     * @param sigma
     *              an integer array
     * @return true if sigma is a permutation
     **/
    public static boolean isPermutation(int[] sigma) {
        return false;
    }

    /**
     * The algorithm maximum cardinality search.
     *
     * @param g
     *          the input graph
     * @return an mcs ordering of g.
     **/
    public static int[] mcs(Graph g) {
        return null;
    }
}