from __future__ import print_function
from argparse import ArgumentParser

from glob import glob
import os
import sys
import string
import csv
from io import StringIO

from maze import *
from dfa import *
from dist import *

class Marker:
    def __init__(self, title):
        self.title = title
        self.tests = {}
        self.headers = None

        self.current = None
        self.comments = None
        self.results = None
    
    def test(self, name):
        self.save()
        self.current = name
        self.comments = []
        self.results = {}
    
    def save(self):
        if self.current and self.headers is None:
            self.headers = list(self.results.keys())
        if self.current:
            self.tests[self.current] = (self.results, self.comments)
            self.current = None

    def comment(self, comment):
        self.comments.append(comment)

    def get_test_marks(self, test):
        results = self.tests[test][0]
        return [int(v) if t == 0 else v for v,_,_,t in results.values()]

    def get_test_mark(self, test):
        results = self.tests[test][0]
        wt = sum([w for _,w,_,t in results.values() if t == 0])
        cap = min([c for v,c,_,t in results.values() if t == 1 and v]+[1])
        return min(sum([v*w for v,w,_,_ in results.values()])/wt, cap)
    
    def get_final_mark(self):
        return sum(map(self.get_test_mark, self.tests.keys()))/len(self.tests)
    
    def mark(self, name, value, weight=1, description=None):
        if self.headers:
            if name not in self.headers:
                raise ValueError("Invalid mark name.")
        if name in self.results:
            weight = weight if weight != 1 else self.results[name][1]
            description = description or self.results[name][2]
        self.results[name] = (value, weight, description, 0)

    def cap(self, name, value, cap, description=None):
        if self.headers:
            if name not in self.headers:
                raise ValueError("Invalid mark name.")
        if name in self.results:
            cap = cap if cap != 1 else self.results[name][1]
            description = description or self.results[name][2]
        self.results[name] = (value, cap, description, 1)

    
    def csv(self):
        with StringIO() as csvfile:
            writer = csv.writer(csvfile)
            writer.writerow(["Test name"] + self.headers + ["Total"])
            for test in self.tests:
                marks = self.get_test_marks(test)
                writer.writerow([test] + [100*v for v in marks] + [100*self.get_test_mark(test)])
            contents = csvfile.getvalue()
            return contents

    def __str__(self):
        # Header
        s = "## {}\n----------------------------\n\n".format(self.title)

        if len(self.tests) == 0:
            return s + "No tests yet."

        # Criteria
        weights = []
        maxdesc = len("Description")
        master_test = next(iter(self.tests.values()))
        for header in self.headers:
            mark = master_test[0][header]
            # Description lengths
            if mark[2]:
                maxdesc = max(maxdesc, len(mark[2]))
            # Description lengths
            if mark[3] == 0:
                weights.append(mark[1])

        totalweight = sum(weights)
        
        s += "### Criteria\n\n"
        form = "|{{:12}}|{{:10}}|{{:{}}}|\n".format(maxdesc)
        s += form.format("Name", "Weight", "Description")
        s += form.format('-'*12, '-'*10, '-'*maxdesc)
        form = "|{{:12}}|{{:>5.1f}}%    |{{:{}}}|\n".format(maxdesc)
        form2 = "|{{:12}}|{{:>5.1f}}% cap|{{:{}}}|\n".format(maxdesc)

        for header in self.headers:
            mark = master_test[0][header]
            if mark[3] == 1:
                s += form2.format(header, 100*mark[1], mark[2] or "")
            else:
                s += form.format(header, 100*mark[1]/totalweight, mark[2] or "")
        
        # Results

        s += "### Results\n\n"
        cols = len(self.headers) + 1
        s += ("|{:20}|"+"{:>12} |"*cols).format('Test', *(self.headers+["Marks"])) + "\n"
        s += ("|{:20}|"+"{:>12} |"*cols).format('-'*20, *['-'*12 for x in range(cols)]) + "\n"
        # Lines
        max_mark = sum(weights)
        cf = [master_test[0][h] for h in self.headers]
        cf = ["      {:>5.1f}% |" if t == 0 else "       {:>5s} |" for _,_,_,t in cf]
        cf = "".join(cf)
        form = "|{:20}|" + cf + "      {:>5.1f}% |\n"
        bool2yn = lambda x: "Yes" if x else "No"
        for test in self.tests.keys():
            marks = self.get_test_marks(test)
            s += form.format(test, *([100*v if type(v) is int else bool2yn(v) for v in marks] + [100*self.get_test_mark(test)]))
            
        # Summary
        s += ("|{:20}|"+"{:>12} |"*cols + "\n").format(
            "**Total**", *(list(' '*(cols-1)) + ["**{:>.1f}%**".format(100*self.get_final_mark())])
        )

        # Comments
        added_heading = False
        for test in self.tests:
            comments = self.tests[test][1]
            if comments:
                if not added_heading:
                    s += "### Comments\n\n"
                    added_heading = True
                s += " - {}:\n".format(test)
                for comment in comments:
                    s += "\t - {}\n".format(comment)
        return s

