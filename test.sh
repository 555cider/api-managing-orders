#!/bin/bash
mkdir /tmp/project
cp -a ./ /tmp/project
gradle clean -x test
