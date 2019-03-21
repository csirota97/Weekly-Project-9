import socket

def main():
	host = '127.0.0.1'
	port = 9876+1
	
	s = socket.socket()
	s.bind((host, port))

	s.listen(1)
	client, addr = s.accept()
	print("Connection from: " +str(addr))
	while True:
		data = client.recv(1024).decode("utf-8")
		if not data:
			break
		print("From connected user: " + data)
		data = data.upper()
		print("Sending")
		client.send(data.encode('utf-8'))
	client.close()
	s.close()

if __name__ == '__main__':
	main()
