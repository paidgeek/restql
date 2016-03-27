# RestQL
[![Build Status](https://travis-ci.org/paidgeek/restql.svg?branch=master)](https://travis-ci.org/paidgeek/restql)

## Syntax grammar

```js
<assignments>:
    <empty>
    <member> = <elements>
    <member> = <elements> & <assignments>

<elements>:
    <or>
    <or> , <elements>

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
    <primary> ( <arguments> )

<arguments>:
    <empty>
    <primary>
    <primary> , <arguments>

<primary>:
    ( <elements> )
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
