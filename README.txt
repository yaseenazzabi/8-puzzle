This program evaluates the reachable search state for any 8-puzzle.

An 8-puzzle looks as follows (S1):

| A  B  C |
| D  E  F |
| G  H  0 |

With '0' denoting a blank square. The letters A-H can be assigned numerical weights in code.

The blank square can be swapped in one tile in any non-diagonal direction.
The goal is to find all states that can be reached from some given state,
such as the one above.

For instance, a reachable state from the state above would be (S2):

| A  B  C |
| D  E  F |
| G  0  H |

There are 9! states for any 8-puzzle. However, the reachable state space for
any 8-puzzle is only (9!/2).

Why? To put it simply, the state graph of an n-puzzle is split into two completely disjoint halves.

Let Nj denote the number of tiles that appear after tiles Ti, i<j that appears after Tj. 
That is, the number of tiles that are assigned lesser values than Ti that appear after Ti, when they should appear before Ti.

| 1  2  3 |
| 4  5  7 |
| 6  0  8 |

In the above example, N7 = 1 because 6 appears after 7 on the tileboard, even though it should appear before it. 7 could be "G" and "6" could be "F".

Now, let N be the sum of all Nj.

It can be concluded that N mod 2 is invariant under any move in the 8-puzzle, which leads to the conclusion that 
an n-puzzle is split into two disjoint halves - one having N mod 2 = 0 and one having N mod 2 = 1.

For a comprehensive review on my the reachable state space of an n-puzzle is (n!/2), read:
Johnson, Wm. Woolsey; Story, William E. (1879), "Notes on the "15" Puzzle", American Journal of Mathematics

