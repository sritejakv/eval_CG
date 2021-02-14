Synthetic Test
==========
Concentrates on evaluating call graph generation algorithm after running them on benchmark suite containing Python files segregated on different language features.

Dockerfile contains the commands in sequence to create a docker image with all the necessary libraries installed.

CallgraphAnalyzer contains the main implementation of generating Wala CG, reading Pyan and Code2flow CGs and recording metrics after comparing them. 

* The framework adapters for Wala, Pyan and Code2flow can be found in `CallgraphAnalyzer/src/main/java/adapters`. 
* The Wala CG engines can be found in `CallgraphAnalyzer/src/main/java/wala`.
* Files in `CallgraphAnalyzer/src/main/java/metrics` contain the implementation to record metrics such as precison, recall and runtime.
* `CallgraphAnalyzer/src/main/java/reports` contain files to write the metrics into a CSV file.
* `CallgraphAnalyzer/src/main/java/utils` contain utility implementation that is required to run the experiment such as converting Wala CG to probe CG, reading resources and constants.

dot2probe contains implementation of converting respective framework CGs (Pyan, Code2flow) to probe format. Converting Wala CG to probe is included in CallgraphAnalyzer.

entry_point_extractor contains implementaion to extract entry points from real world libraries that are used to generate dynamic CG and static CGs. 

BenchmarkSuite contains Python test files for which CGs are generated and compared. Test files are seperated based on different categories.

### To Run

* Create a folder named 'docker_reports' in the 'program_test' directory.
* Checkout the repository and run the following commands from 'program_test' directory.

Build the docker image with the following command

`docker build -t synthetic_test -f Dockerfile .`

Start the container using the following command

`docker-compose -f docker_compose_synthetic_test.yaml up`

Stop the container using the following command

`docker-compose -f docker_compose_synthetic_test.yaml down`

### Results

* Results will be stored as CSV files in the folder named 'docker_reports'. Run the Python files inside 'Plots' directory to generate the graphs.