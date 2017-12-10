import os
import random
import socket
import threading
import time
import string
import threading
import traceback
from queue import Queue
from threading import Thread
import datetime
from controller_factura import ControllerFactura
from ControllerProduct import ControllerProducts
from RepositoryFactura import RepositoryFactura
from RepositoryProduct import RepositoryProducts
import logger
from factura import Factura
from product import Product
from Utils import *

DELIMITER = ' '
PRODUCT_DELIMITER = '~'
GETALL = "getAll"
BUY_PRODUCTS = "buyProducts"


class Worker(Thread):
    """ Thread executing tasks from a given tasks queue """

    def __init__(self, tasks):
        Thread.__init__(self)
        self.tasks = tasks
        self.daemon = True
        self.start()

    def run(self):
        while True:
            func, arg1, arg2, arg3 = self.tasks.get()
            try:
                func(arg1, arg2, arg3)
            except Exception as e:
                # An exception happened in this thread
                print(e)
            finally:
                # Mark this task as done, whether an exception happened or not
                self.tasks.task_done()


class ThreadPool:
    """ Pool of threads consuming tasks from a queue """

    def __init__(self, num_threads):
        self.tasks = Queue(num_threads)
        for _ in range(num_threads):
            Worker(self.tasks)

    def add_task(self, func, arg1, arg2, arg3):
        """ Add a task to the queue """
        self.tasks.put((func, arg1, arg2, arg3))

    def map(self, func, arg1, arg2, arg3):
        """ Add a list of tasks to the queue """
        # for args in args_list:
        self.add_task(func, arg1, arg2, arg3)

    def wait_completion(self):
        """ Wait for completion of all the tasks in the queue """
        self.tasks.join()


