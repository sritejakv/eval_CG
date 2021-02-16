FROM openkbs/jdk-mvn-py3
RUN sudo apt-get update && sudo apt-get install git
RUN sudo apt-get install -y mercurial
RUN sudo apt-get install -y ant
RUN sudo apt-get install -y rsync
RUN sudo apt-get install -y graphviz 
RUN pip install future

WORKDIR /synthetic_test
RUN sudo chmod -R 777 .

#Begin - installing WALA
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
#End - installing WALA

ARG CACHEBUST=1
RUN sudo git clone -b probe https://github.com/sritejakv/pycallgraph.git && cd pycallgraph && sudo python3 setup.py install
ARG CACHEBUST=1
RUN sudo git clone -b callgraph_fixes https://github.com/sritejakv/code2flow.git && cd code2flow && sudo python3 setup.py install
ARG CACHEBUST=1
RUN sudo git clone -b callgraph https://github.com/sritejakv/pyan.git

ARG CACHEBUST=1
COPY ./BenchmarkSuite BenchmarkSuite
ARG CACHEBUST=1
COPY ./dot2probe dot2probe
ARG CACHEBUST=1
COPY ./CallgraphAnalyzer CallgraphAnalyzer
ARG CACHEBUST=1
COPY ./code2flow.sh .
ARG CACHEBUST=1
COPY ./pyan.sh .
ARG CACHEBUST=1
COPY ./pycallgraph.sh .
ARG CACHEBUST=1
COPY ./generate_cg.sh .

RUN sudo sh generate_cg.sh

RUN sudo chmod -R 777 dot2probe
RUN cd dot2probe && mvn install -DskipTests
RUN mkdir -p $(mvn help:evaluate -Dexpression=settings.localRepository -q -DforceStdout)/probe/probe/1.0
RUN cp CallgraphAnalyzer/libs/probe.jar $(mvn help:evaluate -Dexpression=settings.localRepository -q -DforceStdout)/probe/probe/1.0/probe-1.0.jar
RUN mkdir -p $(mvn help:evaluate -Dexpression=settings.localRepository -q -DforceStdout)/gxl/gxl/1.0
RUN cp CallgraphAnalyzer/libs/gxl.jar $(mvn help:evaluate -Dexpression=settings.localRepository -q -DforceStdout)/gxl/gxl/1.0/gxl-1.0.jar
RUN sudo chmod -R 777 CallgraphAnalyzer
WORKDIR /synthetic_test/CallgraphAnalyzer
RUN mvn clean install -DskipTests

#CMD tail -f /dev/null
CMD sudo java -cp target/callgraph_analyzer-1.1-SNAPSHOT.jar Main --pyan --code2flow --wala