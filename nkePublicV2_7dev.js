/**
 * Decoder for all NKE Watteco sensors
 *   v1.0 (public)  : From THIEBAUD Claudine TGI/OLS
 *   V2.4 (public)  : 27-09-2021 - C.THIEBAUD (OLS) - ajouts tags profile, ajout clusters 8008 800C 4006 0403, decodage commande 8A
 *   V2.5_dev (private)	:	25.05.2022 - Mathieu POUILLOT <mpouillot@watteco.fr> (WATTECO)
 *	 V2.5 (public)	:	06.06.2022 - Mathieu POUILLOT <mpouillot@watteco.fr> (WATTECO) + bug fix (08-09.06.2022)
 *   V2.6_dev (private) :   26.07.2022 - Lisa DUTERTE <lduterte@watteco.fr> (WATTECO) with some modifications
 *   V2.6 (public) :   03.08.2022 - Lisa DUTERTE <lduterte@watteco.fr> (WATTECO)
 *   V2.7_dev (private) : 02.02.2023 - Mathieu POUILLOT <mpouillot@watteco.fr> (WATTECO) correcting monito, ventilo
 */

function Fixfield(encoded, digitstart, digitend, name) {
	this.name = name;
	this.hexavalue = encoded.substring(digitstart, digitend + 1).toUpperCase();
}
Fixfield.prototype.postProcessMap = function (data, map) {
	var mapLocal = map;
	var targetValue = mapLocal[this.hexavalue];
	data[this.name] = targetValue;
	return data;
};

var mapEndpoint = {
	'11': '0',
	'31': '1',
	'51': '2',
	'71': '3',
	'91': '4',
	'B1': '5',
	'D1': '6',
	'F1': '7',
	'13': '8',
	'33': '9'
};

var mapCommandId = {
	'00': 'Read Attribute Request',
	'01': 'Read Attribute Response',
	'06': 'Configure Reporting',
	'07': 'Configure Reporting Response',
	'08': 'Read Reporting Configuration',
	'09': 'Read Reporting Configuration Response',
	'0A': 'Report Attributes',
	'8A': 'Threshold Report'
};

var mapCluster = {
	'0000': 'Basic',
	'0006': 'On/Off',
	'000C': 'Analog Input',
	'000F': 'Binary Input',
	'0013': 'MultiState Output',
	'0400': 'Illuminance Measurement',
	'0402': 'Temperature Measurement',
	'0403': 'Pressure Measurement',
	'0405': 'Relative Humidity Measurement',
	'0406': 'Occupancy',
	'0050': 'Configuration',
	'0052': 'Simple Metering-Like',
	'0053': 'TIC Information ERDF-ICE',
	'0054': 'TIC Information ERDF-CBE',
	'0055': 'TIC Information ERDF-CJE',
	'0056': 'TIC Information ERDF-STD',
	'8002': 'Senso Volume Meter',
	'8003': 'Senso',
	'8004': 'LoRaWAN',
	'8005': 'Multi Binary Input',
	'8006': 'Serial Interface',
	'8007': 'Serial Master/Slave Protocol',
	'8008': 'Differential Pressure Measurement',
	'8009': 'Multi Master/Slave answers',
	'8010': 'Energy and Power Multi Metering',
	'800A': 'Energy and Power Metering',
	'800B': 'Voltage and Current Metering',
	'800C': 'Concentration Measurement',
	'800D': 'Voltage and Current Multi Metering',
	'8052': 'Power Quality'
};

var mapStatusCommands = {
	'80': 'Malformed Command',
	'81': 'Unsupported Cluster Command',
	'82': 'Unsupported General Command',
	'86': 'Unsupported Attribute',
	'87': 'Invalid Field',
	'88': 'Invalid Value',
	'89': 'Insufficient Space',
	'8C': 'Unreportable Attribute',
	'C2': 'Batch Report: No Free Slot',
	'C3': 'Batch Report: Invalid Tag Size',
	'C4': 'Batch Report: Duplicate Tag Label',
	'C5': 'Batch Report: Label Out Of Range',
	'00': 'Ok'
};
var mapReport = {
	'00': 'Standard report',
	'01': 'Batch report',
};
var mapAnalogInputAttributId = {
	'0055': 'PresentValue',
	'0100': 'ApplicationType',
	'8003': 'PowerDuration'
};
var mapBasicAttributId = {
	'0002': 'Firmware Version',
	'0003': 'Kernel Version',
	'0004': 'Manufacturer',
	'0005': 'Model Identifier',
	'0006': 'Date Code',
	'0010': 'Location Description',
	'8001': 'Application Name'
};
var mapBinaryInputAttributId = {
	'0054': 'Polarity',
	'0055': 'PresentValue',
	'0100': 'ApplicationType',
	'0400': 'Edge Selection',
	'0401': 'Debounce Period',
	'0402': 'Count'
};
var mapConfigurationAttributId = {
	'0003': 'Current IPv6/port (UDP) connexion binded for reports',
	'0004': 'Endpoint/clusterID list managed by the sensor',
	'0005': 'Current configuration mode status',
	'0006': 'Node power descriptor'
};
var mapIlluminanceMeasurementAttributId = {
	'0000': 'MeasuredValue',
	'0001': 'MinMeasuredValue',
	'0002': 'MaxMeasuredValue'
};
var mapLorawanAttributId = {
	'0000': 'MessageType',
	'0001': 'RetryConfirmed',
	'0002': 'ReAssociation',
	'0003': 'DataRate',
	'0004': 'ABP_DevAddr',
	'0005': 'OTA_AppEUI'
};
var mapMultiBinaryInputAttributId = {
	'0000': 'PresentValue'
};
var mapMultiStateOutputAttributId = {
	'004A': 'NumberOfStates',
	'0100': 'ApplicationType',
	'0055': 'PresentValue'
};
var mapOnOffAttributId = {
	'0000': 'OnOFF relay state'
};
var mapPowerQualityAttributId = {
	'0000': 'Current',
	'0001': 'Sag Cycle Threshold',
	'0002': 'Sag Voltage Threshold',
	'0003': 'Over Voltage Threshold'
};
var mapRelativeHumidityMeasurementAttributId = {
	'0000': 'MeasuredValue',
	'0001': 'MinMeasuredValue',
	'0002': 'MaxMeasuredValue'
};
var mapSensoVolumeMeterAttributId = {
	'0000': 'Volume',
	'0001': 'VolumeDisplayMode',
	'0002': 'MinFlow',
	'0003': 'MaxFlow',
	'0004': 'FlowDisplayMode'
};
var mapSensoAttributId = {
	'0000': 'Status',
	'0001': 'CountDownThresholds',
	'0002': 'InstallationRotation',
	'0003': 'VolumeRotation',
	'0004': 'TemperatureMeterFreeze',
	'0005': 'TemperatureMinTxOff',
	'0006': 'ParametersLeakFlow'
};
var mapSerialInterfaceAttributId = {
	'0000': 'Speed',
	'0001': 'Data Bits',
	'0002': 'Parity',
	'0003': 'Stop Bits'
};
var mapSerialMasterSlaveProtocolAttributId = {
	'0000': 'Request',
	'0001': 'Answer',
	'0002': 'Application Type'
};

var mapMultiMasterSlaveAnswersAttributId = {
	'0000': 'PresentValue',
	'0001': 'HeaderOption'
};

var mapSimpleMeteringAttributId = {
	'0000': 'Current metering',
	'8000': 'Current calibration coefficient'
};
var mapTemperatureMeasurementAttributId = {
	'0000': 'MeasuredValue',
	'0001': 'MinMeasuredValue',
	'0002': 'MaxMeasuredValue'
};

var mapPressureMeasurementAttributId = {
	'0000': 'MeasuredValue',
	'0001': 'MinMeasuredValue',
	'0002': 'MaxMeasuredValue'
};

var mapTICinformationCbeAttributId = {
	'0000': 'CBE original',
	'0010': 'TIC Meter Type'
};

var mapDifferentialPressureMeasurementAttributId = {
	'0000': 'Measured value',
	'0001': 'Min measured value',
	'0002': 'Max measured value',
	'0003': 'Measurement period',
	'0004': 'Samples per measurement',
	'0005': 'Samples per confirmation measurement',
	'0006': 'Sampling period',
	'0100': 'Mean measured value since last report',
	'0101': 'Minimal measured value since last report',
	'0102': 'Maximal measured value since last report'
};

var mapConcentrationMeasurementAttributId = {
	'0000': 'Measured value',
	'0100': 'Mean measured value',
	'0101': 'Minimum measured value',
	'0102': 'Maximum measured value',
	'0001': 'Concentration level classification',
	'8004': 'Concentration unit',
	'8010': 'Classification levels',
	'8020': 'HMI display period',
	'8008': 'Minimal/Normal concentration level',
	'8009': 'Calibration period'
};

var mapOccupancyAttributId = {
	'0000': 'Occupancy value',
	'0010': 'Occupied to Unoccupied delay',
	'0001': 'Occupancy Type'
};

var mapEnergyPowerMeteringAttributId = {
	'0000': 'Measured values'
};

var mapEnergyPowerMultiMeteringAttributId = {
	'0000': 'Energy Measured values',
	'0001': 'Power Measured values'
};
var mapVoltageCurrentMeteringAttributId = {
	'0000': 'Measured values'
};

var mapVoltageCurrentMultiMeteringAttributId = {
	'0000': 'Measured values'
};

var mapCurrentPowerMode = {
	'00': 'ON when idle',
	'01': 'Periodically ON',
	'02': 'ON on user event',
	'03': 'Reserved'
};
var mapCurrentPowerSources = {
	'01': 'Constant (Main) Or External Power',
	'02': 'Rechargeable Battery',
	'04': 'Diposable Battery',
	'08': 'Solar Harvesting',
	'10': 'TIC Harvesting'
};
var mapTIC_MeterType = {
	'00': 'Unknown',
	'01': 'Concentrateur teleport',
	'02': 'Compteur Bleu Electrique Monophasé',
	'03': 'Compteur Bleu Electrique Monophasé ICC',
	'04': 'Compteur Bleu Electrique Triphasé',
	'05': 'Compteur Jaune Electronique',
	'06': 'Compteur Interface Clientelle Emeraude',
	'07': 'TIC Standard (Linky)'
};


