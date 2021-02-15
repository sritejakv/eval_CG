Synthetic Test
==========

This experiment evaluates the callgraphs on a benchmark suite of Python programs. The benchmark suite contains 49 programs divided into 13 categories based on literature and Python specification (mainly focusing on constructs that involve function calls).

## Implementation structure
+ BenchmarkSuite
    - Contains synthetic Python programs (divided into 13 categories) focusing on qualitative evaluation of Python callgraphs.
+ CallgraphAnalyzer
    - Central component of the experiment which initiates the experiment.
    - Contains a FrameworkAdapter for each framework, namely, Code2flow, Pyan, and Wala (`CallgraphAnalyzer/src/main/java/adapters`).
    - After comparing static callgraphs with the dynamic callgraphs, it calculates precision and recall (`CallgraphAnalyzer/src/main/java/metrics`).
+ dot2probe
    - This artifact contains dot format to probe format converter implementations of Pyan, Code2flow, and PyCallGraph which are used by FrameworkAdapters in CallgraphAnalyzer.
+ jython
    - Contains the Jython library which is used to build Wala.
+ Plots
    - Contains Python scripts to generate plots from the results.
+ Others
    - code2flow.sh
        - Contains commands to run code2flow algorithm on each file in the BenchmarkSuite to generate C2F_CG and store it in `CallgraphAnalyzer/src/main/resources/code2flow`.
    - pyan.sh
        - Contains commands to run Pyan algorithm on each file in the BenchmarkSuite to generate PyanCG and store it in `CallgraphAnalyzer/src/main/resources/pyan`.
    - pycallgraph.sh
        - Contains commands to run PyCallGraph on each file in the BenchmarkSuite to generate dynamic callgraph and store it in `CallgraphAnalyzer/src/main/resources/pycallgraph`.
    - generate_cg.sh
        - Executes `code2flow.sh`, `pyan.sh`, and `pycallgraph.sh`.
    - Dockerfile
        - Contains the commands in sequence to create a docker image with all the necessary libraries installed.
    - docker_compose_synthetic_test.yaml
        - Contains commands to run the docker container and hence run the experiment.

## To Run
* Create a folder named 'docker_reports' in the synthetic_test directory.

* Build the docker image with the following command

        docker build -t synthetic_test -f Dockerfile .

* Start the container using the following command

        docker-compose -f docker_compose_synthetic_test.yaml up

* Stop the container using the following command

        docker-compose -f docker_compose_synthetic_test.yaml down

## Results

* Results will be stored as CSV files in the folder named 'docker_reports'. Run the Python files inside 'Plots' directory to generate the graphs.