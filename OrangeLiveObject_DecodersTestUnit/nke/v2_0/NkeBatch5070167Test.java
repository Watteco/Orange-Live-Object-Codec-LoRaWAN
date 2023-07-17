package com.orange.lo.decoder.js.nke.v2_0;
import static org.assertj.core.api.Assertions.assertThat;
import com.orange.lo.decoder.js.TestBase;
import com.orange.lo.decoder.js.doc.annotation.DeviceDescription;
import com.orange.lo.decoder.js.doc.annotation.PayloadDescription;
import com.orange.lo.decoder.js.exception.JsDecodingException;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEBatch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
@Slf4j
@DeviceDescription(name = "NKE 50-70-167 sensor with batch configuration", manufacturer = "NKE WATTECO", docLink = "http://support.nke-watteco.com/", 
					version = { "1.0",
							"</h5>",
							"<p>Tags corresponding to the batch configuration must be declared in the device parameters on Live Objects, in order to the decoder to get the contain of the received frame.</p>",
							"",
							"<p>Naming principles of a tag <b>BATCH_<name>_<unit>_Lx_Ry_Tz_Dv</b> where :</p>",
							"<div>-&nbsp;BATCH is the tag prefix, </div>",
							"<div>-&nbsp;name is the field name which will appear in the generated json (It must be a plural name, starting with a lowercase, and in camelCase format),</div>",
							"<div>-&nbsp;unit is the unit in which the values ​​will be displayed (after division if _Dv is present) but equal to NoUnit in the case where the decoded values have no unit,</div>",
							"<div>-&nbsp;x is the label number, </div>",
							"<div>-&nbsp;y is the resolution,</div>",
							"<div>-&nbsp;z is the type (cf http://support.nke-watteco.com/batchtype/),</div>",
							"<div>-&nbsp;_Dv is optional with v the value by which the decoded raw data will be divided.</div>",
							"</br>",
							"<p>When all specific tags are declared, a global tag BATCH_tagsize_n must be added, where n is tagsize parameter of function br_uncompress (cf http://support.nke-watteco.com/#BatchReport).</p>",
							"<p>Examples : BATCH_illuminances_%_L2_R1_T12, BATCH_batteryLevels_V_L2_R1_T6_D1000, BATCH_openCaseAlarms_NoUnit_L3_R1_T1,BATCH_tagsize_2</p>",
							"</br>",
							"<p>As the same field batch tags are often declared for each device type, the decoder now contains field batch tags sets for several device types:</p>",
							"<p>50-70-053, 50-70-085, 50-70-160, 50-70-161, 50-70-162, 50-70-163, 50-70-164, 50-70-167 and 50-70-168.</p>",
							"<p>So it is now possible to declare only one <b>BATCH_device_reference_DEFAULT_PROFILE</b> tag, replacing the recurrent field batch tags. The decoder will replace the batch profile tag by the field batch tags.</p>",
							"<p>See BATCH_device_reference_DEFAULT_PROFILE tag in device test descriptions below, to get the corresponding field batch tags.</p>", 				   
							"<h5>"},  
							encoding = "nke_lora_5070167_v2.3", hidden = true, developer = "Nke-Watteco / Orange")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NkeBatch5070167Test  extends TestBase {
	private final String SCRIPT_PATH = "nke/nkePrivateV2_3_5070167";
	//private final String SCRIPT_PATH = "nke/nkePublicV2_3";
	//private final String SCRIPT_PATH = "nke/nkePublicV2_2";
	//private final String SCRIPT_PATH = "nke/nkeBatch1.4";
/*
    @Test
    @Ignore //fonctions récursives volontairement laissées dans le script
    public void should_check_script() throws JsDecodingException {
        checkScript(SCRIPT_PATH);
    }
*/ 
  
    @Test
    public void should_profile() throws JsDecodingException {
   	String inputDataMessage = "{" + " \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\"," 
    			+ "   \"connector\": \"lora\","
                + "   \"encoding\": \"nke_watteco_v2.4\"," 
    			+ "   \"network\": {" + " \"lora\": {" + " \"port\": 6,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + " } }  }," 
    			+ "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\"," 
                + "   \"value\": {" + " \"payload\": \"2000c0c476fa8000b86d38b40004d5a953571d013c\"" + "   },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": []}";         
       
        String input = "2000c0c476fa8000b86d38b40004d5a953571d013c";              
    
        log.info("result: {}", profile(SCRIPT_PATH, input, inputDataMessage));
    }	
  
    @Test
    public void should_decode_bad_payload() throws JsDecodingException {    
    	String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\"," 
    			+ "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 6,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   }," 
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\"," 
                + "   \"value\": {" + "      \"payload\": \"00\"" + "   },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"BATCH_pa1_kW_L0_R1_T6\","
                + "\"BATCH_tagsize_1\" ]" + "}";
        
        NKEBatch frame = formatAndDecode(SCRIPT_PATH, "00", inputDataMessage, NKEBatch.class);
        assertThat(frame.getError()).isEqualTo("decoding failed : incomplete payload");
    }
    
    
    @PayloadDescription(name = "BATCH_5070167_DEFAULT_PROFILE for device 50-70-167", description = "Batch payload sent by a device 50-70-167 associated to the tag BATCH_5070167_DEFAULT_PROFILE. "
    		+ "\nThe BATCH_5070167_DEFAULT_PROFILE tag is replaced by the decoder by these following field batch tags:\n"
    		+ "BATCH_tagsize_2,\n "
    		+ "BATCH_temperatures_°C_L0_R10_T7_D100, \n"
            + "BATCH_batteryLevels_mV_L2_R1_T6, \n"
            + "BATCH_openCaseAlarms_NoUnit_L3_R1_T1")
    @Test
    public void should_decode_5070167_payload_with_tagBatchProfile() throws JsDecodingException {
    	
    	String inputDataMessage = "{" + " \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\"," 
    			+ "   \"connector\": \"lora\","
                + "   \"encoding\": \"nke_watteco_v2.4\"," 
    			+ "   \"network\": {" + " \"lora\": {" + " \"port\": 6,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + " } }  }," 
    			+ "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\"," 
                + "   \"value\": {" + " \"payload\": \"2000c0c476fa8000b86d38b40004d5a953571d013c\"" + "   },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": []}";         
       
        String input = "2000c0c476fa8000b86d38b40004d5a953571d013c";              
        NKEBatch frame = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEBatch.class);
        assertThat(frame.getBatchCounter()).isEqualTo(0);
        assertThat(frame.getBatchTimestamp()).isEqualTo("2017-11-07T17:17:25.000Z");
        assertThat(frame.getBatteryLevels().getUnit()).isEqualTo("mV");
        assertThat(frame.getBatteryLevels().getValues().size()).isEqualTo(1);
        assertThat(frame.getBatteryLevels().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T17:17:15.000Z");
        assertThat(frame.getBatteryLevels().getValues().get(0).getValue()).isEqualTo(3629);
        assertThat(frame.getTemperatures().getUnit()).isEqualTo("°C");
        assertThat(frame.getTemperatures().getValues().size()).isEqualTo(5);
        assertThat(frame.getTemperatures().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T17:12:33.000Z");
        assertThat(frame.getTemperatures().getValues().get(0).getValue()).isEqualTo(7.7f);
        assertThat(frame.getTemperatures().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T17:13:33.000Z");
        assertThat(frame.getTemperatures().getValues().get(1).getValue()).isEqualTo(7.7f);
        assertThat(frame.getTemperatures().getValues().get(2).getTimestamp()).isEqualTo("2017-11-07T17:14:33.000Z");
        assertThat(frame.getTemperatures().getValues().get(2).getValue()).isEqualTo(7.7f);
        assertThat(frame.getTemperatures().getValues().get(3).getTimestamp()).isEqualTo("2017-11-07T17:15:33.000Z");
        assertThat(frame.getTemperatures().getValues().get(3).getValue()).isEqualTo(7.6f);
        assertThat(frame.getTemperatures().getValues().get(4).getTimestamp()).isEqualTo("2017-11-07T17:16:33.000Z");
        assertThat(frame.getTemperatures().getValues().get(4).getValue()).isEqualTo(7.6f);
        assertThat(frame.getOpenCaseAlarms().getValues().size()).isEqualTo(0);   
    }
    
 
    
 
    
}