class Server:
    def __init__(self):
        self.logger = logger.get_logger()
        self.host = socket.gethostname()
        self.port = 9999
        self.repositoryFactura = RepositoryFactura()
        self.repositoryProduct = RepositoryProducts()
        self.controllerFactura = ControllerFactura(self.repositoryFactura)
        self.controllerProduct = ControllerProducts(self.repositoryProduct)
        self.pool = ThreadPool(5)
        self.th_no = 0
        self.serversocket = socket.socket(
            socket.AF_INET, socket.SOCK_STREAM)

        # get local machine name
        host = socket.gethostname()

        port = 9999

        # bind to the port
        self.serversocket.bind((host, port))

        # queue up to 5 requests
        self.serversocket.listen()

    def run(self):
        # self.generate_random_actions()

        from apscheduler.schedulers.background import BackgroundScheduler
        scheduler = BackgroundScheduler()
        scheduler.add_job(self.generate_random_actions, 'interval', seconds=5)
        scheduler.add_job(self.periodic_verification, 'interval', seconds=5)
        scheduler.start()
        self.main_loop()
        print('Press Ctrl+{0} to exit'.format('Break' if os.name == 'nt' else 'C'))

        try:
            # This is here to simulate application activity (which keeps the main thread alive).
            while True:
                time.sleep(2)
        except (KeyboardInterrupt, SystemExit):
            # Not strictly necessary if daemonic mode is enabled but should be done if possible
            scheduler.shutdown()
        # set_interval(self.generate_random_actions, 10)
        # set_interval(self.periodic_verification, 5)
        # while True:
        #     # establish a connection
        #     clientsocket, addr = self.serversocket.accept()
        #     print("Got a connection from %s" % str(addr))
        #     self.pool.map(self.client_handler, clientsocket, self.th_no, addr)
        #     self.th_no += 1

    # method handler for each client request separatly
    def client_handler(self, client_socket, th_no, addr):
        print("Got a connection from %s" % str(addr))
        # current_time = time.ctime(time.time()) + "\r\n"
        # client_socket.send(current_time.encode('ascii'))
        self.start(client_socket, th_no)
        client_socket.close()

    def start(self, clientSocket, clientNo):
        # each client has his thread
        try:
            while True:
                tm = None
                try:
                    tm = clientSocket.recv(1024)
                except:
                    self.logger.debug("client closed the connection")
                    break
                if not tm:
                    self.logger.debug("client closed the connection")
                    break
                data_from_client = tm.decode("ascii")
                cmd, strings = self.split_data_from_client(data_from_client, clientNo)
                if cmd == GETALL:
                    self.logger.debug("Client: %s , Command: %s; Strings: *%s*" % (str(clientNo), str(GETALL), strings))
                    all_products_response = self.make_response(self.controllerProduct.get_all(), clientNo)
                    self.send_response_to_client(clientSocket, all_products_response)
                elif cmd == BUY_PRODUCTS:
                    self.logger.debug("Client: %s , Command: %s, Strings: *%s*" %
                                      (str(clientNo), str(BUY_PRODUCTS), strings))
                    import asyncio

                    async def slow_operation(future):
                        message = self.buy_products(strings, clientNo)
                        future.set_result(message)

                    loop = asyncio.new_event_loop()
                    asyncio.set_event_loop(loop)
                    future = asyncio.Future()
                    asyncio.ensure_future(slow_operation(future))
                    loop.run_until_complete(future)
                    self.send_response_to_client(clientSocket, future.result())
                    loop.close()
                else:
                    self.logger.error("Invalid Command: " + str(cmd))
        except Exception as exc:
            exception = "> Exception: " + str(exc)
            self.logger.debug(exception)
            self.logger.debug(traceback.print_exc())
        dis_msg = "> Client: " + str(clientNo) + " disconnected."
        self.logger.debug(dis_msg)

    @staticmethod
    def send_response_to_client(client_socket, response):
        # send response to client
        client_socket.send(response.encode('ascii'))

    # send product list
    def make_response(self, products, clientNo):
        response = ""
        for p in products:
            response += p.name + DELIMITER + p.code + DELIMITER + str(p.quantity) + DELIMITER + str(p.price) \
                        + DELIMITER + p.measure_unit + DELIMITER + str(p.total) + PRODUCT_DELIMITER

        self.logger.debug("> Client: " + str(clientNo) + " response: " + response)
        return response

    def buy_products(self, strings, clientNo):
        buyed_products = list()
        total = 0
        self.logger.debug(">>Client %s buy: %s" % (str(clientNo), strings))
        message = "Successfuly buyed: %s" % strings
        i = 1
        while i < len(strings):
            # for i in range(1, len(strings)):
            code = strings[i]
            i += 1
            quantity = int(strings[i])
            i += 1
            product_found = self.controllerProduct.buy_product(code, quantity)
            if product_found is None:
                message = "Product with code: " + code + " and quantity: " + \
                          str(quantity) + " not enough available quantity"
                self.logger.debug(">Client: " + str(clientNo) + message)
                return message
            else:
                new_product = Product(product_found.name,
                                      product_found.code,
                                      product_found.quantity,
                                      product_found.price,
                                      product_found.measure_unit,
                                      product_found.total
                                      )
                new_product.quantity = quantity
                buyed_products.append(new_product)
                total += quantity * new_product.price
        factura = Factura(total, products=buyed_products)
        if self.controllerFactura.try_add(generate_random_string(), factura):
            log = "> Client: " + str(clientNo) + " - " + str(factura)
            self.controllerFactura.add_to_total(total)
            log += " --- total casa de marcat: " + str(self.controllerFactura.get_total())
            self.logger.debug(log)
            message = str(factura)
        else:
            self.logger.error("Error adding factura: " + str(factura))
        return message

    def split_data_from_client(self, dataFromClient, clientNo):
        if len(dataFromClient) > 0:
            # read client data
            value = dataFromClient
            value = value.replace("$", "")
            strings = value.split(DELIMITER)
            # self.logger.debug client command on server
            command = strings[0]
            msg = "> Client: " + str(clientNo) + " executed: " + command + " data: " + dataFromClient
            self.logger.debug(msg)
            return command, strings
        return None, None

    def make_factura(self, threadNo):
        sum_total = 0
        buyed_products = list()
        code = str(random.randint(0, self.repositoryProduct.PRODUCT_NO -1))
        self.logger.debug("Buy %s" % code)
        quantity = 1
        product_found = self.controllerProduct.buy_product(code, quantity)
        # buy it, returned elem
        if product_found is None:
            self.logger.debug("> TEST " + str(threadNo) + " : Product with code: " + str(code) +
                              " and quantity: " + str(quantity) + " not enough available quantity")
        else:
            new_product = Product(product_found.name,
                                  product_found.code,
                                  product_found.quantity,
                                  product_found.price,
                                  product_found.measure_unit,
                                  product_found.total)
            # making new object, to not alter the one in repo!!
            new_product.quantity = quantity  # set quantity how much user buyed buyedProducts.Add(newProduct)
            sum_total += new_product.quantity * product_found.price

        fact = Factura(sum_total, products=buyed_products)
        res = self.controllerFactura.try_add(generate_random_string(), fact)
        while not res:
            res = self.controllerFactura.try_add(generate_random_string(), fact)
        # self.logger.debug(res)
        if res:
            log = "> TEST  " + str(threadNo) + " : " + str(fact)
            self.controllerFactura.add_to_total(sum_total)
            log += " ---- total casa de marcat: " + str(self.controllerFactura.get_total())
            self.logger.debug(log)

    def generate_random_actions(self):
        t1 = threading.Thread(target=self.make_factura(1))
        t2 = threading.Thread(target=self.make_factura(2))
        t3 = threading.Thread(target=self.make_factura(3))
        t1.start()
        t2.start()
        t3.start()
        t1.join()
        t2.join()
        t3.join()

    def periodic_verification(self):
        if len(self.controllerFactura.get_all()) > 0:
            sum_total = 0
            for f in self.controllerFactura.get_all():
                sum_total += f.total
            log = ""
            tot = self.controllerFactura.get_total()
            sum_total -= tot
            if sum_total == tot:
                log = ">PERIODIC VERIFICATION SUCCESS: " + str(sum_total) + " " + str(tot) + " correct periodic total!"
            else:
                log = ">PERIODIC VERIFICATION FAILED: " + str(sum_total) + " " + str(tot) + " incorrect periodic total!"
            self.logger.debug(log)

    def main_loop(self):
        while True:
            # establish a connection
            clientsocket, addr = self.serversocket.accept()
            print("Got a connection from %s" % str(addr))
            self.pool.map(self.client_handler, clientsocket, self.th_no, addr)
            self.th_no += 1

# if __name__ == "__main__":
serv = Server()
serv.run()
