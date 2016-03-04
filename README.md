# RestQL
[![Build Status](https://travis-ci.org/paidgeek/restql.svg?branch=master)](https://travis-ci.org/paidgeek/restql)

## Syntax grammar

```js
query:
	empty
	or query
	
or:
	and
	and . or
	
and:
	equality
	equality , and

equality:
	relation
	relation equalityOperator equality

relation:
	primary
	relation relationOperator primary
	
primary:
	( element )
	identifier
	number
	string

equalityOperator:
	:
	!:
	~
	
relationOperator:
	<
	>
	<:
	:>
```
