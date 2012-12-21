# Gateway Service for the BPF (Bundle Protocol Framework) 

## General
### About
This is a project made by (or partially by) WSN-Team 2012 in the course CSD, which is part of the [Technology Transfer Alliance](http://ttaportal.org/).
### Purpose
One of the goals of the project is to be able to send data upstream from rural areas and to do this DTN communication has been used.
One of the main goals of the project was to provide a platform independent solution for DTN communication. This was achieved by developing the BPF (Bundle Protocol Framework). The BPF cannot work on its own. It needs to be implemented with a application/service, which is device specific. This project, BPF-Gateway-Service, is an example of such a service.
### Description
This project is a service implementing the BPF and providing the functionalities for the gateway needed within the context of the WSN-Team 2012 project.  This service is based on a Linux environment and implements the platform-dependent classes of the BPF. In particular for the gateway node, the service includes a directory watcher. The watched directory contains the data that we want to send using DTN communication. Once THRESHOLD new bytes of data are saved into the folder, a bundle is generated containing the new data as payload.
## Build & Install
### Prerequisites
You will need to have ant to compile this in an easy way. To get ant look into how to install it on your platform.

### Building
The BPF is included as a submodule and it is built automatically when building the service. It is possible to configure several settings in the configuration file.
Follow the below steps to build service and BPF.

1.  `git clone --recursive https://github.com/WSN-2012/BPF-Gateway-Service.git`
2.  `cd BPF-Base-Service`
3.  `vi config/dtn.config.xml`
4.  `ant`

Note: When building the configuration located in config/dtn.config.xml is copied into the build folder together with the built jar. If you want to change any configuration options please edit the configuration stored in the built folder or rebuild.

### Start/Stop script
To just run the application after building it:

1.  `./run.sh`

To install a start/stop script, follow the below steps:

1.  `cp linux-scripts/bpf-service /etc/init.d/bpf-service`
2.  `/etc/init.d/bpf-service start`
3.  `/etc/init.d/bpf-service stop`

### Monitoring
To be able to monitor the service and automatically restarting it, we suggest using monit. To do this you will need to have set up the start/stop scripts as shown above. Follow the below steps to set the monitoring up:

1.  Install monit on your system. E.g: `sudo apt-get install monit`
2.  `cp linux-scripts/bpf-service.monit /etc/monit/conf.d/bpf-service`
3.  `/etc/init.d/monit restart`

### Start at boot
Do not forget to add the service to start at boot. E.g: `sudo update–rc.d bpf-service defaults`

## License
Copyright 2012 KTH

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   [http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
