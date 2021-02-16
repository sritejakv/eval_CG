FROM openkbs/jdk-mvn-py3
RUN sudo apt-get update && sudo apt-get install git
RUN sudo apt-get install -y mercurial
RUN sudo apt-get install -y ant
RUN sudo apt-get install -y rsync
RUN sudo apt-get install -y graphviz 
RUN sudo pip install future
RUN sudo pip install coverage plotly_express==0.4.0 requests pytest matplotlib scipy pandas numpy

WORKDIR /real_world_test
RUN sudo chmod -R 777 .

#Begin installing WALA
RUN sudo git clone https://github.com/sritejakv/WALA.git
RUN sudo chmod -R 777 WALA
RUN cd WALA && ./gradlew publishToMavenLocal
RUN sudo git clone https://github.com/wala/IDE.git
RUN sudo chmod -R 777 IDE
RUN cd IDE/com.ibm.wala.cast.lsp && mvn clean install -DskipTests

COPY ./jython jython
RUN mkdir -p $(mvn help:evaluate -Dexpression=settings.localRepository -q -DforceStdout)/org/python/jython/
RUN cp -r jython/build2/repo/org $(mvn help:evaluate -Dexpression=settings.localRepository -q -DforceStdout)
RUN git clone https://github.com/juliandolby/jython3.git
RUN sudo chmod -R 777 jython3
RUN cd jython3 && ant
RUN mkdir -p $(mvn help:evaluate -Dexpression=settings.localRepository -q -DforceStdout)/org/python/jython3/0.0.1-SNAPSHOT/
RUN mv jython3/dist/jython-dev.jar $(mvn help:evaluate -Dexpression=settings.localRepository -q -DforceStdout)/org/python/jython3/0.0.1-SNAPSHOT/jython3-0.0.1-SNAPSHOT.jar

RUN sudo git clone https://github.com/wala/ML.git
RUN sudo chmod -R 777 ML

RUN cd ML && mvn clean install -DskipTests
#End installing WALA

RUN sudo git clone -b probe https://github.com/sritejakv/pycallgraph.git && cd pycallgraph && sudo git pull && sudo python3 setup.py install

RUN sudo git clone -b callgraph_fixes https://github.com/sritejakv/code2flow.git && cd code2flow && sudo python3 setup.py install

RUN sudo git clone -b callgraph https://github.com/sritejakv/pyan.git && cd pyan && sudo git pull

RUN echo "Copying real_world_test resources"
COPY ./entry_point_extractor entry_point_extractor
RUN sudo chmod -R 777 ./entry_point_extractor
COPY ./CallgraphAnalyzer CallgraphAnalyzer
RUN sudo chmod -R 777 ./CallgraphAnalyzer
COPY ./dot2probe dot2probe
RUN sudo chmod -R 777 ./dot2probe
COPY checkout_realworld_libraries.sh checkout_realworld_libraries.sh
COPY pyan.sh pyan.sh
COPY generate_pyan_pruned_entry_points.sh generate_pyan_pruned_entry_points.sh
COPY code2flow.sh code2flow.sh

#Begin - Checking out real world libraries and copying them to entry_point_extractors
RUN mkdir -p ./entry_point_extractor/src/main/resources/realWorldLibSrc/
RUN sh checkout_realworld_libraries.sh
#End - Checking out real world libraries and copying them to entry_point_extractors

#Start - Pruning the entry points
RUN mkdir -p ./entry_point_extractor/src/main/resources/prunedEntryPoints/pyan/
RUN bash generate_pyan_pruned_entry_points.sh
RUN cd entry_point_extractor && mvn clean install -DskipTests

WORKDIR /real_world_test/entry_point_extractor
RUN java -cp target/entry_point_extractor-1.1-SNAPSHOT.jar CompareEntryPoints mitmproxy PythonRobotics cookiecutter ycm thefuck
RUN echo "Copying entry points to CallgraphAnalyzer"
RUN mkdir -p ../CallgraphAnalyzer/src/main/resources/entryPoints
RUN sudo rsync -r commonEntryPoints/ ../CallgraphAnalyzer/src/main/resources/entryPoints
#End - Pruning the entry points

