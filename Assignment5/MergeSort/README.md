*Activity 1*

**Task 1**
1. The structure of the code provides nodes, branches and starts that make up primarily the function of the code.
A NetworkUtils is provided as a common place for network operations, allowing for reuse of networking code. Node 
is a super class to Branch and Sorter with the Sorter acting as a working node and Branch dividing up the work between
nodes and branches that are connected to other branches. The overall advantage is being able to divide the computational 
work between multiple nodes, but its only a main advantage when the data set being worked on is large. The disadvantage is
CPU time that is taking care of networking communications and waiting to send and receive packages. Which, the full system
on a local host, does not take a long amount of time since it does not have to route through the internet. Essentially, the
same functionality with this code, running totally on one local system, is slightly less effective than using threads (due
to having to communicate through ports on a system). On a broader scale, for a large data set it is efficient computationally,
but can be really limited by networking. Especially in the case of using merge sort which is O(n log n) time, already a pretty compu-
tationally efficient algorithm. It would prove much better in the event that the sort algorithm was something like bubble sort
that is O(n^2) time.

2.
Data (elements - time in miliseconds):
	14	    54
	14	    59
	40	    143
	52	    169
	10	    41
	1000	7342
	1000	5011
	500	    2421
	1500	7703
	1500	7519
	750	    5751
	800	    3375

Graph is also provided in the main folder for Assignment 5. Titled:

Overall, looking at a graph of the data, it appears to be working in more a linear fashion than in an n log n fashion. This could be
due to the data sets not being really large.

3.
2 branches, 3 nodes
Data (elements number - time in miliseconds):
	14	    170
	14	    255
	1000	14454
	1000	8395
	500	    4951
	750	    5063
	250	    2078
	250	    2122
	1500	9714
	2000	14042

3 branches, 4 nodes
Data (elements number - time in miliseconds):
	14	405
	14	315
	100	2163
	250	4042
	500	8343
	750	10307

Graphs provided as an imagine file in Assignment5 directory:
2 branch, 3 node graph:
3 branch, 4 node graph: Acticity1MargeSort3Branch4Nodes.png

It appears that the performance of time gets worst when the merge sort is distributed between more and more nodes and branches.
We see that to sort 750 elements, it takes 5063 ms with 3 nodes and 10307 ms with 4 nodes. This is probably due to the latency 
in communicating information between the nodes, branches and the starter program. 

4.
The traffic on wireshark is very large for the program to run. The branch, nodes, and starter are returning values to one another
one traffic call at a time, meaning a lot of time is spent just retreiving single values. A way to reduce traffic would be to send
data in batches. Instead of a node returning one sorted value at a time over the network, sending their whole sorted array back to the
branch node which will merge it with the whole result of the sorted half by the other node.

**Task 2**
1.
I do expect to see changes in runtime. Network traffic is going to have to be routed through an actual network, meaning messages passed between
an unknown number of servers with an unknown throughput time. The time I expect is also going to be variable depending on how the traffic is routed.
As an example, Doing a traceroute on my machine to google, it took 7 hops to get to google. From another location and another time, it could take
5 hops, 2 hops or 14 hops. Also, there is a time penalty for routing through a server. According to traceroute, some nodes took up to 90ms, while
others took 20 ms. 

2.

**Task 3**
1.

2.
