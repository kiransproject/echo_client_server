import socket
import sys

# Create a TCP/IP socket

try:
	sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

	# Connect the socket to the port where the server is listening
	server_address = ('localhost', 7878)
	print ( 'connecting to %s port %s' % server_address)
	sock.connect(server_address)
    
	r= input('Enter message   ') 
	sock.send((r+ '\n').encode())
	data = ''
	data = sock.recv(1024).decode()
	print (data)
	

finally:
	sock.close()
