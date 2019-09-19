import socket
import struct

HOST = '127.0.0.1'  # Standard loopback interface address (localhost)
PORT = 1234  		# Port to listen on (non-privileged ports are > 1023)

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind((HOST, PORT))
s.listen(5)
print(f"Servicio iniciado en {HOST}:{PORT}")

while True:
	ss, address = s.accept()
	print(f"Conexión desde {address} se ha establecido!")
	#ss.send(bytes("HOLA QUE TAL!"+'\n',"utf-8"))
	#cadena = input("Escribe algo para enviar ('q' para salir)\n")
	data = ss.recv(1024)
	if not data:
		break
	print(data)
	#num, = struct.unpack('<i',data)
	#print(num)
	msg = data.decode("utf-8")
	if(msg=="1"):
		print("Principiante")
	else:
		if(msg=="2"):
			print("Intermedio")
		else:
			print("Avanzado")

	print(f"Mandaron: {msg}\nEnviando eco...")
	ss.send(bytes(msg+'\n',"utf-8"))
	print("OK!")
	"""if(cadena == 'q'):
		print("ADIOS!")
		ss.send(bytes("Lost connection :'v"+'\n',"utf-8"))
		ss.close()
		break;
	else:
		ss.send(bytes(cadena+'\n',"utf-8"))
		#ss.send(bytes(cadena))

	"""
	#ss.send("Hola!\r\n".encode("UTF-8"))
	#ss.close()