class Product:
    def __init__(self, name, code, quantity, price, measureUnit, total):
        self.name = name
        self.code = code
        self.quantity = quantity
        self.price = price
        self.measureUnit = measureUnit
        self.total = total

    def __str__(self):
        return "| Name:" + self.name + " - Price: " + str(self.price) + " - Quantity: " + str(self.quantity)+" |"

    def __eq__(self, oth):
        return self.name == oth.nume and self.code == oth.code

    def __hash__(self, *args, **kwargs):
        return super().__hash__(*args, **kwargs)
