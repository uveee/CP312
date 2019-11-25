import sys

def binary_search(A, left, right):

    if A[right] != A[right-1]:
        return A[right]
    if A[left] != A[left+1]:
        return A[left]

    if (right + left) % 2 == 0:
        mid = (right + left) // 2
    else:
        mid = (right + left) // 2 + 1

    if left == right:
        return A[right]
    if (A[mid] != A[mid-1] and A[mid] != A[mid+1]) or (len(A) == 1):
        return A[mid]
    elif A[mid] != A[mid-1]:
        if (mid-left) % 2 != 0:
            return binary_search(A, left, mid-1)
        else:
            return binary_search(A, mid+1, right)
    elif A[mid] != A[mid+1]:
        if (right-mid) % 2 != 0:
            return binary_search(A, mid+1, right)
        else:
            return binary_search(A, left, mid-1)

if "__main__":

    array = str(input())
    array = list(map(int, array.split()))
    print(binary_search(array, 0, len(array)-1))