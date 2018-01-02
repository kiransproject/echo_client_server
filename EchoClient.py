import socket
import sys
from multiprocessing.dummy import Pool

sockets =[]
upperbound = 10
text = ['test1\n', 'test2\n']
# Create a TCP/IP socket

def OpenSocket():
    try:
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        # Connect the socket to the port where the server is listening
        server_address = ('localhost', 7878)
        print ( 'connecting to %s port %s' % server_address)
        sock.connect(server_address)
        return sock
    except socket.error as exc:
        print ("Caught exception socket.error : %s" % exc)

def CloseSocket(sock):
    try:
        sock.shutdown(socet.SHUT_RDWR)
        sock.close()
    except socket.error as ex:
        print ("Caught exception socket.error : %s" % ex)
    
def SendandPrint(soc):
    data = ''
    try:
        for line in text:
                try:
                    soc.send(line.encode())
                    data = soc.recv((len(line))).decode()
                    print (data)
                except (EOFError):
                    CloseSocket(soc)
                    break
    except (RuntimeError): #add socet clousre
        print("Run time Error occureed")
        CloseSocket(soc)
    except (OSError):
        print("Exception occured")
        CloseSocket(soc)

def main():
    for i in range (0,upperbound):
        sockets.append(OpenSocket()) #open 10 sockets
    pools = Pool(upperbound)
    pools.map(SendandPrint, sockets)
    #SendandPrint(OpenSocket())

if __name__ == "__main__":
    main()
