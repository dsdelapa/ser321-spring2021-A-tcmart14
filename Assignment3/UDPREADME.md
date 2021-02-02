UDP Client-Server QA Program

A small implementation of steps 1-3 on the TCP variant.

How to run:
The server can be ran with default configurations using the following:
gradle runUDPServer
Or the server can have a user specific port with
gradle runUDPServer -Pport=#

The client can be ran with default configuration using the following:
gradle runUDPClient
Or the client can connect to a server host and port with the following:
gradle runUDPClient -Phost=hostname -Pport=#

Protocol information
The protocol that I used is the same as from the TCP version that uses JSON.
All messages have a type key-value pair and a message key-value pair. An exmaple:

{
	"type" : "message"
	"message" : "What is your name?"
}

The protocol is short and in most circumstances with transmitting text will fit
everything within the buffer size limitations.

Requirements I did not get to:
I did not get a syn-ack-syn implementation

How would this differ with TCP on sending images?
TCP will make sure packets make it, break data into multiple packets and 
peice the packets back together in the proper order. UDP by default does
not offer this, and therefore would have to be implemented, especially in
the case of an image where image data is often large. UDP has a default maximum
buffer size. So an image's data would have to have checking to make sure all 
pieces of the image get there, and also each packet would have to include information
regarding the ordering. An example of a packet using my protocol may be:
{
	"type" : "image"
	"message number" : #
	"total message" : #
	"image" : string of data to represent part of an image
}

The UDP client would probably hold all of the received messages in a collection until all
message numbers in series counts up to the total messages. Then using a sort algorithm, sort them into
the correct order incase they arrived out of order. Then transverse the collection peicing together
the pixel data from images out of the "image" key.
