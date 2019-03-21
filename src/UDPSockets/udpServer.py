import socket

def main():
	host = socket.getfqdn()
	print(host)
	port = 19876

	s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
	s.bind(('', port))

	print("Server Started")

	while True:
		data, addr = s.recvfrom(1024)
		data = data.decode('utf-8')

		print("Message from: " + str(addr))
		print("From user: " + data)

		data = data.upper()

		print('Sending: ' + data)

		s.sendto(data.encode('utf-8'), addr)

	c.close()


if __name__ == '__main__':
	main()





