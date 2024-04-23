# simple-aged-cache
Fundamentals of Software Architecture for Big Data assignment #1

The exercise - get the tests to pass!

Explore using an inner class ExpirableEntry
Try not using built in collection classes; Lists, Maps, or Sets.

----------------------------------------------------------------
Dev Notes:

Validating the cache, removing expired entries was the throught provoking challenge with this assignment. 
I decided to handle the .get by simply finding the requested items and not handling removing the expired entries 
in order to return values in a timely manner. The time validation happens when the size and isEmpty methods are called. 
Since these methods aren't as time sensitive, I decided that this is the best way to handle the cache validations. 