var decode = function (encoded, dataMessage) {
	try {
		if (encoded == null) {
			throw new Error("empty payload");
		}
		if (dataMessage == null) {
			throw new Error("empty dataMessage");
		}
		if (encoded.length < 4) {
			throw new Error("incomplete payload");
		}
		if (isBatchPayload(encoded))
			return decodeBatch(encoded, dataMessage);
		else return decodeStandard(encoded, dataMessage);
	} catch (e) {
		return "{\"error\":\"decoding failed : " + e.message + "\"}";
	}
};
function decodeStandard(encoded, dataMessage) {
	encoded = encoded.toUpperCase();
	var endpoint = new Fixfield(encoded, 0, 1, 'endpoint');
	var cmdid = new Fixfield(encoded, 2, 3, 'commandId');
	var cluster = new Fixfield(encoded, 4, 7, 'messageType');
	var framedsl = 'ubyte endpoint; ubyte command_id; ubyte[2] cluster; ';

	var mapAttributId;
	var decodeFunction;
	switch (cluster.hexavalue) {
		case '0000':
			mapAttributId = mapBasicAttributId;
			decodeFunction = decodeBasicCluster;
			break;
		case '0006':
			mapAttributId = mapOnOffAttributId;
			decodeFunction = decodeOnOffCluster;
			break;
		case '000C':
			mapAttributId = mapAnalogInputAttributId;
			decodeFunction = decodeAnalogInputBasicCluster;
			break;
		case '000F':
			mapAttributId = mapBinaryInputAttributId;
			decodeFunction = decodeBinaryInputBasicCluster;
			break;
		case '0013':
			mapAttributId = mapMultiStateOutputAttributId;
			decodeFunction = decodeMultiStateOutputCluster;
			break;
		case '0052':
			mapAttributId = mapSimpleMeteringAttributId;
			decodeFunction = decodeSimpleMeteringCluster;
			break;
		case '0400':
			mapAttributId = mapIlluminanceMeasurementAttributId;
			decodeFunction = decodeIlluminanceMeasurementCluster;
			break;
		case '0402':
			mapAttributId = mapTemperatureMeasurementAttributId;
			decodeFunction = decodeTemperatureMeasurementCluster;
			break;
		case '0403':
			mapAttributId = mapPressureMeasurementAttributId;
			decodeFunction = decodePressureMeasurementCluster;
			break;

		case '0405':
			mapAttributId = mapRelativeHumidityMeasurementAttributId;
			decodeFunction = decodeRelativeHumidityMeasurementCluster;
			break;
		case '0406':
			mapAttributId = mapOccupancyAttributId;
			decodeFunction = decodeOccupancyCluster;
			break;
		case '0050':
			mapAttributId = mapConfigurationAttributId;
			decodeFunction = decodeConfigurationCluster;
			break;
		case '0053':
			decodeFunction = decodeTICinformationIceCluster;
			break;
		case '0054':
			mapAttributId = mapTICinformationCbeAttributId;
			decodeFunction = decodeTICinformationCbeCluster;
			break;
		case '0055':
			decodeFunction = decodeTICinformationCjeCluster;
			break;
		case '0056':
			decodeFunction = decodeTICinformationStdCluster;
			break;
		case '8002':
			mapAttributId = mapSensoVolumeMeterAttributId;
			decodeFunction = decodeSensoVolumeMeterCluster;
			break;
		case '8003':
			mapAttributId = mapSensoAttributId;
			decodeFunction = decodeSensoCluster;
			break;
		case '8004':
			mapAttributId = mapLorawanAttributId;
			decodeFunction = decodeLorawanCluster;
			break;
		case '8005':
			mapAttributId = mapMultiBinaryInputAttributId;
			decodeFunction = decodeMultiBinaryInputCluster;
			break;
		case '8006':
			mapAttributId = mapSerialInterfaceAttributId;
			decodeFunction = decodeSerialInterfaceCluster;
			break;
		case '8007':
			mapAttributId = mapSerialMasterSlaveProtocolAttributId;
			decodeFunction = decodeSerialMasterSlaveProtocolCluster;
			break;
		case '8008':
			mapAttributId = mapDifferentialPressureMeasurementAttributId;
			decodeFunction = decodeDifferentialPressureMeasurementCluster;
			break;
		case '8009':
			mapAttributId = mapMultiMasterSlaveAnswersAttributId;
			decodeFunction = decodeMultiMasterSlaveAnswersCluster;
			break;
		case '8010':
			mapAttributId = mapEnergyPowerMultiMeteringAttributId;
			decodeFunction = decodeEnergyPowerMultiMeteringCluster;
			break;
		case '800A':
			mapAttributId = mapEnergyPowerMeteringAttributId;
			decodeFunction = decodeEnergyPowerMeteringCluster;
			break;
		case '800B':
			mapAttributId = mapVoltageCurrentMeteringAttributId;
			decodeFunction = decodeVoltageCurrentMeteringCluster;
			break;
		case '800C':
			mapAttributId = mapConcentrationMeasurementAttributId;
			decodeFunction = decodeConcentrationMeasurementCluster;
			break;
		case '800D':
			mapAttributId = mapVoltageCurrentMultiMeteringAttributId;
			decodeFunction = decodeVoltageCurrentMultiMeteringCluster;
			break;
		case '8052':
			mapAttributId = mapPowerQualityAttributId;
			decodeFunction = decodePowerQualityCluster;
			break;
		default:
			break;
	}

	var status;
	var direction;
	var attributId;
	var attributType;
	var decoded;
	var data = {};

	if (cmdid.hexavalue == '07') {
		/* Configure reporting response, All frames with cmdid 07 have the same format */
		status = new Fixfield(encoded, 8, 9, 'status');
		if (encoded.length > 10) {
			direction = new Fixfield(encoded, 10, 11, 'report');
			attributId = new Fixfield(encoded, 12, 15, 'attributId');
			framedsl += ' ubyte status;ubyte direction ;byte[2] attribut_id;';
			try {
				decoded = BinaryDecoder.decode(framedsl, encoded);
				data = JSON.parse(decoded);
			} catch (e) {
				return "{\"error\":\"decoding failed\"}";
			}
		}
		else {
			framedsl += ' ubyte status;';
			try {
				decoded = BinaryDecoder.decode(framedsl, encoded);
				data = JSON.parse(decoded);
			} catch (e) {
				return "{\"error\":\"decoding failed\"}";
			}
		}

		data = postProcessEndpointCommandIdAndCluster(data);
		if (encoded.length > 10) {
			delete data.direction;
			data = direction.postProcessMap(data, mapReport);
			delete data.attribut_id;
			data = attributId.postProcessMap(data, mapAttributId);
		}
		delete data.status;
		data = status.postProcessMap(data, mapStatusCommands);
	} else if ((cmdid.hexavalue == '01') || (cmdid.hexavalue == '0A') || (cmdid.hexavalue == '8A')) {
		attributId = new Fixfield(encoded, 8, 11, 'attributId');
		if (cmdid.hexavalue == '01') { /* Read attribute response */
			framedsl += 'ubyte[2] attribut_id; byte status; ';
			status = new Fixfield(encoded, 12, 13, 'status');
			attributType = new Fixfield(encoded, 14, 15, 'attributType');
			if (status.hexavalue != '00') {
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
			} else {
				framedsl += ' byte attribut_type;';
				data = decodeFunction(encoded, dataMessage);
			}
		} else if (cmdid.hexavalue == '0A' || (cmdid.hexavalue == '8A')) { /* Report attributes */
			attributType = new Fixfield(encoded, 12, 13, 'attributType');
			framedsl += 'ubyte[2] attribut_id; byte attribut_type; ';
			data = decodeFunction(encoded, dataMessage);
		}
	}
	return JSON.stringify(data);

	/* cluster data decoding functions */
	function decodeBasicCluster(encoded) {
		var data;
		var decoded;
		if (attributId.hexavalue == '0002' && attributType.hexavalue == '0D') { /* Firmware Version */
			var rcbuild;
			if (cmdid.hexavalue == '01') {
				rcbuild = new Fixfield(encoded, 22, 27, 'RCBuild');
			}
			framedsl += ' ubyte major; ubyte minor; ubyte revision; ubyte[3] rcbuild;';
			try {
				decoded = BinaryDecoder.decode(framedsl, encoded);
				data = JSON.parse(decoded);
			} catch (e) {
				return "{\"error\":\"decoding failed\"}";
			}
			data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
			data.firmware = {};
			var versionMajor = String(Math.round(data.major));
			var versionMinor = String(Math.round(data.minor));
			var versionRevision = String(Math.round(data.revision));
			data.firmware.version = versionMajor + '.' + versionMinor + '.'
				+ versionRevision;
			delete data.major;
			delete data.minor;
			delete data.revision;
			delete data.rcbuild;
			data.firmware.RCBuild = parseInt(rcbuild.hexavalue, 16);
		} else if (attributId.hexavalue == '0003' || /* 'Kernel Version */
			attributId.hexavalue == '0004' || /* 'Manufacturer */
			attributId.hexavalue == '0005' || /* 'Model identifier */
			attributId.hexavalue == '0006' || /* 'Date Code */
			attributId.hexavalue == '0010' || /* 'Location description */
			attributId.hexavalue == '8001') /* 'Application name */ {
			if (attributType.hexavalue != '42') {
				return "{\"error\":\"wrong attributType\"}";
			}
			framedsl += ' ubyte length; utf8[length] name;';
			try {
				decoded = BinaryDecoder.decode(framedsl, encoded);
				data = JSON.parse(decoded);
			} catch (e) {
				return "{\"error\":\"decoding failed\"}";
			}
			data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
			delete data.length;
			switch (attributId.hexavalue) {
				case '0003':
					data.KernelVersion = data.name;
					break;
				case '0004':
					data.Manufacturer = data.name;
					break;
				case '0005':
					data.ModelIdentifier = data.name;
					break;
				case '0006':
					data.DateCode = data.name;
					break;
				case '0010':
					data.LocationDescription = data.name;
					break;
				case '8001':
					data.ApplicationName = data.name;
					break;
			}
			delete data.name;
		}
		return (data);
	}

	function decodeOnOffCluster(encoded) {
		var data;
		var decoded;
		if (attributId.hexavalue == '0000') {
			if (attributType.hexavalue != '10') {
				return "{\"error\":\"wrong attributType\"}";
			}
			framedsl += 'ubyte onoff_value ;';
			try {
				decoded = BinaryDecoder.decode(framedsl, encoded);
				data = JSON.parse(decoded);
			} catch (e) {
				return "{\"error\":\"decoding failed\"}";
			}
			data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
			data.relayState = 'undefined';
			if (data.onoff_value == 0) {
				data.relayState = 'OFF';
			}
			if (data.onoff_value == 1) {
				data.relayState = 'ON';
			}
			delete data.onoff_value;
		}
		return (data);
	}

	function decodePowerQualityCluster(encoded) {
		var data;
		var decoded;
		switch (attributId.hexavalue) {
			case '0000': /* Current */
				if (attributType.hexavalue != '41') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'ubyte lg18 ; ushort freq; ushort freqmin; ushort freqmax; ' +
					'ushort vrms; ushort vrmsmin; ushort vrmsmax;' +
					'ushort vpeak; ushort vpeakmin; ushort vpeakmax;' +
					'ushort overvoltagenb; ushort sagnb; ushort brownoutnb;';

				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				delete data.lg18;
				data.current = {};
				data.current.freq = {};
				data.current.freq.unit = "Hz";
				data.current.freq.freq = (data.freq + 22232) / 1000;
				data.current.freq.freqmin = (data.freqmin + 22232) / 1000;
				data.current.freq.freqmax = (data.freqmax + 22232) / 1000;
				delete data.freq;
				delete data.freqmin;
				delete data.freqmax;

				data.current.vrms = {};
				data.current.vrms.unit = "V";
				data.current.vrms.vrms = data.vrms / 10;
				data.current.vrms.vrmsmin = data.vrmsmin / 10;
				data.current.vrms.vrmsmax = data.vrmsmax / 10;
				delete data.vrms;
				delete data.vrmsmin;
				delete data.vrmsmax;

				data.current.vpeak = {};
				data.current.vpeak.unit = "V";
				data.current.vpeak.vpeak = data.vpeak / 10;
				data.current.vpeak.vpeakmin = data.vpeakmin / 10;
				data.current.vpeak.vpeakmax = data.vpeakmax / 10;
				delete data.vpeak;
				delete data.vpeakmin;
				delete data.vpeakmax;

				data.current.overVoltageNumber = data.overvoltagenb;
				data.current.sagNumber = data.sagnb;
				data.current.brownoutNumber = data.brownoutnb;
				delete data.overvoltagenb;
				delete data.sagnb;
				delete data.brownoutnb;
				break;
			case '0001': /* Sag Cycle Threshold */
				if (attributType.hexavalue != '20') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'ubyte sagcyclthr ;';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				data.sagCycleThreshold = data.sagcyclthr;
				delete data.sagcyclthr;
				break;
			case '0002': /* Sag Voltage Threshold */
				if (attributType.hexavalue != '21') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'ushort sagvoltthr ;';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				data.sagVoltageThreshold = {};
				data.sagVoltageThreshold.unit = 'V';
				data.sagVoltageThreshold.value = (data.sagvoltthr) / 10;
				delete data.sagvoltthr;
				break;
			case '0003': /* Over Voltage Threshold */
				if (attributType.hexavalue != '21') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'ushort overvoltthr ;';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				data.overVoltageThreshold = {};
				data.overVoltageThreshold.unit = 'V';
				data.overVoltageThreshold.value = (data.overvoltthr) / 10;
				delete data.overvoltthr;
				break;
		}
		return (data);
	}

	function decodeAnalogInputBasicCluster(encoded) {
		var data;
		var decoded;
		switch (attributId.hexavalue) {
			case '0055': /* PresentValue */
				if (attributType.hexavalue != '39') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'int present_value ;';
				if (cmdid.hexavalue == '8A') framedsl += 'bit batch; bit nohp; bit secu; bit secifa; bit:2 cr; bit todel;bit nmc; ';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				data.value = calculateType39FromDslInt(data.present_value, 6);
				delete data.present_value;
				if (cmdid.hexavalue == '8A') {
					data = decodeCommand8A(encoded, data, attributType.hexavalue, false);
				}
				break;
			case '0100': /* applicationType */
				var appType;
				if (attributType.hexavalue != '23') {
					return "{\"error\":\"wrong attributType\"}";
				}
				if (cmdid.hexavalue == '01') {
					appType = new Fixfield(encoded, 16, 23, 'attributType');
				} else if (cmdid.hexavalue == '0A') {
					appType = new Fixfield(encoded, 14, 21, 'attributType');
				}
				framedsl += 'ubyte[4] application_type;';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				delete data.application_type;
				if (appType.hexavalue == '00050000') {
					data.applicationType = 'Carbon Dioxide AI application';
					data.applicationUnit = 'PPM';
				} else if (appType.hexavalue == '00FF0000') {
					data.applicationType = 'Group Analog Input(AI) Type Others';
					data.applicationUnit = 'mA';
				} else if (appType.hexavalue == '00FF0001') {
					data.applicationType = 'Group Analog Input(AI) Type Others';
					data.applicationUnit = 'mV';
				}
				break;
			case '8003': /* PowerDuration */
				if (attributType.hexavalue != '21') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'ushort power_duration ;';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				data.powerDuration = {};
				data.powerDuration.unit = 'ms';
				data.powerDuration.value = data.power_duration;
				delete data.power_duration;
				break;
		}
		return (data);
	}

	function decodeBinaryInputBasicCluster(encoded) {
		var data;
		var decoded;
		switch (attributId.hexavalue) {
			case '0402': /* count */
				if (attributType.hexavalue != '23') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'ubyte[4] counter ;';
				if (cmdid.hexavalue == '8A') framedsl += 'bit batch; bit nohp; bit secu; bit secifa; bit:2 cr; bit todel;bit nmc; ';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				data.counterCurrentValue = calculateType23FromDslTab4Ubytes(data.counter);
				delete data.counter;
				if (cmdid.hexavalue == '8A') {
					data = decodeCommand8A(encoded, data, attributType.hexavalue, false);
				}
				break;
			case '0054': /* Polarity */
			case '0055': /* PresentValue */
				if (attributType.hexavalue != '10') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'ubyte binary_value ;';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				if (attributId.hexavalue == '0054') {
					if (data.binary_value == 0) data.polarity = "Normal";
					if (data.binary_value == 1) data.polarity = "Reversed";
				}
				else if (attributId.hexavalue == '0055') {
					data.value = String(data.binary_value);
				}
				delete data.binary_value;
				break;
			case '0400': /* Edge selection*/
				if (attributType.hexavalue != '18') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'ubyte code_value ;';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				data.edgeSelection = "";
				switch (Number(data.code_value)) {
					case 1: data.edgeSelection = "Falling edge"; break;
					case 2: data.edgeSelection = "Rising Edge"; break;
					case 3: data.edgeSelection = "Both Rising and falling"; break;
					case 4: data.edgeSelection = "Polling"; break;
					default: data.edgeSelection = "Undefined"; break;
				}
				delete data.code_value;
				break;
			case '0401': /* Debounce period */
				if (attributType.hexavalue != '21') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'ushort debounce ;';
				if (cmdid.hexavalue == '8A') framedsl += 'bit batch; bit nohp; bit secu; bit secifa; bit:2 cr; bit todel;bit nmc; ';

				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				data.debouncePeriod = {
					value: data.debounce,
					unit: "milliseconds"
				};
				delete data.debounce;
				if (cmdid.hexavalue == '8A') {
					data = decodeCommand8A(encoded, data, attributType.hexavalue, false);
				}
				break;
		}
		return (data);
	}

	function decodeMultiBinaryInputCluster(encoded) {
		var data;
		var decoded;
		if (attributId.hexavalue == '0000') { /* PresentValue */
			if (attributType.hexavalue != '19') {
				return "{\"error\":\"wrong attributType\"}";
			}
			framedsl += ' ushort inputfield;';
			try {
				decoded = BinaryDecoder.decode(framedsl, encoded);
				data = JSON.parse(decoded);
			} catch (e) {
				return "{\"error\":\"decoding failed\"}";
			}
			var binValue;
			binvalue = ("0000000000000000" + (parseInt(data.inputfield, 10)).toString(2)).substr(-16);
			data.binaryValues = {
				input1Value: (parseInt(binvalue.charAt(15), 16) == 1),
				input2Value: (parseInt(binvalue.charAt(14), 16) == 1),
				input3Value: (parseInt(binvalue.charAt(13), 16) == 1),
				input4Value: (parseInt(binvalue.charAt(12), 16) == 1),
				input5Value: (parseInt(binvalue.charAt(11), 16) == 1),
				input6Value: (parseInt(binvalue.charAt(10), 16) == 1),
				input7Value: (parseInt(binvalue.charAt(9), 16) == 1),
				input8Value: (parseInt(binvalue.charAt(8), 16) == 1),
				input9Value: (parseInt(binvalue.charAt(7), 16) == 1),
				input10Value: (parseInt(binvalue.charAt(6), 16) == 1)
			};
			delete data.inputfield;
			data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
		}
		return (data);
	}

	function decodeSerialInterfaceCluster(encoded) {
		var data;
		var decoded;
		switch (attributId.hexavalue) {
			case '0000': /* Speed */
				if (attributType.hexavalue != '22') {
					return "{\"error\":\"wrong attributType\"}";
				}
				var speedval = parseInt(encoded.substring(16, 22), 16);
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				data.speed = {
					value: speedval,
					unit: "bit/s"
				};
				break;
			case '0001': /* databits */
				if (attributType.hexavalue != '20') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += ' ubyte data_bits;';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				data.dataBits = data.data_bits;
				delete data.data_bits;
				break;
			case '0002': /* parity */
				if (attributType.hexavalue != '20') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += ' ubyte parity_value;';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				switch (data.parity_value) {
					case 0:
						data.parity = "None";
						break;
					case 1:
						data.parity = "Odd";
						break;
					case 2:
						data.parity = "Even";
						break;
					default:
						data.parity = "Unknown";
						break;
				}
				delete data.parity_value;
				break;
			case '0003': /* stopBits */
				if (attributType.hexavalue != '20') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += ' ubyte stop_bits;';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				data.stopBits = data.stop_bits;
				delete data.stop_bits;
				break;
		}
		return (data);
	}

	function decodeSerialMasterSlaveProtocolCluster(encoded, dataMessage) {
		var data;
		var decoded;
		switch (attributId.hexavalue) {
			case '0000': /* Request */
				if (attributType.hexavalue != '41') {
					return "{\"error\":\"wrong attributType\"}";
				}
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				data.requestFrame = encoded.substring(18);
				break;

			case '0001': /* Answer */
				if (attributType.hexavalue != '41') {
					return "{\"error\":\"wrong attributType\"}";
				}
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);

				var answerFrame = "";
				if (cmdid.hexavalue == '01') {
					answerFrame = encoded.substring(18);
				}
				else {
					answerFrame = encoded.substring(16);
				}
				var tabtags = [];
				try {
					var dataMess = JSON.parse(dataMessage);
					if (dataMess == null) throw new Error("empty dataMessage");
					for (var i = 0; i < dataMess.tags.length; i++) {
						if (dataMess.tags[i] != null) tabtags.push(dataMess.tags[i]);
					}
				}
				catch (e) {
					throw new Error(e.message);
				}

				if (tabtags.length === 0) {
					data.answerFrame = answerFrame;
					return (data);
				}
				data = postProcessModbus8007Answer(answerFrame, tabtags, data);

				break;


			case '0002': /* Application Type */
				if (attributType.hexavalue != '20') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += ' ubyte app_type;';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				switch (data.app_type) {
					case 0:
						data.applicationType = "ModBus";
						break;
					default:
						data.applicationType = "Unknown";
						break;
				}
				delete data.app_type;
				break;
		}

		return (data);
	}

	function decodeMultiMasterSlaveAnswersCluster(encoded, dataMessage) {
		var data;
		var decoded;
		switch (attributId.hexavalue) {

			case '0000': /* PresentValue */
				if (attributType.hexavalue != '41') {
					return "{\"error\":\"wrong attributType\"}";
				}
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);

				var presentValue = "";
				if (cmdid.hexavalue == '01') {
					presentValue = encoded.substring(18);
				}
				else {
					presentValue = encoded.substring(16);
				}
				var tabtags = [];
			/*var header = 1 */;
				try {
					var dataMess = JSON.parse(dataMessage);
					if (dataMess == null) throw new Error("empty dataMessage");
					for (var i = 0; i < dataMess.tags.length; i++) {
						if (dataMess.tags[i] != null) tabtags.push(dataMess.tags[i]);
					}
				}
				catch (e) {
					throw new Error(e.message);
				}

				if (tabtags.length === 0) {
					data.presentValue = presentValue;
					return (data);
				}
				data = postProcessModbus8009PresentValue(presentValue, tabtags, data);

				break;


			case '0001': /* Header option */
				if (attributType.hexavalue != '10') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'ubyte header_option ;';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				if (data.header_option == 0) {
					data.headerOption = false;
				}
				if (data.header_option == 1) {
					data.headerOption = true;
				}
				delete data.header_option;
				break;
		}

		return (data);
	}

	function decodeMultiStateOutputCluster(encoded) {
		var data;
		var decoded;
		switch (attributId.hexavalue) {
			case '004A': /* NumberOfStates */
				if (attributType.hexavalue != '20') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'ubyte numberstates ;';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				data.numberOfStates = data.numberstates;
				delete data.numberstates;
				break;
			case '0100': /* applicationType */
				var appType;
				if (attributType.hexavalue != '23') {
					return "{\"error\":\"wrong attributType\"}";
				}
				if (cmdid.hexavalue == '01') {
					appType = new Fixfield(encoded, 16, 23, 'attributType');
				} else if (cmdid.hexavalue == '0A') {
					appType = new Fixfield(encoded, 14, 21, 'attributType');
				}
				framedsl += 'ubyte[4] application_type;';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				delete data.application_type;
				if (appType.hexavalue == '0EFFFFFF') {
					data.applicationType = 'Default';
				} else if (appType.hexavalue == '0E002000') {
					data.applicationType = 'IPSensor-PilotWire implements : Pilot wire';
				}
				break;
			case '0055': /* PresentValue */
				if (attributType.hexavalue != '20') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'ubyte presentvalue ;';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				data.presentValue = data.presentvalue;
				delete data.presentvalue;
				break;
		}
		return (data);
	}

	function decodeSimpleMeteringCluster(encoded) {
		var data;
		var decoded;
		switch (attributId.hexavalue) {
			case '0000':
				var activeEnergySum;
				var reactiveEnergySum;
				if (attributType.hexavalue != '41') {
					return "{\"error\":\"wrong attributType\"}";
				}
				if (cmdid.hexavalue == '01') {
					activeEnergySum = new Fixfield(encoded, 18, 23, 'aesum');
					reactiveEnergySum = new Fixfield(encoded, 24, 29, 'resum');
				} else if (cmdid.hexavalue == '0A') {
					activeEnergySum = new Fixfield(encoded, 16, 21, 'aesum');
					reactiveEnergySum = new Fixfield(encoded, 22, 27, 'resum');
				}
				framedsl += ' ubyte data_length;'
					+ 'byte[3] active_energy_summation; '
					+ 'byte[3] reactive_energy_summation; '
					+ 'ushort sample_number;   ' + 'short active_power;  '
					+ 'short reactive_power;  ';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				delete data.data_length;
				data.activeEnergySum = {};
				data.activeEnergySum.value = parseSignedIntFrom3bytes(activeEnergySum.hexavalue);
				data.activeEnergySum.unit = 'Wh';
				delete data.active_energy_summation;
				data.reactiveEnergySum = {};
				data.reactiveEnergySum.value = parseSignedIntFrom3bytes(reactiveEnergySum.hexavalue);
				data.reactiveEnergySum.unit = 'VARh';
				delete data.reactive_energy_summation;
				data.sampleNumber = data.sample_number;
				delete data.sample_number;
				data.activePower = {};
				data.activePower.value = data.active_power;
				data.activePower.unit = 'W';
				delete data.active_power;
				data.reactivePower = {};
				data.reactivePower.value = data.reactive_power;
				data.reactivePower.unit = 'VAR';
				delete data.reactive_power;
				break;
			case '8000':
				if (attributType.hexavalue != '41') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += ' ubyte data_length;' + 'ubyte etopot; '
					+ 'short active_power_multiplier; '
					+ 'short active_power_divisor;   '
					+ 'short reactive_power_multiplier; '
					+ 'short reactive_power_divisor;   ';

				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				delete data.data_length;
				data.currentE2Pot = Number(data.etopot);
				delete data.etopot;

				data.activePowerMultiplier = Number(data.active_power_multiplier);
				delete data.active_power_multiplier;
				data.reactivePowerMultiplier = Number(data.reactive_power_multiplier);
				delete data.reactive_power_multiplier;

				data.activePowerDivisor = Number(data.active_power_divisor);
				delete data.active_power_divisor;
				data.reactivePowerDivisor = Number(data.reactive_power_divisor);
				delete data.reactive_power_divisor;

				break;
		}
		return (data);
	}

	function decodeVoltageCurrentMeteringCluster(encoded) {
		var data;
		var decoded;
		switch (attributId.hexavalue) {
			case '0000':
				var vrms;
				var irms;
				var angle;
				if (attributType.hexavalue != '41') {
					return "{\"error\":\"wrong attributType\"}";
				}

				framedsl += ' ubyte data_length;'
					+ 'short Vrms_measured; '
					+ 'short Irms_measured; '
					+ 'short Angle_measured; ';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				delete data.data_length;
				data.vrms = {};
				data.vrms.value = Number(data.vrms_measured) / 10;
				data.vrms.unit = 'V';
				delete data.vrms_measured;
				data.irms = {};
				data.irms.value = Number(data.irms_measured) / 10;
				data.irms.unit = 'A';
				delete data.irms_measured;

				data.angle = {};
				data.angle.value = Number(data.angle_measured);
				data.angle.unit = '°';
				delete data.angle_measured;
				break;
		}
		return (data);
	}

	function decodeVoltageCurrentMultiMeteringCluster(encoded) {
		var data;
		var decoded;
		switch (attributId.hexavalue) {
			case '0000':
				var vrmsA;
				var irmsA;
				var angleA;
				var vrmsB;
				var irmsB;
				var angleB;
				var vrmsC;
				var irmsC;
				var angleC;
				if (attributType.hexavalue != '41') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += ' ubyte data_length;'
					+ 'short VrmsA_measured; '
					+ 'short IrmsA_measured; '
					+ 'short AngleA_measured; '
					+ 'short VrmsB_measured; '
					+ 'short IrmsB_measured; '
					+ 'short AngleB_measured; '
					+ 'short VrmsC_measured; '
					+ 'short IrmsC_measured; '
					+ 'short AngleC_measured; ';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				delete data.data_length;

				data.vrmsA = {};
				data.vrmsA.value = Number(data.vrmsa_measured) / 10;
				data.vrmsA.unit = 'V';
				delete data.vrmsa_measured;

				data.irmsA = {};
				data.irmsA.value = Number(data.irmsa_measured) / 10;
				data.irmsA.unit = 'A';
				delete data.irmsa_measured;

				data.angleA = {};
				data.angleA.value = Number(data.anglea_measured);
				data.angleA.unit = '°';
				delete data.anglea_measured;

				data.vrmsB = {};
				data.vrmsB.value = Number(data.vrmsb_measured) / 10;
				data.vrmsB.unit = 'V';
				delete data.vrmsb_measured;

				data.irmsB = {};
				data.irmsB.value = Number(data.irmsb_measured) / 10;
				data.irmsB.unit = 'A';
				delete data.irmsb_measured;

				data.angleB = {};
				data.angleB.value = Number(data.angleb_measured);
				data.angleB.unit = '°';
				delete data.angleb_measured;

				data.vrmsC = {};
				data.vrmsC.value = Number(data.vrmsc_measured) / 10;
				data.vrmsC.unit = 'V';
				delete data.vrmsc_measured;

				data.irmsC = {};
				data.irmsC.value = Number(data.irmsc_measured) / 10;
				data.irmsC.unit = 'A';
				delete data.irmsc_measured;

				data.angleC = {};
				data.angleC.value = Number(data.anglec_measured);
				data.angleC.unit = '°';
				delete data.anglec_measured;
				break;
		}
		return (data);
	}
	function decodeEnergyPowerMeteringCluster(encoded) {
		var data;
		var decoded;
		switch (attributId.hexavalue) {
			case '0000':
				var SumPosActEnergy;
				var SumNegActEnergy;
				var SumPosReactEnergy;
				var SumNegReactEnergy;
				var PosActPow;
				var NegActPow;
				var PosReactPow;
				var NegReactPow;

				framedsl += ' ubyte data_length;'
					+ 'ubyte[4] SumPosActEnergy_measured; '
					+ 'ubyte[4] SumNegActEnergy_measured; '
					+ 'ubyte[4] SumPosReactEnergy_measured; '
					+ 'ubyte[4] SumNegReactEnergy_measured; '
					+ 'ubyte[4] PosActPow_measured; '
					+ 'ubyte[4] NegActPow_measured; '
					+ 'ubyte[4] PosReactPow_measured; '
					+ 'ubyte[4] NegReactPow_measured; ';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				delete data.data_length;
				data.SumPosActEnergy = {};
				data.SumPosActEnergy.value = calculateType23FromDslTab4Ubytes(data.sumposactenergy_measured);
				data.SumPosActEnergy.unit = 'Wh/kWh';
				delete data.sumposactenergy_measured;

				data.SumNegActEnergy = {};
				data.SumNegActEnergy.value = calculateType23FromDslTab4Ubytes(data.sumnegactenergy_measured);
				data.SumNegActEnergy.unit = 'Wh/kWh';
				delete data.sumnegactenergy_measured;

				data.SumPosReactEnergy = {};
				data.SumPosReactEnergy.value = calculateType23FromDslTab4Ubytes(data.sumposreactenergy_measured);
				data.SumPosReactEnergy.unit = 'VARh/kVARh';
				delete data.sumposreactenergy_measured;

				data.SumNegReactEnergy = {};
				data.SumNegReactEnergy.value = calculateType23FromDslTab4Ubytes(data.sumnegreactenergy_measured);
				data.SumNegReactEnergy.unit = 'VARh/kVARh';
				delete data.sumnegreactenergy_measured;

				data.PosActPow = {};
				data.PosActPow.value = calculateType23FromDslTab4Ubytes(data.posactpow_measured);
				data.PosActPow.unit = 'W';
				delete data.posactpow_measured;

				data.NegActPow = {};
				data.NegActPow.value = calculateType23FromDslTab4Ubytes(data.negactpow_measured);
				data.NegActPow.unit = 'W';
				delete data.negactpow_measured;

				data.PosReactPow = {};
				data.PosReactPow.value = calculateType23FromDslTab4Ubytes(data.posreactpow_measured);
				data.PosReactPow.unit = 'VAR';
				delete data.posreactpow_measured;

				data.NegReactPow = {};
				data.NegReactPow.value = calculateType23FromDslTab4Ubytes(data.negreactpow_measured);
				data.NegReactPow.unit = 'VAR';
				delete data.negreactpow_measured;

				break;
		}
		return (data);
	}

	function decodeEnergyPowerMultiMeteringCluster(encoded) {
		var data;
		var decoded;
		switch (attributId.hexavalue) {
			case '0000':
				var SumActEnergyPhaseA;
				var SumActEnergyPhaseB;
				var SumActEnergyPhaseC;
				var SumActEnergyPhaseABC;
				var SumReactEnergyPhaseA;
				var SumReactEnergyPhaseB;
				var SumReactEnergyPhaseC;
				var SumReactEnergyPhaseABC;

				framedsl += ' ubyte data_length;'
					+ 'ubyte[4] SumActEnergyPhaseA_measured; '
					+ 'ubyte[4] SumActEnergyPhaseB_measured; '
					+ 'ubyte[4] SumActEnergyPhaseC_measured; '
					+ 'ubyte[4] SumActEnergyPhaseABC_measured; '
					+ 'ubyte[4] SumReactEnergyPhaseA_measured; '
					+ 'ubyte[4] SumReactEnergyPhaseB_measured; '
					+ 'ubyte[4] SumReactEnergyPhaseC_measured; ';
				+ 'ubyte[4] SumReactEnergyPhaseABC_measured; ';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				delete data.data_length;
				data.SumActEnergyPhaseA = {};
				data.SumActEnergyPhaseA.value = calculateType23FromDslTab4Ubytes(data.sumactenergyphasea_measured);
				data.SumActEnergyPhaseA.unit = 'Wh/kWh';
				delete data.sumactenergyphasea_measured;

				data.SumActEnergyPhaseB = {};
				data.SumActEnergyPhaseB.value = calculateType23FromDslTab4Ubytes(data.sumactenergyphaseb_measured);
				data.SumActEnergyPhaseB.unit = 'Wh/kWh';
				delete data.sumactenergyphaseb_measured;

				data.SumActEnergyPhaseC = {};
				data.SumActEnergyPhaseC.value = calculateType23FromDslTab4Ubytes(data.sumactenergyphasec_measured);
				data.SumActEnergyPhaseC.unit = 'Wh/kWh';
				delete data.sumactenergyphasec_measured;

				data.SumActEnergyPhaseABC = {};
				data.SumActEnergyPhaseABC.value = calculateType23FromDslTab4Ubytes(data.sumactenergyphaseabc_measured);
				data.SumActEnergyPhaseABC.unit = 'Wh/kWh';
				delete data.sumactenergyphaseabc_measured;
				data.SumReactEnergyPhaseA = {};
				data.SumReactEnergyPhaseA.value = calculateType23FromDslTab4Ubytes(data.sumreactenergyphasea_measured);
				data.SumReactEnergyPhaseA.unit = 'VARh/kVARh';
				delete data.sumreactenergyphasea_measured;
				data.SumReactEnergyPhaseB = {};
				data.SumReactEnergyPhaseB.value = calculateType23FromDslTab4Ubytes(data.sumreactenergyphaseb_measured);
				data.SumReactEnergyPhaseB.unit = 'VARh/kVARh';
				delete data.sumreactenergyphaseb_measured;

				data.SumReactEnergyPhaseC = {};
				data.SumReactEnergyPhaseC.value = calculateType23FromDslTab4Ubytes(data.sumreactenergyphasec_measured);
				data.SumReactEnergyPhaseC.unit = 'VARh/kVARh';
				delete data.sumreactenergyphasec_measured;

				data.SumReactEnergyPhaseABC = {};
				data.SumReactEnergyPhaseABC.value = calculateType23FromDslTab4Ubytes(data.sumreactenergyphaseabc_measured);
				data.SumReactEnergyPhaseABC.unit = 'VARh/kVARh';
				delete data.sumreactenergyphaseabc_measured;
				break;
			case '0001':
				var SumActPowerPhaseA;
				var SumActPowerPhaseB;
				var SumActPowerPhaseC;
				var SumActPowerPhaseABC;
				var SumReactPowerPhaseA;
				var SumReactPowerPhaseB;
				var SumReactPowerPhaseC;
				var SumReactPowerPhaseABC;

				framedsl += ' ubyte data_length;'
					+ 'ubyte[4] SumActPowerPhaseA_measured; '
					+ 'ubyte[4] SumActPowerPhaseB_measured; '
					+ 'ubyte[4] SumActPowerPhaseC_measured; '
					+ 'ubyte[4] SumActPowerPhaseABC_measured; '
					+ 'ubyte[4] SumReactPowerPhaseA_measured; '
					+ 'ubyte[4] SumReactPowerPhaseB_measured; '
					+ 'ubyte[4] SumReactPowerPhaseC_measured; '
					+ 'ubyte[4] SumReactPowerPhaseABC_measured; ';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				delete data.data_length;
				data.SumActPowerPhaseA = {};
				data.SumActPowerPhaseA.value = calculateType23FromDslTab4Ubytes(data.sumactpowerphasea_measured);
				data.SumActPowerPhaseA.unit = 'Wh/kWh';
				delete data.sumactpowerphasea_measured;

				data.SumActPowerPhaseB = {};
				data.SumActPowerPhaseB.value = calculateType23FromDslTab4Ubytes(data.sumactpowerphaseb_measured);
				data.SumActPowerPhaseB.unit = 'Wh/kWh';
				delete data.sumactpowerphaseb_measured;

				data.SumActPowerPhaseC = {};
				data.SumActPowerPhaseC.value = calculateType23FromDslTab4Ubytes(data.sumactpowerphasec_measured);
				data.SumActPowerPhaseC.unit = 'Wh/kWh';
				delete data.sumactpowerphasec_measured;

				data.SumActPowerPhaseABC = {};
				data.SumActPowerPhaseABC.value = calculateType23FromDslTab4Ubytes(data.sumactpowerphaseabc_measured);
				data.SumActPowerPhaseABC.unit = 'Wh/kWh';
				delete data.sumactpowerphaseabc_measured;
				data.SumReactPowerPhaseA = {};
				data.SumReactPowerPhaseA.value = calculateType23FromDslTab4Ubytes(data.sumreactpowerphasea_measured);
				data.SumReactPowerPhaseA.unit = 'VARh/kVARh';
				delete data.sumreactpowerphasea_measured;
				data.SumReactPowerPhaseB = {};
				data.SumReactPowerPhaseB.value = calculateType23FromDslTab4Ubytes(data.sumreactpowerphaseb_measured);
				data.SumReactPowerPhaseB.unit = 'VARh/kVARh';
				delete data.sumreactpowerphaseb_measured;

				data.SumReactPowerPhaseC = {};
				data.SumReactPowerPhaseC.value = calculateType23FromDslTab4Ubytes(data.sumreactpowerphasec_measured);
				data.SumReactPowerPhaseC.unit = 'VARh/kVARh';
				delete data.sumreactpowerphasec_measured;

				data.SumReactPowerPhaseABC = {};
				data.SumReactPowerPhaseABC.value = calculateType23FromDslTab4Ubytes(data.sumreactpowerphaseabc_measured);
				data.SumReactPowerPhaseABC.unit = 'VARh/kVARh';
				delete data.sumreactpowerphaseabc_measured;
				break;
		}
		return (data);
	}
	function decodeIlluminanceMeasurementCluster(encoded) {
		var data;
		var decoded;
		if (attributType.hexavalue != '21') {
			return "{\"error\":\"wrong attributType\"}";
		}
		framedsl += 'ushort measured_value; ';
		try {
			decoded = BinaryDecoder.decode(framedsl, encoded);
			data = JSON.parse(decoded);
		} catch (e) {
			return "{\"error\":\"decoding failed\"}";
		}
		data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
		switch (attributId.hexavalue) {
			case '0000': /* MeasuredValue */
				data.measure = {};
				data.measure.value = 10 ^ ((data.measured_value - 1) / 10000);
				data.measure.unit = 'lux';
				break;
			case '0001': /* minimum value */
				data.minimum = {};
				data.minimum.value = 10 ^ ((data.measured_value - 1) / 10000);
				data.minimum.unit = 'lux';
				break;
			case '0002': /* maximum value */
				data.maximum = {};
				data.maximum.value = 10 ^ ((data.measured_value - 1) / 10000);
				data.maximum.unit = 'lux';
				break;
		}
		delete data.measured_value;
		return (data);
	}

	function decodeTemperatureMeasurementCluster(encoded) {
		var data;
		var decoded;
		if (attributType.hexavalue != '29') {
			return "{\"error\":\"wrong attributType\"}";
		}
		framedsl += 'short measured_value; ';
		if (cmdid.hexavalue == '8A') framedsl += 'bit batch; bit nohp; bit secu; bit secifa; bit:2 cr; bit todel;bit nmc; ';

		try {
			decoded = BinaryDecoder.decode(framedsl, encoded);
			data = JSON.parse(decoded);
		} catch (e) {
			return "{\"error\":\"decoding failed\"}";
		}
		data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
		switch (attributId.hexavalue) {
			case '0000': /* MeasuredValue */
				data.measure = {};
				data.measure.value = data.measured_value / 100;
				data.measure.unit = '°C';
				break;
			case '0001': /* minimum value */
				data.minimum = {};
				data.minimum.value = data.measured_value / 100;
				data.minimum.unit = '°C';
				break;
			case '0002': /* maximum value */
				data.maximum = {};
				data.maximum.value = data.measured_value / 100;
				data.maximum.unit = '°C';
				break;
		}
		delete data.measured_value;
		if (cmdid.hexavalue == '8A') {
			data = decodeCommand8A(encoded, data, attributType.hexavalue, false);
		}
		return (data);
	}
	function decodePressureMeasurementCluster(encoded) {
		var data;
		var decoded;
		if (attributType.hexavalue != '29') {
			return "{\"error\":\"wrong attributType\"}";
		}
		framedsl += 'short measured_value; ';
		if (cmdid.hexavalue == '8A') framedsl += 'bit batch; bit nohp; bit secu; bit secifa; bit:2 cr; bit todel;bit nmc; ';

		try {
			decoded = BinaryDecoder.decode(framedsl, encoded);
			data = JSON.parse(decoded);
		} catch (e) {
			return "{\"error\":\"decoding failed\"}";
		}
		data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
		switch (attributId.hexavalue) {
			case '0000': /* MeasuredValue */
				data.measure = {};
				data.measure.value = data.measured_value;
				data.measure.unit = 'hPa';
				break;
			case '0001': /* minimum value */
				data.minimum = {};
				data.minimum.value = data.measured_value;
				data.minimum.unit = 'hPa';
				break;
			case '0002': /* maximum value */
				data.maximum = {};
				data.maximum.value = data.measured_value;
				data.maximum.unit = 'hPa';
				break;
		}
		delete data.measured_value;
		if (cmdid.hexavalue == '8A') {
			data = decodeCommand8A(encoded, data, attributType.hexavalue, false);
		}
		return (data);
	}

	function decodeRelativeHumidityMeasurementCluster(encoded) {
		var data;
		var decoded;
		if (attributType.hexavalue != '21') {
			return "{\"error\":\"wrong attributType\"}";
		}
		framedsl += 'ushort measured_value; ';
		if (cmdid.hexavalue == '8A') framedsl += 'bit batch; bit nohp; bit secu; bit secifa; bit:2 cr; bit todel;bit nmc; ';

		try {
			decoded = BinaryDecoder.decode(framedsl, encoded);
			data = JSON.parse(decoded);
		} catch (e) {
			return "{\"error\":\"decoding failed\"}";
		}
		data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
		switch (attributId.hexavalue) {
			case '0000': /* MeasuredValue */
				data.measure = {};
				data.measure.value = data.measured_value / 100;
				data.measure.unit = '%';
				break;
			case '0001': /* minimum value */
				data.minimum = {};
				data.minimum.value = data.measured_value / 100;
				data.minimum.unit = '%';
				break;
			case '0002': /* maximum value */
				data.maximum = {};
				data.maximum.value = data.measured_value / 100;
				data.maximum.unit = '%';
				break;
		}
		delete data.measured_value;
		if (cmdid.hexavalue == '8A') {
			data = decodeCommand8A(encoded, data, attributType.hexavalue, false);
		}
		return (data);
	}

	function decodeOccupancyCluster(encoded) {
		var data;
		var decoded;
		switch (attributId.hexavalue) {

			case '0000': /*'Occupancy value'*/
				if (attributType.hexavalue != '18') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'ubyte occ_value ;';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				data.occupancyValue = data.occ_value;
				delete data.occ_value;
				break;
			case '0010': /*Occupied to Unoccupied delay*/
				if (attributType.hexavalue != '21') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'ushort delay ;';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				data.occupiedToUnoccupiedDelay = {};
				data.occupiedToUnoccupiedDelay.unit = 'seconds';
				data.occupiedToUnoccupiedDelay.value = data.delay;
				delete data.delay;
				break;
			case '0001': /*Occupancy Type'*/
				if (attributType.hexavalue != '30') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'ubyte occ_type ;';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				if (data.occ_type == 0) data.occupancyType = 'Passive infrared';
				else if (data.occ_type == 1) data.occupancyType = 'UltraSonic';
				else if (data.occ_type == 2) data.occupancyType = 'Passive Infrared and Ultrasonic';
				delete data.occ_type;
				break;
		}
		return (data);
	}

	function decodeConfigurationCluster(encoded) {
		var data;
		var decoded;
		switch (attributId.hexavalue) {
			case '0005': /* configuration mode */
				if (attributType.hexavalue != '0A') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += ' ubyte modeconf; ushort remaining_duration;';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				switch (data.modeconf) {
					case 0: data.mode = 'Not configuration mode'; break;
					case 1: data.mode = 'Configuration mode RX (Always awake)'; break;
					case 2: data.mode = 'Configuration mode NS (NS each 3 seconds)'; break;
					default: data.mode = 'Reserved'; break;
				}
				delete data.modeconf;
				if (data.remaining_duration == 0) {
					data.remainingDuration = 'Back to periodic sleeping mode';
				} else if (data.remaining_duration == 65535) {
					data.remainingDuration = 'Stay infinitely in Configuration mode';
				} else {
					data.remainingDuration = {};
					data.remainingDuration.value = data.remaining_duration;
					data.remainingDuration.unit = 'seconds';
				}
				delete data.remaining_duration;
				break;
			case '0006': /* node power description */
				var currentPowerMode;
				if (attributType.hexavalue != '41') {
					return "{\"error\":\"wrong attributType\"}";
				}
				if (cmdid.hexavalue == '01') {
					currentPowerMode = new Fixfield(encoded, 18, 19, 'currentPowerMode');
				} else if (cmdid.hexavalue == '0A') {
					currentPowerMode = new Fixfield(encoded, 16, 17, 'currentPowerMode');
				}
				var currentPowerSource = new Fixfield(encoded, encoded.length - 2,
					encoded.length - 1, 'currentPowerSource');
				framedsl += ' ubyte power_length;'
					+ 'ubyte current_power_mode; '
					+ 'bit constant_or_external_power; bit rechargeable_battery; bit diposable_battery; bit solar_harvesting; bit tic_harvesting;  bit:3 skip_bits;  '
					+ 'ushort[constant_or_external_power] constant_or_external_power_value;   '
					+ 'ushort[rechargeable_battery] rechargeable_battery_value;  '
					+ 'ushort[diposable_battery] diposable_battery_value;  '
					+ 'ushort[solar_harvesting] solar_harvesting_value;  '
					+ 'ushort[tic_harvesting] tic_harvesting_value;  '
					+ 'ubyte currentPowerSource;';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				delete data.power_length;
				delete data.current_power_mode;
				data = currentPowerMode.postProcessMap(data, mapCurrentPowerMode);
				delete data.skip_bits;
				data.powerSources = {};
				data.powerSources.constOrExtPower = {};
				data.powerSources.constOrExtPower.available = (data.constant_or_external_power == 1) ? true : false;
				if (data.constant_or_external_power_value.length != 0) {
					data.powerSources.constOrExtPower.value = Number(data.constant_or_external_power_value) / 1000;
					data.powerSources.constOrExtPower.unit = 'V';
				}
				delete data.constant_or_external_power;
				delete data.constant_or_external_power_value;
				data.powerSources.rechargBattery = {};
				data.powerSources.rechargBattery.available = (data.rechargeable_battery == 1) ? true : false;
				if (data.rechargeable_battery_value.length != 0) {
					data.powerSources.rechargBattery.value = Number(data.rechargeable_battery_value) / 1000;
					data.powerSources.rechargBattery.unit = 'V';
				}
				delete data.rechargeable_battery;
				delete data.rechargeable_battery_value;
				data.powerSources.diposBattery = {};
				data.powerSources.diposBattery.available = (data.diposable_battery == 1) ? true
					: false;
				if (data.diposable_battery_value.length != 0) {
					data.powerSources.diposBattery.value = Number(data.diposable_battery_value) / 1000;
					data.powerSources.diposBattery.unit = 'V';
				}
				delete data.diposable_battery;
				delete data.diposable_battery_value;
				data.powerSources.solHarvesting = {};
				data.powerSources.solHarvesting.available = (data.solar_harvesting == 1) ? true : false;
				if (data.solar_harvesting_value.length != 0) {
					data.powerSources.solHarvesting.value = Number(data.solar_harvesting_value) / 1000;
					data.powerSources.solHarvesting.unit = 'V';
				}
				delete data.solar_harvesting;
				delete data.solar_harvesting_value;
				data.powerSources.ticHarvesting = {};
				data.powerSources.ticHarvesting.available = (data.tic_harvesting == 1) ? true : false;
				if (data.tic_harvesting_value.length != 0) {
					data.powerSources.ticHarvesting.value = Number(data.tic_harvesting_value) / 1000;
					data.powerSources.ticHarvesting.unit = 'V';
				}
				delete data.tic_harvesting;
				delete data.tic_harvesting_value;
				data = currentPowerSource.postProcessMap(data,
					mapCurrentPowerSources);
				delete data.currentpowersource;
				break;
		}
		return (data);
	}

	function decodeTICinformationIceCluster(encoded) {
	}

	function decodeTICinformationCbeCluster(encoded) {
		/* Payloads for CBE */
		var data;
		var decoded;
		if (attributId.hexavalue == '0000') {
			framedsl += 'ubyte informations_length;'
				+ 'ubyte[3] skipbytes;'
				+ 'bit papp ;bit hhphc ;bit motdetat ;bit ppot ;bit:4  skipbits; '
				+ 'bit iinst2 ;bit iinst3 ;bit  adps ;bit  imax ;bit imax1 ;  bit imax2 ; bit imax3 ;bit pmax;  '
				+ 'bit bbrhpjr ;bit pejp ;bit gaz ;bit autre ;bit ptec ; bit demain ;bit iinst ;bit iinst1 ;'
				+ 'bit hchp ;bit ejphn ;bit ejphpm ;bit bbrhcjb ;bit bbrhpjb ;  bit bbrhcjw ;bit bbrhpjw ;bit bbrhcjr ;'
				+ 'bit adir1 ;bit adir2 ;bit adir3 ;  bit adco ;bit optarif ;bit isousc ;bit base ;bit hchc ;'
				+ 'ushort [adir1] adir1_value  ; ushort [adir2] adir2_value  ;ushort [adir3] adir3_value  ; utf8[13*adco] adco_value  ; '
				+ 'utf8[5*optarif] optarif_value  ; ubyte [isousc] isousc_value  ; int [base] base_value  ; int [hchc] hchc_value  ; '
				+ 'int [hchp] hchp_value  ;int [ejphn] ejphn_value  ;int [ejphpm] ejphpm_value  ;int [bbrhcjb] bbrhcjb_value  ;  '
				+ 'int [bbrhpjb] bbrhpjb_value  ; int [bbrhcjw] bbrhcjw_value  ; int [bbrhpjw] bbrhpjw_value  ; int [bbrhcjr] bbrhcjr_value  ;  '
				+ 'int [bbrhpjr] bbrhpjr_value  ; ubyte [pejp] pejp_value  ;int [gaz] gaz_value  ; int [autre] autre_value  ; '
				+ 'utf8[5*ptec] ptec_value  ; utf8[5*demain] demain_value  ; ushort[iinst] iinst_value  ;ushort[iinst1] iinst1_value  ; '
				+ 'ushort[iinst2] iinst2_value  ; ushort[iinst3] iinst3_value  ;ushort[adps] adps_value  ; ushort[imax] imax_value  ; '
				+ 'ushort[imax1] imax1_value  ; ushort[imax2] imax2_value  ; ushort[imax3] imax3_value  ;int[pmax] pmax_value  ; '
				+ 'int[papp] papp_value  ; utf8[hhphc] hhphc_value  ;utf8[7*motdetat] motdetat_value  ;utf8[3*ppot] ppot_value  ; ';
			try {
				decoded = BinaryDecoder.decode(framedsl, encoded);
				data = JSON.parse(decoded);
			} catch (e) {
				return "{\"error\":\"decoding failed\"}";
			}

			data = postProcessEndpointCommandIdAndCluster(data);
			delete data.attribut_id;
			data.attributId = "CBE original";
			delete data.attribut_type;
			if (cmdid.hexavalue == '01') {
				delete data.status;
				data = status.postProcessMap(data, mapStatusCommands);
			}
			delete data.informations_length;
			delete data.skipbytes;
			delete data.skipbits;
			if ((data.adir1 == 1) || (data.adir2 == 1) || (data.adir3 == 1)) {
				data.avertDepassI = {};
				data.avertDepassI.unit = "A";
			}
			if (data.adir1 == 1) {
				data.avertDepassI.phase1 = data.adir1_value[0];
			}
			delete data.adir1;
			delete data.adir1_value;
			if (data.adir2 == 1) {
				data.avertDepassI.phase2 = data.adir2_value[0];
			}
			delete data.adir2;
			delete data.adir2_value;
			if (data.adir3 == 1) {
				data.avertDepassI.phase3 = data.adir3_value[0];
			}
			delete data.adir3;
			delete data.adir3_value;
			data = postProcessCbeStringData(data, 'adco', 'adresseCompteur');
			data = postProcessCbeStringData(data, 'optarif', 'optionTarifStr');
			switch (data.optionTarifStr) {
				case 'BASE': data.optionTarif = 'Base'; break;
				case 'HC..': data.optionTarif = 'Heure creuse heure pleine'; break;
				case 'BBR(': data.optionTarif = 'Tempo'; break;
				case 'EJP.': data.optionTarif = 'Effacement des jours de pointe'; break;
				default: data.optionTarif = data.optionTarifStr; break;
			}
			delete data.optionTarifStr;
			if (data.isousc == 1) {
				data.intensiteSouscrite = {};
				data.intensiteSouscrite.value = parseInt(data.isousc_value[0]);
				data.intensiteSouscrite.unit = 'A';
			}
			delete data.isousc;
			delete data.isousc_value;
			data = postProcessCbeNumData(data, 'base', 'indexOptionBase', 'Wh');
			data = postProcessCbeNumData(data, 'hchc', 'indexHeuresCreuses', 'Wh');
			data = postProcessCbeNumData(data, 'hchp', 'indexHeuresPleines', 'Wh');
			data = postProcessCbeNumData(data, 'ejphn', 'ejpHeuresNormales', 'Wh');
			data = postProcessCbeNumData(data, 'ejphpm', 'ejpHeuresPointeMobile', 'Wh');
			data = postProcessCbeNumData(data, 'bbrhcjb', 'tempoHeuresCreusesJbleus', 'Wh');
			data = postProcessCbeNumData(data, 'bbrhpjb', 'tempoHeuresPleinesJbleus', 'Wh');
			data = postProcessCbeNumData(data, 'bbrhcjw', 'tempoHeuresCreusesJblancs', 'Wh');
			data = postProcessCbeNumData(data, 'bbrhpjw', 'tempoHeuresPleinesJblancs', 'Wh');
			data = postProcessCbeNumData(data, 'bbrhcjr', 'tempoHeuresCreusesJrouges', 'Wh');
			data = postProcessCbeNumData(data, 'bbrhpjr', 'tempoHeuresPleinesJrouges', 'Wh');
			data = postProcessCbeNumData(data, 'pejp', 'preavisEJP', 'Min');
			data = postProcessCbeNumData(data, 'gaz', 'indexGaz', 'dal');
			data = postProcessCbeNumData(data, 'autre', 'indexTroisiemeCompteur', 'dal');
			data = postProcessCbeStringData(data, 'ptec', 'periodeTarifaireEnCoursStr');
			switch (data.periodeTarifaireEnCoursStr) {
				case 'TH..': data.periodeTarifaireEnCours = 'Base'; break;
				case 'HC..': data.periodeTarifaireEnCours = 'Heures creuses'; break;
				case 'HP..': data.periodeTarifaireEnCours = 'Heures pleines'; break;
				case 'HPJB': data.periodeTarifaireEnCours = 'Heures pleines jours bleus'; break;
				case 'HCJB': data.periodeTarifaireEnCours = 'Heures creuses jours bleus'; break;
				case 'HPJR': data.periodeTarifaireEnCours = 'Heures pleines jours rouges'; break;
				case 'HCJR': data.periodeTarifaireEnCours = 'Heures creuses jours rouges'; break;
				case 'HPJW': data.periodeTarifaireEnCours = 'Heures pleines jours blancs'; break;
				case 'HCJW': data.periodeTarifaireEnCours = 'Heures creuses jours blancs'; break;
				case 'HN..': data.periodeTarifaireEnCours = 'Heures normales'; break;
				case 'PM..': data.periodeTarifaireEnCours = 'Heures de pointe mobile'; break;
				default: data.periodeTarifaireEnCours = data.periodeTarifaireEnCoursStr; break;
			}
			delete data.periodeTarifaireEnCoursStr;

			data = postProcessCbeStringData(data, 'demain', 'couleurLendemain');
			data = postProcessCbeNumData(data, 'iinst', 'intensiteInstant', 'A');
			data = postProcessCbeNumData(data, 'iinst1', 'intensiteInstantPhase1', 'A');
			data = postProcessCbeNumData(data, 'iinst2', 'intensiteInstantPhase2', 'A');
			data = postProcessCbeNumData(data, 'iinst3', 'intensiteInstantPhase3', 'A');
			data = postProcessCbeNumData(data, 'adps', 'avertDepassPuissSous', 'A');
			data = postProcessCbeNumData(data, 'imax', 'intensiteMaxAppelee', 'A');
			data = postProcessCbeNumData(data, 'imax1', 'intensiteMaxAppeleeP1', 'A');
			data = postProcessCbeNumData(data, 'imax2', 'intensiteMaxAppeleeP2', 'A');
			data = postProcessCbeNumData(data, 'imax3', 'intensiteMaxAppeleeP3', 'A');
			data = postProcessCbeNumData(data, 'pmax', 'puissanceMax', 'W');
			data = postProcessCbeNumData(data, 'papp', 'puissanceApp', 'VA');
			if (data.hhphc == 1) {
				data.horaireHeurePleineCreuse = String(data.hhphc_value);
			}
			delete data.hhphc;
			delete data.hhphc_value;
			data = postProcessCbeStringData(data, 'motdetat', 'motEtatCompteur');
			data = postProcessCbeStringData(data, 'ppot', 'presencePotentiels');
		} else if (attributId.hexavalue == '0010') { /* meter type*/
			var ticMeterType;
			if (attributType.hexavalue != '20') {
				return "{\"error\":\"wrong attributType\"}";
			}
			if (cmdid.hexavalue == '01') {
				ticMeterType = new Fixfield(encoded, 16, 17, 'TIC_MeterType');
			} else if (cmdid.hexavalue == '0A') {
				ticMeterType = new Fixfield(encoded, 14, 15, 'TIC_MeterType');
			}
			framedsl += 'ubyte tic_type ;';
			try {
				decoded = BinaryDecoder.decode(framedsl, encoded);
				data = JSON.parse(decoded);
			} catch (e) {
				return "{\"error\":\"decoding failed\"}";
			}
			data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
			if (Number(data.tic_type) < 8) {
				data = ticMeterType.postProcessMap(data, mapTIC_MeterType);
			} else {
				data.TIC_MeterType = 'Unknown';
			}
			delete data.tic_type;
		}
		return (data);
	}

	function decodeTICinformationCjeCluster(encoded) {
	}

	function decodeTICinformationStdCluster(encoded) {
	}

	function decodeSensoVolumeMeterCluster(encoded) {
		var data;
		var decoded;
		switch (attributId.hexavalue) {
			case '0000': /* 'volume' */
				if (attributType.hexavalue != '2B') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'int volume_index ;';
				if (cmdid.hexavalue == '8A') framedsl += 'bit batch; bit nohp; bit secu; bit secifa; bit:2 cr; bit todel;bit nmc; ';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				data.volumeIndex = Number(data.volume_index);
				delete data.volume_index;
				if (cmdid.hexavalue == '8A') {
					data = decodeCommand8A(encoded, data, attributType.hexavalue, false);
				}
				break;
			case '0001': /* volumeDisplayMode */
				if (attributType.hexavalue != '20') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'ubyte volume_unit ;';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				if (Number(data.volume_unit) == 0) {
					data.volumeUnit = 'deciLiter';
				} else if (Number(data.volume_unit) == 1) {
					data.volumeUnit = 'Liter';
				}
				delete data.volume_unit;
				break;
			case '0002': /* minFlow */
			case '0003': /* maxFlow */
				if (attributType.hexavalue != '28') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'ubyte flow ;';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				if (attributId.hexavalue == '0002') {
					data.minimumFlow = data.flow;
				} else if (attributId.hexavalue == '0003') {
					data.maximumFlow = data.flow;
				}
				delete data.flow;
				break;
			case '0004': /* flowDisplayMode */
				if (attributType.hexavalue != '20') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'ubyte flow_unit ;';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				if (Number(data.flow_unit) == 0) {
					data.flowDisplayMode = 'Max: Liter/min, Min: dLiter/Hour';
				}
				delete data.flow_unit;
				break;
		}
		return (data);
	}

	function decodeSensoCluster(encoded) {
		var data;
		var decoded;
		switch (attributId.hexavalue) {
			case '0000': /* status */
				if (attributType.hexavalue != '18') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'bit leak; bit back_water_1; bit back_water_2; bit back_water_3;'
					+ 'bit fraud; bit battery; bit installation; bit freeze;';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				data.sensorStatus = {};
				data.sensorStatus.leak = (data.leak == 1) ? 'Ok' : 'Nok';
				data.sensorStatus.backWaterLevel1 = (data.back_water_1 == 1) ? 'Ok' : 'Nok';
				data.sensorStatus.backWaterLevel2 = (data.back_water_2 == 1) ? 'Ok' : 'Nok';
				data.sensorStatus.backWaterLevel3 = (data.back_water_3 == 1) ? 'Ok' : 'Nok';
				data.sensorStatus.fraud = (data.fraud == 1) ? 'Ok' : 'Nok';
				data.sensorStatus.battery = (data.battery == 1) ? 'Ok' : 'Nok';
				data.sensorStatus.installation = (data.installation == 1) ? 'Ok' : 'Nok';
				data.sensorStatus.freeze = (data.freeze == 1) ? 'Ok' : 'Nok';
				delete data.leak;
				delete data.back_water_1;
				delete data.back_water_2;
				delete data.back_water_3;
				delete data.fraud;
				delete data.battery;
				delete data.installation;
				delete data.freeze;
				break;
			case '0001': /* countDownThresholds */
				if (attributType.hexavalue != '41') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'ubyte length; ushort countDown1threshold ; ushort countDown2threshold; ushort countDown3threshold;';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				data.CountDown1Threshold = data.countDown1threshold;
				data.CountDown2Threshold = data.countDown2threshold;
				data.CountDown3Threshold = data.countDown3threshold;
				delete data.length;
				delete data.countDown1threshold;
				delete data.countDown2threshold;
				delete data.countDown3threshold;
				break;
			case '0002': /* InstallationRotation */
				if (attributType.hexavalue != '20') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'ubyte install_rotation; ';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				data.installationRotation = Number(data.install_rotation);
				delete data.install_rotation;
				break;
			case '0003': /* volumeRotation */
				if (attributType.hexavalue != '21') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'ushort volume_rotation; ';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				data.volumeRotation = data.volume_rotation;
				delete data.volume_rotation;
				break;
			case '0004': /* TemperatureMeterFreeze */
			case '0005': /* TemperatureMinTxOff */
				if (attributType.hexavalue != '28') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'byte temperature; ';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				if (attributId.hexavalue == '0004') {
					data.temperatureMeterFreeze = {};
					data.temperatureMeterFreeze.value = Number(data.temperature);
					data.temperatureMeterFreeze.unit = '°C';
				} else if (attributId.hexavalue == '0005') {
					data.temperatureMinTxOff = {};
					data.temperatureMinTxOff.value = Number(data.temperature);
					data.temperatureMinTxOff.unit = '°C';
				}
				delete data.temperature;
				break;
			case '0006': /* ParametersLeakFlow */
				if (attributType.hexavalue != '41') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'ubyte length; ubyte volume_threshold; ushort period_average; ushort period_observation;';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				data.volumeThreshold = {};
				data.volumeThreshold.value = Number(data.volume_threshold);
				data.volumeThreshold.unit = 'Leter';
				data.periodCalculateAverageLeakFlow = {};
				data.periodCalculateAverageLeakFlow.value = data.period_average;
				data.periodCalculateAverageLeakFlow.unit = 'minutes';
				data.periodObservationLeakFlow = {};
				data.periodObservationLeakFlow.value = data.period_observation;
				data.periodObservationLeakFlow.unit = 'hours';
				delete data.length;
				delete data.volume_threshold;
				delete data.period_average;
				delete data.period_observation;
				break;
		}
		return (data);
	}

	function decodeLorawanCluster(encoded) {
		var data;
		var decoded;
		switch (attributId.hexavalue) {
			case '0000': /* 'MessageType' */
				if (attributType.hexavalue != '08') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'ubyte message_type ;';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				if (Number(data.message_type) == 1) {
					data.messageTypeConfiguration = 'All frames Confirmed (default)';
				} else if (Number(data.message_type) == 0) {
					data.messageTypeConfiguration = 'All frames Unconfirmed';
				}
				delete data.message_type;
				break;
			case '0001': /* 'RetryConfirmed' */
				if (attributType.hexavalue != '20') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'ubyte retry_confirmed ;';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				data.retryConfirmedNumber = Number(data.retry_confirmed);
				delete data.retry_confirmed;
				break;
			case '0002': /* 'ReAssociation' */
				if (attributType.hexavalue != '41') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'ubyte length ; ushort period_without_frame; ushort consecutive_failure;';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				data.periodNoFrameAfterNewAssoc = {};
				data.periodNoFrameAfterNewAssoc.value = data.period_without_frame;
				data.periodNoFrameAfterNewAssoc.unit = 'Minutes';
				data.consecutiveFailureAfterNewAssoc = data.consecutive_failure;
				delete data.length;
				delete data.period_without_frame;
				delete data.consecutive_failure;
				break;
			case '0003': /* 'DataRate' */
				if (attributType.hexavalue != '41') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'ubyte length ; ubyte hopping; ubyte start_data_rate;';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				if (Number(data.start_data_rate) == 255) {
					data.startDataRate = 'from the maximum (default)';
				}
				else if (Number(data.start_data_rate) == 254) {
					data.startDataRate = 'from the minimum';
				}
				else {
					data.startDataRate = 'from ' + String(data.start_data_rate) + ' Datarate (0 to 5 for EU)';
				}
				delete data.length;
				delete data.hopping;
				delete data.start_data_rate;
				break;
			case '0004': /* 'ABP_DevAddr' */
			case '0005': /* 'OTA_AppEUI' */
				if (attributType.hexavalue != '41') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'ubyte length ; ubyte[length] adress;';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				if (attributId.hexavalue == '0004') {
					data.ABP_DevAddress = "";
					if (cmdid.hexavalue == '01') {
						data.ABP_DevAddress = encoded.substring(18, 18 + (data.length * 2));
					} else if (cmdid.hexavalue == '0A') {
						data.ABP_DevAddress = encoded.substring(16, 16 + (data.length * 2));
					}
					if (data.ABP_DevAddress == "FFFFFFFF") {
						data.ABP_DevAddress = "non défini";
					}
				} else if (attributId.hexavalue == '0005') {
					data.OTA_AppEUI = "";
					if (cmdid.hexavalue == '01') {
						data.OTA_AppEUI = encoded.substring(18, 18 + (data.length * 2));
					} else if (cmdid.hexavalue == '0A') {
						data.OTA_AppEUI = encoded.substring(16, 16 + (data.length * 2));
					}
				}
				delete data.length;
				delete data.adress;
				break;
		}
		return (data);
	}


	function decodeDifferentialPressureMeasurementCluster(encoded) {
		var data;
		var decoded;
		switch (attributId.hexavalue) {
			case '0000': /* 'Measured value' */
			case '0001': /* 'Min measured value' */
			case '0002': /* 'Max measured value' */
			case '0100': /* 'Mean measured value since last report' */
			case '0101': /* 'Minimal measured value since last report' */
			case '0102': /* 'Maximal measured value since last report' */
				if (attributType.hexavalue != '29') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'short measured_value; ';
				if (cmdid.hexavalue == '8A') framedsl += 'bit batch; bit nohp; bit secu; bit secifa; bit:2 cr; bit todel;bit nmc; ';

				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				if (attributId.hexavalue == '0000') {
					data.measure = {};
					data.measure.value = data.measured_value;
					data.measure.unit = 'Pa';
				}
				else if (attributId.hexavalue == '0001') {
					data.minimum = {};
					data.minimum.value = data.measured_value;
					data.minimum.unit = 'Pa';
				}
				if (attributId.hexavalue == '0002') {
					data.maximum = {};
					data.maximum.value = data.measured_value;
					data.maximum.unit = 'Pa';
				}
				if (attributId.hexavalue == '0100') {
					data.meanMeasuredValueSinceLastReport = {};
					data.meanMeasuredValueSinceLastReport.value = data.measured_value;
					data.meanMeasuredValueSinceLastReport.unit = 'Pa';
				}
				if (attributId.hexavalue == '0101') {
					data.minimalMeasuredValueSinceLastReport = {};
					data.minimalMeasuredValueSinceLastReport.value = data.measured_value;
					data.minimalMeasuredValueSinceLastReport.unit = 'Pa';
				}
				if (attributId.hexavalue == '0102') {
					data.maximalMeasuredValueSinceLastReport = {};
					data.maximalMeasuredValueSinceLastReport.value = data.measured_value;
					data.maximalMeasuredValueSinceLastReport.unit = 'Pa';
				}

				delete data.measured_value;
				break;

			case '0003': /* 'Measurement period' */
			case '0006': /* 'Sampling period' */
				if (attributType.hexavalue != '23') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'ubyte[4] period ;';
				if (cmdid.hexavalue == '8A') framedsl += 'bit batch; bit nohp; bit secu; bit secifa; bit:2 cr; bit todel;bit nmc; ';

				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				if (attributId.hexavalue == '0003') {
					data.measurementPeriod = {};
					data.measurementPeriod.value = calculateType23FromDslTab4Ubytes(data.period);
					data.measurementPeriod.unit = 'ms';
				}
				else if (attributId.hexavalue == '0006') {
					data.samplingPeriod = {};
					data.samplingPeriod.value = calculateType23FromDslTab4Ubytes(data.period);
					data.samplingPeriod.unit = 'ms';
				}

				delete data.period;
				break;
			case '0004': /* 'Samples per measurement' */
			case '0005': /* 'Samples per confirmation measurement' */
				if (attributType.hexavalue != '21') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'ushort samples; ';
				if (cmdid.hexavalue == '8A') framedsl += 'bit batch; bit nohp; bit secu; bit secifa; bit:2 cr; bit todel;bit nmc; ';

				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				if (attributId.hexavalue == '0004') {
					data.samplesPerMeasurement = data.samples;
				}
				if (attributId.hexavalue == '0005') {
					data.samplesPerConfirmationMeasurement = data.samples;
				}
				delete data.samples;
				break;
		}

		if (cmdid.hexavalue == '8A') {
			data = decodeCommand8A(encoded, data, attributType.hexavalue, false);
		}
		return (data);
	}


	function decodeConcentrationMeasurementCluster(encoded) {
		var data;
		var decoded;
		switch (attributId.hexavalue) {
			case '0000': /* 'Measured value' */
			case '0100': /* 'Mean measured value' */
			case '0101': /* 'Minimum measured value' */
			case '0102': /* 'Maximum measured value' */
				if (attributType.hexavalue != '21') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'ushort measured_value; ';
				if (cmdid.hexavalue == '8A') framedsl += 'bit batch; bit nohp; bit secu; bit secifa; bit:2 cr; bit todel;bit nmc; ';

				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				if (attributId.hexavalue == '0000') {
					data.measure = {};
					data.measure.value = data.measured_value;
					data.measure.unit = 'ppm or IAQ';
				}
				else if (attributId.hexavalue == '0100') {
					data.mean = {};
					data.mean.value = data.measured_value;
					data.mean.unit = 'ppm or IAQ';
				}
				else if (attributId.hexavalue == '0101') {
					data.minimum = {};
					data.minimum.value = data.measured_value;
					data.minimum.unit = 'ppm or IAQ';
				}
				else if (attributId.hexavalue == '0102') {
					data.maximum = {};
					data.maximum.value = data.measured_value;
					data.maximum.unit = 'ppm or IAQ';
				}
				delete data.measured_value;
				if (cmdid.hexavalue == '8A') {
					data = decodeCommand8A(encoded, data, attributType.hexavalue, false);
				}
				break;

			case '0001': /* 'Concentration level classification' */
			case '8004': /* 'concentration unit' */
			case '8009': /* 'Calibration period' */
				if (attributType.hexavalue != '20') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'ubyte val ;';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				if (attributId.hexavalue == '0001') {
					switch (data.val) {
						case 0: data.currentClassificationLevel = 'EXCELLENT'; break;
						case 1: data.currentClassificationLevel = 'GOOD'; break;
						case 2: data.currentClassificationLevel = 'AVERAGE'; break;
						case 3: data.currentClassificationLevel = 'BAD'; break;
						case 4: data.currentClassificationLevel = 'VERY BAD'; break;
						case 5: data.currentClassificationLevel = 'UNHEALTHY'; break;
					}
				}
				else if (attributId.hexavalue == '8004') {
					var cctUnit;
					if (cmdid.hexavalue == '01') {
						cctUnit = encoded.substring(16, 18);
					}
					else if (cmdid.hexavalue == '0A' || cmdid.hexavalue == '8A') {
						cctUnit = encoded.substring(14, 16);
					}
					switch (cctUnit) {
						case 'FF': data.concentrationUnit = 'Unit is undefined'; break;
						case '01': data.concentrationUnit = 'Parts per 10'; break;
						case '02': data.concentrationUnit = 'Parts per 100'; break;
						case '03': data.concentrationUnit = 'Parts per 1000'; break;
						case '06': data.concentrationUnit = 'Parts per Million'; break;
						case '09': data.concentrationUnit = 'Parts per Billion'; break;
						case '0C': data.concentrationUnit = 'Parts par Trillion'; break;
						case '0F': data.concentrationUnit = 'Parts par Quintillion'; break;
						case '80': data.concentrationUnit = '0 to 500 Indicator (IAQ)'; break;
					}
				}
				else if (attributId.hexavalue == '8009') {
					data.calibrationPeriod = {};
					data.calibrationPeriod.unit = 'days';
					data.calibrationPeriod.value = data.val;
				}
				delete data.val;
				break;
			case '8010': /* 'Classification levels' */
				if (attributType.hexavalue != '41') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'ubyte lg18 ; ushort excellent; ushort good; ushort average; ushort bad; ushort verybad; ';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				delete data.lg18;
				data.calibrationCoefficients = {};
				data.calibrationCoefficients.excellentUpTo = data.excellent;
				delete data.excellent;
				data.calibrationCoefficients.goodUpTo = data.good;
				delete data.good;
				data.calibrationCoefficients.averageUpTo = data.average;
				delete data.average;
				data.calibrationCoefficients.badUpTo = data.bad;
				delete data.bad;
				data.calibrationCoefficients.verybadUpTo = data.verybad;
				delete data.verybad;
				break;
			case '8020': /* 'HMI display period' */
			case '8008': /* 'Minimal/Normal concentration level' */
				if (attributType.hexavalue != '21') {
					return "{\"error\":\"wrong attributType\"}";
				}
				framedsl += 'ushort valp ;';
				try {
					decoded = BinaryDecoder.decode(framedsl, encoded);
					data = JSON.parse(decoded);
				} catch (e) {
					return "{\"error\":\"decoding failed\"}";
				}
				data = postProcessFirstFixfieldsInFrame(data, cmdid.hexavalue, mapAttributId);
				if (attributId.hexavalue == '8020') {
					data.hmiDisplayPeriod = {};
					data.hmiDisplayPeriod.unit = 'Seconds';
					data.hmiDisplayPeriod.value = data.valp;
				}
				else if (attributId.hexavalue == '8008') {
					data.minNormalLevel = {};
					data.minNormalLevel.unit = 'ppm or IAQ';
					data.minNormalLevel.value = data.valp;
				}
				delete data.valp;
				break;

		}

		return (data);
	}


	function postProcessEndpointCommandIdAndCluster(data) {
		delete data.endpoint;
		data = endpoint.postProcessMap(data, mapEndpoint);
		delete data.command_id;
		data = cmdid.postProcessMap(data, mapCommandId);
		delete data.cluster;
		data = cluster.postProcessMap(data, mapCluster);
		return data;
	}
	function postProcessFirstFixfieldsInFrame(data, cmdid, mapAttributId) {
		data = postProcessEndpointCommandIdAndCluster(data);
		delete data.attribut_id;
		data = attributId.postProcessMap(data, mapAttributId);
		if (cmdid == '01') {
			delete data.status;
			data = status.postProcessMap(data, mapStatusCommands);
		}
		delete data.attribut_type;
		return data;
	}

	function postProcessCbeNumData(data, booleanProperty, targetName, unit) {
		if (data[booleanProperty] == 1) {
			data[targetName] = {};
			data[targetName]['value'] = data[booleanProperty + '_value'][0];
			data[targetName]['unit'] = unit;
		}
		delete data[booleanProperty];
		delete data[booleanProperty + '_value'];
		return data;
	}
	function postProcessCbeStringData(data, booleanProperty, targetName) {
		if (data[booleanProperty] == 1) {
			var property_value = String(data[booleanProperty + '_value']);
			var plength = property_value.length;
			if (property_value[plength - 1] == '\u0000') {
				data[targetName] = property_value.substring(0, plength - 1);
			} else {
				data[targetName] = property_value;
			}
		}
		delete data[booleanProperty];
		delete data[booleanProperty + '_value'];
		return data;
	}
}

