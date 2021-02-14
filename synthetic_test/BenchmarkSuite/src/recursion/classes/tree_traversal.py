"""
:testcase_name tree_traversal
:author Sriteja Kummita
:script_type Class
:description Class, Traversals contains tree traversal methods like inorder, preorder and postorder are
implemented recursively
"""


class Node:
    def __init__(self, v):
        self.left = None
        self.value = v
        self.right = None


class Traversals:
    def __init__(self):
        self.traversed_list = []

    def inorder(self, root):
        if root:
            self.inorder(root.left)
            self.traversed_list.append(root.value)
            self.inorder(root.right)

    def preorder(self, root):
        if root:
            self.traversed_list.append(root.value)
            self.preorder(root.left)
            self.preorder(root.right)

    def postorder(self, root):
        if root:
            self.preorder(root.left)
            self.preorder(root.right)
            self.traversed_list.append(root.value)


if __name__ == '__main__':
    root = Node(1)
    root.left = Node(4)
    root.right = Node(7)
    root.left.left = Node(2)
    root.left.right = Node(3)
    root.right.left = Node(5)
    root.right.right = Node(6)
    obj = Traversals()
    obj.inorder(root)
    obj2 = Traversals()
    obj2.postorder(root)
