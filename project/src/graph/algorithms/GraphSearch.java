package graph.algorithms;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
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
 * 
 */
public class GraphSearch {

    /**
     * UPDATED
     * To find an end vertex of unit interval graph using BFS
     * Lemma 3.7 - Let G be unit interval graph. Let T be a BFS tree of G. A vertex
     * in the last level With the minimum degree is an end vertex.
     * 
     * Since there can be disconnected graph, we return a list of end vertices. Each
     * vertex in the list is an end vertex in a connected component.
     * 
     * @param g: the input graph
     * @return end vertices list
     */
    public static int[] findendVertex(Graph g) {

        int n = g.vertexNum;
        LinkedList[] adj = g.adjlist;

        // Mark all the vertices as not visited(By default set as false)
        boolean visited[] = new boolean[n];

        int[] endVertices = new int[n + 1];
        int endVertexNum = 0;

        for (int j = 0; j < n; j++) {
            if (!visited[j]) {
                int[] largestLevelVertices = BFS(g, j, visited);
                int largestLevelVerticesNum = largestLevelVertices[0];

                int mindegree = n;
                int endVertex = -1;

                for (int i = 1; i <= largestLevelVerticesNum; i++) {
                    int vertex = largestLevelVertices[i];
                    if (adj[vertex].size() < mindegree) {
                        mindegree = adj[vertex].size();
                        endVertex = vertex;
                    }
                }
                endVertexNum++;
                endVertices[endVertexNum] = endVertex;

            }
        }

        // the nunmber of end vertices is stored as the first element in the BFS array
        endVertices[0] = endVertexNum;
        return endVertices;
    }

    /**
     * UPDATED
     * a modified BFS, which records the level of each node in the BFS tree and the
     * largest level of the tree
     * 
     * https://www.geeksforgeeks.org/breadth-first-search-or-bfs-for-a-graph/
     *
     * @param g:       the input graph
     * @param source:  the starting vertex
     * @param visited: a boolean list to record whether a node has been visited
     * @return a list of vertices in the largest level in this connected BFS tree
     */
    private static int[] BFS(Graph g, int source, boolean[] visited) {
        LinkedList[] adj = g.adjlist;
        int V = g.vertexNum;

        // boolean visited[] = new boolean[V];

        // Create a queue for BFS
        LinkedList<Integer> queue = new LinkedList<Integer>();

        // to find endvertex, we need to record the level of each node in the BFS tree
        // use level[V] to pass the largest level of the bfs tree
        int[] level = new int[V + 1];

        // Mark the first node as visited and enqueue it
        visited[source] = true;
        queue.add(source);
        level[source] = 0;

        int largestLevel = 0;
        int[] largestLevelVertices = new int[V + 1];
        largestLevelVertices[1] = source;
        int largestLevelVerticesNum = 1;

        while (queue.size() != 0) {
            // Dequeue a vertex from queue and print it
            int s = queue.poll();

            // Get all adjacent vertices of the dequeued vertex s
            // If an adjacent has not been visited, then mark it visited and enqueue it
            Iterator it = adj[s].iterator();
            while (it.hasNext()) {
                int j = (int) it.next();
                if (!visited[j]) {
                    visited[j] = true;
                    queue.add(j);
                    level[j] = level[s] + 1;
                    if (level[j] > largestLevel) {
                        largestLevel = level[j];
                        largestLevelVerticesNum = 1;
                        largestLevelVertices[largestLevelVerticesNum] = j;
                    } else if (level[j] == largestLevel) {
                        largestLevelVerticesNum++;
                        largestLevelVertices[largestLevelVerticesNum] = j;
                    }

                }
            }
        }

        largestLevelVertices[0] = largestLevelVerticesNum;
        return largestLevelVertices;
    }

