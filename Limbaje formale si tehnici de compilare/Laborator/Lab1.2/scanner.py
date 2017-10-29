import sys
import re
from prettytable import PrettyTable

codification = {
    "identifier": 0,
    "constant": 1,
    "integer": 2,
    "character": 3,
    "boolean": 4,
    "array": 5,
    "of": 6,
    "string": 7,
    "HI": 8,
    "BYE": 9,
    "DECLARATIONS": 10,
    "BODY": 11,
    "!": 12,
    "if": 13,
    "else": 14,
    "while_loop": 15,
    "in": 16,
    "out": 17,
    "{": 18,
    "}": 19,
    "[": 20,
    "]": 21,
    "(": 22,
    ")": 23,
    ":": 24,
    ";": 25,
    ",": 26,
    " ": 27,
    "\n": 28,
    "+": 29,
    "-": 30,
    "*": 31,
    "/": 32,
    "<": 33,
    "<=": 34,
    "==": 35,
    ">=": 36,
    ">": 37,
    "!=": 38,
    "=": 39,
    "and": 40,
    "or": 41,
    "xor": 42,
    "not": 43,
    "->": 44,
    "<-": 45
}

separators = {
    "!": 12,
    "{": 18,
    "}": 19,
    "[": 20,
    "]": 21,
    "(": 22,
    ")": 23,
    ":": 24,
    ";": 25,
    ",": 26,
    " ": 27,
    "\n": 28
}


class TS:
    def __init__(self):
        self.table = {}

    def exists(self, atom):
        return atom in self.table

    def add(self, atom):
        if self.exists(atom):
            return self.table[atom]
        else:
            poz = self.get_next_position()
            self.table[atom] = poz
            return poz

    def get_next_position(self):
        if len(self.table) == 0:
            return 1
        else:
            return sorted(self.table.values())[-1] + 1


class ProgramTables:
    def __init__(self):
        self.FIP = []
        self.TS = TS()
        self.errors = []
        self.content_by_lines = ""

    def get_error_lines(self, error_element):
        lines = []
        for line in range(len(self.content_by_lines)):
            if error_element in self.content_by_lines[line]:
                lines.append(line + 1)
        return lines

    def display(self, output):
        file = open(output, "w")
        file.write("Tabela de simboluri:\n")
        t = PrettyTable(['Element', 'Cod Ts'])
        for key in self.TS.table:
            t.add_row([key, self.TS.table[key]])
        file.write(str(t))
        file.write("\n\n\nForma interna a programului:\n")
        t = PrettyTable(['Cod atom', 'Cod Ts'])
        for pair in self.FIP:
            if pair[1] == -1:
                t.add_row([pair[0], '-'])
            else:
                t.add_row([pair[0], pair[1]])
        file.write(str(t))
        if self.content_by_lines != "":
            file.write('\n\n\nERRORS:\n')
            for error_element in self.errors:
                file.write('-> ' + error_element + " appear on lines " + str(self.get_error_lines(error_element)))
        file.close()


def split_after_delimiter(delimiter, content):
    delimiter = re.escape(delimiter)
    delimiter = "(" + delimiter + ")"
    rez = []
    for line in content:
        rez += re.split(delimiter, line)

    return rez


def is_identifier(identifier):
    return re.match(r'^[a-zA-Z][a-zA-Z0-9]{0,7}$', identifier)


def is_constant(constant):
    return re.match(r'^[-]?[0-9]*$|^[a-zA-Z]$|^@[0-9A-Za-z@]*@$', constant)


def split_after_separators(content):
    content = [content]
    for key in separators:
        if codification[key] != 0 and codification[key] != 1:
            content = split_after_delimiter(key, content)
            content = list(filter(None, content))
    return content


def scanning(content):
    content = additional_work_for_constants(content)
    content = split_after_separators(content)

    tables = ProgramTables()
    for atom in content:
        if atom in codification:
            tables.FIP.append([codification[atom], -1])
        elif is_identifier(atom):
            tables.FIP.append([codification['identifier'], tables.TS.add(atom)])
        elif is_constant(atom):
            tables.FIP.append([codification['constant'], tables.TS.add(atom)])
        else:
            if atom not in tables.errors:
                tables.errors.append(atom)
    return tables


def additional_work_for_constants(content):
    content = list(content)
    i = 0
    while i < len(content):
        if content[i] == '@':
            i += 1
            while content[i] != '@' and i < len(content):
                if content[i] == ' ':
                    content[i] = '@'
                i += 1
        i += 1
    return "".join(content)


def process_input_file(input_file):
    try:
        file = open(input_file, "r")
    except FileNotFoundError:
        display_error('Input file not found!')
        return

    print("Scanning " + input_file + "...")
    content = file.read()
    tables = scanning(content)
    tables.content_by_lines = content.split('\n')
    return tables


def display_error(message):
    print("[ERROR] - " + message + "\n")


def main():
    if len(sys.argv) != 3:
        display_error('wrong numbers of arguments!')
        return

    input_file = sys.argv[1]
    output_file = sys.argv[2]

    tables = process_input_file(input_file)

    tables.display(output_file)


main()
