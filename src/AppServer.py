import socket
from pynput.mouse import Button, Controller



def main():
	mouse = Controller()

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

		msg = data[1:-1]
		mouse.position = (int(msg.split(',')[0]),int(msg.split(',')[1]))

	c.close()


if __name__ == '__main__':
	main()





