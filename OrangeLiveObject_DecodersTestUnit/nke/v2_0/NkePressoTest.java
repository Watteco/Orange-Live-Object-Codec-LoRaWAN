package com.orange.lo.decoder.js.nke.v2_0;

import static org.assertj.core.api.Assertions.assertThat;

import com.orange.lo.decoder.js.TestBase;
import com.orange.lo.decoder.js.doc.annotation.DeviceDescription;
import com.orange.lo.decoder.js.doc.annotation.PayloadDescription;
import com.orange.lo.decoder.js.exception.JsDecodingException;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterAnalogInput;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterBinaryInput;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/* Tests clusters Presso :                 
0x000C Analog Input
0x000F Binary Input
*/
@Slf4j
@DeviceDescription(name = "Watteco (NKE-Watteco) Presso 50-70-017", manufacturer = "WATTECO", docLink = "https://support.watteco.com/",
        version = { "1.0",
                "</h5>",
                "Clusters:<ul>",
                "<li>000C Analog Input</li>",
                "<li>000F Binary Input</li>",
                "</ul>",
                "<h5>" },
        encoding = "watteco_generic", hidden = false)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NkePressoTest extends TestBase {
    private final String SCRIPT_PATH = "nke/nkePublicV2_10";

    @Test
    public void should_profile() throws JsDecodingException {
        String input = "3101000c005500394201e577";
        log.info("result: {}", profile(SCRIPT_PATH, input, null));
    }


    @Test
    @PayloadDescription(name = "ClusterAnalogInput_PresentValue", description = "Cluster: Analog input, Attribut: PresentValue, Command: Read Attribute Response.")
    public void should_decode_000C_AnalogInput_attribut0055_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "3101000c005500394201e577";

        NKEClusterAnalogInput analogInput = formatAndDecode(SCRIPT_PATH, input, NKEClusterAnalogInput.class);
        assertThat(analogInput.getMessageType()).isEqualTo("Analog Input");
        assertThat(analogInput.getEndpoint()).isEqualTo("1");
        assertThat(analogInput.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(analogInput.getAttributId()).isEqualTo("PresentValue");
        assertThat(analogInput.getStatus()).isEqualTo("Ok");
        assertThat(analogInput.getValue().getClass().getSimpleName()).isEqualTo("Float");
        assertThat(analogInput.getValue()).isEqualTo(32.474087f);
    }

    @Test
    @PayloadDescription(name = "ClusterAnalogInput_PresentValue", description = "Cluster: Analog input, Attribut: PresentValue, Command: Read Attribute Response.")
    public void should_decode_000C_AnalogInput_attribut0055_cmd01_error_payload() throws JsDecodingException {
        /* real payload */
        String input = "3101000c005580";

        NKEClusterAnalogInput analogInput = formatAndDecode(SCRIPT_PATH, input, NKEClusterAnalogInput.class);
        assertThat(analogInput.getMessageType()).isEqualTo("Analog Input");
        assertThat(analogInput.getEndpoint()).isEqualTo("1");
        assertThat(analogInput.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(analogInput.getAttributId()).isEqualTo("PresentValue");
        assertThat(analogInput.getStatus()).isEqualTo("Malformed Command");
    }

    @Test
    public void should_decode_000C_AnalogInput_attribut0055_cmd07_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "3107000C00000055";

        NKEClusterAnalogInput analogInput = formatAndDecode(SCRIPT_PATH, input, NKEClusterAnalogInput.class);
        assertThat(analogInput.getMessageType()).isEqualTo("Analog Input");
        assertThat(analogInput.getEndpoint()).isEqualTo("1");
        assertThat(analogInput.getCommandId()).isEqualTo("Configure Reporting Response");
        assertThat(analogInput.getAttributId()).isEqualTo("PresentValue");
        assertThat(analogInput.getStatus()).isEqualTo("Ok");
        assertThat(analogInput.getReport()).isEqualTo("Standard report");
    }

    @Test
    public void should_decode_000C_AnalogInput_attribut0055_cmd8A_short_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "118A000c0055394201e57798B2D3";

        NKEClusterAnalogInput analogInput = formatAndDecode(SCRIPT_PATH, input, NKEClusterAnalogInput.class);
        assertThat(analogInput.getMessageType()).isEqualTo("Analog Input");
        assertThat(analogInput.getEndpoint()).isEqualTo("0");
        assertThat(analogInput.getCommandId()).isEqualTo("Threshold Report");
        assertThat(analogInput.getAttributId()).isEqualTo("PresentValue");
        assertThat(analogInput.getValue()).isEqualTo(32.474087f);
        assertThat(analogInput.getReportParameters().getBatch()).isEqualTo(false);
        assertThat(analogInput.getReportParameters().getNoHeaderPort()).isEqualTo(false);
        assertThat(analogInput.getReportParameters().getSecured()).isEqualTo(false);
        assertThat(analogInput.getReportParameters().getSecuredIfAlarm()).isEqualTo(true);
        assertThat(analogInput.getReportParameters().getCauseRequest()).isEqualTo("short");
        assertThat(analogInput.getReportParameters().getNewModeConfiguration()).isEqualTo(true);
        assertThat(analogInput.getCriterions().get(0).getCriteriaSlotDescriptor().getCriterionIndex()).isEqualTo(2);
        assertThat(analogInput.getCriterions().get(0).getCriteriaSlotDescriptor().getMode()).isEqualTo("threshold");
        assertThat(analogInput.getCriterions().get(0).getCriteriaSlotDescriptor().getOnFall()).isEqualTo(true);
        assertThat(analogInput.getCriterions().get(0).getCriteriaSlotDescriptor().getOnExceed()).isEqualTo(false);
        assertThat(analogInput.getCriterions().get(0).getCriteriaSlotDescriptor().getAlarm()).isEqualTo(true);
        assertThat(analogInput.getCriterions().get(1).getCriteriaSlotDescriptor().getCriterionIndex()).isEqualTo(3);
        assertThat(analogInput.getCriterions().get(1).getCriteriaSlotDescriptor().getMode()).isEqualTo("threshold");
        assertThat(analogInput.getCriterions().get(1).getCriteriaSlotDescriptor().getOnFall()).isEqualTo(false);
        assertThat(analogInput.getCriterions().get(1).getCriteriaSlotDescriptor().getOnExceed()).isEqualTo(true);
        assertThat(analogInput.getCriterions().get(1).getCriteriaSlotDescriptor().getAlarm()).isEqualTo(true);

    }

    @Test
    public void should_decode_000C_AnalogInput_attribut0055_cmd8A_long_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "118A000c0055394201e577A8B24201e575400000008000010002D34201e5763F8000008000020003";

        NKEClusterAnalogInput analogInput = formatAndDecode(SCRIPT_PATH, input, NKEClusterAnalogInput.class);
        assertThat(analogInput.getMessageType()).isEqualTo("Analog Input");
        assertThat(analogInput.getEndpoint()).isEqualTo("0");
        assertThat(analogInput.getCommandId()).isEqualTo("Threshold Report");
        assertThat(analogInput.getAttributId()).isEqualTo("PresentValue");
        assertThat(analogInput.getValue()).isEqualTo(32.474087f);
        assertThat(analogInput.getReportParameters().getBatch()).isEqualTo(false);
        assertThat(analogInput.getReportParameters().getNoHeaderPort()).isEqualTo(false);
        assertThat(analogInput.getReportParameters().getSecured()).isEqualTo(false);
        assertThat(analogInput.getReportParameters().getSecuredIfAlarm()).isEqualTo(true);
        assertThat(analogInput.getReportParameters().getCauseRequest()).isEqualTo("long");
        assertThat(analogInput.getReportParameters().getNewModeConfiguration()).isEqualTo(true);
        assertThat(analogInput.getCriterions().get(0).getCriteriaSlotDescriptor().getCriterionIndex()).isEqualTo(2);
        assertThat(analogInput.getCriterions().get(0).getCriteriaSlotDescriptor().getMode()).isEqualTo("threshold");
        assertThat(analogInput.getCriterions().get(0).getCriteriaSlotDescriptor().getOnFall()).isEqualTo(true);
        assertThat(analogInput.getCriterions().get(0).getCriteriaSlotDescriptor().getOnExceed()).isEqualTo(false);
        assertThat(analogInput.getCriterions().get(0).getCriteriaSlotDescriptor().getAlarm()).isEqualTo(true);
        assertThat(analogInput.getCriterions().get(0).getValue()).isEqualTo(32.474079f);
        assertThat(analogInput.getCriterions().get(0).getGap()).isEqualTo(2.0f);
        assertThat(analogInput.getCriterions().get(0).getNumberOfOccurances()).isEqualTo(0);
        assertThat(analogInput.getCriterions().get(0).getOccurencesHigh()).isEqualTo(1);
        assertThat(analogInput.getCriterions().get(0).getOccurencesLow()).isEqualTo(2);
        assertThat(analogInput.getCriterions().get(1).getCriteriaSlotDescriptor().getCriterionIndex()).isEqualTo(3);
        assertThat(analogInput.getCriterions().get(1).getCriteriaSlotDescriptor().getMode()).isEqualTo("threshold");
        assertThat(analogInput.getCriterions().get(1).getCriteriaSlotDescriptor().getOnFall()).isEqualTo(false);
        assertThat(analogInput.getCriterions().get(1).getCriteriaSlotDescriptor().getOnExceed()).isEqualTo(true);
        assertThat(analogInput.getCriterions().get(1).getCriteriaSlotDescriptor().getAlarm()).isEqualTo(true);
        assertThat(analogInput.getCriterions().get(1).getValue()).isEqualTo(32.474083f);
        assertThat(analogInput.getCriterions().get(1).getGap()).isEqualTo(1.0f);
        assertThat(analogInput.getCriterions().get(1).getNumberOfOccurances()).isEqualTo(0);
        assertThat(analogInput.getCriterions().get(1).getOccurencesHigh()).isEqualTo(2);
        assertThat(analogInput.getCriterions().get(1).getOccurencesLow()).isEqualTo(3);

    }

    @Test
    public void should_decode_000C_AnalogInput_cmd07_payload() throws JsDecodingException {
        /* real payload */
        String input = "3107000c80";

        NKEClusterAnalogInput analogInput = formatAndDecode(SCRIPT_PATH, input, NKEClusterAnalogInput.class);
        assertThat(analogInput.getMessageType()).isEqualTo("Analog Input");
        assertThat(analogInput.getEndpoint()).isEqualTo("1");
        assertThat(analogInput.getCommandId()).isEqualTo("Configure Reporting Response");
        assertThat(analogInput.getStatus()).isEqualTo("Malformed Command");
    }

    @Test
    @PayloadDescription(name = "ClusterAnalogInput_ApplicationType", description = "Cluster: Analog input, Attribut:ApplicationType, Command: Read Attribute Response.")
    public void should_decode_000C_AnalogInput_attribut0100_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "3101000C0100002300050000";

        NKEClusterAnalogInput analogInput = formatAndDecode(SCRIPT_PATH, input, NKEClusterAnalogInput.class);
        assertThat(analogInput.getMessageType()).isEqualTo("Analog Input");
        assertThat(analogInput.getEndpoint()).isEqualTo("1");
        assertThat(analogInput.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(analogInput.getAttributId()).isEqualTo("ApplicationType");
        assertThat(analogInput.getStatus()).isEqualTo("Ok");
        assertThat(analogInput.getApplicationType()).isEqualTo("Carbon Dioxide AI application");
        assertThat(analogInput.getApplicationUnit()).isEqualTo("PPM");
    }

    @Test
    @PayloadDescription(name = "ClusterAnalogInput_PowerDuration", description = "Cluster: Analog input, Attribut:PowerDuration, Command: Read Attribute Response.")
    public void should_decode_000C_AnalogInput_attribut8003_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "3101000C800300210005";

        NKEClusterAnalogInput analogInput = formatAndDecode(SCRIPT_PATH, input, NKEClusterAnalogInput.class);
        assertThat(analogInput.getMessageType()).isEqualTo("Analog Input");
        assertThat(analogInput.getEndpoint()).isEqualTo("1");
        assertThat(analogInput.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(analogInput.getAttributId()).isEqualTo("PowerDuration");
        assertThat(analogInput.getStatus()).isEqualTo("Ok");
        assertThat(analogInput.getPowerDuration().getUnit()).isEqualTo("ms");
        assertThat(analogInput.getPowerDuration().getValue()).isEqualTo(5);
    }

    @Test
    @PayloadDescription(name = "ClusterBinaryInput_Count", description = "Cluster: Binary input, Attribut:count, Command: Report Attributes.")
    public void should_decode_000F_BinaryInput_attribut0402_cmd0A_payload() throws JsDecodingException {
        /* real payload */
        String input = "110a000f0402230000005a";

        NKEClusterBinaryInput binaryInput = formatAndDecode(SCRIPT_PATH, input, NKEClusterBinaryInput.class);
        assertThat(binaryInput.getMessageType()).isEqualTo("Binary Input");
        assertThat(binaryInput.getEndpoint()).isEqualTo("0");
        assertThat(binaryInput.getCommandId()).isEqualTo("Report Attributes");
        assertThat(binaryInput.getAttributId()).isEqualTo("Count");
        assertThat(binaryInput.getCounterCurrentValue()).isEqualTo(90);
    }


    @Test
    public void should_decode_000F_BinaryInput_attribut0402_cmd8A_long_payload() throws JsDecodingException {
        /* real payload */
        String input = "118a000f0402230000005aA8B2000000990000000f8000010002D3000000773F8000008000020003";

        NKEClusterBinaryInput binaryInput = formatAndDecode(SCRIPT_PATH, input, NKEClusterBinaryInput.class);
        assertThat(binaryInput.getMessageType()).isEqualTo("Binary Input");
        assertThat(binaryInput.getEndpoint()).isEqualTo("0");
        assertThat(binaryInput.getCommandId()).isEqualTo("Threshold Report");
        assertThat(binaryInput.getAttributId()).isEqualTo("Count");
        assertThat(binaryInput.getCounterCurrentValue()).isEqualTo(90);

        assertThat(binaryInput.getReportParameters().getBatch()).isEqualTo(false);
        assertThat(binaryInput.getReportParameters().getNoHeaderPort()).isEqualTo(false);
        assertThat(binaryInput.getReportParameters().getSecured()).isEqualTo(false);
        assertThat(binaryInput.getReportParameters().getSecuredIfAlarm()).isEqualTo(true);
        assertThat(binaryInput.getReportParameters().getCauseRequest()).isEqualTo("long");
        assertThat(binaryInput.getReportParameters().getNewModeConfiguration()).isEqualTo(true);
        assertThat(binaryInput.getCriterions().get(0).getCriteriaSlotDescriptor().getCriterionIndex()).isEqualTo(2);
        assertThat(binaryInput.getCriterions().get(0).getCriteriaSlotDescriptor().getMode()).isEqualTo("threshold");
        assertThat(binaryInput.getCriterions().get(0).getCriteriaSlotDescriptor().getOnFall()).isEqualTo(true);
        assertThat(binaryInput.getCriterions().get(0).getCriteriaSlotDescriptor().getOnExceed()).isEqualTo(false);
        assertThat(binaryInput.getCriterions().get(0).getCriteriaSlotDescriptor().getAlarm()).isEqualTo(true);
        assertThat(binaryInput.getCriterions().get(0).getValue()).isEqualTo(153);
        assertThat(binaryInput.getCriterions().get(0).getGap()).isEqualTo(15);
        assertThat(binaryInput.getCriterions().get(0).getNumberOfOccurances()).isEqualTo(0);
        assertThat(binaryInput.getCriterions().get(0).getOccurencesHigh()).isEqualTo(1);
        assertThat(binaryInput.getCriterions().get(0).getOccurencesLow()).isEqualTo(2);
        assertThat(binaryInput.getCriterions().get(1).getCriteriaSlotDescriptor().getCriterionIndex()).isEqualTo(3);
        assertThat(binaryInput.getCriterions().get(1).getCriteriaSlotDescriptor().getMode()).isEqualTo("threshold");
        assertThat(binaryInput.getCriterions().get(1).getCriteriaSlotDescriptor().getOnFall()).isEqualTo(false);
        assertThat(binaryInput.getCriterions().get(1).getCriteriaSlotDescriptor().getOnExceed()).isEqualTo(true);
        assertThat(binaryInput.getCriterions().get(1).getCriteriaSlotDescriptor().getAlarm()).isEqualTo(true);
        assertThat(binaryInput.getCriterions().get(1).getValue()).isEqualTo(119);
        assertThat(binaryInput.getCriterions().get(1).getGap()).isEqualTo(1065353216);
        assertThat(binaryInput.getCriterions().get(1).getNumberOfOccurances()).isEqualTo(0);
        assertThat(binaryInput.getCriterions().get(1).getOccurencesHigh()).isEqualTo(2);
        assertThat(binaryInput.getCriterions().get(1).getOccurencesLow()).isEqualTo(3);


    }


    @Test
    @PayloadDescription(name = "ClusterBinaryInput_Count", description = "Cluster: Binary input, Attribut:PresentValue, Command: Read Attribute Response.")
    public void should_decode_000F_BinaryInput_attribut0055_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "1101000f0055001001";

        NKEClusterBinaryInput binaryInput = formatAndDecode(SCRIPT_PATH, input, NKEClusterBinaryInput.class);
        assertThat(binaryInput.getMessageType()).isEqualTo("Binary Input");
        assertThat(binaryInput.getEndpoint()).isEqualTo("0");
        assertThat(binaryInput.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(binaryInput.getAttributId()).isEqualTo("PresentValue");
        assertThat(binaryInput.getStatus()).isEqualTo("Ok");
        assertThat(binaryInput.getValue()).isEqualTo("1");
    }


    @Test
    @PayloadDescription(name = "ClusterBinaryInput_Polarity", description = "Cluster: Binary input, Attribut:Polarity, Command: Read Attribute Response.")
    public void should_decode_000F_BinaryInput_attribut0054_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "1101000f0054001001";

        NKEClusterBinaryInput binaryInput = formatAndDecode(SCRIPT_PATH, input, NKEClusterBinaryInput.class);
        assertThat(binaryInput.getMessageType()).isEqualTo("Binary Input");
        assertThat(binaryInput.getEndpoint()).isEqualTo("0");
        assertThat(binaryInput.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(binaryInput.getAttributId()).isEqualTo("Polarity");
        assertThat(binaryInput.getStatus()).isEqualTo("Ok");
        assertThat(binaryInput.getPolarity()).isEqualTo("Reversed");
    }

    @Test
    @PayloadDescription(name = "ClusterBinaryInput_EdgeSelection", description = "Cluster: Binary input, Attribut:Edge selection, Command: Read Attribute Response.")
    public void should_decode_000F_BinaryInput_attribut0400_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "1101000f0400001801";

        NKEClusterBinaryInput binaryInput = formatAndDecode(SCRIPT_PATH, input, NKEClusterBinaryInput.class);
        assertThat(binaryInput.getMessageType()).isEqualTo("Binary Input");
        assertThat(binaryInput.getEndpoint()).isEqualTo("0");
        assertThat(binaryInput.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(binaryInput.getAttributId()).isEqualTo("Edge Selection");
        assertThat(binaryInput.getStatus()).isEqualTo("Ok");
        assertThat(binaryInput.getEdgeSelection()).isEqualTo("Falling edge");
    }

    @Test
    @PayloadDescription(name = "ClusterBinaryInput_DebouncePeriod", description = "Cluster: Binary input, Attribut:Debounce period , Command: Read Attribute Response.")
    public void should_decode_000F_BinaryInput_attribut0401_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "1101000f040100210011";

        NKEClusterBinaryInput binaryInput = formatAndDecode(SCRIPT_PATH, input, NKEClusterBinaryInput.class);
        assertThat(binaryInput.getMessageType()).isEqualTo("Binary Input");
        assertThat(binaryInput.getEndpoint()).isEqualTo("0");
        assertThat(binaryInput.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(binaryInput.getAttributId()).isEqualTo("Debounce Period");
        assertThat(binaryInput.getStatus()).isEqualTo("Ok");
        assertThat(binaryInput.getDebouncePeriod().getValue()).isEqualTo(17);
        assertThat(binaryInput.getDebouncePeriod().getUnit()).isEqualTo("milliseconds");
    }

    @Test
    public void should_decode_000F_BinaryInput_attribut0401_cmd8A_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "118A000f0401210011A8B2001200018000010002D3001300028000020003";

        NKEClusterBinaryInput binaryInput = formatAndDecode(SCRIPT_PATH, input, NKEClusterBinaryInput.class);
        assertThat(binaryInput.getMessageType()).isEqualTo("Binary Input");
        assertThat(binaryInput.getEndpoint()).isEqualTo("0");
        assertThat(binaryInput.getCommandId()).isEqualTo("Threshold Report");
        assertThat(binaryInput.getAttributId()).isEqualTo("Debounce Period");
        assertThat(binaryInput.getDebouncePeriod().getValue()).isEqualTo(17);
        assertThat(binaryInput.getDebouncePeriod().getUnit()).isEqualTo("milliseconds");
        assertThat(binaryInput.getReportParameters().getBatch()).isEqualTo(false);
        assertThat(binaryInput.getReportParameters().getNoHeaderPort()).isEqualTo(false);
        assertThat(binaryInput.getReportParameters().getSecured()).isEqualTo(false);
        assertThat(binaryInput.getReportParameters().getSecuredIfAlarm()).isEqualTo(true);
        assertThat(binaryInput.getReportParameters().getCauseRequest()).isEqualTo("long");
        assertThat(binaryInput.getReportParameters().getNewModeConfiguration()).isEqualTo(true);
        assertThat(binaryInput.getCriterions().get(0).getCriteriaSlotDescriptor().getCriterionIndex()).isEqualTo(2);
        assertThat(binaryInput.getCriterions().get(0).getCriteriaSlotDescriptor().getMode()).isEqualTo("threshold");
        assertThat(binaryInput.getCriterions().get(0).getCriteriaSlotDescriptor().getOnFall()).isEqualTo(true);
        assertThat(binaryInput.getCriterions().get(0).getCriteriaSlotDescriptor().getOnExceed()).isEqualTo(false);
        assertThat(binaryInput.getCriterions().get(0).getCriteriaSlotDescriptor().getAlarm()).isEqualTo(true);
        assertThat(binaryInput.getCriterions().get(0).getValue()).isEqualTo(18);
        assertThat(binaryInput.getCriterions().get(0).getGap()).isEqualTo(1);
        assertThat(binaryInput.getCriterions().get(0).getNumberOfOccurances()).isEqualTo(0);
        assertThat(binaryInput.getCriterions().get(0).getOccurencesHigh()).isEqualTo(1);
        assertThat(binaryInput.getCriterions().get(0).getOccurencesLow()).isEqualTo(2);
        assertThat(binaryInput.getCriterions().get(1).getCriteriaSlotDescriptor().getCriterionIndex()).isEqualTo(3);
        assertThat(binaryInput.getCriterions().get(1).getCriteriaSlotDescriptor().getMode()).isEqualTo("threshold");
        assertThat(binaryInput.getCriterions().get(1).getCriteriaSlotDescriptor().getOnFall()).isEqualTo(false);
        assertThat(binaryInput.getCriterions().get(1).getCriteriaSlotDescriptor().getOnExceed()).isEqualTo(true);
        assertThat(binaryInput.getCriterions().get(1).getCriteriaSlotDescriptor().getAlarm()).isEqualTo(true);
        assertThat(binaryInput.getCriterions().get(1).getValue()).isEqualTo(19);
        assertThat(binaryInput.getCriterions().get(1).getGap()).isEqualTo(2);
        assertThat(binaryInput.getCriterions().get(1).getNumberOfOccurances()).isEqualTo(0);
        assertThat(binaryInput.getCriterions().get(1).getOccurencesHigh()).isEqualTo(2);
        assertThat(binaryInput.getCriterions().get(1).getOccurencesLow()).isEqualTo(3);


    }

}
