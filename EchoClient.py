import socket
import sys
from multiprocessing.dummy import Pool
import string
import random
import time

sockets =[]
upperbound = 10
# Create a TCP/IP socket

def OpenSocket():
    try:
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        # Connect the socket to the port where the server is listening
        server_address = ('localhost', 7878)
        # print ( 'connecting to %s port %s' % server_address)
        sock.connect(server_address)
        return sock
    except socket.error as exc:
        print ("Caught exception socket.error : %s" % exc)

def CloseSocket(sok):
    try:
        sok.shutdown(socket.SHUT_RDWR) #RDWR further sends and receives are now disallowed
        sok.close()
    except socket.error as ex:
        print ("Caught exception socket.error : %s" % ex)

# Function adapted from https://stackoverflow.com/questions/2257441/random-string-generation-with-upper-case-letters-and-digits-in-python/23728630#23728630
def char_gen(size=1000): 
    return ((''.join(random.SystemRandom().choice(string.ascii_uppercase + string.digits) for _ in range(size)))+'\r')
# pick a random char from the alphabet or digit , underscore is to ignore index, so do size times, \r needs to be appended to trigger EOF exception


def SendandPrint(soc):
#    time.sleep(5)
    line = char_gen()
    try:
        soc.send(line.encode()) 
        data = soc.recv(len(line)).decode()
        if (line.strip() == (data.strip())): #strip used to remove any special characters
            CloseSocket(soc)
            return True
        else:
            CloseSocket(soc)
            return False
    except (EOFError):
        CloseSocket(soc)
    except (RuntimeError): #add socket clousre
        print("Run time Error occureed")
        CloseSocket(soc)
    except (OSError):
        print("Exception occured")
        CloseSocket(soc)

def main():
    for i in range (0,upperbound):
        sockets.append(OpenSocket())
    pools = Pool(upperbound) #create pools
    results = pools.map(SendandPrint,sockets)
    pools.close() #workers will terminate when all work already assigned has completed
    pools.join() #waits until all threads are done before proceeding 
    if (all(x==True for x in results)):
        print ("All tests echoed back correctly")
    else:
        print("Some tests failed, results as follows, with True indicating a correct response: " + " ".join(str(x) for x in results))


if __name__ == "__main__":
    main()
