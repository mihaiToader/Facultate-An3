class Automaton:
    def __init__(self):
        self.content = {}
        self.start = []
        self.finish = []
        self.alphabet = []

    def get_from_string(self, content):
        try:
            content = content.split("\n")
            self.start = content[0].split(" ")
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
            self.content = {}
            self.start = []
            self.finish = []
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
