libPythonRobotics='PythonRobotics'
libMitmProxy='mitmproxy'
libCookiecutter='cookiecutter'
libYCM='ycm'
libFuck='thefuck'
sourceFileSuffix='SourceFiles'

declare -a libs=("${libMitmProxy}" "${libPythonRobotics}" "${libCookiecutter}" "${libYCM}" "${libFuck}")
libPath='entry_point_extractor/src/main/resources/realWorldLibSrc/'
for i in "${libs[@]}"
do
find "${libPath}${i}" -name '*.py' > "${i}SourceFiles.txt"
done

for i in "${libs[@]}"
do
echo "Generating pyan entry points for the library ${i}"
filePath="${i}${sourceFileSuffix}.txt"
eP=""
while read line; do
	eP="${eP} ${line}"
done < $filePath
pyan/pyan.py $eP --pruneEntryPoints --library "entry_point_extractor/src/main/resources/prunedEntryPoints/pyan/${i}PrunedEntryPoints.txt"
done