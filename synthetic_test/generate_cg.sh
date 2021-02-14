echo "Performing synthetic test!"
echo "Copying BenchmarkSuite"
mkdir -p CallgraphAnalyzer/src/main/resources/BenchmarkSuite
rsync -av ./BenchmarkSuite/ CallgraphAnalyzer/src/main/resources/BenchmarkSuite --exclude venv --exclude __pycache__ --exclude .idea --exclude .gitignore --exclude .DS_Store
echo "generating PyCallGraph resources"
sh pycallgraph.sh
echo "generating Pyan resources"
sh pyan.sh
echo "generating Code2flow resources"
sh code2flow.sh
echo "Done!"
