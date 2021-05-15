#! /usr/bin/env python3

import numpy as np
import matplotlib as mpl
import matplotlib.pyplot as plt
import matplotlib.cm as cm

def gen_normal(size, shape=[8, 8]) :
    w1 = np.random.normal(0, 0.1, size)
    w1 *= 1000
    np.mod(w1, 128)
    w1 = w1.astype(int)
    w1 = np.reshape(w1, shape)
    print(w1)
    return w1

if __name__ == "__main__":
    DIM = 32
    NUM_NEURONS = DIM ** 2
    NUM_WEIGHTS = NUM_NEURONS ** 2

    HIDDEN0 = [0 for x in range(NUM_NEURONS)]
    HIDDEN1 = [0 for x in range(NUM_NEURONS)]

    init_pic = gen_normal(NUM_NEURONS, [NUM_NEURONS])
    weights0 = gen_normal(NUM_WEIGHTS, [NUM_WEIGHTS])
    weights1 = gen_normal(NUM_WEIGHTS, [NUM_WEIGHTS])

    #print(HIDDEN0)
    IN = init_pic
    for nid, _ in enumerate(HIDDEN0):
        for ins, _ in enumerate(init_pic):
            HIDDEN0[nid] = init_pic[ins] * weights0[nid * NUM_NEURONS + ins]
            if HIDDEN0[nid] < 0:
                HIDDEN0[nid] = 0

    #print(HIDDEN0)

    #print(HIDDEN1)
    for nid, _ in enumerate(HIDDEN1):
        for ins, _ in enumerate(HIDDEN0):
            HIDDEN1[nid] = HIDDEN0[ins] * weights0[nid * NUM_NEURONS + ins]
            if HIDDEN1[nid] < 0:
                HIDDEN1[nid] = 0

    #print(HIDDEN1)
    OUT = np.mod(HIDDEN1, 256)
    print(OUT)
    plt.imsave("a.bmp", OUT.reshape(DIM, DIM))#, cmap=cm.gray)
