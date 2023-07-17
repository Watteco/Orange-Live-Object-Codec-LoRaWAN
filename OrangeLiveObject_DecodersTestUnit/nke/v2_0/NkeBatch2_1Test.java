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
@DeviceDescription(name = "All Watteco (NKE-Watteco) sensors with batch configuration", manufacturer = "WATTECO", docLink = "https://support.watteco.com/", 
					version = { "1.0",
							"</h5>",
							"<p>Tags corresponding to the batch configuration must be declared in the device parameters on Live Objects, in order to the decoder to get the contain of the received frame.</p>",
							"",
							"<p>Naming principles of a tag <b>BATCH_<i>name</i>_<i>unit</i>_Lx_Ry_Tz_Dv</b> where :</p>",
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
							"<p>50-70-053, 50-70-085, 50-70-108, 50-70-160, 50-70-161, 50-70-162, 50-70-163, 50-70-164, 50-70-166, 50-70-167 and 50-70-168.</p>",
							"<p>So it is now possible to declare only one <b>BATCH_device_reference_DEFAULT_PROFILE</b> tag, replacing the recurrent field batch tags. The decoder will replace the batch profile tag by the field batch tags.</p>",
							"<p>See BATCH_device_reference_DEFAULT_PROFILE tag in device test descriptions below, to get the corresponding field batch tags.</p>", 				   
							"<h5>"},  
							encoding = "watteco_generic", hidden = false, developer = "Watteco / Nke-Watteco / Orange")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NkeBatch2_1Test  extends TestBase {
	private final String SCRIPT_PATH = "nke/nkePublicV2_10";
	
/*
    @Test
    @Ignore //fonctions récursives volontairement laissées dans le script
    public void should_check_script() throws JsDecodingException {
        checkScript(SCRIPT_PATH);
    }
*/ 
 
    @Test
    public void should_profile() throws JsDecodingException {
        String input = "00";
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
    
    @Test
    @PayloadDescription(name = "Batch payload decoding with batch field tags", description = "Decoding of a batch frame. Tags corresponding to the batch configuration must have been declared for the device in live object, "
    		+ "in order to the decoder to get the contain of the received frame. Naming principles of a tag BATCH_<name>_<unit>_Lx_Ry_Tz_Dv : "
    		+ "BATCH is the tag prefix, "
    		+ "name is the field name which will appear in the generated json (It must be a plural name, starting with a lowercase, and in camelCase format), "
    		+ "unit is the unit in which the values ​​will be displayed (after division if _Dv is present) but equal to NoUnit in the case where the decoded values have no unit, "
            + "x is the label number, "
    		+ "y is the resolution, "
    		+ "z is the type, "
    		+ "_Dv is optional with v the value by which the decoded raw data will be divided. "
    		+ "When all specific tags are declared, a global tag BATCH_tagsize_n must be added, where n is tagsize parameter of function br_uncompress (cf documentation)."
    		+ "Exemples : BATCH_illuminances_%_L2_R1_T12, BATCH_batteryLevels_V_L2_R1_T6_D1000, BATCH_openCaseAlarms_NoUnit_L3_R1_T1,BATCH_tagsize_2")
    public void should_decode_batch_payload() throws JsDecodingException {      	
    	String inputDataMessage = "{" + "   \"metadata\": {" + "      \"source\": \"urn:lora:70B3D516C0000068\"," 
    			+ "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "         \"lora\": {" + "            \"port\": 6,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "         }" + "      }" + "   }," + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," 
                + "   \"model\": \"lora_v0\"," + "   \"value\": {" 
                + "   \"payload\": \"52000080819504b0c83e8232b40f02050008de1770631300401040ba06d1122b8af9280cd91100802212\"" + "   },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ",   \"tags\": [ "
        		+ "\"BATCH_temperatures_°C_L0_R100_T7_D100\", "
        		+ "\"BATCH_humidities_%_L1_R500_T6_D100\","
        		+ "\"BATCH_illuminances_%_L2_R1_T12\", "
        		+ "\"BATCH_rechargeableBattery_Volts_L3_R600_T6_D1000\","
        		+ "\"BATCH_disposableBattery_Volts_L4_R600_T6_D1000\" ]" + "}";

        //System.out.println("Test data message == " + inputDataMessage);      
        String input = "52000080819504bfc83e8232b40f02050008de1770631300401040ba06d1122b8af9280cd91100802212";
        
        NKEBatch frame = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEBatch.class);
        assertThat(frame.getBatchCounter()).isEqualTo(0);
        assertThat(frame.getBatchTimestamp()).isEqualTo("2017-11-07T17:17:25.000Z");
        assertThat(frame.getTemperatures().getUnit()).isEqualTo("°C");
        assertThat(frame.getTemperatures().getValues().size()).isEqualTo(2);
        assertThat(frame.getTemperatures().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T07:40:56.000Z");
        assertThat(frame.getTemperatures().getValues().get(0).getValue()).isEqualTo(24.3f);
        assertThat(frame.getTemperatures().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T07:41:26.000Z");
        assertThat(frame.getTemperatures().getValues().get(1).getValue()).isEqualTo(26.3f);
        assertThat(frame.getHumidities().getUnit()).isEqualTo("%");
        assertThat(frame.getHumidities().getValues().size()).isEqualTo(2);
        assertThat(frame.getHumidities().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T07:40:56.000Z");
        assertThat(frame.getHumidities().getValues().get(0).getValue()).isEqualTo(45f);
        assertThat(frame.getHumidities().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T07:41:26.000Z");
        assertThat(frame.getHumidities().getValues().get(1).getValue()).isEqualTo(90f);
        assertThat(frame.getIlluminances().getUnit()).isEqualTo("%");
        assertThat(frame.getIlluminances().getValues().size()).isEqualTo(2);
        assertThat(frame.getIlluminances().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T07:40:56.000Z");
        assertThat(frame.getIlluminances().getValues().get(0).getValue()).isEqualTo(20f);
        assertThat(frame.getIlluminances().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T07:41:26.000Z");
        assertThat(frame.getIlluminances().getValues().get(1).getValue()).isEqualTo(50f);
        assertThat(frame.getRechargeableBattery().getUnit()).isEqualTo("Volts");
        assertThat(frame.getRechargeableBattery().getValues().size()).isEqualTo(1);
        assertThat(frame.getRechargeableBattery().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T07:41:29.000Z");
        assertThat(frame.getRechargeableBattery().getValues().get(0).getValue()).isEqualTo(0f);
        assertThat(frame.getDisposableBattery().getUnit()).isEqualTo("Volts");
        assertThat(frame.getDisposableBattery().getValues().size()).isEqualTo(1);
        assertThat(frame.getDisposableBattery().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T07:41:25.000Z");
        assertThat(frame.getDisposableBattery().getValues().get(0).getValue()).isEqualTo(3f);       
    }
    
    
    @Test
    public void should_decode_real_payloads() throws JsDecodingException {  
    	
    	//TH_4222_and_upper ref 50-70-053    	
    	String inputDataMessage = "{" + " \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\"," 
    			+ "   \"connector\": \"lora\","
                + "   \"encoding\": \"nke_watteco_v2.4\"," 
    			+ "   \"network\": {" + " \"lora\": {" + " \"port\": 6,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + " } }  }," 
    			+ "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\"," 
                + "   \"value\": {" + " \"payload\": \"2002c06867cd81a2b44f204e02da79820b68e709423b\"" + "   },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"BATCH_temperatures_°C_L0_R1_T7_D100\", "
                + "\"BATCH_relativeHumidities_%RH_L1_R100_T6_D100\","
                + "\"BATCH_batteryLevels_V_L2_R1_T6_D1000\", "
                + "\"BATCH_openCaseAlarms_NoUnit_L3_R1_T1\","
                + "\"BATCH_tagsize_2\" ]" + "}";
    	
        //System.out.println("Test data message == " + inputDataMessage);           
       
        String input = "2002c06867cd81a2b44f204e02da79820b68e709423b";              
        NKEBatch frame = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEBatch.class);
        assertThat(frame.getBatchCounter()).isEqualTo(2);
        assertThat(frame.getBatchTimestamp()).isEqualTo("2017-11-07T17:17:25.000Z");
        assertThat(frame.getTemperatures().getUnit()).isEqualTo("°C");
        assertThat(frame.getTemperatures().getValues().size()).isEqualTo(2);
        assertThat(frame.getTemperatures().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T16:44:19.000Z");
        assertThat(frame.getTemperatures().getValues().get(0).getValue()).isEqualTo(19.30f);
        assertThat(frame.getTemperatures().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T17:14:19.000Z");
        assertThat(frame.getTemperatures().getValues().get(1).getValue()).isEqualTo(19.28f);
        assertThat(frame.getRelativeHumidities().getUnit()).isEqualTo("%RH");
        assertThat(frame.getRelativeHumidities().getValues().size()).isEqualTo(2);
        assertThat(frame.getRelativeHumidities().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T16:44:19.000Z");
        assertThat(frame.getRelativeHumidities().getValues().get(0).getValue()).isEqualTo(25.00f);
        assertThat(frame.getRelativeHumidities().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T17:14:19.000Z");
        assertThat(frame.getRelativeHumidities().getValues().get(1).getValue()).isEqualTo(32.00f);
        assertThat(frame.getBatteryLevels().getValues().size()).isEqualTo(0);
        assertThat(frame.getBatteryLevels().getUnit()).isEqualTo("V");
        assertThat(frame.getOpenCaseAlarms().getValues().size()).isEqualTo(0);      
        
        input = "4007000000190248b69f40ccafc5ab03911010a52820caff0046026c2d";
        
		frame = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEBatch.class);
		assertThat(frame.getBatchCounter()).isEqualTo(7);
		assertThat(frame.getBatchTimestamp()).isEqualTo("2017-11-07T17:17:25.000Z");
		assertThat(frame.getTemperatures().getUnit()).isEqualTo("°C");
		assertThat(frame.getTemperatures().getValues().size()).isEqualTo(2);
		assertThat(frame.getTemperatures().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T17:10:45.000Z");
		assertThat(frame.getTemperatures().getValues().get(0).getValue()).isEqualTo(20.80f);
		assertThat(frame.getTemperatures().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T17:12:25.000Z");
		assertThat(frame.getTemperatures().getValues().get(1).getValue()).isEqualTo(20.87f);
		assertThat(frame.getRelativeHumidities().getUnit()).isEqualTo("%RH");
		assertThat(frame.getRelativeHumidities().getValues().size()).isEqualTo(2);
		assertThat(frame.getRelativeHumidities().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T17:10:45.000Z");
		assertThat(frame.getRelativeHumidities().getValues().get(0).getValue()).isEqualTo(50.00f);
		assertThat(frame.getRelativeHumidities().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T17:12:25.000Z");
		assertThat(frame.getRelativeHumidities().getValues().get(1).getValue()).isEqualTo(80.00f);
		assertThat(frame.getBatteryLevels().getValues().size()).isEqualTo(1);
		assertThat(frame.getBatteryLevels().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T17:11:35.000Z");
		assertThat(frame.getBatteryLevels().getValues().get(0).getValue()).isEqualTo(3.652f);
		assertThat(frame.getOpenCaseAlarms().getValues().size()).isEqualTo(2);
		assertThat(frame.getOpenCaseAlarms().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T17:11:10.000Z");
		assertThat(frame.getOpenCaseAlarms().getValues().get(0).getValue()).isEqualTo(1);
		assertThat(frame.getOpenCaseAlarms().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T17:11:13.000Z");
		assertThat(frame.getOpenCaseAlarms().getValues().get(1).getValue()).isEqualTo(0);
         
    }
    
    
    @Test
    public void should_decode_batch_tic_payloads() throws JsDecodingException {  
    	
    	String inputDataMessage = "     {" + "   \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\"," 
    			+ "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "  \"lora\": {" + "   \"port\": 6,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "  }" + "      }" + "   }," 
                + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\"," 
                + "   \"value\": {" + "      \"payload\": \"2002c06867cd81a2b44f204e02da79820b68e709423b\"" + "   },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"BATCH_pa1_kW_L0_R1_T6\","
                + "\"BATCH_tagsize_1\" ]" + "}";
    	
        System.out.println("Test data message == " + inputDataMessage);
        
        //payload found in documentation TIC_Application_Layer_Description_1.0.pdf
        String input = "100200c0e41880925814dbdd196bb798d6eeceb1dd625abb3bc7768bc1dadd39b65b4c6b77e7d86e31addd9db1768b89edeedcda2d26b6bb736bb718acdd9d63bbc5b476778eed16d3dadd196bb79818";
        NKEBatch frame = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEBatch.class);
		assertThat(frame.getBatchCounter()).isEqualTo(2);
		assertThat(frame.getBatchTimestamp()).isEqualTo("2017-11-07T17:17:25.000Z");
		assertThat(frame.getPa1().getUnit()).isEqualTo("kW");
		assertThat(frame.getPa1().getValues().size()).isEqualTo(23);
		assertThat(frame.getPa1().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T16:57:34.000Z");
		assertThat(frame.getPa1().getValues().get(0).getValue()).isEqualTo(148);
		assertThat(frame.getPa1().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T16:58:17.000Z");
		assertThat(frame.getPa1().getValues().get(1).getValue()).isEqualTo(610);
		assertThat(frame.getPa1().getValues().get(2).getTimestamp()).isEqualTo("2017-11-07T16:59:42.000Z");
		assertThat(frame.getPa1().getValues().get(2).getValue()).isEqualTo(148);
		assertThat(frame.getPa1().getValues().get(3).getTimestamp()).isEqualTo("2017-11-07T17:00:24.000Z");
		assertThat(frame.getPa1().getValues().get(3).getValue()).isEqualTo(610);
		assertThat(frame.getPa1().getValues().get(22).getTimestamp()).isEqualTo("2017-11-07T17:16:42.000Z");
		assertThat(frame.getPa1().getValues().get(22).getValue()).isEqualTo(148);
		
    }
    
    @Test
    public void should_decode_batch_payload_while_wrong_tags() throws JsDecodingException {  
    	
    	String inputDataMessage = "{" + "   \"metadata\": {" + "      \"source\": \"urn:lora:70B3D516C0000068\"," 
    			+ "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "         \"lora\": {" + "            \"port\": 6,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "         }" + "      }" + "   }," + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," 
                + "   \"model\": \"lora_v0\"," + "   \"value\": {" 
                + "   \"payload\": \"52000080819504b0c83e8232b40f02050008de1770631300401040ba06d1122b8af9280cd91100802212\"" + "   },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ",   \"tags\": [ "
                		+ "\"BATCH_temperatures_°C_L0_R100_D100\", "
                		+ "\"BATCH_humidities_%_L1_R500_T6_D100\","
                		+ "\"BATCH_illuminances_%_L2_R1_T12\", "
                		+ "\"BATCH_rechargeableBattery_Volts_L3_R600_T6_D1000\","
                		+ "\"BATCH_disposableBattery_Volts_L4_R600_T6_D1000\" ]" + "}";
    	
        String input = "52000080819504b0c83e8232b40f02050008de1770631300401040ba06d1122b8af9280cd91100802212";
        
        NKEBatch frame = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEBatch.class);
        assertThat(frame.getError()).isEqualTo("decoding failed : type missing in tag");
    }
    
    @Test
    public void should_decode_batch_payload_without_tags() throws JsDecodingException {  
    	
    	String inputDataMessage = "{" + "   \"metadata\": {" + "      \"source\": \"urn:lora:70B3D516C0000068\"," 
    			+ "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "         \"lora\": {" + "            \"port\": 6,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "         }" + "      }" + "   }," + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," 
                + "   \"model\": \"lora_v0\"," + "   \"value\": {" 
                + "   \"payload\": \"52000080819504b0c83e8232b40f02050008de1770631300401040ba06d1122b8af9280cd91100802212\"" + "   },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ",   \"tags\": []" + "}";

        System.out.println("Test data message == " + inputDataMessage);      
        String input = "52000080819504b0c83e8232b40f02050008de1770631300401040ba06d1122b8af9280cd91100802212";
        
        NKEBatch frame = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEBatch.class);
        assertThat(frame.getError()).isEqualTo("decoding failed : no batch tag defined for the device");
        
    }
    
    @Test
    public void should_decode_batch_real_payload() throws JsDecodingException {  
    	
    	String inputDataMessage = "{" + "   \"metadata\": {" + "      \"source\": \"urn:lora:70B3D516C0000068\"," 
    			+ "   \"connector\": \"lora\","
                + "   \"encoding\": \"nkeBatchv1.0\"," + "      \"network\": {" + "         \"lora\": {" + "            \"port\": 6,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + "         }" + "      }" + "   }," + "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," 
                + "   \"model\": \"lora_v0\"," + "   \"value\": {" 
                + "   \"payload\": \"1001c0c33949824610d0ce13f800\"" + "   },"
                + "   \"timestamp\": \"2019-02-07T11:17:25.000Z\"" + ",   \"tags\": [ "
                		+ "\"BATCH_temperatures_°C_L0_R1_T7_D100\", "
                		+ "\"BATCH_batteryLevels_V_L2_R1_T6\","
                		+ "\"BATCH_tagsize_2\" ]" + "}";
    	
        String input = "1001c0c33949824610d0ce13f800";
        
        NKEBatch frame = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEBatch.class);
        assertThat(frame.getBatchCounter()).isEqualTo(1);
		assertThat(frame.getBatchTimestamp()).isEqualTo("2019-02-07T11:17:25.000Z");
		assertThat(frame.getTemperatures().getUnit()).isEqualTo("°C");
		assertThat(frame.getTemperatures().getValues().size()).isEqualTo(2);
		assertThat(frame.getTemperatures().getValues().get(0).getTimestamp()).isEqualTo("2019-02-07T10:47:07.000Z");
		assertThat(frame.getTemperatures().getValues().get(0).getValue()).isEqualTo(23.3f);
		assertThat(frame.getTemperatures().getValues().get(1).getTimestamp()).isEqualTo("2019-02-07T11:17:07.000Z");
		assertThat(frame.getTemperatures().getValues().get(1).getValue()).isEqualTo(23.3f);
		assertThat(frame.getBatteryLevels().getUnit()).isEqualTo("V");
		assertThat(frame.getBatteryLevels().getValues().size()).isEqualTo(0);
		
    }

    
    @Test
    public void should_decode_5070053_payload_with_fields_tag() throws JsDecodingException {  
    		
    	String inputDataMessage = "{" + " \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\"," 
    			+ "   \"connector\": \"lora\","
                + "   \"encoding\": \"nke_watteco_v2.4\"," 
    			+ "   \"network\": {" + " \"lora\": {" + " \"port\": 6,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + " } }  }," 
    			+ "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\"," 
                + "   \"value\": {" + " \"payload\": \"2002c06867cd81a2b44f204e02da79820b68e709423b\"" + "   },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"BATCH_temperatures_°C_L0_R10_T7_D100\", "
                + "\"BATCH_relativeHumidities_%RH_L1_R100_T6_D100\","
                + "\"BATCH_batteryLevels_mV_L2_R1_T6\", "
                + "\"BATCH_openCaseAlarms_NoUnit_L3_R1_T1\","
                + "\"BATCH_tagsize_2\" ]" + "}";    
    	
        String input = "2002c06867cd81a2b44f204e02da79820b68e709423b";              
        NKEBatch frame = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEBatch.class);
        assertThat(frame.getBatchCounter()).isEqualTo(2);
        assertThat(frame.getBatchTimestamp()).isEqualTo("2017-11-07T17:17:25.000Z");
        assertThat(frame.getTemperatures().getUnit()).isEqualTo("°C");
        assertThat(frame.getTemperatures().getValues().size()).isEqualTo(2);
        assertThat(frame.getTemperatures().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T16:44:19.000Z");
        assertThat(frame.getTemperatures().getValues().get(0).getValue()).isEqualTo(19.30f);
        assertThat(frame.getTemperatures().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T17:14:19.000Z");
        assertThat(frame.getTemperatures().getValues().get(1).getValue()).isEqualTo(19.1f);
        assertThat(frame.getRelativeHumidities().getUnit()).isEqualTo("%RH");
        assertThat(frame.getRelativeHumidities().getValues().size()).isEqualTo(2);
        assertThat(frame.getRelativeHumidities().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T16:44:19.000Z");
        assertThat(frame.getRelativeHumidities().getValues().get(0).getValue()).isEqualTo(25.00f);
        assertThat(frame.getRelativeHumidities().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T17:14:19.000Z");
        assertThat(frame.getRelativeHumidities().getValues().get(1).getValue()).isEqualTo(32.00f);
        assertThat(frame.getBatteryLevels().getValues().size()).isEqualTo(0);
        assertThat(frame.getBatteryLevels().getUnit()).isEqualTo("mV");
        assertThat(frame.getOpenCaseAlarms().getValues().size()).isEqualTo(0);      
    }
 
    @PayloadDescription(name = "BATCH_5070053_DEFAULT_PROFILE for device 50-70-053", description = "Batch payload sent by a device 50-70-053 associated to the tag BATCH_5070053_DEFAULT_PROFILE. "
    		+ "\nThe BATCH_5070053_DEFAULT_PROFILE tag is replaced by the decoder by these following field batch tags:\n"
    		+ "BATCH_tagsize_2, \n "
    		+ "BATCH_temperatures_°C_L0_R10_T7_D100, \n"
            + "BATCH_relativeHumidities_%RH_L1_R100_T6_D100, \n"
            + "BATCH_batteryLevels_mV_L2_R1_T6, \n"
            + "BATCH_openCaseAlarms_NoUnit_L3_R1_T1 ")
    @Test
    public void should_decode_5070053_payload_with_tagBatchProfile() throws JsDecodingException {
    	
    	String inputDataMessage = "{" + " \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\"," 
    			+ "   \"connector\": \"lora\","
                + "   \"encoding\": \"nke_watteco_v2.4\"," 
    			+ "   \"network\": {" + " \"lora\": {" + " \"port\": 6,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + " } }  }," 
    			+ "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\"," 
                + "   \"value\": {" + " \"payload\": \"2002c06867cd81a2b44f204e02da79820b68e709423b\"" + "   },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"BATCH_5070053_DEFAULT_PROFILE\"]}";         
       
        String input = "2002c06867cd81a2b44f204e02da79820b68e709423b";              
        NKEBatch frame = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEBatch.class);
        assertThat(frame.getBatchCounter()).isEqualTo(2);
        assertThat(frame.getBatchTimestamp()).isEqualTo("2017-11-07T17:17:25.000Z");
        assertThat(frame.getTemperatures().getUnit()).isEqualTo("°C");
        assertThat(frame.getTemperatures().getValues().size()).isEqualTo(2);
        assertThat(frame.getTemperatures().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T16:44:19.000Z");
        assertThat(frame.getTemperatures().getValues().get(0).getValue()).isEqualTo(19.30f);
        assertThat(frame.getTemperatures().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T17:14:19.000Z");
        assertThat(frame.getTemperatures().getValues().get(1).getValue()).isEqualTo(19.1f);
        assertThat(frame.getRelativeHumidities().getUnit()).isEqualTo("%RH");
        assertThat(frame.getRelativeHumidities().getValues().size()).isEqualTo(2);
        assertThat(frame.getRelativeHumidities().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T16:44:19.000Z");
        assertThat(frame.getRelativeHumidities().getValues().get(0).getValue()).isEqualTo(25.00f);
        assertThat(frame.getRelativeHumidities().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T17:14:19.000Z");
        assertThat(frame.getRelativeHumidities().getValues().get(1).getValue()).isEqualTo(32.00f);
        assertThat(frame.getBatteryLevels().getValues().size()).isEqualTo(0);
        assertThat(frame.getBatteryLevels().getUnit()).isEqualTo("mV");
        assertThat(frame.getOpenCaseAlarms().getValues().size()).isEqualTo(0);      
    }
    

    
    
    @Test
    public void should_decode_5070085_payload_with_fields_tag() throws JsDecodingException {  
    		
    	String inputDataMessage = "{" + " \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\"," 
    			+ "   \"connector\": \"lora\","
                + "   \"encoding\": \"nke_watteco_v2.4\"," 
    			+ "   \"network\": {" + " \"lora\": {" + " \"port\": 6,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + " } }  }," 
    			+ "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\"," 
                + "   \"value\": {" + " \"payload\": \"2000c0c476fa8000b86d38b40004d5a953571d013c\"" + "   },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"BATCH_tagsize_2\", "
                + "\"BATCH_temperatures_°C_L0_R10_T7_D100\","
                + "\"BATCH_batteryLevels_mV_L2_R1_T6\", "
                + "\"BATCH_openCaseAlarms_NoUnit_L3_R1_T1\" ]" + "}";    
   	
    	
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

    
    @Test
    @PayloadDescription(name = "BATCH_5070085_DEFAULT_PROFILE for device 50-70-085", description = "Batch payload sent by a device 50-70-085 associated to the tag BATCH_5070085_DEFAULT_PROFILE. "
    		+ "\nThe BATCH_5070085_DEFAULT_PROFILE tag is replaced by the decoder by these following field batch tags:\n"
            + "BATCH_tagsize_2, \n"
    		+ "BATCH_temperatures_°C_L0_R10_T7_D100, \n"
    		+ "BATCH_batteryLevels_mV_L2_R1_T6, \n"
    		+ "BATCH_openCaseAlarms_NoUnit_L3_R1_T1")
    public void should_decode_5070085_payload_with_tagBatchProfile() throws JsDecodingException {
    	
    	String inputDataMessage = "{" + " \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\"," 
    			+ "   \"connector\": \"lora\","
                + "   \"encoding\": \"nke_watteco_v2.4\"," 
    			+ "   \"network\": {" + " \"lora\": {" + " \"port\": 6,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + " } }  }," 
    			+ "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\"," 
                + "   \"value\": {" + " \"payload\": \"2000c0c476fa8000b86d38b40004d5a953571d013c\"" + "   },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"BATCH_5070085_DEFAULT_PROFILE\"]}";         
       
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

    @PayloadDescription(name = "BATCH_5070108_DEFAULT_PROFILE for device 50-70-108", description = "Batch payload sent by a device 50-70-108 associated to the tag BATCH_5070108_DEFAULT_PROFILE. "
    		+ "\nThe BATCH_5070108_DEFAULT_PROFILE tag is replaced by the decoder by these following field batch tags:\n"
    		+ "BATCH_tagsize_2, \n"
    		+ "BATCH_OpenClose_NoUnit_L0_R1_T1, \n"
            + "BATCH_batteryLevels_mV_L1_R100_T6")
    @Test
    public void should_decode_5070108_payload_with_tagBatchProfile() throws JsDecodingException {
    	
    	String inputDataMessage = "{" + " \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\"," 
    			+ "   \"connector\": \"lora\","
                + "   \"encoding\": \"nke_watteco_v2.4\"," 
    			+ "   \"network\": {" + " \"lora\": {" + " \"port\": 6,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + " } }  }," 
    			+ "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\"," 
                + "   \"value\": {" + " \"payload\": \"1005018aecb420a01d3a81769828\"" + "   },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"BATCH_5070108_DEFAULT_PROFILE\"]}";      
        String input = "1005018aecb420a01d3a81769828";              
        NKEBatch frame = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEBatch.class);

        assertThat(frame.getBatchCounter()).isEqualTo(5);
        assertThat(frame.getBatchTimestamp()).isEqualTo("2017-11-07T17:17:25.000Z");
        assertThat(frame.getOpenClose().getValues().size()).isEqualTo(2);
        assertThat(frame.getOpenClose().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T09:18:51.000Z");
        assertThat(frame.getOpenClose().getValues().get(0).getValue()).isEqualTo(0);
        assertThat(frame.getOpenClose().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T13:39:10.000Z");
        assertThat(frame.getOpenClose().getValues().get(1).getValue()).isEqualTo(1);      
        assertThat(frame.getBatteryLevels().getUnit()).isEqualTo("mV");
        assertThat(frame.getBatteryLevels().getValues().size()).isEqualTo(0);

    }
    
    @Test
    public void should_decode_5070160_payload_with_fields_tag() throws JsDecodingException {  
    		
    	String inputDataMessage = "{" + " \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\"," 
    			+ "   \"connector\": \"lora\","
                + "   \"encoding\": \"nke_watteco_v2.4\"," 
    			+ "   \"network\": {" + " \"lora\": {" + " \"port\": 6,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + " } }  }," 
    			+ "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\"," 
                + "   \"value\": {" + " \"payload\": \"4203000278db00000000117b00000080903d00000040d80e0ac6812e0820c08e08b02302ec084892481249124316\"" + "   },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"BATCH_tagsize_4\", "
                + "\"BATCH_index1_/_L0_R1_T10_D1\","
                + "\"BATCH_index2_/_L1_R1_T10_D1\","
                + "\"BATCH_index3_/_L2_R1_T10_D1\","
                + "\"BATCH_batteryLevels_mV_L6_R100_T6\" ]" + "}";	
    	
        String input = "4203000278db00000000117b00000080903d00000040d80e0ac6812e0820c08e08b02302ec084892481249124316";              
        NKEBatch frame = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEBatch.class);
        assertThat(frame.getBatchCounter()).isEqualTo(3);
        assertThat(frame.getBatchTimestamp()).isEqualTo("2017-11-07T17:17:25.000Z");
        assertThat(frame.getIndex1().getUnit()).isEqualTo("/");
        assertThat(frame.getIndex1().getValues().size()).isEqualTo(4);
        assertThat(frame.getIndex1().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T14:17:24.000Z");
        assertThat(frame.getIndex1().getValues().get(0).getValue()).isEqualTo(0);
        assertThat(frame.getIndex1().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T15:17:24.000Z");
        assertThat(frame.getIndex1().getValues().get(1).getValue()).isEqualTo(0);
        assertThat(frame.getIndex1().getValues().get(2).getTimestamp()).isEqualTo("2017-11-07T16:17:24.000Z");
        assertThat(frame.getIndex1().getValues().get(2).getValue()).isEqualTo(0);
        assertThat(frame.getIndex1().getValues().get(3).getTimestamp()).isEqualTo("2017-11-07T17:17:24.000Z");
        assertThat(frame.getIndex1().getValues().get(3).getValue()).isEqualTo(0);        
        assertThat(frame.getIndex2().getValues().size()).isEqualTo(4);
        assertThat(frame.getIndex2().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T14:17:24.000Z");
        assertThat(frame.getIndex2().getValues().get(0).getValue()).isEqualTo(0);
        assertThat(frame.getIndex2().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T15:17:24.000Z");
        assertThat(frame.getIndex2().getValues().get(1).getValue()).isEqualTo(0);
        assertThat(frame.getIndex2().getValues().get(2).getTimestamp()).isEqualTo("2017-11-07T16:17:24.000Z");
        assertThat(frame.getIndex2().getValues().get(2).getValue()).isEqualTo(0);
        assertThat(frame.getIndex2().getValues().get(3).getTimestamp()).isEqualTo("2017-11-07T17:17:24.000Z");
        assertThat(frame.getIndex2().getValues().get(3).getValue()).isEqualTo(0);
        assertThat(frame.getIndex3().getValues().size()).isEqualTo(4);
        assertThat(frame.getIndex3().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T14:17:24.000Z");
        assertThat(frame.getIndex3().getValues().get(0).getValue()).isEqualTo(0);
        assertThat(frame.getIndex3().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T15:17:24.000Z");
        assertThat(frame.getIndex3().getValues().get(1).getValue()).isEqualTo(0);
        assertThat(frame.getIndex3().getValues().get(2).getTimestamp()).isEqualTo("2017-11-07T16:17:24.000Z");
        assertThat(frame.getIndex3().getValues().get(2).getValue()).isEqualTo(0);
        assertThat(frame.getIndex3().getValues().get(3).getTimestamp()).isEqualTo("2017-11-07T17:17:24.000Z");
        assertThat(frame.getIndex3().getValues().get(3).getValue()).isEqualTo(0);     
        assertThat(frame.getBatteryLevels().getUnit()).isEqualTo("mV");
        assertThat(frame.getBatteryLevels().getValues().size()).isEqualTo(1);
        assertThat(frame.getBatteryLevels().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T17:17:23.000Z");
        assertThat(frame.getBatteryLevels().getValues().get(0).getValue()).isEqualTo(3700);
    }
     
    @PayloadDescription(name = "BATCH_5070160_DEFAULT_PROFILE for device 50-70-160", description = "Batch payload sent by a device 50-70-160 associated to the tag BATCH_5070160_DEFAULT_PROFILE. "
    		+ "\nThe BATCH_5070160_DEFAULT_PROFILE tag is replaced by the decoder by these following field batch tags:\n"
    		+ "BATCH_tagsize_4, \n"
    		+ "BATCH_index1_/_L0_R1_T10_D1, \n"
    		+ "BATCH_index2_/_L1_R1_T10_D1, \n"
    		+ "BATCH_index3_/_L2_R1_T10_D1, \n"
            + "BATCH_batteryLevels_mV_L6_R100_T6")
    @Test
    public void should_decode_5070160_payload_with_tagBatchProfile() throws JsDecodingException {
    	
    	String inputDataMessage = "{" + " \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\"," 
    			+ "   \"connector\": \"lora\","
                + "   \"encoding\": \"nke_watteco_v2.4\"," 
    			+ "   \"network\": {" + " \"lora\": {" + " \"port\": 6,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + " } }  }," 
    			+ "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\"," 
                + "   \"value\": {" + " \"payload\": \"4203000278db00000000117b00000080903d00000040d80e0ac6812e0820c08e08b02302ec084892481249124316\"" + "   },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"BATCH_5070160_DEFAULT_PROFILE\"]}";         
       
        String input = "4203000278db00000000117b00000080903d00000040d80e0ac6812e0820c08e08b02302ec084892481249124316";              
        NKEBatch frame = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEBatch.class);
        assertThat(frame.getBatchCounter()).isEqualTo(3);
        assertThat(frame.getBatchTimestamp()).isEqualTo("2017-11-07T17:17:25.000Z");
        assertThat(frame.getIndex1().getUnit()).isEqualTo("/");
        assertThat(frame.getIndex1().getValues().size()).isEqualTo(4);
        assertThat(frame.getIndex1().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T14:17:24.000Z");
        assertThat(frame.getIndex1().getValues().get(0).getValue()).isEqualTo(0);
        assertThat(frame.getIndex1().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T15:17:24.000Z");
        assertThat(frame.getIndex1().getValues().get(1).getValue()).isEqualTo(0);
        assertThat(frame.getIndex1().getValues().get(2).getTimestamp()).isEqualTo("2017-11-07T16:17:24.000Z");
        assertThat(frame.getIndex1().getValues().get(2).getValue()).isEqualTo(0);
        assertThat(frame.getIndex1().getValues().get(3).getTimestamp()).isEqualTo("2017-11-07T17:17:24.000Z");
        assertThat(frame.getIndex1().getValues().get(3).getValue()).isEqualTo(0);        
        assertThat(frame.getIndex2().getValues().size()).isEqualTo(4);
        assertThat(frame.getIndex2().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T14:17:24.000Z");
        assertThat(frame.getIndex2().getValues().get(0).getValue()).isEqualTo(0);
        assertThat(frame.getIndex2().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T15:17:24.000Z");
        assertThat(frame.getIndex2().getValues().get(1).getValue()).isEqualTo(0);
        assertThat(frame.getIndex2().getValues().get(2).getTimestamp()).isEqualTo("2017-11-07T16:17:24.000Z");
        assertThat(frame.getIndex2().getValues().get(2).getValue()).isEqualTo(0);
        assertThat(frame.getIndex2().getValues().get(3).getTimestamp()).isEqualTo("2017-11-07T17:17:24.000Z");
        assertThat(frame.getIndex2().getValues().get(3).getValue()).isEqualTo(0);
        assertThat(frame.getIndex3().getValues().size()).isEqualTo(4);
        assertThat(frame.getIndex3().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T14:17:24.000Z");
        assertThat(frame.getIndex3().getValues().get(0).getValue()).isEqualTo(0);
        assertThat(frame.getIndex3().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T15:17:24.000Z");
        assertThat(frame.getIndex3().getValues().get(1).getValue()).isEqualTo(0);
        assertThat(frame.getIndex3().getValues().get(2).getTimestamp()).isEqualTo("2017-11-07T16:17:24.000Z");
        assertThat(frame.getIndex3().getValues().get(2).getValue()).isEqualTo(0);
        assertThat(frame.getIndex3().getValues().get(3).getTimestamp()).isEqualTo("2017-11-07T17:17:24.000Z");
        assertThat(frame.getIndex3().getValues().get(3).getValue()).isEqualTo(0);     
        assertThat(frame.getBatteryLevels().getUnit()).isEqualTo("mV");
        assertThat(frame.getBatteryLevels().getValues().size()).isEqualTo(1);
        assertThat(frame.getBatteryLevels().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T17:17:23.000Z");
        assertThat(frame.getBatteryLevels().getValues().get(0).getValue()).isEqualTo(3700);
    }   
    
    @PayloadDescription(name = "BATCH_5070161_DEFAULT_PROFILE for device 50-70-161", description = "Batch payload sent by a device 50-70-161 associated to the tag BATCH_5070161_DEFAULT_PROFILE. "
    		+ "\nThe BATCH_5070161_DEFAULT_PROFILE tag is replaced by the decoder by these following field batch tags:\n"
    		+ "BATCH_tagsize_4, \n"
    		+ "BATCH_index1_/_L0_R1_T10_D1, \n"
    		+ "BATCH_index2_/_L1_R1_T10_D1, \n"
    		+ "BATCH_index3_/_L2_R1_T10_D1, \n"
            + "BATCH_batteryLevels_mV_L6_R100_T6")
    @Test
    public void should_decode_5070161_payload_with_tagBatchProfile() throws JsDecodingException {
    	
    	String inputDataMessage = "{" + " \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\"," 
    			+ "   \"connector\": \"lora\","
                + "   \"encoding\": \"nke_watteco_v2.4\"," 
    			+ "   \"network\": {" + " \"lora\": {" + " \"port\": 6,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + " } }  }," 
    			+ "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\"," 
                + "   \"value\": {" + " \"payload\": \"4203000278db00000000117b00000080903d00000040d80e0ac6812e0820c08e08b02302ec084892481249124316\"" + "   },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"BATCH_5070161_DEFAULT_PROFILE\"]}";         
       
        String input = "4203000278db00000000117b00000080903d00000040d80e0ac6812e0820c08e08b02302ec084892481249124316";              
        NKEBatch frame = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEBatch.class);
        assertThat(frame.getBatchCounter()).isEqualTo(3);
        assertThat(frame.getBatchTimestamp()).isEqualTo("2017-11-07T17:17:25.000Z");
        assertThat(frame.getIndex1().getUnit()).isEqualTo("/");
        assertThat(frame.getIndex1().getValues().size()).isEqualTo(4);
        assertThat(frame.getIndex1().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T14:17:24.000Z");
        assertThat(frame.getIndex1().getValues().get(0).getValue()).isEqualTo(0);
        assertThat(frame.getIndex1().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T15:17:24.000Z");
        assertThat(frame.getIndex1().getValues().get(1).getValue()).isEqualTo(0);
        assertThat(frame.getIndex1().getValues().get(2).getTimestamp()).isEqualTo("2017-11-07T16:17:24.000Z");
        assertThat(frame.getIndex1().getValues().get(2).getValue()).isEqualTo(0);
        assertThat(frame.getIndex1().getValues().get(3).getTimestamp()).isEqualTo("2017-11-07T17:17:24.000Z");
        assertThat(frame.getIndex1().getValues().get(3).getValue()).isEqualTo(0);        
        assertThat(frame.getIndex2().getValues().size()).isEqualTo(4);
        assertThat(frame.getIndex2().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T14:17:24.000Z");
        assertThat(frame.getIndex2().getValues().get(0).getValue()).isEqualTo(0);
        assertThat(frame.getIndex2().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T15:17:24.000Z");
        assertThat(frame.getIndex2().getValues().get(1).getValue()).isEqualTo(0);
        assertThat(frame.getIndex2().getValues().get(2).getTimestamp()).isEqualTo("2017-11-07T16:17:24.000Z");
        assertThat(frame.getIndex2().getValues().get(2).getValue()).isEqualTo(0);
        assertThat(frame.getIndex2().getValues().get(3).getTimestamp()).isEqualTo("2017-11-07T17:17:24.000Z");
        assertThat(frame.getIndex2().getValues().get(3).getValue()).isEqualTo(0);
        assertThat(frame.getIndex3().getValues().size()).isEqualTo(4);
        assertThat(frame.getIndex3().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T14:17:24.000Z");
        assertThat(frame.getIndex3().getValues().get(0).getValue()).isEqualTo(0);
        assertThat(frame.getIndex3().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T15:17:24.000Z");
        assertThat(frame.getIndex3().getValues().get(1).getValue()).isEqualTo(0);
        assertThat(frame.getIndex3().getValues().get(2).getTimestamp()).isEqualTo("2017-11-07T16:17:24.000Z");
        assertThat(frame.getIndex3().getValues().get(2).getValue()).isEqualTo(0);
        assertThat(frame.getIndex3().getValues().get(3).getTimestamp()).isEqualTo("2017-11-07T17:17:24.000Z");
        assertThat(frame.getIndex3().getValues().get(3).getValue()).isEqualTo(0);     
        assertThat(frame.getBatteryLevels().getUnit()).isEqualTo("mV");
        assertThat(frame.getBatteryLevels().getValues().size()).isEqualTo(1);
        assertThat(frame.getBatteryLevels().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T17:17:23.000Z");
        assertThat(frame.getBatteryLevels().getValues().get(0).getValue()).isEqualTo(3700);
    }   

    
    
    @PayloadDescription(name = "BATCH_5070162_DEFAULT_PROFILE for device 50-70-162", description = "Batch payload sent by a device 50-70-162 associated to the tag BATCH_5070162_DEFAULT_PROFILE. "
    		+ "\nThe BATCH_5070162_DEFAULT_PROFILE tag is replaced by the decoder by these following field batch tags:\n"
    		+ "BATCH_tagsize_2, \n"
    		+ "BATCH_temperatures_°C_L0_R10_T7_D100, \n"
            + "BATCH_relativeHumidities_%RH_L1_R100_T6_D100, \n"
            + "BATCH_batteryLevels_mV_L2_R1_T6, \n"
            + "BATCH_openCaseAlarms_NoUnit_L3_R1_T1")
    @Test
    public void should_decode_5070162_payload_with_tagBatchProfile() throws JsDecodingException {
    	
    	String inputDataMessage = "{" + " \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\"," 
    			+ "   \"connector\": \"lora\","
                + "   \"encoding\": \"nke_watteco_v2.4\"," 
    			+ "   \"network\": {" + " \"lora\": {" + " \"port\": 6,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + " } }  }," 
    			+ "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\"," 
                + "   \"value\": {" + " \"payload\": \"2002c06867cd81a2b44f204e02da79820b68e709423b\"" + "   },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"BATCH_5070162_DEFAULT_PROFILE\"]}";         
       
        String input = "2002c06867cd81a2b44f204e02da79820b68e709423b";              
        NKEBatch frame = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEBatch.class);
        assertThat(frame.getBatchCounter()).isEqualTo(2);
        assertThat(frame.getBatchTimestamp()).isEqualTo("2017-11-07T17:17:25.000Z");
        assertThat(frame.getTemperatures().getUnit()).isEqualTo("°C");
        assertThat(frame.getTemperatures().getValues().size()).isEqualTo(2);
        assertThat(frame.getTemperatures().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T16:44:19.000Z");
        assertThat(frame.getTemperatures().getValues().get(0).getValue()).isEqualTo(19.30f);
        assertThat(frame.getTemperatures().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T17:14:19.000Z");
        assertThat(frame.getTemperatures().getValues().get(1).getValue()).isEqualTo(19.1f);
        assertThat(frame.getRelativeHumidities().getUnit()).isEqualTo("%RH");
        assertThat(frame.getRelativeHumidities().getValues().size()).isEqualTo(2);
        assertThat(frame.getRelativeHumidities().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T16:44:19.000Z");
        assertThat(frame.getRelativeHumidities().getValues().get(0).getValue()).isEqualTo(25.00f);
        assertThat(frame.getRelativeHumidities().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T17:14:19.000Z");
        assertThat(frame.getRelativeHumidities().getValues().get(1).getValue()).isEqualTo(32.00f);
        assertThat(frame.getBatteryLevels().getValues().size()).isEqualTo(0);
        assertThat(frame.getBatteryLevels().getUnit()).isEqualTo("mV");
        assertThat(frame.getOpenCaseAlarms().getValues().size()).isEqualTo(0);      
    }
    
    
    @PayloadDescription(name = "BATCH_5070162_DEFAULT_PROFILE for device 50-70-162", description = "Batch payload sent by a device 50-70-162 associated to the tag BATCH_5070162_DEFAULT_PROFILE. "
    		+ "\nThe BATCH_5070162_DEFAULT_PROFILE tag is replaced by the decoder by these following field batch tags:\n"
    		+ "BATCH_tagsize_2, \n"
    		+ "BATCH_temperatures_°C_L0_R10_T7_D100, \n"
            + "BATCH_relativeHumidities_%RH_L1_R100_T6_D100, \n"
            + "BATCH_batteryLevels_mV_L2_R1_T6, \n"
            + "BATCH_openCaseAlarms_NoUnit_L3_R1_T1")
    
    @Test
    public void should_decode_5070162_payload2_with_tagBatchProfile() throws JsDecodingException {
    	
    	String inputDataMessage = "{" + " \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\"," 
    			+ "   \"connector\": \"lora\","
                + "   \"encoding\": \"nke_watteco_v2.4\"," 
    			+ "   \"network\": {" + " \"lora\": {" + " \"port\": 6,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + " } }  }," 
    			+ "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\"," 
                + "   \"value\": {" + " \"payload\": \"3007c088582b0113d87268813c116280a0fad655771d01027802\"" + "   },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"BATCH_5070162_DEFAULT_PROFILE\"]}";         
       
        String input = "3007c088582b0113d87268813c116280a0fad655771d01027802";              
        NKEBatch frame = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEBatch.class);
        assertThat(frame.getBatchCounter()).isEqualTo(7);
        assertThat(frame.getBatchTimestamp()).isEqualTo("2017-11-07T17:17:25.000Z");
        assertThat(frame.getTemperatures().getUnit()).isEqualTo("°C");
        assertThat(frame.getTemperatures().getValues().size()).isEqualTo(5);
        assertThat(frame.getTemperatures().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T17:13:01.000Z");
        assertThat(frame.getTemperatures().getValues().get(0).getValue()).isEqualTo(11.0f);
        assertThat(frame.getTemperatures().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T17:14:01.000Z");
        assertThat(frame.getTemperatures().getValues().get(1).getValue()).isEqualTo(11.2f);
        assertThat(frame.getTemperatures().getValues().get(2).getTimestamp()).isEqualTo("2017-11-07T17:15:01.000Z");
        assertThat(frame.getTemperatures().getValues().get(2).getValue()).isEqualTo(11.1f);
        assertThat(frame.getTemperatures().getValues().get(3).getTimestamp()).isEqualTo("2017-11-07T17:16:01.000Z");
        assertThat(frame.getTemperatures().getValues().get(3).getValue()).isEqualTo(11.2f);
        assertThat(frame.getTemperatures().getValues().get(4).getTimestamp()).isEqualTo("2017-11-07T17:17:01.000Z");
        assertThat(frame.getTemperatures().getValues().get(4).getValue()).isEqualTo(11.2f);
        assertThat(frame.getRelativeHumidities().getUnit()).isEqualTo("%RH");
        assertThat(frame.getRelativeHumidities().getValues().size()).isEqualTo(1);
        assertThat(frame.getRelativeHumidities().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T17:13:53.000Z");
        assertThat(frame.getRelativeHumidities().getValues().get(0).getValue()).isEqualTo(89.0f);       
        assertThat(frame.getBatteryLevels().getValues().size()).isEqualTo(1);
        assertThat(frame.getBatteryLevels().getUnit()).isEqualTo("mV");
        assertThat(frame.getBatteryLevels().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T17:13:43.000Z");
        assertThat(frame.getBatteryLevels().getValues().get(0).getValue()).isEqualTo(3629);              
        assertThat(frame.getOpenCaseAlarms().getValues().size()).isEqualTo(0);      
    } 
    
    
    @Test
    public void should_decode_5070163_payload_with_fields_tag() throws JsDecodingException {  
    		
    	String inputDataMessage = "{" + " \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\"," 
    			+ "   \"connector\": \"lora\","
                + "   \"encoding\": \"nke_watteco_v2.4\"," 
    			+ "   \"network\": {" + " \"lora\": {" + " \"port\": 6,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + " } }  }," 
    			+ "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\"," 
                + "   \"value\": {" + " \"payload\": \"220200008f426cfdc8de823c0c20005901c80a405600b2029085244992542a7b\"" + "   },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"BATCH_tagsize_3\", "
                + "\"BATCH_temperatures-1_°C_L0_R10_T7_D100\","
                + "\"BATCH_temperatures-2_°C_L1_R10_T7_D100\","
                + "\"BATCH_batteryLevels_mV_L5_R100_T6\" ]" + "}";
    	
        String input = "220200008f426cfdc8de823c0c20005901c80a405600b2029085244992542a7b";              
        NKEBatch frame = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEBatch.class);
        assertThat(frame.getBatchCounter()).isEqualTo(2);
        assertThat(frame.getBatchTimestamp()).isEqualTo("2017-11-07T17:17:25.000Z");
        assertThat(frame.getBatteryLevels().getUnit()).isEqualTo("mV");
        assertThat(frame.getBatteryLevels().getValues().size()).isEqualTo(0);
    }
 /*   
    {"batchCounter":2,"batchTimestamp":"2017-11-07T17:17:25.000Z",
    	"temperatures-1":{"unit":"°C","values":[{"timestamp":"2017-11-07T16:27:25.000Z","value":-99.9},
    	                                        {"timestamp":"2017-11-07T16:37:25.000Z","value":-99.9},
    	                                        {"timestamp":"2017-11-07T16:47:25.000Z","value":-99.9},
    	                                        {"timestamp":"2017-11-07T16:57:25.000Z","value":-99.9},
    	                                        {"timestamp":"2017-11-07T17:07:25.000Z","value":-99.9},
    	                                        {"timestamp":"2017-11-07T17:17:25.000Z","value":-99.9}]},
    	"temperatures-2":{"unit":"°C","values":[{"timestamp":"2017-11-07T16:27:25.000Z","value":58.6},
    	                                        {"timestamp":"2017-11-07T16:37:25.000Z","value":58.6},
    	                                        {"timestamp":"2017-11-07T16:47:25.000Z","value":58.7},
    	                                        {"timestamp":"2017-11-07T16:57:25.000Z","value":58.7},
    	                                        {"timestamp":"2017-11-07T17:07:25.000Z","value":58.8},
    	                                        {"timestamp":"2017-11-07T17:17:25.000Z","value":58.8}]},
    	"batteryLevels":{"unit":"mV","values":[]}}
*/
    @PayloadDescription(name = "BATCH_5070163_DEFAULT_PROFILE for device 50-70-163", description = "Batch payload sent by a device 50-70-163 associated to the tag BATCH_5070163_DEFAULT_PROFILE. "
    		+ "\nThe BATCH_5070163_DEFAULT_PROFILE tag is replaced by the decoder by these following field batch tags:\n"
    		+ "BATCH_tagsize_3, \n"
    		+ "BATCH_temperatures-1_°C_L0_R10_T7_D100, \n"
    		+ "BATCH_temperatures-2_°C_L1_R10_T7_D100, \n"
            + "BATCH_batteryLevels_mV_L5_R100_T6")
    @Test
    public void should_decode_5070163_payload_with_tagBatchProfile() throws JsDecodingException {
    	
    	String inputDataMessage = "{" + " \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\"," 
    			+ "   \"connector\": \"lora\","
                + "   \"encoding\": \"nke_watteco_v2.4\"," 
    			+ "   \"network\": {" + " \"lora\": {" + " \"port\": 6,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + " } }  }," 
    			+ "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\"," 
                + "   \"value\": {" + " \"payload\": \"220200008f426cfdc8de823c0c20005901c80a405600b2029085244992542a7b\"" + "   },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"BATCH_5070163_DEFAULT_PROFILE\"]}";         
       
        String input = "220200008f426cfdc8de823c0c20005901c80a405600b2029085244992542a7b";              
        NKEBatch frame = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEBatch.class);
        assertThat(frame.getBatchCounter()).isEqualTo(2);
        assertThat(frame.getBatchTimestamp()).isEqualTo("2017-11-07T17:17:25.000Z");
        assertThat(frame.getBatteryLevels().getUnit()).isEqualTo("mV");
        assertThat(frame.getBatteryLevels().getValues().size()).isEqualTo(0);
    }
 /*   
    {"batchCounter":2,"batchTimestamp":"2017-11-07T17:17:25.000Z",
    	"temperatures-1":{"unit":"°C","values":[{"timestamp":"2017-11-07T16:27:25.000Z","value":-99.9},
    	                                        {"timestamp":"2017-11-07T16:37:25.000Z","value":-99.9},
    	                                        {"timestamp":"2017-11-07T16:47:25.000Z","value":-99.9},
    	                                        {"timestamp":"2017-11-07T16:57:25.000Z","value":-99.9},
    	                                        {"timestamp":"2017-11-07T17:07:25.000Z","value":-99.9},
    	                                        {"timestamp":"2017-11-07T17:17:25.000Z","value":-99.9}]},
    	"temperatures-2":{"unit":"°C","values":[{"timestamp":"2017-11-07T16:27:25.000Z","value":58.6},
    	                                        {"timestamp":"2017-11-07T16:37:25.000Z","value":58.6},
    	                                        {"timestamp":"2017-11-07T16:47:25.000Z","value":58.7},
    	                                        {"timestamp":"2017-11-07T16:57:25.000Z","value":58.7},
    	                                        {"timestamp":"2017-11-07T17:07:25.000Z","value":58.8},
    	                                        {"timestamp":"2017-11-07T17:17:25.000Z","value":58.8}]},
    	"batteryLevels":{"unit":"mV","values":[]}}
*/

 
       
    @Test
    public void should_decode_5070164_payload_with_fields_tag() throws JsDecodingException {  
    		
    	String inputDataMessage = "{" + " \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\"," 
    			+ "   \"connector\": \"lora\","
                + "   \"encoding\": \"nke_watteco_v2.4\"," 
    			+ "   \"network\": {" + " \"lora\": {" + " \"port\": 6,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + " } }  }," 
    			+ "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\"," 
                + "   \"value\": {" + " \"payload\": \"100500813d1901134041bb6579b72cef96e5ddb2baddb26600\"" + "   },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"BATCH_tagsize_3\", "
                + "\"BATCH_temperatures_°C_L0_R10_T7_D100\","
                + "\"BATCH_batteryLevels_mV_L5_R100_T6\" ]" + "}";
    	
        String input = "100500813d1901134041bb6579b72cef96e5ddb2baddb26600";              
        NKEBatch frame = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEBatch.class);
        assertThat(frame.getBatchCounter()).isEqualTo(5);
        assertThat(frame.getBatchTimestamp()).isEqualTo("2017-11-07T17:17:25.000Z");
        assertThat(frame.getTemperatures().getUnit()).isEqualTo("°C");
        assertThat(frame.getTemperatures().getValues().size()).isEqualTo(6);
        assertThat(frame.getTemperatures().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T16:27:22.000Z");
        assertThat(frame.getTemperatures().getValues().get(0).getValue()).isEqualTo(5.5f);
        assertThat(frame.getTemperatures().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T16:37:22.000Z");
        assertThat(frame.getTemperatures().getValues().get(1).getValue()).isEqualTo(5.6f);
        assertThat(frame.getTemperatures().getValues().get(2).getTimestamp()).isEqualTo("2017-11-07T16:47:22.000Z");
        assertThat(frame.getTemperatures().getValues().get(2).getValue()).isEqualTo(5.7f);
        assertThat(frame.getTemperatures().getValues().get(3).getTimestamp()).isEqualTo("2017-11-07T16:57:22.000Z");
        assertThat(frame.getTemperatures().getValues().get(3).getValue()).isEqualTo(5.8f);
        assertThat(frame.getTemperatures().getValues().get(4).getTimestamp()).isEqualTo("2017-11-07T17:07:22.000Z");
        assertThat(frame.getTemperatures().getValues().get(4).getValue()).isEqualTo(5.4f);
        assertThat(frame.getTemperatures().getValues().get(5).getTimestamp()).isEqualTo("2017-11-07T17:17:22.000Z");
        assertThat(frame.getTemperatures().getValues().get(5).getValue()).isEqualTo(5.1f);
        assertThat(frame.getBatteryLevels().getUnit()).isEqualTo("mV");
        assertThat(frame.getBatteryLevels().getValues().size()).isEqualTo(0);
    }
    
    @PayloadDescription(name = "BATCH_5070164_DEFAULT_PROFILE for device 50-70-164", description = "Batch payload sent by a device 50-70-164 associated to the tag BATCH_5070164_DEFAULT_PROFILE. "
    		+ "\nThe BATCH_5070164_DEFAULT_PROFILE tag is replaced by the decoder by these following field batch tags:\n"
    		+ "BATCH_tagsize_3, \n"
    		+ "BATCH_temperatures_°C_L0_R10_T7_D100, \n"
            + "BATCH_batteryLevels_mV_L5_R100_T6")
    @Test
    public void should_decode_5070164_payload_with_tagBatchProfile() throws JsDecodingException {
    	
    	String inputDataMessage = "{" + " \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\"," 
    			+ "   \"connector\": \"lora\","
                + "   \"encoding\": \"nke_watteco_v2.4\"," 
    			+ "   \"network\": {" + " \"lora\": {" + " \"port\": 6,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + " } }  }," 
    			+ "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\"," 
                + "   \"value\": {" + " \"payload\": \"100500813d1901134041bb6579b72cef96e5ddb2baddb26600\"" + "   },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"BATCH_5070164_DEFAULT_PROFILE\"]}";         
       
    	String input = "100500813d1901134041bb6579b72cef96e5ddb2baddb26600";              
        NKEBatch frame = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEBatch.class);
        assertThat(frame.getBatchCounter()).isEqualTo(5);
        assertThat(frame.getBatchTimestamp()).isEqualTo("2017-11-07T17:17:25.000Z");
        assertThat(frame.getTemperatures().getUnit()).isEqualTo("°C");
        assertThat(frame.getTemperatures().getValues().size()).isEqualTo(6);
        assertThat(frame.getTemperatures().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T16:27:22.000Z");
        assertThat(frame.getTemperatures().getValues().get(0).getValue()).isEqualTo(5.5f);
        assertThat(frame.getTemperatures().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T16:37:22.000Z");
        assertThat(frame.getTemperatures().getValues().get(1).getValue()).isEqualTo(5.6f);
        assertThat(frame.getTemperatures().getValues().get(2).getTimestamp()).isEqualTo("2017-11-07T16:47:22.000Z");
        assertThat(frame.getTemperatures().getValues().get(2).getValue()).isEqualTo(5.7f);
        assertThat(frame.getTemperatures().getValues().get(3).getTimestamp()).isEqualTo("2017-11-07T16:57:22.000Z");
        assertThat(frame.getTemperatures().getValues().get(3).getValue()).isEqualTo(5.8f);
        assertThat(frame.getTemperatures().getValues().get(4).getTimestamp()).isEqualTo("2017-11-07T17:07:22.000Z");
        assertThat(frame.getTemperatures().getValues().get(4).getValue()).isEqualTo(5.4f);
        assertThat(frame.getTemperatures().getValues().get(5).getTimestamp()).isEqualTo("2017-11-07T17:17:22.000Z");
        assertThat(frame.getTemperatures().getValues().get(5).getValue()).isEqualTo(5.1f);
        assertThat(frame.getBatteryLevels().getUnit()).isEqualTo("mV");
        assertThat(frame.getBatteryLevels().getValues().size()).isEqualTo(0);  
    }
        

    @PayloadDescription(name = "BATCH_5070166_DEFAULT_PROFILE for device 50-70-166", description = "Batch payload sent by a device 50-70-166 associated to the tag BATCH_5070166_DEFAULT_PROFILE. "
    		+ "\nThe BATCH_5070166_DEFAULT_PROFILE tag is replaced by the decoder by these following field batch tags:\n"
    		+ "BATCH_tagsize_3,\n"
    		+ "BATCH_meanDifferentialPressureSinceLastReport_Pa_L0_R1_T7,\n"
            + "BATCH_minimalDifferentialPressureSinceLastReport_Pa_L1_R1_T7, \n"
            + "BATCH_maximalDifferentialPressureSinceLastReport_Pa_L2_R1_T7, \n"
            + "BATCH_batteryLevel_mV_L3_R1_T6, \n"
            + "BATCH_temperature_°C_L4_R10_T7, \n"
            + "BATCH_differentialPressure_Pa_L5_R1_T10, \n"
            + "BATCH_index_NoUnit_L6_R1_T10, \n"
            + "BATCH_state_NoUnit_L7_R1_T10")   
    @Test
    public void should_decode_5070166_payload_with_tagBatchProfile() throws JsDecodingException {
    	
    	String inputDataMessage = "{" + " \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\"," 
    			+ "   \"connector\": \"lora\","
                + "   \"encoding\": \"nke_watteco_v2.4\"," 
    			+ "   \"network\": {" + " \"lora\": {" + " \"port\": 6,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + " } }  }," 
    			+ "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\"," 
                + "   \"value\": {" + " \"payload\": \"3600000d593780b9ec0192b407b01d01\"" + "   },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"BATCH_5070166_DEFAULT_PROFILE\"]}";         
       
        String input = "3600000d593780b9ec0192b407b01d01";              
        NKEBatch frame = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEBatch.class);
        assertThat(frame.getBatchCounter()).isEqualTo(0);
        assertThat(frame.getBatchTimestamp()).isEqualTo("2017-11-07T17:17:25.000Z");
        assertThat(frame.getMeanDifferentialPressureSinceLastReport().getUnit()).isEqualTo("Pa");
        assertThat(frame.getMeanDifferentialPressureSinceLastReport().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T17:17:20.000Z");
        assertThat(frame.getMeanDifferentialPressureSinceLastReport().getValues().get(0).getValue()).isEqualTo(115);
        assertThat(frame.getMinimalDifferentialPressureSinceLastReport().getUnit()).isEqualTo("Pa");
        assertThat(frame.getMinimalDifferentialPressureSinceLastReport().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T17:17:20.000Z");
        assertThat(frame.getMinimalDifferentialPressureSinceLastReport().getValues().get(0).getValue()).isEqualTo(73);
        assertThat(frame.getMaximalDifferentialPressureSinceLastReport().getUnit()).isEqualTo("Pa");
        assertThat(frame.getMaximalDifferentialPressureSinceLastReport().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T17:17:20.000Z");
        assertThat(frame.getMaximalDifferentialPressureSinceLastReport().getValues().get(0).getValue()).isEqualTo(182);        
        assertThat(frame.getBatteryLevels().getUnit()).isEqualTo("mV");
        assertThat(frame.getBatteryLevels().getValues().size()).isEqualTo(0);       
        assertThat(frame.getTemperatures().getUnit()).isEqualTo("°C");
        assertThat(frame.getTemperatures().getValues().size()).isEqualTo(0);
        assertThat(frame.getDifferentialPressures().getUnit()).isEqualTo("Pa");
        assertThat(frame.getDifferentialPressures().getValues().size()).isEqualTo(0);
        assertThat(frame.getIndex().getValues().size()).isEqualTo(0);
        assertThat(frame.getStates().getValues().size()).isEqualTo(0);
        
        /*
        	decoded : {"batchCounter":0,"batchTimestamp":"2017-11-07T17:17:25.000Z",
        	"meanDifferentialPressureSinceLastReport":{"unit":"Pa","values":[{"timestamp":"2017-11-07T17:17:20.000Z","value":115}]},
        	"minimalDifferentialPressureSinceLastReport":{"unit":"Pa","values":[{"timestamp":"2017-11-07T17:17:20.000Z","value":73}]},
        	"maximalDifferentialPressureSinceLastReport":{"unit":"Pa","values":[{"timestamp":"2017-11-07T17:17:20.000Z","value":182}]},
        	"batteryLevels":{"unit":"mV","values":[]},
        	"temperatures":{"unit":"°C","values":[]},
        	"differentialPressures":{"unit":"Pa","values":[]},"index":{"values":[]},"states":{"values":[]}}
*/

    }
    
    @PayloadDescription(name = "BATCH_5070166_Fields for device 50-70-166", description = "Batch payload sent by a device 50-70-166 associated to the tags BATCH fields.")   
    @Test
    public void should_decode_5070166_payload_with_tagBatchFields() throws JsDecodingException {
    	
    	String inputDataMessage = "{" + " \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\"," 
    			+ "   \"connector\": \"lora\","
                + "   \"encoding\": \"nke_watteco_v2.4\"," 
    			+ "   \"network\": {" + " \"lora\": {" + " \"port\": 6,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + " } }  }," 
    			+ "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\"," 
                + "   \"value\": {" + " \"payload\": \"4631000001b30648ec0138b20720d01ea06601\"" + "   },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"BATCH_tagsize_3\", "
                + "\"BATCH_MeanDifferentialPressures_Pa_L0_R1_T7\","
                + "\"BATCH_MinDifferentialPressures_Pa_L1_R1_T7\","
                + "\"BATCH_MaxDifferentialPressures_Pa_L2_R1_T7\","
                + "\"BATCH_batteryLevels_mV_L3_R1_T6\", "
                + "\"BATCH_temperatures4_°C_L4_R10_T7\","
                + "\"differentialPressure_Pa_L5_L5_R1_T7\","
                + "\"BATCH_index_NoUnit_L6_R1_T10\","
                + "\"BATCH_state_NoUnit_L7_R1_T1\"]}";
       
        String input = "4631000001b30648ec0138b20720d01ea06601";              
        NKEBatch frame = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEBatch.class);
        assertThat(frame.getBatchCounter()).isEqualTo(1);
        /*
          decoded : {"batchCounter":1,"batchTimestamp":"2017-11-07T17:17:25.000Z",
          "MeanDifferentialPressures":{"unit":"Pa","values":[{"timestamp":"2017-11-07T17:17:24.000Z","value":28}]},
          "MinDifferentialPressures":{"unit":"Pa","values":[{"timestamp":"2017-11-07T17:17:24.000Z","value":4}]},
          "MaxDifferentialPressures":{"unit":"Pa","values":[{"timestamp":"2017-11-07T17:17:24.000Z","value":53}]},
          "batteryLevels":{"unit":"mV","values":[{"timestamp":"2017-11-07T17:17:24.000Z","value":3472}]},
          "temperatures4":{"unit":"°C","values":[]},
          "index":{"values":[]},"state":{"values":[]}}
         */
        
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
                + "   \"tags\": [ "
                + "\"BATCH_5070167_DEFAULT_PROFILE\"]}";         
       
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
    
    
 
    @Test
    public void should_decode_5070168_payload_with_fields_tag() throws JsDecodingException {  
    		
    	String inputDataMessage = "{" + " \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\"," 
    			+ "   \"connector\": \"lora\","
                + "   \"encoding\": \"nke_watteco_v2.4\"," 
    			+ "   \"network\": {" + " \"lora\": {" + " \"port\": 6,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + " } }  }," 
    			+ "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\"," 
                + "   \"value\": {" + " \"payload\": \"4232000127e7005ce06e2800c8a0bc027c64b72c04657168bf1b4ad3dccdc9dccdc9147cfbb27763b2e55eb4b264e542010a5a08500888e2681b11\"" + "   },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"BATCH_tagsize_3\", "
                + "\"BATCH_temperatures_°C_L1_R10_T7_D100\","
                + "\"BATCH_relativeHumidities_%RH_L2_R100_T6_D100\","
                + "\"BATCH_CO2_ppm_L3_R10_T6\","
                + "\"BATCH_COV_NoUnit_L4_R10_T6\" ]" + "}";
  
    	
        String input = "4232000127e7005ce06e2800c8a0bc027c64b72c04657168bf1b4ad3dccdc9dccdc9147cfbb27763b2e55eb4b264e542010a5a08500888e2681b11";              
        NKEBatch frame = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEBatch.class);
        assertThat(frame.getBatchCounter()).isEqualTo(2);
        assertThat(frame.getBatchTimestamp()).isEqualTo("2017-11-07T17:17:25.000Z");
        assertThat(frame.getTemperatures().getUnit()).isEqualTo("°C");
        assertThat(frame.getTemperatures().getValues().size()).isEqualTo(4);
        assertThat(frame.getTemperatures().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T16:38:03.000Z");
        assertThat(frame.getTemperatures().getValues().get(0).getValue()).isEqualTo(22.5f);
        assertThat(frame.getTemperatures().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T16:48:03.000Z");
        assertThat(frame.getTemperatures().getValues().get(1).getValue()).isEqualTo(22.4f);
        assertThat(frame.getTemperatures().getValues().get(2).getTimestamp()).isEqualTo("2017-11-07T16:58:05.000Z");
        assertThat(frame.getTemperatures().getValues().get(2).getValue()).isEqualTo(22.1f);
        assertThat(frame.getTemperatures().getValues().get(3).getTimestamp()).isEqualTo("2017-11-07T17:08:05.000Z");
        assertThat(frame.getTemperatures().getValues().get(3).getValue()).isEqualTo(21.9f);
        assertThat(frame.getRelativeHumidities().getUnit()).isEqualTo("%RH");
        assertThat(frame.getRelativeHumidities().getValues().size()).isEqualTo(2);
        assertThat(frame.getRelativeHumidities().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T16:28:03.000Z");
        assertThat(frame.getRelativeHumidities().getValues().get(0).getValue()).isEqualTo(28.00f);
        assertThat(frame.getRelativeHumidities().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T16:58:05.000Z");
        assertThat(frame.getRelativeHumidities().getValues().get(1).getValue()).isEqualTo(29.00f);
        assertThat(frame.getCO2().getUnit()).isEqualTo("ppm");
        assertThat(frame.getCO2().getValues().size()).isEqualTo(6);
        assertThat(frame.getCO2().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T16:17:51.000Z");
        assertThat(frame.getCO2().getValues().get(0).getValue()).isEqualTo(440);
        assertThat(frame.getCO2().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T16:27:52.000Z");
        assertThat(frame.getCO2().getValues().get(1).getValue()).isEqualTo(450);
        assertThat(frame.getCO2().getValues().get(2).getTimestamp()).isEqualTo("2017-11-07T16:37:52.000Z");
        assertThat(frame.getCO2().getValues().get(2).getValue()).isEqualTo(440);
        assertThat(frame.getCO2().getValues().get(3).getTimestamp()).isEqualTo("2017-11-07T16:47:52.000Z");
        assertThat(frame.getCO2().getValues().get(3).getValue()).isEqualTo(480);
        assertThat(frame.getCO2().getValues().get(4).getTimestamp()).isEqualTo("2017-11-07T16:57:52.000Z");
        assertThat(frame.getCO2().getValues().get(4).getValue()).isEqualTo(430);
        assertThat(frame.getCO2().getValues().get(5).getTimestamp()).isEqualTo("2017-11-07T17:07:52.000Z");
        assertThat(frame.getCO2().getValues().get(5).getValue()).isEqualTo(450);
        assertThat(frame.getCOV().getValues().size()).isEqualTo(3);
        assertThat(frame.getCOV().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T16:27:02.000Z");
        assertThat(frame.getCOV().getValues().get(0).getValue()).isEqualTo(200);
        assertThat(frame.getCOV().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T16:52:02.000Z");
        assertThat(frame.getCOV().getValues().get(1).getValue()).isEqualTo(190);
        assertThat(frame.getCOV().getValues().get(2).getTimestamp()).isEqualTo("2017-11-07T17:12:02.000Z");
        assertThat(frame.getCOV().getValues().get(2).getValue()).isEqualTo(200);        
    }
    
    @PayloadDescription(name = "BATCH_5070168_DEFAULT_PROFILE for device 50-70-168", description = "Batch payload sent by a device 50-70-168 associated to the tag BATCH_5070168_DEFAULT_PROFILE. "
    		+ "\nThe BATCH_5070168_DEFAULT_PROFILE tag is replaced by the decoder by these following field batch tags:\n"
    		+ "BATCH_tagsize_3, \n"
    		+ "BATCH_temperatures_°C_L1_R10_T7_D100, \n"
    		+ "BATCH_relativeHumidities_%RH_L2_R100_T6_D100, \n"
    		+ "BATCH_CO2_ppm_L3_R10_T6, \n"
            + "BATCH_COV_NoUnit_L4_R10_T6")
    @Test
    public void should_decode_5070168_payload_with_tagBatchProfile() throws JsDecodingException {
    	
    	String inputDataMessage = "{" + " \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\"," 
    			+ "   \"connector\": \"lora\","
                + "   \"encoding\": \"nke_watteco_v2.4\"," 
    			+ "   \"network\": {" + " \"lora\": {" + " \"port\": 6,"
                + "   \"devEUI\": \"70B3D516C0000068\"" + " } }  }," 
    			+ "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
                + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\"," 
                + "   \"value\": {" + " \"payload\": \"4232000127e7005ce06e2800c8a0bc027c64b72c04657168bf1b4ad3dccdc9dccdc9147cfbb27763b2e55eb4b264e542010a5a08500888e2681b11\"" + "   },"
                + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
                + "   \"tags\": [ "
                + "\"BATCH_5070168_DEFAULT_PROFILE\"]}";         
       
    	String input = "4232000127e7005ce06e2800c8a0bc027c64b72c04657168bf1b4ad3dccdc9dccdc9147cfbb27763b2e55eb4b264e542010a5a08500888e2681b11";              
        NKEBatch frame = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEBatch.class);
        assertThat(frame.getBatchCounter()).isEqualTo(2);
        assertThat(frame.getBatchTimestamp()).isEqualTo("2017-11-07T17:17:25.000Z");
        assertThat(frame.getTemperatures().getUnit()).isEqualTo("°C");
        assertThat(frame.getTemperatures().getValues().size()).isEqualTo(4);
        assertThat(frame.getTemperatures().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T16:38:03.000Z");
        assertThat(frame.getTemperatures().getValues().get(0).getValue()).isEqualTo(22.5f);
        assertThat(frame.getTemperatures().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T16:48:03.000Z");
        assertThat(frame.getTemperatures().getValues().get(1).getValue()).isEqualTo(22.4f);
        assertThat(frame.getTemperatures().getValues().get(2).getTimestamp()).isEqualTo("2017-11-07T16:58:05.000Z");
        assertThat(frame.getTemperatures().getValues().get(2).getValue()).isEqualTo(22.1f);
        assertThat(frame.getTemperatures().getValues().get(3).getTimestamp()).isEqualTo("2017-11-07T17:08:05.000Z");
        assertThat(frame.getTemperatures().getValues().get(3).getValue()).isEqualTo(21.9f);
        assertThat(frame.getRelativeHumidities().getUnit()).isEqualTo("%RH");
        assertThat(frame.getRelativeHumidities().getValues().size()).isEqualTo(2);
        assertThat(frame.getRelativeHumidities().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T16:28:03.000Z");
        assertThat(frame.getRelativeHumidities().getValues().get(0).getValue()).isEqualTo(28.00f);
        assertThat(frame.getRelativeHumidities().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T16:58:05.000Z");
        assertThat(frame.getRelativeHumidities().getValues().get(1).getValue()).isEqualTo(29.00f);
        assertThat(frame.getCO2().getUnit()).isEqualTo("ppm");
        assertThat(frame.getCO2().getValues().size()).isEqualTo(6);
        assertThat(frame.getCO2().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T16:17:51.000Z");
        assertThat(frame.getCO2().getValues().get(0).getValue()).isEqualTo(440);
        assertThat(frame.getCO2().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T16:27:52.000Z");
        assertThat(frame.getCO2().getValues().get(1).getValue()).isEqualTo(450);
        assertThat(frame.getCO2().getValues().get(2).getTimestamp()).isEqualTo("2017-11-07T16:37:52.000Z");
        assertThat(frame.getCO2().getValues().get(2).getValue()).isEqualTo(440);
        assertThat(frame.getCO2().getValues().get(3).getTimestamp()).isEqualTo("2017-11-07T16:47:52.000Z");
        assertThat(frame.getCO2().getValues().get(3).getValue()).isEqualTo(480);
        assertThat(frame.getCO2().getValues().get(4).getTimestamp()).isEqualTo("2017-11-07T16:57:52.000Z");
        assertThat(frame.getCO2().getValues().get(4).getValue()).isEqualTo(430);
        assertThat(frame.getCO2().getValues().get(5).getTimestamp()).isEqualTo("2017-11-07T17:07:52.000Z");
        assertThat(frame.getCO2().getValues().get(5).getValue()).isEqualTo(450);
        assertThat(frame.getCOV().getValues().size()).isEqualTo(3);
        assertThat(frame.getCOV().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T16:27:02.000Z");
        assertThat(frame.getCOV().getValues().get(0).getValue()).isEqualTo(200);
        assertThat(frame.getCOV().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T16:52:02.000Z");
        assertThat(frame.getCOV().getValues().get(1).getValue()).isEqualTo(190);
        assertThat(frame.getCOV().getValues().get(2).getTimestamp()).isEqualTo("2017-11-07T17:12:02.000Z");
        assertThat(frame.getCOV().getValues().get(2).getValue()).isEqualTo(200);    
    }
	
	@Test
	public void should_decode_5070201_payload_with_fields_tag() throws JsDecodingException {  
			
		String inputDataMessage = "{" + " \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\"," 
				+ "   \"connector\": \"lora\","
	            + "   \"encoding\": \"nke_watteco_v2.4\"," 
				+ "   \"network\": {" + " \"lora\": {" + " \"port\": 6,"
	            + "   \"devEUI\": \"70B3D516C0000068\"" + " } }  }," 
				+ "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
	            + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\"," 
	            + "   \"value\": {" + " \"payload\": \"200400808049162c8a4042e0cccd000379c7a185db31f0873bc051b10bfe05beec50dc6b074b1b8005\"" + "   },"
	            + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
	            + "   \"tags\": [ "
	            + "\"BATCH_chockMaxAccelerations_mg_L0_R1_T6\", "
	            + "\"BATCH_angles_Deg_L1_R1_T12\", "
	            + "\"BATCH_tagsize_3\" ]" + "}";    
		
	    String input = "200400808049162c8a4042e0cccd000379c7a185db31f0873bc051b10bfe05beec50dc6b074b1b8005";              
	    NKEBatch frame = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEBatch.class);
	    assertThat(frame.getBatchCounter()).isEqualTo(4);
	    assertThat(frame.getBatchTimestamp()).isEqualTo("2017-11-07T17:17:25.000Z");
	    assertThat(frame.getAngles().getUnit()).isEqualTo("Deg");
	    assertThat(frame.getAngles().getValues().size()).isEqualTo(1);
	    assertThat(frame.getAngles().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T17:14:32.000Z");
	    assertThat(frame.getAngles().getValues().get(0).getValue()).isEqualTo(112.4000015258789f);   
	    assertThat(frame.getChockMaxAccelerations().getValues().size()).isEqualTo(7);
	    assertThat(frame.getChockMaxAccelerations().getUnit()).isEqualTo("mg");
	    assertThat(frame.getChockMaxAccelerations().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T17:11:53.000Z");
	    assertThat(frame.getChockMaxAccelerations().getValues().get(0).getValue()).isEqualTo(11352);
	    assertThat(frame.getChockMaxAccelerations().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T17:11:59.000Z");
	    assertThat(frame.getChockMaxAccelerations().getValues().get(1).getValue()).isEqualTo(19822);
	    assertThat(frame.getChockMaxAccelerations().getValues().get(2).getTimestamp()).isEqualTo("2017-11-07T17:12:09.000Z");
	    assertThat(frame.getChockMaxAccelerations().getValues().get(2).getValue()).isEqualTo(3566);
	    assertThat(frame.getChockMaxAccelerations().getValues().get(3).getTimestamp()).isEqualTo("2017-11-07T17:12:14.000Z");
	    assertThat(frame.getChockMaxAccelerations().getValues().get(3).getValue()).isEqualTo(7825);
	    assertThat(frame.getChockMaxAccelerations().getValues().get(4).getTimestamp()).isEqualTo("2017-11-07T17:12:21.000Z");
	    assertThat(frame.getChockMaxAccelerations().getValues().get(4).getValue()).isEqualTo(10000);
	    assertThat(frame.getChockMaxAccelerations().getValues().get(5).getTimestamp()).isEqualTo("2017-11-07T17:17:23.000Z");
	    assertThat(frame.getChockMaxAccelerations().getValues().get(5).getValue()).isEqualTo(3327);
	    assertThat(frame.getChockMaxAccelerations().getValues().get(6).getTimestamp()).isEqualTo("2017-11-07T17:17:24.000Z");
	    assertThat(frame.getChockMaxAccelerations().getValues().get(6).getValue()).isEqualTo(9139);  
	}
	
    
	@PayloadDescription(name = "BATCH_5070201_DEFAULT_PROFILE for device 50-70-201", description = "Batch payload sent by a device 50-70-201 associated to the tag BATCH_5070201_DEFAULT_PROFILE. "
			+ "\nThe BATCH_5070201_DEFAULT_PROFILE tag is replaced by the decoder by these following field batch tags:\n"
			+ "BATCH_tagsize_3, \n "
			+ "BATCH_chockMaxAccelerations_mg_L0_R1_T6, \n"
	        + "BATCH_angles_Deg_L1_R1_T12 ")
	@Test
	public void should_decode_5070201_payload_with_tagBatchProfile() throws JsDecodingException {
		
		String inputDataMessage = "{" + " \"metadata\": {" + " \"source\": \"urn:lora:70B3D516C0000068\"," 
				+ "   \"connector\": \"lora\","
	            + "   \"encoding\": \"nke_watteco_v2.4\"," 
				+ "   \"network\": {" + " \"lora\": {" + " \"port\": 6,"
	            + "   \"devEUI\": \"70B3D516C0000068\"" + " } }  }," 
				+ "   \"streamId\": \"urn:lora:70B3D516C0000068!uplink\","
	            + "   \"created\": \"2017-11-07T17:17:26.000Z\"," + "   \"model\": \"lora_v0\"," 
	            + "   \"value\": {" + " \"payload\": \"200400808049162c8a4042e0cccd000379c7a185db31f0873bc051b10bfe05beec50dc6b074b1b8005\"" + "   },"
	            + "   \"timestamp\": \"2017-11-07T17:17:25.000Z\"" + ","
	            + "   \"tags\": [ "
	            + "\"BATCH_5070201_DEFAULT_PROFILE\"]}";         
	   
	    String input = "200400808049162c8a4042e0cccd000379c7a185db31f0873bc051b10bfe05beec50dc6b074b1b8005";              
	    NKEBatch frame = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, NKEBatch.class);
		assertThat(frame.getBatchCounter()).isEqualTo(4);
		assertThat(frame.getBatchTimestamp()).isEqualTo("2017-11-07T17:17:25.000Z");
		assertThat(frame.getAngles().getUnit()).isEqualTo("Deg");
		assertThat(frame.getAngles().getValues().size()).isEqualTo(1);
		assertThat(frame.getAngles().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T17:14:32.000Z");
		assertThat(frame.getAngles().getValues().get(0).getValue()).isEqualTo(112.4000015258789f);   
		assertThat(frame.getChockMaxAccelerations().getValues().size()).isEqualTo(7);
		assertThat(frame.getChockMaxAccelerations().getUnit()).isEqualTo("mg");
		assertThat(frame.getChockMaxAccelerations().getValues().get(0).getTimestamp()).isEqualTo("2017-11-07T17:11:53.000Z");
		assertThat(frame.getChockMaxAccelerations().getValues().get(0).getValue()).isEqualTo(11352);
		assertThat(frame.getChockMaxAccelerations().getValues().get(1).getTimestamp()).isEqualTo("2017-11-07T17:11:59.000Z");
		assertThat(frame.getChockMaxAccelerations().getValues().get(1).getValue()).isEqualTo(19822);
		assertThat(frame.getChockMaxAccelerations().getValues().get(2).getTimestamp()).isEqualTo("2017-11-07T17:12:09.000Z");
		assertThat(frame.getChockMaxAccelerations().getValues().get(2).getValue()).isEqualTo(3566);
		assertThat(frame.getChockMaxAccelerations().getValues().get(3).getTimestamp()).isEqualTo("2017-11-07T17:12:14.000Z");
		assertThat(frame.getChockMaxAccelerations().getValues().get(3).getValue()).isEqualTo(7825);
		assertThat(frame.getChockMaxAccelerations().getValues().get(4).getTimestamp()).isEqualTo("2017-11-07T17:12:21.000Z");
		assertThat(frame.getChockMaxAccelerations().getValues().get(4).getValue()).isEqualTo(10000);
		assertThat(frame.getChockMaxAccelerations().getValues().get(5).getTimestamp()).isEqualTo("2017-11-07T17:17:23.000Z");
		assertThat(frame.getChockMaxAccelerations().getValues().get(5).getValue()).isEqualTo(3327);
		assertThat(frame.getChockMaxAccelerations().getValues().get(6).getTimestamp()).isEqualTo("2017-11-07T17:17:24.000Z");
		assertThat(frame.getChockMaxAccelerations().getValues().get(6).getValue()).isEqualTo(9139); 
	}
    
}

