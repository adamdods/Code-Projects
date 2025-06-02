from __future__ import print_function
import re
import dfa

def get_dfa_distance_matrix(text,dfa):
    mat = []
    split_pat = re.compile(r"\s+")
    l = 0
    for line in text.strip().split("\n"):
        line = line.strip()
        p = split_pat.split(line)

        v = [0]*(len(p)+1)
        for i in range(len(p)):
            item = p[i]
            if i >= l:
                i += 1
            if '-' in item:
                v[i] = item
            else:
                v[i] = 1
        l += 1
        mat.append(v)

    done = False
    nodes = int(len(mat)**0.5)
    loop_counter = 0
    while not done:
        done = True
        for s in range(len(mat)):
            for t in range(len(mat)):
                if type(mat[s][t]) is not int: 
                    done = False
                    dfa.move(mat[s][t][1], state=s)
                    n = dfa.get_state_index()
                    loop_counter += 1
                    
                    if type(mat[n][t]) is int:
                        loop_counter = 0
                        mat[s][t] = mat[n][t]+1

                    if loop_counter > 10000:
                        raise ValueError("Infinite loop found.")
    return mat

def get_maze_distance_matrix(text, maze_path):

    with open(maze_path) as f:
        rows,cols = map(int,f.readline().strip().split())
    mat = []
    split_pat = re.compile(r"\s+")
     # Generate distance matrix
    queue = []
    l = 0
    for line in text.strip().split("\n"):
        line = line.strip()
        p = split_pat.split(line)
        # print(p)
        v = [0]*(len(p)+1)
        for i in range(len(p)):
            item = p[i]
            if i >= l:
                i += 1
            if '-' in item:
                v[i] = item[1]
            else:
                v[i] = 1
                queue.append((l, i))
        l += 1
        mat.append(v)

    dirs = { # inverses
        'c': (-1,0), # UP
        'd': (0,1),  # RIGHT
        'a': (1,0),  # DOWN
        'b': (0,-1), # LEFT
    }
    while len(queue) > 0:
        next = []
        while len(queue) > 0:
            s,t = queue.pop()
            ndist = mat[s][t] + 1

            sr = s//cols
            sc = s%cols

            for label, dr in dirs.items():
                nr = sr + dr[0]
                nc = sc + dr[1]
                if nr >= 0 and nr < rows and nc >= 0 and nc < cols:
                    n = nr*cols + nc
                    if type(mat[n][t]) is not int and label == mat[n][t]:
                        mat[n][t] = ndist
                        next.append((n,t))
        queue = next
    
    
    
    return mat

if __name__ == '__main__':
    from argparse import ArgumentParser

    parser = ArgumentParser(description="Get shortest math matrix")
    parser.add_argument("input", type=str)
    parser.add_argument("maze", type=str)

    args = parser.parse_args()

    with open(args.input) as f:
        text = f.read()

    for line in get_maze_distance_matrix(text, args.maze):
        print(*line)

    # parser = ArgumentParser(description="Get shortest math matrix")
    # parser.add_argument("input", type=str)
    # parser.add_argument("dfa", type=str)

    # args = parser.parse_args()

    # dfa = dfa.DFA(args.dfa)

    # with open(args.input) as f:
    #     text = f.read()

    # for line in get_dfa_distance_matrix(text, dfa):
    #     print(*line)
