# lexer
:1234: java lexer

## What it is
- Lexical analyzer for a subset of Java language.
- Syntax analyzer.
- Generator of the intermediate language by syntax tree.
- Generator of assembler code.

All modules are written in Java language. There is an ability to look the graphical view of syntax tree.

## How it works
- Lexical analyzer works like a state machine, parses source code and generates the list of the tokens.
- Syntax analyzer is based on [the Operator-precedence grammars](https://en.wikipedia.org/wiki/Operator-precedence_grammar). Tables for grammars are read from txt files.
Syntax analyzer gets tokens and tries to make the syntax tree.
- Generator of the intermediate language constructs the flat list of tetrads by syntax tree.
- Generator of target code generates asm code by the tetrads.

## Contributors
[oxaoo](https://github.com/oxaoo), [FatherOctber](https://github.com/FatherOctber), [dydus0x14](https://github.com/dydus0x14)