/* converting functions */
function checkHex(n) {
	return /^[0-9A-Fa-f]{1,64}$/.test(n);
}

function Hex2Bin(n) {
	if (!checkHex(n)) return 0;
	return parseInt(n, 16).toString(2);
}

function parseSignedIntFrom3bytes(hexa) {
	var valueInt = parseInt(hexa, 16);
	if (valueInt > 8388607) {
		valueInt = (16777215 - valueInt + 1) * (-1);
	}
	return valueInt;
}

function ieee32ToFloat(intval, nbDecimals) {
	var fval = 0.0;
	var x;
	var m;
	var s;
	s = (intval & 0x80000000) ? -1 : 1;
	x = ((intval >> 23) & 0xFF);
	m = (intval & 0x7FFFFF);
	switch (x) {
		case 0:
			break;
		case 0xFF:
			if (m)
				fval = NaN;
			else if (s > 0)
				fval = Number.POSITIVE_INFINITY;
			else
				fval = Number.NEGATIVE_INFINITY;
			break;
		default:
			x -= 127;
			m += 0x800000;
			fval = (s * (m / 8388608.0) * Math.pow(2, x)).toFixed(nbDecimals);
			break;
	}
	return fval;
}

/* Functions for batch payload decoding */
Math.trunc = Math.trunc || function (x) {
	if (isNaN(x)) {
		return NaN;
	}
	if (x > 0) {
		return Math.floor(x);
	}
	return Math.ceil(x);
};

