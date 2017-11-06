class Grammar:
    def __init__(self):
        self.grammar = {}

    def getFromString(self, content):
        content = content.split("\n")
        for line in content:
            expression = line.split("->")
            self.grammar[expression[0]] = expression[1].split("|")

    def getFromFile(self, fileName):
        f = open(fileName, "r")
        self.getFromString(f.read())
        f.close()

    def setOfNonTerminals(self):
        return self.grammar.keys()

    def setOfTerminals(self):
        res = []
        for lista in self.grammar.values():
            for element in lista:
                ok = True
                for atom in list(element):
                    if atom in self.grammar:
                        ok = False
                        break
                if ok:
                    res.append(element)
        return list(set(res))

    def productionOfNonTerminalSymbol(self, symbol):
        return self.grammar[symbol]
