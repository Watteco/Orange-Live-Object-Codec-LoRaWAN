package com.orange.lo.decoder.js.nke.v2_0;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.orange.decoder.exception.DecodingException;
import com.orange.decoder.exception.js.JsDecoderBuildingException;
import com.orange.decoder.js.JsDecoderFactory;
import com.orange.decoder.simple.SimpleDecoder;
import com.orange.lo.decoder.js.TestBase;
import com.orange.lo.decoder.js.doc.annotation.DeviceDescription;
import com.orange.lo.decoder.js.doc.annotation.PayloadDescription;
import com.orange.lo.decoder.js.exception.JsDecodingException;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterMultiMasterSlaveAnswers;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterSerialInterface;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterSerialMasterSlaveProtocol;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/* Tests clusters  :                 
0x8006 Serial Interface
0x8007 Serial Master/Slave protocol
0x8009 (only v3.5.0.4740) Multi Master/Slave answers
*/
@Slf4j
@DeviceDescription(name = "Watteco (NKE-Watteco) Modbus", manufacturer = "WATTECO", docLink = "https://support.watteco.com/",
        version = { "50-70-080(class A) 50-70-109(class C)",
                "</h5>",
                "</br>",
                "</br>",
                "<h5><b>* Decoding of cluster 8007 (serial master/slave protocol) Answer field (0001) according modbus tags associated to the device:</b></h5>",
                "</br>",
                "<p>Declare tags, in order, each tag describing each field present in Answer data, with the following tag format:</p>",
                "<b> MODBUS_8007_EPn_r_name_unit_type</b> where:",
                "<div>-&nbsp;n is the EndPoint number</div>",
                "<div>-&nbsp;r is the raw in the fields list or the number order</div>",
                "<div>-&nbsp;name in camelCase format, is the key which will appear in the generated json containing the decoded value</div>",
                "<div>-&nbsp;unit indicates the unit associated to the value. If the value has no unit, use 'NOUNIT'.</div>",
                "<div>-&nbsp;type must be one of this list, according to the data field value coding:<ul>",
                "<li>'U8' 		unsigned on 1 byte</li>",
                "<li>'S8'  		signed on 1 byte</li>",
                "<li>'U16' 		unsigned on 2 bytes</li>",
                "<li>'S16' 		signed on 2 bytes</li>",
                "<li>'U32' 		unsigned on 4 bytes</li>",
                "<li>'S32' 		signed on 4 bytes</li>",
                "<li>'U32L'	 	unsigned on 4 bytes inverted on 2 bytes</li>",
                "<li>'S32L'	 	signed on 4 bytes inverted on 2 bytes</li>",
                "<li>'FL' 		float on 4 bytes</li>",
                "<li>'FL2L'	 	float on 4 bytes inverted on 2 bytes</li>",
                "<li>'An'		ascii string composed of n characters</li>",
                "<li>'Hn' 		hexa string composed of nbytes</li>",
                "<li>'Bn'	  	coded on n bits	Bn</li>",
                "<li>'Rn'	 	reserved or padding of n bits</li></ul></div>",
                "<p>Examples: MODBUS_8007_EP0_1_humidity_%_U8, MODBUS_8007_EP0_2_outdoorTemperature_°C_S8, MODBUS_8007_EP0_3_pressure_Pa_U16, MODBUS_8007_EP0_4_counter_NOUNIT_S16</p>",
                "<p></p>",
                "</br>",
                "<h5><b>* Decoding of cluster 8009 (Multi Master/Slave answers cluster) PresentValue field (0000) according modbus tags associated to the device:</b></h5>",
                "</br>",
                "<p>First, declare the tag <b>MODBUS_2009_WITHOUT_HEADER</b> if the header is not present before each endpoint payload.</p>",
                "</br>",
                "<p>Then declare MODBUS_8009 tags, in order, each tag describing each field present in an endpoint data block of the frame, with the same tag format than for MODBUS_8007 tags:</p>",
                "<b> MODBUS_8009_EPn_r_name_unit_type</b> where:",
                "<div>-&nbsp;n is the EndPoint number</div>",
                "<div>-&nbsp;r is the raw in the fields list or the number order in the endpoint data block</div>",
                "<div>-&nbsp;name in camelCase format, is the key, which will appear, prefixed by multimodbusEP number, in the generated json containing the decoded value</div>",
                "<div>-&nbsp;unit indicates the unit associated to the value. If the value has no unit, use 'NOUNIT'.</div>",
                "<div>-&nbsp;type must be one of the list given for 8007 cluster tags.</div>",
                "<div>Example: when the endpoint 0 data block contains 2 datas in this order: humidity and temperature, declare MODBUS_8009_EP0_1_humidity_%_U8 and MODBUS_8007_EP0_2_temperature_°C_S8</div>",
                "<div>So, JSON will contain humidity and temperature keys in multimodbusEP0 substructure.</div>.",
                "</br>",
                "<h5><b>* Modbus profile tags</b></h5>",
                "<p>As the same modbus tags are often declared for some device types, the decoder now contains modbus tags sets defined by these tags:</p>",
                "<p>MODBUS_8007_PROFILE_EP0_10_REGS_NOUNIT_FL, MODBUS_8007_PROFILE_EP0_10_REGS_NOUNIT_FL2L, </p>",
                "<p>MODBUS_8009_PROFILE_NOHEADER_10_ENDPOINTS_REG_NOUNIT_FL and MODBUS_8009_PROFILE_NOHEADER_10_ENDPOINTS_REG_NOUNIT_FL2L </p>",
                "<p>So it is now possible to declare one of these 4 MODBUS PROFILE tags, replacing the recurrent modbus tags. The decoder will replace the profile tag by the corresponding modbus tags.</p>",
                "<p>See _tag_PROFILE tests and their description below, to get the corresponding modbus tags for each of these 4 profile tags.</p>",
                "<h5>" },
        encoding = "watteco_generic", hidden = false)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NkeModbusTest extends TestBase {

    private final String SCRIPT_PATH = "nke/nkePublicV2_8";

    @Test
    public void should_profile() throws JsDecodingException {
        String input = "1101800600008822002580";
        log.info("result: {}", profile(SCRIPT_PATH, input, null));
    }

    @Test
    public void should_decode_8006_SerialInterface_attribut0000_cmd01_payload_status_fail() throws JsDecodingException {
        /* payload build for test */
        String input = "1101800600008822002580";

        NKEClusterSerialInterface serialInterface = formatAndDecode(SCRIPT_PATH, input, NKEClusterSerialInterface.class);
        assertThat(serialInterface.getMessageType()).isEqualTo("Serial Interface");
        assertThat(serialInterface.getEndpoint()).isEqualTo("0");
        assertThat(serialInterface.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(serialInterface.getAttributId()).isEqualTo("Speed");
        assertThat(serialInterface.getStatus()).isEqualTo("Invalid Value");
    }

    @Test
    @PayloadDescription(name = "ClusterSerialInterface_Speed", description = "Cluster: Serial Interface, Attribut: Speed, Command: Read Attribute Response.")
    public void should_decode_8006_SerialInterface_attribut0000_cmd01_payload_speed_9600() throws JsDecodingException {
        /* payload build for test */
        String input = "1101800600000022002580";

        NKEClusterSerialInterface serialInterface = formatAndDecode(SCRIPT_PATH, input, NKEClusterSerialInterface.class);
        assertThat(serialInterface.getMessageType()).isEqualTo("Serial Interface");
        assertThat(serialInterface.getEndpoint()).isEqualTo("0");
        assertThat(serialInterface.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(serialInterface.getAttributId()).isEqualTo("Speed");
        assertThat(serialInterface.getSpeed().getValue()).isEqualTo(9600);
        assertThat(serialInterface.getSpeed().getUnit()).isEqualTo("bit/s");
    }

    @Test
    @PayloadDescription(name = "ClusterSerialInterface_Databits", description = "Cluster: Serial Interface, Attribut: Data bits, Command: Read Attribute Response.")
    public void should_decode_8006_SerialInterface_attribut0001_cmd01_payload_databits_7() throws JsDecodingException {
        /* payload build for test */
        String input = "110180060001002007";

        NKEClusterSerialInterface serialInterface = formatAndDecode(SCRIPT_PATH, input, NKEClusterSerialInterface.class);
        assertThat(serialInterface.getMessageType()).isEqualTo("Serial Interface");
        assertThat(serialInterface.getEndpoint()).isEqualTo("0");
        assertThat(serialInterface.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(serialInterface.getAttributId()).isEqualTo("Data Bits");
        assertThat(serialInterface.getDataBits()).isEqualTo(7);
    }

    @Test
    @PayloadDescription(name = "ClusterSerialInterface_Parity", description = "Cluster: Serial Interface, Attribut: Parity, Command: Read Attribute Response.")
    public void should_decode_8006_SerialInterface_attribut0002_cmd01_payload_parity_None() throws JsDecodingException {
        /* payload build for test */
        String input = "110180060002002000";

        NKEClusterSerialInterface serialInterface = formatAndDecode(SCRIPT_PATH, input, NKEClusterSerialInterface.class);
        assertThat(serialInterface.getMessageType()).isEqualTo("Serial Interface");
        assertThat(serialInterface.getEndpoint()).isEqualTo("0");
        assertThat(serialInterface.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(serialInterface.getAttributId()).isEqualTo("Parity");
        assertThat(serialInterface.getParity()).isEqualTo("None");
    }

    @Test
    @PayloadDescription(name = "ClusterSerialInterface_Parity", description = "Cluster: Serial Interface, Attribut: Parity, Command: Read Attribute Response.")
    public void should_decode_8006_SerialInterface_attribut0002_cmd01_payload_parity_odd() throws JsDecodingException {
        /* payload build for test */
        String input = "110180060002002001";

        NKEClusterSerialInterface serialInterface = formatAndDecode(SCRIPT_PATH, input, NKEClusterSerialInterface.class);
        assertThat(serialInterface.getMessageType()).isEqualTo("Serial Interface");
        assertThat(serialInterface.getEndpoint()).isEqualTo("0");
        assertThat(serialInterface.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(serialInterface.getAttributId()).isEqualTo("Parity");
        assertThat(serialInterface.getParity()).isEqualTo("Odd");
    }

    @Test
    @PayloadDescription(name = "ClusterSerialInterface_Parity", description = "Cluster: Serial Interface, Attribut: Parity, Command: Read Attribute Response.")
    public void should_decode_8006_SerialInterface_attribut0002_cmd01_payload_parity_even() throws JsDecodingException {
        /* payload build for test */
        String input = "110180060002002002";

        NKEClusterSerialInterface serialInterface = formatAndDecode(SCRIPT_PATH, input, NKEClusterSerialInterface.class);
        assertThat(serialInterface.getMessageType()).isEqualTo("Serial Interface");
        assertThat(serialInterface.getEndpoint()).isEqualTo("0");
        assertThat(serialInterface.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(serialInterface.getAttributId()).isEqualTo("Parity");
        assertThat(serialInterface.getParity()).isEqualTo("Even");
    }

    @Test
    @PayloadDescription(name = "ClusterSerialInterface_Parity", description = "Cluster: Serial Interface, Attribut: Parity, Command: Read Attribute Response.")
    public void should_decode_8006_SerialInterface_attribut0002_cmd01_payload_parity_unknown() throws JsDecodingException {
        /* payload build for test */
        String input = "110180060002002003";

        NKEClusterSerialInterface serialInterface = formatAndDecode(SCRIPT_PATH, input, NKEClusterSerialInterface.class);
        assertThat(serialInterface.getMessageType()).isEqualTo("Serial Interface");
        assertThat(serialInterface.getEndpoint()).isEqualTo("0");
        assertThat(serialInterface.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(serialInterface.getAttributId()).isEqualTo("Parity");
        assertThat(serialInterface.getParity()).isEqualTo("Unknown");
    }

    @Test
    @PayloadDescription(name = "ClusterSerialInterface_StopBits", description = "Cluster: Serial Interface, Attribut: Stop Bits, Command: Read Attribute Response.")
    public void should_decode_8006_SerialInterface_attribut0003_cmd01_payload_stop_bits() throws JsDecodingException {
        /* payload build for test */
        String input = "310180060003002001";

        NKEClusterSerialInterface serialInterface = formatAndDecode(SCRIPT_PATH, input, NKEClusterSerialInterface.class);
        assertThat(serialInterface.getMessageType()).isEqualTo("Serial Interface");
        assertThat(serialInterface.getEndpoint()).isEqualTo("1");
        assertThat(serialInterface.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(serialInterface.getAttributId()).isEqualTo("Stop Bits");
        assertThat(serialInterface.getStopBits()).isEqualTo(1);
    }

    @Test
    @PayloadDescription(name = "ClusterMasterSlaveProtocol_Request", description = "Cluster: Master/Slave Protocol, Attribut: Request, Command: Read Attribute Response.")
    public void should_decode_8007_MasterSlaveProtocol_attribut0000_cmd01_payload() throws JsDecodingException {
        /* payload build for test : frame = AE */
        String input = "110180070000004101AE";
        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "] }";

        NKEClusterSerialMasterSlaveProtocol masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterSerialMasterSlaveProtocol.class);
        assertThat(masterSlaveProtocol.getMessageType()).isEqualTo("Serial Master/Slave Protocol");
        assertThat(masterSlaveProtocol.getEndpoint()).isEqualTo("0");
        assertThat(masterSlaveProtocol.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(masterSlaveProtocol.getAttributId()).isEqualTo("Request");
        assertThat(masterSlaveProtocol.getApplicationType()).isNull();
        assertThat(masterSlaveProtocol.getRequestFrame()).isEqualTo("AE");
        assertThat(masterSlaveProtocol.getAnswerFrame()).isNull();

        /* payload build for test : frame = empty ! */
        input = "510180070000004100";

        masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterSerialMasterSlaveProtocol.class);
        assertThat(masterSlaveProtocol.getMessageType()).isEqualTo("Serial Master/Slave Protocol");
        assertThat(masterSlaveProtocol.getEndpoint()).isEqualTo("2");
        assertThat(masterSlaveProtocol.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(masterSlaveProtocol.getAttributId()).isEqualTo("Request");
        assertThat(masterSlaveProtocol.getApplicationType()).isNull();
        assertThat(masterSlaveProtocol.getRequestFrame()).isEqualTo("");
        assertThat(masterSlaveProtocol.getAnswerFrame()).isNull();

        /* payload build for test : frame = 112233445566778899AABBCCDDEEFF21 */
        input = "910180070000004110112233445566778899AABBCCDDEEFF21";

        masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterSerialMasterSlaveProtocol.class);
        assertThat(masterSlaveProtocol.getMessageType()).isEqualTo("Serial Master/Slave Protocol");
        assertThat(masterSlaveProtocol.getEndpoint()).isEqualTo("4");
        assertThat(masterSlaveProtocol.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(masterSlaveProtocol.getAttributId()).isEqualTo("Request");
        assertThat(masterSlaveProtocol.getApplicationType()).isNull();
        assertThat(masterSlaveProtocol.getRequestFrame()).isEqualTo("112233445566778899AABBCCDDEEFF21");
        assertThat(masterSlaveProtocol.getAnswerFrame()).isNull();
    }

    @Test
    @PayloadDescription(name = "ClusterMasterSlaveProtocol_Answer", description = "Cluster: Master/Slave Protocol, Attribut: Answer, Command: Report attributes. No modbus tag associated to the device.")
    public void should_decode_8007_MasterSlaveProtocol_attribut0001_cmd0A_notag_payload() throws JsDecodingException {
        /* payload build for test : frame = AE */
        String input = "110A800700014101AE";
        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "] }";

        NKEClusterSerialMasterSlaveProtocol masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterSerialMasterSlaveProtocol.class);
        assertThat(masterSlaveProtocol.getMessageType()).isEqualTo("Serial Master/Slave Protocol");
        assertThat(masterSlaveProtocol.getEndpoint()).isEqualTo("0");
        assertThat(masterSlaveProtocol.getCommandId()).isEqualTo("Report Attributes");
        assertThat(masterSlaveProtocol.getAttributId()).isEqualTo("Answer");
        assertThat(masterSlaveProtocol.getApplicationType()).isNull();
        assertThat(masterSlaveProtocol.getRequestFrame()).isNull();
        assertThat(masterSlaveProtocol.getAnswerFrame()).isEqualTo("AE");

        /* payload build for test : frame = empty ! */
        input = "510A800700014100";

        masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterSerialMasterSlaveProtocol.class);
        assertThat(masterSlaveProtocol.getMessageType()).isEqualTo("Serial Master/Slave Protocol");
        assertThat(masterSlaveProtocol.getEndpoint()).isEqualTo("2");
        assertThat(masterSlaveProtocol.getCommandId()).isEqualTo("Report Attributes");
        assertThat(masterSlaveProtocol.getAttributId()).isEqualTo("Answer");
        assertThat(masterSlaveProtocol.getApplicationType()).isNull();
        assertThat(masterSlaveProtocol.getRequestFrame()).isNull();
        assertThat(masterSlaveProtocol.getAnswerFrame()).isEqualTo("");

    }

    @Test
    @PayloadDescription(name = "ClusterMasterSlaveProtocol_Answer", description = "Cluster: Master/Slave Protocol, Attribut: Answer, Command: Read attribute response. No modbus tag associated to the device.")
    public void should_decode_8007_MasterSlaveProtocol_attribut0001_cmd01_notag_payload() throws JsDecodingException {
        /* payload build for test : frame = AE */
        String input = "910180070001004110112233445566778899AABBCCDDEEFF21";
        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "] }";

        NKEClusterSerialMasterSlaveProtocol masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterSerialMasterSlaveProtocol.class);
        assertThat(masterSlaveProtocol.getMessageType()).isEqualTo("Serial Master/Slave Protocol");
        assertThat(masterSlaveProtocol.getEndpoint()).isEqualTo("4");
        assertThat(masterSlaveProtocol.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(masterSlaveProtocol.getAttributId()).isEqualTo("Answer");
        assertThat(masterSlaveProtocol.getApplicationType()).isNull();
        assertThat(masterSlaveProtocol.getRequestFrame()).isNull();
        assertThat(masterSlaveProtocol.getAnswerFrame()).isEqualTo("112233445566778899AABBCCDDEEFF21");
    }

    @Test
    @PayloadDescription(name = "ClusterMasterSlaveProtocol_ApplicationType", description = "Cluster: Master/Slave Protocol, Attribut: Application Type, Command: Read attribute response.")
    public void should_decode_8007_MasterSlaveProtocol_attribut0002_cmd01_payload() throws JsDecodingException {
        /* payload build for test : ModBus Application */
        String input = "110180070002002000";

        NKEClusterSerialMasterSlaveProtocol masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, NKEClusterSerialMasterSlaveProtocol.class);
        assertThat(masterSlaveProtocol.getMessageType()).isEqualTo("Serial Master/Slave Protocol");
        assertThat(masterSlaveProtocol.getEndpoint()).isEqualTo("0");
        assertThat(masterSlaveProtocol.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(masterSlaveProtocol.getAttributId()).isEqualTo("Application Type");
        assertThat(masterSlaveProtocol.getApplicationType()).isEqualTo("ModBus");
        assertThat(masterSlaveProtocol.getRequestFrame()).isNull();
        assertThat(masterSlaveProtocol.getAnswerFrame()).isNull();

        /* payload build for test : Unknown Application */
        input = "510180070002002001";

        masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, NKEClusterSerialMasterSlaveProtocol.class);
        assertThat(masterSlaveProtocol.getMessageType()).isEqualTo("Serial Master/Slave Protocol");
        assertThat(masterSlaveProtocol.getEndpoint()).isEqualTo("2");
        assertThat(masterSlaveProtocol.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(masterSlaveProtocol.getAttributId()).isEqualTo("Application Type");
        assertThat(masterSlaveProtocol.getApplicationType()).isEqualTo("Unknown");
        assertThat(masterSlaveProtocol.getRequestFrame()).isNull();
        assertThat(masterSlaveProtocol.getAnswerFrame()).isNull();
    }

    /********** tests tags modbus 8007 ***************/

    @Test
    @PayloadDescription(name = "ClusterMasterSlaveProtocol_Answer", description = "Cluster: Master/Slave Protocol, Attribut: Answer, Command: Report attributes. Modbus tags associated to the device: \n"
            + "MODBUS_8007_EP0_1_modbusfregister00_NOUNIT_FL,\n"
            + "MODBUS_8007_EP0_2_modbusfregister01_NOUNIT_FL,\n"
            + "MODBUS_8007_EP0_3_modbusfregister02_NOUNIT_FL,\n"
            + "MODBUS_8007_EP0_4_modbusfregister03_NOUNIT_FL,\n"
            + "MODBUS_8007_EP0_5_modbusfregister04_NOUNIT_FL,\n"
            + "MODBUS_8007_EP0_6_modbusfregister05_NOUNIT_FL,\n"
            + "MODBUS_8007_EP0_7_modbusfregister06_NOUNIT_FL,\n"
            + "MODBUS_8007_EP0_8_modbusfregister07_NOUNIT_FL\n")
    public void should_decode_8007_MasterSlaveProtocol_attribut0001_tags_float_0A_payload() throws IOException, DecodingException, JsDecoderBuildingException, JsDecodingException {

        String input = "110a8007000141230103200000000000000000497423f0497423f0436e0000436900004250000041B11EB8";

        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"MODBUS_8007_EP0_1_modbusfregister00_NOUNIT_FL\","
                + "\"MODBUS_8007_EP0_2_modbusfregister01_NOUNIT_FL\","
                + "\"MODBUS_8007_EP0_3_modbusfregister02_NOUNIT_FL\","
                + "\"MODBUS_8007_EP0_4_modbusfregister03_NOUNIT_FL\","
                + "\"MODBUS_8007_EP0_5_modbusfregister04_NOUNIT_FL\","
                + "\"MODBUS_8007_EP0_6_modbusfregister05_NOUNIT_FL\","
                + "\"MODBUS_8007_EP0_7_modbusfregister06_NOUNIT_FL\","
                + "\"MODBUS_8007_EP0_8_modbusfregister07_NOUNIT_FL\""
                + "] }";

        NKEClusterSerialMasterSlaveProtocol masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterSerialMasterSlaveProtocol.class);
        String script = loadJavascriptFile(SCRIPT_PATH);
        SimpleDecoder decoder = JsDecoderFactory.buildDefaultDecoder(script);
        Gson gwm = new Gson();
        Object owm = gwm.fromJson(inputDataMessage, Object.class);
        JsonObject decodedJsonObject = decoder.toJsonObject(input, owm);

        assertThat(decodedJsonObject.getAsJsonPrimitive("endpoint").getAsString()).isEqualTo("0");
        assertThat(decodedJsonObject.getAsJsonPrimitive("commandId").getAsString()).isEqualTo("Report Attributes");
        assertThat(decodedJsonObject.getAsJsonPrimitive("messageType").getAsString()).isEqualTo("Serial Master/Slave Protocol");
        assertThat(decodedJsonObject.getAsJsonPrimitive("attributId").getAsString()).isEqualTo("Answer");
        assertThat(decodedJsonObject.getAsJsonPrimitive("additionalAddress").getAsString()).isEqualTo("01");
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusCommand").getAsString()).isEqualTo("Read Holding Registers");
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusfregister00").getAsFloat()).isEqualTo(0f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusfregister01").getAsFloat()).isEqualTo(0f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusfregister02").getAsFloat()).isEqualTo(999999.0f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusfregister03").getAsFloat()).isEqualTo(999999.0f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusfregister04").getAsFloat()).isEqualTo(238.000000f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusfregister05").getAsFloat()).isEqualTo(233.000000f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusfregister06").getAsFloat()).isEqualTo(52.000000f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusfregister07").getAsFloat()).isEqualTo(22.139999f);

    }

    @Test
    public void should_decode_8007_MasterSlaveProtocol_attribut0001_tags_float_01_payload() throws IOException, DecodingException, JsDecoderBuildingException, JsDecodingException {

        String input = "1101800700010041230103200000000000000000497423f0497423f0436e0000436900004250000000000000";

        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"MODBUS_8007_EP0_1_modbusfregister00_NOUNIT_FL\","
                + "\"MODBUS_8007_EP0_2_modbusfregister01_NOUNIT_FL\","
                + "\"MODBUS_8007_EP0_3_modbusfregister02_NOUNIT_FL\","
                + "\"MODBUS_8007_EP0_4_modbusfregister03_NOUNIT_FL\","
                + "\"MODBUS_8007_EP0_5_modbusfregister04_NOUNIT_FL\","
                + "\"MODBUS_8007_EP0_6_modbusfregister05_NOUNIT_FL\","
                + "\"MODBUS_8007_EP0_7_modbusfregister06_NOUNIT_FL\","
                + "\"MODBUS_8007_EP0_8_modbusfregister07_NOUNIT_FL\""
                + "] }";

        NKEClusterSerialMasterSlaveProtocol masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterSerialMasterSlaveProtocol.class);

        String script = loadJavascriptFile(SCRIPT_PATH);
        SimpleDecoder decoder = JsDecoderFactory.buildDefaultDecoder(script);
        Gson gwm = new Gson();
        Object owm = gwm.fromJson(inputDataMessage, Object.class);
        JsonObject decodedJsonObject = decoder.toJsonObject(input, owm);

        // Assertions to check that the decoded payload is compliant with the expected result

        assertThat(decodedJsonObject.getAsJsonPrimitive("endpoint").getAsString()).isEqualTo("0");
        assertThat(decodedJsonObject.getAsJsonPrimitive("commandId").getAsString()).isEqualTo("Read Attribute Response");
        assertThat(decodedJsonObject.getAsJsonPrimitive("messageType").getAsString()).isEqualTo("Serial Master/Slave Protocol");
        assertThat(decodedJsonObject.getAsJsonPrimitive("attributId").getAsString()).isEqualTo("Answer");
        assertThat(decodedJsonObject.getAsJsonPrimitive("additionalAddress").getAsString()).isEqualTo("01");
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusCommand").getAsString()).isEqualTo("Read Holding Registers");
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusfregister00").getAsFloat()).isEqualTo(0f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusfregister01").getAsFloat()).isEqualTo(0f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusfregister02").getAsFloat()).isEqualTo(999999.0f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusfregister03").getAsFloat()).isEqualTo(999999.0f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusfregister04").getAsFloat()).isEqualTo(238.000000f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusfregister05").getAsFloat()).isEqualTo(233.000000f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusfregister06").getAsFloat()).isEqualTo(52.000000f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusfregister07").getAsFloat()).isEqualTo(0f);

    }


    @Test
    @PayloadDescription(name = "ClusterMasterSlaveProtocol_Answer", description = "Cluster: Master/Slave Protocol, Attribut: Answer, Command: Report attributes. Modbus tags associated to the device: \n"
            + "MODBUS_8007_EP0_1_modbusfregister00_NOUNIT_FL2L,\n"
            + "MODBUS_8007_EP0_2_modbusfregister01_NOUNIT_FL2L,\n"
            + "MODBUS_8007_EP0_3_modbusfregister02_NOUNIT_FL2L,\n"
            + "MODBUS_8007_EP0_4_modbusfregister03_NOUNIT_FL2L,\n"
            + "MODBUS_8007_EP0_5_modbusfregister04_NOUNIT_FL2L,\n"
            + "MODBUS_8007_EP0_6_modbusfregister05_NOUNIT_FL2L,\n"
            + "MODBUS_8007_EP0_7_modbusfregister06_NOUNIT_FL2L,\n"
            + "MODBUS_8007_EP0_8_modbusfregister07_NOUNIT_FL2L\n")
    public void should_decode_8007_MasterSlaveProtocol_attribut0001_tags_float2le_payload() throws IOException, DecodingException, JsDecoderBuildingException, JsDecodingException {
        String input = "110a800700014123010320469a468bcccd423ef7dc4a217ae13e943333429a7ae1428f0000000000000000";
        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"MODBUS_8007_EP0_1_modbusfregister00_NOUNIT_FL2L\","
                + "\"MODBUS_8007_EP0_2_modbusfregister01_NOUNIT_FL2L\","
                + "\"MODBUS_8007_EP0_3_modbusfregister02_NOUNIT_FL2L\","
                + "\"MODBUS_8007_EP0_4_modbusfregister03_NOUNIT_FL2L\","
                + "\"MODBUS_8007_EP0_5_modbusfregister04_NOUNIT_FL2L\","
                + "\"MODBUS_8007_EP0_6_modbusfregister05_NOUNIT_FL2L\","
                + "\"MODBUS_8007_EP0_7_modbusfregister06_NOUNIT_FL2L\","
                + "\"MODBUS_8007_EP0_8_modbusfregister07_NOUNIT_FL2L\","
                + "] }";

        NKEClusterSerialMasterSlaveProtocol masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterSerialMasterSlaveProtocol.class);

        String script = loadJavascriptFile(SCRIPT_PATH);
        SimpleDecoder decoder = JsDecoderFactory.buildDefaultDecoder(script);
        Gson gwm = new Gson();
        Object owm = gwm.fromJson(inputDataMessage, Object.class);
        JsonObject decodedJsonObject = decoder.toJsonObject(input, owm);

        assertThat(decodedJsonObject.getAsJsonPrimitive("endpoint").getAsString()).isEqualTo("0");
        assertThat(decodedJsonObject.getAsJsonPrimitive("commandId").getAsString()).isEqualTo("Report Attributes");
        assertThat(decodedJsonObject.getAsJsonPrimitive("messageType").getAsString()).isEqualTo("Serial Master/Slave Protocol");
        assertThat(decodedJsonObject.getAsJsonPrimitive("attributId").getAsString()).isEqualTo("Answer");
        assertThat(decodedJsonObject.getAsJsonPrimitive("additionalAddress").getAsString()).isEqualTo("01");
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusCommand").getAsString()).isEqualTo("Read Holding Registers");
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusfregister00").getAsFloat()).isEqualTo(17827.300781f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusfregister01").getAsFloat()).isEqualTo(47.700001f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusfregister02").getAsFloat()).isEqualTo(2653687.000000f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusfregister03").getAsFloat()).isEqualTo(0.29f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusfregister04").getAsFloat()).isEqualTo(77.099998f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusfregister05").getAsFloat()).isEqualTo(71.739998f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusfregister06").getAsFloat()).isEqualTo(0f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusfregister07").getAsFloat()).isEqualTo(0f);

    }

    @Test
    @PayloadDescription(name = "ClusterMasterSlaveProtocol_Answer", description = "Cluster: Master/Slave Protocol, Attribut: Answer, Command: Report attributes. Modbus tags associated to the device: \n"
            + "MODBUS_8007_EP0_1_counter_NOUNIT_U32,\n"
            + "MODBUS_8007_EP0_2_battery_millivolts_S32\n")
    public void should_decode_8007_MasterSlaveProtocol_attribut0001_tags_s32_u32() throws IOException, DecodingException, JsDecoderBuildingException, JsDecodingException {
        String input = "110a80070001410B010308FFFFFFFEFFFFFFFE";
        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"MODBUS_8007_EP0_1_counter_NOUNIT_U32\","
                + "\"MODBUS_8007_EP0_2_battery_millivolts_S32\""
                + "] }";
        NKEClusterSerialMasterSlaveProtocol masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterSerialMasterSlaveProtocol.class);

        String script = loadJavascriptFile(SCRIPT_PATH);
        SimpleDecoder decoder = JsDecoderFactory.buildDefaultDecoder(script);
        Gson gwm = new Gson();
        Object owm = gwm.fromJson(inputDataMessage, Object.class);
        JsonObject decodedJsonObject = decoder.toJsonObject(input, owm);

        assertThat(decodedJsonObject.getAsJsonPrimitive("endpoint").getAsString()).isEqualTo("0");
        assertThat(decodedJsonObject.getAsJsonPrimitive("commandId").getAsString()).isEqualTo("Report Attributes");
        assertThat(decodedJsonObject.getAsJsonPrimitive("messageType").getAsString()).isEqualTo("Serial Master/Slave Protocol");
        assertThat(decodedJsonObject.getAsJsonPrimitive("attributId").getAsString()).isEqualTo("Answer");
        assertThat(decodedJsonObject.getAsJsonPrimitive("additionalAddress").getAsString()).isEqualTo("01");
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusCommand").getAsString()).isEqualTo("Read Holding Registers");
        assertThat(decodedJsonObject.getAsJsonPrimitive("counter").getAsFloat()).isEqualTo(4294967294f);
        assertThat(decodedJsonObject.getAsJsonObject("battery").getAsJsonPrimitive("unit").getAsString()).isEqualTo("millivolts");
        assertThat(decodedJsonObject.getAsJsonObject("battery").getAsJsonPrimitive("value").getAsInt()).isEqualTo(-2);

    }

    @Test
    @PayloadDescription(name = "ClusterMasterSlaveProtocol_Answer", description = "Cluster: Master/Slave Protocol, Attribut: Answer, Command: Report attributes. Modbus tags associated to the device: \n"
            + "MODBUS_8007_EP0_1_counter_NOUNIT_U32L,\n"
            + "MODBUS_8007_EP0_2_battery_millivolts_S32L\n")
    public void should_decode_8007_MasterSlaveProtocol_attribut0001_tags_s32L_u32L() throws IOException, DecodingException, JsDecoderBuildingException, JsDecodingException {
        String input = "110a80070001410B010308FFFFFFFEFFFFFFFE";
        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"MODBUS_8007_EP0_1_counter_NOUNIT_U32L\","
                + "\"MODBUS_8007_EP0_2_battery_millivolts_S32L\""
                + "] }";
        NKEClusterSerialMasterSlaveProtocol masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterSerialMasterSlaveProtocol.class);

        String script = loadJavascriptFile(SCRIPT_PATH);
        SimpleDecoder decoder = JsDecoderFactory.buildDefaultDecoder(script);
        Gson gwm = new Gson();
        Object owm = gwm.fromJson(inputDataMessage, Object.class);
        JsonObject decodedJsonObject = decoder.toJsonObject(input, owm);

        assertThat(decodedJsonObject.getAsJsonPrimitive("endpoint").getAsString()).isEqualTo("0");
        assertThat(decodedJsonObject.getAsJsonPrimitive("commandId").getAsString()).isEqualTo("Report Attributes");
        assertThat(decodedJsonObject.getAsJsonPrimitive("messageType").getAsString()).isEqualTo("Serial Master/Slave Protocol");
        assertThat(decodedJsonObject.getAsJsonPrimitive("attributId").getAsString()).isEqualTo("Answer");
        assertThat(decodedJsonObject.getAsJsonPrimitive("additionalAddress").getAsString()).isEqualTo("01");
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusCommand").getAsString()).isEqualTo("Read Holding Registers");
        assertThat(decodedJsonObject.getAsJsonPrimitive("counter").getAsFloat()).isEqualTo(4294901759f);
        assertThat(decodedJsonObject.getAsJsonObject("battery").getAsJsonPrimitive("unit").getAsString()).isEqualTo("millivolts");
        assertThat(decodedJsonObject.getAsJsonObject("battery").getAsJsonPrimitive("value").getAsInt()).isEqualTo(-65537);

    }


    @Test
    @PayloadDescription(name = "ClusterMasterSlaveProtocol_Answer", description = "Cluster: Master/Slave Protocol, Attribut: Answer, Command: Report attributes. Modbus tags associated to the device: \n"
            + "MODBUS_8007_EP0_1_modbusfregister00_NOUNIT_S32,\n"
            + "MODBUS_8007_EP0_2_modbusfregister01_NOUNIT_U32,\n"
            + "MODBUS_8007_EP0_3_modbusfregister02_NOUNIT_S32,\n"
            + "MODBUS_8007_EP0_4_modbusfregister03_NOUNIT_U32,\n"
            + "MODBUS_8007_EP0_5_modbusfregister04_NOUNIT_S32L,\n"
            + "MODBUS_8007_EP0_6_modbusfregister05_NOUNIT_U32L,\n"
            + "MODBUS_8007_EP0_7_modbusfregister06_NOUNIT_S32L,\n"
            + "MODBUS_8007_EP0_8_modbusfregister07_NOUNIT_U32L\n")
    public void should_decode_8007_MasterSlaveProtocol_attribut0001_tags_s32_u32_s32L_u32L() throws IOException, DecodingException, JsDecoderBuildingException, JsDecodingException {
        String input = "110a800700014123010320469a468bcccd423ef7dc4a217ae13e943333429a7ae1428f0000000000000000";
        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"MODBUS_8007_EP0_1_modbusfregister00_NOUNIT_S32\","
                + "\"MODBUS_8007_EP0_2_modbusfregister01_NOUNIT_U32\","
                + "\"MODBUS_8007_EP0_3_modbusfregister02_NOUNIT_S32\","
                + "\"MODBUS_8007_EP0_4_modbusfregister03_NOUNIT_U32\","
                + "\"MODBUS_8007_EP0_5_modbusfregister04_NOUNIT_S32L\","
                + "\"MODBUS_8007_EP0_6_modbusfregister05_NOUNIT_U32L\","
                + "\"MODBUS_8007_EP0_7_modbusfregister06_NOUNIT_S32L\","
                + "\"MODBUS_8007_EP0_8_modbusfregister07_NOUNIT_U32L\","
                + "] }";
        NKEClusterSerialMasterSlaveProtocol masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterSerialMasterSlaveProtocol.class);
        String script = loadJavascriptFile(SCRIPT_PATH);
        SimpleDecoder decoder = JsDecoderFactory.buildDefaultDecoder(script);
        Gson gwm = new Gson();
        Object owm = gwm.fromJson(inputDataMessage, Object.class);
        JsonObject decodedJsonObject = decoder.toJsonObject(input, owm);

        assertThat(decodedJsonObject.getAsJsonPrimitive("endpoint").getAsString()).isEqualTo("0");
        assertThat(decodedJsonObject.getAsJsonPrimitive("commandId").getAsString()).isEqualTo("Report Attributes");
        assertThat(decodedJsonObject.getAsJsonPrimitive("messageType").getAsString()).isEqualTo("Serial Master/Slave Protocol");
        assertThat(decodedJsonObject.getAsJsonPrimitive("attributId").getAsString()).isEqualTo("Answer");
        assertThat(decodedJsonObject.getAsJsonPrimitive("additionalAddress").getAsString()).isEqualTo("01");
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusCommand").getAsString()).isEqualTo("Read Holding Registers");
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusfregister00").getAsFloat()).isEqualTo(1184515723f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusfregister01").getAsFloat()).isEqualTo(3436003902f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusfregister02").getAsFloat()).isEqualTo(-136558047f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusfregister03").getAsFloat()).isEqualTo(2061581972f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusfregister04").getAsFloat()).isEqualTo(1117401907f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusfregister05").getAsFloat()).isEqualTo(1116699361f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusfregister06").getAsFloat()).isEqualTo(0f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusfregister07").getAsFloat()).isEqualTo(0f);

    }


    @Test
    @PayloadDescription(name = "ClusterMasterSlaveProtocol_Answer", description = "Cluster: Master/Slave Protocol, Attribut: Answer, Command: Report attributes. Modbus tags associated to the device: \n"
            + "MODBUS_8007_EP0_1_humidity_%_U8,\n"
            + "MODBUS_8007_EP0_2_outdoorTemperature_°C_S8,\n"
            + "MODBUS_8007_EP0_3_pressure_Pa_U16,\n"
            + "MODBUS_8007_EP0_4_counter_NOUNIT_S16\n")
    public void should_decode_8007_MasterSlaveProtocol_attribut0001_tags_u8u16_s8s16_payload() throws IOException, DecodingException, JsDecoderBuildingException, JsDecodingException {
        String input = "110a800700014109010306FEFEFFFEFFFE";
        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"MODBUS_8007_EP0_1_humidity_%_U8\","
                + "\"MODBUS_8007_EP0_2_outdoorTemperature_°C_S8\","
                + "\"MODBUS_8007_EP0_3_pressure_Pa_U16\","
                + "\"MODBUS_8007_EP0_4_counter_NOUNIT_S16\""
                + "] }";
        NKEClusterSerialMasterSlaveProtocol masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterSerialMasterSlaveProtocol.class);

        String script = loadJavascriptFile(SCRIPT_PATH);
        SimpleDecoder decoder = JsDecoderFactory.buildDefaultDecoder(script);
        Gson gwm = new Gson();
        Object owm = gwm.fromJson(inputDataMessage, Object.class);
        JsonObject decodedJsonObject = decoder.toJsonObject(input, owm);

        assertThat(decodedJsonObject.getAsJsonPrimitive("endpoint").getAsString()).isEqualTo("0");
        assertThat(decodedJsonObject.getAsJsonPrimitive("commandId").getAsString()).isEqualTo("Report Attributes");
        assertThat(decodedJsonObject.getAsJsonPrimitive("messageType").getAsString()).isEqualTo("Serial Master/Slave Protocol");
        assertThat(decodedJsonObject.getAsJsonPrimitive("attributId").getAsString()).isEqualTo("Answer");
        assertThat(decodedJsonObject.getAsJsonPrimitive("additionalAddress").getAsString()).isEqualTo("01");
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusCommand").getAsString()).isEqualTo("Read Holding Registers");
        assertThat(decodedJsonObject.getAsJsonObject("humidity").getAsJsonPrimitive("unit").getAsString()).isEqualTo("%");
        assertThat(decodedJsonObject.getAsJsonObject("humidity").getAsJsonPrimitive("value").getAsInt()).isEqualTo(254);
        assertThat(decodedJsonObject.getAsJsonObject("outdoorTemperature").getAsJsonPrimitive("unit").getAsString()).isEqualTo("°C");
        assertThat(decodedJsonObject.getAsJsonObject("outdoorTemperature").getAsJsonPrimitive("value").getAsInt()).isEqualTo(-2);
        assertThat(decodedJsonObject.getAsJsonObject("pressure").getAsJsonPrimitive("unit").getAsString()).isEqualTo("Pa");
        assertThat(decodedJsonObject.getAsJsonObject("pressure").getAsJsonPrimitive("value").getAsInt()).isEqualTo(65534);
        assertThat(decodedJsonObject.getAsJsonPrimitive("counter").getAsInt()).isEqualTo(-2);

    }

    @Test
    @PayloadDescription(name = "ClusterMasterSlaveProtocol_Answer", description = "Cluster: Master/Slave Protocol, Attribut: Answer, Command: Report attributes. Modbus tag associated to the device: \n"
            + "MODBUS_8007_EP0_1_product_NOUNIT_A6\n")
    public void should_decode_8007_MasterSlaveProtocol_attribut0001_tags_ascii_payload() throws IOException, DecodingException, JsDecoderBuildingException, JsDecodingException {
        String input = "110a800700014109010306414243444546";
        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"MODBUS_8007_EP0_1_product_NOUNIT_A6\""
                + "] }";
        NKEClusterSerialMasterSlaveProtocol masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterSerialMasterSlaveProtocol.class);
        String script = loadJavascriptFile(SCRIPT_PATH);
        SimpleDecoder decoder = JsDecoderFactory.buildDefaultDecoder(script);
        Gson gwm = new Gson();
        Object owm = gwm.fromJson(inputDataMessage, Object.class);
        JsonObject decodedJsonObject = decoder.toJsonObject(input, owm);

        assertThat(decodedJsonObject.getAsJsonPrimitive("endpoint").getAsString()).isEqualTo("0");
        assertThat(decodedJsonObject.getAsJsonPrimitive("commandId").getAsString()).isEqualTo("Report Attributes");
        assertThat(decodedJsonObject.getAsJsonPrimitive("messageType").getAsString()).isEqualTo("Serial Master/Slave Protocol");
        assertThat(decodedJsonObject.getAsJsonPrimitive("attributId").getAsString()).isEqualTo("Answer");
        assertThat(decodedJsonObject.getAsJsonPrimitive("additionalAddress").getAsString()).isEqualTo("01");
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusCommand").getAsString()).isEqualTo("Read Holding Registers");
        assertThat(decodedJsonObject.getAsJsonPrimitive("product").getAsString()).isEqualTo("ABCDEF");
    }

    @Test
    @PayloadDescription(name = "ClusterMasterSlaveProtocol_Answer", description = "Cluster: Master/Slave Protocol, Attribut: Answer, Command: Report attributes. Modbus tag associated to the device: \n"
            + "MODBUS_8007_EP0_1_product_NOUNIT_H6\n")
    public void should_decode_8007_MasterSlaveProtocol_attribut0001_tags_hexa_payload() throws IOException, DecodingException, JsDecoderBuildingException, JsDecodingException {
        String input = "110a800700014109010306414243444546";
        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"MODBUS_8007_EP0_1_product_NOUNIT_H6\""
                + "] }";
        NKEClusterSerialMasterSlaveProtocol masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterSerialMasterSlaveProtocol.class);
        String script = loadJavascriptFile(SCRIPT_PATH);
        SimpleDecoder decoder = JsDecoderFactory.buildDefaultDecoder(script);
        Gson gwm = new Gson();
        Object owm = gwm.fromJson(inputDataMessage, Object.class);
        JsonObject decodedJsonObject = decoder.toJsonObject(input, owm);

        assertThat(decodedJsonObject.getAsJsonPrimitive("endpoint").getAsString()).isEqualTo("0");
        assertThat(decodedJsonObject.getAsJsonPrimitive("commandId").getAsString()).isEqualTo("Report Attributes");
        assertThat(decodedJsonObject.getAsJsonPrimitive("messageType").getAsString()).isEqualTo("Serial Master/Slave Protocol");
        assertThat(decodedJsonObject.getAsJsonPrimitive("attributId").getAsString()).isEqualTo("Answer");
        assertThat(decodedJsonObject.getAsJsonPrimitive("additionalAddress").getAsString()).isEqualTo("01");
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusCommand").getAsString()).isEqualTo("Read Holding Registers");
        assertThat(decodedJsonObject.getAsJsonPrimitive("product").getAsString()).isEqualTo("414243444546");
    }


    @Test
    @PayloadDescription(name = "ClusterMasterSlaveProtocol_Answer", description = "Cluster: Master/Slave Protocol, Attribut: Answer, Command: Report attributes. Modbus tags associated to the device: \n"
            + "MODBUS_8007_EP0_1_counter_NOUNIT_U32,\n"
            + "MODBUS_8007_EP0_2_ok_NOUNIT_B1,\n"
            + "MODBUS_8007_EP0_3_status_NOUNIT_B3,\n"
            + "MODBUS_8007_EP0_4_padding_NOUNIT_R4,\n"
            + "MODBUS_8007_EP0_5_humidity_%_U8,\n"
            + "MODBUS_8007_EP0_6_temperature_°C_S16,\n"
            + "MODBUS_8007_EP0_7_modbusfregister06_NOUNIT_S32L \n")
    public void should_decode_8007_MasterSlaveProtocol_attribut0001_tags_B_R_payload() throws IOException, DecodingException, JsDecoderBuildingException, JsDecodingException {
        String input = "110a80070001410B010308FFFFFFFEFFFFFFFE";
        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"MODBUS_8007_EP0_1_counter_NOUNIT_U32\","
                + "\"MODBUS_8007_EP0_2_ok_NOUNIT_B1\","
                + "\"MODBUS_8007_EP0_3_status_NOUNIT_B3\","
                + "\"MODBUS_8007_EP0_4_padding_NOUNIT_R4\","
                + "\"MODBUS_8007_EP0_5_humidity_%_U8\","
                + "\"MODBUS_8007_EP0_6_temperature_°C_S16\""
                + "] }";
        NKEClusterSerialMasterSlaveProtocol masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterSerialMasterSlaveProtocol.class);

        String script = loadJavascriptFile(SCRIPT_PATH);
        SimpleDecoder decoder = JsDecoderFactory.buildDefaultDecoder(script);
        Gson gwm = new Gson();
        Object owm = gwm.fromJson(inputDataMessage, Object.class);
        JsonObject decodedJsonObject = decoder.toJsonObject(input, owm);

        assertThat(decodedJsonObject.getAsJsonPrimitive("endpoint").getAsString()).isEqualTo("0");
        assertThat(decodedJsonObject.getAsJsonPrimitive("commandId").getAsString()).isEqualTo("Report Attributes");
        assertThat(decodedJsonObject.getAsJsonPrimitive("messageType").getAsString()).isEqualTo("Serial Master/Slave Protocol");
        assertThat(decodedJsonObject.getAsJsonPrimitive("attributId").getAsString()).isEqualTo("Answer");
        assertThat(decodedJsonObject.getAsJsonPrimitive("additionalAddress").getAsString()).isEqualTo("01");
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusCommand").getAsString()).isEqualTo("Read Holding Registers");
        assertThat(decodedJsonObject.getAsJsonPrimitive("counter").getAsFloat()).isEqualTo(4294967294f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("ok").getAsString()).isEqualTo("1");
        assertThat(decodedJsonObject.getAsJsonPrimitive("status").getAsString()).isEqualTo("7");
        assertThat(decodedJsonObject.getAsJsonObject("humidity").getAsJsonPrimitive("unit").getAsString()).isEqualTo("%");
        assertThat(decodedJsonObject.getAsJsonObject("humidity").getAsJsonPrimitive("value").getAsInt()).isEqualTo(255);
        assertThat(decodedJsonObject.getAsJsonObject("temperature").getAsJsonPrimitive("unit").getAsString()).isEqualTo("°C");
        assertThat(decodedJsonObject.getAsJsonObject("temperature").getAsJsonPrimitive("value").getAsInt()).isEqualTo(-2);

    }

    /********** tests erreurs tags ***************/

    @Test
    public void should_decode_8007_MasterSlaveProtocol_attribut0001_tags_missing_tag__payload() throws IOException, DecodingException, JsDecoderBuildingException, JsDecodingException {
        String input = "110a80070001410B010308FFFFFFFEFFFFFFFE";
        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"MODBUS_8007_EP0_1_counter_NOUNIT_U32\","
                + "\"MODBUS_8007_EP0_2_ok_NOUNIT_B1\","
                + "\"MODBUS_8007_EP0_4_padding_NOUNIT_R4\","
                + "\"MODBUS_8007_EP0_5_humidity_%_U8\","
                + "\"MODBUS_8007_EP0_6_temperature_°C_S16\""
                + "] }";
        NKEClusterSerialMasterSlaveProtocol masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterSerialMasterSlaveProtocol.class);

        String script = loadJavascriptFile(SCRIPT_PATH);
        SimpleDecoder decoder = JsDecoderFactory.buildDefaultDecoder(script);
        Gson gwm = new Gson();
        Object owm = gwm.fromJson(inputDataMessage, Object.class);
        JsonObject decodedJsonObject = decoder.toJsonObject(input, owm);

        assertThat(decodedJsonObject.getAsJsonPrimitive("error").getAsString()).isEqualTo("decoding failed : modbus tag missing");

    }


    @Test
    public void should_decode_8007_MasterSlaveProtocol_attribut0001_tags_wrong_type_payload() throws IOException, DecodingException, JsDecoderBuildingException, JsDecodingException {
        String input = "110a80070001410B010308FFFFFFFEFFFFFFFE";
        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"MODBUS_8007_EP0_1_counter_NOUNIT_U32\","
                + "\"MODBUS_8007_EP0_2_ok_NOUNIT_B1\","
                + "\"MODBUS_8007_EP0_3_status_NOUNIT_B3\","
                + "\"MODBUS_8007_EP0_4_padding_NOUNIT_R4\","
                + "\"MODBUS_8007_EP0_5_humidity_%_U24\","
                + "\"MODBUS_8007_EP0_6_temperature_°C_S16\""
                + "] }";
        NKEClusterSerialMasterSlaveProtocol masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterSerialMasterSlaveProtocol.class);

        String script = loadJavascriptFile(SCRIPT_PATH);
        SimpleDecoder decoder = JsDecoderFactory.buildDefaultDecoder(script);
        Gson gwm = new Gson();
        Object owm = gwm.fromJson(inputDataMessage, Object.class);
        JsonObject decodedJsonObject = decoder.toJsonObject(input, owm);

        assertThat(decodedJsonObject.getAsJsonPrimitive("error").getAsString()).isEqualTo("decoding failed : modbus error type in tag");

    }

    @Test
    public void should_decode_8007_MasterSlaveProtocol_attribut0001_tags_wrong_ep_payload() throws IOException, DecodingException, JsDecoderBuildingException, JsDecodingException {
        String input = "110a80070001410B010308FFFFFFFEFFFFFFFE";
        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"MODBUS_8007_EP2_1_counter_NOUNIT_U32\","
                + "\"MODBUS_8007_EP2_2_ok_NOUNIT_B1\","
                + "\"MODBUS_8007_EP2_3_status_NOUNIT_B3\","
                + "\"MODBUS_8007_EP2_4_padding_NOUNIT_R4\","
                + "\"MODBUS_8007_EP2_5_humidity_%_U8\","
                + "\"MODBUS_8007_EP2_6_temperature_°C_S16\""
                + "] }";
        NKEClusterSerialMasterSlaveProtocol masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterSerialMasterSlaveProtocol.class);

        String script = loadJavascriptFile(SCRIPT_PATH);
        SimpleDecoder decoder = JsDecoderFactory.buildDefaultDecoder(script);
        Gson gwm = new Gson();
        Object owm = gwm.fromJson(inputDataMessage, Object.class);
        JsonObject decodedJsonObject = decoder.toJsonObject(input, owm);

        assertThat(decodedJsonObject.getAsJsonPrimitive("error").getAsString()).isEqualTo("decoding failed : Not defined tags for endpoint 0");


    }


    @PayloadDescription(name = "ClusterMasterSlaveProtocol_Answer_MODBUS_8007_PROFILE_EP0_10_REGS_NOUNIT_FL", description = "Cluster 8007 Attribute Answer standard payload containing modbus data sent by a device associated to the tag MODBUS_8007_PROFILE_EP0_10_REGS_NOUNIT_FL. "
            + "\nThe MODBUS_8007_PROFILE_EP0_10_REGS_NOUNIT_FL tag is replaced by the decoder by these following modbus tags:\n"
            + "MODBUS_8007_EP0_1_fregister00_NOUNIT_FL, \n"
            + "MODBUS_8007_EP0_2_fregister01_NOUNIT_FL, \n"
            + "MODBUS_8007_EP0_3_fregister02_NOUNIT_FL, \n"
            + "MODBUS_8007_EP0_4_fregister03_NOUNIT_FL, \n"
            + "MODBUS_8007_EP0_5_fregister04_NOUNIT_FL, \n"
            + "MODBUS_8007_EP0_6_fregister05_NOUNIT_FL, \n"
            + "MODBUS_8007_EP0_7_fregister06_NOUNIT_FL, \n"
            + "MODBUS_8007_EP0_8_fregister07_NOUNIT_FL, \n"
            + "MODBUS_8007_EP0_9_fregister08_NOUNIT_FL, \n"
            + "MODBUS_8007_EP0_10_fregister09_NOUNIT_FL")
    @Test
    public void should_decode_8007_MasterSlaveProtocol_attribut0001_tag_PROFILE_EP0_10_REGS_NOUNIT_FL() throws IOException, DecodingException, JsDecoderBuildingException, JsDecodingException {

        String input = "110a80070001412301032041AC0000419DC28F418AB8524187D70A4180147B41668F5C4123AE14411C51EC";

        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"MODBUS_8007_PROFILE_EP0_10_REGS_NOUNIT_FL\""
                + "] }";

        NKEClusterSerialMasterSlaveProtocol masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterSerialMasterSlaveProtocol.class);

        String script = loadJavascriptFile(SCRIPT_PATH);
        SimpleDecoder decoder = JsDecoderFactory.buildDefaultDecoder(script);
        Gson gwm = new Gson();
        Object owm = gwm.fromJson(inputDataMessage, Object.class);
        JsonObject decodedJsonObject = decoder.toJsonObject(input, owm);

        assertThat(decodedJsonObject.getAsJsonPrimitive("endpoint").getAsString()).isEqualTo("0");
        assertThat(decodedJsonObject.getAsJsonPrimitive("commandId").getAsString()).isEqualTo("Report Attributes");
        assertThat(decodedJsonObject.getAsJsonPrimitive("messageType").getAsString()).isEqualTo("Serial Master/Slave Protocol");
        assertThat(decodedJsonObject.getAsJsonPrimitive("attributId").getAsString()).isEqualTo("Answer");
        assertThat(decodedJsonObject.getAsJsonPrimitive("additionalAddress").getAsString()).isEqualTo("01");
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusCommand").getAsString()).isEqualTo("Read Holding Registers");
        assertThat(decodedJsonObject.getAsJsonPrimitive("fregister00").getAsFloat()).isEqualTo(21.5f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("fregister01").getAsFloat()).isEqualTo(19.719999f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("fregister02").getAsFloat()).isEqualTo(17.34f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("fregister03").getAsFloat()).isEqualTo(16.98f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("fregister04").getAsFloat()).isEqualTo(16.01f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("fregister05").getAsFloat()).isEqualTo(14.41f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("fregister06").getAsFloat()).isEqualTo(10.23f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("fregister07").getAsFloat()).isEqualTo(9.77f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("fregister08").getAsFloat()).isEqualTo(0f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("fregister09").getAsFloat()).isEqualTo(0f);

    }

    @PayloadDescription(name = "ClusterMasterSlaveProtocol_Answer_MODBUS_8007_PROFILE_EP0_10_REGS_NOUNIT_FL2L", description = "Cluster 8007 Attribute Answer standard payload containing modbus data sent by a device associated to the tag MODBUS_8007_PROFILE_EP0_10_REGS_NOUNIT_FL2L. "
            + "\nThe MODBUS_8007_PROFILE_EP0_10_REGS_NOUNIT_FL2L tag is replaced by the decoder by these following modbus tags:\n"
            + "MODBUS_8007_EP0_1_fregister00_NOUNIT_FL2L, \n"
            + "MODBUS_8007_EP0_2_fregister01_NOUNIT_FL2L, \n"
            + "MODBUS_8007_EP0_3_fregister02_NOUNIT_FL2L, \n"
            + "MODBUS_8007_EP0_4_fregister03_NOUNIT_FL2L, \n"
            + "MODBUS_8007_EP0_5_fregister04_NOUNIT_FL2L, \n"
            + "MODBUS_8007_EP0_6_fregister05_NOUNIT_FL2L, \n"
            + "MODBUS_8007_EP0_7_fregister06_NOUNIT_FL2L, \n"
            + "MODBUS_8007_EP0_8_fregister07_NOUNIT_FL2L, \n"
            + "MODBUS_8007_EP0_9_fregister08_NOUNIT_FL2L, \n"
            + "MODBUS_8007_EP0_10_fregister09_NOUNIT_FL2L")
    @Test
    public void should_decode_8007_MasterSlaveProtocol_attribut0001_tag_PROFILE_EP0_10_REGS_NOUNIT_FL2L() throws IOException, DecodingException, JsDecoderBuildingException, JsDecodingException {
        String input = "110a800700014123010320469a468bcccd423ef7dc4a217ae13e943333429a7ae1428f0000000000000000";
        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"MODBUS_8007_PROFILE_EP0_10_REGS_NOUNIT_FL2L\""
                + "] }";

        NKEClusterSerialMasterSlaveProtocol masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterSerialMasterSlaveProtocol.class);

        String script = loadJavascriptFile(SCRIPT_PATH);
        SimpleDecoder decoder = JsDecoderFactory.buildDefaultDecoder(script);
        Gson gwm = new Gson();
        Object owm = gwm.fromJson(inputDataMessage, Object.class);
        JsonObject decodedJsonObject = decoder.toJsonObject(input, owm);

        assertThat(decodedJsonObject.getAsJsonPrimitive("endpoint").getAsString()).isEqualTo("0");
        assertThat(decodedJsonObject.getAsJsonPrimitive("commandId").getAsString()).isEqualTo("Report Attributes");
        assertThat(decodedJsonObject.getAsJsonPrimitive("messageType").getAsString()).isEqualTo("Serial Master/Slave Protocol");
        assertThat(decodedJsonObject.getAsJsonPrimitive("attributId").getAsString()).isEqualTo("Answer");
        assertThat(decodedJsonObject.getAsJsonPrimitive("additionalAddress").getAsString()).isEqualTo("01");
        assertThat(decodedJsonObject.getAsJsonPrimitive("modbusCommand").getAsString()).isEqualTo("Read Holding Registers");
        assertThat(decodedJsonObject.getAsJsonPrimitive("fregister00").getAsFloat()).isEqualTo(17827.300781f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("fregister01").getAsFloat()).isEqualTo(47.700001f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("fregister02").getAsFloat()).isEqualTo(2653687.000000f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("fregister03").getAsFloat()).isEqualTo(0.29f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("fregister04").getAsFloat()).isEqualTo(77.099998f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("fregister05").getAsFloat()).isEqualTo(71.739998f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("fregister06").getAsFloat()).isEqualTo(0f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("fregister07").getAsFloat()).isEqualTo(0f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("fregister08").getAsFloat()).isEqualTo(0f);
        assertThat(decodedJsonObject.getAsJsonPrimitive("fregister09").getAsFloat()).isEqualTo(0f);
    }

    @Test
    @PayloadDescription(name = "ClusterMultiMasterSlaveAnswers_PresentValue", description = "Cluster: MultiMasterSlaveAnswers, Attribut: PresentValue, Command: Read Attribute Response. No modbus tag associated to the device.")
    public void should_decode_8009_MultiMasterSlaveAnswers_attribut0000_cmd01_payload() throws JsDecodingException {

        String input = "110180090000004101AE";
        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "] }";

        NKEClusterMultiMasterSlaveAnswers masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterMultiMasterSlaveAnswers.class);
        assertThat(masterSlaveProtocol.getMessageType()).isEqualTo("Multi Master/Slave answers");
        assertThat(masterSlaveProtocol.getEndpoint()).isEqualTo("0");
        assertThat(masterSlaveProtocol.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(masterSlaveProtocol.getAttributId()).isEqualTo("PresentValue");
        assertThat(masterSlaveProtocol.getStatus()).isEqualTo("Ok");
        assertThat(masterSlaveProtocol.getPresentValue()).isEqualTo("AE");

    }

    @Test
    @PayloadDescription(name = "ClusterMultiMasterSlaveAnswers_PresentValue", description = "Cluster: MultiMasterSlaveAnswers, Attribut: PresentValue, Command: Report Attributes. No modbus tag associated to the device.")
    public void should_decode_8009_MultiMasterSlaveAnswers_attribut0000_cmd0A_notag_payload() throws JsDecodingException {

        String input = "910A800900004101AE";
        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "] }";

        NKEClusterMultiMasterSlaveAnswers masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterMultiMasterSlaveAnswers.class);
        assertThat(masterSlaveProtocol.getMessageType()).isEqualTo("Multi Master/Slave answers");
        assertThat(masterSlaveProtocol.getEndpoint()).isEqualTo("4");
        assertThat(masterSlaveProtocol.getCommandId()).isEqualTo("Report Attributes");
        assertThat(masterSlaveProtocol.getAttributId()).isEqualTo("PresentValue");
        assertThat(masterSlaveProtocol.getPresentValue()).isEqualTo("AE");

    }


    @Test
    @PayloadDescription(name = "ClusterMultiMasterSlaveAnswers_HeaderOption", description = "Cluster: MultiMasterSlaveAnswers, Attribut: HeaderOption, Command: Read Attribute Response.")
    public void should_decode_8009_MultiMasterSlaveAnswers_attribut0001_cmd01_payload() throws JsDecodingException {

        String input = "110180090001001001";
        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "] }";

        NKEClusterMultiMasterSlaveAnswers masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterMultiMasterSlaveAnswers.class);
        assertThat(masterSlaveProtocol.getMessageType()).isEqualTo("Multi Master/Slave answers");
        assertThat(masterSlaveProtocol.getEndpoint()).isEqualTo("0");
        assertThat(masterSlaveProtocol.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(masterSlaveProtocol.getAttributId()).isEqualTo("HeaderOption");
        assertThat(masterSlaveProtocol.getStatus()).isEqualTo("Ok");
        assertThat(masterSlaveProtocol.getHeaderOption()).isEqualTo(true);
    }


    /********** tests tags modbus 8009 ***************/

    @Test
    @PayloadDescription(name = "ClusterMultiMasterSlaveAnswers_PresentValue", description = "Cluster: MultiMasterSlaveAnswers, Attribut: PresentValue, Command: Report Attributes. Modbus tags associated to the device: \n"
            + "MODBUS_8009_EP0_1_counter_NOUNIT_U16,\n"
            + "MODBUS_8009_EP1_1_counter_NOUNIT_U16,\n"
            + "MODBUS_8009_EP2_1_counter_NOUNIT_U16,\n"
            + "MODBUS_8009_EP3_1_counter_NOUNIT_U16\n")
    public void should_decode_8009_MultiMasterSlaveAnswers_attribut0000_tags_with_header_1_field_payload() throws IOException, DecodingException, JsDecoderBuildingException, JsDecodingException {

        String input = "110A80090000411706000F0103020001010302000201030200030103020004";

        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"MODBUS_8009_EP0_1_counter_NOUNIT_U16\","
                + "\"MODBUS_8009_EP1_1_counter_NOUNIT_U16\","
                + "\"MODBUS_8009_EP2_1_counter_NOUNIT_U16\","
                + "\"MODBUS_8009_EP3_1_counter_NOUNIT_U16\","
                + "] }";

        NKEClusterMultiMasterSlaveAnswers masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterMultiMasterSlaveAnswers.class);

        String script = loadJavascriptFile(SCRIPT_PATH);
        SimpleDecoder decoder = JsDecoderFactory.buildDefaultDecoder(script);
        Gson gwm = new Gson();
        Object owm = gwm.fromJson(inputDataMessage, Object.class);
        JsonObject decodedJsonObject = decoder.toJsonObject(input, owm);

        assertThat(decodedJsonObject.getAsJsonPrimitive("endpoint").getAsString()).isEqualTo("0");
        assertThat(decodedJsonObject.getAsJsonPrimitive("commandId").getAsString()).isEqualTo("Report Attributes");
        assertThat(decodedJsonObject.getAsJsonPrimitive("messageType").getAsString()).isEqualTo("Multi Master/Slave answers");
        assertThat(decodedJsonObject.getAsJsonPrimitive("attributId").getAsString()).isEqualTo("PresentValue");
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusFrameSeriesSent").getAsInt()).isEqualTo(6);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusFrameNumberInSerie").getAsInt()).isEqualTo(0);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusLastFrameOfSerie").getAsInt()).isEqualTo(0);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP0").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP1").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP2").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP3").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP4").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP5").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP6").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP7").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP8").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP9").getAsBoolean()).isEqualTo(false);

        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP0").getAsJsonPrimitive("counter").getAsInt()).isEqualTo(1);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP1").getAsJsonPrimitive("counter").getAsInt()).isEqualTo(2);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP2").getAsJsonPrimitive("counter").getAsInt()).isEqualTo(3);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP3").getAsJsonPrimitive("counter").getAsInt()).isEqualTo(4);

    }


    @Test
    @PayloadDescription(name = "ClusterMultiMasterSlaveAnswers_PresentValue", description = "Cluster: MultiMasterSlaveAnswers, Attribut: PresentValue, Command: Report Attributes. Modbus tags associated to the device: \n"
            + "MODBUS_8009_EP0_1_Index1_NOUNIT_U16,\n"
            + "MODBUS_8009_EP1_1_Index2_NOUNIT_U16,\n"
            + "MODBUS_8009_EP2_1_Index3_NOUNIT_U16,\n"
            + "MODBUS_8009_EP3_1_Index4_NOUNIT_U16,\n"
            + "MODBUS_8009_EP3_2_Index5_NOUNIT_U16\n")
    public void should_decode_8009_MultiMasterSlaveAnswers_attribut0000_tags_with_header_2_field_payload() throws IOException, DecodingException, JsDecoderBuildingException, JsDecodingException {

        String input = "110A80090000411706000F01030200010103020002010302000301030400040005";

        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"MODBUS_8009_EP0_1_Index1_NOUNIT_U16\","
                + "\"MODBUS_8009_EP1_1_Index2_NOUNIT_U16\","
                + "\"MODBUS_8009_EP2_1_Index3_NOUNIT_U16\","
                + "\"MODBUS_8009_EP3_1_Index4_NOUNIT_U16\","
                + "\"MODBUS_8009_EP3_2_Index5_NOUNIT_U16\","
                + "] }";

        NKEClusterMultiMasterSlaveAnswers masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterMultiMasterSlaveAnswers.class);

        String script = loadJavascriptFile(SCRIPT_PATH);
        SimpleDecoder decoder = JsDecoderFactory.buildDefaultDecoder(script);
        Gson gwm = new Gson();
        Object owm = gwm.fromJson(inputDataMessage, Object.class);
        JsonObject decodedJsonObject = decoder.toJsonObject(input, owm);

        assertThat(decodedJsonObject.getAsJsonPrimitive("endpoint").getAsString()).isEqualTo("0");
        assertThat(decodedJsonObject.getAsJsonPrimitive("commandId").getAsString()).isEqualTo("Report Attributes");
        assertThat(decodedJsonObject.getAsJsonPrimitive("messageType").getAsString()).isEqualTo("Multi Master/Slave answers");
        assertThat(decodedJsonObject.getAsJsonPrimitive("attributId").getAsString()).isEqualTo("PresentValue");
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusFrameSeriesSent").getAsInt()).isEqualTo(6);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusFrameNumberInSerie").getAsInt()).isEqualTo(0);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusLastFrameOfSerie").getAsInt()).isEqualTo(0);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP0").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP1").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP2").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP3").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP4").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP5").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP6").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP7").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP8").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP9").getAsBoolean()).isEqualTo(false);

        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP0").getAsJsonPrimitive("Index1").getAsInt()).isEqualTo(1);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP1").getAsJsonPrimitive("Index2").getAsInt()).isEqualTo(2);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP2").getAsJsonPrimitive("Index3").getAsInt()).isEqualTo(3);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP3").getAsJsonPrimitive("Index4").getAsInt()).isEqualTo(4);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP3").getAsJsonPrimitive("Index5").getAsInt()).isEqualTo(5);

    }

    @Test
    @PayloadDescription(name = "ClusterMultiMasterSlaveAnswers_PresentValue", description = "Cluster: MultiMasterSlaveAnswers, Attribut: PresentValue, Command: Report Attributes. Modbus tags associated to the device: \n"
            + "MODBUS_8009_EP0_1_energie_Wh_S32, \n"
            + "MODBUS_8009_EP1_1_volume_m3_U32, \n"
            + "MODBUS_8009_EP2_1_puissance_kw_S32L, \n"
            + "MODBUS_8009_EP2_2_débit_m3/h_U32L, \n"
            + "MODBUS_8009_EP3_1_tempdépart_°C_FL, \n"
            + "MODBUS_8009_EP3_2_tempretour_°C_FL2L, \n"
            + "MODBUS_8009_EP3_3_deltatemp_K_FL2L \n")
    public void should_decode_8009_MultiMasterSlaveAnswers_attribut0000_tags_with_header_types_32_payload() throws IOException, DecodingException, JsDecoderBuildingException, JsDecodingException {

        String input = "110a80090000411f06000f010304fffffffe01030400000004010308fffeffff0005000001030c41a5999a3333429a7ae1428f";

        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"MODBUS_8009_EP0_1_energie_Wh_S32\","
                + "\"MODBUS_8009_EP1_1_volume_m3_U32\","
                + "\"MODBUS_8009_EP2_1_puissance_kw_S32L\","
                + "\"MODBUS_8009_EP2_2_débit_m3/h_U32L\","
                + "\"MODBUS_8009_EP3_1_tempdépart_°C_FL\","
                + "\"MODBUS_8009_EP3_2_tempretour_°C_FL2L\","
                + "\"MODBUS_8009_EP3_3_deltatemp_K_FL2L\","
                + "] }";


        NKEClusterMultiMasterSlaveAnswers masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterMultiMasterSlaveAnswers.class);

        String script = loadJavascriptFile(SCRIPT_PATH);
        SimpleDecoder decoder = JsDecoderFactory.buildDefaultDecoder(script);
        Gson gwm = new Gson();
        Object owm = gwm.fromJson(inputDataMessage, Object.class);
        JsonObject decodedJsonObject = decoder.toJsonObject(input, owm);

        assertThat(decodedJsonObject.getAsJsonPrimitive("endpoint").getAsString()).isEqualTo("0");
        assertThat(decodedJsonObject.getAsJsonPrimitive("commandId").getAsString()).isEqualTo("Report Attributes");
        assertThat(decodedJsonObject.getAsJsonPrimitive("messageType").getAsString()).isEqualTo("Multi Master/Slave answers");
        assertThat(decodedJsonObject.getAsJsonPrimitive("attributId").getAsString()).isEqualTo("PresentValue");
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusFrameSeriesSent").getAsInt()).isEqualTo(6);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusFrameNumberInSerie").getAsInt()).isEqualTo(0);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusLastFrameOfSerie").getAsInt()).isEqualTo(0);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP0").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP1").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP2").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP3").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP4").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP5").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP6").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP7").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP8").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP9").getAsBoolean()).isEqualTo(false);

        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP0").getAsJsonObject("energie").getAsJsonPrimitive("unit").getAsString()).isEqualTo("Wh");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP0").getAsJsonObject("energie").getAsJsonPrimitive("value").getAsFloat()).isEqualTo(-2f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP1").getAsJsonObject("volume").getAsJsonPrimitive("unit").getAsString()).isEqualTo("m3");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP1").getAsJsonObject("volume").getAsJsonPrimitive("value").getAsFloat()).isEqualTo(4f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP2").getAsJsonObject("puissance").getAsJsonPrimitive("unit").getAsString()).isEqualTo("kw");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP2").getAsJsonObject("puissance").getAsJsonPrimitive("value").getAsFloat()).isEqualTo(-2f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP2").getAsJsonObject("débit").getAsJsonPrimitive("unit").getAsString()).isEqualTo("m3/h");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP2").getAsJsonObject("débit").getAsJsonPrimitive("value").getAsFloat()).isEqualTo(5f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP3").getAsJsonObject("tempdépart").getAsJsonPrimitive("unit").getAsString()).isEqualTo("°C");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP3").getAsJsonObject("tempdépart").getAsJsonPrimitive("value").getAsFloat()).isEqualTo(20.700001f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP3").getAsJsonObject("tempretour").getAsJsonPrimitive("unit").getAsString()).isEqualTo("°C");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP3").getAsJsonObject("tempretour").getAsJsonPrimitive("value").getAsFloat()).isEqualTo(77.099998f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP3").getAsJsonObject("deltatemp").getAsJsonPrimitive("unit").getAsString()).isEqualTo("K");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP3").getAsJsonObject("deltatemp").getAsJsonPrimitive("value").getAsFloat()).isEqualTo(71.739998f);

    }

    @Test
    @PayloadDescription(name = "ClusterMultiMasterSlaveAnswers_PresentValue", description = "Cluster: MultiMasterSlaveAnswers, Attribut: PresentValue, Command: Report Attributes. Modbus tags associated to the device: \n"
            + "MODBUS_8009_EP0_1_humidity_%_U8, \n"
            + "MODBUS_8009_EP1_1_outdoorTemperature_°C_S8, \n"
            + "MODBUS_8009_EP2_1_pressure_Pa_U16, \n"
            + "MODBUS_8009_EP3_1_counter_NOUNIT_S16 \n")
    public void should_decode_8009_MultiMasterSlaveAnswers_attribut0000_tags_with_header_types_u8u16_s8s16_payload() throws IOException, DecodingException, JsDecoderBuildingException, JsDecodingException {
        String input = "110a80090000411f06000f010301FE010301FE010302FFFE010302FFFE";
        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"MODBUS_8009_EP0_1_humidity_%_U8\","
                + "\"MODBUS_8009_EP1_1_outdoorTemperature_°C_S8\","
                + "\"MODBUS_8009_EP2_1_pressure_Pa_U16\","
                + "\"MODBUS_8009_EP3_1_counter_NOUNIT_S16\""
                + "] }";
        NKEClusterMultiMasterSlaveAnswers masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterMultiMasterSlaveAnswers.class);

        String script = loadJavascriptFile(SCRIPT_PATH);
        SimpleDecoder decoder = JsDecoderFactory.buildDefaultDecoder(script);
        Gson gwm = new Gson();
        Object owm = gwm.fromJson(inputDataMessage, Object.class);
        JsonObject decodedJsonObject = decoder.toJsonObject(input, owm);

        assertThat(decodedJsonObject.getAsJsonPrimitive("endpoint").getAsString()).isEqualTo("0");
        assertThat(decodedJsonObject.getAsJsonPrimitive("commandId").getAsString()).isEqualTo("Report Attributes");
        assertThat(decodedJsonObject.getAsJsonPrimitive("messageType").getAsString()).isEqualTo("Multi Master/Slave answers");
        assertThat(decodedJsonObject.getAsJsonPrimitive("attributId").getAsString()).isEqualTo("PresentValue");
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusFrameSeriesSent").getAsInt()).isEqualTo(6);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusFrameNumberInSerie").getAsInt()).isEqualTo(0);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusLastFrameOfSerie").getAsInt()).isEqualTo(0);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP0").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP1").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP2").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP3").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP4").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP5").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP6").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP7").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP8").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP9").getAsBoolean()).isEqualTo(false);

        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP0").getAsJsonObject("humidity").getAsJsonPrimitive("unit").getAsString()).isEqualTo("%");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP0").getAsJsonObject("humidity").getAsJsonPrimitive("value").getAsInt()).isEqualTo(254);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP1").getAsJsonObject("outdoorTemperature").getAsJsonPrimitive("unit").getAsString()).isEqualTo("°C");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP1").getAsJsonObject("outdoorTemperature").getAsJsonPrimitive("value").getAsInt()).isEqualTo(-2);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP2").getAsJsonObject("pressure").getAsJsonPrimitive("unit").getAsString()).isEqualTo("Pa");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP2").getAsJsonObject("pressure").getAsJsonPrimitive("value").getAsInt()).isEqualTo(65534);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP3").getAsJsonPrimitive("counter").getAsInt()).isEqualTo(-2);

    }

    @Test
    @PayloadDescription(name = "ClusterMultiMasterSlaveAnswers_PresentValue", description = "Cluster: MultiMasterSlaveAnswers, Attribut: PresentValue, Command: Report Attributes. Modbus tags associated to the device: \n"
            + "MODBUS_8009_EP0_1_product_NOUNIT_A6, \n"
            + "MODBUS_8009_EP1_1_reference_NOUNIT_H4\n")
    public void should_decode_8009_MultiMasterSlaveAnswers_attribut0000_tags_with_header_types_ascii_hexa_payload() throws IOException, DecodingException, JsDecoderBuildingException, JsDecodingException {

        String input = "110a80090000411f06000301030641424344454601030441424344";

        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"MODBUS_8009_EP0_1_product_NOUNIT_A6\","
                + "\"MODBUS_8009_EP1_1_reference_NOUNIT_H4\""
                + "] }";
        NKEClusterMultiMasterSlaveAnswers masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterMultiMasterSlaveAnswers.class);

        String script = loadJavascriptFile(SCRIPT_PATH);
        SimpleDecoder decoder = JsDecoderFactory.buildDefaultDecoder(script);
        Gson gwm = new Gson();
        Object owm = gwm.fromJson(inputDataMessage, Object.class);
        JsonObject decodedJsonObject = decoder.toJsonObject(input, owm);

        assertThat(decodedJsonObject.getAsJsonPrimitive("endpoint").getAsString()).isEqualTo("0");
        assertThat(decodedJsonObject.getAsJsonPrimitive("commandId").getAsString()).isEqualTo("Report Attributes");
        assertThat(decodedJsonObject.getAsJsonPrimitive("messageType").getAsString()).isEqualTo("Multi Master/Slave answers");
        assertThat(decodedJsonObject.getAsJsonPrimitive("attributId").getAsString()).isEqualTo("PresentValue");
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusFrameSeriesSent").getAsInt()).isEqualTo(6);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusFrameNumberInSerie").getAsInt()).isEqualTo(0);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusLastFrameOfSerie").getAsInt()).isEqualTo(0);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP0").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP1").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP2").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP3").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP4").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP5").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP6").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP7").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP8").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP9").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP0").getAsJsonPrimitive("product").getAsString()).isEqualTo("ABCDEF");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP1").getAsJsonPrimitive("reference").getAsString()).isEqualTo("41424344");
    }

    @Test
    @PayloadDescription(name = "ClusterMultiMasterSlaveAnswers_PresentValue", description = "Cluster: MultiMasterSlaveAnswers, Attribut: PresentValue, Command: Report Attributes. Modbus tags associated to the device: \n"
            + "MODBUS_8009_EP0_1_counter_NOUNIT_U32, \n"
            + "MODBUS_8009_EP1_1_ok_NOUNIT_B1, \n"
            + "MODBUS_8009_EP1_2_status_NOUNIT_B3, \n"
            + "MODBUS_8009_EP1_3_padding_NOUNIT_R4, \n"
            + "MODBUS_8009_EP2_1_humidity_%_U8, \n"
            + "MODBUS_8009_EP3_1_temperature_°C_S16 \n")
    public void should_decode_8009_MultiMasterSlaveAnswers_attribut0000_tags_with_header_types_B_R_payload() throws IOException, DecodingException, JsDecoderBuildingException, JsDecodingException {
        String input = "110a80090000411f06000f010304FFFFFFFE010301FF010301FF010302FFFE";
        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"MODBUS_8009_EP0_1_counter_NOUNIT_U32\","
                + "\"MODBUS_8009_EP1_1_ok_NOUNIT_B1\","
                + "\"MODBUS_8009_EP1_2_status_NOUNIT_B3\","
                + "\"MODBUS_8009_EP1_3_padding_NOUNIT_R4\","
                + "\"MODBUS_8009_EP2_1_humidity_%_U8\","
                + "\"MODBUS_8009_EP3_1_temperature_°C_S16\""
                + "] }";

        NKEClusterMultiMasterSlaveAnswers masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterMultiMasterSlaveAnswers.class);

        String script = loadJavascriptFile(SCRIPT_PATH);
        SimpleDecoder decoder = JsDecoderFactory.buildDefaultDecoder(script);
        Gson gwm = new Gson();
        Object owm = gwm.fromJson(inputDataMessage, Object.class);
        JsonObject decodedJsonObject = decoder.toJsonObject(input, owm);

        assertThat(decodedJsonObject.getAsJsonPrimitive("endpoint").getAsString()).isEqualTo("0");
        assertThat(decodedJsonObject.getAsJsonPrimitive("commandId").getAsString()).isEqualTo("Report Attributes");
        assertThat(decodedJsonObject.getAsJsonPrimitive("messageType").getAsString()).isEqualTo("Multi Master/Slave answers");
        assertThat(decodedJsonObject.getAsJsonPrimitive("attributId").getAsString()).isEqualTo("PresentValue");
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusFrameSeriesSent").getAsInt()).isEqualTo(6);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusFrameNumberInSerie").getAsInt()).isEqualTo(0);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusLastFrameOfSerie").getAsInt()).isEqualTo(0);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP0").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP1").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP2").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP3").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP4").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP5").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP6").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP7").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP8").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP9").getAsBoolean()).isEqualTo(false);

        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP0").getAsJsonPrimitive("counter").getAsFloat()).isEqualTo(4294967294f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP1").getAsJsonPrimitive("ok").getAsString()).isEqualTo("1");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP1").getAsJsonPrimitive("status").getAsString()).isEqualTo("7");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP2").getAsJsonObject("humidity").getAsJsonPrimitive("unit").getAsString()).isEqualTo("%");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP2").getAsJsonObject("humidity").getAsJsonPrimitive("value").getAsInt()).isEqualTo(255);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP3").getAsJsonObject("temperature").getAsJsonPrimitive("unit").getAsString()).isEqualTo("°C");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP3").getAsJsonObject("temperature").getAsJsonPrimitive("value").getAsInt()).isEqualTo(-2);

    }


    @Test
    @PayloadDescription(name = "ClusterMultiMasterSlaveAnswers_PresentValue", description = "Cluster: MultiMasterSlaveAnswers, Attribut: PresentValue, Command: Report Attributes. Modbus tags associated to the device: \n"
            + "MODBUS_8009_WITHOUT_HEADER, \n"
            + "MODBUS_8009_EP0_1_energie_Wh_FL, \n"
            + "MODBUS_8009_EP1_1_volume_m3_FL, \n"
            + "MODBUS_8009_EP2_1_puissance_kw_FL, \n"
            + "MODBUS_8009_EP3_1_débit_m3/h_FL, \n"
            + "MODBUS_8009_EP4_1_tempdépart_°C_FL, \n"
            + "MODBUS_8009_EP5_1_tempretour_°C_FL2L, \n"
            + "MODBUS_8009_EP6_1_deltatemp_K_FL2 \n")
    public void should_decode_8009_MultiMasterSlaveAnswers_attribut0000_tags_without_header_1_field_payload() throws IOException, DecodingException, JsDecoderBuildingException, JsDecodingException {

        String input = "110a80090000411f06007f0000000000000000000000000000000041a5999a3333429a7ae1428f";
        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"MODBUS_8009_WITHOUT_HEADER\","
                + "\"MODBUS_8009_EP0_1_energie_Wh_FL\","
                + "\"MODBUS_8009_EP1_1_volume_m3_FL\","
                + "\"MODBUS_8009_EP2_1_puissance_kw_FL\","
                + "\"MODBUS_8009_EP3_1_débit_m3/h_FL\","
                + "\"MODBUS_8009_EP4_1_tempdépart_°C_FL\","
                + "\"MODBUS_8009_EP5_1_tempretour_°C_FL2L\","
                + "\"MODBUS_8009_EP6_1_deltatemp_K_FL2L\","
                + "] }";

        NKEClusterMultiMasterSlaveAnswers masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterMultiMasterSlaveAnswers.class);

        String script = loadJavascriptFile(SCRIPT_PATH);
        SimpleDecoder decoder = JsDecoderFactory.buildDefaultDecoder(script);
        Gson gwm = new Gson();
        Object owm = gwm.fromJson(inputDataMessage, Object.class);
        JsonObject decodedJsonObject = decoder.toJsonObject(input, owm);

        assertThat(decodedJsonObject.getAsJsonPrimitive("endpoint").getAsString()).isEqualTo("0");
        assertThat(decodedJsonObject.getAsJsonPrimitive("commandId").getAsString()).isEqualTo("Report Attributes");
        assertThat(decodedJsonObject.getAsJsonPrimitive("messageType").getAsString()).isEqualTo("Multi Master/Slave answers");
        assertThat(decodedJsonObject.getAsJsonPrimitive("attributId").getAsString()).isEqualTo("PresentValue");
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusFrameSeriesSent").getAsInt()).isEqualTo(6);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusFrameNumberInSerie").getAsInt()).isEqualTo(0);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusLastFrameOfSerie").getAsInt()).isEqualTo(0);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP0").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP1").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP2").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP3").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP4").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP5").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP6").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP7").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP8").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP9").getAsBoolean()).isEqualTo(false);

        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP0").getAsJsonObject("energie").getAsJsonPrimitive("unit").getAsString()).isEqualTo("Wh");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP0").getAsJsonObject("energie").getAsJsonPrimitive("value").getAsFloat()).isEqualTo(0f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP1").getAsJsonObject("volume").getAsJsonPrimitive("unit").getAsString()).isEqualTo("m3");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP1").getAsJsonObject("volume").getAsJsonPrimitive("value").getAsFloat()).isEqualTo(0f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP2").getAsJsonObject("puissance").getAsJsonPrimitive("unit").getAsString()).isEqualTo("kw");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP2").getAsJsonObject("puissance").getAsJsonPrimitive("value").getAsFloat()).isEqualTo(0f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP3").getAsJsonObject("débit").getAsJsonPrimitive("unit").getAsString()).isEqualTo("m3/h");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP3").getAsJsonObject("débit").getAsJsonPrimitive("value").getAsFloat()).isEqualTo(0f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP4").getAsJsonObject("tempdépart").getAsJsonPrimitive("unit").getAsString()).isEqualTo("°C");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP4").getAsJsonObject("tempdépart").getAsJsonPrimitive("value").getAsFloat()).isEqualTo(20.7f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP5").getAsJsonObject("tempretour").getAsJsonPrimitive("unit").getAsString()).isEqualTo("°C");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP5").getAsJsonObject("tempretour").getAsJsonPrimitive("value").getAsFloat()).isEqualTo(77.099998f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP6").getAsJsonObject("deltatemp").getAsJsonPrimitive("unit").getAsString()).isEqualTo("K");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP6").getAsJsonObject("deltatemp").getAsJsonPrimitive("value").getAsFloat()).isEqualTo(71.739998f);

    }

    @Test
    @PayloadDescription(name = "ClusterMultiMasterSlaveAnswers_PresentValue", description = "Cluster: MultiMasterSlaveAnswers, Attribut: PresentValue, Command: Report Attributes. Modbus tags associated to the device: \n"
            + "MODBUS_8009_WITHOUT_HEADER, \n"
            + "MODBUS_8009_EP0_1_energie_Wh_S32, \n"
            + "MODBUS_8009_EP1_1_volume_m3_U32, \n"
            + "MODBUS_8009_EP2_1_puissance_kw_S32L, \n"
            + "MODBUS_8009_EP2_2_débit_m3/h_U32L, \n"
            + "MODBUS_8009_EP3_1_tempdépart_°C_FL, \n"
            + "MODBUS_8009_EP3_2_tempretour_°C_FL2L, \n"
            + "MODBUS_8009_EP3_3_deltatemp_K_FL2L \n")
    public void should_decode_8009_MultiMasterSlaveAnswers_attribut0000_tags_without_header_types_32_payload() throws IOException, DecodingException, JsDecoderBuildingException, JsDecodingException {

        String input = "110a80090000411f06000ffffffffe00000004fffeffff0005000041a5999a3333429a7ae1428f";

        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"MODBUS_8009_WITHOUT_HEADER\","
                + "\"MODBUS_8009_EP0_1_energie_Wh_S32\","
                + "\"MODBUS_8009_EP1_1_volume_m3_U32\","
                + "\"MODBUS_8009_EP2_1_puissance_kw_S32L\","
                + "\"MODBUS_8009_EP2_2_débit_m3/h_U32L\","
                + "\"MODBUS_8009_EP3_1_tempdépart_°C_FL\","
                + "\"MODBUS_8009_EP3_2_tempretour_°C_FL2L\","
                + "\"MODBUS_8009_EP3_3_deltatemp_K_FL2L\","
                + "] }";

        NKEClusterMultiMasterSlaveAnswers masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterMultiMasterSlaveAnswers.class);

        String script = loadJavascriptFile(SCRIPT_PATH);
        SimpleDecoder decoder = JsDecoderFactory.buildDefaultDecoder(script);
        Gson gwm = new Gson();
        Object owm = gwm.fromJson(inputDataMessage, Object.class);
        JsonObject decodedJsonObject = decoder.toJsonObject(input, owm);

        assertThat(decodedJsonObject.getAsJsonPrimitive("endpoint").getAsString()).isEqualTo("0");
        assertThat(decodedJsonObject.getAsJsonPrimitive("commandId").getAsString()).isEqualTo("Report Attributes");
        assertThat(decodedJsonObject.getAsJsonPrimitive("messageType").getAsString()).isEqualTo("Multi Master/Slave answers");
        assertThat(decodedJsonObject.getAsJsonPrimitive("attributId").getAsString()).isEqualTo("PresentValue");
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusFrameSeriesSent").getAsInt()).isEqualTo(6);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusFrameNumberInSerie").getAsInt()).isEqualTo(0);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusLastFrameOfSerie").getAsInt()).isEqualTo(0);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP0").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP1").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP2").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP3").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP4").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP5").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP6").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP7").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP8").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP9").getAsBoolean()).isEqualTo(false);

        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP0").getAsJsonObject("energie").getAsJsonPrimitive("unit").getAsString()).isEqualTo("Wh");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP0").getAsJsonObject("energie").getAsJsonPrimitive("value").getAsFloat()).isEqualTo(-2f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP1").getAsJsonObject("volume").getAsJsonPrimitive("unit").getAsString()).isEqualTo("m3");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP1").getAsJsonObject("volume").getAsJsonPrimitive("value").getAsFloat()).isEqualTo(4f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP2").getAsJsonObject("puissance").getAsJsonPrimitive("unit").getAsString()).isEqualTo("kw");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP2").getAsJsonObject("puissance").getAsJsonPrimitive("value").getAsFloat()).isEqualTo(-2f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP2").getAsJsonObject("débit").getAsJsonPrimitive("unit").getAsString()).isEqualTo("m3/h");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP2").getAsJsonObject("débit").getAsJsonPrimitive("value").getAsFloat()).isEqualTo(5f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP3").getAsJsonObject("tempdépart").getAsJsonPrimitive("unit").getAsString()).isEqualTo("°C");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP3").getAsJsonObject("tempdépart").getAsJsonPrimitive("value").getAsFloat()).isEqualTo(20.700001f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP3").getAsJsonObject("tempretour").getAsJsonPrimitive("unit").getAsString()).isEqualTo("°C");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP3").getAsJsonObject("tempretour").getAsJsonPrimitive("value").getAsFloat()).isEqualTo(77.099998f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP3").getAsJsonObject("deltatemp").getAsJsonPrimitive("unit").getAsString()).isEqualTo("K");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP3").getAsJsonObject("deltatemp").getAsJsonPrimitive("value").getAsFloat()).isEqualTo(71.739998f);

    }

    @Test
    @PayloadDescription(name = "ClusterMultiMasterSlaveAnswers_PresentValue", description = "Cluster: MultiMasterSlaveAnswers, Attribut: PresentValue, Command: Report Attributes. Modbus tags associated to the device: \n"
            + "MODBUS_8009_WITHOUT_HEADER, \n"
            + "MODBUS_8009_EP0_1_humidity_%_U8, \n"
            + "MODBUS_8009_EP1_1_outdoorTemperature_°C_S8, \n"
            + "MODBUS_8009_EP2_1_pressure_Pa_U16, \n"
            + "MODBUS_8009_EP3_1_counter_NOUNIT_S16 \n")
    public void should_decode_8009_MultiMasterSlaveAnswers_attribut0000_tags_without_header_types_u8u16_s8s16_payload() throws IOException, DecodingException, JsDecoderBuildingException, JsDecodingException {
        String input = "110a80090000411f06000fFEFEFFFEFFFE";
        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"MODBUS_8009_WITHOUT_HEADER\","
                + "\"MODBUS_8009_EP0_1_humidity_%_U8\","
                + "\"MODBUS_8009_EP1_1_outdoorTemperature_°C_S8\","
                + "\"MODBUS_8009_EP2_1_pressure_Pa_U16\","
                + "\"MODBUS_8009_EP3_1_counter_NOUNIT_S16\""
                + "] }";

        NKEClusterMultiMasterSlaveAnswers masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterMultiMasterSlaveAnswers.class);

        String script = loadJavascriptFile(SCRIPT_PATH);
        SimpleDecoder decoder = JsDecoderFactory.buildDefaultDecoder(script);
        Gson gwm = new Gson();
        Object owm = gwm.fromJson(inputDataMessage, Object.class);
        JsonObject decodedJsonObject = decoder.toJsonObject(input, owm);

        assertThat(decodedJsonObject.getAsJsonPrimitive("endpoint").getAsString()).isEqualTo("0");
        assertThat(decodedJsonObject.getAsJsonPrimitive("commandId").getAsString()).isEqualTo("Report Attributes");
        assertThat(decodedJsonObject.getAsJsonPrimitive("messageType").getAsString()).isEqualTo("Multi Master/Slave answers");
        assertThat(decodedJsonObject.getAsJsonPrimitive("attributId").getAsString()).isEqualTo("PresentValue");
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusFrameSeriesSent").getAsInt()).isEqualTo(6);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusFrameNumberInSerie").getAsInt()).isEqualTo(0);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusLastFrameOfSerie").getAsInt()).isEqualTo(0);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP0").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP1").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP2").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP3").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP4").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP5").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP6").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP7").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP8").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP9").getAsBoolean()).isEqualTo(false);

        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP0").getAsJsonObject("humidity").getAsJsonPrimitive("unit").getAsString()).isEqualTo("%");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP0").getAsJsonObject("humidity").getAsJsonPrimitive("value").getAsInt()).isEqualTo(254);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP1").getAsJsonObject("outdoorTemperature").getAsJsonPrimitive("unit").getAsString()).isEqualTo("°C");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP1").getAsJsonObject("outdoorTemperature").getAsJsonPrimitive("value").getAsInt()).isEqualTo(-2);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP2").getAsJsonObject("pressure").getAsJsonPrimitive("unit").getAsString()).isEqualTo("Pa");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP2").getAsJsonObject("pressure").getAsJsonPrimitive("value").getAsInt()).isEqualTo(65534);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP3").getAsJsonPrimitive("counter").getAsInt()).isEqualTo(-2);

    }

    @Test
    @PayloadDescription(name = "ClusterMultiMasterSlaveAnswers_PresentValue", description = "Cluster: MultiMasterSlaveAnswers, Attribut: PresentValue, Command: Report Attributes. Modbus tags associated to the device: \n"
            + "MODBUS_8009_WITHOUT_HEADER, \n"
            + "MODBUS_8009_EP0_1_product_NOUNIT_A6, \n"
            + "MODBUS_8009_EP1_1_reference_NOUNIT_H6 \n")
    public void should_decode_8009_MultiMasterSlaveAnswers_attribut0000_tags_without_header_types_ascii_hexa_payload() throws IOException, DecodingException, JsDecoderBuildingException, JsDecodingException {

        String input = "110a80090000411f060003414243444546414243444546";

        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"MODBUS_8009_WITHOUT_HEADER\","
                + "\"MODBUS_8009_EP0_1_product_NOUNIT_A6\","
                + "\"MODBUS_8009_EP1_1_reference_NOUNIT_H6\""
                + "] }";
        NKEClusterMultiMasterSlaveAnswers masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterMultiMasterSlaveAnswers.class);

        String script = loadJavascriptFile(SCRIPT_PATH);
        SimpleDecoder decoder = JsDecoderFactory.buildDefaultDecoder(script);
        Gson gwm = new Gson();
        Object owm = gwm.fromJson(inputDataMessage, Object.class);
        JsonObject decodedJsonObject = decoder.toJsonObject(input, owm);

        assertThat(decodedJsonObject.getAsJsonPrimitive("endpoint").getAsString()).isEqualTo("0");
        assertThat(decodedJsonObject.getAsJsonPrimitive("commandId").getAsString()).isEqualTo("Report Attributes");
        assertThat(decodedJsonObject.getAsJsonPrimitive("messageType").getAsString()).isEqualTo("Multi Master/Slave answers");
        assertThat(decodedJsonObject.getAsJsonPrimitive("attributId").getAsString()).isEqualTo("PresentValue");
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusFrameSeriesSent").getAsInt()).isEqualTo(6);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusFrameNumberInSerie").getAsInt()).isEqualTo(0);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusLastFrameOfSerie").getAsInt()).isEqualTo(0);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP0").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP1").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP2").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP3").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP4").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP5").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP6").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP7").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP8").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP9").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP0").getAsJsonPrimitive("product").getAsString()).isEqualTo("ABCDEF");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP1").getAsJsonPrimitive("reference").getAsString()).isEqualTo("414243444546");
    }

    @Test
    @PayloadDescription(name = "ClusterMultiMasterSlaveAnswers_PresentValue", description = "Cluster: MultiMasterSlaveAnswers, Attribut: PresentValue, Command: Report Attributes. Modbus tags associated to the device: \n"
            + "MODBUS_8009_WITHOUT_HEADER, \n"
            + "MODBUS_8009_EP0_1_counter_NOUNIT_U32, \n"
            + "MODBUS_8009_EP1_1_ok_NOUNIT_B1, \n"
            + "MODBUS_8009_EP1_2_status_NOUNIT_B3, \n"
            + "MODBUS_8009_EP1_3_padding_NOUNIT_R4, \n"
            + "MODBUS_8009_EP2_1_humidity_%_U8, \n"
            + "MODBUS_8009_EP3_1_temperature_°C_S16 \n")
    public void should_decode_8009_MultiMasterSlaveAnswers_attribut0000_tags_without_header_types_B_R_payload() throws IOException, DecodingException, JsDecoderBuildingException, JsDecodingException {
        String input = "110a80090000411f06000fFFFFFFFEFFFFFFFE";
        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"MODBUS_8009_WITHOUT_HEADER\","
                + "\"MODBUS_8009_EP0_1_counter_NOUNIT_U32\","
                + "\"MODBUS_8009_EP1_1_ok_NOUNIT_B1\","
                + "\"MODBUS_8009_EP1_2_status_NOUNIT_B3\","
                + "\"MODBUS_8009_EP1_3_padding_NOUNIT_R4\","
                + "\"MODBUS_8009_EP2_1_humidity_%_U8\","
                + "\"MODBUS_8009_EP3_1_temperature_°C_S16\""
                + "] }";

        NKEClusterMultiMasterSlaveAnswers masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterMultiMasterSlaveAnswers.class);

        String script = loadJavascriptFile(SCRIPT_PATH);
        SimpleDecoder decoder = JsDecoderFactory.buildDefaultDecoder(script);
        Gson gwm = new Gson();
        Object owm = gwm.fromJson(inputDataMessage, Object.class);
        JsonObject decodedJsonObject = decoder.toJsonObject(input, owm);

        assertThat(decodedJsonObject.getAsJsonPrimitive("endpoint").getAsString()).isEqualTo("0");
        assertThat(decodedJsonObject.getAsJsonPrimitive("commandId").getAsString()).isEqualTo("Report Attributes");
        assertThat(decodedJsonObject.getAsJsonPrimitive("messageType").getAsString()).isEqualTo("Multi Master/Slave answers");
        assertThat(decodedJsonObject.getAsJsonPrimitive("attributId").getAsString()).isEqualTo("PresentValue");
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusFrameSeriesSent").getAsInt()).isEqualTo(6);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusFrameNumberInSerie").getAsInt()).isEqualTo(0);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusLastFrameOfSerie").getAsInt()).isEqualTo(0);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP0").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP1").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP2").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP3").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP4").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP5").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP6").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP7").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP8").getAsBoolean()).isEqualTo(false);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP9").getAsBoolean()).isEqualTo(false);

        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP0").getAsJsonPrimitive("counter").getAsFloat()).isEqualTo(4294967294f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP1").getAsJsonPrimitive("ok").getAsString()).isEqualTo("1");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP1").getAsJsonPrimitive("status").getAsString()).isEqualTo("7");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP2").getAsJsonObject("humidity").getAsJsonPrimitive("unit").getAsString()).isEqualTo("%");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP2").getAsJsonObject("humidity").getAsJsonPrimitive("value").getAsInt()).isEqualTo(255);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP3").getAsJsonObject("temperature").getAsJsonPrimitive("unit").getAsString()).isEqualTo("°C");
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP3").getAsJsonObject("temperature").getAsJsonPrimitive("value").getAsInt()).isEqualTo(-2);

    }

    /********** tests erreurs tags ***************/

    @Test
    public void should_decode_8009_MultiMasterSlaveAnswers_attribut0000_tags_missing_tag__payload() throws IOException, DecodingException, JsDecoderBuildingException, JsDecodingException {
        String input = "110a80090000411f06000fFFFFFFFEFFFFFFFE";
        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"MODBUS_8009_WITHOUT_HEADER\","
                + "\"MODBUS_8009_EP0_1_counter_NOUNIT_U32\","
                + "\"MODBUS_8009_EP1_1_ok_NOUNIT_B1\","
                + "\"MODBUS_8009_EP1_1_status_NOUNIT_B3\","
                + "\"MODBUS_8009_EP1_3_padding_NOUNIT_R4\","
                + "\"MODBUS_8009_EP2_1_humidity_%_U8\","
                + "\"MODBUS_8009_EP3_1_temperature_°C_S16\""
                + "] }";

        NKEClusterMultiMasterSlaveAnswers masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterMultiMasterSlaveAnswers.class);

        String script = loadJavascriptFile(SCRIPT_PATH);
        SimpleDecoder decoder = JsDecoderFactory.buildDefaultDecoder(script);
        Gson gwm = new Gson();
        Object owm = gwm.fromJson(inputDataMessage, Object.class);
        JsonObject decodedJsonObject = decoder.toJsonObject(input, owm);

        assertThat(decodedJsonObject.getAsJsonPrimitive("error").getAsString()).isEqualTo("decoding failed : modbus tag missing");

    }

    @Test
    public void should_decode_8009_MultiMasterSlaveAnswers_attribut0000_tags_wrong_type_payload() throws IOException, DecodingException, JsDecoderBuildingException, JsDecodingException {
        String input = "110a80090000411f06000fFFFFFFFEFFFFFFFE";
        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"MODBUS_8009_WITHOUT_HEADER\","
                + "\"MODBUS_8009_EP0_1_counter_NOUNIT_U32\","
                + "\"MODBUS_8009_EP1_1_ok_NOUNIT_B1\","
                + "\"MODBUS_8009_EP1_2_status_NOUNIT_B3\","
                + "\"MODBUS_8009_EP1_3_padding_NOUNIT_R4\","
                + "\"MODBUS_8009_EP2_1_humidity_%_U8\","
                + "\"MODBUS_8009_EP3_1_temperature_°C_S24\""
                + "] }";

        NKEClusterMultiMasterSlaveAnswers masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterMultiMasterSlaveAnswers.class);

        String script = loadJavascriptFile(SCRIPT_PATH);
        SimpleDecoder decoder = JsDecoderFactory.buildDefaultDecoder(script);
        Gson gwm = new Gson();
        Object owm = gwm.fromJson(inputDataMessage, Object.class);
        JsonObject decodedJsonObject = decoder.toJsonObject(input, owm);

        assertThat(decodedJsonObject.getAsJsonPrimitive("error").getAsString()).isEqualTo("decoding failed : modbus error type in tag");

    }

    @Test
    public void should_decode_8009_MultiMasterSlaveAnswers_attribut0000_tags_wrong_ep_payload() throws IOException, DecodingException, JsDecoderBuildingException, JsDecodingException {
        String input = "110a80090000411f06007f0000000000000000000000000000000041a5999a3333429a7ae1428f";

        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"MODBUS_8009_WITHOUT_HEADER\","
                + "\"MODBUS_8009_EP0_1_energie_Wh_FL\","
                + "\"MODBUS_8009_EP1_1_volume_m3_FL\","
                + "\"MODBUS_8009_EP2_1_puissance_kw_FL\","
                + "\"MODBUS_8009_EP3_1_débit_m3/h_FL\","
                + "\"MODBUS_8009_EP4_1_tempdépart_°C_FL\","
                + "\"MODBUS_8009_EP6_1_deltatemp_K_FL2L\","
                + "] }";


        NKEClusterMultiMasterSlaveAnswers masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterMultiMasterSlaveAnswers.class);

        String script = loadJavascriptFile(SCRIPT_PATH);
        SimpleDecoder decoder = JsDecoderFactory.buildDefaultDecoder(script);
        Gson gwm = new Gson();
        Object owm = gwm.fromJson(inputDataMessage, Object.class);
        JsonObject decodedJsonObject = decoder.toJsonObject(input, owm);

        assertThat(decodedJsonObject.getAsJsonPrimitive("error").getAsString()).isEqualTo("decoding failed : Not defined tags for endpoint 5");

    }

    @PayloadDescription(name = "ClusterMultiMasterSlaveAnswers_PresentValue_MODBUS_8009_PROFILE_NOHEADER_10_ENDPOINTS_REG_NOUNIT_FL", description = "Cluster 8009 Attribute PresentValue standard payload containing modbus data sent by a device associated to the tag MODBUS_8009_PROFILE_NOHEADER_10_ENDPOINTS_REG_NOUNIT_FL. "
            + "\nThe MODBUS_8009_PROFILE_NOHEADER_10_ENDPOINTS_REG_NOUNIT_FL tag is replaced by the decoder by these following modbus tags:\n"
            + "MODBUS_8009_WITHOUT_HEADER, \n"
            + "MODBUS_8009_EP0_1_fregister00_NOUNIT_FL, \n"
            + "MODBUS_8009_EP1_1_fregister01_NOUNIT_FL, \n"
            + "MODBUS_8009_EP2_1_fregister02_NOUNIT_FL, \n"
            + "MODBUS_8009_EP3_1_fregister03_NOUNIT_FL, \n"
            + "MODBUS_8009_EP4_1_fregister04_NOUNIT_FL, \n"
            + "MODBUS_8009_EP5_1_fregister05_NOUNIT_FL, \n"
            + "MODBUS_8009_EP6_1_fregister06_NOUNIT_FL, \n"
            + "MODBUS_8009_EP7_1_fregister07_NOUNIT_FL, \n"
            + "MODBUS_8009_EP8_1_fregister08_NOUNIT_FL, \n"
            + "MODBUS_8009_EP9_1_fregister09_NOUNIT_FL")
    @Test
    public void should_decode_8009_MultiMasterSlaveAnswers_attribut0000_tag_PROFILE_NOHEADER_10_ENDPOINTS_REG_NOUNIT_FL() throws IOException, DecodingException, JsDecoderBuildingException, JsDecodingException {

        String input = "110a80090000411f0603ff41AC0000419DC28F418AB8524187D70A4180147B41668F5C4123AE14411C51EC0000000000000000";

        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"MODBUS_8009_PROFILE_NOHEADER_10_ENDPOINTS_REG_NOUNIT_FL\""
                + "] }";

        NKEClusterMultiMasterSlaveAnswers masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterMultiMasterSlaveAnswers.class);

        String script = loadJavascriptFile(SCRIPT_PATH);
        SimpleDecoder decoder = JsDecoderFactory.buildDefaultDecoder(script);
        Gson gwm = new Gson();
        Object owm = gwm.fromJson(inputDataMessage, Object.class);
        JsonObject decodedJsonObject = decoder.toJsonObject(input, owm);

        assertThat(decodedJsonObject.getAsJsonPrimitive("endpoint").getAsString()).isEqualTo("0");
        assertThat(decodedJsonObject.getAsJsonPrimitive("commandId").getAsString()).isEqualTo("Report Attributes");
        assertThat(decodedJsonObject.getAsJsonPrimitive("messageType").getAsString()).isEqualTo("Multi Master/Slave answers");
        assertThat(decodedJsonObject.getAsJsonPrimitive("attributId").getAsString()).isEqualTo("PresentValue");
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusFrameSeriesSent").getAsInt()).isEqualTo(6);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusFrameNumberInSerie").getAsInt()).isEqualTo(0);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusLastFrameOfSerie").getAsInt()).isEqualTo(0);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP0").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP1").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP2").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP3").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP4").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP5").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP6").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP7").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP8").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP9").getAsBoolean()).isEqualTo(true);

        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP0").getAsJsonPrimitive("fregister00").getAsFloat()).isEqualTo(21.5f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP1").getAsJsonPrimitive("fregister01").getAsFloat()).isEqualTo(19.719999f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP2").getAsJsonPrimitive("fregister02").getAsFloat()).isEqualTo(17.34f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP3").getAsJsonPrimitive("fregister03").getAsFloat()).isEqualTo(16.98f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP4").getAsJsonPrimitive("fregister04").getAsFloat()).isEqualTo(16.01f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP5").getAsJsonPrimitive("fregister05").getAsFloat()).isEqualTo(14.41f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP6").getAsJsonPrimitive("fregister06").getAsFloat()).isEqualTo(10.23f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP7").getAsJsonPrimitive("fregister07").getAsFloat()).isEqualTo(9.77f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP8").getAsJsonPrimitive("fregister08").getAsFloat()).isEqualTo(0f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP9").getAsJsonPrimitive("fregister09").getAsFloat()).isEqualTo(0f);
    }

    @PayloadDescription(name = "ClusterMultiMasterSlaveAnswers_PresentValue_MODBUS_8009_PROFILE_NOHEADER_10_ENDPOINTS_REG_NOUNIT_FL2L", description = "Cluster 8009 Attribute PresentValue standard payload containing modbus data sent by a device associated to the tag MODBUS_8009_PROFILE_NOHEADER_10_ENDPOINTS_REG_NOUNIT_FL2L. "
            + "\nThe MODBUS_8009_PROFILE_NOHEADER_10_ENDPOINTS_REG_NOUNIT_FL2L tag is replaced by the decoder by these following modbus tags:\n"
            + "MODBUS_8009_WITHOUT_HEADER, \n"
            + "MODBUS_8009_EP0_1_fregister00_NOUNIT_FL2L, \n"
            + "MODBUS_8009_EP1_1_fregister01_NOUNIT_FL2L, \n"
            + "MODBUS_8009_EP2_1_fregister02_NOUNIT_FL2L, \n"
            + "MODBUS_8009_EP3_1_fregister03_NOUNIT_FL2L, \n"
            + "MODBUS_8009_EP4_1_fregister04_NOUNIT_FL2L, \n"
            + "MODBUS_8009_EP5_1_fregister05_NOUNIT_FL2L, \n"
            + "MODBUS_8009_EP6_1_fregister06_NOUNIT_FL2L, \n"
            + "MODBUS_8009_EP7_1_fregister07_NOUNIT_FL2L, \n"
            + "MODBUS_8009_EP8_1_fregister08_NOUNIT_FL2L, \n"
            + "MODBUS_8009_EP9_1_fregister09_NOUNIT_FL2L")
    @Test
    public void should_decode_8009_MultiMasterSlaveAnswers_attribut0000_tag_PROFILE_NOHEADER_10_ENDPOINTS_REG_NOUNIT_FL2L() throws IOException, DecodingException, JsDecoderBuildingException, JsDecodingException {

        String input = "110a80090000411f0603ff469a468bcccd423ef7dc4a217ae13e943333429a7ae1428f00000000000000007ae1428f00000000";

        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"MODBUS_8009_PROFILE_NOHEADER_10_ENDPOINTS_REG_NOUNIT_FL2L\""
                + "] }";
        NKEClusterMultiMasterSlaveAnswers masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterMultiMasterSlaveAnswers.class);

        String script = loadJavascriptFile(SCRIPT_PATH);
        SimpleDecoder decoder = JsDecoderFactory.buildDefaultDecoder(script);
        Gson gwm = new Gson();
        Object owm = gwm.fromJson(inputDataMessage, Object.class);
        JsonObject decodedJsonObject = decoder.toJsonObject(input, owm);

        assertThat(decodedJsonObject.getAsJsonPrimitive("endpoint").getAsString()).isEqualTo("0");
        assertThat(decodedJsonObject.getAsJsonPrimitive("commandId").getAsString()).isEqualTo("Report Attributes");
        assertThat(decodedJsonObject.getAsJsonPrimitive("messageType").getAsString()).isEqualTo("Multi Master/Slave answers");
        assertThat(decodedJsonObject.getAsJsonPrimitive("attributId").getAsString()).isEqualTo("PresentValue");
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusFrameSeriesSent").getAsInt()).isEqualTo(6);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusFrameNumberInSerie").getAsInt()).isEqualTo(0);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusLastFrameOfSerie").getAsInt()).isEqualTo(0);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP0").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP1").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP2").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP3").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP4").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP5").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP6").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP7").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP8").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP9").getAsBoolean()).isEqualTo(true);

        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP0").getAsJsonPrimitive("fregister00").getAsFloat()).isEqualTo(17827.300781f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP1").getAsJsonPrimitive("fregister01").getAsFloat()).isEqualTo(47.700001f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP2").getAsJsonPrimitive("fregister02").getAsFloat()).isEqualTo(2653687f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP3").getAsJsonPrimitive("fregister03").getAsFloat()).isEqualTo(0.29f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP4").getAsJsonPrimitive("fregister04").getAsFloat()).isEqualTo(77.099998f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP5").getAsJsonPrimitive("fregister05").getAsFloat()).isEqualTo(71.739998f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP6").getAsJsonPrimitive("fregister06").getAsFloat()).isEqualTo(0f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP7").getAsJsonPrimitive("fregister07").getAsFloat()).isEqualTo(0f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP8").getAsJsonPrimitive("fregister08").getAsFloat()).isEqualTo(71.739998f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP9").getAsJsonPrimitive("fregister09").getAsFloat()).isEqualTo(0f);

    }

    @PayloadDescription(name = "ClusterMultiMasterSlaveAnswers_PresentValue_MODBUS_8009_PROFILE_NOHEADER_10_ENDPOINTS_REG_NOUNIT_FL2L", description = "Cluster 8009 Attribute PresentValue standard payload containing modbus data sent by a device associated to the tag MODBUS_8009_PROFILE_NOHEADER_10_ENDPOINTS_REG_NOUNIT_FL2L. The modbus data contains only 7 coded registers whereas the modbus tags are declared for 10 registers. "
            + "\nThe MODBUS_8009_PROFILE_NOHEADER_10_ENDPOINTS_REG_NOUNIT_FL2L tag is replaced by the decoder by these following modbus tags:\n"
            + "MODBUS_8009_WITHOUT_HEADER, \n"
            + "MODBUS_8009_EP0_1_fregister00_NOUNIT_FL2L, \n"
            + "MODBUS_8009_EP1_1_fregister01_NOUNIT_FL2L, \n"
            + "MODBUS_8009_EP2_1_fregister02_NOUNIT_FL2L, \n"
            + "MODBUS_8009_EP3_1_fregister03_NOUNIT_FL2L, \n"
            + "MODBUS_8009_EP4_1_fregister04_NOUNIT_FL2L, \n"
            + "MODBUS_8009_EP5_1_fregister05_NOUNIT_FL2L, \n"
            + "MODBUS_8009_EP6_1_fregister06_NOUNIT_FL2L, \n"
            + "MODBUS_8009_EP7_1_fregister07_NOUNIT_FL2L, \n"
            + "MODBUS_8009_EP8_1_fregister08_NOUNIT_FL2L, \n"
            + "MODBUS_8009_EP9_1_fregister09_NOUNIT_FL2L")
    @Test
    public void should_decode_8009_MultiMasterSlaveAnswers_attribut0000_tag_PROFILE_NOHEADER_10_ENDPOINTS_missing_data() throws IOException, DecodingException, JsDecoderBuildingException, JsDecodingException {

        String input = "110a80090000411f0603ff469a468bcccd423ef7dc4a217ae13e943333429a7ae1428f00000000";

        String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\","
                + "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 0,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\","
                + "   \"value\": {" + "      \"payload\": \" " + input + "\"    },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"MODBUS_8009_PROFILE_NOHEADER_10_ENDPOINTS_REG_NOUNIT_FL2L\""
                + "] }";

        NKEClusterMultiMasterSlaveAnswers masterSlaveProtocol = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEClusterMultiMasterSlaveAnswers.class);

        String script = loadJavascriptFile(SCRIPT_PATH);
        SimpleDecoder decoder = JsDecoderFactory.buildDefaultDecoder(script);
        Gson gwm = new Gson();
        Object owm = gwm.fromJson(inputDataMessage, Object.class);
        JsonObject decodedJsonObject = decoder.toJsonObject(input, owm);

        assertThat(decodedJsonObject.getAsJsonPrimitive("endpoint").getAsString()).isEqualTo("0");
        assertThat(decodedJsonObject.getAsJsonPrimitive("commandId").getAsString()).isEqualTo("Report Attributes");
        assertThat(decodedJsonObject.getAsJsonPrimitive("messageType").getAsString()).isEqualTo("Multi Master/Slave answers");
        assertThat(decodedJsonObject.getAsJsonPrimitive("attributId").getAsString()).isEqualTo("PresentValue");
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusFrameSeriesSent").getAsInt()).isEqualTo(6);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusFrameNumberInSerie").getAsInt()).isEqualTo(0);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusLastFrameOfSerie").getAsInt()).isEqualTo(0);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP0").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP1").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP2").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP3").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP4").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP5").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP6").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP7").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP8").getAsBoolean()).isEqualTo(true);
        assertThat(decodedJsonObject.getAsJsonPrimitive("multimodbusEP9").getAsBoolean()).isEqualTo(true);

        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP0").getAsJsonPrimitive("fregister00").getAsFloat()).isEqualTo(17827.300781f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP1").getAsJsonPrimitive("fregister01").getAsFloat()).isEqualTo(47.700001f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP2").getAsJsonPrimitive("fregister02").getAsFloat()).isEqualTo(2653687f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP3").getAsJsonPrimitive("fregister03").getAsFloat()).isEqualTo(0.29f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP4").getAsJsonPrimitive("fregister04").getAsFloat()).isEqualTo(77.099998f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP5").getAsJsonPrimitive("fregister05").getAsFloat()).isEqualTo(71.739998f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP6").getAsJsonPrimitive("fregister06").getAsFloat()).isEqualTo(0f);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP7")).isEqualTo(null);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP8")).isEqualTo(null);
        assertThat(decodedJsonObject.getAsJsonObject("multimodbus_EP9")).isEqualTo(null);

    }

}