/* brUncompress main function */
function brUncompress(tagsz, argList, hexString, batch_absolute_timestamp) {
	var ST_U8 = 4;
	var out = initResult();
	var buffer = createBuffer(parseHexString(hexString));
	var flag = generateFlag(buffer.getNextSample(ST_U8));
	out.batch_counter = buffer.getNextSample(ST_U8, 3);
	buffer.getNextSample(ST_U8, 1);
	var temp = prePopulateOutput(out, buffer, argList, flag, tagsz);
	var last_timestamp = temp.last_timestamp;
	var index_of_the_first_sample = temp.index_of_the_first_sample;
	if (flag.hasSample) {
		last_timestamp = uncompressSamplesData(out, buffer, index_of_the_first_sample, argList, last_timestamp, flag, tagsz);
	}
	out.batch_relative_timestamp = extractTimestampFromBuffer(buffer, last_timestamp);
	return adaptToExpectedFormat(out, argList, batch_absolute_timestamp);
}

/* Init br_uncompress result data structure */
function initResult() {
	var NUMBER_OF_SERIES = 16;
	var series = [], i = 0;
	while (!(java.lang.Thread.interrupted()) && (i < NUMBER_OF_SERIES)) {
		series.push({
			codingType: 0,
			codingTable: 0,
			resolution: null,
			uncompressSamples: []
		});
		i += 1;
	}
	return {
		batch_counter: 0,
		batch_relative_timestamp: 0,
		series: series
	};
}


