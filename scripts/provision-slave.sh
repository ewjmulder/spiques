#!/bin/bash

# TODO: find a way to get this into the slaves easily

# Blacklist the wifi drivers, cause we won't be needing those
# TODO: enable
#echo "blacklist brcmfmac\nblacklist brcmutil" | sudo tee > /etc/modprobe.d/local-blacklist.conf

# TODO: block bluetooth

#echo "interface eth0\n\n" \
#     "static ip_address=192.168.4.10x/24\n" \
#     "static routers=192.168.4.100\n" \
#     "static domain_name_servers=192.168.4.100" \
#OR
#     "static domain_name_servers=8.8.8.8" \
#     | sudo tee -a /etc/dhcpcd.conf

cd /tmp

wget http://dl.piwall.co.uk/pwlibs1_1.1_armhf.deb
sudo dpkg -i pwlibs1_1.1_armhf.deb
wget http://dl.piwall.co.uk/pwomxplayer_20130815_armhf.deb
sudo dpkg -i pwomxplayer_20130815_armhf.deb

pwomxplayer --tile-code=43 udp://239.0.1.23:1234?buffer_size=1200000B
