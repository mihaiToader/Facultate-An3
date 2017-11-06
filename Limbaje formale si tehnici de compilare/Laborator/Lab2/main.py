from grammar import Grammar

def main():
    theMenu()


def theMenu():
    g = Grammar()
    while True:
        cmd = input("-->")
        if cmd == 'exit':
            break
        elif 'read' in cmd:
            g.getFromFile(cmd.split()[1])
        elif 'grammar non-terminals' == cmd:
            print(g.setOfNonTerminals())
        elif 'grammar terminals' == cmd:
            print(g.setOfTerminals())
        elif 'grammar production of' in cmd:
            try:
                print(g.productionOfNonTerminalSymbol(cmd.split()[3]))
            except KeyError:
                print('Symbol doesn\'t exists')
        else:
            print("I don't know what you want from me!")
main()