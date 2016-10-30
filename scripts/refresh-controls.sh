# rebuild the controls jar
gw build 

# distribute the controls jar
scp controls/build/libs/controls.jar pi@192.168.4.1:~ 

# Start the control
sm1 "sudo java -cp controls.jar:pi4j-core-1.1.jar ListenGpioExample >> ~/spiques.log 2>&1 &" > /dev/null 2>&1 &

#TODO: poll and write message when controls is up.