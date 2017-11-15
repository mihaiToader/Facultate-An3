from grammar import Grammar
from automaton import Automaton
from transformation import Transformation

def main():
    the_menu()


def print_menu():
    print("0 - exit")
    print("1 - read grammar from file")
    print("2 - set of non-terminals")
    print("3 - set of terminals")
    print("4 - set of productions")
    print("5 - the productions of a given non-terminal symbol")
    print("6 - is the grammar regular?")
    print("7 - reads FA from file")
    print("8 - the set of states")
    print("9 - the alphabet ")
    print("10 - all the transitions")
    print("11 - the set of final state")
    print("12 - from grammar to finite automaton")
    print("13 - from finite automaton to regular grammar")
    print("14 - save grammar to file")
    print("16 - save finite automaton to file")


def the_menu():
    g = Grammar()
    a = Automaton()
    t = Transformation()
    g.get_from_file("grammar1.txt")
    a.get_from_file("automaton1.txt")
    while True:
        cmd = input("M to display menu\n-->")
        if cmd == '0':
            break
        elif cmd == '1':
            g.get_from_file(input("File: "))
        elif cmd == '2':
            print("Set of non terminals:")
            print(' '.join(g.set_of_non_terminals()))
        elif cmd == '3':
            print("Set of terminals:")
            print(' '.join(g.set_of_terminals()))
        elif cmd == 'M':
            print_menu()
        elif cmd == '5':
            try:
                symbol = input("Symbol:")
                production = g.production_of_non_terminal_symbol(symbol)
                print("The productions of a given non-terminal symbol " + symbol)
                print(symbol + " -> " + ' | '.join(production))
            except KeyError:
                print('Symbol doesn\'t exists')
        elif cmd == '4':
            print('Set of productions')
            print(g.start + " -> " + ' | '.join(g.production_of_non_terminal_symbol(g.start)))
            for symbol in g.set_of_non_terminals():
                if symbol != g.start:
                    print(symbol + " -> " + ' | '.join(g.production_of_non_terminal_symbol(symbol)))
        elif cmd == '6':
            print(g.check_if_regular())
        elif cmd == '7':
            a.get_from_file(input("File:"))
        elif cmd == '8':
            print('Set of states:')
            print(' '.join(a.set_of_states()))
        elif cmd == '9':
            print('The alphabet:')
            print(' '.join(a.set_alphabet()))
        elif cmd == '10':
            print('All the transitions:')
            print(a.all_transitions())
        elif cmd == '11':
            print('Set of final states:')
            print(' '.join(a.finish))
        elif cmd == '12':
            print('From current grammar to finite automaton:')
            if g.get_grammar_type() != 0:
                a = t.fromGrammarToAutomaton(g)
                print('Transformation done, use commands for finite automaton to see results')
            else:
                print('Grammar is not regular')
        elif cmd == '13':
            print('From current finite automaton to grammar:')
            g = t.fromAutomatonToGrammar(a)
            print('Transformation done, use commands for grammar to see results')
        elif cmd == '14':
            pass
        elif cmd == '13':
            pass
        else:
            print("I don't know what you want from me!")

main()