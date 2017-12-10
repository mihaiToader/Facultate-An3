# client.py
import socket

# create a socket object
import time

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# get local machine name
host = socket.gethostname()

port = 9999

# connection to hostname on the port.
s.connect((host, port))

# Receive no more than 1024 bytes
i =0
while True:
    tm = s.recv(1024)
    time.sleep(1)
    i += 1
    if i >10:
        break
s.close()

print("The time got from the server is %s" % tm.decode('ascii'))


def handle_comanda_produs_ui():
    print("Dati codul produsului")
    cod_p = input()
    print("Dati cantitatea")
    cant = input()
    try:
        cant = int(cant)
        if cant < 0:
            raise ValueError
    except:
        print("Cantitatea trebuie sa fie numar intreg pozitiv")
        return None, None
    return cod_p, cant


def menu():
    print("--->Meniu<---")
    print("1 - Comanda produs")