    /**
     * UPDATED
     * Get the vertices order by degree from small to large
     * 
     * @param g - graph
     * @return vertices sorted by degree
     */
    public static int[] getDegreeOrder(Graph g) {

        LinkedList<Integer>[] adjgraph = g.adjlist;
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
    private static LinkedList<Integer>[] sortAdjacencyLists(LinkedList<Integer>[] originalLists, int[] permutation) {

        int vertexNum = originalLists.length;
        LinkedList<Integer>[] newlist = new LinkedList[vertexNum];
        for (int i = 0; i < vertexNum; i++) {
            newlist[i] = new LinkedList<>();
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
     * Input: A graph G.
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

        int[] sigma = LBFSplus(g, t_new);
        int[] sigma_new = Functions.transferIE(sigma);

        int[] sigmaPLUS = LBFSplus(g, sigma_new);
        int[] sigmaPLUS_new = Functions.transferIE(sigmaPLUS);

        return intervalOrderingChecking(g, sigmaPLUS_new, true);
    }

    /**
     * UPDATED
     * Input: A graph G.
     * Output: Whether G is a unit interval graph.
     * 
     * Figure 7: A two-sweep recognition algorithm for unit interval graphs.
     * 
     * @param g
     * @return whether or not graph g is a UIG
     */
    public static boolean twoSweepUIG(Graph g) {

        int u[] = findendVertex(g);
        int new_u[] = Functions.deleteFirstElement(u);

        int[] sigma = LBFSdelta(g, new_u);
        int[] sigma_new = Functions.transferIE(sigma);

        if (!intervalOrderingChecking(g, sigma_new, true))
            return false;

        return true;
    }

    /**
     * Input : A graph G
     * Output : Whether G is a interval graph
     * 
     * Figure 12: The recognition algorithm for interval graphs
     * 
     * @param g
     * @return
     */
    public static boolean interval_recognition(Graph g) {
        int[] t = LBFS(g);
        int[] t_new = Functions.transferIE(t);

        int[] tplus = LBFSplus(g, t_new);
        int[] tplus_new = Functions.transferIE(tplus);

        int[] pi = LBFSup(g, tplus_new);
        int[] pi_new = Functions.transferIE(pi);

        int[] piplus = LBFSplus(g, pi_new);
        int[] piplus_new = Functions.transferIE(piplus);

        return intervalOrderingChecking(g, piplus_new, false);

    }

    private static int[] LBFS(Graph g) {
        int[] permutation = IntStream.range(0, g.vertexNum).toArray();
        return basicLBFS(g.adjlist, permutation, 0, null, false, false, false);
    }

    private static int[] LBFSplus(Graph g, int[] permutation) {
        return basicLBFS(g.adjlist, permutation, -1, null, true, false, false);
    }

    private static int[] LBFSdelta(Graph g, int[] out) {
        // this permutation is sorted vertices by degree from small to large
        int[] permutation = getDegreeOrder(g);
        return basicLBFS(g.adjlist, permutation, -1, out, false, true, false);
    }

    private static int[] LBFSup(Graph g, int[] permutation) {
        return basicLBFS(g.adjlist, permutation, -1, null, true, false, true);
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
     * For connected and disconnected graph, the procedure LBFSup and LBFS plus and
     * LBFS are the same.Since if the super node is not the initial list, that means
     * it is processing a connected component.
     * While if finishes a connected component, the super node is the initial linked
     * list, and it should get the vertex according to the corresponding rule.
     * 
     * For LBFS delta, there is a slight different when choosing the out vertex in
     * the initial linked list, it should choose the vertex that is an end vertex.
     * That means we should choose out vertex in the parameter {@code outlist}. So,
     * an additional index pointer is used.
     * 
     * 
     * @param adjgraph    - adjacency list for graph
     * @param permutation - [0,1,...,n] for LBFS
     * @param out         - vertex to take out at the first step, 0 for LBFS
     * @param outlist     - end vertices used for LBFS delta algorithm
     * @param plus        - whether this is LBFS plus algorithm
     * @param delta       - whether this is LBFS delta algorithm
     * @param up          - whether this is LBFS up algorithm
     * @return
     */
    private static int[] basicLBFS(LinkedList<Integer>[] adjgraph, int[] permutation, int out, int[] outlist,
            boolean plus,
            boolean delta, boolean up) {

        int vertexNum = permutation.length;

        // used for LBFS delta
        int outindex = 0;

        // used for LBFS up
        int[] inverse_permutation = Functions.transferIE(permutation);
        // We maintain an array degree_before that degree_before[i] is the number of
        // neighbors of vertex i that are before vertex i in permutation
        // We maintain an array degree_after that degree_after[i] is the number of
        // neighbors of vertex i that are after vertex i in permutation
        int[] degree_before = new int[vertexNum];
        int[] degree_after = new int[vertexNum];
        Arrays.fill(degree_before, 0);
        Arrays.fill(degree_after, 0);
        for (int i = 0; i < vertexNum; i++)
            for (int j : adjgraph[i])
                if (inverse_permutation[j] < inverse_permutation[i])
                    degree_before[i]++;
                else
                    degree_after[i]++;

        // For LBFSplus and LBFSdelta, in step 2.2, when choosing the vertex to be taken
        // out, a certain condition related to the permutation should be satisfied.
        // To enable a O(1) implementation to pick the vertex, we sort the adjacency
        // list by the permutation.
        // Then, since we scan the list of neighbors from left to right in the
        // linkedlist,
        // then order we scan is the exactly the permutation.
        // In this case every small linked list is guaranted to be sorted by
        // permutation, we don't need extra modify in the procedure.

        // we also sort the adj list for normal LBFS, because we also give a permutation
        // by default from 1 to n
        LinkedList<Integer>[] adj = sortAdjacencyLists(adjgraph, permutation);

        // used for lbfs up
        // keep track of the last vertex in t available
        Node[][] adj_copy_track = new Node[vertexNum][vertexNum];
        DoublyLinkedList[] adj_copy = new DoublyLinkedList[vertexNum];
        for (int i = 0; i < vertexNum; i++) {
            adj_copy[i] = new DoublyLinkedList();
            for (int j : adj[i]) {
                Node node = new Node<Integer>(j);
                adj_copy[i].insertAtEnd(null);
                adj_copy_track[i][j] = node;
            }
        }

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

        // int debug =0;

        // LBFS core:
        // Select the vertex to be taken out, move it from the linked list
        // Move all its neighbors in the lists to a higher precedence (adding to get a
        // larger lexicographic label)
        for (int i = 0; i < vertexNum; i++) {

            // lexicographical_linkedlist.display();

            // S ← unvisited vertices with the lexicographically largest label;
            DoublyLinkedList superNode = (DoublyLinkedList) lexicographical_linkedlist.head.element;

            Node outNode = null;// node gonna be taken out

            // 2.2. v ← the last vertex of σ|S;
            // for each small linked list, by implementation it is sorted by permutation
            if (plus == true) {
                // both ok for connected and disconnected
                outNode = superNode.tail;
            } else if (up == true) {
                // both ok for connected and disconnected
                if (superNode.head == superNode.tail) {
                    outNode = superNode.head;
                } else {
                    Node vp = superNode.head;
                    Node vq = superNode.tail;
                    int vp_vertex = (int) vp.element;
                    int vq_vertex = (int) vq.element;

                    if (degree_before[vp_vertex] > 0) {
                        outNode = vp;
                    } else if (degree_after[vq_vertex] > 0) {
                        Node outv = adj_copy[vq_vertex].tail;
                        outNode = nodes[(int) outv.element];
                    } else
                        outNode = vq;
                }

            } else if (delta == true) {
                if (superNode == initial_linkedlist) {
                    // new connected component starts
                    // for each connected component, it should start with the end vertex.
                    outNode = nodes[outlist[outindex]];
                    outindex++;
                    // debug++;
                } else {
                    outNode = superNode.head;
                }
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

                // both ok for connected and disconnected
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

            LinkedList<Node> emptyLists = new LinkedList<>();

            for (int j : adj[outVertex]) {

                if (inverse_permutation[j] > inverse_permutation[outVertex]) {
                    degree_before[j]--;
                } else {
                    degree_after[j]--;
                    adj_copy[j].delete(adj_copy_track[j][outVertex]);
                }

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
        // System.out.println(debug+","+outlist.length);

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
     * @param vertexOrder
     * @param unit        - whether it is a unit interval graph or just interval
     *                    graph checking
     * @return
     */
    public static boolean intervalOrderingChecking(Graph g, int[] vertexOrder, boolean unit) {

        int vertexNum = vertexOrder.length;
        LinkedList<Integer> adj[] = g.adjlist;

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
        // Since Linkedlist add() Method in Java appends the specified element to the
        // end.
        // So, after scanning all vertices and adding to the corresponding adjacency
        // list, we have already sorted the adjacency list.

        LinkedList<Integer>[] newadj = new LinkedList[vertexNum];

        for (int i = 0; i < vertexNum; i++) {
            newadj[i] = new LinkedList<>();
        }

        // sort the adjacent list within each vertex in decreasing order by the
        // vertexOrder
        for (int i = vertexNum - 1; i >= 0; i--) {
            int vertex = vertexOrder[i];
            for (int j : adj[vertex]) {
                newadj[label[j]].add(i);
            }
        }

        // check whether the ith list starts from the first number to i+1.
        // convert the checking to whether the number at position (first - (i+1)) is
        // exactly i+1
        // this for loop is O(m+n)

        // for unit interval graph, we not only need to satisfy the above condition,
        // but also need to check whether it is unit. that is the end point for vertex
        // in order should not decrease
        if (unit) {
            int lastend = -1;
            for (int i = 0; i < vertexNum; i++) {
                LinkedList<Integer> list = newadj[i];
                if (list.isEmpty()) {
                    continue;
                    // return false;
                }
                int end = list.getFirst();
                if (end < lastend)
                    end = i;
                if (end < lastend) {
                    // for(int j = 0; j<list.size(); j++){
                    // System.out.print(list.get(j));
                    // }
                    // System.out.println();
                    // System.out.println(end+" "+ lastend);
                    return false;
                }
                lastend = end;
                // for(int j = 0; j<list.size(); j++){
                // System.out.print(list.get(j));
                // }
                // System.out.println();
            }
        }

        for (int i = 0; i < vertexNum; i++) {
            LinkedList<Integer> list = newadj[i];
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

}