/* Function to create a buffer from a byteArray. Allow to read sample from the byteArray to extract data. */
function createBuffer(byteArray) {
	var ST_UNDEF = 0;
	var ST_BL = 1;
	var ST_U4 = 2;
	var ST_I4 = 3;
	var ST_U8 = 4;
	var ST_I8 = 5;
	var ST_U16 = 6;
	var ST_I16 = 7;
	var ST_U24 = 8;
	var ST_I24 = 9;
	var ST_U32 = 10;
	var ST_I32 = 11;
	var ST_FL = 12;
	var ST = {};
	ST[ST_UNDEF] = 0;
	ST[ST_BL] = 1;
	ST[ST_U4] = 4;
	ST[ST_I4] = 4;
	ST[ST_U8] = 8;
	ST[ST_I8] = 8;
	ST[ST_U16] = 16;
	ST[ST_I16] = 16;
	ST[ST_U24] = 24;
	ST[ST_I24] = 24;
	ST[ST_U32] = 32;
	ST[ST_I32] = 32;
	ST[ST_FL] = 32;

	/** Retrieve the pattern for HUFF table lookup */
	function bitsBuf2HuffPattern(byteArray, index, nb_bits) {
		var sourceBitStart = index;
		var sz = nb_bits - 1;
		if (byteArray.length * 8 < sourceBitStart + nb_bits) {
			throw new Error("internal error");
		}
		var bittoread = 0;
		var pattern = 0;
		while (!(java.lang.Thread.interrupted()) && (nb_bits > 0)) {
			if (byteArray[sourceBitStart >> 3] & 1 << (sourceBitStart & 0x07)) {
				pattern |= 1 << sz - bittoread;
			}
			nb_bits--;
			bittoread++;
			sourceBitStart++;
		}
		return pattern;
	}
	return {
		index: 0,
		byteArray: byteArray,
		getNextSample: function getNextSample(sampleType, nbBitsInput) {
			var nbBits = nbBitsInput || ST[sampleType];
			var sourceBitStart = this.index;
			this.index += nbBits;
			if (sampleType === ST_FL && nbBits !== 32) {
				throw new Error("wrong type in tag");
			}
			var u32 = 0;
			var nbytes = Math.trunc((nbBits - 1) / 8) + 1;
			var nbitsfrombyte = nbBits % 8;
			if (nbitsfrombyte === 0 && nbytes > 0) {
				nbitsfrombyte = 8;
			}
			while (!(java.lang.Thread.interrupted()) && (nbytes > 0)) {
				var bittoread = 0;
				while (!(java.lang.Thread.interrupted()) && (nbitsfrombyte > 0)) {
					var idx = sourceBitStart >> 3;
					if (this.byteArray[idx] & 1 << (sourceBitStart & 0x07)) {
						u32 |= 1 << (nbytes - 1) * 8 + bittoread;
					}
					nbitsfrombyte--;
					bittoread++;
					sourceBitStart += 1;
				}
				nbytes--;
				nbitsfrombyte = 8;
			}
			/* Propagate the sign bit if 1 */
			if ((sampleType == ST_I4 || sampleType == ST_I8 || sampleType == ST_I16 || sampleType == ST_I24) && u32 & 1 << nbBits - 1) {

				for (var i = nbBits; i < 32; i++) {
					u32 |= 1 << i;
					nbBits++;
				}
			}
			return u32;
		},

		/* Extract sz and bi from Huff table */
		getNextBifromHi: function getNextBifromHi(huff_coding) {
			var HUFF = [[{ sz: 2, lbl: 0x000 }, { sz: 2, lbl: 0x001 }, { sz: 2, lbl: 0x003 },
			{ sz: 3, lbl: 0x005 }, { sz: 4, lbl: 0x009 }, { sz: 5, lbl: 0x011 },
			{ sz: 6, lbl: 0x021 }, { sz: 7, lbl: 0x041 }, { sz: 8, lbl: 0x081 },
			{ sz: 10, lbl: 0x200 }, { sz: 11, lbl: 0x402 }, { sz: 11, lbl: 0x403 },
			{ sz: 11, lbl: 0x404 }, { sz: 11, lbl: 0x405 }, { sz: 11, lbl: 0x406 },
			{ sz: 11, lbl: 0x407 }], [{ sz: 7, lbl: 0x06f }, { sz: 5, lbl: 0x01a },
			{ sz: 4, lbl: 0x00c }, { sz: 3, lbl: 0x003 }, { sz: 3, lbl: 0x007 },
			{ sz: 2, lbl: 0x002 }, { sz: 2, lbl: 0x000 }, { sz: 3, lbl: 0x002 },
			{ sz: 6, lbl: 0x036 }, { sz: 9, lbl: 0x1bb }, { sz: 9, lbl: 0x1b9 },
			{ sz: 10, lbl: 0x375 }, { sz: 10, lbl: 0x374 }, { sz: 10, lbl: 0x370 },
			{ sz: 11, lbl: 0x6e3 }, { sz: 11, lbl: 0x6e2 }],
			[{ sz: 4, lbl: 0x009 }, { sz: 3, lbl: 0x005 }, { sz: 2, lbl: 0x000 },
			{ sz: 2, lbl: 0x001 }, { sz: 2, lbl: 0x003 }, { sz: 5, lbl: 0x011 },
			{ sz: 6, lbl: 0x021 }, { sz: 7, lbl: 0x041 }, { sz: 8, lbl: 0x081 },
			{ sz: 10, lbl: 0x200 }, { sz: 11, lbl: 0x402 }, { sz: 11, lbl: 0x403 },
			{ sz: 11, lbl: 0x404 }, { sz: 11, lbl: 0x405 }, { sz: 11, lbl: 0x406 },
			{ sz: 11, lbl: 0x407 }]];
			for (var i = 2; i < 12; i++) {
				var lhuff = bitsBuf2HuffPattern(this.byteArray, this.index, i);
				for (var j = 0; j < HUFF[huff_coding].length; j++) {
					if (HUFF[huff_coding][j].sz == i && lhuff == HUFF[huff_coding][j].lbl) {
						this.index += i;
						return j;
					}
				}
			}
			throw new Error("internal error");
		}
	};
}

