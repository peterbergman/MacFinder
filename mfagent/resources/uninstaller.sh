#!/bin/bash
echo "Uninstalling MacFinder agent..."
sudo rm -r /opt/macfinder
sudo rm -r /var/root/Library/MacFinder
sudo launchctl unload -w /Library/LaunchDaemons/org.macfinder.agent.plist
sudo rm /Library/LaunchDaemons/org.macfinder.agent.plist
echo "Done!"
