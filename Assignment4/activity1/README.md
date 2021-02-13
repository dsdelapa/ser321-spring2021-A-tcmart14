# Assignment 4 Activity 1
## Description
The initail Performer code only has one function for adding strings to an array: 

## Protocol

### Requests
request: { "selected": <int: 1=add, 2=remove, 3=display, 4=count, 5=reverse,
0=quit>, "data": <thing to send>}

  data <string>: add
  data <int> pop
  data <int> <int> switch but send as String

### Responses

sucess response: {"type": <"hello", add",
"remove", "display", "count", "switch", "quit"> "data": <thing to return> }

type <String>: echoes original selected from request
data <string>: add = new list, pop = new list, display = current list, count = num elements, switch = switch two elements


error response: {"type": "error", "message"": <error string> }
Should give good error message if something goes wrong


## How to run the program
### Terminal
Base Code, please use the following commands:
```
    For Server, run "gradle runServer -Pport=9099 -q --console=plain"
```
```   
    For Client, run "gradle runClient -Phost=localhost -Pport=9099 -q --console=plain"
```   


## Requirement fulfilled

All tasks should be implemnted fully and be operational without any issues.

## Program input

add expects the user to put in a string, anye stream of characters should work.

Pop, no use input other than selecting the pop operation.

Display, no extra input outside of selecting the display operation

Count, no extra input outside of selecting the count operation

Switch, expects the input of two numbers that are valid indexes of the list of Strings
