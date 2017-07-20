from Adafruit_CharLCD import Adafruit_CharLCD
from time import sleep, strftime
from datetime import datetime
import RPi.GPIO as GPIO
import json
import requests
import socket
import geocoder
from pyfcm import FCMNotification

push_service = FCMNotification(api_key="AIzaSyDJSwQFdG-bqfpx-LV30ovcdvk8zK96o94")

# registration_id = "cYvY46UG4-0:APA91bGrw2agOtErKjkqp9DUZRDEvCZI_MKZs-50SECrbuAOOg2Jcy7dG3X0G6I3oMyAthXWyy0ULlWB5u-ThxsWlpC7vXLBci2PjbOd1zfm66OrPyHhoyK5-mkOSU74s11c_tA7jWTr"
#Abhinandan's phone dSx9....
registration_id="dSx94C_a4WY:APA91bFo4WHGtPdmRG1ziafvKgSs6zncykDdo3YPhgmDydFjw1400W6x3WldcD0qvA4LzzBITkenqCXAvPfhwU0KTn-WigiuzN3OMFDhcWAxeqE_bOeRiPU1MbdVbFvJ2ibm5s-g1NyY"
message_title = "Akshay"
message_body = "Bahadur"


HOST = '192.168.2.7' #this is your localhost
PORT = 8888
 
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print 'socket created'
 
#Bind socket to Host and Port
try:
    s.bind((HOST, PORT))
except socket.error as err:
    print 'Bind Failed, Error Code: ' + str(err[0]) + ', Message: ' + err[1]
    sys.exit()

s.listen(1)

firebase_url='https://nari-9b323.firebaseio.com/'

lcd = Adafruit_CharLCD(rs=26, en=19,
                       d4=13, d5=6, d6=5, d7=11,
                       cols=16, lines=2)
GPIO.setup(14, GPIO.IN, pull_up_down=GPIO.PUD_UP)

def get_ip_address():
    return [
             (s.connect(('8.8.8.8', 53)),
              s.getsockname()[0],
              s.close()) for s in
                  [socket.socket(socket.AF_INET, socket.SOCK_DGRAM)]
           ][0][1]

ip = get_ip_address()



def buttonPressed(channel):
	lcd.clear()
	lcd.message('Location Sent')
	time_hhmmss = strftime('%H:%M:%S')
	date_mmddyyyy =strftime('%d/%m/%Y')
	data = {'date':date_mmddyyyy,'time':time_hhmmss,'ip':ip}
	print data
    #result_data = requests.post(firebase_url + '/SOSDetails.json', data=json.dumps(data))
    # result_push = push_service.notify_single_device(registration_id=registration_id, message_title=message_title, message_body=message_body)
    # print result
	conn, addr = s.accept()
	print 'Connect with ' + addr[0] + ':' + str(addr[1])
	conn.send("Hi")
	return
    
GPIO.add_event_detect(14, GPIO.RISING, callback=buttonPressed, bouncetime=250) 

try:
    while 1:
        lcd.clear()
        lcd.message(datetime.now().strftime('%b %d \n%H:%M:%S\n'))
        sleep(1)
        
except KeyboardInterrupt:
    print('CTRL-C pressed.  Program exiting...')

finally:
    lcd.clear()
