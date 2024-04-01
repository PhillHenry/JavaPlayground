import sys
import re
import math
import matplotlib.pyplot as plt

class Result:
    def __init__(self, x, value, error):
        self.x = x
        self.value = value
        self.error = error

    def __repr__(self):
        return f"{self.x},{self.value},{self.error}"

    def __str__(self):
        return f"{self.x},{self.value},{self.error}"


def read(logs):
    results = []
    with open(logs, "r") as f:
        for line in f.readlines():
            line = line.rstrip()
            elements = line.split(" ")
            n = elements[0]
            value = elements[1]
            error = elements[2]
            results.append(Result(n, value, error))
    return results


def plot_results(results: [Result]):
    print(results)
    xs_log = [math.log10(int(result.x)) for result in results]
    errors_log = [float(result.error) for result in results]
    ys = [float(result.value) for result in results]
    print(list(zip(xs_log, ys)))
    # plt.plot(xs_log, ys)
    plt.errorbar(xs_log, ys, yerr=errors_log, capsize=5, fmt="r--.", ecolor = "black")
    plt.xlabel("Array size (log10)")
    plt.ylabel("Ops/second (10 millions)")
    plt.title("Read throughput of an Array of 64-bit numbers")
    plt.show()


if __name__ == "__main__":
    logs = sys.argv[1]
    print(f"Opening file {logs}")
    results = read(logs)
    plot_results(results)
