# Weekly-Project-9
This week, I challenged myself to create an app that communicates between an android phone a computer using the UDP protocol.

The app, when complete, will act as a wireless trackpad for computers, allowing users to use their phones to control the mouse on screen.

This app utilizes the User Datagram Protocol (UDP) to send commands via the internet from phone to laptop.

In order to use this app, make sure both devices are connected to the same wireless network. Next, from the root directory of the repository execute the following commands
>source venv/bin/activate

>python3 src/AppServer.py

***If you get a "No module named 'pynput'" error, run the following lines in the terminal***
>pip3 install pynput

>python3 src/AppServer.py

These commands will enter the virtual environment and then run the code. Once the python script is running, open the mobile app. In the textfield labled "IP" input your computer's IP address. Then type in coordinates into the XPos and YPos textboxes. To make sure that your positions are valid, if you don't know your monitor's dimensions, it might be a good idea to start with 0 for both coordinates. Once both coordinates are given, clicking the send button will send the command to the computer to move the mouse to the position. You should see a message in the terminal window where the python script is running and the cursor should move. Once you have sent the first location, you can either move the mouse via entering coordinates or using the arrow buttons.

*If you move the mouse with a physical mouse, trackpad, or other device running this app, the mobile app, at this point in time, will not be aware, so any commands sent from the app will act as if there was no change.*
