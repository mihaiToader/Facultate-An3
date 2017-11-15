import random

class Automaton:
    def __init__(self):
        self.content = {}
        self.start = None
        self.finish = []
        self.alphabet = []

    def clear(self):
        self.content = {}
        self.start = None
        self.finish = []
        self.alphabet = []

    def get_from_string(self, content):
        self.clear()
        try:
            content = content.split("\n")
            self.start = content[0].strip()
            self.finish = content[1].split(" ")
            for index in range(2, len(content)):
                expression = list(filter(None, content[index].split(" ")))
                if len(expression) > 3: raise Exception
                if expression[0] not in self.content:
                    self.content[expression[0]] = {}
                if expression[1] not in self.content[expression[0]]:
                    self.content[expression[0]][expression[1]] = [expression[2]]
                else:
                    self.content[expression[0]][expression[1]].append(expression[2])

                self.alphabet = set(list(self.alphabet) + [expression[2]])
        except Exception:
            self.clear()
            print("Incorrect automaton")

    def get_from_file(self, file_name):
        try:
            f = open(file_name, "r")
            self.get_from_string(f.read())
            f.close()
        except FileNotFoundError:
            print("File not found!")

    def set_alphabet(self):
        return sorted(self.alphabet)

    def set_of_states(self):
        states = []
        for state in self.content:
            states += list(self.content[state].keys())
        states += list(self.content.keys())
        return sorted(list(set(states)))

    def all_transitions(self):
        res = ""
        for start in self.content:
            for end in self.content[start]:
                for transition_element in self.content[start][end]:
                    res += start + " - " + transition_element + " - " + end + "\n"
        return res

    def add_state_and_transition(self, state1, state2, value):
        if state1 not in self.content:
            self.content[state1] = {}
        if state2 not in self.content[state1]:
            self.content[state1][state2] = [value]
        else:
            self.content[state1][state1].append(value)
        self.alphabet = set(list(self.alphabet) + [value])

    def add_final(self, state, value):
        if state not in self.finish:
            if value == '$':
                self.finish.append(state)
            else:
                ok = False
                for to_state in self.content[state]:
                    if value in self.content[state][to_state]:
                        if to_state not in self.finish:
                            self.finish.append(to_state)
                        ok = True
                        break
                if not ok:
                    final_state = state + "_final_" + random.randint(1, 10000)
                    self.add_state_and_transition(state, final_state, value)
                    self.finish.append(final_state)
