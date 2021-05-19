#! /usr/bin/env python3

import numpy as np
import matplotlib as mpl
import matplotlib.pyplot as plt
import matplotlib.cm as cm
import sys

if __name__ == "__main__":
    DIM = 4
    mtx = np.array(sys.argv[1].split(','))
    mtx = mtx.astype(int)
    print(mtx)
    plt.imsave("orama.bmp", mtx.reshape(DIM, DIM))
