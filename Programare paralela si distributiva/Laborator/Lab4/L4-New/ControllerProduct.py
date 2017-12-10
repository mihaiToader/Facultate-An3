class ControllerProducts:
    def __init__(self, repo):
        self.repo = repo

    def get_all(self):
        return self.repo.get_all()

    def buy_product(self, code, quantity):
        return self.repo.buy_product(code, quantity)
