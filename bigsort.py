#!/bin/python3

import sys

unsorted = [
    "31415926535897932384626433832795",
    "1",
    "3",
    "10",
    "3",
    "5"]

# inplace non-recursive merge-sort


def lt(left, right):
    if len(left) == len(right):
        return left < right

    if len(left) > len(right):
        return False
    else:
        return True


def merge(arr, start, mid, end):
    buff = arr[start:mid]
    left_idx = 0
    right_idx = mid
    cur_idx = start
    while cur_idx != right_idx:
        cur_left = buff[left_idx]
        if right_idx == end or lt(cur_left, arr[right_idx]):
            arr[cur_idx] = cur_left
            left_idx += 1
        else:
            arr[cur_idx] = arr[right_idx]
            right_idx += 1
        cur_idx += 1
map = {}
boundaries = []
last_end = 0
for idx in range(len(unsorted)-1):
    if lt(unsorted[idx+1], unsorted[idx]):
        boundaries.append((last_end, idx+1))
        last_end = idx+1
boundaries.append((last_end, len(unsorted)))

while len(boundaries) != 1:
    new_boundaries = []
    for i in range(0, len(boundaries)-1, 2):
        (start1, end1) = boundaries[i]
        (start2, end2) = boundaries[i+1]
        merge(unsorted, start1, end1, end2)
        new_boundaries.append((start1, end2))
    if len(boundaries) % 2 != 0:
        new_boundaries.append(boundaries[-1])
    boundaries = new_boundaries


for i in unsorted:
    print(i)
