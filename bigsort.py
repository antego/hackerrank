#!/bin/python3

import sys

unsorted = [
    "4",
    "11",
    "110",
    "12",
    "11000"]


# quick sort without recursion
def sort_array(arr, start, end):
    if (end - start) < 2:
        return
    pivot_idx = int((end - start) / 2)
    pivot = arr[pivot_idx]
    cur_el = start
    while cur_el < end:
        cur_str = arr[cur_el]

        if cur_el < pivot_idx and not (len(cur_str) < len(pivot)) and (len(cur_str) > len(pivot) or cur_str > pivot):
            arr.insert(pivot_idx, arr.pop(cur_el))
            pivot_idx -= 1
        elif cur_el > pivot_idx and not (len(cur_str) > len(pivot)) and (len(cur_str) < len(pivot) or cur_str < pivot):
            arr.insert(pivot_idx, arr.pop(cur_el))
            pivot_idx += 1
        else:
            cur_el += 1
    return pivot_idx


stack = []
pivot = sort_array(unsorted, 0, len(unsorted))
stack.append((0, pivot))
stack.append((pivot + 1, len(unsorted)))
while len(stack) > 0:
    start, end = stack.pop()
    if end - start < 2:
        continue
    pivot = sort_array(unsorted, start, end)
    stack.append((start, pivot))
    stack.append((pivot + 1, end))

for i in unsorted:
    print(i)
