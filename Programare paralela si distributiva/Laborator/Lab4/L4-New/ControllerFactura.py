class ControllerFactura:

    def __init__(self, repo):
        self.repo = repo

    def get_all(self):
        return self.repo.get_all()

    def try_add(self, code, fact):
        return self.repo.try_add(code, fact)

    def get_total(self):
        return self.repo.get_total()

    def add_to_total(self, value):
        return self.repo.add_to_total(value)
