import socket

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

print(s)

#server = 'pythonprogramming.net'
#server = '198.185.159.145'

server = input()

port = 80

#server_ip = socket.gethostbyname(server)
#print(server_ip)

request = "GET / HTTP/1.1\nHost: " + server + "\n\n"

s.connect((server, port))
s.send(request.encode())

results = s.recv(4096)

print(results)


while (len(results) > 0) :
  print(results)
  results = s.recv(1024)
