from grammar import Grammar
from automaton import Automaton


class Transformation:
    def fromGrammarToAutomaton(self, grammar):
        automat = Automaton()
        if grammar.get_grammar_type() != 0:
            automat.start = grammar.start
            for non_terminal in grammar.set_of_non_terminals():
                productions = grammar.production_of_non_terminal_symbol(non_terminal)
                productions.sort(key=lambda x: len(x), reverse=True)
                for production_element in productions:
                    if len(production_element) == 2:
                        first = list(production_element)[0]
                        second = list(production_element)[1]
                        if first in grammar.set_of_non_terminals():
                            automat.add_state_and_transition(non_terminal, first, second)
                        else:
                            automat.add_state_and_transition(non_terminal, second, first)
                    else:
                        automat.add_final(non_terminal, production_element)
        return automat

    def fromAutomatonToGrammar(self, automaton):
        grammar = Grammar()
        grammar.start = automaton.start
        for from_state in automaton.set_of_states():
            if from_state in automaton.finish:
                grammar.add_production(from_state, '$')
            for to_state in automaton.content[from_state]:
                if to_state in automaton.finish:
                    for value in automaton.content[from_state][to_state]:
                        grammar.add_production(from_state, value)
                for value in automaton.content[from_state][to_state]:
                    grammar.add_production(from_state, str(value) + str(to_state))
        return grammar
