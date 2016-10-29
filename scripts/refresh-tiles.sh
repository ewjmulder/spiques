# Shut down all running services
curl --silent -X POST http://192.168.4.101:8181/spiques/shutdown
curl --silent -X POST http://192.168.4.102:8181/spiques/shutdown
curl --silent -X POST http://192.168.4.103:8181/spiques/shutdown
curl --silent -X POST http://192.168.4.104:8181/spiques/shutdown

# rebuild the service fat jar
gw build bootRepackage

# distribute the service fat jar to all tiles
scp tile/service/build/libs/service.jar pi@192.168.4.101:~
scp tile/service/build/libs/service.jar pi@192.168.4.102:~
scp tile/service/build/libs/service.jar pi@192.168.4.103:~
scp tile/service/build/libs/service.jar pi@192.168.4.104:~

# rebuild the jfx app
gw jfxJar

# distribute the jfx app to all tiles
scp tile/service/build/jfx/app/project-jfx.jar pi@192.168.4.101:~
scp tile/service/build/jfx/app/project-jfx.jar pi@192.168.4.102:~
scp tile/service/build/jfx/app/project-jfx.jar pi@192.168.4.103:~
scp tile/service/build/jfx/app/project-jfx.jar pi@192.168.4.104:~

# Start the tile services
st1 "java -Dspiques.tile.properties.location=./spiques.tile.properties.example -Dtile.code=41 -jar service.jar >> ~/spiques.log 2>&1 &" > /dev/null 2>&1 &
st2 "java -Dspiques.tile.properties.location=./spiques.tile.properties.example -Dtile.code=42 -jar service.jar >> ~/spiques.log 2>&1 &" > /dev/null 2>&1 &
st3 "java -Dspiques.tile.properties.location=./spiques.tile.properties.example -Dtile.code=43 -jar service.jar >> ~/spiques.log 2>&1 &" > /dev/null 2>&1 &
st4 "java -Dspiques.tile.properties.location=./spiques.tile.properties.example -Dtile.code=44 -jar service.jar >> ~/spiques.log 2>&1 &" > /dev/null 2>&1 &

#TODO: poll all ping endpoints and write message when all tiles are up.