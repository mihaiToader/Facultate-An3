from client import product


class Controller:
    def __init__(self, sockClient, repository):
        self.GET_ALL = "getAll"
        self.BUY_PRODUCTS = "buyProducts"

        self.DELIMITER = ' '
        self.PRODUCT_DELIMITER = '~'

        self.sockClient = sockClient
        self.repo = repository

    def send_message(self, message_to_send):
        self.sockClient.send((message_to_send + "$").encode('ascii'))

    def receive_response(self):
        tm = self.sockClient.recv(8 * 1024 * 1024)
        return tm.decode("ascii")

    def get_products_list_from_response(self, response):
        products_sub_strings = response.split(self.PRODUCT_DELIMITER)
        products = list()
        contor = 1
        for p in products_sub_strings:
            if not ("$" in p) and contor < len(products_sub_strings):
                contor += 1
                product_components = p.split(self.DELIMITER)
                products.append(product.Product(product_components[0], product_components[1],
                                                int(product_components[2]),
                                                int(product_components[3]), product_components[4],
                                                int(product_components[5])))
        return products

    def buy_quantity(self, code, quantity):
        try:
            quantity_int = int(quantity)
            if quantity_int <= 0:
                raise Exception("Quantity must pe a positive number and not 0!")
            self.repo.buy_quantity(code, quantity_int)
        except:
            raise Exception("Insert a valid number on Quantity!")

    def pay_products(self):
        message = self.BUY_PRODUCTS
        all = self.get_all()
        for p in all:
            if p.total > 0:
                message += self.DELIMITER + p.code + self.DELIMITER + p.total
        self.send_message(message)

    def get_all_products_from_server(self):
        message = self.GET_ALL
        self.send_message(message)
        response = self.receive_response()
        prods = self.get_products_list_from_response(response)
        self.repo.cart = prods
        return prods

    def get_all(self):
        return self.repo.get_cart()

    def get_local_shopping_cart_total(self):
        return self.repo.get_cart_total()

    def empty_local_cart(self):
        self.repo.empty_cart()
        self.get_all_products_from_server()
