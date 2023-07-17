package com.orange.lo.decoder.js.nke.v2_0;

import static org.assertj.core.api.Assertions.assertThat;

import com.orange.lo.decoder.js.TestBase;
import com.orange.lo.decoder.js.doc.annotation.DeviceDescription;
import com.orange.lo.decoder.js.doc.annotation.PayloadDescription;
import com.orange.lo.decoder.js.exception.JsDecodingException;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterBinaryInput;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterSimpleMetering;
import com.orange.lo.decoder.js.nke.v2_0.pojo.NKEClusterTICinformationCBE;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/* Tests clusters des capteurs TIC :
0x0052 Simple metering
0x000F Binary input
Cluster selon le compteur connecté:
0x0054 TIC-CBE Compteur Bleu 
0x0053 TIC-ICE Compteur Emeraude
0x0055 TIC-CJE Compteur Jaune 
0x0056 TIC-STD Compteur Linky
0x0057 TIC-PMEPMI
*/
@Slf4j
@DeviceDescription(name = "Watteco (NKE-Watteco) TIC sensors", manufacturer = "WATTECO", docLink = "https://support.watteco.com/",
        version = { "1.0",
                "TIC Harvesting 50-70-008, TIC+S0 50-70-002, TIC+2S0+2TORES 50-70-038, TICS'0(ex. TIC PMEPMI) 50-70-045",
                "</h5>",
                "Clusters:<ul>",
                "<li>0052 Simple metering</li>",
                "<li>000F Binary Input</li>",
                "<li>0054 TIC-CBE</li>",
                "</ul>",
                "<h5>" },
        encoding = "watteco_generic", hidden = false)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NkeTICsensorsTest extends TestBase {

    private final String SCRIPT_PATH = "nke/nkePublicV2_8";

    @Test
    public void should_profile() throws JsDecodingException {
        String input = "11010052800000410910EEEEDDDDCCCCBBBB";
        log.info("result: {}", profile(SCRIPT_PATH, input, null));
    }	

		/*
	    @Test
	    @Ignore //fonctions récursives volontairement laissées dans le script
	    public void should_check_script() throws JsDecodingException {
	        checkScript(SCRIPT_PATH);
	    }
		*/

    @Test
    public void should_decode_bad_payload() throws JsDecodingException {
        formatAndDecode(SCRIPT_PATH, "0", Object.class);
    }

    @Test
    @PayloadDescription(name = "ClusterSimpleMeteringLike_CurrentCalibration", description = "Cluster: Simple Metering-Like, Attribut: current Calibration, Command: Read Attribute Response, current calibration coefficient.")
    public void should_decode_0052_SimpleMetering_attribut8000_cmd01_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "11010052800000410910EEEEDDDDCCCCBBBB";

        NKEClusterSimpleMetering simpleMetering = formatAndDecode(SCRIPT_PATH, input, NKEClusterSimpleMetering.class);
        assertThat(simpleMetering.getMessageType()).isEqualTo("Simple Metering-Like");
        assertThat(simpleMetering.getEndpoint()).isEqualTo("0");
        assertThat(simpleMetering.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(simpleMetering.getAttributId()).isEqualTo("Current calibration coefficient");
        assertThat(simpleMetering.getStatus()).isEqualTo("Ok");
        assertThat(simpleMetering.getCurrentE2Pot()).isEqualTo(16);
        assertThat(simpleMetering.getActivePowerMultiplier()).isEqualTo(-4370.0f);
        assertThat(simpleMetering.getActivePowerDivisor()).isEqualTo(-8739.0f);
        assertThat(simpleMetering.getReactivePowerMultiplier()).isEqualTo(-13108.0f);
        assertThat(simpleMetering.getReactivePowerDivisor()).isEqualTo(-17477.0f);
    }

    @Test
    public void should_decode_0052_SimpleMetering_attribut0000_cmd07_payload() throws JsDecodingException {
        /* real payload */
        String input = "3107005200000000";

        NKEClusterSimpleMetering simpleMetering = formatAndDecode(SCRIPT_PATH, input, NKEClusterSimpleMetering.class);
        assertThat(simpleMetering.getMessageType()).isEqualTo("Simple Metering-Like");
        assertThat(simpleMetering.getEndpoint()).isEqualTo("1");
        assertThat(simpleMetering.getCommandId()).isEqualTo("Configure Reporting Response");
        assertThat(simpleMetering.getAttributId()).isEqualTo("Current metering");
        assertThat(simpleMetering.getStatus()).isEqualTo("Ok");
        assertThat(simpleMetering.getReport()).isEqualTo("Standard report");
    }

    @Test
    @PayloadDescription(name = "ClusterSimpleMeteringLike_CurrentMetering", description = "Cluster: Simple Metering-Like, Attribut: current metering, Command: Report Attributes. Give the consumption of the remote SmartPlug.")
    public void should_decode_0052_SimpleMetering_attribut0000_cmd0A_payload() throws JsDecodingException {
        /* real payload */
        String input = "310a00520000410c000047ffffbe0063002fffd6";

        NKEClusterSimpleMetering simpleMetering = formatAndDecode(SCRIPT_PATH, input, NKEClusterSimpleMetering.class);
        assertThat(simpleMetering.getMessageType()).isEqualTo("Simple Metering-Like");
        assertThat(simpleMetering.getEndpoint()).isEqualTo("1");
        assertThat(simpleMetering.getCommandId()).isEqualTo("Report Attributes");
        assertThat(simpleMetering.getAttributId()).isEqualTo("Current metering");
        assertThat(simpleMetering.getActiveEnergySum().getValue()).isEqualTo(71.0f);
        assertThat(simpleMetering.getActiveEnergySum().getUnit()).isEqualTo("Wh");
        assertThat(simpleMetering.getReactiveEnergySum().getValue()).isEqualTo(-66.0f);
        assertThat(simpleMetering.getReactiveEnergySum().getUnit()).isEqualTo("VARh");
        assertThat(simpleMetering.getActivePower().getValue()).isEqualTo(47.0f);
        assertThat(simpleMetering.getActivePower().getUnit()).isEqualTo("W");
        assertThat(simpleMetering.getReactivePower().getValue()).isEqualTo(-42.0f);
        assertThat(simpleMetering.getReactivePower().getUnit()).isEqualTo("VAR");
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


    /**
     * TICInformationERDF CBE cluster tests
     **/

    @Test
    @PayloadDescription(name = "ClusterTICInformationERDF-CBE_Original_CBE_AllFields", description = "Cluster: TIC Information ERDF-CBE, Attribut: CBE original, Command: Report Attributes.")
    public void should_decode_0054_TICbleu_attribut0000_cmd0A_payload() throws JsDecodingException {
        /* payload build for test */
        String input = "110a005400004186000000FFFFFFFFFF212122222323454545454545454545454545455050505050010042000000000001000030000000300100003002000030030000400000004001000040020000400300004004FF0000400500004006515151515152525252523232232324117011711172117311741175110000FFFF00234567394243444546474841424352";

        NKEClusterTICinformationCBE clusterBlue = formatAndDecode(SCRIPT_PATH, input, NKEClusterTICinformationCBE.class);
        assertThat(clusterBlue.getMessageType()).isEqualTo("TIC Information ERDF-CBE");
        assertThat(clusterBlue.getEndpoint()).isEqualTo("0");
        assertThat(clusterBlue.getCommandId()).isEqualTo("Report Attributes");
        assertThat(clusterBlue.getAttributId()).isEqualTo("CBE original");
        assertThat(clusterBlue.getAvertDepassI().getUnit()).isEqualTo("A");
        assertThat(clusterBlue.getAvertDepassI().getPhase1()).isEqualTo(8481.0f);
        assertThat(clusterBlue.getAvertDepassI().getPhase2()).isEqualTo(8738.0f);
        assertThat(clusterBlue.getAvertDepassI().getPhase3()).isEqualTo(8995.0f);
        assertThat(clusterBlue.getAdresseCompteur()).isEqualTo("EEEEEEEEEEEEE");
        assertThat(clusterBlue.getOptionTarif()).isEqualTo("PPPPP");
        assertThat(clusterBlue.getIntensiteSouscrite().getValue()).isEqualTo(1);
        assertThat(clusterBlue.getIntensiteSouscrite().getUnit()).isEqualTo("A");
        assertThat(clusterBlue.getIndexOptionBase().getValue()).isEqualTo(4325376);
        assertThat(clusterBlue.getIndexOptionBase().getUnit()).isEqualTo("Wh");
        assertThat(clusterBlue.getIndexHeuresCreuses().getValue()).isEqualTo(1.0f);
        assertThat(clusterBlue.getIndexHeuresCreuses().getUnit()).isEqualTo("Wh");
        assertThat(clusterBlue.getIndexHeuresPleines().getValue()).isEqualTo(12288.0f);
        assertThat(clusterBlue.getIndexHeuresPleines().getUnit()).isEqualTo("Wh");
        assertThat(clusterBlue.getEjpHeuresNormales().getValue()).isEqualTo(12289.0f);
        assertThat(clusterBlue.getEjpHeuresNormales().getUnit()).isEqualTo("Wh");
        assertThat(clusterBlue.getEjpHeuresPointeMobile().getValue()).isEqualTo(12290.0f);
        assertThat(clusterBlue.getEjpHeuresPointeMobile().getUnit()).isEqualTo("Wh");
        assertThat(clusterBlue.getTempoHeuresCreusesJbleus().getValue()).isEqualTo(12291.0f);
        assertThat(clusterBlue.getTempoHeuresCreusesJbleus().getUnit()).isEqualTo("Wh");
        assertThat(clusterBlue.getTempoHeuresPleinesJbleus().getValue()).isEqualTo(16384.0f);
        assertThat(clusterBlue.getTempoHeuresPleinesJbleus().getUnit()).isEqualTo("Wh");
        assertThat(clusterBlue.getTempoHeuresCreusesJblancs().getValue()).isEqualTo(16385.0f);
        assertThat(clusterBlue.getTempoHeuresCreusesJblancs().getUnit()).isEqualTo("Wh");
        assertThat(clusterBlue.getTempoHeuresPleinesJblancs().getValue()).isEqualTo(16386.0f);
        assertThat(clusterBlue.getTempoHeuresPleinesJblancs().getUnit()).isEqualTo("Wh");
        assertThat(clusterBlue.getTempoHeuresCreusesJrouges().getValue()).isEqualTo(16387.0f);
        assertThat(clusterBlue.getTempoHeuresCreusesJrouges().getUnit()).isEqualTo("Wh");
        assertThat(clusterBlue.getTempoHeuresPleinesJrouges().getValue()).isEqualTo(16388.0f);
        assertThat(clusterBlue.getTempoHeuresPleinesJrouges().getUnit()).isEqualTo("Wh");
        assertThat(clusterBlue.getPreavisEJP().getValue()).isEqualTo(255.0f);
        assertThat(clusterBlue.getPreavisEJP().getUnit()).isEqualTo("Min");
        assertThat(clusterBlue.getIndexGaz().getValue()).isEqualTo(16389.0f);
        assertThat(clusterBlue.getIndexGaz().getUnit()).isEqualTo("dal");
        assertThat(clusterBlue.getIndexTroisiemeCompteur().getValue()).isEqualTo(16390.0f);
        assertThat(clusterBlue.getIndexTroisiemeCompteur().getUnit()).isEqualTo("dal");
        assertThat(clusterBlue.getPeriodeTarifaireEnCours()).isEqualTo("QQQQQ");
        assertThat(clusterBlue.getCouleurLendemain()).isEqualTo("RRRRR");
        assertThat(clusterBlue.getIntensiteInstant().getValue()).isEqualTo(12850);
        assertThat(clusterBlue.getIntensiteInstant().getUnit()).isEqualTo("A");
        assertThat(clusterBlue.getIntensiteInstantPhase1().getValue()).isEqualTo(8995);
        assertThat(clusterBlue.getIntensiteInstantPhase1().getUnit()).isEqualTo("A");
        assertThat(clusterBlue.getIntensiteInstantPhase2().getValue()).isEqualTo(9233);
        assertThat(clusterBlue.getIntensiteInstantPhase2().getUnit()).isEqualTo("A");
        assertThat(clusterBlue.getIntensiteInstantPhase3().getValue()).isEqualTo(28689);
        assertThat(clusterBlue.getIntensiteInstantPhase3().getUnit()).isEqualTo("A");
        assertThat(clusterBlue.getAvertDepassPuissSous().getValue()).isEqualTo(28945);
        assertThat(clusterBlue.getAvertDepassPuissSous().getUnit()).isEqualTo("A");
        assertThat(clusterBlue.getIntensiteMaxAppelee().getValue()).isEqualTo(29201);
        assertThat(clusterBlue.getIntensiteMaxAppelee().getUnit()).isEqualTo("A");
        assertThat(clusterBlue.getIntensiteMaxAppeleeP1().getValue()).isEqualTo(29457);
        assertThat(clusterBlue.getIntensiteMaxAppeleeP1().getUnit()).isEqualTo("A");
        assertThat(clusterBlue.getIntensiteMaxAppeleeP2().getValue()).isEqualTo(29713);
        assertThat(clusterBlue.getIntensiteMaxAppeleeP2().getUnit()).isEqualTo("A");
        assertThat(clusterBlue.getIntensiteMaxAppeleeP3().getValue()).isEqualTo(29969);
        assertThat(clusterBlue.getIntensiteMaxAppeleeP3().getUnit()).isEqualTo("A");
        assertThat(clusterBlue.getPuissanceMax().getValue()).isEqualTo(65535.0f);
        assertThat(clusterBlue.getPuissanceMax().getUnit()).isEqualTo("W");
        assertThat(clusterBlue.getPuissanceApp().getValue()).isEqualTo(2311527.0f);
        assertThat(clusterBlue.getPuissanceApp().getUnit()).isEqualTo("VA");
        assertThat(clusterBlue.getHoraireHeurePleineCreuse()).isEqualTo("9");
        assertThat(clusterBlue.getMotEtatCompteur()).isEqualTo("BCDEFGH");
        assertThat(clusterBlue.getPresencePotentiels()).isEqualTo("ABC");
    }

    @Test
    public void should_decode_0054_TICbleu_attribut0000_cmd0A_payload_modeBASE() throws JsDecodingException {
        /* real payload */
        String input = "110a00540000412700000001084000783032313532383830323735360042415345002d00003e60000000080000005a";

        NKEClusterTICinformationCBE clusterBlue = formatAndDecode(SCRIPT_PATH, input, NKEClusterTICinformationCBE.class);
        assertThat(clusterBlue.getMessageType()).isEqualTo("TIC Information ERDF-CBE");
        assertThat(clusterBlue.getEndpoint()).isEqualTo("0");
        assertThat(clusterBlue.getCommandId()).isEqualTo("Report Attributes");
        assertThat(clusterBlue.getAttributId()).isEqualTo("CBE original");
        assertThat(clusterBlue.getAdresseCompteur()).isEqualTo("021528802756");
        assertThat(clusterBlue.getOptionTarif()).isEqualTo("Base");
        assertThat(clusterBlue.getIntensiteSouscrite().getValue()).isEqualTo(45);
        assertThat(clusterBlue.getIntensiteSouscrite().getUnit()).isEqualTo("A");
        assertThat(clusterBlue.getIndexOptionBase().getValue()).isEqualTo(15968);
        assertThat(clusterBlue.getIndexOptionBase().getUnit()).isEqualTo("Wh");
        assertThat(clusterBlue.getIntensiteInstant().getValue()).isEqualTo(0);
        assertThat(clusterBlue.getIntensiteInstant().getUnit()).isEqualTo("A");
        assertThat(clusterBlue.getIntensiteMaxAppelee().getValue()).isEqualTo(8);
        assertThat(clusterBlue.getIntensiteMaxAppelee().getUnit()).isEqualTo("A");
        assertThat(clusterBlue.getPuissanceApp().getValue()).isEqualTo(90);
        assertThat(clusterBlue.getPuissanceApp().getUnit()).isEqualTo("VA");

    }

    @Test
    public void should_decode_0054_TICbleu_attribut0000_cmd0A_payload_modeHCHP() throws JsDecodingException {
        /* real payload */
        String input = "110a00540000413000000001085001b83032313532383830323735360048432e2e002d0000441f0000708f48502e2e00000000080000005a";

        NKEClusterTICinformationCBE clusterBlue = formatAndDecode(SCRIPT_PATH, input, NKEClusterTICinformationCBE.class);
        assertThat(clusterBlue.getMessageType()).isEqualTo("TIC Information ERDF-CBE");
        assertThat(clusterBlue.getEndpoint()).isEqualTo("0");
        assertThat(clusterBlue.getCommandId()).isEqualTo("Report Attributes");
        assertThat(clusterBlue.getAttributId()).isEqualTo("CBE original");
        assertThat(clusterBlue.getAdresseCompteur()).isEqualTo("021528802756");
        assertThat(clusterBlue.getOptionTarif()).isEqualTo("Heure creuse heure pleine");
        assertThat(clusterBlue.getIntensiteSouscrite().getValue()).isEqualTo(45);
        assertThat(clusterBlue.getIntensiteSouscrite().getUnit()).isEqualTo("A");
        assertThat(clusterBlue.getIndexHeuresCreuses().getValue()).isEqualTo(17439.0f);
        assertThat(clusterBlue.getIndexHeuresCreuses().getUnit()).isEqualTo("Wh");
        assertThat(clusterBlue.getIndexHeuresPleines().getValue()).isEqualTo(28815.0f);
        assertThat(clusterBlue.getIndexHeuresPleines().getUnit()).isEqualTo("Wh");
        assertThat(clusterBlue.getPeriodeTarifaireEnCours()).isEqualTo("Heures pleines");
        assertThat(clusterBlue.getIntensiteInstant().getValue()).isEqualTo(0);
        assertThat(clusterBlue.getIntensiteInstant().getUnit()).isEqualTo("A");
        assertThat(clusterBlue.getIntensiteMaxAppelee().getValue()).isEqualTo(8);
        assertThat(clusterBlue.getIntensiteMaxAppelee().getUnit()).isEqualTo("A");
        assertThat(clusterBlue.getPuissanceApp().getValue()).isEqualTo(90);
        assertThat(clusterBlue.getPuissanceApp().getUnit()).isEqualTo("VA");

    }

    @Test
    @PayloadDescription(name = "ClusterTICInformationERDF-CBE_Original_CBE_TempoMode", description = "Cluster: TIC Information ERDF-CBE, mode TEMPO, Attribut: CBE original, Command: Report Attributes.")
    public void should_decode_0054_TICbleu_attribut0000_cmd0A_payload_modeTEMPO() throws JsDecodingException {
        /* real payload */
        String input = "110a005400004140000000010851f8383032313532383830323735360042425228002d000040f9000070860000000000000227000000000000006d48504a5700000000080000005a";

        NKEClusterTICinformationCBE clusterBlue = formatAndDecode(SCRIPT_PATH, input, NKEClusterTICinformationCBE.class);
        assertThat(clusterBlue.getMessageType()).isEqualTo("TIC Information ERDF-CBE");
        assertThat(clusterBlue.getEndpoint()).isEqualTo("0");
        assertThat(clusterBlue.getCommandId()).isEqualTo("Report Attributes");
        assertThat(clusterBlue.getAttributId()).isEqualTo("CBE original");
        assertThat(clusterBlue.getAdresseCompteur()).isEqualTo("021528802756");
        assertThat(clusterBlue.getOptionTarif()).isEqualTo("Tempo");
        assertThat(clusterBlue.getIntensiteSouscrite().getValue()).isEqualTo(45);
        assertThat(clusterBlue.getIntensiteSouscrite().getUnit()).isEqualTo("A");
        assertThat(clusterBlue.getTempoHeuresCreusesJbleus().getValue()).isEqualTo(16633.0f);
        assertThat(clusterBlue.getTempoHeuresCreusesJbleus().getUnit()).isEqualTo("Wh");
        assertThat(clusterBlue.getTempoHeuresPleinesJbleus().getValue()).isEqualTo(28806.0f);
        assertThat(clusterBlue.getTempoHeuresPleinesJbleus().getUnit()).isEqualTo("Wh");
        assertThat(clusterBlue.getTempoHeuresCreusesJblancs().getValue()).isEqualTo(0.0f);
        assertThat(clusterBlue.getTempoHeuresCreusesJblancs().getUnit()).isEqualTo("Wh");
        assertThat(clusterBlue.getTempoHeuresPleinesJblancs().getValue()).isEqualTo(551.0f);
        assertThat(clusterBlue.getTempoHeuresPleinesJblancs().getUnit()).isEqualTo("Wh");
        assertThat(clusterBlue.getTempoHeuresCreusesJrouges().getValue()).isEqualTo(0.0f);
        assertThat(clusterBlue.getTempoHeuresCreusesJrouges().getUnit()).isEqualTo("Wh");
        assertThat(clusterBlue.getTempoHeuresPleinesJrouges().getValue()).isEqualTo(109.0f);
        assertThat(clusterBlue.getTempoHeuresPleinesJrouges().getUnit()).isEqualTo("Wh");
        assertThat(clusterBlue.getPeriodeTarifaireEnCours()).isEqualTo("Heures pleines jours blancs");
        assertThat(clusterBlue.getIntensiteInstant().getValue()).isEqualTo(0);
        assertThat(clusterBlue.getIntensiteInstant().getUnit()).isEqualTo("A");
        assertThat(clusterBlue.getIntensiteMaxAppelee().getValue()).isEqualTo(8);
        assertThat(clusterBlue.getIntensiteMaxAppelee().getUnit()).isEqualTo("A");
        assertThat(clusterBlue.getPuissanceApp().getValue()).isEqualTo(90);
        assertThat(clusterBlue.getPuissanceApp().getUnit()).isEqualTo("VA");

    }

    @Test
    public void should_decode_0054_TICbleu_attribut0000_cmd0A_payload_modeEJP() throws JsDecodingException {
        /* real payload */
        String input = "110a005400004130000000010850063830323135323838303237353600454a502e002d0000411200007086484e2e2e000000000800000078";

        NKEClusterTICinformationCBE clusterBlue = formatAndDecode(SCRIPT_PATH, input, NKEClusterTICinformationCBE.class);
        assertThat(clusterBlue.getMessageType()).isEqualTo("TIC Information ERDF-CBE");
        assertThat(clusterBlue.getEndpoint()).isEqualTo("0");
        assertThat(clusterBlue.getCommandId()).isEqualTo("Report Attributes");
        assertThat(clusterBlue.getAttributId()).isEqualTo("CBE original");
        assertThat(clusterBlue.getAdresseCompteur()).isEqualTo("021528802756");
        assertThat(clusterBlue.getOptionTarif()).isEqualTo("Effacement des jours de pointe");
        assertThat(clusterBlue.getIntensiteSouscrite().getValue()).isEqualTo(45);
        assertThat(clusterBlue.getIntensiteSouscrite().getUnit()).isEqualTo("A");
        assertThat(clusterBlue.getEjpHeuresNormales().getValue()).isEqualTo(16658.0f);
        assertThat(clusterBlue.getEjpHeuresNormales().getUnit()).isEqualTo("Wh");
        assertThat(clusterBlue.getEjpHeuresPointeMobile().getValue()).isEqualTo(28806.0f);
        assertThat(clusterBlue.getEjpHeuresPointeMobile().getUnit()).isEqualTo("Wh");
        assertThat(clusterBlue.getPeriodeTarifaireEnCours()).isEqualTo("Heures normales");
        assertThat(clusterBlue.getIntensiteInstant().getValue()).isEqualTo(0);
        assertThat(clusterBlue.getIntensiteInstant().getUnit()).isEqualTo("A");
        assertThat(clusterBlue.getIntensiteMaxAppelee().getValue()).isEqualTo(8);
        assertThat(clusterBlue.getIntensiteMaxAppelee().getUnit()).isEqualTo("A");
        assertThat(clusterBlue.getPuissanceApp().getValue()).isEqualTo(120);
        assertThat(clusterBlue.getPuissanceApp().getUnit()).isEqualTo("VA");
    }

    @Test
    @PayloadDescription(name = "ClusterTICInformationERDF-CBE_TICMeterType", description = "Cluster: TIC Information ERDF-CBE, mode EJP, Attribut: TIC Meter Type, Command: Read Attribute Response.")
    public void should_decode_0054_TICbleu_attribut0010_cmd01_payload() throws JsDecodingException {
        /* real payload */
        String input = "110100540010002003";

        NKEClusterTICinformationCBE clusterBlue = formatAndDecode(SCRIPT_PATH, input, NKEClusterTICinformationCBE.class);
        assertThat(clusterBlue.getMessageType()).isEqualTo("TIC Information ERDF-CBE");
        assertThat(clusterBlue.getEndpoint()).isEqualTo("0");
        assertThat(clusterBlue.getCommandId()).isEqualTo("Read Attribute Response");
        assertThat(clusterBlue.getAttributId()).isEqualTo("TIC Meter Type");
        assertThat(clusterBlue.getStatus()).isEqualTo("Ok");
        assertThat(clusterBlue.getTIC_MeterType()).isEqualTo("Compteur Bleu Electrique Monophasé ICC");
    }

}
