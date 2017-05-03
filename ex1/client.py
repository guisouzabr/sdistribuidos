#! /usr/bin/python

import socket

s = socket.socket()
host = 'localhost'
port = 13578

s.connect((host,port))
try:
	while True:
		str = raw_input("Digite sua msg:\n")
		s.send(str)
		print "Resposta: "+s.recv(1024)
	
finally:
	s.close()
