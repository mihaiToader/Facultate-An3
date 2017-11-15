class Grammar:
    def __init__(self):
        self.grammar = {}
        self.start = None

    def get_from_string(self, content):
        self.grammar = {}
        try:
            content = content.split("\n")
            for line in content:
                expression = line.split("->")
                if expression[0].strip() in self.grammar:
                    self.grammar[expression[0].strip()] += list(map(lambda x: x.strip(), expression[1].split("|")))
                else:
                    self.grammar[expression[0].strip()] = list(map(lambda x: x.strip(), expression[1].split("|")))
                    if self.start is not None:
                        self.start = expression[0].strip()
        except Exception:
            self.grammar = {}
            print("Incorrect grammar")

    def get_from_file(self, file_name):
        try:
            f = open(file_name, "r")
            self.get_from_string(f.read())
            f.close()
        except FileNotFoundError:
            print("File not found!")

    def set_of_non_terminals(self):
        return list(self.grammar.keys())

    def set_of_terminals(self):
        res = []
        for lista in self.grammar.values():
            for element in lista:
                for atom in list(element):
                    if atom not in self.grammar:
                        res.append(atom)
        return list(set(res))

    def production_of_non_terminal_symbol(self, symbol):
        return self.grammar[symbol]

    # 1 - left grammar, 2-right grammar, 0 - neither
    def get_grammar_type(self):
        right = 0
        left = 0
        for non_terminal in self.set_of_non_terminals():
            for production_element in self.production_of_non_terminal_symbol(non_terminal):
                if len(production_element) > 2:
                    print('Contains more than 2 elements in production: ' + production_element)
                    return 0
                if len(production_element) == 2:
                    first = list(production_element)[0]
                    second = list(production_element)[1]
                    if first in self.set_of_non_terminals() and second in self.set_of_non_terminals():
                        print('Production contain 2 non-terminals: ' + production_element)
                        return 0
                    if first not in self.grammar and second not in self.grammar:
                        print('Production contain 2 terminals: ' + production_element)
                        return 0
                    if first in self.grammar:
                        left += 1
                    if second in self.grammar:
                        right += 1
                else:
                    if production_element in self.set_of_non_terminals():
                        print('Production contain only one non-terminal: ' + production_element)
                        return 0
        if right == 0:
            return 2
        if left == 0:
            return 1
        return 0

    def check_if_regular(self):
        grammar_type = self.get_grammar_type()
        if grammar_type == 1:
            return "Left regular grammar"
        if grammar_type == 2:
            return "Right regular grammar"
        else:
            return "The grammar isn't regular"

    def add_production(self, non_terminal, production):
        if non_terminal in self.grammar:
            self.grammar[non_terminal].append(production)
        else:
            self.grammar[non_terminal] = [production]