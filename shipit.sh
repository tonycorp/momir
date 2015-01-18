#!/bin/bash

./gradlew clean test || exit

echo "ALL TESTS PASSED! PUSH TO GIT [y/n]?"

read -n 1 answer

if [ "$answer" == "y" ]; then
	echo
  	git push -u origin HEAD
else
	echo
	echo Exiting shipit script
fi