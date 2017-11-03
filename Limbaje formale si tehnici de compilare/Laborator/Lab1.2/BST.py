class Node:
    def __init__(self, value, left, right):
        self.value = value
        self.right = right
        self.left = left


class BinarySearchTree:
    def __init__(self):
        self.root = None

    def add(self, new_node, key=None):
        if self.root is None:
            self.root = Node(new_node, None, None)
        else:
            if key is None:
                self.__add_to_bts(self.root, new_node, lambda x: x)
            else:
                self.__add_to_bts(self.root, new_node, key)

    def __add_to_bts(self, root, new_value, key):
        if key(root.value) > key(new_value):
            if root.left is None:
                root.left = Node(new_value, None, None)
            else:
                self.__add_to_bts(root.left, new_value, key)
        elif key(root.value) < key(new_value):
            if root.right is None:
                root.right = Node(new_value, None, None)
            else:
                self.__add_to_bts(root.right, new_value, key)

    def __exists_value(self, root, the_value, key):
        if root is None:
            return None
        elif key(root.value) > key(the_value):
            return self.__exists_value(root.left, the_value, key)
        elif key(root.value) < key(the_value):
            return self.__exists_value(root.right, the_value, key)
        else:
            return root

    def __inorder(self, root, value_list=None):
        if value_list is None:
            value_list = []
        if root:
            self.__inorder(root.left, value_list)
            value_list.append(root.value)
            self.__inorder(root.right, value_list)
            return value_list

    def get_inorder(self):
        return self.__inorder(self.root)

    def exists(self, the_value, key):
        node = self.__exists_value(self.root, the_value, lambda x: x) if key is None else self.__exists_value(self.root,
                                                                                                              the_value,
                                                                                                              key)
        return None if node is None else node.value
