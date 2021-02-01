TCP Client-Server Program

An implementation of a question and answer game. The game consist of a server that gather some basic information
from the client such as their name and the number of questions they wish to be asked. The question bank is 20
questions, no more can be input. During the session, the server will asks the questions and wait for the client
response. The question session is timed, the forumla for the timing is the number of questions multiplied by 5.
If the client exceeds the time, the client automatically looses. After a question session, the user is presented
with the option to play again, if yes, they may play again, if no, they may not.


The server can run with the following default configuration:
gradle runServer

The server can also have a user define port  with the format:
gradle runServer -Phost=hostname -Pport=#

The client can be run under default configuration with
gradel runClient

The client can also be ran by defining an user defined host and port (must have both) with the following:
gradle runClient -Phost=hostname -Pport=#

Notes on running the program from the client side:
The error handling is not great. There is not check for making sure that the user does not ask for more
than 20 questions. When the server asks if the client is ready, the client MUST reply with the singular word
start. There is not sufficient error handling to prevent undefined behavior if the user enters the expected 
reply. There is error handling for if the user enters an unexpected answer for number of questions. 
It does not take a name for a client to 'play again.' Instead it asks with the expectation of getting a yes/no
answer, reusing the name that was originally entered by the client. A client may skip a question by submitting
"next" as an answer to the server.


Other tasks that I did not get complete:
An image is not transmitted to communicate win or loose. It is just a simple String. 
The server does not notify the client when they answered incorrectly to a question.
Some issues with error handling

Notes about the protocol:
The protocol is JSON that is converted into bytes and transmitted over TCP. It is simple with each message containing
two keys. One key defines the message type. The next is the message itsself. If the message is just a question, then 
the packet sent from the server to the client looks like the following:

{
	"type" : "question"
	"question" : "What color is the sun?"
}

If it is a message for general session stuff, the protocol define the packet as such:

{
	"type" : "message"
	"message" : "Ready. Enter start when ready"
}

The protocol is small and simple to try to reduce the chance of error and be smaller send between client and server.