/* Convert the hex string given as parameter to a ByteArray */
function parseHexString(str) {
	str = str.split("").filter(function (x) {
		return !isNaN(parseInt(x, 16));
	}).join("");
	var result = [];
	while (!(java.lang.Thread.interrupted()) && (str.length >= 2)) {
		result.push(parseInt(str.substring(0, 2), 16));
		str = str.substring(2, str.length);
	}
	return result;
}

/* Generate a flag object from an integer value. */
function generateFlag(flagAsInt) {
	var binbase = flagAsInt.toString(2);
	/* leftpad */
	while (!(java.lang.Thread.interrupted()) && (binbase.length < 8)) {
		binbase = "0" + binbase;
	}
	return {
		isCommonTimestamp: parseInt(binbase[binbase.length - 2], 2),
		hasSample: !parseInt(binbase[binbase.length - 3], 2),
		batch_req: parseInt(binbase[binbase.length - 4], 2),
		nb_of_type_measure: parseInt(binbase.substring(0, 4), 2)
	};
}

/* Prepopulate output with relative timestamp and measure of the first sample for each series. */
function prePopulateOutput(out, buffer, argList, flag, tagsz) {
	var currentTimestamp = 0;
	var ST_U8 = 4;
	var index_of_the_first_sample = 0;
	for (var i = 0; i < flag.nb_of_type_measure; i++) {
		var tag = {
			size: tagsz,
			lbl: buffer.getNextSample(ST_U8, tagsz)
		};
		var sampleIndex = findIndexFromArgList(argList, tag);
		if (i == 0) {
			index_of_the_first_sample = sampleIndex;
		}
		currentTimestamp = extractTimestampFromBuffer(buffer, currentTimestamp);
		out.series[sampleIndex] = computeSeries(buffer, argList[sampleIndex].sampletype, tag.lbl, currentTimestamp);
		if (flag.hasSample) {
			out.series[sampleIndex].codingType = buffer.getNextSample(ST_U8, 2);
			out.series[sampleIndex].codingTable = buffer.getNextSample(ST_U8, 2);
		}
	}
	return {
		last_timestamp: currentTimestamp,
		index_of_the_first_sample: index_of_the_first_sample
	};
}

/* Initialize next series from buffer */
function computeSeries(buffer, sampletype, label, currentTimestamp) {
	return {
		uncompressSamples: [{
			data_relative_timestamp: currentTimestamp,
			data: {
				value: getMeasure(buffer, sampletype),
				label: label
			}
		}],
		codingType: 0,
		codingTable: 0,
		resolution: null
	};
}

/* Return the index of tag lbl in the argument list */
function findIndexFromArgList(argList, tag) {
	for (var i = 0; i < argList.length; i++) {
		if (argList[i].taglbl === tag.lbl) {
			return i;
		}
	}
	throw new Error("defined batch tags don’t match the payload");
}

/* Extract a new time stamp using Huff table, optionnaly from a baseTimestamp */
function extractTimestampFromBuffer(buffer, baseTimestamp) {
	var ST_U32 = 10;
	if (baseTimestamp) {
		var bi = buffer.getNextBifromHi(1);
		return computeTimestampFromBi(buffer, baseTimestamp, bi);
	}
	return buffer.getNextSample(ST_U32);
}

/* Compute a new timestamp from a previous one, regarding bi value */
function computeTimestampFromBi(buffer, baseTimestamp, bi) {
	var ST_U32 = 10;
	var BR_HUFF_MAX_INDEX_TABLE = 14;
	if (bi > BR_HUFF_MAX_INDEX_TABLE) {
		return buffer.getNextSample(ST_U32);
	}
	if (bi > 0) {
		return computeTimestampFromPositiveBi(buffer, baseTimestamp, bi);
	}
	return baseTimestamp;
}

/* Compute a new timestamp from a previous one, regarding positive bi value */
function computeTimestampFromPositiveBi(buffer, baseTimestamp, bi) {
	var ST_U32 = 10;
	return buffer.getNextSample(ST_U32, bi) + baseTimestamp + Math.pow(2, bi) - 1;
}

/* Extract the measure from the buffer, handling float case */
function getMeasure(buffer, sampletype) {
	var ST_FL = 12;
	var v = buffer.getNextSample(sampletype);
	return sampletype === ST_FL ? bytes2Float32(v) : v;
}

/* Convert bytes to a float32 representation. */
function bytes2Float32(bytes) {
	var sign = bytes & 0x80000000 ? -1 : 1,
		exponent = (bytes >> 23 & 0xff) - 127,
		significand = bytes & ~(-1 << 23);
	if (exponent == 128) {
		return sign * (significand ? Number.NaN : Number.POSITIVE_INFINITY);
	}
	if (exponent == -127) {
		if (significand == 0) {
			return sign * 0.0;
		}
		exponent = -126;
		significand /= 1 << 22;
	} else {
		significand = (significand | 1 << 23) / (1 << 23);
	}
	return sign * significand * Math.pow(2, exponent);
}

/* Uncompress samples data presenting common timestamp or separate timestamp */
function uncompressSamplesData(out, buffer, index_of_the_first_sample, argList, last_timestamp, flag, tagsz) {
	if (flag.isCommonTimestamp) {
		return handleCommonTimestamp(out, buffer, index_of_the_first_sample, argList, flag, tagsz);
	}
	return handleSeparateTimestamp(out, buffer, argList, last_timestamp, flag, tagsz);
}

/* Uncompress data in case of common timestamp */
function handleCommonTimestamp(out, buffer, index_of_the_first_sample, argList, flag, tagsz) {
	var ST_U8 = 4;
	var BR_HUFF_MAX_INDEX_TABLE = 14;
	var nb_sample_to_parse = buffer.getNextSample(ST_U8, 8);
	var tag = {};

	var temp = initTimestampCommonTable(out, buffer, nb_sample_to_parse, index_of_the_first_sample);
	var timestampCommon = temp.timestampCommon;
	var lastTimestamp = temp.lastTimestamp;

	for (var j = 0; j < flag.nb_of_type_measure; j++) {
		var first_null_delta_value = 1;
		tag.lbl = buffer.getNextSample(ST_U8, tagsz);
		var sampleIndex = findIndexFromArgList(argList, tag);
		for (var i = 0; i < nb_sample_to_parse; i++) {
			/*Available bit*/
			var available = buffer.getNextSample(ST_U8, 1);
			if (available) {
				/*Delta value*/
				var bi = buffer.getNextBifromHi(out.series[sampleIndex].codingTable);
				var currentMeasure = {
					data_relative_timestamp: 0,
					data: {}
				};
				if (bi <= BR_HUFF_MAX_INDEX_TABLE) {
					var precedingValue = out.series[sampleIndex].uncompressSamples[out.series[sampleIndex].uncompressSamples.length - 1].data.value;
					if (bi > 0) {
						currentMeasure.data.value = completeCurrentMeasure(buffer, precedingValue, out.series[sampleIndex].codingType, argList[sampleIndex].resol, bi);
					} else {
						/* (bi <= 0)*/
						if (first_null_delta_value) {
							/* First value is yet recorded starting from the header*/
							first_null_delta_value = 0;
							continue;
						} else {
							currentMeasure.data.value = precedingValue;
						}
					}
				} else {
					/* bi > BR_HUFF_MAX_INDEX_TABLE*/
					currentMeasure.data.value = buffer.getNextSample(argList[sampleIndex].sampletype);
				}
				currentMeasure.data_relative_timestamp = timestampCommon[i];
				out.series[sampleIndex].uncompressSamples.push(currentMeasure);
			}
		}
	}
	return lastTimestamp;
}

/* Initialize common timestamp table. Returns the table and last calculated timestamp */
function initTimestampCommonTable(out, buffer, nbSampleToParse, firstSampleIndex) {
	var ST_U8 = 4;
	var ST_U32 = 10;
	var BR_HUFF_MAX_INDEX_TABLE = 14;
	var timestampCommon = [];
	var lastTimestamp = 0;
	var timestampCoding = buffer.getNextSample(ST_U8, 2);
	for (var i = 0; i < nbSampleToParse; i++) {
		/*delta timestamp*/
		var bi = buffer.getNextBifromHi(timestampCoding);
		if (bi <= BR_HUFF_MAX_INDEX_TABLE) {
			if (i == 0) {
				timestampCommon.push(out.series[firstSampleIndex].uncompressSamples[0].data_relative_timestamp);
			} else {
				if (bi > 0) {
					var precedingTimestamp = timestampCommon[i - 1];
					timestampCommon.push(buffer.getNextSample(ST_U32, bi) + precedingTimestamp + Math.pow(2, bi) - 1);
				} else {
					timestampCommon.push(precedingTimestamp);
				}
			}
		} else {
			timestampCommon.push(buffer.getNextSample(ST_U32));
		}
		lastTimestamp = timestampCommon[i];
	}
	return {
		timestampCommon: timestampCommon,
		lastTimestamp: lastTimestamp
	};
}

/* Complete current measure from the preceding one */
function completeCurrentMeasure(buffer, precedingValue, codingType, resol, bi) {
	var ST_U16 = 6;
	var currentValue = buffer.getNextSample(ST_U16, bi);
	if (codingType === 0) { /* ADLC*/
		return computeAdlcValue(currentValue, resol, precedingValue, bi);
	}
	if (codingType === 1) { /* Positive*/
		return (currentValue + Math.pow(2, bi) - 1) * resol + precedingValue;
	}
	/* Negative*/
	return precedingValue - (currentValue + (Math.pow(2, bi) - 1)) * resol;
}

/* Return current value in ADLC case */
function computeAdlcValue(currentValue, resol, precedingValue, bi) {
	if (currentValue >= Math.pow(2, bi - 1)) {
		return currentValue * resol + precedingValue;
	}
	return (currentValue + 1 - Math.pow(2, bi)) * resol + precedingValue;
}

/* Uncompress data in case of separate timestamp */
function handleSeparateTimestamp(out, buffer, argList, last_timestamp, flag, tagsz) {
	var ST_U8 = 4;
	var BR_HUFF_MAX_INDEX_TABLE = 14;
	var tag = {};
	for (var i = 0; i < flag.nb_of_type_measure; i++) {
		tag.lbl = buffer.getNextSample(ST_U8, tagsz);
		var sampleIndex = findIndexFromArgList(argList, tag);
		var compressSampleNb = buffer.getNextSample(ST_U8, 8);
		if (compressSampleNb) {
			var timestampCoding = buffer.getNextSample(ST_U8, 2);
			for (var j = 0; j < compressSampleNb; j++) {
				var precedingRelativeTimestamp = out.series[sampleIndex].uncompressSamples[out.series[sampleIndex].uncompressSamples.length - 1].data_relative_timestamp;
				var currentMeasure = {
					data_relative_timestamp: 0,
					data: {}
				};
				var bi = buffer.getNextBifromHi(timestampCoding);
				currentMeasure.data_relative_timestamp = computeTimestampFromBi(buffer, precedingRelativeTimestamp, bi);
				if (currentMeasure.data_relative_timestamp > last_timestamp) {
					last_timestamp = currentMeasure.data_relative_timestamp;
				}
				bi = buffer.getNextBifromHi(out.series[sampleIndex].codingTable);
				if (bi <= BR_HUFF_MAX_INDEX_TABLE) {
					var precedingValue = out.series[sampleIndex].uncompressSamples[out.series[sampleIndex].uncompressSamples.length - 1].data.value;
					if (bi > 0) {
						currentMeasure.data.value = completeCurrentMeasure(buffer, precedingValue, out.series[sampleIndex].codingType, argList[sampleIndex].resol, bi);
					} else {
						/* bi <= 0*/
						currentMeasure.data.value = precedingValue;
					}
				} else {
					/* bi > BR_HUFF_MAX_INDEX_TABLE*/
					currentMeasure.data.value = buffer.getNextSample(argList[sampleIndex].sampletype);
				}
				out.series[sampleIndex].uncompressSamples.push(currentMeasure);
			}
		}
	}
	return last_timestamp;
}

/* Translate brUncompress output data to expected structure */
function adaptToExpectedFormat(out, argList, batchAbsoluteTimestamp) {
	var returnedGlobalObject = {
		batch_counter: out.batch_counter,
		batch_relative_timestamp: out.batch_relative_timestamp
	};
	if (batchAbsoluteTimestamp) {
		returnedGlobalObject.batch_absolute_timestamp = batchAbsoluteTimestamp;
	}
	returnedGlobalObject.dataset = out.series.reduce(function (acc, current, index) {
		return acc.concat(current.uncompressSamples.map(function (item) {
			var returned = {
				data_relative_timestamp: item.data_relative_timestamp,
				data: {
					value: argList[index].divide ? item.data.value / argList[index].divide : item.data.value,
					label: argList[index].taglbl
				}
			};
			if (argList[index].lblname) {
				returned.data.label_name = argList[index].lblname;
			}
			if (batchAbsoluteTimestamp) {
				returned.data_absolute_timestamp = computeDataAbsoluteTimestamp(batchAbsoluteTimestamp, out.batch_relative_timestamp, item.data_relative_timestamp);
			}
			return returned;
		}));
	}, []);
	return returnedGlobalObject;
}

/** Compute data absolute timestamp from batch absolute timestamp (bat), batch relative timestamp (brt) and data relative timestamp (drt) */
function computeDataAbsoluteTimestamp(bat, brt, drt) {
	return new Date(new Date(bat) - (brt - drt) * 1000).toISOString();
}

function postProcessModbus8007Answer(dataframe, tabtags, data) {

	var ep = data.endpoint;
	if (typeof ep === 'undefined') throw new Error('modbus endpoint error');

	/*decodage octets adress et commande*/
	data.additionalAddress = dataframe.substring(0, 2); /* valeur restituée en hexa */
	var command = dataframe.substring(2, 4);
	data.modbusCommand = fmapcommandmodbus(command);
	data = decodeModbus('8007', dataframe, ep, tabtags, data);
	return data;
} /*fin postprocess*/


function postProcessModbus8009PresentValue(dataframe, tabtags, data) {
	/* calcul endpoints */

	var b1 = dataframe.substring(0, 2);
	var b1dec = convertNumber(b1, 16, 10);
	data.multimodbusFrameSeriesSent = Number(b1dec);
	var b2 = dataframe.substring(2, 4);
	var b2bin = convertNumber(b2, 16, 2).toString();
	while (b2bin.length < 8) {
		b2bin = '0' + b2bin;
	}
	var nbcurrentframebin = b2bin.substring(0, 3);
	data.multimodbusFrameNumberInSerie = Number(convertNumber(nbcurrentframebin, 2, 10));
	var nblastframebin = b2bin.substring(3, 6);
	data.multimodbusLastFrameOfSerie = Number(convertNumber(nblastframebin, 2, 10));

	var b3 = dataframe.substring(4, 6);
	var b3bin = convertNumber(b3, 16, 2).toString();
	while (b3bin.length < 8) {
		b3bin = '0' + b3bin;
	}
	var ppppppbin = b2bin.substring(6, 8) + b3bin;
	var tabep = []; /*recup endpoints */
	var i = 0;
	for (j = ppppppbin.length; j > 0; j--) {
		var fieldname = 'multimodbusEP' + i;
		if (ppppppbin.substring(j - 1, j) === '1') {
			tabep.push(i);
			data[fieldname] = true;
		}
		else {
			data[fieldname] = false;
		}
		i++;
	}

	/*extraction data de chaque endpoint */
	data.zclindex = 6;
	for (var e = 0; e < tabep.length; e++) {
		var endpoint = tabep[e];
		data = decodeModbus('8009', dataframe, endpoint, tabtags, data);
	}
	delete data.zclindex;
	return data;
} /*fin postprocess*/

