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
code2flow --language py -o "CallgraphAnalyzer/src/main/resources/code2flow/${lib}.dot" $eP
done
