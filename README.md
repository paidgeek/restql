# RestQL
[![Build Status](https://travis-ci.org/paidgeek/restql.svg?branch=master)](https://travis-ci.org/paidgeek/restql)

## Usage

### Parsing
```java
AstNode result = RestQL.parse("print('hello, world')&life=42&nope=1!:2");
result.accept(new DumpVisitor());
```

Result AST:
```js
Query
  Sequence
    Call
      Identifier('print')
      Sequence
        Literal(STRING, hello, world)
  Assignment
    Sequence
      Identifier('life')
    Sequence
      Literal(NUMBER, 42.0)
  Assignment
    Sequence
      Identifier('nope')
    Sequence
      Binary(NOT_EQUAL)
        Literal(NUMBER, 1.0)
        Literal(NUMBER, 2.0)

```
Use custom visitor, which implements [Visitor](https://github.com/paidgeek/restql/blob/master/src/main/java/com/moybl/restql/ast/Visitor.java) interface, to traverse an AST. For example, a visitor can be used to generate a SQL statement.

### Executing
```java
Engine e = new Engine();
e.execute("a = 5 & b = 3");
e.execute("user.age = 19");

// prints '3'
System.out.println(e.getVariable("b").numberValue());
// prints '1.0' a.k.a 'true'
System.out.println(e.evaluate("user.age >: 18").numberValue());
```

## Example queries
```js
user.name:'bob%' and user.age >: 18
```
```js
a = sum(1, 2, 3) & b = avg(a, 2)
```

## Syntax grammar

```js
<query>:
    <empty>
    <assignment>
    <assignment> & <query>

<assignment>:
    <or>
    <or> = <sequence>

<sequence>:
    <or>
    <or> , <sequence>

<or>:
    <and>
    <and> or <or>

<and>:
    <equality>
    <equality> and <and>

<equality>:
    <relation>
    <relation> <equalityOperator> <equality>

<relation>:
    <unary>
    <unary> <relationOperator> <relation>

<unary>:
    <member>
    ! <member>

<member>:
    <primary>
    <primary> . <member>
    <primary> ( <sequence> )

<primary>:
    ( <sequence> )
    <identifier>
    <number>
    <string>

<equalityOperator>:
    :
    !:

<relationOperator>:
    <
    >
    <:
    :>
```
