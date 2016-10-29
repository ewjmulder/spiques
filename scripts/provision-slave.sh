#!/bin/bash

# TODO: find a way to get this into the slaves easily

# Blacklist the wifi drivers, cause we won't be needing those
# TODO: enable
#echo "blacklist brcmfmac\nblacklist brcmutil" | sudo tee > /etc/modprobe.d/local-blacklist.conf

# TODO: block bluetooth

#echo "auto eth0
#iface eth0 inet static
#    address 192.168.4.10x
#    netmask 255.255.255.0
#    up route add -net 224.0.0.0 netmask 240.0.0.0 eth0"
#OR
#     "static domain_name_servers=8.8.8.8" \
#     | sudo tee -a /etc/dhcpcd.conf

#TODO: set timezone to Amsterdam
#TODO: set keyboard to US English with euro sign on 5

#TODO: steps to set up a new host
# 1. plug in a USB stick with the network init script + ssh key adder
# 2. execute the network init script (local command line)
# 2. execute the ssh key adder (local command line)
# 3. scp all other init scripts to the new slave + deb files
# 4. ssh to the host (without password)
# 5. execute the rest of the init scripts

cd /tmp

wget http://dl.piwall.co.uk/pwlibs1_1.1_armhf.deb
sudo dpkg -i pwlibs1_1.1_armhf.deb
wget http://dl.piwall.co.uk/pwomxplayer_20130815_armhf.deb
sudo dpkg -i pwomxplayer_20130815_armhf.deb

pwomxplayer --tile-code=43 udp://239.0.1.23:1234?buffer_size=1200000B
