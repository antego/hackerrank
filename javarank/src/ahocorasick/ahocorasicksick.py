#!/bin/python

import sys

class AhoNode:
    ''' Вспомогательный класс для построения дерева
    '''
    def __init__(self):
        self.goto = {}
        self.out = set()
        self.fail = None
        self.weight = 0


def aho_create_forest(patterns, weights):
    '''Создать бор - дерево паттернов
    '''
    root = AhoNode()

    for idx, path in enumerate(patterns):
        node = root
        for symbol in path:
            node = node.goto.setdefault(symbol, AhoNode())
        node.out.add(path)
        node.weight = node.weight + weights[idx]
    return root


def aho_create_statemachine(patterns, weights):
    '''Создать автомат Ахо-Корасика.
    Фактически создает бор и инициализирует fail-функции
    всех узлов, обходя дерево в ширину.
    '''
    # Создаем бор, инициализируем
    # непосредственных потомков корневого узла
    root = aho_create_forest(patterns, weights)
    queue = []
    for node in root.goto.values():
        queue.append(node)
        node.fail = root

    # Инициализируем остальные узлы:
    # 1. Берем очередной узел (важно, что проход в ширину)
    # 2. Находим самую длинную суффиксную ссылку для этой вершины - это и будет fail-функция
    # 3. Если таковой не нашлось - устанавливаем fail-функцию в корневой узел
    while len(queue) > 0:
        rnode = queue.pop(0)

        for key, unode in rnode.goto.items():
            queue.append(unode)
            fnode = rnode.fail
            while fnode is not None and key not in fnode.goto:
                fnode = fnode.fail
            unode.fail = fnode.goto[key] if fnode else root
            unode.out |= unode.fail.out

    return root


def aho_find_all(s, root, callback):
    '''Находит все возможные подстроки из набора паттернов в строке.
    '''
    node = root

    for i in range(len(s)):
        while node is not None and s[i] not in node.goto:
            node = node.fail
        if node is None:
            node = root
            continue
        node = node.goto[s[i]]
        for pattern in node.out:
            callback(node.weight, pattern)


############################
# Демонстрация работы алгоритма
count = 0
def on_occurence(weight, pattern):
    global count
    count += weight




if __name__ == "__main__":
    file = open("/home/anton/projects/hackerrank/javarank/src/ahocorasick/input7", "r")

    n = int(file.readline().strip())
    genes = file.readline().strip().split(' ')
    health = list(map(int, file.readline().strip().split(' ')))
    s = int(file.readline().strip())
    mincount = 9999999999999
    maxcount = 0
    for a0 in range(s):
        first, last, d = file.readline().strip().split(' ')
        first, last, d = [int(first), int(last), str(d)]

        count = 0
        root = aho_create_statemachine(genes[first:last+1], health[first:last+1])
        aho_find_all(d, root, on_occurence)
        if count < mincount:
            mincount = count
        if count > maxcount:
            maxcount = count
    print(mincount, maxcount)