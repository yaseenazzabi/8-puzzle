This program evaluates the reachable search state for any 8-puzzle.

An 8-puzzle looks as follows (S1):

| A  B  C |
| D  E  F |
| G  H  0 |

With '0' denoting a blank square.

The blank square can be swapped in one tile in any non-diagonal direction.
The goal is to find all states that can be reached from some given state,
such as the one above.

For instance, a reachable state from the state above would be (S2):

| A  B  C |
| D  E  F |
| G  0  H |

There are 9! states for any 8-puzzle. However, the reachable state space for
any 8-puzzle is only (9!/2). This is because of a quality called
'permutation inversions'.

To learn more, see the simple proof written here:

https://cs.stackexchange.com/questions/16515/reachable-state-space-of-an-8-puzzle

Or, for a more comprehensive review, read:

Johnson, Wm. Woolsey; Story, William E. (1879), "Notes on the "15" Puzzle", American Journal of Mathematics

