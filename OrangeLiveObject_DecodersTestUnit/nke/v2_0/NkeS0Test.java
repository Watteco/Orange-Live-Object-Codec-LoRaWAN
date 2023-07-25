package com.orange.lo.decoder.js.nke.v2_0;

import static org.assertj.core.api.Assertions.assertThat;

import com.orange.lo.decoder.js.TestBase;
import com.orange.lo.decoder.js.doc.annotation.DeviceDescription;
import com.orange.lo.decoder.js.doc.annotation.PayloadDescription;
import com.orange.lo.decoder.js.exception.JsDecodingException;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterBinaryInput;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterMultiBinaryInputs;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


@Slf4j
@DeviceDescription(name = "Watteco (NKE-Watteco) S0 50-70-001", manufacturer = "WATTECO", docLink = "https://support.watteco.com/",
        version = { "1.0",
                "</h5>",
                "Clusters:<ul>",
                "<li>000F Binary Input</li>",
                "<li>8005 Multi Binary Inputs</li>",
                "</ul>",
                "<h5>" },
        encoding = "watteco_generic", hidden = false)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NkeS0Test extends TestBase {
    private final String SCRIPT_PATH = "nke/nkePublicV2_10";

    @Test
    public void should_decode_bad_payload() throws JsDecodingException {
        formatAndDecode(SCRIPT_PATH, "0", Object.class);
    }

    @Test
    public void should_profile() throws JsDecodingException {
        String input = "110a000f0402230000005a";
        log.info("result: {}", profile(SCRIPT_PATH, input, null));
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
    @PayloadDescription(name = "ClusterBinaryInput_Count", description = "Cluster: Binary input, Attribut:PresentValue, Command: Configure Reporting Response.")
    public void should_decode_000F_BinaryInput_attribut0055_cmd07_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "3107000F00000055";

        NKEClusterBinaryInput binaryInput = formatAndDecode(SCRIPT_PATH, input, NKEClusterBinaryInput.class);
        assertThat(binaryInput.getMessageType()).isEqualTo("Binary Input");
        assertThat(binaryInput.getEndpoint()).isEqualTo("1");
        assertThat(binaryInput.getCommandId()).isEqualTo("Configure Reporting Response");
        assertThat(binaryInput.getAttributId()).isEqualTo("PresentValue");
        assertThat(binaryInput.getStatus()).isEqualTo("Ok");
        assertThat(binaryInput.getReport()).isEqualTo("Standard report");
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
    @PayloadDescription(name = "MultiBinaryInputs_PresentValue", description = "Cluster: MultiBinaryInputs, Attribut:PresentValue , Command: Report Attributes.")
    public void should_decode_8005_MultiBinaryInputs_attribut0000_cmd0A_payload_input371() throws JsDecodingException {
        /* payload build for test */
        String input = "110A80050000190371";

        NKEClusterMultiBinaryInputs multiBinaryInput = formatAndDecode(SCRIPT_PATH, input, NKEClusterMultiBinaryInputs.class);
        assertThat(multiBinaryInput.getMessageType()).isEqualTo("Multi Binary Input");
        assertThat(multiBinaryInput.getEndpoint()).isEqualTo("0");
        assertThat(multiBinaryInput.getCommandId()).isEqualTo("Report Attributes");
        assertThat(multiBinaryInput.getAttributId()).isEqualTo("PresentValue");
        assertThat(multiBinaryInput.binaryValues.input1Value).isEqualTo(true);
        assertThat(multiBinaryInput.binaryValues.input2Value).isEqualTo(false);
        assertThat(multiBinaryInput.binaryValues.input3Value).isEqualTo(false);
        assertThat(multiBinaryInput.binaryValues.input4Value).isEqualTo(false);
        assertThat(multiBinaryInput.binaryValues.input5Value).isEqualTo(true);
        assertThat(multiBinaryInput.binaryValues.input6Value).isEqualTo(true);
        assertThat(multiBinaryInput.binaryValues.input7Value).isEqualTo(true);
        assertThat(multiBinaryInput.binaryValues.input8Value).isEqualTo(false);
        assertThat(multiBinaryInput.binaryValues.input9Value).isEqualTo(true);
        assertThat(multiBinaryInput.binaryValues.input10Value).isEqualTo(true);
    }


}
