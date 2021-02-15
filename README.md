eval_CG
========

This repository contains the artifacts corresponding to the publication - 

```Sriteja Kummita, Goran Piskachev, Johannes Spaeth, and Eric Bodden. 2021. Qualitative and Quantitative Analysis of Callgraph Algorithms for Python. In Proceedings of the First International Conference on Code Quality (ICCQ ’21). To appear.```

* Two experiments are described in the paper, namely, Synthetic Test and Real-world Test. Synthetic test focuses on evaluation the callgraph algorithms in terms of their quality and Real-world test focuses on quantitative evaluation of the callgraph algorithms.

* In both the experiments, three frameworks are considered, namely, [Code2flow](https://github.com/scottrogowski/code2flow), [Pyan](https://github.com/DavidFraser/pyan), and [Wala](https://github.com/wala/WALA). 

* The dynamic callgraph is generated using [PyCallGraph](https://pycallgraph.readthedocs.io/en/master/). This callgraph is considered as the ground truth in both the experiments.

## Run the experiments
* Please refer to the documentation inside each directory (`synthetic_test`, and `real_world_test`) for the instructions to reproduce the resutls discussed in the publication.