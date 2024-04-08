import React, { useState } from "react";
import {
  View,
  TextInput,
  StyleSheet,
  Button,
  Text,
  Switch,
} from "react-native";
import { useForm, Controller } from "react-hook-form";
import Toast from "react-native-toast-message";
import { useAuth } from "../service/AuthContext";
import Login from "../service/LoginService";

const myToast = (type, title, message) => {
  Toast.show({
    type: type,
    text1: title,
    text2: message,
  });
};

const SignInScreen = ({ navigation }) => {
  const { handleLogin } = useAuth();
  const {
    control,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm();

  const [showPassword, setShowPassword] = useState(false);

  const onSubmit = async (data) => {
    const credentials = {
      userName: data.userName,
      password: data.password,
    };
  
    try {
      const response = await Login(credentials);
      if (response.data.token != null) {
        myToast("success", "Login Successful", "Success");
        await handleLogin(response.data.token);
      } else {
        switch (response.data.message) {
          case "Invalid":
            myToast("error", "Invalid email or password", "Please try again");
            break;
          default:
            myToast("error", "Login Error", "Unknown error occurred");
            break;
        }
      }
    } catch (error) {
      myToast("error", "Error while logging in", error.message);
    }
  };
  
  return (
    <View style={styles.container}>
      <Text style={styles.title}>Sign In</Text>
      <Controller
        control={control}
        rules={{ required: true }}
        render={({ field: { onChange, onBlur, value } }) => (
          <TextInput
            style={styles.input}
            onBlur={onBlur}
            onChangeText={onChange}
            value={value}
            placeholder="User Name"
          />
        )}
        name="userName"
      />
      {errors.userName && (
        <Text style={styles.error}>User Name is required.</Text>
      )}

      <Controller
        control={control}
        rules={{ required: true }}
        render={({ field: { onChange, onBlur, value } }) => (
          <TextInput
            style={styles.input}
            onBlur={onBlur}
            onChangeText={onChange}
            value={value}
            placeholder="Password"
            secureTextEntry={!showPassword} // Toggle secureTextEntry based on showPassword state
          />
        )}
        name="password"
      />
      {errors.password && (
        <Text style={styles.error}>Password is required.</Text>
      )}

      <View style={styles.checkboxContainer}>
        <Text>Show Password</Text>
        <Switch
          value={showPassword}
          onValueChange={(value) => setShowPassword(value)}
        />
      </View>

      <Button
        disabled={isSubmitting}
        title="Sign In"
        onPress={handleSubmit(onSubmit)}
      />

      <Text
        style={styles.switchText}
        onPress={() => navigation.navigate("SignUp")}
      >
        Don't have an account? Sign Up
      </Text>
      <Toast autoHide={true} visibilityTime={2500} />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    paddingHorizontal: 20,
    backgroundColor: "#ffffff",
  },
  title: {
    fontSize: 24,
    fontWeight: "bold",
    marginBottom: 20,
  },
  input: {
    width: "100%",
    height: 40,
    borderColor: "#ccc",
    borderWidth: 1,
    borderRadius: 5,
    paddingHorizontal: 10,
    marginBottom: 10,
  },
  switchText: {
    marginTop: 20,
    color: "#007bff",
    textDecorationLine: "underline",
  },
  error: {
    color: "red",
    marginBottom: 10,
  },
  checkboxContainer: {
    flexDirection: "row",
    alignItems: "center",
    marginBottom: 10,
  },
});

export default SignInScreen;
