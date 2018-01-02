import socket
import sys
from multiprocessing.dummy import Pool

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
        for line in sys.stdin:
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
    SendandPrint(OpenSocket())

if __name__ == "__main__":
    main()
