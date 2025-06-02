import string

def charIndex(ch):
    return (ord(ch)-65)%32

class DFA:
    def __init__(self, path):
        self.state = 0
        with open(path) as f:
            states,edges,transitions = map(int, f.readline().strip().split())
            self.states = string.ascii_uppercase[:states]
            self.edges = string.ascii_lowercase[:edges]
            self.adj = [[] for _ in range(states)]

            for line in range(transitions):
                src,edge,target = f.readline().strip().split()
                self.adj[charIndex(src)].append((charIndex(target),charIndex(edge)))

    def get_state(self):
        return self.states[self.state]

    def get_state_index(self):
        return self.state

    def move(self, edge, state=None):
        if edge not in self.edges:
                raise ValueError("Invalid edge '{}' given.".format(edge))
        if state is None:
            state = self.state
        if type(state) is not int:
            if state not in self.states:
                raise ValueError("Invalid state '{}' given.".format(state))
            state = charIndex(state)
        newstate = None
        for t in self.adj[state]:
            if t[1] == charIndex(edge):
                newstate = t[0]
        
        if newstate is not None:
            self.state = newstate
        else:
            raise ValueError('Invalid transistion @ state \'{}\' with edge \'{}\''.format(self.states[state], edge))

    def set_state(self, state):
        self.state = charIndex(state)


if __name__ == '__main__':
    from argparse import ArgumentParser

    parser = ArgumentParser(description="Test the paths of a shortest path DFA")
    parser.add_argument("dfa", type=str)
    parser.add_argument("targets", type=str)
    parser.add_argument("input", type=str)

    args = parser.parse_args()

    dfa = DFA(args.dfa)

    with open(args.targets) as f:
        init = f.readline().strip()
        targets = []

        for i in range(int(f.readline().strip())):
            t = f.readline().strip()
            targets.append(t)
    
    dfa.set_state(init)
    targets = targets[::-1]
    while len(targets) and targets[-1] == dfa.get_state():
        t = targets.pop()
        print("{} reached.".foramt(t))

    with open(args.input) as f:
        for edge in f:
            edge = edge.strip()
            if edge:
                print(edge)
                dfa.move(edge)

                while len(targets) and targets[-1] == dfa.get_state():
                    t = targets.pop()
                    print("{} reached.".format(t))
    


        