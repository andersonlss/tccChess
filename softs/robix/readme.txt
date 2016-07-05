Robix(tm) Usbor Software 1.1.0
www.robix.com
2005-03-21

=========TABLE OF CONTENTS===========

1.  System Requirements
2.  Driver Setup
      Windows 2000 and XP
      Windows 95/98/NT4
      Mac OSX
      PC-Linux
3.  Java Setup
4.  Changes from DOS version
      Command omissions
5.  Known Issues
      Firmware update required
6.  Known Bugs
7.  Programming Reference (Java/C/C++/VB)
8.  Discussion Forums
9.  Bug reporting
10. Revision history


==================================
1. SYSTEM REQUIREMENTS
==================================

To run the Robix Usbor software, you need:

* One or more of the following operating systems:
  - Microsoft Windows 2000/XP
  - Mac OSX 10.2.3 or greater
  - PC-Linux (x86) with kernel 2.6 or greater and the USB virtual
    file system (usbfs)
* Java Runtime version 1.4.2 or greater
* Robix Usbor Robotic Servo Controller(s)


==================================
2. DRIVER SETUP
==================================

Windows 2000 and Windows XP
===========================

NOTE: If you are updating your Usbor software from a previous
      version, you do not need to update the device driver.

1. Apply power to Usbor device.
2. Connect USB cable to Usbor device and to your computer.
3. Windows should find "Usbor-32i" device and start the New Hardware Wizard.
4. Select 'Search for a suitable driver for my device (recommended)'.
5. Click 'Next'.
6. If you are installing from a CD, select 'CD-ROM drives'.
7. Select 'Specify a location'.
8. Click 'Next'.
9. Click 'Browse' and navigate to the Usbor driver directory which
   contains UsborDrv.inf and UsborDrv.sys (probably in the 'driver'
   sub-directory of the same directory as this readme file).
10. Click 'OK'. Windows should say that it found a driver for this device.
11. Click 'Next' to install the driver.
12. Click 'Finish'


Windows 95/98/ME/NT4
====================
These operating systems are not supported.


MAC OSX
=======
No driver setup is required under OSX.


PC-Linux
========
The Usbor Software does not provide a kernel driver for accessing Usbor devices.
Instead, hardware is accessed from user-mode through the USB virtual filesystem
(usbfs). Usbfs is commonly mounted as /proc/bus/usb and contains a node for each
detected USB device. In order for the Usbor Software to detect and control an
Usbor device, the software must have read and write access to the device's node
in usbfs. Depending on your linux distribution, the default permissions for
nodes in the usbfs may vary. If the default permissions do not allow read/write
access by the Usbor Software (i.e. by the user running the Usbor Software), you
(or your system administrator) will have to change the permissions accordingly.


==============================================
3. JAVA SETUP
==============================================

The Usbor software requires a Java environment. Sun Microsystems
offers several different Java distributions for free download.

We recommend J2SE SDK, which stands for Java 2 Platform Standard
Edition Software Development Kit. You'll need version 1.4.1 or later.

As of this writing the latest J2SE SDK can be downloaded from:
http://java.sun.com/j2se/1.4.2/download.html
(Click the link that reads 'Download J2SE SDK'.)

(When you actually read this note there may be a later Java version
available, in which case you can try that one. Sun recognizes the need
for backward compatibility, so later versions should work fine; however,
this is out of our control and so it is hard for us to guarantee
compatibility. For this release of the Usbor software, you may need to
use a 1.4 version.)


==============================================
4. CHANGES FROM WINDOWS PARALLEL PORT VERSION
==============================================

COMMAND OMISSIONS
==================

FORGET
------
Forget is no longer needed and will be ignored in future versions of
the software.

PAUSE
-----
The command 'Pause'  has been replaced by the command 'Wait'.
'Wait' has the same meaning that 'Pause' had.


==================================
5. KNOWN ISSUES
==================================

In Windows, we have experienced some problems with the Sonix webcam
driver, especially with regatds to USB bandwidth. Running multiple
cameras at high bandwidth can occasionally cause a system crash or
BSOD (Blue Screen of Death). By default, camera bandwidth is set to
a safe, low setting by the Usbor software. Although a spinner is
provided for adjusting USB bandwidth, we recommend that you leave it
at its default setting. Moreover, we have observed that increasing
the bandwidth places a greater load on the system, with little or no
noticeable benifit to the Usbor software.


==================================
6. KNOWN BUGS
==================================

There are no known bugs as of this writing.


==================================
7. PROGRAMMING REFERENCE
==================================

Software developers can access Usbor devices via the Nexway API.

Java is our preferred development language, but we have also
provided a C-style shell wrapper (aka NexwayLib) for our Java
classes. This allows C/C++ and Visual Basic .NET applications
to access the Nexway API.

Documentation is fairly complete for the Java API and the C/C++ API.
As of this writing, separate VB documentation is not planned. VB
users should explore the C/C++ API.

Default locations for documentation:
Java API
 Windows: C:\Program Files\Robix\Usbor\doc\API\Java\index.html
 Mac OSX: /Applications/Robix/Usbor/doc/API/Java/index.html
 Linux: /opt/Robix/Usbor/doc/API/Java/index.html
C/C++ NexwayLib API
 Windows: C:\Program Files\Robix\Usbor\doc\API\NexwayLib\index.html
 Mac OSX: /Applications/Robix/Usbor/doc/API/NexwayLib/index.html
 Linux: /opt/Robix/Usbor/doc/API/NexwayLib/index.html

Sample programs are available in the /examples directory of the
Usbor software. As of this writing, only one introductory example
is provided, but it is available in Java, C, and VB.


Our C/C++ Nexway library is statically linked with a product called Jace
(available at http://jace.reyelts.com/jace).

Jace is made available under the BSD license as follows:
----------
Copyright (c) 2002, Toby Reyelts
All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:

- Redistributions of source code must retain the above copyright notice,
   this list of conditions and the following disclaimer.
- Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.
- Neither the name of Toby Reyelts nor the names of his contributors
   may be used to endorse or promote products derived from this software
   without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
----------


==================================
8. DISCUSSION FORUMS
==================================

Please visit our forums at:
  http://www.robix.com


==================================
9. BUG REPORTING
==================================

Please report software problems and comments to:
   bugs@robix.com


==================================
10. REVISION HISTORY
==================================

ver 1.1.0  - 2005-03-21
           - added support for Nexway control of cognition parameters
           - new Java class: CognitionParams
           - added cognit viewer to Nexus
           - fixed cognition bugs
           - changed list of 'known objects' for vision system

ver 1.0.0  - 2005-03-01
           - public release

ver 0.0.14 - internal development release
           - C/C++/VB NexwayLib library documented
           - fixed vision bugs
           - added numerous camera/vision features
             - tunable vision parameters
             - enhanced Cognit display
             - added streaming of 'source' image to Nexways

ver 0.0.13 - version number skipped

ver 0.0.12 - internal development release
           - Config Editor: removed System_All_Const parent servo
           - added config dump to Info tab
           - changed format of config dump
           - visual touchups

ver 0.0.11 - internal development release
           - script-tracking improved
           - visual touchups

ver 0.0.10 - internal development release
           - camera/vision introduced


ver 0.0.9 - digout bug fixed
          - 'Classic' Usbor Configuration now built into Config Editor
          - Nexway API documented
          - C/C++/VB library (rbxNexwayLib) introduced
          - default look-and-feel on Mac OSX changed to Aqua
          - directory structure changed on Mac OSX
          - cosmetic changes made

ver 0.0.8 - initial release

