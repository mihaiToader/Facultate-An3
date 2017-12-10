import factura


class RepositoryFactura:

        def __init__(self):
            self.TOTAL_CASH_KEY = "total"
            self.sales = dict()
            self.sales[self.TOTAL_CASH_KEY] = factura.Factura(0)

        def try_add(self, code, fact):
            self.sales[code] = fact
            return True

        def get_total(self):
            t = None
            try:
                t = self.sales[self.TOTAL_CASH_KEY].total
            except:
                pass
            return t

        def add_to_total(self, value):
            elem = self.get_total()
            if elem is not None:
                self.sales[self.TOTAL_CASH_KEY].total += value
                return True
            return False

        def get_all(self):
            all = list()
            for f in self.sales.values ():
                if f.date is not None:
                    all.append(f)
            return all
