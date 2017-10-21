import sys
import re

codification = {
    "identifier": 0,
    "constant": 1,
    "int": 2,
    "char": 3,
    "array": 4,
    "HI": 5,
    "BYE": 6,
    "if": 7,
    "elif": 8,
    "else": 9,
    "while": 10,
    "in": 11,
    "out": 12,
    "{": 13,
    "}": 14,
    "[": 15,
    "]": 16,
    "(": 17,
    ")": 18,
    ";": 19,
    "+": 20,
    "-": 21,
    "*": 22,
    "/": 23,
    "<": 24,
    "<=": 25,
    "=": 26,
    ">=": 27,
    ">": 28,
    "!=": 29,
    "->": 30,
    "<-": 31,
    "DECLARATIONS": 32,
    "BODY": 33
}


class Program_Tables:
    def __init__(self):
        self.pif = {}
        self.st = {}


def is_identifier(identifier):
    # ToDo implement is_identifier
    return True


def is_constant(constant):
    # ToDo implement is_constant
    return True


def scanning(content):
    # Split after space
    content = additional_work_for_constants(content)
    content = content.split(" ")
    content = list(filter(None, content))
    content = list(map(lambda x: x.replace("\n", ""), content))

    print(content)


def additional_work_for_constants(content):
    content = list(content)
    i = 0
    while i < len(content):
        if content[i] == '@':
            i += 1
            while content[i] != '@' and i < len(content):
                if content[i] == ' ':
                    content[i] = '&'
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
    scanning(content)


def display_error(message):
    print("[ERROR] - " + message + "\n")


def main():
    if len(sys.argv) != 3:
        display_error('wrong numbers of arguments!')
        return

    input_file = sys.argv[1]
    output_file = sys.argv[2]

    process_input_file(input_file)


main()
