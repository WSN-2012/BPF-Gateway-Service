#!/bin/bash
java -jar build/jar/WSN-Linux.jar 'build/jar/dtn.config.xml' 3 'build/wsndata' 'build/wsnservice.log' "$@"
