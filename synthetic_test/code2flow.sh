#branching
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/branching/classes/for_klass.dot  BenchmarkSuite/src/branching/classes/for_klass.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/branching/classes/while_klass.dot  BenchmarkSuite/src/branching/classes/while_klass.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/branching/classes/if_klass.dot  BenchmarkSuite/src/branching/classes/if_klass.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/branching/modules/for_module.dot  BenchmarkSuite/src/branching/modules/for_module.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/branching/modules/while_module.dot  BenchmarkSuite/src/branching/modules/while_module.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/branching/modules/if_module.dot  BenchmarkSuite/src/branching/modules/if_module.py

#code_generation
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/code_generation/classes/eval_bark.dot BenchmarkSuite/src/code_generation/classes/eval_bark.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/code_generation/classes/exec_bark.dot BenchmarkSuite/src/code_generation/classes/exec_bark.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/code_generation/modules/eval_concat.dot BenchmarkSuite/src/code_generation/modules/eval_concat.py

#decorators
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/decorators/classes/greet_decorator.dot BenchmarkSuite/src/decorators/classes/greet_decorator.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/decorators/classes/time_decorator.dot BenchmarkSuite/src/decorators/classes/time_decorator.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/decorators/classes/user_credentials.dot BenchmarkSuite/src/decorators/classes/user_credentials.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/decorators/modules/string_check.dot BenchmarkSuite/src/decorators/modules/string_check.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/decorators/modules/change_foo_to_bar.dot BenchmarkSuite/src/decorators/modules/change_foo_to_bar.py

#direct_functions
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/direct_functions/classes/simple_call_1.dot BenchmarkSuite/src/direct_functions/classes/simple_call_1.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/direct_functions/classes/simple_call_2.dot BenchmarkSuite/src/direct_functions/classes/simple_call_2.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/direct_functions/classes/record_metrics.dot BenchmarkSuite/src/direct_functions/classes/record_metrics.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/direct_functions/classes/error_specification.dot BenchmarkSuite/src/direct_functions/classes/error_specification.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/direct_functions/classes/simple_chat.dot BenchmarkSuite/src/direct_functions/classes/simple_chat.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/direct_functions/modules/module_call_1.dot BenchmarkSuite/src/direct_functions/modules/module_call_1.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/direct_functions/modules/module_call_2.dot BenchmarkSuite/src/direct_functions/modules/module_call_2.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/direct_functions/modules/read_book.dot BenchmarkSuite/src/direct_functions/modules/read_book.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/direct_functions/modules/async_lib.dot BenchmarkSuite/src/direct_functions/modules/async_lib.py

#duck_typing
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/duck_typing/classes/quack.dot BenchmarkSuite/src/duck_typing/classes/quack.py

#lambda functions
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/lambda_functions/classes/operations.dot BenchmarkSuite/src/lambda_functions/classes/operations.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/lambda_functions/modules/calc.dot BenchmarkSuite/src/lambda_functions/modules/calc.py

#library_loading
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/library_loading/classes/load_class.dot BenchmarkSuite/src/library_loading/classes/load_class.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/library_loading/modules/load_module.dot BenchmarkSuite/src/library_loading/modules/load_module.py

#object_changes
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/object_changes/classes/setattr_fun.dot BenchmarkSuite/src/object_changes/classes/setattr_fun.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/object_changes/classes/setattr_klass.dot BenchmarkSuite/src/object_changes/classes/setattr_klass.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/object_changes/classes/language_factory.dot BenchmarkSuite/src/object_changes/classes/language_factory.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/object_changes/classes/plus_operation.dot BenchmarkSuite/src/object_changes/classes/plus_operation.py

#polymorphic_functions
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/polymorphic_functions/classes/inheritance.dot BenchmarkSuite/src/polymorphic_functions/classes/inheritance.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/polymorphic_functions/classes/multilevel_inheritance.dot BenchmarkSuite/src/polymorphic_functions/classes/multilevel_inheritance.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/polymorphic_functions/classes/multiple_inheritance.dot BenchmarkSuite/src/polymorphic_functions/classes/multiple_inheritance.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/polymorphic_functions/classes/exception_handling.dot BenchmarkSuite/src/polymorphic_functions/classes/exception_handling.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/polymorphic_functions/modules/polymorphic_add.dot BenchmarkSuite/src/polymorphic_functions/modules/polymorphic_add.py

#recursion
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/recursion/classes/factorial.dot BenchmarkSuite/src/recursion/classes/factorial.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/recursion/classes/tree_traversal.dot BenchmarkSuite/src/recursion/classes/tree_traversal.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/recursion/modules/fibonacci.dot BenchmarkSuite/src/recursion/modules/fibonacci.py

#reflection
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/reflection/classes/greet.dot BenchmarkSuite/src/reflection/classes/greet.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/reflection/classes/visitor.dot BenchmarkSuite/src/reflection/classes/visitor.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/reflection/classes/class_method_call.dot BenchmarkSuite/src/reflection/classes/class_method_call.py

#static_functions
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/static_functions/classes/static_call_1.dot BenchmarkSuite/src/static_functions/classes/static_call_1.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/static_functions/classes/static_call_2.dot BenchmarkSuite/src/static_functions/classes/static_call_2.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/static_functions/classes/static_call_3.dot BenchmarkSuite/src/static_functions/classes/static_call_3.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/static_functions/classes/static_call_4.dot BenchmarkSuite/src/static_functions/classes/static_call_4.py

#nested_code
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/nested_code/classes/nested_class.dot BenchmarkSuite/src/nested_code/classes/nested_class.py
code2flow -o ./CallgraphAnalyzer/src/main/resources/code2flow/nested_code/modules/nested_factorial.dot BenchmarkSuite/src/nested_code/modules/nested_factorial.py

