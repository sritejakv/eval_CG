libPythonRobotics='PythonRobotics'
libCookiecutter='cookiecutter'
libMitmProxy='mitmproxy'
libYCM='ycm'
libFuck='thefuck'

declare -a libs=("${libMitmProxy}" "${libPythonRobotics}" "${libCookiecutter}" "${libYCM}" "${libFuck}")
for lib in "${libs[@]}"
do
echo "Generating code2flow static call graphs of ${lib}"
echo "Reading entry points"
filePath="entry_point_extractor/commonEntryPoints/${lib}.txt"
eP=""
while read line; do
	eP="${eP} ${line}"
done < $filePath
pyan/pyan.py $eP --no-defines --dot -f "CallgraphAnalyzer/src/main/resources/pyan/${lib}.dot"
done
