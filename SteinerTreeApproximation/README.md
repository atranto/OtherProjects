# EBC2121 - Allocations and Algorithms

_Project description_

_The Steiner tree problem_

## 1 Introduction

In this project we consider the Steiner Tree problem. The problem has been introduced in the lecture. Its formal definition is as follows.
Given a complete, undirected graph G=(V,E), non-negative edge costs c, and a terminal set T⊆V , a Steiner tree is a subset of the edges that connects all terminals in T. The minimum cost Steiner tree problem is to find a Steiner tree of minimum cost.

1. Do a brief literature search on the Steiner tree problem and mention and describe some of the known algorithms to solve it.
2. Use/modify one of the existing methods or come up with your own algorithm to find solutions
to the Steiner tree problem.
3. Implement your algorithm in a programming language of your choice.
4. Experiment with your algorithm and the given instances.
5. Analyze the performance of your algorithm: solution quality and running time.
6. Give a short presentation about your approaches and solutions and write a concise report on the above points.

*Note:* the higher numbered instances have a higher number of nodes, edges and terminals. You can choose to concentrate on either finding an exact approach for the instances with fewer terminals or on the design of a heuristic that can also find solutions to larger instances. Argue in your presentation and report why you made that choice.

## 2 Data sets

There are 100 instances available to test your algorithms and your implementation. The source of these instances is the Parameterized Algorithms and Computational Experiments Challenge (https://pacechallenge.wordpress.com/pace-2018/).

The file format is as follows. The file starts with a line ‘SECTION Graph’. The next two lines are of the form ‘Nodes #nodes’ and ‘Edges #edges’, always in that order, where #nodes is the number of vertices and #edges is the number of edges of the graph. The #edges next lines are of the form ‘E u v w’ where u and v are integers between 1 and #nodes representing an edge between u and v of weight the positive integer w. The following line reads ‘END’ and finishes the list of edges.

There is then a section Terminals announced by two lines ‘SECTION Terminals’ and ‘Terminals #terminals’ where #terminals is the number of terminals. The next #terminals lines are of the form ‘T u’ where u is an integer from 1 to #nodes, which means that u is a terminal. Again, the section ends with the line ‘END’.

SECTION Graph <br>
Nodes 5 <br>
Edges 6 <br>
E 1 2 1 <br>
E 1 4 3 <br>
E 3 2 3 <br>
E 2 4 4 <br>
E 3 5 10 <br>
E 4 5 1 <br>
END <br>
SECTION Terminals <br>
Terminals 2 <br>
T 2 <br>
T 4 <br>
END <br>
EOF

The output file should have the same name as the input file with the ending .sol (i.e. the solution file for instance instance027.gr should be named instance027.sol). It should start with the line ‘VALUE x’ where x is the weight of the found Steiner tree. The next lines are of the form ‘u v’ where u and v are two vertices of the graph linked by an edge and lists all the edges of the found Steiner tree. Here is a small example.

VALUE 20 <br>
1 3 <br>
3 5 <br>
7 3 <br>
10 7 <br>
7 22 <br>
