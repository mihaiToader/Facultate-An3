class Repository:
    def __init__(self):
        self.cart = list()

    def add_to_cart(self, product):
        for e in self.cart:
            if e.code == product.code:
                e.total += product.quantity
                return
        self.cart.append(product)

    def get_cart_total(self):
        total = 0
        for p in self.cart:
            total += p.price * p.total
        return total

    def empty_cart(self):
        self.cart = list()

    def buy_quantity(self, code, quantity):
        for p in self.cart:
            if p.code == code:
                if p.quantity >= quantity:
                    p.quantity -= quantity
                    p.total += quantity
                else:
                    raise Exception("Too much!!")
