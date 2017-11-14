Members:
Victoria Reed
Trenton Ford

This program can be used as a Scheme interpreter. It is built off of the reference
parser and pretty printer implementation, so csc4101.jar is required to run it.
Compile: javac -cp .:csc4101.jar *.java
Run: java -cp .:csc4101.jar Main

We implemented the Environment such that it contains a linked list of Frames, 
which represent scopes. The BuiltInFrame is used
to populate a scope with the following built-in functions:
1. binary arithmetic (b+, b-, b*, b/, b<, b>, and b=)
2. list operations (cons, car, cdr, set-car!, set-cdr!)
3. test operations (symbol?, number?, null?, procedure?, pair?, eq?)
4. I/O operations (read, write, display, newline)
5. eval
6. apply
7. interaction-environment
Other functions (define, lambda, let, and set!) are implemented according to the Scheme R(5)
report
