import * as React from "react";
import { Ionicons } from "@expo/vector-icons";
import { NavigationContainer } from "@react-navigation/native";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import HomeScreen from "./screens/HomeScreen";
import ProfileScreen from "./screens/ProfileScreen";
import TopicScreen from "./screens/TopicScreen";
import SignInScreen from "./screens/SignInScreen"; // New screen for signing in
import SignUpScreen from "./screens/SignUpScreen"; // New screen for signing up
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import { AuthProvider, useAuth } from "./service/AuthContext";
import SplashScreen from "./screens/SplashScreen";

const Tab = createBottomTabNavigator();
const Stack = createNativeStackNavigator();

export default function App() {
  return (
    <AuthProvider>
      <AppContent /> 
      {/* changes */}
    </AuthProvider>
  );
}

function AppContent() {
  const { isAuthenticated, handleLogout } = useAuth(); // Accessing isAuthenticated and handleLogout from AuthContext

  const [isLoading, setIsLoading] = React.useState(true); // Define loading state

  React.useEffect(() => {
    // Simulate loading for 2 seconds
    const timer = setTimeout(() => {
      setIsLoading(false);
    }, 2000);

    return () => clearTimeout(timer); // Cleanup on unmount
  }, []);
  if (isLoading) {
    return <SplashScreen />;
  }

  return (
    <NavigationContainer>
      <Stack.Navigator
        screenOptions={{
          headerStyle: { backgroundColor: "orange" },
          headerTitleStyle: {
            color: "white",
            fontWeight: "bold",
          },
        }}
      >
        {isAuthenticated ? (
          <>
            <Stack.Screen
              name="DebeateBox"
              component={MainTabs}
              options={{
                title: "Debeate Box",
                headerRight: () => (
                  <Ionicons
                    name="log-out"
                    size={24}
                    color="white"
                    style={{ marginRight: 15 }}
                    onPress={handleLogout}
                  />
                ),
              }}
            />
            <Stack.Screen
              name="TopicScreen"
              component={TopicScreen}
              options={{
                title: "Topic Screen",
              }}
            />
          
          </>
        ) : (
          <>
            <Stack.Screen
              name="SignIn"
              component={SignInScreen}
              options={{ title: "Sign In" }}
            />
            <Stack.Screen
              name="SignUp"
              component={SignUpScreen}
              options={{
                title: "Sign Up",
              }}
            />
          </>
        )}
      </Stack.Navigator>
    </NavigationContainer>
  );
}

function MainTabs() {
  return (
    <Tab.Navigator
      screenOptions={({ route }) => ({
        tabBarIcon: ({ focused, color, size }) => {
          let iconName;

          if (route.name === "Home") {
            iconName = focused ? "home" : "home-outline";
          } else if (route.name === "Profile") {
            iconName = focused ? "person-circle" : "person-circle-outline";
          }

          return <Ionicons name={iconName} size={size} color={color} />;
        },
        tabBarActiveTintColor: "tomato",
        tabBarInactiveTintColor: "gray",
      })}
    >
      <Tab.Screen
        options={{ headerShown: false }}
        name="Home"
        component={HomeScreen}
      />
      <Tab.Screen
        // options={{ headerShown: false }}
        name="Profile"
        component={ProfileScreen}
      />
    </Tab.Navigator>
  );
}