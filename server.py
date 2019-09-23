import socket
import struct
from random import randrange

def contarMinas(i,j,k,l,tablero):
	#i,j: Dimensiones del tablero
	#k,l: Posicion de celda
	numMinas = 0
	if ((k > 0) and (l > 0)):
		if (tablero[k - 1][l - 1] == -1):
			numMinas+=1
	if (k > 0):
		if (tablero[k - 1][l] == -1):
			numMinas+=1
	if ((k > 0) and (l < (j - 1))):
		if (tablero[k - 1][l + 1] == -1):
			numMinas+=1
	if ((l < (j - 1))):
		if (tablero[k][l + 1] == -1):
			numMinas+=1
	if ((l < (j - 1)) and (k < (i - 1))):
		if (tablero[k + 1][l + 1] == -1):
			numMinas+=1
	if ((k < (i - 1))):
		if (tablero[k + 1][l] == -1):
			numMinas+=1
	if ((l > 0) and (k < (i - 1))):
		if (tablero[k + 1][l - 1] == -1):
			numMinas+=1
	if ((l > 0)):
		if (tablero[k][l - 1] == -1):
			numMinas+=1
	tablero[k][l] = numMinas
	return numMinas
	

def creaTablero(f,c):
	arr=[-5]*f
	for i in range(f):
		arr[i]= [-5]*c

	minas=10;
	if(c==9): minas=10
	if(c==16): minas=40
	if(c==30): minas=99
	print(f"Hay {minas} minas")
	i = 0
	while i < minas:#CICLO PARA COLOCAR MINAS
		n = randrange(f)#filas
		m = randrange(c)#filas
		if(arr[n][m]== -5):
			print(f"[{i+1}] Mina en posición ({n},{m})")
			arr[n][m] = -1
			i+=1

	for i in range(len(arr)):
		for j in range(len(arr[0])):
			if(arr[i][j]!=-1):
				arr[i][j] = contarMinas(f,c,i,j,arr)

	return arr

def mandaTablero():
	data = ss.recv(1024)
	print(data)
	msg = data.decode("utf-8")
	#print(f"Dificultad: {msg}")
	if(msg=="1"):
		print("Principiante")
		tablero=creaTablero(9,9)
	else:
		if(msg=="2"):
			print("Intermedio")
			tablero=creaTablero(16,16)
		else:
			print("Avanzado")
			tablero=creaTablero(16,30)

	print("Enviando arreglo...")
	for i in range(len(tablero)):
		for j in range(len(tablero[0])):
			ss.send(bytes(str(tablero[i][j])+'\n',"utf-8"))

	print("Done...")

def mandaScoreboard():

	print("Done...")
	
def saveWinner():
	print("Guardando score...")
	f = open('Records.txt', 'a')
	data = ss.recv(1024)
	print(data)
	msg = data.decode("utf-8")
	f.write(msg+'\n')
	f.close()
	print("Done!")


#---------------------------------------------------------------------------------

switcher = {
	"0": mandaTablero,
	"1": mandaScoreboard,
	"2": saveWinner
}

def opciones(argument):
	func = switcher.get(argument, "Errorts")
	return func()

HOST = '127.0.0.1'  # Standard loopback interface address (localhost)
PORT = 1234  		# Port to listen on (non-privileged ports are > 1023)

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind((HOST, PORT))
s.listen(5)
print(f"Servicio iniciado en {HOST}:{PORT}")

"""
f = open('Records.txt', 'r')
print(f.read())
f.close()

f = open('Records.txt', 'a')
f.write('Fernando: 00:09:22\n')
f.close()
"""

while True:
	ss, address = s.accept()
	print(f"Conexión desde {address} se ha establecido!")
	mandaTablero()
	#ss.send(bytes("HOLA QUE TAL!"+'\n',"utf-8"))
	#cadena = input("Escribe algo para enviar ('q' para salir)\n")
	while True:
		print("Esperando instrucción...")
		datos = ss.recv(1024)
		print(datos)
		opt = datos.decode("utf-8")
		if(opt=="-1"):
			break
		else:
			print(f"Ejecutando opción: {opt}")
			opciones(opt)
	
	#num, = struct.unpack('<i',data)
	#print(num)
	
	"""
	if(msg=="1"):
		print("Principiante")
		tablero=creaTablero(9,9)
	else:
		if(msg=="2"):
			print("Intermedio")
			tablero=creaTablero(16,16)
		else:
			print("Avanzado")
			tablero=creaTablero(16,30)
	
	print("Enviando arreglo...")
	for i in range(len(tablero)):
		for j in range(len(tablero[0])):
			ss.send(bytes(str(tablero[i][j])+'\n',"utf-8"))

	print("OK!")"""
	
	
	"""
	for i in range(len(tablero)):
		for j in range(len(tablero[0])):
			print(tablero[i][j],end=' ')
		print('\n')
	"""

	"""if(cadena == 'q'):
		print("ADIOS!")
		ss.send(bytes("Lost connection :'v"+'\n',"utf-8"))
		ss.close()
		break;
	else:
		ss.send(bytes(cadena+'\n',"utf-8"))
		#ss.send(bytes(cadena))

	"""