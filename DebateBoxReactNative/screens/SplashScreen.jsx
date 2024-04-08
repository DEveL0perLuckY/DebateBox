import React from "react";
import { View, Text, StyleSheet } from "react-native";

const SplashScreen = () => {
  return (
    <View style={styles.container}>
      <Text style={styles.text}>DebateBox</Text>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#ffffff", // Adjust the background color as needed
  },
  text: {
    fontSize: 24,
    fontWeight: "bold",
    color: "#333333", // Adjust the text color as needed
  },
});

export default SplashScreen;
