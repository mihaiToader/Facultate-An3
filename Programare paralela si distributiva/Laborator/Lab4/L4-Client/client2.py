import socket
import traceback

import controller
import repository

SERVER_ADDRESS = socket.gethostname()
PORT = 9999


class Client:
    def __init__(self):
        self.sockClient = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.ctrl = None

    def run(self):
        # connection to hostname on the port.
        try:
            self.sockClient.connect((SERVER_ADDRESS, PORT))
        except:
            print("Fail to connect to server. Traceback:")
            traceback.print_exc()
            return

        self.ctrl = controller.Controller(self.sockClient, repository.Repository())
        while True:
            self.print_menu()
            cmd = self.read_cmd()
            if cmd == 0:
                break
            elif cmd == 1:
                self.afiseaza_produse()
            elif cmd == 2:
                self.buy_prod()
            else:
                print("Unknown command: %d" % cmd)

            # elif cmd == 3:
            #     self.plateste()
            # elif cmd == 4:
            #     self.print_cart()

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
        # print("3 - Plateste")
        # print("4 - Cos de cumparaturi")
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
            try:
                # self.ctrl.buy_quantity(code, quantity)
                self.ctrl.pay_products_2(code, quantity)
            except Exception as exc:
                print(exc)
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
            prods = self.ctrl.get_cart_prods()
            for p in prods:
                print("     " + str(p))
        except:
            print(traceback.print_exc())


if __name__ == "__main__":
    c = Client()
    c.run()