#Begin - generating dynamic (PyCallGraph) call graph for each of the libraries
WORKDIR /real_world_test
RUN mkdir ./entry_point_extractor/src/main/resources/pycallgraphs/
RUN cd mitmproxy && find test -name "test*.py" > mitmproxy_tc.txt
RUN cd mitmproxy && python3 dynamicCG.py mitmproxy_tc.txt
RUN sudo rsync -r mitmproxy/dotFiles/ ./entry_point_extractor/src/main/resources/pycallgraphs/mitmproxy/

RUN cd cookiecutter && find tests -name "test*.py" > cookiecutter_tc.txt
RUN cd cookiecutter && python3 dynamicCG.py cookiecutter_tc.txt
RUN sudo rsync -r cookiecutter/dotFiles/ ./entry_point_extractor/src/main/resources/pycallgraphs/cookiecutter/

RUN cd YouCompleteMe && find python/ycm/tests -name "*.py" > ycm_tc.txt
RUN cd YouCompleteMe && python3 run_tests.py --no-flake8 --fromFile
RUN sudo rsync -r YouCompleteMe/dotFiles/ ./entry_point_extractor/src/main/resources/pycallgraphs/ycm/

RUN cd thefuck && find tests -name "*.py" > thefuck_tc.txt
RUN cd thefuck && python3 dynamicCG.py thefuck_tc.txt
RUN sudo rsync -r thefuck/dotFiles/ ./entry_point_extractor/src/main/resources/pycallgraphs/thefuck/

RUN cd PythonRobotics && find tests -name "test*.py" > PythonRobotics_tc.txt
RUN cd PythonRobotics && python3 dynamicCG.py PythonRobotics_tc.txt
RUN sudo rsync -r ./PythonRobotics/dotFiles/ ./entry_point_extractor/src/main/resources/pycallgraphs/PythonRobotics/
#End - generating dynamic (PyCallGraph) call graph for each of the libraries

WORKDIR /real_world_test/entry_point_extractor
RUN mvn clean install
RUN echo "Merging dot graphs"
RUN java -cp target/entry_point_extractor-1.1-SNAPSHOT.jar MergeDotGraphs PythonRobotics mitmproxy cookiecutter ycm thefuck
RUN echo "Copying the merged pycallgraphs to CallgraphAnalyzer"
RUN sudo rsync -r src/main/resources/merged_pycallgraphs/ ../CallgraphAnalyzer/src/main/resources/pycallgraph

WORKDIR /real_world_test
RUN echo "Generating pyan call graph into CallgraphAnalyzer"
RUN sudo bash pyan.sh
RUN sudo bash code2flow.sh
RUN mkdir -p $(mvn help:evaluate -Dexpression=settings.localRepository -q -DforceStdout)/probe/probe/1.0
RUN cp CallgraphAnalyzer/libs/probe.jar $(mvn help:evaluate -Dexpression=settings.localRepository -q -DforceStdout)/probe/probe/1.0/probe-1.0.jar
RUN mkdir -p $(mvn help:evaluate -Dexpression=settings.localRepository -q -DforceStdout)/gxl/gxl/1.0
RUN cp CallgraphAnalyzer/libs/gxl.jar $(mvn help:evaluate -Dexpression=settings.localRepository -q -DforceStdout)/gxl/gxl/1.0/gxl-1.0.jar
RUN cd dot2probe && mvn clean install -DskipTests

WORKDIR /real_world_test/CallgraphAnalyzer
RUN mvn clean install -DskipTests
# CMD tail -f /dev/null
CMD sudo java -Xmx1g -cp target/CallgraphAnalyzer-1.1-SNAPSHOT.jar Main --wala --pyan --code2flow