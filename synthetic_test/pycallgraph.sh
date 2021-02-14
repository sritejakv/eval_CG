#branching
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/branching/classes/for_klass.dot -- BenchmarkSuite/src/branching/classes/for_klass.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/branching/classes/while_klass.dot -- BenchmarkSuite/src/branching/classes/while_klass.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/branching/classes/if_klass.dot -- BenchmarkSuite/src/branching/classes/if_klass.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/branching/modules/for_module.dot -- BenchmarkSuite/src/branching/modules/for_module.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/branching/modules/while_module.dot -- BenchmarkSuite/src/branching/modules/while_module.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/branching/modules/if_module.dot -- BenchmarkSuite/src/branching/modules/if_module.py

#code_generation
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/code_generation/classes/eval_bark.dot -- BenchmarkSuite/src/code_generation/classes/eval_bark.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/code_generation/classes/exec_bark.dot -- BenchmarkSuite/src/code_generation/classes/exec_bark.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/code_generation/modules/eval_concat.dot -- BenchmarkSuite/src/code_generation/modules/eval_concat.py

#decorators
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/decorators/classes/greet_decorator.dot -- BenchmarkSuite/src/decorators/classes/greet_decorator.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/decorators/classes/time_decorator.dot -- BenchmarkSuite/src/decorators/classes/time_decorator.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/decorators/classes/user_credentials.dot -- BenchmarkSuite/src/decorators/classes/user_credentials.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/decorators/modules/string_check.dot -- BenchmarkSuite/src/decorators/modules/string_check.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/decorators/modules/change_foo_to_bar.dot -- BenchmarkSuite/src/decorators/modules/change_foo_to_bar.py

#direct_functions
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/direct_functions/classes/simple_call_1.dot -- BenchmarkSuite/src/direct_functions/classes/simple_call_1.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/direct_functions/classes/simple_call_2.dot -- BenchmarkSuite/src/direct_functions/classes/simple_call_2.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/direct_functions/classes/record_metrics.dot -- BenchmarkSuite/src/direct_functions/classes/record_metrics.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/direct_functions/classes/error_specification.dot -- BenchmarkSuite/src/direct_functions/classes/error_specification.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/direct_functions/classes/simple_chat.dot -- BenchmarkSuite/src/direct_functions/classes/simple_chat.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/direct_functions/modules/module_call_1.dot -- BenchmarkSuite/src/direct_functions/modules/module_call_1.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/direct_functions/modules/module_call_2.dot -- BenchmarkSuite/src/direct_functions/modules/module_call_2.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/direct_functions/modules/read_book.dot -- BenchmarkSuite/src/direct_functions/modules/read_book.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/direct_functions/modules/async_lib.dot -- BenchmarkSuite/src/direct_functions/modules/async_lib.py

#duck_typing
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/duck_typing/classes/quack.dot -- BenchmarkSuite/src/duck_typing/classes/quack.py

#lambda functions
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/lambda_functions/classes/operations.dot -- BenchmarkSuite/src/lambda_functions/classes/operations.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/lambda_functions/modules/calc.dot -- BenchmarkSuite/src/lambda_functions/modules/calc.py

#library_loading
cd BenchmarkSuite/
pycallgraph --exclude="_*" --exclude="*File*" --exclude="Source*" --exclude="spec_*" graphviz --dot-file ../CallgraphAnalyzer/src/main/resources/pycallgraph/library_loading/classes/load_class.dot -- src/library_loading/classes/load_class.py
pycallgraph --exclude="_*" --exclude="*File*" --exclude="Source*" --exclude="spec_*" graphviz --dot-file ../CallgraphAnalyzer/src/main/resources/pycallgraph/library_loading/modules/load_module.dot -- src/library_loading/modules/load_module.py
cd ../

#object_changes
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/object_changes/classes/setattr_fun.dot -- BenchmarkSuite/src/object_changes/classes/setattr_fun.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/object_changes/classes/setattr_klass.dot -- BenchmarkSuite/src/object_changes/classes/setattr_klass.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/object_changes/classes/language_factory.dot -- BenchmarkSuite/src/object_changes/classes/language_factory.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/object_changes/classes/plus_operation.dot -- BenchmarkSuite/src/object_changes/classes/plus_operation.py

#polymorphic_functions
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/polymorphic_functions/classes/inheritance.dot -- BenchmarkSuite/src/polymorphic_functions/classes/inheritance.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/polymorphic_functions/classes/multilevel_inheritance.dot -- BenchmarkSuite/src/polymorphic_functions/classes/multilevel_inheritance.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/polymorphic_functions/classes/multiple_inheritance.dot -- BenchmarkSuite/src/polymorphic_functions/classes/multiple_inheritance.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/polymorphic_functions/classes/exception_handling.dot -- BenchmarkSuite/src/polymorphic_functions/classes/exception_handling.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/polymorphic_functions/modules/polymorphic_add.dot -- BenchmarkSuite/src/polymorphic_functions/modules/polymorphic_add.py

#recursion
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/recursion/classes/factorial.dot -- BenchmarkSuite/src/recursion/classes/factorial.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/recursion/classes/tree_traversal.dot -- BenchmarkSuite/src/recursion/classes/tree_traversal.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/recursion/modules/fibonacci.dot -- BenchmarkSuite/src/recursion/modules/fibonacci.py

#reflection
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/reflection/classes/greet.dot -- BenchmarkSuite/src/reflection/classes/greet.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/reflection/classes/visitor.dot -- BenchmarkSuite/src/reflection/classes/visitor.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/reflection/classes/class_method_call.dot -- BenchmarkSuite/src/reflection/classes/class_method_call.py

#static_functions
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/static_functions/classes/static_call_1.dot -- BenchmarkSuite/src/static_functions/classes/static_call_1.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/static_functions/classes/static_call_2.dot -- BenchmarkSuite/src/static_functions/classes/static_call_2.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/static_functions/classes/static_call_3.dot -- BenchmarkSuite/src/static_functions/classes/static_call_3.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/static_functions/classes/static_call_4.dot -- BenchmarkSuite/src/static_functions/classes/static_call_4.py

#nested_code
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/nested_code/classes/nested_class.dot -- BenchmarkSuite/src/nested_code/classes/nested_class.py
pycallgraph graphviz --dot-file ./CallgraphAnalyzer/src/main/resources/pycallgraph/nested_code/modules/nested_factorial.dot -- BenchmarkSuite/src/nested_code/modules/nested_factorial.py