function decodeModbus(cluster, datazcl, ep, tabtags, data) {

	/*pr('decodeModbus: data.zclindex = '+ data.zclindex + ' pour le endpoint '+ ep);*/

	var tabtagsLength = -1;
	/*
	var bindex = 0;
	var batchNamesArray = [];
	var batchUnitsArray = [];		
	var batchLblnameArray = [];	*/

	if (tabtags != null) {
		tabtagsLength = tabtags.length;
		/* Remplacement tags batch default profiles */
		if (tabtagsLength >= 1) {
			for (var i = 0; i < tabtagsLength; i++) {
				var tag = tabtags[i];
				switch (tag) {
					case 'MODBUS_8007_PROFILE_EP0_10_REGS_NOUNIT_FL':
						tabtags[i] = 'MODBUS_8007_EP0_1_fregister00_NOUNIT_FL';
						tabtags[tabtagsLength++] = 'MODBUS_8007_EP0_2_fregister01_NOUNIT_FL';
						tabtags[tabtagsLength++] = 'MODBUS_8007_EP0_3_fregister02_NOUNIT_FL';
						tabtags[tabtagsLength++] = 'MODBUS_8007_EP0_4_fregister03_NOUNIT_FL';
						tabtags[tabtagsLength++] = 'MODBUS_8007_EP0_5_fregister04_NOUNIT_FL';
						tabtags[tabtagsLength++] = 'MODBUS_8007_EP0_6_fregister05_NOUNIT_FL';
						tabtags[tabtagsLength++] = 'MODBUS_8007_EP0_7_fregister06_NOUNIT_FL';
						tabtags[tabtagsLength++] = 'MODBUS_8007_EP0_8_fregister07_NOUNIT_FL';
						tabtags[tabtagsLength++] = 'MODBUS_8007_EP0_9_fregister08_NOUNIT_FL';
						tabtags[tabtagsLength++] = 'MODBUS_8007_EP0_10_fregister09_NOUNIT_FL';
						break;
					case 'MODBUS_8009_PROFILE_NOHEADER_10_ENDPOINTS_REG_NOUNIT_FL':
						tabtags[i] = 'MODBUS_8009_WITHOUT_HEADER';
						tabtags[tabtagsLength++] = 'MODBUS_8009_EP0_1_fregister00_NOUNIT_FL';
						tabtags[tabtagsLength++] = 'MODBUS_8009_EP1_1_fregister01_NOUNIT_FL';
						tabtags[tabtagsLength++] = 'MODBUS_8009_EP2_1_fregister02_NOUNIT_FL';
						tabtags[tabtagsLength++] = 'MODBUS_8009_EP3_1_fregister03_NOUNIT_FL';
						tabtags[tabtagsLength++] = 'MODBUS_8009_EP4_1_fregister04_NOUNIT_FL';
						tabtags[tabtagsLength++] = 'MODBUS_8009_EP5_1_fregister05_NOUNIT_FL';
						tabtags[tabtagsLength++] = 'MODBUS_8009_EP6_1_fregister06_NOUNIT_FL';
						tabtags[tabtagsLength++] = 'MODBUS_8009_EP7_1_fregister07_NOUNIT_FL';
						tabtags[tabtagsLength++] = 'MODBUS_8009_EP8_1_fregister08_NOUNIT_FL';
						tabtags[tabtagsLength++] = 'MODBUS_8009_EP9_1_fregister09_NOUNIT_FL';
						break;
					case 'MODBUS_8007_PROFILE_EP0_10_REGS_NOUNIT_FL2L':
						tabtags[i] = 'MODBUS_8007_EP0_1_fregister00_NOUNIT_FL2L';
						tabtags[tabtagsLength++] = 'MODBUS_8007_EP0_2_fregister01_NOUNIT_FL2L';
						tabtags[tabtagsLength++] = 'MODBUS_8007_EP0_3_fregister02_NOUNIT_FL2L';
						tabtags[tabtagsLength++] = 'MODBUS_8007_EP0_4_fregister03_NOUNIT_FL2L';
						tabtags[tabtagsLength++] = 'MODBUS_8007_EP0_5_fregister04_NOUNIT_FL2L';
						tabtags[tabtagsLength++] = 'MODBUS_8007_EP0_6_fregister05_NOUNIT_FL2L';
						tabtags[tabtagsLength++] = 'MODBUS_8007_EP0_7_fregister06_NOUNIT_FL2L';
						tabtags[tabtagsLength++] = 'MODBUS_8007_EP0_8_fregister07_NOUNIT_FL2L';
						tabtags[tabtagsLength++] = 'MODBUS_8007_EP0_9_fregister08_NOUNIT_FL2L';
						tabtags[tabtagsLength++] = 'MODBUS_8007_EP0_10_fregister09_NOUNIT_FL2L';
						break;
					case 'MODBUS_8009_PROFILE_NOHEADER_10_ENDPOINTS_REG_NOUNIT_FL2L':
						tabtags[i] = 'MODBUS_8009_WITHOUT_HEADER';
						tabtags[tabtagsLength++] = 'MODBUS_8009_EP0_1_fregister00_NOUNIT_FL2L';
						tabtags[tabtagsLength++] = 'MODBUS_8009_EP1_1_fregister01_NOUNIT_FL2L';
						tabtags[tabtagsLength++] = 'MODBUS_8009_EP2_1_fregister02_NOUNIT_FL2L';
						tabtags[tabtagsLength++] = 'MODBUS_8009_EP3_1_fregister03_NOUNIT_FL2L';
						tabtags[tabtagsLength++] = 'MODBUS_8009_EP4_1_fregister04_NOUNIT_FL2L';
						tabtags[tabtagsLength++] = 'MODBUS_8009_EP5_1_fregister05_NOUNIT_FL2L';
						tabtags[tabtagsLength++] = 'MODBUS_8009_EP6_1_fregister06_NOUNIT_FL2L';
						tabtags[tabtagsLength++] = 'MODBUS_8009_EP7_1_fregister07_NOUNIT_FL2L';
						tabtags[tabtagsLength++] = 'MODBUS_8009_EP8_1_fregister08_NOUNIT_FL2L';
						tabtags[tabtagsLength++] = 'MODBUS_8009_EP9_1_fregister09_NOUNIT_FL2L';
						break;
					default:
						break;
				}
			}
		}
	}

	var datazclsav = datazcl;
	var header = true;
	if (cluster === '8007') {
		datazcl = datazcl.substring(6);
	}
	if (cluster === '8009') {
		for (var t = 0; t < tabtags.length; t++) {
			if (tabtags[t].toUpperCase() === 'MODBUS_8009_WITHOUT_HEADER') header = false;
		}
		if ((data.zclindex > 0) && (datazcl.length >= data.zclindex)) {
			datazcl = datazcl.substring(data.zclindex);
		}
		if (header) {
			datazcl = datazcl.substring(6);
			data.zclindex = data.zclindex + 6;
		}
	}

	if (datazcl == "") return data;

	var searchpat = '';
	if (cluster === '8007') searchpat = 'MODBUS_8007_EP' + ep;
	else if (cluster === '8009') searchpat = 'MODBUS_8009_EP' + ep;
	else throw new Error('cluster number error');

	var searchpatlength = searchpat.length;
	var tabAllowedTypes = ['U', 'S', 'F', 'A', 'H', 'B', 'R'];
	var tabname = ["unused"];
	var tabunit = ["unused"];
	var tabtype = ["unused"];
	var tablgdigits = [0];/* nb bits ou octets */
	var tabT = ["unused"];
	var tabhexa = ["unused"];
	var nbtags = 0;

	for (var t = 0; t < tabtags.length; t++) {
		var currentTag = tabtags[t];
		if (currentTag.length > 14 && currentTag.substring(0, searchpatlength).toUpperCase() == searchpat) {
			var tabsplit = currentTag.split('_');
			if (tabsplit.length == 7) {
				var rang = parseInt(tabsplit[3]);
				if (Number.isInteger(rang) && rang > 0) {
					tabname[rang] = tabsplit[4];
					tabunit[rang] = tabsplit[5];
					var type = tabsplit[6].toUpperCase();
					tabtype[rang] = type;
					var ttyp = type.substring(0, 1);
					if (tabAllowedTypes.indexOf(ttyp) == -1) throw new Error('modbus wrong tag');
					if (type == 'U8' || type == 'S8') tablgdigits[rang] = 2;
					else if (type == 'U16' || type == 'S16') tablgdigits[rang] = 4;
					else if (type == 'U32' || type == 'S32') tablgdigits[rang] = 8;
					else if (type == 'U32L' || type == 'S32L') tablgdigits[rang] = 8;
					else if (ttyp == 'F') tablgdigits[rang] = 8;
					else if (ttyp == 'A' || ttyp == 'H') {
						var tlg = parseInt(type.substring(1, type.length));
						if (Number.isInteger(tlg)) {
							tablgdigits[rang] = tlg * 2;
						}
						else throw new Error('modbus wrong tag');
					}
					if (ttyp == 'B' || ttyp == 'R') {
						var tlg = parseInt(type.substring(1, type.length));
						if (Number.isInteger(tlg)) tablgdigits[rang] = tlg;
						else throw new Error('modbus wrong tag');
					}
					tabT[rang] = ttyp;
					nbtags++;
				}
				else throw new Error('modbus wrong tag');
			}
		}	/* fin if*/
	}/*fin for*/

	/* controle tag manquant type erroné*/
	var taballtypes = ['U8', 'S8', 'U16', 'S16', 'U32', 'S32', 'U32L', 'S32L', 'FL', 'FL2L'];
	var taballprefs = ['A', 'H', 'B', 'R'];
	if (nbtags === 0) {
		if (cluster === '8007') data.answerFrame = datazclsav;
		else if (cluster === '8009') data.presentValue = datazclsav;
		throw new Error('Not defined tags for endpoint ' + ep);
		return data;
	}
	for (var t = 1; t < nbtags + 1; t++) {
		if (typeof tabtype[t] === 'undefined') throw new Error('modbus tag missing');
		else if (taballtypes.indexOf(tabtype[t]) == -1) {
			if (taballprefs.indexOf(tabtype[t].substring(0, 1)) == -1) throw new Error('modbus error type in tag');
		}
	}

	/*valeurs hexa et bin */
	var index = 0;
	for (var t = 1; t < nbtags + 1; t++) {
		if (tabT[t] != 'B' && tabT[t] != 'R') {
			tabhexa[t] = datazcl.substring(index, index + tablgdigits[t]);
			index = index + tablgdigits[t];
		}
		else {
			var lgbits = tablgdigits[t];
			var bsav = 0;
			for (var b = t + 1; b < nbtags + 1; b++) {
				if (tabT[b] == 'B' || tabT[b] == 'R') {
					lgbits += tablgdigits[b];
					bsav = b;
				}
				else b = nbtags + 1;
			}
			var nboctets = Math.floor((lgbits + 7) / 8);
			var hexa = datazcl.substring(index, index + (nboctets * 2));
			index = index + (nboctets * 2);
			var bin = convertNumber(hexa, 16, 2);

			var strbin = bin.toString();
			var blocklength = strbin.length;
			for (var w = t; (lgbits <= blocklength) && (w < nbtags + 1); w++) {
				lgbits = tablgdigits[w];
				tabhexa[w] = strbin.substring(0, lgbits);
				strbin = strbin.substring(lgbits, blocklength);
				blocklength = strbin.length;
			}
			t = bsav;
		}
	}
	if (cluster === '8009') {
		data.zclindex = data.zclindex + index;
	}

	/* extraction value */
	var index = 0;
	var lgdigits = 0;
	if (cluster === '8009') {
		var newepkey = 'multimodbus_EP' + ep;
		data[newepkey] = {};
	}
	for (var t = 1; t < nbtags + 1; t++) {
		var name = tabname[t];
		var unit = tabunit[t];
		var type = tabtype[t];
		var ttyp = tabT[t];
		var lgdigits = tablgdigits[t];
		var hexa = tabhexa[t];

		if (ttyp == 'U' || ttyp == 'S') {
			var value = 0;
			if (type == 'U8' || type == 'U16' || type == 'U32') value = modbushexi(hexa, false);
			else if (type == 'S8' || type == 'S16' || type == 'S32') value = modbushexi(hexa, true);
			else if (type == 'U32L') value = modbushexi2L(hexa, false);
			else if (type == 'S32L') value = modbushexi2L(hexa, true);
			if (unit == 'NOUNIT') {
				if (cluster === '8009') data[newepkey][name] = value;
				else data[name] = value;
			}
			else {
				if (cluster === '8009') data[newepkey][name] = { 'unit': unit, 'value': value };
				else data[name] = { 'unit': unit, 'value': value };
			}
		}
		else if (ttyp == 'F') {
			var value = 0;
			if (type == 'FL') value = parseFloat(modbushexfloat(hexa));
			else if (type == 'FL2L') value = parseFloat(modbushexfloat2L(hexa));
			if (unit == 'NOUNIT') {
				if (cluster === '8009') data[newepkey][name] = value;
				else data[name] = value;
			}
			else {
				if (cluster === '8009') data[newepkey][name] = { 'unit': unit, 'value': value };
				else data[name] = { 'unit': unit, 'value': value };
			}
		}
		else if (ttyp == 'A' || ttyp == 'H') {
			var value = '';
			if (ttyp == 'A') value = modbushexascii(hexa);
			else if (ttyp == 'H') value = hexa;
			if (unit == 'NOUNIT') {
				if (cluster === '8009') data[newepkey][name] = value;
				else data[name] = value;
			}
			else {
				if (cluster === '8009') data[newepkey][name] = { 'unit': unit, 'value': value };
				else data[name] = { 'unit': unit, 'value': value };
			}
		}
		else if (tabT[t] == 'B') {
			var value;
			value = convertNumber(hexa, 2, 10);
			if (unit == 'NOUNIT') {
				if (cluster === '8009') data[newepkey][name] = value;
				else data[name] = value;
			}
			else {
				if (cluster === '8009') data[newepkey][name] = { 'unit': unit, 'value': value };
				else data[name] = { 'unit': unit, 'value': value };
			}
		}
		else if (tabT[t] != 'R') throw new Error('Type in Tag not recognized');
	}/* fin for*/
	return data;
} /*fin decodeModbus*/

function modbushexi(hex, signed) {
	if (hex.length % 2 != 0) {
		hex = "0" + hex;
	}
	var num = parseInt(hex, 16);
	if (!signed) return num;
	var maxVal = Math.pow(2, hex.length / 2 * 8);
	if (num > maxVal / 2 - 1) {
		num = num - maxVal
	}
	return num;
}

function modbushexi2L(hex, signed) {
	var p1 = hex.substring(0, 4);
	var p2 = hex.substring(4, 8);
	hex = "";
	hex = p2 + p1;
	return modbushexi(hex, signed);
}

function modbushexfloat(hex) {
	var intval = modbushexi(hex, false);
	return ieee32ToFloat(intval, 6);
}

function modbushexfloat2L(hex) {
	var p1 = hex.substring(0, 4);
	var p2 = hex.substring(4, 8);
	hex = "";
	hex = p2 + p1;
	var intval = modbushexi(hex, false);
	return ieee32ToFloat(intval, 6);
}

function modbushexascii(hexasc) {
	var hex = hexasc.toString();
	var str = '';
	for (var n = 0; n < hex.length; n += 2) {
		str += String.fromCharCode(parseInt(hex.substr(n, 2), 16));
	}
	return str;
}

function convertNumber(n, fromBase, toBase) {
	if (fromBase === void 0) {
		fromBase = 10;
	}
	if (toBase === void 0) {
		toBase = 10;
	}
	return parseInt(n.toString(), fromBase).toString(toBase);
}

function lengthAccordingType(typ) {
	if (typ.length != 2) return 0;
	var lg = 0;
	switch (typ) {
		case '10':
		case '08':
		case '18':
		case '20':
		case '28':
		case '30':
			lg = 1; break;
		case '29':
		case '19':
		case '21':
		case '09':
			lg = 2; break;
		case '0A':
		case '22':
			lg = 3; break;
		case '0B':
		case '23':
		case '2B':
		case '39':
			lg = 4; break;
	}
	return lg;
}

function dslAccordingType(typ) {
	if (typ.length != 2) return 0;
	var dsl = '';
	switch (typ) {
		case '10':
		case '08':
		case '18':
		case '20':
		case '30': dsl = 'ubyte'; break;
		case '28': dsl = 'byte'; break;
		case '29': dsl = 'short'; break;
		case '09':
		case '19':
		case '21': dsl = 'ushort'; break;
		case '0A': dsl = 'ubyte[3]'; break;
		case '22': dsl = 'ubyte[3]'; break;

		case '0B':
		case '23': dsl = 'ubyte[4]'; break;
		case '2B': dsl = 'int'; break;
		case '39': dsl = 'int'; break;
			lg = 4; break;
	}
	return dsl;
}

function calculateType39FromDslInt(intdecodedvalue, decimalnumber) {
	var val = parseFloat(ieee32ToFloat(intdecodedvalue, decimalnumber));
	return val;
}

function calculateType23FromDslTab4Ubytes(tabdecodedvalue) {
	var val = 16777216 * tabdecodedvalue[0] + 65536 * tabdecodedvalue[1] + 256 * tabdecodedvalue[2] + tabdecodedvalue[3];
	return val;
}

function decodeCommand8A(encoded, data, attributTypeHexavalue, fieldIndex) {
	delete data.todel;
	data.reportParameters = {};
	data.reportParameters.batch = (data.batch == 1) ? true : false;
	delete data.batch;
	data.reportParameters.noHeaderPort = (data.nohp == 1) ? true : false;
	delete data.nohp;
	data.reportParameters.secured = (data.secu == 1) ? true : false;
	delete data.secu;
	data.reportParameters.securedIfAlarm = (data.secifa == 1) ? true : false;
	delete data.secifa;
	switch (data.cr) {
		case 0: data.reportParameters.causeRequest = 'no'; break;
		case 1: data.reportParameters.causeRequest = 'short'; break;
		case 2: data.reportParameters.causeRequest = 'long'; break;
		case 3: data.reportParameters.causeRequest = 'reserved'; break;
	}
	delete data.cr;
	data.reportParameters.newModeConfiguration = (data.nmc == 1) ? true : false;
	delete data.nmc;

	var longueurselontype = lengthAccordingType(attributTypeHexavalue);
	var partiehexacause = encoded.substring(14 + (longueurselontype * 2) + 2);
	data.criterions = [];


	if (data.reportParameters.causeRequest == 'short') {
		var nbcrit = (partiehexacause.length) / 2;
		for (var c = 0; c < nbcrit; c++) {
			var extract = partiehexacause.substring((c * 2), (c * 2 + 2));
			var critdsl = 'bit:3 ci; bit:2 decmode; bit of; bit oe; bit ala; ';
			try {
				var decodedcrit = BinaryDecoder.decode(critdsl, extract);
				var datacrit = JSON.parse(decodedcrit);
			} catch (e) {
				return "{\"error\":\"decoding failed\"}";
			}
			var criterion = {};
			criterion.criteriaSlotDescriptor = {};
			criterion.criteriaSlotDescriptor.criterionIndex = datacrit.ci;
			switch (datacrit.decmode) {
				case 0: criterion.criteriaSlotDescriptor.mode = 'unused'; break;
				case 1: criterion.criteriaSlotDescriptor.mode = 'delta'; break;
				case 2: criterion.criteriaSlotDescriptor.mode = 'threshold'; break;
				case 3: criterion.criteriaSlotDescriptor.mode = 'threshold with actions'; break;
			}
			criterion.criteriaSlotDescriptor.onFall = (datacrit.of == 1) ? true : false;
			criterion.criteriaSlotDescriptor.onExceed = (datacrit.oe == 1) ? true : false;
			criterion.criteriaSlotDescriptor.alarm = (datacrit.ala == 1) ? true : false;
			data.criterions.push(criterion);
		}
	}
	/*long*/
	else if (data.reportParameters.causeRequest == 'long') {

		while (partiehexacause.length > 1) {
			var extract = partiehexacause.substring(0, 2);          /*1er octet*/
			var critdsl = 'bit:3 ci; bit:2 decmode; bit of; bit oe; bit ala; ';
			try {
				var decodedcrit = BinaryDecoder.decode(critdsl, extract);
				var datacrit = JSON.parse(decodedcrit);
			} catch (e) {
				return "{\"error\":\"decoding failed\"}";
			}
			var criterion = {};
			criterion.criteriaSlotDescriptor = {};
			criterion.criteriaSlotDescriptor.criterionIndex = datacrit.ci;
			switch (datacrit.decmode) {
				case 0: criterion.criteriaSlotDescriptor.mode = 'unused'; break;
				case 1: criterion.criteriaSlotDescriptor.mode = 'delta'; break;
				case 2: criterion.criteriaSlotDescriptor.mode = 'threshold'; break;
				case 3: criterion.criteriaSlotDescriptor.mode = 'threshold with actions'; break;
			}
			criterion.criteriaSlotDescriptor.onFall = (datacrit.of == 1) ? true : false;
			criterion.criteriaSlotDescriptor.onExceed = (datacrit.oe == 1) ? true : false;
			criterion.criteriaSlotDescriptor.alarm = (datacrit.ala == 1) ? true : false;
			partiehexacause = partiehexacause.substring(2);       /* partihexacause.length -2 */
			var offset = 0;
			var newdsl = '';
			/*2eme octet This field is optional. It is only present for multiple fields attributes (Power monitor, Simple-metering, Power quality). The indexes are defined by documentation on each attibute.*/
			if (fieldIndex) {
				newdsl += 'ubyte field_index; ';
				offset += 2;
			}
			if (criterion.criteriaSlotDescriptor.mode != 'unused') {
				newdsl += dslAccordingType(attributTypeHexavalue) + ' value ; ';
				offset += lengthAccordingType(attributTypeHexavalue) * 2;
			}
			if (criterion.criteriaSlotDescriptor.mode == 'threshold' || criterion.criteriaSlotDescriptor.mode == 'threshold with actions') {
				newdsl += dslAccordingType(attributTypeHexavalue) + ' gap ; ';
				offset += lengthAccordingType(attributTypeHexavalue) * 2;
			}
			if (criterion.criteriaSlotDescriptor.mode == 'threshold') {
				newdsl += 'bit:7 nbocc ; bit hl ;';
				offset += 2;
			}
			try {
				var decodedcrit2 = BinaryDecoder.decode(newdsl, partiehexacause);
				var datacrit2 = JSON.parse(decodedcrit2);
			} catch (e) {
				return "{\"error\":\"decoding failed\"}";
			}
			partiehexacause = partiehexacause.substring(offset);
			if (fieldIndex) criteriaSlotDescriptor.fieldIndex = datacrit2.field_index;
			if (criterion.criteriaSlotDescriptor.mode != 'unused') {
				var decval = datacrit2.value;
				if (attributTypeHexavalue == '39') decval = calculateType39FromDslInt(datacrit2.value, 6);
				if (attributTypeHexavalue == '23') decval = calculateType23FromDslTab4Ubytes(datacrit2.value);
				criterion.value = decval;
			}
			if (criterion.criteriaSlotDescriptor.mode == 'threshold' || criterion.criteriaSlotDescriptor.mode == 'threshold with actions') {
				var decgap = datacrit2.gap;
				if (attributTypeHexavalue == '39') decgap = calculateType39FromDslInt(datacrit2.gap, 6);
				if (attributTypeHexavalue == '23') decgap = calculateType23FromDslTab4Ubytes(datacrit2.gap);
				criterion.gap = decgap;
			}
			if (criterion.criteriaSlotDescriptor.mode == 'threshold') {
				criterion.numberOfOccurances = datacrit2.nbocc;
				if (datacrit2.hl == 1) {
					try {
						var decodedcrit3 = BinaryDecoder.decode('ushort occh; ushort occl;', partiehexacause);
						var datacrit3 = JSON.parse(decodedcrit3);
					} catch (e) {
						return "{\"error\":\"decoding failed\"}";
					}
					partiehexacause = partiehexacause.substring(8);
					criterion.occurencesHigh = datacrit3.occh;
					criterion.occurencesLow = datacrit3.occl;
				}
			}
			data.criterions.push(criterion);
		}

	}/*fin long*/
	return data;
}


