import socket
import traceback

from client import controller
from client import repository

SERVER_ADDRESS = "127.0.0.1"
PORT = 1234


class Client:
    def __init__(self):
        print("start init ")
        self.sockClient = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

        # connection to hostname on the port.
        self.sockClient.connect((SERVER_ADDRESS, PORT))
        print("start init ")
        self.ctrl = controller.Controller(self.sockClient, repositoryRepository())
        print("done init ")

    def run(self):
        while True:
            self.print_menu()
            cmd = self.read_cmd()
            if cmd == 0:
                break
            elif cmd == 1:
                self.afiseaza_produse()
            elif cmd == 2:
                self.buy_prod()
            elif cmd == 3:
                self.plateste()
            elif cmd == 4:
                self.print_cart()

    @staticmethod
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

    @staticmethod
    def print_menu():
        print("--->Meniu<---")
        print("1 - Afiseaza produse")
        print("2 - Comanda produs")
        print("3 - Plateste")
        print("4 - Cos de cumparaturi")
        print("0 - Exit")

    def afiseaza_produse(self):
        try:
            prods = self.ctrl.get_all_products_from_server()
            for p in prods:
                print("     " + str(p))
        except:
            print(traceback.print_exc())

    def buy_prod(self):
        try:
            # update table + add in cart
            code, quantity = self.handle_comanda_produs_ui()
            self.ctrl.buy_quantity(code, quantity)
            # refresh table
        except:
            print(traceback.print_exc())

    def plateste(self):
        self.ctrl.pay_products()
        # clear cart
        self.ctrl.empty_local_cart()

    def read_cmd(self):
        print("Dati comanda: ")
        cmd = input()
        try:
            cmd = int(cmd)
        except:
            print("Comanda trebuie sa fie 0/1/2/3/4. Reincercati...")
            self.print_menu()
            cmd = self.read_cmd()
        return cmd

    def print_cart(self):
        try:
            prods = self.ctrl.repo.cart
            for p in prods:
                print("     " + str(p))
        except:
            print(traceback.print_exc())


if __name__ == "__main__":
    print(1)
    c = Client()
    print(2)
    c.run()
    print(3)
