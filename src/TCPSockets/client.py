import socket
#from pynput.mouse import Button, Controller

#mouse = Controller()


def main():
	host = '127.0.0.1'
	port = 9876+1

	s = socket.socket()
#	ip = input("IP: ")
#	while True:
#		try:
	s.connect((host, port))

	message = input("->")
	while message != 'q':
		s.send(message.encode('utf-8'))
		data = s.recv(1024).decode('utf-8')
		print('From Server: '+ data)
		message = input("->")

	s.close()

	#msg = s.recv(1024)
	#msg = msg.decode("utf-8")[1:-1]
	#mouse.position = (int(msg.split(',')[0]), int(msg.split(',')[1]))

#	print(msg)
#


if __name__ == '__main__':
	main()
