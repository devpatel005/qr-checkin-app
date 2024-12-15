import React, { useRef, useState, useEffect } from "react";
import {
  Text,
  View,
  StyleSheet,
  Modal,
  Pressable,
  TouchableOpacity,
  Animated,
} from "react-native";
import { BarCodeScanner } from "expo-barcode-scanner"; // make sure to change this
import axios from "axios";
import { Icon } from '@rneui/themed';

const FadeInView = props => {
  const fadeAnim = useRef(new Animated.Value(0)).current; // Initial value for opacity: 0

  useEffect(() => {
    Animated.timing(fadeAnim, {
      toValue: 1,
      duration: 1000,
      useNativeDriver: true,
    }).start();
  }, [fadeAnim]);

  return (
    <Animated.View // Special animatable View
      style={{
        ...props.style,
        opacity: fadeAnim, // Bind opacity to animated value
      }}>
      {props.children}
    </Animated.View>
  );
};

export default function App() {
  const [hasPermission, setHasPermission] = useState(null);
  const [scanned, setScanned] = useState(false);
  const [jsonData, setJsonData] = useState(null);
  const [modalVisible, setModalVisible] = useState(false);
  const [isScanning, setIsScanning] = useState(true);

  useEffect(() => {
    const getBarCodeScannerPermissions = async () => {
      const { status } = await BarCodeScanner.requestPermissionsAsync();
      setHasPermission(status === "granted");
    };

    getBarCodeScannerPermissions();
  }, []);

  const resetScanner = () => {
    setScanned(false);
    setModalVisible(false);
    setJsonData(null);
    setIsScanning(true);
  };

  async function handleBarCodeScanned({ data }) {
    if (!isScanning) return;
    setIsScanning(false);

    try {
      //const response = await axios.post(`http://REPLACE:8080/api/checkIn/${data}`);
      setJsonData(response.data);
      setModalVisible(true);
      setScanned(true);
      
      setTimeout(() => {
        setIsScanning(true);
      }, 2000);
    } catch (error) {
      console.error("Error fetching delegate data:", error);
      setJsonData(false);
      setModalVisible(true);
      setIsScanning(true);
      setScanned(true);
    }
  }

  if (hasPermission === null) {
    return <Text>Requesting for camera permission</Text>;
  }
  if (hasPermission === false) {
    return <Text>No access to camera</Text>;
  }

  return (
    <View style={styles.container}>
      <Text style={styles.title}>KDC QR Check In</Text>

      {!scanned ? (
        <FadeInView style={styles.scannerContainer}>
          <BarCodeScanner
            style={styles.camera}
            onBarCodeScanned={isScanning ? handleBarCodeScanned : undefined}
            barCodeTypes={[BarCodeScanner.Constants.BarCodeType.qr]}
          />
        </FadeInView>
      ) : (
        <TouchableOpacity
          style={styles.closeButtonStatic}
          onPress={resetScanner}
        >
          <Text style={styles.buttonStyle}>RESCAN</Text>
        </TouchableOpacity>
      )}
      
      <DelegateModal 
        visible={modalVisible}
        data={jsonData}
        onClose={() => setModalVisible(false)}
      />
    </View>
  );
}

const DelegateModal = ({ visible, data, onClose }) => {
  const renderContent = () => {
    if (!data) return null;

    if (data === false) {
      return (
        <View>
          <Icon name='warning' type='font-awesome' color='#f50' size={60} containerStyle={styles.icon} />
          <Text style={[styles.errorSize, styles.detailsTextStyle]}>Delegate Is Not Found!</Text>
        </View>
      );
    }

    const { registration_status } = data;

    if (registration_status === "Not Registered" || registration_status === "Not Attending") {
      return (
        <View>
          <Icon name='warning' type='font-awesome' color='#f50' size={60} containerStyle={styles.icon} />
          <Text style={[styles.errorSize, styles.detailsTextStyle]}>
            Delegate Is Marked {registration_status}
          </Text>
        </View>
      );
    }

    if (registration_status === "Pending Liability Form") {
      return (
        <View>
          <Icon name='exclamation-circle' type='font-awesome' color='gold' size={60} containerStyle={styles.icon} />
          <Text style={[styles.detailsTextStyle]}>Sign Liability Form!</Text>
          <Text style={styles.detailsTextStyle}>Check-In Status: {data.check_in_status}</Text>
        </View>
      );
    }

    return (
      <View>
        <Icon name='check-circle' type='font-awesome' color='green' size={60} containerStyle={styles.icon} />
        <Text style={[styles.detailsTextStyle]}>Name: {data.firstName + " " + data.lastName}</Text>
        <Text style={styles.detailsTextStyle}>Grade: {data.gradeLvl}</Text>
        <Text style={styles.detailsTextStyle}>Wing: {data.wing}</Text>
        <Text style={styles.detailsTextStyle}>Father's Name: {data.fatherName}</Text>
        <Text style={styles.detailsTextStyle}>Mother's Name: {data.motherName}</Text>
        <Text style={styles.detailsTextStyle}>Father's Num: {data.fatherNum}</Text>
        <Text style={styles.detailsTextStyle}>Mother's Num: {data.motherNum}</Text>
        <Text style={styles.detailsTextStyle}>Check-In Status: Complete</Text>
      </View>
    );
  };

  return (
    <Modal
      animationType="slide"
      transparent={true}
      visible={visible}
      onRequestClose={onClose}
    >
      <View style={styles.centeredView}>
        <View style={styles.modalView}>
          {renderContent()}
          <Pressable
            style={[styles.closeButtonStatic, styles.buttonOnClose]}
            onPress={onClose}
          >
            <Text style={styles.buttonStyle}>CLOSE</Text>
          </Pressable>
        </View>
      </View>
    </Modal>
  );
};

const styles = StyleSheet.create({
  title: {
    textAlign: "center",
    fontSize: 30,
    lineHeight: 30,
    fontWeight: "bold",
    letterSpacing: 0.25,
    color: "white",
    top: -150,
  },
  errorSize: {
    fontSize: 14.6
  },
  centeredView: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    marginTop: 22,
  },
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "grey",
  },
  camera: {
    top: -100,
    width: 300,
    height: 300,
  },
  closeButtonStatic: {
    width: "auto",
    padding: 10,
    margin: 10,
    backgroundColor: "green",
    justifyContent: "space-between",
    borderRadius: 20,
  },
  icon: {
    marginBottom: 20,
  },
  buttonOnClose: {
    backgroundColor: "#2196F3",
  },
  buttonStyle: {
    color: "white",
    fontWeight: "bold",
    textAlign: "center",
  },
  detailsTextStyle: {
    color: "black",
    fontWeight: "bold",
    textAlign: "left",
    margin: 2
  },
  modalView: {
    margin: 20,
    backgroundColor: "white",
    borderRadius: 20,
    padding: 35,
    alignItems: 'center',
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.25,
    shadowRadius: 4,
    elevation: 5,
  },
  scannerContainer: {
    width: 300,
    height: 50,
    backgroundColor: 'powderblue',
  },
});