# Mode 0 & 1
def test_matrix(out, exp, test_path, marker, weight_multiplier=1, dtype='dfa'):
    """
    Marks:
    - Correct output: 0.3
    - Shortest paths: 0.7
    """
    marker.mark("Format", 0, weight=0.3*weight_multiplier,
        description="Formatting of the table")
    marker.mark("Paths", 0, weight=0.7*weight_multiplier,
        description="Correctness of the shortests paths")

    # Format check
    format_mark = 1
    lines = out.split("\n")
    for i in range(len(lines)):
        line = lines[i]
        # No blank lines
        if not line:
            continue
        # Check lengths
        if (len(line)+1)%3 != 0:
            marker.comment("Wrong spacing on line {}".format(i))
            format_mark = 0
            break
        # Check values
        for j in range(0,len(line),3):
            if line[j] not in " -":
                marker.comment("Invalid character {} on line {}, col {}".format(line[j], i, j))
                format_mark = 0

            if line[j+1] not in string.ascii_lowercase:
                if not (line[j+1] == " " and j//3 == i):
                    marker.comment("Invalid character {} on line {}, col {}".format(line[j+1], i ,j+1))
                    format_mark = 0

            if j+2 < len(line) and line[j+2] != ' ':
                marker.comment("Invalid character {} on line {}, col {} (expected whitespace)".format(line[j+2], i, j+2))
                format_mark = 0

    try:
        if dtype == 'dfa':
            dfa = DFA(os.path.join(test_path, "dfa.in"))
            outmat = get_dfa_distance_matrix(out, dfa)
            expmat = get_dfa_distance_matrix(exp, dfa)
        else:
            outmat = get_maze_distance_matrix(out, os.path.join(test_path, "maze.in"))
            expmat = get_maze_distance_matrix(exp, os.path.join(test_path, "maze.in"))

        total = 0
        correct = 0
        if len(expmat) != len(outmat):
            marker.comment("Too many rows in input (actual: {}, expected: {})".format(len(outmat), len(expmat)))
            return marker
        for i in range(len(expmat)):
            if len(expmat[i]) != len(outmat[i]):
                marker.comment("Too many columns on row {} in input (actual: {}, expected: {})".format(i, len(outmat[i]), len(expmat[i])))
                return marker
            
            total += len(expmat)-1
            for j in range(len(expmat)):
                if expmat[i][j] >= outmat[i][j] and expmat[i][j] != 0:
                    correct += 1

        marker.mark("Format", format_mark)
        if total:
            marker.mark("Paths", correct/total)
        else:
            marker.mark("Paths", 1)
        return marker
    except Exception as e:
        if type(e) is ValueError:
            marker.comment("Could not parse input: {}".format(e))
        else:
            marker.comment("Could not parse input")
        return marker

def test_dfa_matrix(out, exp, test_path, marker, weight_multiplier=1):
    return test_matrix(out, exp, test_path, marker, weight_multiplier, dtype='dfa')
def test_maze_matrix(out, exp, test_path, marker, weight_multiplier=1):
    return test_matrix(out, exp, test_path, marker, weight_multiplier, dtype='maze')

# Mode 2
def run_dfa(input, dfa, init, targets):
    targets = targets[::-1]
    # Init DFAs
    dfa.set_state(init)

    moves = 0
    move_tracker = []

    while len(targets) and targets[-1] == dfa.get_state():
        t = targets.pop()
        move_tracker.append(moves)
        moves = 0
    if input.strip():
        for edge in input.strip().split("\n"):
            edge = edge.strip()
            if edge:
                dfa.move(edge)
                moves += 1

                while len(targets) and targets[-1] == dfa.get_state():
                    t = targets.pop()
                    move_tracker.append(moves)
                    moves = 0
            else:
                raise ValueError("Input error: Found whitespace instead of edge.")
    return move_tracker, len(targets), moves
def test_simon(out, exp, test_path, marker, weight_multiplier=1):
    """
    Marks:
    - Correct format and no extra moves: 0.1
    - Number of targets reached: 0.35
    - Shortest paths: 0.55
    """
    marker.mark("Format", 0, weight=0.1*weight_multiplier,
        description="Correct format and no extra moves")
    marker.mark("Targets", 0, weight=0.35*weight_multiplier,
        description="Number of targets reached")
    marker.mark("Paths", 0, weight=0.55*weight_multiplier,
        description="Correctness of the shortests paths")

    dfa = DFA(os.path.join(test_path, "dfa.in"))
    # Load targets
    with open(os.path.join(test_path, "targets.in")) as f:
        init = f.readline().strip()
        targets = []

        for i in range(int(f.readline().strip())):
            t = f.readline().strip()
            targets.append(t)
    # Run DFAs
    exp_move_counts, exp_targets_left, exp_last_move_count = run_dfa(exp, dfa, init, targets)
    try:
        out_move_counts, out_targets_left, out_last_move_count = run_dfa(out, dfa, init, targets)

        # Check targets reached
        if out_targets_left != 0:
            marker.comment("Reached {}/{} targets.".format(len(targets)-out_targets_left, len(targets)))
        marker.mark("Targets", (len(targets)-out_targets_left)/len(targets))
        # Moving after targets
        if out_targets_left == 0 and out_last_move_count != 0:
            marker.comment("Moved after finishing targets.")
            marker.mark("Format", 0.4)
        else:
            marker.mark("Format", 1)
        
        correct_paths = 0
        for i in range(len(targets)):
            if i < len(out_move_counts):
                if exp_move_counts[i] >= out_move_counts[i]:
                    correct_paths += 1
                else:
                    marker.comment("Shorter path exists for target {}.".format())
        marker.mark("Paths", correct_paths/len(targets))
        return marker
    except Exception as e:
        if type(e) is ValueError:
            marker.comment("Could not parse input: {}".format(targets[i]))
        else:
            marker.comment("Could not parse input")
        return marker

# Mode 3
def run_maze(input, maze, init, targets):
    targets = targets[::-1]
    # Init DFAs
    maze.set_position(*init)
    
    moves = 0
    move_tracker = []

    while len(targets) and targets[-1] == maze.get_position():
        t = targets.pop()
        move_tracker.append(moves)
        moves = 0
    if input.strip():
        for edge in input.strip().split("\n"):
            edge = edge.strip()
            if edge:
                maze.move(edge)
                moves += 1

                while len(targets) and targets[-1] == maze.get_position():
                    t = targets.pop()
                    move_tracker.append(moves)
                    moves = 0
            else:
                raise ValueError("Input error: Found whitespace instead of edge.")
    return move_tracker, len(targets), moves
def test_maze(out, exp, test_path, marker, weight_multiplier=1):
    """
    Marks:
    - Correct format and no extra moves: 0.1
    - Number of targets reached: 0.35
    - Shortest paths: 0.55
    """
    marker.mark("Format", 0, weight=0.1*weight_multiplier,
        description="Correct format and no extra moves")
    marker.mark("Targets", 0, weight=0.35*weight_multiplier,
        description="Number of targets reached")
    marker.mark("Paths", 0, weight=0.55*weight_multiplier,
        description="Correctness of the shortests paths")

    maze = Maze(os.path.join(test_path, "maze.in"))
    # Convert targets to format
    topos = lambda t: [ord(t[0])-ord('A'), ord(t[1])-ord('A')]
    frompos = lambda t: chr(t[0]+ord('A'))+chr(t[1]+ord('A'))
    # Load targets
    with open(os.path.join(test_path, "targets.in")) as f:
        init = topos(f.readline().strip())
        targets = []

        for i in range(int(f.readline().strip())):
            t = f.readline().strip()
            targets.append(topos(t))
    # Run DFAs
    exp_move_counts, exp_targets_left, exp_last_move_count = run_maze(exp, maze, init, targets)
    try:
        out_move_counts, out_targets_left, out_last_move_count = run_maze(out, maze, init, targets)

        # Check targets reached
        if out_targets_left != 0:
            marker.comment("Reached {}/{} targets.".format(len(targets)-out_targets_left, len(targets)))
        marker.mark("Targets", (len(targets)-out_targets_left)/len(targets))
        # Moving after targets
        if out_targets_left == 0 and out_last_move_count != 0:
            marker.comment("Moved after finishing targets.")
            marker.mark("Format", 0.4)
        else:
            marker.mark("Format", 1)
        
        correct_paths = 0
        for i in range(len(targets)):
            if i < len(out_move_counts):
                if exp_move_counts[i] >= out_move_counts[i]:
                    correct_paths += 1
                else:
                    marker.comment("Shorter path exists for target {}.".format(frompos(targets[i])))
        marker.mark("Paths", correct_paths/len(targets))
        return marker
    except Exception as e:
        if type(e) is ValueError:
            marker.comment("Could not parse input: {}".format(e))
        else:
            marker.comment("Could not parse input")
        return marker
    
# Mode 4
def run_labelled_maze(input, maze, dfa, init, targets):
    targets = targets[::-1]
    # Init DFAs
    maze.set_position(*init)
    dfa.set_state(maze.get_label())

    maze_moves = 0
    dfa_moves = 0
    dfa_move_tracker = []
    maze_move_tracker = []

    # Get rid of targets that you start at
    while len(targets) and targets[-1] == maze.get_position():
        t = targets.pop()
        maze_move_tracker.append(maze_moves)
        maze_moves = 0
        # DFA reached multiple targets (We only care in total transistions)
        dfa_move_tracker.append(dfa_moves)
        dfa_moves = 0
    if input.strip():
        for edge in input.strip().split("\n"):
            edge = edge.strip()
            if edge:
                if edge in string.ascii_lowercase: # DFA movement
                    dfa.move(edge)
                    dfa_moves += 1

                else: # Maze movement
                    maze.move(edge, dfa.get_state())
                    maze_moves += 1

                    while len(targets) and targets[-1] == maze.get_position():
                        t = targets.pop()
                        maze_move_tracker.append(maze_moves)
                        maze_moves = 0
                        # DFA reached multiple targets (We only care in total transistions)
                        dfa_move_tracker.append(dfa_moves)
                        dfa_moves = 0
            else:
                raise ValueError("Input error: Found whitespace instead of edge.")
    return maze_move_tracker, dfa_move_tracker, len(targets), maze_moves, dfa_moves
def test_traversal(out, exp, test_path, marker, weight_multiplier=1):
    """
    Marks:
    - Correct format and no extra moves: 0.1
    - Number of targets reached: 0.35
    - Shortest paths: 0.55
    """
    marker.mark("Format", 0, weight=0.1*weight_multiplier,
        description="Correct format and no extra moves")
    marker.mark("Targets", 0, weight=0.35*weight_multiplier,
        description="Number of targets reached")
    marker.mark("DFA Paths", 0, weight=0.55*weight_multiplier,
        description="Correctness of the shortests paths for the dfa")
    marker.mark("Maze Paths", 0, weight=0.55*weight_multiplier,
        description="Correctness of the shortests paths for the maze")

    maze = LabelledMaze(os.path.join(test_path, "maze.in"))
    dfa = DFA(os.path.join(test_path, "dfa.in"))
    # Convert targets to format
    topos = lambda t: [ord(t[0])-ord('A'), ord(t[1])-ord('A')]
    frompos = lambda t: chr(t[0]+ord('A'))+chr(t[1]+ord('A'))
    # Load targets
    with open(os.path.join(test_path, "targets.in")) as f:
        init = topos(f.readline().strip())
        targets = []

        for i in range(int(f.readline().strip())):
            t = f.readline().strip()
            targets.append(topos(t))
    # Run DFAs
    exp_run = run_labelled_maze(exp, maze, dfa, init, targets)
    try:
        out_run = run_labelled_maze(out, maze, dfa, init, targets)

        out_maze_move_counts = out_run[0]
        out_dfa_move_counts = out_run[1]
        out_targets_left = out_run[2]
        out_last_maze_move_count = out_run[3]
        out_last_dfa_move_count = out_run[4]
        
        exp_maze_move_counts = exp_run[0]
        exp_dfa_move_counts = exp_run[1]
        exp_targets_left = exp_run[2]
        exp_last_maze_move_count = exp_run[3]
        exp_last_dfa_move_count = exp_run[4]
        
        # Check targets reached
        if out_targets_left != 0:
            marker.comment("Reached {}/{} targets.".format(len(targets)-out_targets_left, len(targets)))
        marker.mark("Targets", (len(targets)-out_targets_left)/len(targets))
        # Moving after targets
        format_mark = 1
        if out_targets_left == 0 and out_last_maze_move_count != 0:
            marker.comment("Moved in maze after finishing targets.")
            format_mark -= 0.4
        if out_targets_left == 0 and out_last_dfa_move_count != 0:
            marker.comment("Changed DFA state after finishing targets.")
            format_mark -= 0.4
        marker.mark("Format", format_mark)
        # Check paths
        # Maze
        correct_paths = 0
        target_errors = [False]*len(targets)
        for i in range(len(targets)):
            if i < len(out_maze_move_counts):
                if exp_maze_move_counts[i] >= out_maze_move_counts[i]:
                    correct_paths += 1
                else:
                    target_errors[i] = True
                    marker.comment("Shorter path exists for target {} in the maze.".format(frompos(targets[i])))
        marker.mark("Maze Paths", correct_paths/len(targets))
        # DFA
        correct_paths = 0
        for i in range(len(exp_dfa_move_counts)):
            if i < len(out_dfa_move_counts):
                if exp_dfa_move_counts[i] >= out_dfa_move_counts[i]:
                    correct_paths += 1
                else:
                    if not target_errors[i]:
                        marker.comment("Shorter path exists in DFA.")
        marker.mark("DFA Paths", correct_paths/len(exp_dfa_move_counts))
        return marker
    except Exception as e:
        if type(e) is ValueError:
            marker.comment("Could not parse input: {}".format(e))
        else:
            marker.comment("Could not parse input")
        return marker

parser = ArgumentParser(description="System to mark the RW214 2020 project.")
parser.add_argument('mode', type=int)
parser.add_argument('--test-path', '-p', type=str, default="tests")
parser.add_argument('--rubric-path', '-r', type=str, default=None)
parser.add_argument('--csv-path', type=str, default=None)

args = parser.parse_args()

# headers = ["Compiled", "No errors", "Correct"]
# weights = [1, 3, 6]
# Collect results
test_function = [
    test_dfa_matrix,
    test_maze_matrix,
    test_simon,
    test_maze,
    test_traversal
][args.mode]
title = [
    "(0) Print DFA",
    "(1) Print maze DFA",
    "(2) Simon says",
    "(3) Solve maze",
    "(4) Traverse maze",
][args.mode]

marker = Marker(title)
for test in glob("{}/{}/*".format(args.test_path, args.mode)):
    test = os.path.basename(test)
    marker.test(test)
    # print("Marking {}".format(test))
    try:
        with open("out/{}.out".format(test)) as f:
            output = f.read()
        with open("out/{}.err".format(test)) as f:
            errors = f.read()
        with open("{}/{}/{}/answer.out".format(args.test_path, args.mode, test)) as f:
            expected = f.read()
        """
        Based on erros we will cap at 60%
        """
  
        marker.cap("Errors", True if errors.strip() else False, 0.6, description="Program executed with errors")
        marker.mark("Compiled", "Compilation error" not in output,
            weight=1.0, description="Program compiled with no errors")
        test_function(output, expected, "{}/{}/{}".format(args.test_path, args.mode, test),
            marker, weight_multiplier=9)

        marker.save()
        
    except Exception as e:
        print("Failed to mark: {}".format(test), file=sys.stderr)

if args.rubric_path:
    with open(args.rubric_path, 'a') as f:
        f.write(str(marker))
if args.csv_path:
    with open(args.rubric_path, 'a') as f:
        f.write(str(marker.csv()))
print(marker.get_final_mark())
