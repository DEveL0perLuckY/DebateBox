import React, { useEffect, useState } from "react";
import { StyleSheet, Text, View } from "react-native";
import Toast from "react-native-toast-message";
import Profile from "../service/ProfileService";
import AsyncStorage from "@react-native-async-storage/async-storage";

function ProfileScreen() {
  const [user, setUser] = useState(null);

  useEffect(() => {
    fetchProfile();
  }, []);

  const fetchProfile = async () => {
    try {
      const token = await AsyncStorage.getItem("token");
      const response = await Profile(token);

      if (response.status === 200) {
        // Check if the response is OK
        const userData = response.data; // Extract user data from the response
        setUser(userData); // Set user data
        showToast("success", "Profile fetched successfully");
      } else {
        showToast("error", "Failed to fetch profile");
      }
    } catch (error) {
      console.error(error);
      showToast("error", "An error occurred while fetching profile");
    }
  };

  const showToast = (type, message) => {
    Toast.show({
      type: type,
      text1: message,
    });
  };

  return (
    <>
      <View style={styles.container}>
        {user ? (
          <ProfileContainer user={user} />
        ) : (
          <Text style={styles.loadingText}>Loading...</Text>
        )}
        <Toast autoHide={true} visibilityTime={2500} />
      </View>
    </>
  );
}

const ProfileContainer = ({ user }) => (
  <View style={styles.profileContainer}>
    <Text style={styles.userInfo}>User ID: {user.id}</Text>
    <Text style={styles.userInfo}>User Name: {user.userName}</Text>
    <Text style={styles.userInfo}>Email: {user.email}</Text>
    <Text style={styles.roleTitle}>Roles:</Text>
    <View style={styles.rolesContainer}>
      {user.roles.map((role) => (
        <Text key={role} style={styles.role}>
          {role}
        </Text>
      ))}
    </View>
  </View>
);

export default ProfileScreen;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
    alignItems: "center",
    justifyContent: "center",
  },
  profileContainer: {
    alignItems: "center",
    padding: 20,
    borderWidth: 1,
    borderColor: "#ccc",
    borderRadius: 10,
    width: "90%",
  },
  userInfo: {
    marginBottom: 10,
    fontSize: 16,
  },
  roleTitle: {
    marginTop: 10,
    marginBottom: 5,
    fontSize: 18,
    fontWeight: "bold",
  },
  rolesContainer: {
    marginLeft: 10,
  },
  role: {
    fontSize: 16,
    marginBottom: 5,
  },
  loadingText: {
    fontSize: 18,
  },
});
