import socket
import sys
from multiprocessing.dummy import Pool
import string
import random

sockets =[]
chars = []
upperbound = 10
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

# Function adapted from https://stackoverflow.com/questions/2257441/random-string-generation-with-upper-case-letters-and-digits-in-python/23728630#23728630
def char_gen(size=1000): 
    return ((''.join(random.SystemRandom().choice(string.ascii_uppercase + string.digits) for _ in range(size)))+'\r')
# pick a random char from the alphabet or digit , underscore is to ignore index, so do size times, \r needs to be appended to trigger EOF exception


def SendandPrint(soc):
    #soc = OpenSocket()
    line = char_gen()
    try:
        soc.send(line.encode()) 
        data = soc.recv(len(line)).decode()
        if (line.strip() == (data.strip())): #strip used to remove any special characters
            return True
        else:
            return False
    except (EOFError):
        CloseSocket(soc)
        #break
    except (RuntimeError): #add socet clousre
        print("Run time Error occureed")
        CloseSocket(soc)
    except (OSError):
        print("Exception occured")
        CloseSocket(soc)

def main():
    for i in range (0,upperbound):
        #chars.append((char_gen()))
        sockets.append(OpenSocket())
    pools = Pool(upperbound)
    test = ['5\r','5\r','5\r','5\r']
    results = pools.map(SendandPrint,sockets)
    pools.close()
    pools.join()
    #print(results)
    if (all(x==results[0] for x in results)):
        print ("All tests echoed back correctly")
    else:
        print("Some tests failed, results as follows, with True indicating a correct response: " + results)

if __name__ == "__main__":
    main()
