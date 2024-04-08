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
import Signup from "../service/RegistrationService";

const myToast = (type, title, message) => {
  Toast.show({
    type: type,
    text1: title,
    text2: message,
  });
};

const SignUpScreen = ({ navigation }) => {
  const { handleLogin } = useAuth();

  const {
    control,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm();
  const [showPassword, setShowPassword] = useState(false);

  const onSubmit = async (data) => {
    const credentials = {
      email: data.email,
      userName: data.userName,
      password: data.password,
    };
    try {
      const response = await Signup(credentials);
      if (response.data.token != null) {
        myToast("success", "Registration successful!", "You can now log in");
        await handleLogin(response.data.token);
      } else {
        switch (response.data.message) {
          case "email":
            myToast("error", "Registration Error", "Email already exists");
            break;
          case "username":
            myToast("error", "Registration Error", "Username already exists");
            break;
          default:
            myToast("error", "Registration Error", "Unknown error occurred");
            break;
        }
      }
    } catch (error) {
      myToast("error", "Registration error", error.message);
    }
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Sign Up</Text>
      <Controller
        control={control}
        render={({ field: { onChange, onBlur, value } }) => (
          <TextInput
            style={styles.input}
            onBlur={onBlur}
            onChangeText={(value) => onChange(value)}
            value={value}
            placeholder="User Name"
          />
        )}
        name="userName"
        rules={{ required: true }}
      />
      {errors.userName && (
        <Text style={styles.error}>User Name is required.</Text>
      )}
      <Controller
        control={control}
        render={({ field: { onChange, onBlur, value } }) => (
          <TextInput
            style={styles.input}
            onBlur={onBlur}
            onChangeText={(value) => onChange(value)}
            value={value}
            placeholder="Email"
            keyboardType="email-address"
          />
        )}
        name="email"
        rules={{ required: true }}
      />
      {errors.email && <Text style={styles.error}>email is required.</Text>}
      <Controller
        control={control}
        render={({ field: { onChange, onBlur, value } }) => (
          <TextInput
            style={styles.input}
            onBlur={onBlur}
            onChangeText={(value) => onChange(value)}
            value={value}
            placeholder="Password"
            secureTextEntry={!showPassword}
          />
        )}
        name="password"
        rules={{ required: true }}
      />
      {errors.password && (
        <Text style={styles.error}>password is required.</Text>
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
        title="Sign Up"
        onPress={handleSubmit(onSubmit)}
      />

      <Text
        style={styles.switchText}
        onPress={() => navigation.navigate("SignIn")}
      >
        Already have an account? Sign In
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

export default SignUpScreen;
