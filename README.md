# Orange-Live-Object-Codec-LoRaWAN

The file "nkePulblicVx.y.js" is the full codec file used by Orange LiveObjects infastructure to decode Watteco sensors.

Until now new development on the codec file "nkePublicV2_x.js" was only tested tested [online](https://liveobjects.orange-business.com/#/login), 
requiring from "Orange LiveObjects Decoder service" to install the "under test" codec as a private one. 
It can then be tested with a corresponding sensor sending the expected dataflow. 
Once validated a new demand to "Orange LiveObject Decoder service", can actuate the delivery for public usage of the codec. Finally, the existing Watteco public profiles/codecs available with Orange LiveObject are defined in the [Orange LiveObject tutorial support page](https://support.watteco.com/live-objects-lpwa-tutorial/) from Watteco. 


However, it is also possible to test locally the codec before sending it to Orange. This can be done by using the dedicated Java/Maven framework available at orange. The way to use this framewaork is quickly described below.

## Entry points for Orange liveObjects framework 
- Documentation: https://liveobjects.orange-business.com/doc/html/lo_manual_v2.html#DATA_DECODER_SCRIPTABLE
- GitHub: https://github.com/DatavenueLiveObjects/Payload-decoders/tree/master
- Wiki: https://github.com/DatavenueLiveObjects/Payload-decoders/wiki


## Orange development and test framework installation steps
Notice that some others steps/version may lead to a working solution?

- Install last Eclipse JDT IDE (ex: Eclipse IDE for Java developper 4.28). It contains necessary Maven and Junit.
  (https://download.eclipse.org/eclipse/downloads/drops4/R-4.28-202306050440/download.php?dropFile=eclipse-SDK-4.28-win32-x86_64.zip)

- Install or verify you can run Java JDK 8 (ex: jdk-8u371-windows-x64.exe from https://www.oracle.com/fr/java/technologies/downloads/#java8-windows).
  Ensure that you path for Java will point in this newly installed JDK 

- Get the last dev framework from Github, and follow wiki to install necessary m2 structure (see above Github and follow Wiki)

- Import the project under your new Eclipse JDT
  The test framework was not directly working on my side, I had to: 
  Copy manually the lo-js-decoder-test-framework-utils-3.0.1-SNAPSHOT.jar in my .m2\repository\com\orange\lo\lo-js-decoder-test-framework-utils\3.0.1-SNAPSHOT 

- Ensure that Eclipse JDT is running JDK1.8 for the project. (notice NashhornScript that is used, as been suppressed from jdk since java 17)
  Select/Add correct JRE (the one from JDK 1.8) in "Properties/Java Build Path/Libraries" of your project. 
  From there, it should be possible to Run or Debug of src/test/resources example[1,2,3,4]

- ADD test units from Orange for nke codec.
  Ask last from Orange or copy existing from "./OrangeLiveObject_DecodersTestUnit/2.0" directory of current project
  Copy the tree in "/lo-js-decoder-test-framework-samples/src/test/java/com/orange/lo/decoder/js/nke/v2_0"
  Put your in dev "nkePublicVx_x.js" in "/lo-js-decoder-test-framework-samples/src/test/resources/javascript/nke/nkePublicV2_x.js"
  From there, it should be possible to Run or Debug any of the unit test like: For any sensor NkeClosoTest.java,... or for batches NkeBatch2_1Test.java
