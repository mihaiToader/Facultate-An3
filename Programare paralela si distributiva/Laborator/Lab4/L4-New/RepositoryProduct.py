import product


class RepositoryProducts:

    def __init__(self):

        self.productNames = ["p1", "p2", "p3", "p4", "p5"]
        self.PRODUCT_NO = 5
        self.products = dict()
        self.ensure_data_in_repo()

    def ensure_data_in_repo(self):
        for i in range(self.PRODUCT_NO):
            self.products[str(i)] = product.Product(self.productNames[i], str(i), 10, (i+1)*10, "kg", 0)

    def get_all(self):
        all_products = list()
        for p in self.products.values():
            all_products.append(p)
        return all_products

    def buy_product(self, code, quantity):
        available_quantity = self.get_available_quantity_of_product(code)
        if available_quantity is not None:
            available_quantity -= quantity
            if available_quantity < 0:
                return None
        else:
            return None

        self.set_prod_new_quantity(code, available_quantity)
        return self.get_product_by_code(code)

    def get_available_quantity_of_product(self, code):
        for p in self.products.values():
            if p.code == code:
                print("Return quantity: " + str(p.quantity))
                return p.quantity
        print("Code not found: " + code)
        return None

    def set_prod_new_quantity(self, code, available_quantity):
        for p in self.products.values():
            if p.code == code:
                p.quantity = available_quantity
                return

    def get_product_by_code(self, code):
        for p in self.products.values():
            if p.code == code:
                return p
        return None
