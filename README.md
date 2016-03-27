# RestQL
[![Build Status](https://travis-ci.org/paidgeek/restql.svg?branch=master)](https://travis-ci.org/paidgeek/restql)

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
    <member>
    <relation> <relationOperator> <member>

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
