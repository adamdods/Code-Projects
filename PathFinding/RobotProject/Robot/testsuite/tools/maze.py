
dirs = {
    'up': 1 << 0,
    'right': 1 << 1,
    'down': 1 << 2,
    'left': 1 << 3
}
dirmap = {
    'a': 'up',
    'b': 'right',
    'c': 'down',
    'd': 'left'
}
class Maze:
    @staticmethod
    def to_position(state):
        return [ord(state[0])-ord('A'), ord(state[1])-ord('A')]
    @staticmethod
    def from_position(t):
        return chr(t[0]+ord('A'))+chr(t[1]+ord('A'))

    def __init__(self, path):
        self.pos = [0,0]
        with open(path) as f:
            rows,cols = map(int, f.readline().strip().split())

            f.readline() # Remove top of maze

            mat = [[0]*cols for x in range(rows)]
            for r in range(rows):
                # Horizontal links
                line = f.readline().strip()
                for j in range(1, cols):
                    if line[j*4] != '|':
                        mat[r][j] |= dirs['left']
                        mat[r][j-1] |= dirs['right']


                # Vertical links
                if(r < rows-1):
                    line = f.readline().strip()
                    for c in range(cols):
                            if line[c*4+1] != '-':
                                mat[r][c] |= dirs['down']
                                mat[r+1][c] |= dirs['up']
        self.mat = mat

    def get_position(self):
        return self.pos

    def move(self, m):
        if m not in dirs:
            raise ValueError('Invalid edge \'{}\''.format(m))
        if dirs[m] & self.mat[self.pos[0]][self.pos[1]] == 0:
            raise ValueError('Invalid move')

        d = {
            'up':       (-1,0),
            'right':    (0,1),
            'down':     (1,0),
            'left':     (0,-1),
        }[m]

        self.pos[0] += d[0]
        self.pos[1] += d[1]

    def set_position(self, r, c):
        self.pos = [r,c]
    
    def __str__(self):
        s = ""
        for line in self.mat:
            s += " ".join(map("{:02}".format, line)) + "\n"
        return s[:-1]

class LabelledMaze(Maze):
    def __init__(self, path):
        self.pos = [0,0]
        with open(path) as f:
            rows,cols,_ = map(int, f.readline().strip().split())

            f.readline() # Remove top of maze

            labels = [[None]*cols for x in range(rows)]
            mat = [[0]*cols for x in range(rows)]
            for r in range(rows):
                # Horizontal links
                line = f.readline().strip()
                for j in range(0, cols):
                    labels[r][j] = line[j*4+2]
                    if j > 0:
                        if line[j*4] != '|':
                            mat[r][j] |= dirs['left']
                            mat[r][j-1] |= dirs['right']


                # Vertical links
                if(r < rows-1):
                    line = f.readline().strip()
                    for c in range(cols):
                            if line[c*4+1] != '-':
                                mat[r][c] |= dirs['down']
                                mat[r+1][c] |= dirs['up']
        self.mat = mat
        self.labels = labels

    def get_position(self):
        return self.pos

    def move(self, m, state):
        if m not in dirs:
            raise ValueError('Invalid edge \'{}\''.format(m))
        if dirs[m] & self.mat[self.pos[0]][self.pos[1]] == 0:
            raise ValueError('Invalid move')

        d = {
            'up':       (-1,0),
            'right':    (0,1),
            'down':     (1,0),
            'left':     (0,-1),
        }[m]

        self.pos[0] += d[0]
        self.pos[1] += d[1]
        if state != self.labels[self.pos[0]][self.pos[1]]:
            raise ValueError("Invalid transistion - not in the correct state for {}.".format(Maze.from_position(self.pos)))
            self.pos[0] -= d[0]
            self.pos[1] -= d[1]

    def set_position(self, r, c):
        self.pos = [r,c]

    def get_label(self):
        return self.labels[self.pos[0]][self.pos[1]]

    
    def __str__(self):
        s = ""
        for line in self.mat:
            s += " ".join(map("{:02}".format, line)) + "\n"
        for line in self.labels:
            s += " ".join(line) + "\n"
        return s[:-1]

if __name__ == '__main__':
    from argparse import ArgumentParser

    parser = ArgumentParser(description="Test the paths of a shortest path DFA")
    parser.add_argument("maze", type=str)

    args = parser.parse_args()

    maze = LabelledMaze(args.maze)
    print(maze)
#     from argparse import ArgumentParser
#     from draw_maze import *

#     parser = ArgumentParser(description="Test the paths of a shortest path DFA")
#     parser.add_argument("maze", type=str)
#     parser.add_argument("state", type=str)
#     parser.add_argument("input", type=str)

#     args = parser.parse_args()

#     maze = Maze(args.maze)
#     print(maze)

#     def topos(state):
#         return [ord(state[0])-ord('A'), ord(state[1])-ord('A')]

#     with open(args.state) as f:
#         targets = []
#         init = topos(f.readline())
#         for i in range(int(f.readline())):
#             targets.append(topos(f.readline()))

#     maze.set_position(*init)

#     drawer = drawer_from_path(args.maze)
#     images = [drawer.get_image(), drawer.get_avatar_image(*init)]

#     targets = targets[::-1]
#     while len(targets) and maze.get_position() == targets[-1]:
#         t = targets.pop()
#         print('Reached {}'.format(t))

#     drawer.set_target(*targets[-1])

#     with open(args.input) as f:
#         for line in f:
#             maze.move(line.strip())
#             images.append(drawer.get_avatar_image(*maze.get_position()))
#             while len(targets) and maze.get_position() == targets[-1]:
#                 t = targets.pop()
#                 if len(targets): drawer.set_target(*targets[-1])
#                 print('Reached {}'.format(t))

#     dur = 5000//len(images)
#     create_gif("trash/out.gif", images, duration=dur)