Number.isInteger = Number.isInteger || function (value) {
	return typeof value === "number" && isFinite(value) && Math.floor(value) === value;
};

function fmapcommandmodbus(code) {
	var mapcommand = {
		'01': 'Read Coils',
		'02': 'Read Discrete Inputs',
		'03': 'Read Holding Registers',
		'04': 'Read Input Register',
		'07': 'Read Exception Status',
		'20': 'Read File Record',
		'23': 'Read/Write Multiple Registers',
		'43': 'Read Device Identification',
		'': 'UNDEFINED'
	};
	if (typeof mapcommand[code] === 'undefined') return 'UNDEFINED';
	return mapcommand[code];
}


function isBatchPayload(str) {
	if (str.length < 2) throw new Error("incomplete payload");
	var firstbyte = str.substring(0, 2);
	var binaire = parseInt(firstbyte, 16).toString(2);
	if (binaire.charAt(binaire.length - 1) == '0') return true;
	else return false;
}

function decodeBatch(encoded, dataMessage) {

	var batchArray = [];
	var tagsize = -1;

	try {
		var dataMess = JSON.parse(dataMessage);
		if (dataMess == null) {
			throw new Error("empty dataMessage");
		}
		var tagArray = dataMess.tags;
		var payloadTimestamp = dataMess.timestamp;
		var tagArrayLength = -1;
		var bindex = 0;
		var batchNamesArray = [];
		var batchUnitsArray = [];
		var batchLblnameArray = [];

		if (tagArray != null) {
			tagArrayLength = tagArray.length;
			/* Remplacement tags batch default profiles */
			if (tagArrayLength >= 1) {
				for (var i = 0; i < tagArrayLength; i++) {
					var tag = tagArray[i];
					switch (tag) {
						case 'BATCH_5070074_DEFAULT_PROFILE': /* vaqao+ */
							tagArray[i] = 'BATCH_tagsize_3';
							tagArray[tagArrayLength++] = 'BATCH_occupancy_NoUnit_L0_R1_T4';
							tagArray[tagArrayLength++] = 'BATCH_temperatures_°C_L1_R10_T7_D100';
							tagArray[tagArrayLength++] = 'BATCH_relativeHumidities_%RH_L2_R100_T6_D100';
							tagArray[tagArrayLength++] = 'BATCH_CO2_ppm_L3_R10_T6';
							tagArray[tagArrayLength++] = 'BATCH_COV_NoUnit_L4_R10_T6';
							tagArray[tagArrayLength++] = 'BATCH_luminosity_lux_L5_R10_T6';
							tagArray[tagArrayLength++] = 'BATCH_pressure_hPa_L6_R10_T6';
							break;
						case 'BATCH_5070043_DEFAULT_PROFILE': /*Remote T */
						case 'BATCH_5070126_DEFAULT_PROFILE': /*Remote T */
							tagArray[i] = 'BATCH_tagsize_1';
							tagArray[tagArrayLength++] = 'BATCH_temperatures_°C_L0_R10_T7_D100';
							tagArray[tagArrayLength++] = 'BATCH_temperatures_°C_L1_R10_T7_D100';
							tagArray[tagArrayLength++] = 'BATCH_batteryLevels_mV_L1_R100_T6';
							break;
						case 'BATCH_5070099_DEFAULT_PROFILE': /* atmo */
							tagArray[i] = 'BATCH_tagsize_3';
							tagArray[tagArrayLength++] = 'BATCH_temperatures_°C_L0_R1_T7_D100';
							tagArray[tagArrayLength++] = 'BATCH_relativeHumidities_%RH_L1_R1_T6_D100';
							tagArray[tagArrayLength++] = 'BATCH_pressure_hPa_L2_R1_T7';
							tagArray[tagArrayLength++] = 'BATCH_index1_/_L3_R1_T10';
							tagArray[tagArrayLength++] = 'BATCH_index2_/_L4_R1_T10';
							tagArray[tagArrayLength++] = 'BATCH_batteryLevels_mV_L5_R1_T6';
							break;
						case 'BATCH_5070124_DEFAULT_PROFILE': /* torano */
						case 'BATCH_5070150_DEFAULT_PROFILE':
							tagArray[i] = 'BATCH_tagsize_4';
							tagArray[tagArrayLength++] = 'BATCH_index1_/_L0_R1_T10_D1';
							tagArray[tagArrayLength++] = 'BATCH_index2_/_L1_R1_T10_D1';
							tagArray[tagArrayLength++] = 'BATCH_index3_/_L2_R1_T10_D1';
							tagArray[tagArrayLength++] = 'BATCH_state1_/_L3_R1_T1';
							tagArray[tagArrayLength++] = 'BATCH_state2_/_L4_R1_T1';
							tagArray[tagArrayLength++] = 'BATCH_state3_/_L5_R1_T1';
							tagArray[tagArrayLength++] = 'BATCH_4-20mA_mA_L6_R0.004_T12';
							tagArray[tagArrayLength++] = 'BATCH_0-5V-1_V_L7_R1_T12_D1000';
							tagArray[tagArrayLength++] = 'BATCH_0-5V-2_V_L8_R1_T12_D1000';
							tagArray[tagArrayLength++] = 'BATCH_ratio-1_%_L9_R1_T12_D1000';
							tagArray[tagArrayLength++] = 'BATCH_ratio-2_%_L10_R1_T12_D1000';
							tagArray[tagArrayLength++] = 'BATCH_batteryLevels_mV_L11_R100_T6';
							break;
						case 'BATCH_5070001_DEFAULT_PROFILE': /*Pulse S0 */
							tagArray[i] = 'BATCH_tagsize_1';
							tagArray[tagArrayLength++] = 'BATCH_index1_/_L0_R1_T10';
							tagArray[tagArrayLength++] = 'BATCH_batteryLevels_mV_L1_R100_T6';
							break;
						case 'BATCH_5070017_DEFAULT_PROFILE': /*Presso*/
						case 'BATCH_5070189_DEFAULT_PROFILE':
							tagArray[i] = 'BATCH_tagsize_3';
							tagArray[tagArrayLength++] = 'BATCH_4-20mA_mA_L0_R0.004_T12';
							tagArray[tagArrayLength++] = 'BATCH_0-10V_mV_L1_R1_T12_D1000';
							tagArray[tagArrayLength++] = 'BATCH_batteryLevels_mV_L2_R100_T6';
							tagArray[tagArrayLength++] = 'BATCH_ExtPowerLevels_mV_L3_R100_T6';
							tagArray[tagArrayLength++] = 'BATCH_index1_/_L4_R1_T10';
							break;
						case 'BATCH_5070001_DEFAULT_PROFILE': /*Flasho */
							tagArray[i] = 'BATCH_tagsize_2';
							tagArray[tagArrayLength++] = 'BATCH_currents_A_L0_R0.1_T12';
							tagArray[tagArrayLength++] = 'BATCH_batteryLevels_mV_L1_R100_T6';
							tagArray[tagArrayLength++] = '';
							break;
						case 'BATCH_5070141_DEFAULT_PROFILE': /*Monito*/
							tagArray[i] = 'BATCH_tagsize_3';
							tagArray[tagArrayLength++] = 'BATCH_0-100mV_mV_L0_R0.02_T12';
							tagArray[tagArrayLength++] = 'BATCH_0-70V_V_L1_R17_T12_D1000';
							tagArray[tagArrayLength++] = 'BATCH_batteryLevels_mV_L2_R100_T6';
							break;
						case 'BATCH_5070053_DEFAULT_PROFILE':
						case 'BATCH_5070191_DEFAULT_PROFILE':
						case 'BATCH_5070162_DEFAULT_PROFILE':
						case 'BATCH_5070200_DEFAULT_PROFILE':
							tagArray[i] = 'BATCH_tagsize_2';
							tagArray[tagArrayLength++] = 'BATCH_temperatures_°C_L0_R10_T7_D100';
							tagArray[tagArrayLength++] = 'BATCH_relativeHumidities_%RH_L1_R100_T6_D100';
							tagArray[tagArrayLength++] = 'BATCH_batteryLevels_mV_L2_R1_T6';
							tagArray[tagArrayLength++] = 'BATCH_openCaseAlarms_NoUnit_L3_R1_T1';
							break;
						case 'BATCH_5070085_DEFAULT_PROFILE':
						case 'BATCH_5070167_DEFAULT_PROFILE':
							tagArray[i] = 'BATCH_tagsize_2';
							tagArray[tagArrayLength++] = 'BATCH_temperatures_°C_L0_R10_T7_D100';
							tagArray[tagArrayLength++] = 'BATCH_batteryLevels_mV_L2_R1_T6';
							tagArray[tagArrayLength++] = 'BATCH_openCaseAlarms_NoUnit_L3_R1_T1';
							break;
						case 'BATCH_5070123_DEFAULT_PROFILE':
						case 'BATCH_5070152_DEFAULT_PROFILE':
						case 'BATCH_5070160_DEFAULT_PROFILE':
						case 'BATCH_5070161_DEFAULT_PROFILE':
						case 'BATCH_5070039_DEFAULT_PROFILE':
							tagArray[i] = 'BATCH_tagsize_4';
							tagArray[tagArrayLength++] = 'BATCH_index1_/_L0_R1_T10_D1';
							tagArray[tagArrayLength++] = 'BATCH_index2_/_L1_R1_T10_D1';
							tagArray[tagArrayLength++] = 'BATCH_index3_/_L2_R1_T10_D1';
							tagArray[tagArrayLength++] = 'BATCH_batteryLevels_mV_L6_R100_T6';
							break;
						case 'BATCH_5070163_DEFAULT_PROFILE':
							tagArray[i] = 'BATCH_tagsize_3';
							tagArray[tagArrayLength++] = 'BATCH_temperatures-1_°C_L0_R10_T7_D100';
							tagArray[tagArrayLength++] = 'BATCH_temperatures-2_°C_L1_R10_T7_D100';
							tagArray[tagArrayLength++] = 'BATCH_batteryLevels_mV_L5_R100_T6';
							break;
						case 'BATCH_5070164_DEFAULT_PROFILE':
							tagArray[i] = 'BATCH_tagsize_3';
							tagArray[tagArrayLength++] = 'BATCH_temperatures_°C_L0_R10_T7_D100';
							tagArray[tagArrayLength++] = 'BATCH_batteryLevels_mV_L5_R100_T6';
							break;
						case 'BATCH_5070168_DEFAULT_PROFILE':
							tagArray[i] = 'BATCH_tagsize_3';
							tagArray[tagArrayLength++] = 'BATCH_temperatures_°C_L1_R10_T7_D100';
							tagArray[tagArrayLength++] = 'BATCH_relativeHumidities_%RH_L2_R100_T6_D100';
							tagArray[tagArrayLength++] = 'BATCH_CO2_ppm_L3_R10_T6';
							tagArray[tagArrayLength++] = 'BATCH_COV_NoUnit_L4_R10_T6';
							break;
						case 'BATCH_5070108_DEFAULT_PROFILE':
							tagArray[i] = 'BATCH_tagsize_2';
							tagArray[tagArrayLength++] = 'BATCH_openClose_NoUnit_L0_R1_T1';
							tagArray[tagArrayLength++] = 'BATCH_batteryLevels_mV_L1_R100_T6';
							break;
						case 'BATCH_5070101_DEFAULT_PROFILE':
						case 'BATCH_5070166_DEFAULT_PROFILE':
							tagArray[i] = 'BATCH_tagsize_3';
							tagArray[tagArrayLength++] = 'BATCH_meanDifferentialPressureSinceLastReport_Pa_L0_R1_T7';
							tagArray[tagArrayLength++] = 'BATCH_minimalDifferentialPressureSinceLastReport_Pa_L1_R1_T7';
							tagArray[tagArrayLength++] = 'BATCH_maximalDifferentialPressureSinceLastReport_Pa_L2_R1_T7';
							tagArray[tagArrayLength++] = 'BATCH_batteryLevels_mV_L3_R1_T6';
							tagArray[tagArrayLength++] = 'BATCH_temperatures_°C_L4_R10_T7';
							tagArray[tagArrayLength++] = 'BATCH_differentialPressures_Pa_L5_R1_T7';
							tagArray[tagArrayLength++] = 'BATCH_index_NoUnit_L6_R1_T10';
							tagArray[tagArrayLength++] = 'BATCH_states_NoUnit_L7_R1_T1';
             
							break;

						default:
							break;
					}
				}
			}

			/*exploitation tags*/
			if (tagArrayLength >= 1) {
				for (var i = 0; i < tagArrayLength; i++) {
					var tag = tagArray[i];
					if (tag.substring(0, 14).toLowerCase() == 'batch_tagsize_') {
						var lg = tag.length;
						tagsize = parseInt(tag.substring(14, lg));
					}
					else if (tag.substring(0, 5).toLowerCase() == 'batch') {
						/*var a = "BATCH_temperature_degrees_L0_R100_T7"*/
						var words = tag.split('_');
						if (words.length > 5) {
							var nom = words[1];
							batchNamesArray[bindex] = nom;
							var unite = words[2];
							batchUnitsArray[bindex] = unite;
							var work = words[3];
							var label = -1;
							if (work.substring(0, 1).toUpperCase() == 'L') {
								var num = work.substring(1, work.length);
								if (isNaN(num)) throw new Error('label not numeric in tag');
								label = parseInt(num);
							}
							else throw new Error('label missing in tag');
							work = words[4];
							var reso = -1;
							if (work.substring(0, 1).toUpperCase() == 'R') {
								var num = work.substring(1, work.length);
								if (isNaN(num)) throw new Error('resolution not numeric in tag');
								reso = parseInt(num);
							}
							else throw new Error('resolution missing in tag');
							work = words[5];
							var typ = -1;
							if (work.substring(0, 1).toUpperCase() == 'T') {
								var num = work.substring(1, work.length);
								if (isNaN(num)) throw new Error('type not numeric in tag');
								typ = parseInt(num);
								if (typ < 1 || typ > 12) throw new Error('invalid type in tag');
							}
							else throw new Error('type missing in tag');
							var divi = 0;
							if (words.length == 7) {
								work = words[6];
								var num = work.substring(1, work.length);
								if (isNaN(num)) throw new Error('divide not numeric in tag');
								divi = parseInt(num);
							}
							if (divi != 0) {
								batchArray[bindex++] = {
									taglbl: label,
									resol: reso,
									sampletype: typ,
									divide: divi
								};
							}
							else {
								batchArray[bindex++] = {
									taglbl: label,
									resol: reso,
									sampletype: typ
								};
							}
						}
					}
				}/* fin for */
				if (batchArray.length == 0) {
					throw new Error('no batch tag defined for the device');
				}

				if (tagsize < 0) {
					if (batchArray.length <= 2) tagsize = 1;
					else if (batchArray.length <= 4) tagsize = 2;
					else if (batchArray.length <= 8) tagsize = 3;
					else if (batchArray.length <= 16) tagsize = 4;
					else if (batchArray.length <= 32) tagsize = 5;
					else if (batchArray.length <= 64) tagsize = 6;
					else tagsize = 7;
				}

			}
			else {
				throw new Error('no batch tag defined for the device');
			}
		}

		var decodedObj = brUncompress(tagsize, batchArray, encoded, payloadTimestamp);
		decodedObj.batchCounter = decodedObj.batch_counter;
		delete decodedObj.batch_counter;
		decodedObj.batchTimestamp = decodedObj.batch_absolute_timestamp;
		delete decodedObj.batch_absolute_timestamp;
		delete decodedObj.batch_relative_timestamp;

		var datas = decodedObj.dataset;
		var nb_measures = datas.length;

		for (var i = 0; i < batchNamesArray.length; i++) {
			var name = batchNamesArray[i];
			var unite = batchUnitsArray[i];
			var obj = {};
			if (unite != 'NoUnit') obj.unit = unite;
			obj.values = [];
			decodedObj[name] = obj;
		}
		for (var j = 0; j < nb_measures; j++) {
			var m = datas[j];
			var timest = m.data_absolute_timestamp;
			var lab = m.data.label;
			var val = m.data.value;
			var index = -1;
			for (var k = 0; k < batchArray.length; k++) {
				if (batchArray[k].taglbl == lab) index = k;
			}
			if (index != -1) {
				var name = batchNamesArray[index];
				var unite = batchUnitsArray[index];
				var cou = {};
				cou.timestamp = timest;
				cou.value = val;
				decodedObj[name].values.push(cou);
			}
		}
		delete decodedObj.dataset;

		if (decodedObj == {}) throw new Error('empty decoding');
		return JSON.stringify(decodedObj);
	} catch (e) {
		return "{\"error\":\"decoding failed : " + e.message + "\"}";
	}

}
