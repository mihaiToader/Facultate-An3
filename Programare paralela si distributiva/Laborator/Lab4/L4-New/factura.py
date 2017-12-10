import datetime


class Factura:
    date = datetime.datetime.now()
    products = list()
    total = 0

    def __init__(self, total, date=datetime.datetime.now(), products=list()):
        self.date = date
        self.products = products
        self.total = total

    def __str__(self):
        log = str(self.date) + ""
        for p in self.products:
            log += " " + str(p)
        log += " total factura: " + str(self.total)
        return log
