import socket
import sys

# Create a TCP/IP socket

try:
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

        # Connect the socket to the port where the server is listening
        server_address = ('localhost', 7878)
        print ( 'connecting to %s port %s' % server_address)
        sock.connect(server_address)
        data = ''
        for line in sys.stdin.readline():
            try:
                sock.send(line.encode())
                data = sock.recv((len(line))).decode()
                print (data)
                '''
        while (True):
            try:
                read = stdin.readline()
                #read = userinput + '\n'
                while (read != null):
                    sock.send(read.encode())
                    data = sock.recv((len(read))).decode()
                    read = null
                    print ("Recieved: " + data)
'''
            except (EOFError):
                sock.shutdown(socket.SHUT_RDWR)
                sock.close()
                break


finally:
        sock.shutdown(socket.SHUT_RDWR)
        sock.close()
