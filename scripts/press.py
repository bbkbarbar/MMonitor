import RPi.GPIO as GPIO ## Import GPIO library
import argparse
import time

GPIO.setwarnings(False)

# File ranemed because of Python based serial output ISSUE:
# Solution found: 
# http://stackoverflow.com/questions/23572003/why-io-module-object-has-no-attribute-rawiobase-eventhough-i-am-using-python-2

def main():
	parser = argparse.ArgumentParser('Button press emulator')
	parser.add_argument("-b","--button", type=int, help="pin of button")
	parser.add_argument("-d","--duration", type=int, help="Duration of press", default = 500)

	args = parser.parse_args()

	button = args.button
	duration = args.duration

	GPIO.setmode(GPIO.BOARD) ## Use board pin numbering

	GPIO.setup(button, GPIO.OUT) ## Setup GPIO Pin 7 to OUT
	GPIO.output(button,True) ## Turn on GPIO pin 7
	print "Button: " + str(button) + " pressed.."
	time.sleep (duration / 1000.0)
	GPIO.output(button,False)
	print "Button: " + str(button) + " released.."

if __name__ == "__main__":
	main()