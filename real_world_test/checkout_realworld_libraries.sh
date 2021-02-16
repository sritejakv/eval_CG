sudo pip3 install cryptography==2.3 pyOpenSSL==19.0.0 requests asynctest parver zstandard==0.11.0
git clone -b program_test https://github.com/sritejakv/mitmproxy.git
cd mitmproxy
sudo python3 setup.py install
cd ../
sudo rsync -r --exclude 'tests' --exclude 'test' --include '*.py' ./mitmproxy/mitmproxy/ ./entry_point_extractor/src/main/resources/realWorldLibSrc/mitmproxy

git clone -b program_test https://github.com/sritejakv/PythonRobotics.git
sudo rsync -r --exclude 'tests' --include '*.py' ./PythonRobotics/ ./entry_point_extractor/src/main/resources/realWorldLibSrc/PythonRobotics

git clone -b program_test https://github.com/sritejakv/cookiecutter.git
cd cookiecutter
sudo python3 setup.py install
sudo pip3 install pytest-cov pytest-mock pytest-catchlog freezegun
cd ../
sudo rsync -r --exclude 'tests' --include '*.py' ./cookiecutter/cookiecutter/ ./entry_point_extractor/src/main/resources/realWorldLibSrc/cookiecutter

git clone -b program_test https://github.com/sritejakv/YouCompleteMe.git
cd YouCompleteMe
git submodule update --init --recursive
sudo apt-get update && sudo apt-get -y install cmake protobuf-compiler
sudo pip3 install PyHamcrest
cd ../
sudo rsync -r --exclude 'tests' --include '*.py' ./YouCompleteMe/python/ycm/ ./entry_point_extractor/src/main/resources/realWorldLibSrc/ycm

git clone -b program_test https://github.com/sritejakv/thefuck.git
cd thefuck
sudo pip3 install -Ur requirements.txt
sudo python3 setup.py install
cd ../
sudo rsync -r --exclude 'tests' --exclude 'test' --include '*.py' ./thefuck/thefuck/ ./entry_point_extractor/src/main/resources/realWorldLibSrc/thefuck