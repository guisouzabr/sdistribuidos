#! /usr/bin/python

import socket

s = socket.socket()
host = 'localhost'
port = 13578
s.bind( (host, port) )
s.listen(5)


c, a = s.accept()
print 'Connection with', a

try:
	while True:
		print "Resposta: "+c.recv(1024)
		str = raw_input("Digite sua msg:\n")
		c.send(str)

finally:
	c.close()
	s.close()
